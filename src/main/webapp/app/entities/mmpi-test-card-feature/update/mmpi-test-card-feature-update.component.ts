import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMMPITestCard } from 'app/entities/mmpi-test-card/mmpi-test-card.model';
import { MMPITestCardService } from 'app/entities/mmpi-test-card/service/mmpi-test-card.service';
import { IMMPIFeature } from 'app/entities/mmpi-feature/mmpi-feature.model';
import { MMPIFeatureService } from 'app/entities/mmpi-feature/service/mmpi-feature.service';
import { MMPITestCardFeatureService } from '../service/mmpi-test-card-feature.service';
import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';
import { MMPITestCardFeatureFormService, MMPITestCardFeatureFormGroup } from './mmpi-test-card-feature-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-test-card-feature-update',
  templateUrl: './mmpi-test-card-feature-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MMPITestCardFeatureUpdateComponent implements OnInit {
  isSaving = false;
  mMPITestCardFeature: IMMPITestCardFeature | null = null;

  mMPITestCardsSharedCollection: IMMPITestCard[] = [];
  mMPIFeaturesSharedCollection: IMMPIFeature[] = [];

  editForm: MMPITestCardFeatureFormGroup = this.mMPITestCardFeatureFormService.createMMPITestCardFeatureFormGroup();

  constructor(
    protected mMPITestCardFeatureService: MMPITestCardFeatureService,
    protected mMPITestCardFeatureFormService: MMPITestCardFeatureFormService,
    protected mMPITestCardService: MMPITestCardService,
    protected mMPIFeatureService: MMPIFeatureService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMMPITestCard = (o1: IMMPITestCard | null, o2: IMMPITestCard | null): boolean =>
    this.mMPITestCardService.compareMMPITestCard(o1, o2);

  compareMMPIFeature = (o1: IMMPIFeature | null, o2: IMMPIFeature | null): boolean => this.mMPIFeatureService.compareMMPIFeature(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mMPITestCardFeature }) => {
      this.mMPITestCardFeature = mMPITestCardFeature;
      if (mMPITestCardFeature) {
        this.updateForm(mMPITestCardFeature);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mMPITestCardFeature = this.mMPITestCardFeatureFormService.getMMPITestCardFeature(this.editForm);
    if (mMPITestCardFeature.id !== null) {
      this.subscribeToSaveResponse(this.mMPITestCardFeatureService.update(mMPITestCardFeature));
    } else {
      this.subscribeToSaveResponse(this.mMPITestCardFeatureService.create(mMPITestCardFeature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMMPITestCardFeature>>): void {
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

  protected updateForm(mMPITestCardFeature: IMMPITestCardFeature): void {
    this.mMPITestCardFeature = mMPITestCardFeature;
    this.mMPITestCardFeatureFormService.resetForm(this.editForm, mMPITestCardFeature);

    this.mMPITestCardsSharedCollection = this.mMPITestCardService.addMMPITestCardToCollectionIfMissing<IMMPITestCard>(
      this.mMPITestCardsSharedCollection,
      mMPITestCardFeature.mMPITestCard,
    );
    this.mMPIFeaturesSharedCollection = this.mMPIFeatureService.addMMPIFeatureToCollectionIfMissing<IMMPIFeature>(
      this.mMPIFeaturesSharedCollection,
      mMPITestCardFeature.mMPIFeature,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.mMPITestCardService
      .query()
      .pipe(map((res: HttpResponse<IMMPITestCard[]>) => res.body ?? []))
      .pipe(
        map((mMPITestCards: IMMPITestCard[]) =>
          this.mMPITestCardService.addMMPITestCardToCollectionIfMissing<IMMPITestCard>(
            mMPITestCards,
            this.mMPITestCardFeature?.mMPITestCard,
          ),
        ),
      )
      .subscribe((mMPITestCards: IMMPITestCard[]) => (this.mMPITestCardsSharedCollection = mMPITestCards));

    this.mMPIFeatureService
      .query()
      .pipe(map((res: HttpResponse<IMMPIFeature[]>) => res.body ?? []))
      .pipe(
        map((mMPIFeatures: IMMPIFeature[]) =>
          this.mMPIFeatureService.addMMPIFeatureToCollectionIfMissing<IMMPIFeature>(mMPIFeatures, this.mMPITestCardFeature?.mMPIFeature),
        ),
      )
      .subscribe((mMPIFeatures: IMMPIFeature[]) => (this.mMPIFeaturesSharedCollection = mMPIFeatures));
  }
}
