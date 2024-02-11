import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMMPIFeature, NewMMPIFeature } from '../mmpi-feature.model';

export type PartialUpdateMMPIFeature = Partial<IMMPIFeature> & Pick<IMMPIFeature, 'id'>;

export type EntityResponseType = HttpResponse<IMMPIFeature>;
export type EntityArrayResponseType = HttpResponse<IMMPIFeature[]>;

@Injectable({ providedIn: 'root' })
export class MMPIFeatureService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmpi-features');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(mMPIFeature: NewMMPIFeature): Observable<EntityResponseType> {
    return this.http.post<IMMPIFeature>(this.resourceUrl, mMPIFeature, { observe: 'response' });
  }

  update(mMPIFeature: IMMPIFeature): Observable<EntityResponseType> {
    return this.http.put<IMMPIFeature>(`${this.resourceUrl}/${this.getMMPIFeatureIdentifier(mMPIFeature)}`, mMPIFeature, {
      observe: 'response',
    });
  }

  partialUpdate(mMPIFeature: PartialUpdateMMPIFeature): Observable<EntityResponseType> {
    return this.http.patch<IMMPIFeature>(`${this.resourceUrl}/${this.getMMPIFeatureIdentifier(mMPIFeature)}`, mMPIFeature, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMMPIFeature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMMPIFeature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMMPIFeatureIdentifier(mMPIFeature: Pick<IMMPIFeature, 'id'>): number {
    return mMPIFeature.id;
  }

  compareMMPIFeature(o1: Pick<IMMPIFeature, 'id'> | null, o2: Pick<IMMPIFeature, 'id'> | null): boolean {
    return o1 && o2 ? this.getMMPIFeatureIdentifier(o1) === this.getMMPIFeatureIdentifier(o2) : o1 === o2;
  }

  addMMPIFeatureToCollectionIfMissing<Type extends Pick<IMMPIFeature, 'id'>>(
    mMPIFeatureCollection: Type[],
    ...mMPIFeaturesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mMPIFeatures: Type[] = mMPIFeaturesToCheck.filter(isPresent);
    if (mMPIFeatures.length > 0) {
      const mMPIFeatureCollectionIdentifiers = mMPIFeatureCollection.map(
        mMPIFeatureItem => this.getMMPIFeatureIdentifier(mMPIFeatureItem)!,
      );
      const mMPIFeaturesToAdd = mMPIFeatures.filter(mMPIFeatureItem => {
        const mMPIFeatureIdentifier = this.getMMPIFeatureIdentifier(mMPIFeatureItem);
        if (mMPIFeatureCollectionIdentifiers.includes(mMPIFeatureIdentifier)) {
          return false;
        }
        mMPIFeatureCollectionIdentifiers.push(mMPIFeatureIdentifier);
        return true;
      });
      return [...mMPIFeaturesToAdd, ...mMPIFeatureCollection];
    }
    return mMPIFeatureCollection;
  }
}
