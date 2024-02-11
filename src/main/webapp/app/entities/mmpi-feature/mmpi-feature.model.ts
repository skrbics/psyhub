import { IMMPITestCardFeature } from 'app/entities/mmpi-test-card-feature/mmpi-test-card-feature.model';

export interface IMMPIFeature {
  id: number;
  name?: string | null;
  mMPITestCardFeatures?: IMMPITestCardFeature[] | null;
}

export type NewMMPIFeature = Omit<IMMPIFeature, 'id'> & { id: null };
