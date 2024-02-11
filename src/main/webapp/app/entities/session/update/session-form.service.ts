import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISession, NewSession } from '../session.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISession for edit and NewSessionFormGroupInput for create.
 */
type SessionFormGroupInput = ISession | PartialWithRequiredKeyOf<NewSession>;

type SessionFormDefaults = Pick<NewSession, 'id' | 'completed'>;

type SessionFormGroupContent = {
  id: FormControl<ISession['id'] | NewSession['id']>;
  date: FormControl<ISession['date']>;
  location: FormControl<ISession['location']>;
  notes: FormControl<ISession['notes']>;
  completed: FormControl<ISession['completed']>;
  sessionBill: FormControl<ISession['sessionBill']>;
  client: FormControl<ISession['client']>;
};

export type SessionFormGroup = FormGroup<SessionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SessionFormService {
  createSessionFormGroup(session: SessionFormGroupInput = { id: null }): SessionFormGroup {
    const sessionRawValue = {
      ...this.getFormDefaults(),
      ...session,
    };
    return new FormGroup<SessionFormGroupContent>({
      id: new FormControl(
        { value: sessionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      date: new FormControl(sessionRawValue.date),
      location: new FormControl(sessionRawValue.location),
      notes: new FormControl(sessionRawValue.notes),
      completed: new FormControl(sessionRawValue.completed),
      sessionBill: new FormControl(sessionRawValue.sessionBill),
      client: new FormControl(sessionRawValue.client),
    });
  }

  getSession(form: SessionFormGroup): ISession | NewSession {
    return form.getRawValue() as ISession | NewSession;
  }

  resetForm(form: SessionFormGroup, session: SessionFormGroupInput): void {
    const sessionRawValue = { ...this.getFormDefaults(), ...session };
    form.reset(
      {
        ...sessionRawValue,
        id: { value: sessionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SessionFormDefaults {
    return {
      id: null,
      completed: false,
    };
  }
}
