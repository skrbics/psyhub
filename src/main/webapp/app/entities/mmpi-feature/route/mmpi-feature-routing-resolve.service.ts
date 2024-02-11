import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMMPIFeature } from '../mmpi-feature.model';
import { MMPIFeatureService } from '../service/mmpi-feature.service';

export const mMPIFeatureResolve = (route: ActivatedRouteSnapshot): Observable<null | IMMPIFeature> => {
  const id = route.params['id'];
  if (id) {
    return inject(MMPIFeatureService)
      .find(id)
      .pipe(
        mergeMap((mMPIFeature: HttpResponse<IMMPIFeature>) => {
          if (mMPIFeature.body) {
            return of(mMPIFeature.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default mMPIFeatureResolve;
