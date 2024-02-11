import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'therapist',
    data: { pageTitle: 'psyhubApp.therapist.home.title' },
    loadChildren: () => import('./therapist/therapist.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'psyhubApp.client.home.title' },
    loadChildren: () => import('./client/client.routes'),
  },
  {
    path: 'address',
    data: { pageTitle: 'psyhubApp.address.home.title' },
    loadChildren: () => import('./address/address.routes'),
  },
  {
    path: 'office',
    data: { pageTitle: 'psyhubApp.office.home.title' },
    loadChildren: () => import('./office/office.routes'),
  },
  {
    path: 'session',
    data: { pageTitle: 'psyhubApp.session.home.title' },
    loadChildren: () => import('./session/session.routes'),
  },
  {
    path: 'session-bill',
    data: { pageTitle: 'psyhubApp.sessionBill.home.title' },
    loadChildren: () => import('./session-bill/session-bill.routes'),
  },
  {
    path: 'mmpi-test',
    data: { pageTitle: 'psyhubApp.mMPITest.home.title' },
    loadChildren: () => import('./mmpi-test/mmpi-test.routes'),
  },
  {
    path: 'mmpi-test-card-feature',
    data: { pageTitle: 'psyhubApp.mMPITestCardFeature.home.title' },
    loadChildren: () => import('./mmpi-test-card-feature/mmpi-test-card-feature.routes'),
  },
  {
    path: 'mmpi-test-card',
    data: { pageTitle: 'psyhubApp.mMPITestCard.home.title' },
    loadChildren: () => import('./mmpi-test-card/mmpi-test-card.routes'),
  },
  {
    path: 'mmpi-feature',
    data: { pageTitle: 'psyhubApp.mMPIFeature.home.title' },
    loadChildren: () => import('./mmpi-feature/mmpi-feature.routes'),
  },
  {
    path: 'mmpi-answer',
    data: { pageTitle: 'psyhubApp.mMPIAnswer.home.title' },
    loadChildren: () => import('./mmpi-answer/mmpi-answer.routes'),
  },
  {
    path: 'country',
    data: { pageTitle: 'psyhubApp.country.home.title' },
    loadChildren: () => import('./country/country.routes'),
  },
  {
    path: 'city',
    data: { pageTitle: 'psyhubApp.city.home.title' },
    loadChildren: () => import('./city/city.routes'),
  },
  {
    path: 'currency',
    data: { pageTitle: 'psyhubApp.currency.home.title' },
    loadChildren: () => import('./currency/currency.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
