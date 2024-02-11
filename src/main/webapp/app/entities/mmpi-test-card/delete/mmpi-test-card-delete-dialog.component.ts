import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMMPITestCard } from '../mmpi-test-card.model';
import { MMPITestCardService } from '../service/mmpi-test-card.service';

@Component({
  standalone: true,
  templateUrl: './mmpi-test-card-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MMPITestCardDeleteDialogComponent {
  mMPITestCard?: IMMPITestCard;

  constructor(
    protected mMPITestCardService: MMPITestCardService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mMPITestCardService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
