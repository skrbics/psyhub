import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICurrency } from 'app/entities/currency/currency.model';
import { CurrencyService } from 'app/entities/currency/service/currency.service';
import { SessionBillService } from '../service/session-bill.service';
import { ISessionBill } from '../session-bill.model';
import { SessionBillFormService } from './session-bill-form.service';

import { SessionBillUpdateComponent } from './session-bill-update.component';

describe('SessionBill Management Update Component', () => {
  let comp: SessionBillUpdateComponent;
  let fixture: ComponentFixture<SessionBillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sessionBillFormService: SessionBillFormService;
  let sessionBillService: SessionBillService;
  let currencyService: CurrencyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SessionBillUpdateComponent],
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
      .overrideTemplate(SessionBillUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SessionBillUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sessionBillFormService = TestBed.inject(SessionBillFormService);
    sessionBillService = TestBed.inject(SessionBillService);
    currencyService = TestBed.inject(CurrencyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Currency query and add missing value', () => {
      const sessionBill: ISessionBill = { id: 456 };
      const currency: ICurrency = { id: 16599 };
      sessionBill.currency = currency;

      const currencyCollection: ICurrency[] = [{ id: 14645 }];
      jest.spyOn(currencyService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const additionalCurrencies = [currency];
      const expectedCollection: ICurrency[] = [...additionalCurrencies, ...currencyCollection];
      jest.spyOn(currencyService, 'addCurrencyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sessionBill });
      comp.ngOnInit();

      expect(currencyService.query).toHaveBeenCalled();
      expect(currencyService.addCurrencyToCollectionIfMissing).toHaveBeenCalledWith(
        currencyCollection,
        ...additionalCurrencies.map(expect.objectContaining),
      );
      expect(comp.currenciesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sessionBill: ISessionBill = { id: 456 };
      const currency: ICurrency = { id: 25437 };
      sessionBill.currency = currency;

      activatedRoute.data = of({ sessionBill });
      comp.ngOnInit();

      expect(comp.currenciesSharedCollection).toContain(currency);
      expect(comp.sessionBill).toEqual(sessionBill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISessionBill>>();
      const sessionBill = { id: 123 };
      jest.spyOn(sessionBillFormService, 'getSessionBill').mockReturnValue(sessionBill);
      jest.spyOn(sessionBillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sessionBill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sessionBill }));
      saveSubject.complete();

      // THEN
      expect(sessionBillFormService.getSessionBill).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sessionBillService.update).toHaveBeenCalledWith(expect.objectContaining(sessionBill));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISessionBill>>();
      const sessionBill = { id: 123 };
      jest.spyOn(sessionBillFormService, 'getSessionBill').mockReturnValue({ id: null });
      jest.spyOn(sessionBillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sessionBill: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sessionBill }));
      saveSubject.complete();

      // THEN
      expect(sessionBillFormService.getSessionBill).toHaveBeenCalled();
      expect(sessionBillService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISessionBill>>();
      const sessionBill = { id: 123 };
      jest.spyOn(sessionBillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sessionBill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sessionBillService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCurrency', () => {
      it('Should forward to currencyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(currencyService, 'compareCurrency');
        comp.compareCurrency(entity, entity2);
        expect(currencyService.compareCurrency).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
