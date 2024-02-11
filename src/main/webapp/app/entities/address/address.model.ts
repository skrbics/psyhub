import { ICity } from 'app/entities/city/city.model';
import { ICountry } from 'app/entities/country/country.model';
import { IClient } from 'app/entities/client/client.model';
import { IOffice } from 'app/entities/office/office.model';

export interface IAddress {
  id: number;
  street?: string | null;
  houseNo?: string | null;
  city?: ICity | null;
  country?: ICountry | null;
  client?: IClient | null;
  office?: IOffice | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
