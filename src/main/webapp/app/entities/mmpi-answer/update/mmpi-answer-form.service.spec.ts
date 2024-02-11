import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmpi-answer.test-samples';

import { MMPIAnswerFormService } from './mmpi-answer-form.service';

describe('MMPIAnswer Form Service', () => {
  let service: MMPIAnswerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MMPIAnswerFormService);
  });

  describe('Service methods', () => {
    describe('createMMPIAnswerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMMPIAnswerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            answeredYes: expect.any(Object),
            mMPITest: expect.any(Object),
            mMPITestCard: expect.any(Object),
          }),
        );
      });

      it('passing IMMPIAnswer should create a new form with FormGroup', () => {
        const formGroup = service.createMMPIAnswerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            answeredYes: expect.any(Object),
            mMPITest: expect.any(Object),
            mMPITestCard: expect.any(Object),
          }),
        );
      });
    });

    describe('getMMPIAnswer', () => {
      it('should return NewMMPIAnswer for default MMPIAnswer initial value', () => {
        const formGroup = service.createMMPIAnswerFormGroup(sampleWithNewData);

        const mMPIAnswer = service.getMMPIAnswer(formGroup) as any;

        expect(mMPIAnswer).toMatchObject(sampleWithNewData);
      });

      it('should return NewMMPIAnswer for empty MMPIAnswer initial value', () => {
        const formGroup = service.createMMPIAnswerFormGroup();

        const mMPIAnswer = service.getMMPIAnswer(formGroup) as any;

        expect(mMPIAnswer).toMatchObject({});
      });

      it('should return IMMPIAnswer', () => {
        const formGroup = service.createMMPIAnswerFormGroup(sampleWithRequiredData);

        const mMPIAnswer = service.getMMPIAnswer(formGroup) as any;

        expect(mMPIAnswer).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMMPIAnswer should not enable id FormControl', () => {
        const formGroup = service.createMMPIAnswerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMMPIAnswer should disable id FormControl', () => {
        const formGroup = service.createMMPIAnswerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
