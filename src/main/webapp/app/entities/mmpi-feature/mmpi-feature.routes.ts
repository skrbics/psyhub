import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MMPIFeatureComponent } from './list/mmpi-feature.component';
import { MMPIFeatureDetailComponent } from './detail/mmpi-feature-detail.component';
import { MMPIFeatureUpdateComponent } from './update/mmpi-feature-update.component';
import MMPIFeatureResolve from './route/mmpi-feature-routing-resolve.service';

const mMPIFeatureRoute: Routes = [
  {
    path: '',
    component: MMPIFeatureComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MMPIFeatureDetailComponent,
    resolve: {
      mMPIFeature: MMPIFeatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MMPIFeatureUpdateComponent,
    resolve: {
      mMPIFeature: MMPIFeatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MMPIFeatureUpdateComponent,
    resolve: {
      mMPIFeature: MMPIFeatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mMPIFeatureRoute;
