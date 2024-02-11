import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { IMMPITest } from '../mmpi-test.model';
import { MMPITestService } from '../service/mmpi-test.service';
import { MMPITestFormService, MMPITestFormGroup } from './mmpi-test-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-test-update',
  templateUrl: './mmpi-test-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MMPITestUpdateComponent implements OnInit {
  isSaving = false;
  mMPITest: IMMPITest | null = null;

  clientsSharedCollection: IClient[] = [];

  editForm: MMPITestFormGroup = this.mMPITestFormService.createMMPITestFormGroup();

  constructor(
    protected mMPITestService: MMPITestService,
    protected mMPITestFormService: MMPITestFormService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mMPITest }) => {
      this.mMPITest = mMPITest;
      if (mMPITest) {
        this.updateForm(mMPITest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mMPITest = this.mMPITestFormService.getMMPITest(this.editForm);
    if (mMPITest.id !== null) {
      this.subscribeToSaveResponse(this.mMPITestService.update(mMPITest));
    } else {
      this.subscribeToSaveResponse(this.mMPITestService.create(mMPITest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMMPITest>>): void {
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

  protected updateForm(mMPITest: IMMPITest): void {
    this.mMPITest = mMPITest;
    this.mMPITestFormService.resetForm(this.editForm, mMPITest);

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(
      this.clientsSharedCollection,
      mMPITest.client,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.mMPITest?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }
}
