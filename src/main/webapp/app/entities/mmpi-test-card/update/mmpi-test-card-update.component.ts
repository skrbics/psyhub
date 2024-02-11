import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMMPITestCard } from '../mmpi-test-card.model';
import { MMPITestCardService } from '../service/mmpi-test-card.service';
import { MMPITestCardFormService, MMPITestCardFormGroup } from './mmpi-test-card-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-test-card-update',
  templateUrl: './mmpi-test-card-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MMPITestCardUpdateComponent implements OnInit {
  isSaving = false;
  mMPITestCard: IMMPITestCard | null = null;

  editForm: MMPITestCardFormGroup = this.mMPITestCardFormService.createMMPITestCardFormGroup();

  constructor(
    protected mMPITestCardService: MMPITestCardService,
    protected mMPITestCardFormService: MMPITestCardFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mMPITestCard }) => {
      this.mMPITestCard = mMPITestCard;
      if (mMPITestCard) {
        this.updateForm(mMPITestCard);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mMPITestCard = this.mMPITestCardFormService.getMMPITestCard(this.editForm);
    if (mMPITestCard.id !== null) {
      this.subscribeToSaveResponse(this.mMPITestCardService.update(mMPITestCard));
    } else {
      this.subscribeToSaveResponse(this.mMPITestCardService.create(mMPITestCard));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMMPITestCard>>): void {
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

  protected updateForm(mMPITestCard: IMMPITestCard): void {
    this.mMPITestCard = mMPITestCard;
    this.mMPITestCardFormService.resetForm(this.editForm, mMPITestCard);
  }
}
