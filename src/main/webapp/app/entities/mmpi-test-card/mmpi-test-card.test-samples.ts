import { IMMPITestCard, NewMMPITestCard } from './mmpi-test-card.model';

export const sampleWithRequiredData: IMMPITestCard = {
  id: 10767,
};

export const sampleWithPartialData: IMMPITestCard = {
  id: 27020,
};

export const sampleWithFullData: IMMPITestCard = {
  id: 31769,
  question: 'dazzling petition slowly',
};

export const sampleWithNewData: NewMMPITestCard = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
