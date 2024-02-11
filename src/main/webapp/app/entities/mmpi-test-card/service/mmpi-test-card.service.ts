import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMMPITestCard, NewMMPITestCard } from '../mmpi-test-card.model';

export type PartialUpdateMMPITestCard = Partial<IMMPITestCard> & Pick<IMMPITestCard, 'id'>;

export type EntityResponseType = HttpResponse<IMMPITestCard>;
export type EntityArrayResponseType = HttpResponse<IMMPITestCard[]>;

@Injectable({ providedIn: 'root' })
export class MMPITestCardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmpi-test-cards');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(mMPITestCard: NewMMPITestCard): Observable<EntityResponseType> {
    return this.http.post<IMMPITestCard>(this.resourceUrl, mMPITestCard, { observe: 'response' });
  }

  update(mMPITestCard: IMMPITestCard): Observable<EntityResponseType> {
    return this.http.put<IMMPITestCard>(`${this.resourceUrl}/${this.getMMPITestCardIdentifier(mMPITestCard)}`, mMPITestCard, {
      observe: 'response',
    });
  }

  partialUpdate(mMPITestCard: PartialUpdateMMPITestCard): Observable<EntityResponseType> {
    return this.http.patch<IMMPITestCard>(`${this.resourceUrl}/${this.getMMPITestCardIdentifier(mMPITestCard)}`, mMPITestCard, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMMPITestCard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMMPITestCard[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMMPITestCardIdentifier(mMPITestCard: Pick<IMMPITestCard, 'id'>): number {
    return mMPITestCard.id;
  }

  compareMMPITestCard(o1: Pick<IMMPITestCard, 'id'> | null, o2: Pick<IMMPITestCard, 'id'> | null): boolean {
    return o1 && o2 ? this.getMMPITestCardIdentifier(o1) === this.getMMPITestCardIdentifier(o2) : o1 === o2;
  }

  addMMPITestCardToCollectionIfMissing<Type extends Pick<IMMPITestCard, 'id'>>(
    mMPITestCardCollection: Type[],
    ...mMPITestCardsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mMPITestCards: Type[] = mMPITestCardsToCheck.filter(isPresent);
    if (mMPITestCards.length > 0) {
      const mMPITestCardCollectionIdentifiers = mMPITestCardCollection.map(
        mMPITestCardItem => this.getMMPITestCardIdentifier(mMPITestCardItem)!,
      );
      const mMPITestCardsToAdd = mMPITestCards.filter(mMPITestCardItem => {
        const mMPITestCardIdentifier = this.getMMPITestCardIdentifier(mMPITestCardItem);
        if (mMPITestCardCollectionIdentifiers.includes(mMPITestCardIdentifier)) {
          return false;
        }
        mMPITestCardCollectionIdentifiers.push(mMPITestCardIdentifier);
        return true;
      });
      return [...mMPITestCardsToAdd, ...mMPITestCardCollection];
    }
    return mMPITestCardCollection;
  }
}
