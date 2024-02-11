import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMMPIAnswer, NewMMPIAnswer } from '../mmpi-answer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMMPIAnswer for edit and NewMMPIAnswerFormGroupInput for create.
 */
type MMPIAnswerFormGroupInput = IMMPIAnswer | PartialWithRequiredKeyOf<NewMMPIAnswer>;

type MMPIAnswerFormDefaults = Pick<NewMMPIAnswer, 'id' | 'answeredYes'>;

type MMPIAnswerFormGroupContent = {
  id: FormControl<IMMPIAnswer['id'] | NewMMPIAnswer['id']>;
  answeredYes: FormControl<IMMPIAnswer['answeredYes']>;
  mMPITest: FormControl<IMMPIAnswer['mMPITest']>;
  mMPITestCard: FormControl<IMMPIAnswer['mMPITestCard']>;
};

export type MMPIAnswerFormGroup = FormGroup<MMPIAnswerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MMPIAnswerFormService {
  createMMPIAnswerFormGroup(mMPIAnswer: MMPIAnswerFormGroupInput = { id: null }): MMPIAnswerFormGroup {
    const mMPIAnswerRawValue = {
      ...this.getFormDefaults(),
      ...mMPIAnswer,
    };
    return new FormGroup<MMPIAnswerFormGroupContent>({
      id: new FormControl(
        { value: mMPIAnswerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      answeredYes: new FormControl(mMPIAnswerRawValue.answeredYes),
      mMPITest: new FormControl(mMPIAnswerRawValue.mMPITest),
      mMPITestCard: new FormControl(mMPIAnswerRawValue.mMPITestCard),
    });
  }

  getMMPIAnswer(form: MMPIAnswerFormGroup): IMMPIAnswer | NewMMPIAnswer {
    return form.getRawValue() as IMMPIAnswer | NewMMPIAnswer;
  }

  resetForm(form: MMPIAnswerFormGroup, mMPIAnswer: MMPIAnswerFormGroupInput): void {
    const mMPIAnswerRawValue = { ...this.getFormDefaults(), ...mMPIAnswer };
    form.reset(
      {
        ...mMPIAnswerRawValue,
        id: { value: mMPIAnswerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MMPIAnswerFormDefaults {
    return {
      id: null,
      answeredYes: false,
    };
  }
}
