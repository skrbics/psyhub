import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMMPITest } from 'app/entities/mmpi-test/mmpi-test.model';
import { MMPITestService } from 'app/entities/mmpi-test/service/mmpi-test.service';
import { IMMPITestCard } from 'app/entities/mmpi-test-card/mmpi-test-card.model';
import { MMPITestCardService } from 'app/entities/mmpi-test-card/service/mmpi-test-card.service';
import { MMPIAnswerService } from '../service/mmpi-answer.service';
import { IMMPIAnswer } from '../mmpi-answer.model';
import { MMPIAnswerFormService, MMPIAnswerFormGroup } from './mmpi-answer-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-answer-update',
  templateUrl: './mmpi-answer-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MMPIAnswerUpdateComponent implements OnInit {
  isSaving = false;
  mMPIAnswer: IMMPIAnswer | null = null;

  mMPITestsSharedCollection: IMMPITest[] = [];
  mMPITestCardsSharedCollection: IMMPITestCard[] = [];

  editForm: MMPIAnswerFormGroup = this.mMPIAnswerFormService.createMMPIAnswerFormGroup();

  constructor(
    protected mMPIAnswerService: MMPIAnswerService,
    protected mMPIAnswerFormService: MMPIAnswerFormService,
    protected mMPITestService: MMPITestService,
    protected mMPITestCardService: MMPITestCardService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMMPITest = (o1: IMMPITest | null, o2: IMMPITest | null): boolean => this.mMPITestService.compareMMPITest(o1, o2);

  compareMMPITestCard = (o1: IMMPITestCard | null, o2: IMMPITestCard | null): boolean =>
    this.mMPITestCardService.compareMMPITestCard(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mMPIAnswer }) => {
      this.mMPIAnswer = mMPIAnswer;
      if (mMPIAnswer) {
        this.updateForm(mMPIAnswer);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mMPIAnswer = this.mMPIAnswerFormService.getMMPIAnswer(this.editForm);
    if (mMPIAnswer.id !== null) {
      this.subscribeToSaveResponse(this.mMPIAnswerService.update(mMPIAnswer));
    } else {
      this.subscribeToSaveResponse(this.mMPIAnswerService.create(mMPIAnswer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMMPIAnswer>>): void {
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

  protected updateForm(mMPIAnswer: IMMPIAnswer): void {
    this.mMPIAnswer = mMPIAnswer;
    this.mMPIAnswerFormService.resetForm(this.editForm, mMPIAnswer);

    this.mMPITestsSharedCollection = this.mMPITestService.addMMPITestToCollectionIfMissing<IMMPITest>(
      this.mMPITestsSharedCollection,
      mMPIAnswer.mMPITest,
    );
    this.mMPITestCardsSharedCollection = this.mMPITestCardService.addMMPITestCardToCollectionIfMissing<IMMPITestCard>(
      this.mMPITestCardsSharedCollection,
      mMPIAnswer.mMPITestCard,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.mMPITestService
      .query()
      .pipe(map((res: HttpResponse<IMMPITest[]>) => res.body ?? []))
      .pipe(
        map((mMPITests: IMMPITest[]) =>
          this.mMPITestService.addMMPITestToCollectionIfMissing<IMMPITest>(mMPITests, this.mMPIAnswer?.mMPITest),
        ),
      )
      .subscribe((mMPITests: IMMPITest[]) => (this.mMPITestsSharedCollection = mMPITests));

    this.mMPITestCardService
      .query()
      .pipe(map((res: HttpResponse<IMMPITestCard[]>) => res.body ?? []))
      .pipe(
        map((mMPITestCards: IMMPITestCard[]) =>
          this.mMPITestCardService.addMMPITestCardToCollectionIfMissing<IMMPITestCard>(mMPITestCards, this.mMPIAnswer?.mMPITestCard),
        ),
      )
      .subscribe((mMPITestCards: IMMPITestCard[]) => (this.mMPITestCardsSharedCollection = mMPITestCards));
  }
}
