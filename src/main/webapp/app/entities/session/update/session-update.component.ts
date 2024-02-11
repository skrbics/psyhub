import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISessionBill } from 'app/entities/session-bill/session-bill.model';
import { SessionBillService } from 'app/entities/session-bill/service/session-bill.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { SessionService } from '../service/session.service';
import { ISession } from '../session.model';
import { SessionFormService, SessionFormGroup } from './session-form.service';

@Component({
  standalone: true,
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;
  session: ISession | null = null;

  sessionBillsCollection: ISessionBill[] = [];
  clientsSharedCollection: IClient[] = [];

  editForm: SessionFormGroup = this.sessionFormService.createSessionFormGroup();

  constructor(
    protected sessionService: SessionService,
    protected sessionFormService: SessionFormService,
    protected sessionBillService: SessionBillService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareSessionBill = (o1: ISessionBill | null, o2: ISessionBill | null): boolean => this.sessionBillService.compareSessionBill(o1, o2);

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.session = session;
      if (session) {
        this.updateForm(session);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.sessionFormService.getSession(this.editForm);
    if (session.id !== null) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(session: ISession): void {
    this.session = session;
    this.sessionFormService.resetForm(this.editForm, session);

    this.sessionBillsCollection = this.sessionBillService.addSessionBillToCollectionIfMissing<ISessionBill>(
      this.sessionBillsCollection,
      session.sessionBill,
    );
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(this.clientsSharedCollection, session.client);
  }

  protected loadRelationshipsOptions(): void {
    this.sessionBillService
      .query({ filter: 'session-is-null' })
      .pipe(map((res: HttpResponse<ISessionBill[]>) => res.body ?? []))
      .pipe(
        map((sessionBills: ISessionBill[]) =>
          this.sessionBillService.addSessionBillToCollectionIfMissing<ISessionBill>(sessionBills, this.session?.sessionBill),
        ),
      )
      .subscribe((sessionBills: ISessionBill[]) => (this.sessionBillsCollection = sessionBills));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.session?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }
}
