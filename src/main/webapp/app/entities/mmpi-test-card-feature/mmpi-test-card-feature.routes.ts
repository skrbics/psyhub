import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MMPITestCardFeatureComponent } from './list/mmpi-test-card-feature.component';
import { MMPITestCardFeatureDetailComponent } from './detail/mmpi-test-card-feature-detail.component';
import { MMPITestCardFeatureUpdateComponent } from './update/mmpi-test-card-feature-update.component';
import MMPITestCardFeatureResolve from './route/mmpi-test-card-feature-routing-resolve.service';

const mMPITestCardFeatureRoute: Routes = [
  {
    path: '',
    component: MMPITestCardFeatureComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MMPITestCardFeatureDetailComponent,
    resolve: {
      mMPITestCardFeature: MMPITestCardFeatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MMPITestCardFeatureUpdateComponent,
    resolve: {
      mMPITestCardFeature: MMPITestCardFeatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MMPITestCardFeatureUpdateComponent,
    resolve: {
      mMPITestCardFeature: MMPITestCardFeatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mMPITestCardFeatureRoute;
