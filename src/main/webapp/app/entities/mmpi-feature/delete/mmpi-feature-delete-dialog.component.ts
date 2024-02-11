import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMMPIFeature } from '../mmpi-feature.model';
import { MMPIFeatureService } from '../service/mmpi-feature.service';

@Component({
  standalone: true,
  templateUrl: './mmpi-feature-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MMPIFeatureDeleteDialogComponent {
  mMPIFeature?: IMMPIFeature;

  constructor(
    protected mMPIFeatureService: MMPIFeatureService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mMPIFeatureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
