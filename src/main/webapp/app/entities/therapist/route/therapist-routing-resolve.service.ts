import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITherapist } from '../therapist.model';
import { TherapistService } from '../service/therapist.service';

export const therapistResolve = (route: ActivatedRouteSnapshot): Observable<null | ITherapist> => {
  const id = route.params['id'];
  if (id) {
    return inject(TherapistService)
      .find(id)
      .pipe(
        mergeMap((therapist: HttpResponse<ITherapist>) => {
          if (therapist.body) {
            return of(therapist.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default therapistResolve;
