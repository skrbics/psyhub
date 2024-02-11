import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmpi-test-card.test-samples';

import { MMPITestCardFormService } from './mmpi-test-card-form.service';

describe('MMPITestCard Form Service', () => {
  let service: MMPITestCardFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MMPITestCardFormService);
  });

  describe('Service methods', () => {
    describe('createMMPITestCardFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMMPITestCardFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            question: expect.any(Object),
          }),
        );
      });

      it('passing IMMPITestCard should create a new form with FormGroup', () => {
        const formGroup = service.createMMPITestCardFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            question: expect.any(Object),
          }),
        );
      });
    });

    describe('getMMPITestCard', () => {
      it('should return NewMMPITestCard for default MMPITestCard initial value', () => {
        const formGroup = service.createMMPITestCardFormGroup(sampleWithNewData);

        const mMPITestCard = service.getMMPITestCard(formGroup) as any;

        expect(mMPITestCard).toMatchObject(sampleWithNewData);
      });

      it('should return NewMMPITestCard for empty MMPITestCard initial value', () => {
        const formGroup = service.createMMPITestCardFormGroup();

        const mMPITestCard = service.getMMPITestCard(formGroup) as any;

        expect(mMPITestCard).toMatchObject({});
      });

      it('should return IMMPITestCard', () => {
        const formGroup = service.createMMPITestCardFormGroup(sampleWithRequiredData);

        const mMPITestCard = service.getMMPITestCard(formGroup) as any;

        expect(mMPITestCard).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMMPITestCard should not enable id FormControl', () => {
        const formGroup = service.createMMPITestCardFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMMPITestCard should disable id FormControl', () => {
        const formGroup = service.createMMPITestCardFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
