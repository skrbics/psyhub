import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMMPITestCard } from '../mmpi-test-card.model';
import { MMPITestCardService } from '../service/mmpi-test-card.service';

export const mMPITestCardResolve = (route: ActivatedRouteSnapshot): Observable<null | IMMPITestCard> => {
  const id = route.params['id'];
  if (id) {
    return inject(MMPITestCardService)
      .find(id)
      .pipe(
        mergeMap((mMPITestCard: HttpResponse<IMMPITestCard>) => {
          if (mMPITestCard.body) {
            return of(mMPITestCard.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default mMPITestCardResolve;
