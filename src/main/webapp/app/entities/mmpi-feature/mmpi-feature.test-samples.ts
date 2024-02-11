import { IMMPIFeature, NewMMPIFeature } from './mmpi-feature.model';

export const sampleWithRequiredData: IMMPIFeature = {
  id: 15539,
};

export const sampleWithPartialData: IMMPIFeature = {
  id: 25499,
  name: 'hasty ha',
};

export const sampleWithFullData: IMMPIFeature = {
  id: 2845,
  name: 'pip a quote',
};

export const sampleWithNewData: NewMMPIFeature = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
