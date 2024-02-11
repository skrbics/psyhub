import dayjs from 'dayjs/esm';

import { ISession, NewSession } from './session.model';

export const sampleWithRequiredData: ISession = {
  id: 27960,
};

export const sampleWithPartialData: ISession = {
  id: 28638,
  date: dayjs('2024-02-10'),
  location: 'rightfully amongst breakable',
  completed: true,
};

export const sampleWithFullData: ISession = {
  id: 16734,
  date: dayjs('2024-02-10'),
  location: 'abandoned inasmuch',
  notes: 'energetically with judgementally',
  completed: true,
};

export const sampleWithNewData: NewSession = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
