import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMMPIAnswer, NewMMPIAnswer } from '../mmpi-answer.model';

export type PartialUpdateMMPIAnswer = Partial<IMMPIAnswer> & Pick<IMMPIAnswer, 'id'>;

export type EntityResponseType = HttpResponse<IMMPIAnswer>;
export type EntityArrayResponseType = HttpResponse<IMMPIAnswer[]>;

@Injectable({ providedIn: 'root' })
export class MMPIAnswerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmpi-answers');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(mMPIAnswer: NewMMPIAnswer): Observable<EntityResponseType> {
    return this.http.post<IMMPIAnswer>(this.resourceUrl, mMPIAnswer, { observe: 'response' });
  }

  update(mMPIAnswer: IMMPIAnswer): Observable<EntityResponseType> {
    return this.http.put<IMMPIAnswer>(`${this.resourceUrl}/${this.getMMPIAnswerIdentifier(mMPIAnswer)}`, mMPIAnswer, {
      observe: 'response',
    });
  }

  partialUpdate(mMPIAnswer: PartialUpdateMMPIAnswer): Observable<EntityResponseType> {
    return this.http.patch<IMMPIAnswer>(`${this.resourceUrl}/${this.getMMPIAnswerIdentifier(mMPIAnswer)}`, mMPIAnswer, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMMPIAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMMPIAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMMPIAnswerIdentifier(mMPIAnswer: Pick<IMMPIAnswer, 'id'>): number {
    return mMPIAnswer.id;
  }

  compareMMPIAnswer(o1: Pick<IMMPIAnswer, 'id'> | null, o2: Pick<IMMPIAnswer, 'id'> | null): boolean {
    return o1 && o2 ? this.getMMPIAnswerIdentifier(o1) === this.getMMPIAnswerIdentifier(o2) : o1 === o2;
  }

  addMMPIAnswerToCollectionIfMissing<Type extends Pick<IMMPIAnswer, 'id'>>(
    mMPIAnswerCollection: Type[],
    ...mMPIAnswersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mMPIAnswers: Type[] = mMPIAnswersToCheck.filter(isPresent);
    if (mMPIAnswers.length > 0) {
      const mMPIAnswerCollectionIdentifiers = mMPIAnswerCollection.map(mMPIAnswerItem => this.getMMPIAnswerIdentifier(mMPIAnswerItem)!);
      const mMPIAnswersToAdd = mMPIAnswers.filter(mMPIAnswerItem => {
        const mMPIAnswerIdentifier = this.getMMPIAnswerIdentifier(mMPIAnswerItem);
        if (mMPIAnswerCollectionIdentifiers.includes(mMPIAnswerIdentifier)) {
          return false;
        }
        mMPIAnswerCollectionIdentifiers.push(mMPIAnswerIdentifier);
        return true;
      });
      return [...mMPIAnswersToAdd, ...mMPIAnswerCollection];
    }
    return mMPIAnswerCollection;
  }
}
