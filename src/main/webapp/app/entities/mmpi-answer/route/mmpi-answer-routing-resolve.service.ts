import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMMPIAnswer } from '../mmpi-answer.model';
import { MMPIAnswerService } from '../service/mmpi-answer.service';

export const mMPIAnswerResolve = (route: ActivatedRouteSnapshot): Observable<null | IMMPIAnswer> => {
  const id = route.params['id'];
  if (id) {
    return inject(MMPIAnswerService)
      .find(id)
      .pipe(
        mergeMap((mMPIAnswer: HttpResponse<IMMPIAnswer>) => {
          if (mMPIAnswer.body) {
            return of(mMPIAnswer.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default mMPIAnswerResolve;
