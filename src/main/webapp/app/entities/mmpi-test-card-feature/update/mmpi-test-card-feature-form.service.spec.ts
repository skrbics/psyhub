import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmpi-test-card-feature.test-samples';

import { MMPITestCardFeatureFormService } from './mmpi-test-card-feature-form.service';

describe('MMPITestCardFeature Form Service', () => {
  let service: MMPITestCardFeatureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MMPITestCardFeatureFormService);
  });

  describe('Service methods', () => {
    describe('createMMPITestCardFeatureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            answerYes: expect.any(Object),
            mMPITestCard: expect.any(Object),
            mMPIFeature: expect.any(Object),
          }),
        );
      });

      it('passing IMMPITestCardFeature should create a new form with FormGroup', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            answerYes: expect.any(Object),
            mMPITestCard: expect.any(Object),
            mMPIFeature: expect.any(Object),
          }),
        );
      });
    });

    describe('getMMPITestCardFeature', () => {
      it('should return NewMMPITestCardFeature for default MMPITestCardFeature initial value', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup(sampleWithNewData);

        const mMPITestCardFeature = service.getMMPITestCardFeature(formGroup) as any;

        expect(mMPITestCardFeature).toMatchObject(sampleWithNewData);
      });

      it('should return NewMMPITestCardFeature for empty MMPITestCardFeature initial value', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup();

        const mMPITestCardFeature = service.getMMPITestCardFeature(formGroup) as any;

        expect(mMPITestCardFeature).toMatchObject({});
      });

      it('should return IMMPITestCardFeature', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup(sampleWithRequiredData);

        const mMPITestCardFeature = service.getMMPITestCardFeature(formGroup) as any;

        expect(mMPITestCardFeature).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMMPITestCardFeature should not enable id FormControl', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMMPITestCardFeature should disable id FormControl', () => {
        const formGroup = service.createMMPITestCardFeatureFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
