import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MMPIAnswerComponent } from './list/mmpi-answer.component';
import { MMPIAnswerDetailComponent } from './detail/mmpi-answer-detail.component';
import { MMPIAnswerUpdateComponent } from './update/mmpi-answer-update.component';
import MMPIAnswerResolve from './route/mmpi-answer-routing-resolve.service';

const mMPIAnswerRoute: Routes = [
  {
    path: '',
    component: MMPIAnswerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MMPIAnswerDetailComponent,
    resolve: {
      mMPIAnswer: MMPIAnswerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MMPIAnswerUpdateComponent,
    resolve: {
      mMPIAnswer: MMPIAnswerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MMPIAnswerUpdateComponent,
    resolve: {
      mMPIAnswer: MMPIAnswerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mMPIAnswerRoute;
