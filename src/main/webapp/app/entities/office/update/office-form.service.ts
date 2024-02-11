import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOffice, NewOffice } from '../office.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOffice for edit and NewOfficeFormGroupInput for create.
 */
type OfficeFormGroupInput = IOffice | PartialWithRequiredKeyOf<NewOffice>;

type OfficeFormDefaults = Pick<NewOffice, 'id'>;

type OfficeFormGroupContent = {
  id: FormControl<IOffice['id'] | NewOffice['id']>;
  officeName: FormControl<IOffice['officeName']>;
  website: FormControl<IOffice['website']>;
  email: FormControl<IOffice['email']>;
  phone: FormControl<IOffice['phone']>;
  address: FormControl<IOffice['address']>;
};

export type OfficeFormGroup = FormGroup<OfficeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OfficeFormService {
  createOfficeFormGroup(office: OfficeFormGroupInput = { id: null }): OfficeFormGroup {
    const officeRawValue = {
      ...this.getFormDefaults(),
      ...office,
    };
    return new FormGroup<OfficeFormGroupContent>({
      id: new FormControl(
        { value: officeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      officeName: new FormControl(officeRawValue.officeName),
      website: new FormControl(officeRawValue.website),
      email: new FormControl(officeRawValue.email),
      phone: new FormControl(officeRawValue.phone),
      address: new FormControl(officeRawValue.address),
    });
  }

  getOffice(form: OfficeFormGroup): IOffice | NewOffice {
    return form.getRawValue() as IOffice | NewOffice;
  }

  resetForm(form: OfficeFormGroup, office: OfficeFormGroupInput): void {
    const officeRawValue = { ...this.getFormDefaults(), ...office };
    form.reset(
      {
        ...officeRawValue,
        id: { value: officeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OfficeFormDefaults {
    return {
      id: null,
    };
  }
}
