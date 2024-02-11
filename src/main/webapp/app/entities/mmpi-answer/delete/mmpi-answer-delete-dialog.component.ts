import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMMPIAnswer } from '../mmpi-answer.model';
import { MMPIAnswerService } from '../service/mmpi-answer.service';

@Component({
  standalone: true,
  templateUrl: './mmpi-answer-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MMPIAnswerDeleteDialogComponent {
  mMPIAnswer?: IMMPIAnswer;

  constructor(
    protected mMPIAnswerService: MMPIAnswerService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mMPIAnswerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
