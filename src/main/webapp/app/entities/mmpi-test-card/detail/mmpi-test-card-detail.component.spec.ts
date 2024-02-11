import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MMPITestCardDetailComponent } from './mmpi-test-card-detail.component';

describe('MMPITestCard Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MMPITestCardDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MMPITestCardDetailComponent,
              resolve: { mMPITestCard: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MMPITestCardDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load mMPITestCard on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MMPITestCardDetailComponent);

      // THEN
      expect(instance.mMPITestCard).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
