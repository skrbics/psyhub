import { ICurrency } from 'app/entities/currency/currency.model';
import { ISession } from 'app/entities/session/session.model';

export interface ISessionBill {
  id: number;
  amount?: number | null;
  paid?: boolean | null;
  currency?: ICurrency | null;
  session?: ISession | null;
}

export type NewSessionBill = Omit<ISessionBill, 'id'> & { id: null };
