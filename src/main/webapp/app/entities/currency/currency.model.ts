import { ISessionBill } from 'app/entities/session-bill/session-bill.model';

export interface ICurrency {
  id: number;
  name?: string | null;
  code?: string | null;
  sessionBills?: ISessionBill[] | null;
}

export type NewCurrency = Omit<ICurrency, 'id'> & { id: null };
