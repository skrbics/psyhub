import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MMPITestDetailComponent } from './mmpi-test-detail.component';

describe('MMPITest Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MMPITestDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MMPITestDetailComponent,
              resolve: { mMPITest: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MMPITestDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load mMPITest on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MMPITestDetailComponent);

      // THEN
      expect(instance.mMPITest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
