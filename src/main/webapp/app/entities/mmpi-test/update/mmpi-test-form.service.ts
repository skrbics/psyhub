import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMMPITest, NewMMPITest } from '../mmpi-test.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMMPITest for edit and NewMMPITestFormGroupInput for create.
 */
type MMPITestFormGroupInput = IMMPITest | PartialWithRequiredKeyOf<NewMMPITest>;

type MMPITestFormDefaults = Pick<NewMMPITest, 'id'>;

type MMPITestFormGroupContent = {
  id: FormControl<IMMPITest['id'] | NewMMPITest['id']>;
  order: FormControl<IMMPITest['order']>;
  date: FormControl<IMMPITest['date']>;
  client: FormControl<IMMPITest['client']>;
};

export type MMPITestFormGroup = FormGroup<MMPITestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MMPITestFormService {
  createMMPITestFormGroup(mMPITest: MMPITestFormGroupInput = { id: null }): MMPITestFormGroup {
    const mMPITestRawValue = {
      ...this.getFormDefaults(),
      ...mMPITest,
    };
    return new FormGroup<MMPITestFormGroupContent>({
      id: new FormControl(
        { value: mMPITestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      order: new FormControl(mMPITestRawValue.order),
      date: new FormControl(mMPITestRawValue.date),
      client: new FormControl(mMPITestRawValue.client),
    });
  }

  getMMPITest(form: MMPITestFormGroup): IMMPITest | NewMMPITest {
    return form.getRawValue() as IMMPITest | NewMMPITest;
  }

  resetForm(form: MMPITestFormGroup, mMPITest: MMPITestFormGroupInput): void {
    const mMPITestRawValue = { ...this.getFormDefaults(), ...mMPITest };
    form.reset(
      {
        ...mMPITestRawValue,
        id: { value: mMPITestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MMPITestFormDefaults {
    return {
      id: null,
    };
  }
}
