import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  id: 28443,
};

export const sampleWithPartialData: ICountry = {
  id: 7896,
  name: 'although because mid',
};

export const sampleWithFullData: ICountry = {
  id: 2481,
  name: 'match',
  abbreviation: 'disclaim sarcastic',
};

export const sampleWithNewData: NewCountry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
