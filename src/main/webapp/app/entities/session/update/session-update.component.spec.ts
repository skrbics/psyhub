import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ISessionBill } from 'app/entities/session-bill/session-bill.model';
import { SessionBillService } from 'app/entities/session-bill/service/session-bill.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { ISession } from '../session.model';
import { SessionService } from '../service/session.service';
import { SessionFormService } from './session-form.service';

import { SessionUpdateComponent } from './session-update.component';

describe('Session Management Update Component', () => {
  let comp: SessionUpdateComponent;
  let fixture: ComponentFixture<SessionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sessionFormService: SessionFormService;
  let sessionService: SessionService;
  let sessionBillService: SessionBillService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SessionUpdateComponent],
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
      .overrideTemplate(SessionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SessionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sessionFormService = TestBed.inject(SessionFormService);
    sessionService = TestBed.inject(SessionService);
    sessionBillService = TestBed.inject(SessionBillService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call sessionBill query and add missing value', () => {
      const session: ISession = { id: 456 };
      const sessionBill: ISessionBill = { id: 29450 };
      session.sessionBill = sessionBill;

      const sessionBillCollection: ISessionBill[] = [{ id: 29145 }];
      jest.spyOn(sessionBillService, 'query').mockReturnValue(of(new HttpResponse({ body: sessionBillCollection })));
      const expectedCollection: ISessionBill[] = [sessionBill, ...sessionBillCollection];
      jest.spyOn(sessionBillService, 'addSessionBillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ session });
      comp.ngOnInit();

      expect(sessionBillService.query).toHaveBeenCalled();
      expect(sessionBillService.addSessionBillToCollectionIfMissing).toHaveBeenCalledWith(sessionBillCollection, sessionBill);
      expect(comp.sessionBillsCollection).toEqual(expectedCollection);
    });

    it('Should call Client query and add missing value', () => {
      const session: ISession = { id: 456 };
      const client: IClient = { id: 21322 };
      session.client = client;

      const clientCollection: IClient[] = [{ id: 22001 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ session });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(
        clientCollection,
        ...additionalClients.map(expect.objectContaining),
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const session: ISession = { id: 456 };
      const sessionBill: ISessionBill = { id: 7824 };
      session.sessionBill = sessionBill;
      const client: IClient = { id: 29506 };
      session.client = client;

      activatedRoute.data = of({ session });
      comp.ngOnInit();

      expect(comp.sessionBillsCollection).toContain(sessionBill);
      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.session).toEqual(session);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISession>>();
      const session = { id: 123 };
      jest.spyOn(sessionFormService, 'getSession').mockReturnValue(session);
      jest.spyOn(sessionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ session });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: session }));
      saveSubject.complete();

      // THEN
      expect(sessionFormService.getSession).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sessionService.update).toHaveBeenCalledWith(expect.objectContaining(session));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISession>>();
      const session = { id: 123 };
      jest.spyOn(sessionFormService, 'getSession').mockReturnValue({ id: null });
      jest.spyOn(sessionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ session: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: session }));
      saveSubject.complete();

      // THEN
      expect(sessionFormService.getSession).toHaveBeenCalled();
      expect(sessionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISession>>();
      const session = { id: 123 };
      jest.spyOn(sessionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ session });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sessionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSessionBill', () => {
      it('Should forward to sessionBillService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(sessionBillService, 'compareSessionBill');
        comp.compareSessionBill(entity, entity2);
        expect(sessionBillService.compareSessionBill).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
