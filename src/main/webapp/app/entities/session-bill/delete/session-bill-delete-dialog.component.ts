import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISessionBill } from '../session-bill.model';
import { SessionBillService } from '../service/session-bill.service';

@Component({
  standalone: true,
  templateUrl: './session-bill-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SessionBillDeleteDialogComponent {
  sessionBill?: ISessionBill;

  constructor(
    protected sessionBillService: SessionBillService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sessionBillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
