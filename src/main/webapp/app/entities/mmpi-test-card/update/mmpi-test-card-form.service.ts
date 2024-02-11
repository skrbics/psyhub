import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMMPITestCard, NewMMPITestCard } from '../mmpi-test-card.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMMPITestCard for edit and NewMMPITestCardFormGroupInput for create.
 */
type MMPITestCardFormGroupInput = IMMPITestCard | PartialWithRequiredKeyOf<NewMMPITestCard>;

type MMPITestCardFormDefaults = Pick<NewMMPITestCard, 'id'>;

type MMPITestCardFormGroupContent = {
  id: FormControl<IMMPITestCard['id'] | NewMMPITestCard['id']>;
  question: FormControl<IMMPITestCard['question']>;
};

export type MMPITestCardFormGroup = FormGroup<MMPITestCardFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MMPITestCardFormService {
  createMMPITestCardFormGroup(mMPITestCard: MMPITestCardFormGroupInput = { id: null }): MMPITestCardFormGroup {
    const mMPITestCardRawValue = {
      ...this.getFormDefaults(),
      ...mMPITestCard,
    };
    return new FormGroup<MMPITestCardFormGroupContent>({
      id: new FormControl(
        { value: mMPITestCardRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      question: new FormControl(mMPITestCardRawValue.question),
    });
  }

  getMMPITestCard(form: MMPITestCardFormGroup): IMMPITestCard | NewMMPITestCard {
    return form.getRawValue() as IMMPITestCard | NewMMPITestCard;
  }

  resetForm(form: MMPITestCardFormGroup, mMPITestCard: MMPITestCardFormGroupInput): void {
    const mMPITestCardRawValue = { ...this.getFormDefaults(), ...mMPITestCard };
    form.reset(
      {
        ...mMPITestCardRawValue,
        id: { value: mMPITestCardRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MMPITestCardFormDefaults {
    return {
      id: null,
    };
  }
}
