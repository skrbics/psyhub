import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IOffice } from '../office.model';
import { OfficeService } from '../service/office.service';
import { OfficeFormService, OfficeFormGroup } from './office-form.service';

@Component({
  standalone: true,
  selector: 'jhi-office-update',
  templateUrl: './office-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OfficeUpdateComponent implements OnInit {
  isSaving = false;
  office: IOffice | null = null;

  addressesCollection: IAddress[] = [];

  editForm: OfficeFormGroup = this.officeFormService.createOfficeFormGroup();

  constructor(
    protected officeService: OfficeService,
    protected officeFormService: OfficeFormService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareAddress = (o1: IAddress | null, o2: IAddress | null): boolean => this.addressService.compareAddress(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ office }) => {
      this.office = office;
      if (office) {
        this.updateForm(office);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const office = this.officeFormService.getOffice(this.editForm);
    if (office.id !== null) {
      this.subscribeToSaveResponse(this.officeService.update(office));
    } else {
      this.subscribeToSaveResponse(this.officeService.create(office));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffice>>): void {
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

  protected updateForm(office: IOffice): void {
    this.office = office;
    this.officeFormService.resetForm(this.editForm, office);

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing<IAddress>(this.addressesCollection, office.address);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'office-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing<IAddress>(addresses, this.office?.address)))
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));
  }
}
