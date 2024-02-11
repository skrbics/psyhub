import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICurrency } from 'app/entities/currency/currency.model';
import { CurrencyService } from 'app/entities/currency/service/currency.service';
import { ISessionBill } from '../session-bill.model';
import { SessionBillService } from '../service/session-bill.service';
import { SessionBillFormService, SessionBillFormGroup } from './session-bill-form.service';

@Component({
  standalone: true,
  selector: 'jhi-session-bill-update',
  templateUrl: './session-bill-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SessionBillUpdateComponent implements OnInit {
  isSaving = false;
  sessionBill: ISessionBill | null = null;

  currenciesSharedCollection: ICurrency[] = [];

  editForm: SessionBillFormGroup = this.sessionBillFormService.createSessionBillFormGroup();

  constructor(
    protected sessionBillService: SessionBillService,
    protected sessionBillFormService: SessionBillFormService,
    protected currencyService: CurrencyService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCurrency = (o1: ICurrency | null, o2: ICurrency | null): boolean => this.currencyService.compareCurrency(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sessionBill }) => {
      this.sessionBill = sessionBill;
      if (sessionBill) {
        this.updateForm(sessionBill);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sessionBill = this.sessionBillFormService.getSessionBill(this.editForm);
    if (sessionBill.id !== null) {
      this.subscribeToSaveResponse(this.sessionBillService.update(sessionBill));
    } else {
      this.subscribeToSaveResponse(this.sessionBillService.create(sessionBill));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISessionBill>>): void {
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

  protected updateForm(sessionBill: ISessionBill): void {
    this.sessionBill = sessionBill;
    this.sessionBillFormService.resetForm(this.editForm, sessionBill);

    this.currenciesSharedCollection = this.currencyService.addCurrencyToCollectionIfMissing<ICurrency>(
      this.currenciesSharedCollection,
      sessionBill.currency,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.currencyService
      .query()
      .pipe(map((res: HttpResponse<ICurrency[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrency[]) =>
          this.currencyService.addCurrencyToCollectionIfMissing<ICurrency>(currencies, this.sessionBill?.currency),
        ),
      )
      .subscribe((currencies: ICurrency[]) => (this.currenciesSharedCollection = currencies));
  }
}
