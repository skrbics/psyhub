import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISessionBill, NewSessionBill } from '../session-bill.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISessionBill for edit and NewSessionBillFormGroupInput for create.
 */
type SessionBillFormGroupInput = ISessionBill | PartialWithRequiredKeyOf<NewSessionBill>;

type SessionBillFormDefaults = Pick<NewSessionBill, 'id' | 'paid'>;

type SessionBillFormGroupContent = {
  id: FormControl<ISessionBill['id'] | NewSessionBill['id']>;
  amount: FormControl<ISessionBill['amount']>;
  paid: FormControl<ISessionBill['paid']>;
  currency: FormControl<ISessionBill['currency']>;
};

export type SessionBillFormGroup = FormGroup<SessionBillFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SessionBillFormService {
  createSessionBillFormGroup(sessionBill: SessionBillFormGroupInput = { id: null }): SessionBillFormGroup {
    const sessionBillRawValue = {
      ...this.getFormDefaults(),
      ...sessionBill,
    };
    return new FormGroup<SessionBillFormGroupContent>({
      id: new FormControl(
        { value: sessionBillRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      amount: new FormControl(sessionBillRawValue.amount),
      paid: new FormControl(sessionBillRawValue.paid),
      currency: new FormControl(sessionBillRawValue.currency),
    });
  }

  getSessionBill(form: SessionBillFormGroup): ISessionBill | NewSessionBill {
    return form.getRawValue() as ISessionBill | NewSessionBill;
  }

  resetForm(form: SessionBillFormGroup, sessionBill: SessionBillFormGroupInput): void {
    const sessionBillRawValue = { ...this.getFormDefaults(), ...sessionBill };
    form.reset(
      {
        ...sessionBillRawValue,
        id: { value: sessionBillRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SessionBillFormDefaults {
    return {
      id: null,
      paid: false,
    };
  }
}
