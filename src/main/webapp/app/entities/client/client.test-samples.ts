import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 12833,
};

export const sampleWithPartialData: IClient = {
  id: 7356,
};

export const sampleWithFullData: IClient = {
  id: 27711,
  firstName: 'Devin',
  middleName: 'follow merit cork',
  lastName: 'Goldner',
  email: 'Eliezer.Langosh@yahoo.com',
  phone: '376.506.5055',
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
