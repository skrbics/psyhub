import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITherapist, NewTherapist } from '../therapist.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITherapist for edit and NewTherapistFormGroupInput for create.
 */
type TherapistFormGroupInput = ITherapist | PartialWithRequiredKeyOf<NewTherapist>;

type TherapistFormDefaults = Pick<NewTherapist, 'id'>;

type TherapistFormGroupContent = {
  id: FormControl<ITherapist['id'] | NewTherapist['id']>;
  firstName: FormControl<ITherapist['firstName']>;
  middleName: FormControl<ITherapist['middleName']>;
  lastName: FormControl<ITherapist['lastName']>;
  userid: FormControl<ITherapist['userid']>;
  office: FormControl<ITherapist['office']>;
};

export type TherapistFormGroup = FormGroup<TherapistFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TherapistFormService {
  createTherapistFormGroup(therapist: TherapistFormGroupInput = { id: null }): TherapistFormGroup {
    const therapistRawValue = {
      ...this.getFormDefaults(),
      ...therapist,
    };
    return new FormGroup<TherapistFormGroupContent>({
      id: new FormControl(
        { value: therapistRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(therapistRawValue.firstName),
      middleName: new FormControl(therapistRawValue.middleName),
      lastName: new FormControl(therapistRawValue.lastName),
      userid: new FormControl(therapistRawValue.userid),
      office: new FormControl(therapistRawValue.office),
    });
  }

  getTherapist(form: TherapistFormGroup): ITherapist | NewTherapist {
    return form.getRawValue() as ITherapist | NewTherapist;
  }

  resetForm(form: TherapistFormGroup, therapist: TherapistFormGroupInput): void {
    const therapistRawValue = { ...this.getFormDefaults(), ...therapist };
    form.reset(
      {
        ...therapistRawValue,
        id: { value: therapistRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TherapistFormDefaults {
    return {
      id: null,
    };
  }
}
