import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMMPIFeature, NewMMPIFeature } from '../mmpi-feature.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMMPIFeature for edit and NewMMPIFeatureFormGroupInput for create.
 */
type MMPIFeatureFormGroupInput = IMMPIFeature | PartialWithRequiredKeyOf<NewMMPIFeature>;

type MMPIFeatureFormDefaults = Pick<NewMMPIFeature, 'id'>;

type MMPIFeatureFormGroupContent = {
  id: FormControl<IMMPIFeature['id'] | NewMMPIFeature['id']>;
  name: FormControl<IMMPIFeature['name']>;
};

export type MMPIFeatureFormGroup = FormGroup<MMPIFeatureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MMPIFeatureFormService {
  createMMPIFeatureFormGroup(mMPIFeature: MMPIFeatureFormGroupInput = { id: null }): MMPIFeatureFormGroup {
    const mMPIFeatureRawValue = {
      ...this.getFormDefaults(),
      ...mMPIFeature,
    };
    return new FormGroup<MMPIFeatureFormGroupContent>({
      id: new FormControl(
        { value: mMPIFeatureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(mMPIFeatureRawValue.name),
    });
  }

  getMMPIFeature(form: MMPIFeatureFormGroup): IMMPIFeature | NewMMPIFeature {
    return form.getRawValue() as IMMPIFeature | NewMMPIFeature;
  }

  resetForm(form: MMPIFeatureFormGroup, mMPIFeature: MMPIFeatureFormGroupInput): void {
    const mMPIFeatureRawValue = { ...this.getFormDefaults(), ...mMPIFeature };
    form.reset(
      {
        ...mMPIFeatureRawValue,
        id: { value: mMPIFeatureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MMPIFeatureFormDefaults {
    return {
      id: null,
    };
  }
}
