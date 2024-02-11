import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOffice } from 'app/entities/office/office.model';
import { OfficeService } from 'app/entities/office/service/office.service';
import { ITherapist } from '../therapist.model';
import { TherapistService } from '../service/therapist.service';
import { TherapistFormService, TherapistFormGroup } from './therapist-form.service';

@Component({
  standalone: true,
  selector: 'jhi-therapist-update',
  templateUrl: './therapist-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TherapistUpdateComponent implements OnInit {
  isSaving = false;
  therapist: ITherapist | null = null;

  officesSharedCollection: IOffice[] = [];

  editForm: TherapistFormGroup = this.therapistFormService.createTherapistFormGroup();

  constructor(
    protected therapistService: TherapistService,
    protected therapistFormService: TherapistFormService,
    protected officeService: OfficeService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareOffice = (o1: IOffice | null, o2: IOffice | null): boolean => this.officeService.compareOffice(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapist }) => {
      this.therapist = therapist;
      if (therapist) {
        this.updateForm(therapist);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const therapist = this.therapistFormService.getTherapist(this.editForm);
    if (therapist.id !== null) {
      this.subscribeToSaveResponse(this.therapistService.update(therapist));
    } else {
      this.subscribeToSaveResponse(this.therapistService.create(therapist));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITherapist>>): void {
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

  protected updateForm(therapist: ITherapist): void {
    this.therapist = therapist;
    this.therapistFormService.resetForm(this.editForm, therapist);

    this.officesSharedCollection = this.officeService.addOfficeToCollectionIfMissing<IOffice>(
      this.officesSharedCollection,
      therapist.office,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.officeService
      .query()
      .pipe(map((res: HttpResponse<IOffice[]>) => res.body ?? []))
      .pipe(map((offices: IOffice[]) => this.officeService.addOfficeToCollectionIfMissing<IOffice>(offices, this.therapist?.office)))
      .subscribe((offices: IOffice[]) => (this.officesSharedCollection = offices));
  }
}
