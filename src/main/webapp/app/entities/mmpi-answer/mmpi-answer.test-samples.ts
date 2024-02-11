import { IMMPIAnswer, NewMMPIAnswer } from './mmpi-answer.model';

export const sampleWithRequiredData: IMMPIAnswer = {
  id: 13566,
};

export const sampleWithPartialData: IMMPIAnswer = {
  id: 30179,
};

export const sampleWithFullData: IMMPIAnswer = {
  id: 3855,
  answeredYes: false,
};

export const sampleWithNewData: NewMMPIAnswer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
