import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISessionBill } from '../session-bill.model';
import { SessionBillService } from '../service/session-bill.service';

export const sessionBillResolve = (route: ActivatedRouteSnapshot): Observable<null | ISessionBill> => {
  const id = route.params['id'];
  if (id) {
    return inject(SessionBillService)
      .find(id)
      .pipe(
        mergeMap((sessionBill: HttpResponse<ISessionBill>) => {
          if (sessionBill.body) {
            return of(sessionBill.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sessionBillResolve;
