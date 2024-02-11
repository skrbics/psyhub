import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-test-card-feature-detail',
  templateUrl: './mmpi-test-card-feature-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MMPITestCardFeatureDetailComponent {
  @Input() mMPITestCardFeature: IMMPITestCardFeature | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
