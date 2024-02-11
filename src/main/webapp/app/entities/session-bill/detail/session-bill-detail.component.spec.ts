import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SessionBillDetailComponent } from './session-bill-detail.component';

describe('SessionBill Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionBillDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SessionBillDetailComponent,
              resolve: { sessionBill: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SessionBillDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load sessionBill on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SessionBillDetailComponent);

      // THEN
      expect(instance.sessionBill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
