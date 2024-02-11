import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../session-bill.test-samples';

import { SessionBillFormService } from './session-bill-form.service';

describe('SessionBill Form Service', () => {
  let service: SessionBillFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionBillFormService);
  });

  describe('Service methods', () => {
    describe('createSessionBillFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSessionBillFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            amount: expect.any(Object),
            paid: expect.any(Object),
            currency: expect.any(Object),
          }),
        );
      });

      it('passing ISessionBill should create a new form with FormGroup', () => {
        const formGroup = service.createSessionBillFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            amount: expect.any(Object),
            paid: expect.any(Object),
            currency: expect.any(Object),
          }),
        );
      });
    });

    describe('getSessionBill', () => {
      it('should return NewSessionBill for default SessionBill initial value', () => {
        const formGroup = service.createSessionBillFormGroup(sampleWithNewData);

        const sessionBill = service.getSessionBill(formGroup) as any;

        expect(sessionBill).toMatchObject(sampleWithNewData);
      });

      it('should return NewSessionBill for empty SessionBill initial value', () => {
        const formGroup = service.createSessionBillFormGroup();

        const sessionBill = service.getSessionBill(formGroup) as any;

        expect(sessionBill).toMatchObject({});
      });

      it('should return ISessionBill', () => {
        const formGroup = service.createSessionBillFormGroup(sampleWithRequiredData);

        const sessionBill = service.getSessionBill(formGroup) as any;

        expect(sessionBill).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISessionBill should not enable id FormControl', () => {
        const formGroup = service.createSessionBillFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSessionBill should disable id FormControl', () => {
        const formGroup = service.createSessionBillFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
