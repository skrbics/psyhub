import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MMPITestCardComponent } from './list/mmpi-test-card.component';
import { MMPITestCardDetailComponent } from './detail/mmpi-test-card-detail.component';
import { MMPITestCardUpdateComponent } from './update/mmpi-test-card-update.component';
import MMPITestCardResolve from './route/mmpi-test-card-routing-resolve.service';

const mMPITestCardRoute: Routes = [
  {
    path: '',
    component: MMPITestCardComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MMPITestCardDetailComponent,
    resolve: {
      mMPITestCard: MMPITestCardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MMPITestCardUpdateComponent,
    resolve: {
      mMPITestCard: MMPITestCardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MMPITestCardUpdateComponent,
    resolve: {
      mMPITestCard: MMPITestCardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mMPITestCardRoute;
