import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMMPITest } from '../mmpi-test.model';
import { MMPITestService } from '../service/mmpi-test.service';

@Component({
  standalone: true,
  templateUrl: './mmpi-test-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MMPITestDeleteDialogComponent {
  mMPITest?: IMMPITest;

  constructor(
    protected mMPITestService: MMPITestService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mMPITestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
