import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmpi-test.test-samples';

import { MMPITestFormService } from './mmpi-test-form.service';

describe('MMPITest Form Service', () => {
  let service: MMPITestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MMPITestFormService);
  });

  describe('Service methods', () => {
    describe('createMMPITestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMMPITestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            order: expect.any(Object),
            date: expect.any(Object),
            client: expect.any(Object),
          }),
        );
      });

      it('passing IMMPITest should create a new form with FormGroup', () => {
        const formGroup = service.createMMPITestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            order: expect.any(Object),
            date: expect.any(Object),
            client: expect.any(Object),
          }),
        );
      });
    });

    describe('getMMPITest', () => {
      it('should return NewMMPITest for default MMPITest initial value', () => {
        const formGroup = service.createMMPITestFormGroup(sampleWithNewData);

        const mMPITest = service.getMMPITest(formGroup) as any;

        expect(mMPITest).toMatchObject(sampleWithNewData);
      });

      it('should return NewMMPITest for empty MMPITest initial value', () => {
        const formGroup = service.createMMPITestFormGroup();

        const mMPITest = service.getMMPITest(formGroup) as any;

        expect(mMPITest).toMatchObject({});
      });

      it('should return IMMPITest', () => {
        const formGroup = service.createMMPITestFormGroup(sampleWithRequiredData);

        const mMPITest = service.getMMPITest(formGroup) as any;

        expect(mMPITest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMMPITest should not enable id FormControl', () => {
        const formGroup = service.createMMPITestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMMPITest should disable id FormControl', () => {
        const formGroup = service.createMMPITestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
