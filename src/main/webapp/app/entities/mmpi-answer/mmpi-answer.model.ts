import { IMMPITest } from 'app/entities/mmpi-test/mmpi-test.model';
import { IMMPITestCard } from 'app/entities/mmpi-test-card/mmpi-test-card.model';

export interface IMMPIAnswer {
  id: number;
  answeredYes?: boolean | null;
  mMPITest?: IMMPITest | null;
  mMPITestCard?: IMMPITestCard | null;
}

export type NewMMPIAnswer = Omit<IMMPIAnswer, 'id'> & { id: null };
