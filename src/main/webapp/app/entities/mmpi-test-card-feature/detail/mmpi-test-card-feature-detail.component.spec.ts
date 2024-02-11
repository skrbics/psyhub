import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MMPITestCardFeatureDetailComponent } from './mmpi-test-card-feature-detail.component';

describe('MMPITestCardFeature Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MMPITestCardFeatureDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MMPITestCardFeatureDetailComponent,
              resolve: { mMPITestCardFeature: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MMPITestCardFeatureDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load mMPITestCardFeature on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MMPITestCardFeatureDetailComponent);

      // THEN
      expect(instance.mMPITestCardFeature).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
