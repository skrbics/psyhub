import { IAddress } from 'app/entities/address/address.model';
import { ITherapist } from 'app/entities/therapist/therapist.model';

export interface IOffice {
  id: number;
  officeName?: string | null;
  website?: string | null;
  email?: string | null;
  phone?: string | null;
  address?: IAddress | null;
  therapists?: ITherapist[] | null;
}

export type NewOffice = Omit<IOffice, 'id'> & { id: null };
