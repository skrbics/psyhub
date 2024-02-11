import { IAddress } from 'app/entities/address/address.model';

export interface ICountry {
  id: number;
  name?: string | null;
  abbreviation?: string | null;
  addresses?: IAddress[] | null;
}

export type NewCountry = Omit<ICountry, 'id'> & { id: null };
