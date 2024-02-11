import { IOffice, NewOffice } from './office.model';

export const sampleWithRequiredData: IOffice = {
  id: 12893,
};

export const sampleWithPartialData: IOffice = {
  id: 25746,
  website: 'and guinea',
  email: 'Desmond_Labadie@hotmail.com',
};

export const sampleWithFullData: IOffice = {
  id: 18587,
  officeName: 'yet',
  website: 'willing cheerfully pish',
  email: 'Muhammad.Kuphal@hotmail.com',
  phone: '494.754.5136 x2178',
};

export const sampleWithNewData: NewOffice = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
