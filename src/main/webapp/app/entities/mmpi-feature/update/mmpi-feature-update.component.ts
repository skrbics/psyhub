import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMMPIFeature } from '../mmpi-feature.model';
import { MMPIFeatureService } from '../service/mmpi-feature.service';
import { MMPIFeatureFormService, MMPIFeatureFormGroup } from './mmpi-feature-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-feature-update',
  templateUrl: './mmpi-feature-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MMPIFeatureUpdateComponent implements OnInit {
  isSaving = false;
  mMPIFeature: IMMPIFeature | null = null;

  editForm: MMPIFeatureFormGroup = this.mMPIFeatureFormService.createMMPIFeatureFormGroup();

  constructor(
    protected mMPIFeatureService: MMPIFeatureService,
    protected mMPIFeatureFormService: MMPIFeatureFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mMPIFeature }) => {
      this.mMPIFeature = mMPIFeature;
      if (mMPIFeature) {
        this.updateForm(mMPIFeature);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mMPIFeature = this.mMPIFeatureFormService.getMMPIFeature(this.editForm);
    if (mMPIFeature.id !== null) {
      this.subscribeToSaveResponse(this.mMPIFeatureService.update(mMPIFeature));
    } else {
      this.subscribeToSaveResponse(this.mMPIFeatureService.create(mMPIFeature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMMPIFeature>>): void {
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

  protected updateForm(mMPIFeature: IMMPIFeature): void {
    this.mMPIFeature = mMPIFeature;
    this.mMPIFeatureFormService.resetForm(this.editForm, mMPIFeature);
  }
}
