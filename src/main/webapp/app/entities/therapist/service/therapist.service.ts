import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITherapist, NewTherapist } from '../therapist.model';

export type PartialUpdateTherapist = Partial<ITherapist> & Pick<ITherapist, 'id'>;

export type EntityResponseType = HttpResponse<ITherapist>;
export type EntityArrayResponseType = HttpResponse<ITherapist[]>;

@Injectable({ providedIn: 'root' })
export class TherapistService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/therapists');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(therapist: NewTherapist): Observable<EntityResponseType> {
    return this.http.post<ITherapist>(this.resourceUrl, therapist, { observe: 'response' });
  }

  update(therapist: ITherapist): Observable<EntityResponseType> {
    return this.http.put<ITherapist>(`${this.resourceUrl}/${this.getTherapistIdentifier(therapist)}`, therapist, { observe: 'response' });
  }

  partialUpdate(therapist: PartialUpdateTherapist): Observable<EntityResponseType> {
    return this.http.patch<ITherapist>(`${this.resourceUrl}/${this.getTherapistIdentifier(therapist)}`, therapist, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITherapist>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITherapist[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTherapistIdentifier(therapist: Pick<ITherapist, 'id'>): number {
    return therapist.id;
  }

  compareTherapist(o1: Pick<ITherapist, 'id'> | null, o2: Pick<ITherapist, 'id'> | null): boolean {
    return o1 && o2 ? this.getTherapistIdentifier(o1) === this.getTherapistIdentifier(o2) : o1 === o2;
  }

  addTherapistToCollectionIfMissing<Type extends Pick<ITherapist, 'id'>>(
    therapistCollection: Type[],
    ...therapistsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const therapists: Type[] = therapistsToCheck.filter(isPresent);
    if (therapists.length > 0) {
      const therapistCollectionIdentifiers = therapistCollection.map(therapistItem => this.getTherapistIdentifier(therapistItem)!);
      const therapistsToAdd = therapists.filter(therapistItem => {
        const therapistIdentifier = this.getTherapistIdentifier(therapistItem);
        if (therapistCollectionIdentifiers.includes(therapistIdentifier)) {
          return false;
        }
        therapistCollectionIdentifiers.push(therapistIdentifier);
        return true;
      });
      return [...therapistsToAdd, ...therapistCollection];
    }
    return therapistCollection;
  }
}
