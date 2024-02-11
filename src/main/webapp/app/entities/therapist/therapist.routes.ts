import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TherapistComponent } from './list/therapist.component';
import { TherapistDetailComponent } from './detail/therapist-detail.component';
import { TherapistUpdateComponent } from './update/therapist-update.component';
import TherapistResolve from './route/therapist-routing-resolve.service';

const therapistRoute: Routes = [
  {
    path: '',
    component: TherapistComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TherapistDetailComponent,
    resolve: {
      therapist: TherapistResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TherapistUpdateComponent,
    resolve: {
      therapist: TherapistResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TherapistUpdateComponent,
    resolve: {
      therapist: TherapistResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default therapistRoute;
