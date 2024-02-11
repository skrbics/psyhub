import dayjs from 'dayjs/esm';
import { ISessionBill } from 'app/entities/session-bill/session-bill.model';
import { IClient } from 'app/entities/client/client.model';

export interface ISession {
  id: number;
  date?: dayjs.Dayjs | null;
  location?: string | null;
  notes?: string | null;
  completed?: boolean | null;
  sessionBill?: ISessionBill | null;
  client?: IClient | null;
}

export type NewSession = Omit<ISession, 'id'> & { id: null };
