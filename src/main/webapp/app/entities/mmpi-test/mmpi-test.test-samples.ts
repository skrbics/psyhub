import dayjs from 'dayjs/esm';

import { IMMPITest, NewMMPITest } from './mmpi-test.model';

export const sampleWithRequiredData: IMMPITest = {
  id: 22057,
};

export const sampleWithPartialData: IMMPITest = {
  id: 13651,
  date: dayjs('2024-02-11'),
};

export const sampleWithFullData: IMMPITest = {
  id: 22156,
  order: 5053,
  date: dayjs('2024-02-11'),
};

export const sampleWithNewData: NewMMPITest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
