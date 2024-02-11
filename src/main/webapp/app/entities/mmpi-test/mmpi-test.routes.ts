import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MMPITestComponent } from './list/mmpi-test.component';
import { MMPITestDetailComponent } from './detail/mmpi-test-detail.component';
import { MMPITestUpdateComponent } from './update/mmpi-test-update.component';
import MMPITestResolve from './route/mmpi-test-routing-resolve.service';

const mMPITestRoute: Routes = [
  {
    path: '',
    component: MMPITestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MMPITestDetailComponent,
    resolve: {
      mMPITest: MMPITestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MMPITestUpdateComponent,
    resolve: {
      mMPITest: MMPITestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MMPITestUpdateComponent,
    resolve: {
      mMPITest: MMPITestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mMPITestRoute;
