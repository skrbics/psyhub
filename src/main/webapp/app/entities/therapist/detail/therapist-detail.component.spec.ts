import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TherapistDetailComponent } from './therapist-detail.component';

describe('Therapist Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TherapistDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TherapistDetailComponent,
              resolve: { therapist: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TherapistDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load therapist on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TherapistDetailComponent);

      // THEN
      expect(instance.therapist).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
