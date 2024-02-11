import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMMPITestCardFeature, NewMMPITestCardFeature } from '../mmpi-test-card-feature.model';

export type PartialUpdateMMPITestCardFeature = Partial<IMMPITestCardFeature> & Pick<IMMPITestCardFeature, 'id'>;

export type EntityResponseType = HttpResponse<IMMPITestCardFeature>;
export type EntityArrayResponseType = HttpResponse<IMMPITestCardFeature[]>;

@Injectable({ providedIn: 'root' })
export class MMPITestCardFeatureService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmpi-test-card-features');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(mMPITestCardFeature: NewMMPITestCardFeature): Observable<EntityResponseType> {
    return this.http.post<IMMPITestCardFeature>(this.resourceUrl, mMPITestCardFeature, { observe: 'response' });
  }

  update(mMPITestCardFeature: IMMPITestCardFeature): Observable<EntityResponseType> {
    return this.http.put<IMMPITestCardFeature>(
      `${this.resourceUrl}/${this.getMMPITestCardFeatureIdentifier(mMPITestCardFeature)}`,
      mMPITestCardFeature,
      { observe: 'response' },
    );
  }

  partialUpdate(mMPITestCardFeature: PartialUpdateMMPITestCardFeature): Observable<EntityResponseType> {
    return this.http.patch<IMMPITestCardFeature>(
      `${this.resourceUrl}/${this.getMMPITestCardFeatureIdentifier(mMPITestCardFeature)}`,
      mMPITestCardFeature,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMMPITestCardFeature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMMPITestCardFeature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMMPITestCardFeatureIdentifier(mMPITestCardFeature: Pick<IMMPITestCardFeature, 'id'>): number {
    return mMPITestCardFeature.id;
  }

  compareMMPITestCardFeature(o1: Pick<IMMPITestCardFeature, 'id'> | null, o2: Pick<IMMPITestCardFeature, 'id'> | null): boolean {
    return o1 && o2 ? this.getMMPITestCardFeatureIdentifier(o1) === this.getMMPITestCardFeatureIdentifier(o2) : o1 === o2;
  }

  addMMPITestCardFeatureToCollectionIfMissing<Type extends Pick<IMMPITestCardFeature, 'id'>>(
    mMPITestCardFeatureCollection: Type[],
    ...mMPITestCardFeaturesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mMPITestCardFeatures: Type[] = mMPITestCardFeaturesToCheck.filter(isPresent);
    if (mMPITestCardFeatures.length > 0) {
      const mMPITestCardFeatureCollectionIdentifiers = mMPITestCardFeatureCollection.map(
        mMPITestCardFeatureItem => this.getMMPITestCardFeatureIdentifier(mMPITestCardFeatureItem)!,
      );
      const mMPITestCardFeaturesToAdd = mMPITestCardFeatures.filter(mMPITestCardFeatureItem => {
        const mMPITestCardFeatureIdentifier = this.getMMPITestCardFeatureIdentifier(mMPITestCardFeatureItem);
        if (mMPITestCardFeatureCollectionIdentifiers.includes(mMPITestCardFeatureIdentifier)) {
          return false;
        }
        mMPITestCardFeatureCollectionIdentifiers.push(mMPITestCardFeatureIdentifier);
        return true;
      });
      return [...mMPITestCardFeaturesToAdd, ...mMPITestCardFeatureCollection];
    }
    return mMPITestCardFeatureCollection;
  }
}
