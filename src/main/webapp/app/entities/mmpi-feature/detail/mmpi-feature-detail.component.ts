import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMMPIFeature } from '../mmpi-feature.model';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-feature-detail',
  templateUrl: './mmpi-feature-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MMPIFeatureDetailComponent {
  @Input() mMPIFeature: IMMPIFeature | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
