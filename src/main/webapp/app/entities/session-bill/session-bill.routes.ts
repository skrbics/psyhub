import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SessionBillComponent } from './list/session-bill.component';
import { SessionBillDetailComponent } from './detail/session-bill-detail.component';
import { SessionBillUpdateComponent } from './update/session-bill-update.component';
import SessionBillResolve from './route/session-bill-routing-resolve.service';

const sessionBillRoute: Routes = [
  {
    path: '',
    component: SessionBillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SessionBillDetailComponent,
    resolve: {
      sessionBill: SessionBillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SessionBillUpdateComponent,
    resolve: {
      sessionBill: SessionBillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SessionBillUpdateComponent,
    resolve: {
      sessionBill: SessionBillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sessionBillRoute;
