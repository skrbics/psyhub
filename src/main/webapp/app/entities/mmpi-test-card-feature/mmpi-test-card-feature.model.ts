import { IMMPITestCard } from 'app/entities/mmpi-test-card/mmpi-test-card.model';
import { IMMPIFeature } from 'app/entities/mmpi-feature/mmpi-feature.model';

export interface IMMPITestCardFeature {
  id: number;
  answerYes?: boolean | null;
  mMPITestCard?: IMMPITestCard | null;
  mMPIFeature?: IMMPIFeature | null;
}

export type NewMMPITestCardFeature = Omit<IMMPITestCardFeature, 'id'> & { id: null };
