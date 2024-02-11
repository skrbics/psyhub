import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';
import { MMPITestCardFeatureService } from '../service/mmpi-test-card-feature.service';

@Component({
  standalone: true,
  templateUrl: './mmpi-test-card-feature-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MMPITestCardFeatureDeleteDialogComponent {
  mMPITestCardFeature?: IMMPITestCardFeature;

  constructor(
    protected mMPITestCardFeatureService: MMPITestCardFeatureService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mMPITestCardFeatureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
