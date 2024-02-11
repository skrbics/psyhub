import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMMPITestCardFeature, NewMMPITestCardFeature } from '../mmpi-test-card-feature.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMMPITestCardFeature for edit and NewMMPITestCardFeatureFormGroupInput for create.
 */
type MMPITestCardFeatureFormGroupInput = IMMPITestCardFeature | PartialWithRequiredKeyOf<NewMMPITestCardFeature>;

type MMPITestCardFeatureFormDefaults = Pick<NewMMPITestCardFeature, 'id' | 'answerYes'>;

type MMPITestCardFeatureFormGroupContent = {
  id: FormControl<IMMPITestCardFeature['id'] | NewMMPITestCardFeature['id']>;
  answerYes: FormControl<IMMPITestCardFeature['answerYes']>;
  mMPITestCard: FormControl<IMMPITestCardFeature['mMPITestCard']>;
  mMPIFeature: FormControl<IMMPITestCardFeature['mMPIFeature']>;
};

export type MMPITestCardFeatureFormGroup = FormGroup<MMPITestCardFeatureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MMPITestCardFeatureFormService {
  createMMPITestCardFeatureFormGroup(mMPITestCardFeature: MMPITestCardFeatureFormGroupInput = { id: null }): MMPITestCardFeatureFormGroup {
    const mMPITestCardFeatureRawValue = {
      ...this.getFormDefaults(),
      ...mMPITestCardFeature,
    };
    return new FormGroup<MMPITestCardFeatureFormGroupContent>({
      id: new FormControl(
        { value: mMPITestCardFeatureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      answerYes: new FormControl(mMPITestCardFeatureRawValue.answerYes),
      mMPITestCard: new FormControl(mMPITestCardFeatureRawValue.mMPITestCard),
      mMPIFeature: new FormControl(mMPITestCardFeatureRawValue.mMPIFeature),
    });
  }

  getMMPITestCardFeature(form: MMPITestCardFeatureFormGroup): IMMPITestCardFeature | NewMMPITestCardFeature {
    return form.getRawValue() as IMMPITestCardFeature | NewMMPITestCardFeature;
  }

  resetForm(form: MMPITestCardFeatureFormGroup, mMPITestCardFeature: MMPITestCardFeatureFormGroupInput): void {
    const mMPITestCardFeatureRawValue = { ...this.getFormDefaults(), ...mMPITestCardFeature };
    form.reset(
      {
        ...mMPITestCardFeatureRawValue,
        id: { value: mMPITestCardFeatureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MMPITestCardFeatureFormDefaults {
    return {
      id: null,
      answerYes: false,
    };
  }
}
