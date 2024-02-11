import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 21390,
};

export const sampleWithPartialData: IAddress = {
  id: 12873,
  street: 'Everette Creek',
};

export const sampleWithFullData: IAddress = {
  id: 28835,
  street: 'Dare Rest',
  houseNo: 'around',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
