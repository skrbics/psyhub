import { ITherapist, NewTherapist } from './therapist.model';

export const sampleWithRequiredData: ITherapist = {
  id: 10641,
};

export const sampleWithPartialData: ITherapist = {
  id: 19178,
};

export const sampleWithFullData: ITherapist = {
  id: 2385,
  firstName: 'Vince',
  middleName: 'business sneaky sombrero',
  lastName: 'Turner',
  userid: 'cheddar obi',
};

export const sampleWithNewData: NewTherapist = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
