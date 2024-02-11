import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITherapist } from '../therapist.model';

@Component({
  standalone: true,
  selector: 'jhi-therapist-detail',
  templateUrl: './therapist-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TherapistDetailComponent {
  @Input() therapist: ITherapist | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
