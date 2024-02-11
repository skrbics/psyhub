import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMMPITest } from '../mmpi-test.model';
import { MMPITestService } from '../service/mmpi-test.service';

export const mMPITestResolve = (route: ActivatedRouteSnapshot): Observable<null | IMMPITest> => {
  const id = route.params['id'];
  if (id) {
    return inject(MMPITestService)
      .find(id)
      .pipe(
        mergeMap((mMPITest: HttpResponse<IMMPITest>) => {
          if (mMPITest.body) {
            return of(mMPITest.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default mMPITestResolve;
