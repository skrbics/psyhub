import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmpi-feature.test-samples';

import { MMPIFeatureFormService } from './mmpi-feature-form.service';

describe('MMPIFeature Form Service', () => {
  let service: MMPIFeatureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MMPIFeatureFormService);
  });

  describe('Service methods', () => {
    describe('createMMPIFeatureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMMPIFeatureFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IMMPIFeature should create a new form with FormGroup', () => {
        const formGroup = service.createMMPIFeatureFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getMMPIFeature', () => {
      it('should return NewMMPIFeature for default MMPIFeature initial value', () => {
        const formGroup = service.createMMPIFeatureFormGroup(sampleWithNewData);

        const mMPIFeature = service.getMMPIFeature(formGroup) as any;

        expect(mMPIFeature).toMatchObject(sampleWithNewData);
      });

      it('should return NewMMPIFeature for empty MMPIFeature initial value', () => {
        const formGroup = service.createMMPIFeatureFormGroup();

        const mMPIFeature = service.getMMPIFeature(formGroup) as any;

        expect(mMPIFeature).toMatchObject({});
      });

      it('should return IMMPIFeature', () => {
        const formGroup = service.createMMPIFeatureFormGroup(sampleWithRequiredData);

        const mMPIFeature = service.getMMPIFeature(formGroup) as any;

        expect(mMPIFeature).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMMPIFeature should not enable id FormControl', () => {
        const formGroup = service.createMMPIFeatureFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMMPIFeature should disable id FormControl', () => {
        const formGroup = service.createMMPIFeatureFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
