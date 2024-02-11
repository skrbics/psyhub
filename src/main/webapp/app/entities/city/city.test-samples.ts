import { ICity, NewCity } from './city.model';

export const sampleWithRequiredData: ICity = {
  id: 5742,
};

export const sampleWithPartialData: ICity = {
  id: 8383,
};

export const sampleWithFullData: ICity = {
  id: 22253,
  name: 'ligate brr',
  zip: 'verbally',
};

export const sampleWithNewData: NewCity = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
