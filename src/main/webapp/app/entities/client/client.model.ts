import { IAddress } from 'app/entities/address/address.model';
import { ISession } from 'app/entities/session/session.model';
import { IMMPITest } from 'app/entities/mmpi-test/mmpi-test.model';

export interface IClient {
  id: number;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phone?: string | null;
  address?: IAddress | null;
  sessions?: ISession[] | null;
  mMPITests?: IMMPITest[] | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
