import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMMPITest, NewMMPITest } from '../mmpi-test.model';

export type PartialUpdateMMPITest = Partial<IMMPITest> & Pick<IMMPITest, 'id'>;

type RestOf<T extends IMMPITest | NewMMPITest> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestMMPITest = RestOf<IMMPITest>;

export type NewRestMMPITest = RestOf<NewMMPITest>;

export type PartialUpdateRestMMPITest = RestOf<PartialUpdateMMPITest>;

export type EntityResponseType = HttpResponse<IMMPITest>;
export type EntityArrayResponseType = HttpResponse<IMMPITest[]>;

@Injectable({ providedIn: 'root' })
export class MMPITestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmpi-tests');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(mMPITest: NewMMPITest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mMPITest);
    return this.http
      .post<RestMMPITest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(mMPITest: IMMPITest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mMPITest);
    return this.http
      .put<RestMMPITest>(`${this.resourceUrl}/${this.getMMPITestIdentifier(mMPITest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(mMPITest: PartialUpdateMMPITest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mMPITest);
    return this.http
      .patch<RestMMPITest>(`${this.resourceUrl}/${this.getMMPITestIdentifier(mMPITest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMMPITest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMMPITest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMMPITestIdentifier(mMPITest: Pick<IMMPITest, 'id'>): number {
    return mMPITest.id;
  }

  compareMMPITest(o1: Pick<IMMPITest, 'id'> | null, o2: Pick<IMMPITest, 'id'> | null): boolean {
    return o1 && o2 ? this.getMMPITestIdentifier(o1) === this.getMMPITestIdentifier(o2) : o1 === o2;
  }

  addMMPITestToCollectionIfMissing<Type extends Pick<IMMPITest, 'id'>>(
    mMPITestCollection: Type[],
    ...mMPITestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mMPITests: Type[] = mMPITestsToCheck.filter(isPresent);
    if (mMPITests.length > 0) {
      const mMPITestCollectionIdentifiers = mMPITestCollection.map(mMPITestItem => this.getMMPITestIdentifier(mMPITestItem)!);
      const mMPITestsToAdd = mMPITests.filter(mMPITestItem => {
        const mMPITestIdentifier = this.getMMPITestIdentifier(mMPITestItem);
        if (mMPITestCollectionIdentifiers.includes(mMPITestIdentifier)) {
          return false;
        }
        mMPITestCollectionIdentifiers.push(mMPITestIdentifier);
        return true;
      });
      return [...mMPITestsToAdd, ...mMPITestCollection];
    }
    return mMPITestCollection;
  }

  protected convertDateFromClient<T extends IMMPITest | NewMMPITest | PartialUpdateMMPITest>(mMPITest: T): RestOf<T> {
    return {
      ...mMPITest,
      date: mMPITest.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMMPITest: RestMMPITest): IMMPITest {
    return {
      ...restMMPITest,
      date: restMMPITest.date ? dayjs(restMMPITest.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMMPITest>): HttpResponse<IMMPITest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMMPITest[]>): HttpResponse<IMMPITest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
