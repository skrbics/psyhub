import { IMMPITestCardFeature, NewMMPITestCardFeature } from './mmpi-test-card-feature.model';

export const sampleWithRequiredData: IMMPITestCardFeature = {
  id: 18087,
};

export const sampleWithPartialData: IMMPITestCardFeature = {
  id: 25711,
  answerYes: true,
};

export const sampleWithFullData: IMMPITestCardFeature = {
  id: 6857,
  answerYes: true,
};

export const sampleWithNewData: NewMMPITestCardFeature = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
