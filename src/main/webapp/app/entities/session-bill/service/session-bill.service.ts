import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISessionBill, NewSessionBill } from '../session-bill.model';

export type PartialUpdateSessionBill = Partial<ISessionBill> & Pick<ISessionBill, 'id'>;

export type EntityResponseType = HttpResponse<ISessionBill>;
export type EntityArrayResponseType = HttpResponse<ISessionBill[]>;

@Injectable({ providedIn: 'root' })
export class SessionBillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/session-bills');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(sessionBill: NewSessionBill): Observable<EntityResponseType> {
    return this.http.post<ISessionBill>(this.resourceUrl, sessionBill, { observe: 'response' });
  }

  update(sessionBill: ISessionBill): Observable<EntityResponseType> {
    return this.http.put<ISessionBill>(`${this.resourceUrl}/${this.getSessionBillIdentifier(sessionBill)}`, sessionBill, {
      observe: 'response',
    });
  }

  partialUpdate(sessionBill: PartialUpdateSessionBill): Observable<EntityResponseType> {
    return this.http.patch<ISessionBill>(`${this.resourceUrl}/${this.getSessionBillIdentifier(sessionBill)}`, sessionBill, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISessionBill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISessionBill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSessionBillIdentifier(sessionBill: Pick<ISessionBill, 'id'>): number {
    return sessionBill.id;
  }

  compareSessionBill(o1: Pick<ISessionBill, 'id'> | null, o2: Pick<ISessionBill, 'id'> | null): boolean {
    return o1 && o2 ? this.getSessionBillIdentifier(o1) === this.getSessionBillIdentifier(o2) : o1 === o2;
  }

  addSessionBillToCollectionIfMissing<Type extends Pick<ISessionBill, 'id'>>(
    sessionBillCollection: Type[],
    ...sessionBillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sessionBills: Type[] = sessionBillsToCheck.filter(isPresent);
    if (sessionBills.length > 0) {
      const sessionBillCollectionIdentifiers = sessionBillCollection.map(
        sessionBillItem => this.getSessionBillIdentifier(sessionBillItem)!,
      );
      const sessionBillsToAdd = sessionBills.filter(sessionBillItem => {
        const sessionBillIdentifier = this.getSessionBillIdentifier(sessionBillItem);
        if (sessionBillCollectionIdentifiers.includes(sessionBillIdentifier)) {
          return false;
        }
        sessionBillCollectionIdentifiers.push(sessionBillIdentifier);
        return true;
      });
      return [...sessionBillsToAdd, ...sessionBillCollection];
    }
    return sessionBillCollection;
  }
}
