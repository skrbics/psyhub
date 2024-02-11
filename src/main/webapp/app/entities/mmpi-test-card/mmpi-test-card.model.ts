import { IMMPIAnswer } from 'app/entities/mmpi-answer/mmpi-answer.model';
import { IMMPITestCardFeature } from 'app/entities/mmpi-test-card-feature/mmpi-test-card-feature.model';

export interface IMMPITestCard {
  id: number;
  question?: string | null;
  mMPIAnswers?: IMMPIAnswer[] | null;
  mMPITestCardFeatures?: IMMPITestCardFeature[] | null;
}

export type NewMMPITestCard = Omit<IMMPITestCard, 'id'> & { id: null };
