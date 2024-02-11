import { ISessionBill, NewSessionBill } from './session-bill.model';

export const sampleWithRequiredData: ISessionBill = {
  id: 5460,
};

export const sampleWithPartialData: ISessionBill = {
  id: 17433,
};

export const sampleWithFullData: ISessionBill = {
  id: 16060,
  amount: 12934.74,
  paid: false,
};

export const sampleWithNewData: NewSessionBill = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
