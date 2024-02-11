import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { MMPITestService } from '../service/mmpi-test.service';
import { IMMPITest } from '../mmpi-test.model';
import { MMPITestFormService } from './mmpi-test-form.service';

import { MMPITestUpdateComponent } from './mmpi-test-update.component';

describe('MMPITest Management Update Component', () => {
  let comp: MMPITestUpdateComponent;
  let fixture: ComponentFixture<MMPITestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mMPITestFormService: MMPITestFormService;
  let mMPITestService: MMPITestService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MMPITestUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MMPITestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MMPITestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mMPITestFormService = TestBed.inject(MMPITestFormService);
    mMPITestService = TestBed.inject(MMPITestService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Client query and add missing value', () => {
      const mMPITest: IMMPITest = { id: 456 };
      const client: IClient = { id: 6783 };
      mMPITest.client = client;

      const clientCollection: IClient[] = [{ id: 22878 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mMPITest });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(
        clientCollection,
        ...additionalClients.map(expect.objectContaining),
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mMPITest: IMMPITest = { id: 456 };
      const client: IClient = { id: 19656 };
      mMPITest.client = client;

      activatedRoute.data = of({ mMPITest });
      comp.ngOnInit();

      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.mMPITest).toEqual(mMPITest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITest>>();
      const mMPITest = { id: 123 };
      jest.spyOn(mMPITestFormService, 'getMMPITest').mockReturnValue(mMPITest);
      jest.spyOn(mMPITestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPITest }));
      saveSubject.complete();

      // THEN
      expect(mMPITestFormService.getMMPITest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mMPITestService.update).toHaveBeenCalledWith(expect.objectContaining(mMPITest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITest>>();
      const mMPITest = { id: 123 };
      jest.spyOn(mMPITestFormService, 'getMMPITest').mockReturnValue({ id: null });
      jest.spyOn(mMPITestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPITest }));
      saveSubject.complete();

      // THEN
      expect(mMPITestFormService.getMMPITest).toHaveBeenCalled();
      expect(mMPITestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITest>>();
      const mMPITest = { id: 123 };
      jest.spyOn(mMPITestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mMPITestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareClient', () => {
      it('Should forward to clientService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientService, 'compareClient');
        comp.compareClient(entity, entity2);
        expect(clientService.compareClient).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
