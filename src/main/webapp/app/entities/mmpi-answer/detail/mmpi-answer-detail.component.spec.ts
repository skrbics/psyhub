import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MMPIAnswerDetailComponent } from './mmpi-answer-detail.component';

describe('MMPIAnswer Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MMPIAnswerDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MMPIAnswerDetailComponent,
              resolve: { mMPIAnswer: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MMPIAnswerDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load mMPIAnswer on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MMPIAnswerDetailComponent);

      // THEN
      expect(instance.mMPIAnswer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
