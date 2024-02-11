import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { AddressService } from '../service/address.service';
import { IAddress } from '../address.model';
import { AddressFormService, AddressFormGroup } from './address-form.service';

@Component({
  standalone: true,
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  address: IAddress | null = null;

  citiesSharedCollection: ICity[] = [];
  countriesSharedCollection: ICountry[] = [];

  editForm: AddressFormGroup = this.addressFormService.createAddressFormGroup();

  constructor(
    protected addressService: AddressService,
    protected addressFormService: AddressFormService,
    protected cityService: CityService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCity = (o1: ICity | null, o2: ICity | null): boolean => this.cityService.compareCity(o1, o2);

  compareCountry = (o1: ICountry | null, o2: ICountry | null): boolean => this.countryService.compareCountry(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.address = address;
      if (address) {
        this.updateForm(address);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.addressFormService.getAddress(this.editForm);
    if (address.id !== null) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  protected updateForm(address: IAddress): void {
    this.address = address;
    this.addressFormService.resetForm(this.editForm, address);

    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing<ICity>(this.citiesSharedCollection, address.city);
    this.countriesSharedCollection = this.countryService.addCountryToCollectionIfMissing<ICountry>(
      this.countriesSharedCollection,
      address.country,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(map((cities: ICity[]) => this.cityService.addCityToCollectionIfMissing<ICity>(cities, this.address?.city)))
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));

    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(map((countries: ICountry[]) => this.countryService.addCountryToCollectionIfMissing<ICountry>(countries, this.address?.country)))
      .subscribe((countries: ICountry[]) => (this.countriesSharedCollection = countries));
  }
}
