import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';
import { MMPITestCardFeatureService } from '../service/mmpi-test-card-feature.service';

export const mMPITestCardFeatureResolve = (route: ActivatedRouteSnapshot): Observable<null | IMMPITestCardFeature> => {
  const id = route.params['id'];
  if (id) {
    return inject(MMPITestCardFeatureService)
      .find(id)
      .pipe(
        mergeMap((mMPITestCardFeature: HttpResponse<IMMPITestCardFeature>) => {
          if (mMPITestCardFeature.body) {
            return of(mMPITestCardFeature.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default mMPITestCardFeatureResolve;
