import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMMPITest } from '../mmpi-test.model';

@Component({
  standalone: true,
  selector: 'jhi-mmpi-test-detail',
  templateUrl: './mmpi-test-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MMPITestDetailComponent {
  @Input() mMPITest: IMMPITest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
