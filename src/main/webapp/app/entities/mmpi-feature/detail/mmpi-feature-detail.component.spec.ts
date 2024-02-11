import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MMPIFeatureDetailComponent } from './mmpi-feature-detail.component';

describe('MMPIFeature Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MMPIFeatureDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MMPIFeatureDetailComponent,
              resolve: { mMPIFeature: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MMPIFeatureDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load mMPIFeature on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MMPIFeatureDetailComponent);

      // THEN
      expect(instance.mMPIFeature).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
