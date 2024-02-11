import { IAddress } from 'app/entities/address/address.model';

export interface ICity {
  id: number;
  name?: string | null;
  zip?: string | null;
  addresses?: IAddress[] | null;
}

export type NewCity = Omit<ICity, 'id'> & { id: null };
