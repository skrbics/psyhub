import { ICurrency, NewCurrency } from './currency.model';

export const sampleWithRequiredData: ICurrency = {
  id: 1523,
};

export const sampleWithPartialData: ICurrency = {
  id: 28936,
  name: 'too hourly lest',
  code: 'amongst',
};

export const sampleWithFullData: ICurrency = {
  id: 23725,
  name: 'wherever for mid',
  code: 'yieldingly',
};

export const sampleWithNewData: NewCurrency = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
