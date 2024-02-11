import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MMPITestCardService } from '../service/mmpi-test-card.service';
import { IMMPITestCard } from '../mmpi-test-card.model';
import { MMPITestCardFormService } from './mmpi-test-card-form.service';

import { MMPITestCardUpdateComponent } from './mmpi-test-card-update.component';

describe('MMPITestCard Management Update Component', () => {
  let comp: MMPITestCardUpdateComponent;
  let fixture: ComponentFixture<MMPITestCardUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mMPITestCardFormService: MMPITestCardFormService;
  let mMPITestCardService: MMPITestCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MMPITestCardUpdateComponent],
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
      .overrideTemplate(MMPITestCardUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MMPITestCardUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mMPITestCardFormService = TestBed.inject(MMPITestCardFormService);
    mMPITestCardService = TestBed.inject(MMPITestCardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mMPITestCard: IMMPITestCard = { id: 456 };

      activatedRoute.data = of({ mMPITestCard });
      comp.ngOnInit();

      expect(comp.mMPITestCard).toEqual(mMPITestCard);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITestCard>>();
      const mMPITestCard = { id: 123 };
      jest.spyOn(mMPITestCardFormService, 'getMMPITestCard').mockReturnValue(mMPITestCard);
      jest.spyOn(mMPITestCardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITestCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPITestCard }));
      saveSubject.complete();

      // THEN
      expect(mMPITestCardFormService.getMMPITestCard).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mMPITestCardService.update).toHaveBeenCalledWith(expect.objectContaining(mMPITestCard));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITestCard>>();
      const mMPITestCard = { id: 123 };
      jest.spyOn(mMPITestCardFormService, 'getMMPITestCard').mockReturnValue({ id: null });
      jest.spyOn(mMPITestCardService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITestCard: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPITestCard }));
      saveSubject.complete();

      // THEN
      expect(mMPITestCardFormService.getMMPITestCard).toHaveBeenCalled();
      expect(mMPITestCardService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITestCard>>();
      const mMPITestCard = { id: 123 };
      jest.spyOn(mMPITestCardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITestCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mMPITestCardService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
