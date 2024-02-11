import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../therapist.test-samples';

import { TherapistFormService } from './therapist-form.service';

describe('Therapist Form Service', () => {
  let service: TherapistFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TherapistFormService);
  });

  describe('Service methods', () => {
    describe('createTherapistFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTherapistFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            userid: expect.any(Object),
            office: expect.any(Object),
          }),
        );
      });

      it('passing ITherapist should create a new form with FormGroup', () => {
        const formGroup = service.createTherapistFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            userid: expect.any(Object),
            office: expect.any(Object),
          }),
        );
      });
    });

    describe('getTherapist', () => {
      it('should return NewTherapist for default Therapist initial value', () => {
        const formGroup = service.createTherapistFormGroup(sampleWithNewData);

        const therapist = service.getTherapist(formGroup) as any;

        expect(therapist).toMatchObject(sampleWithNewData);
      });

      it('should return NewTherapist for empty Therapist initial value', () => {
        const formGroup = service.createTherapistFormGroup();

        const therapist = service.getTherapist(formGroup) as any;

        expect(therapist).toMatchObject({});
      });

      it('should return ITherapist', () => {
        const formGroup = service.createTherapistFormGroup(sampleWithRequiredData);

        const therapist = service.getTherapist(formGroup) as any;

        expect(therapist).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITherapist should not enable id FormControl', () => {
        const formGroup = service.createTherapistFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTherapist should disable id FormControl', () => {
        const formGroup = service.createTherapistFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
