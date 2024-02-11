import { IOffice } from 'app/entities/office/office.model';

export interface ITherapist {
  id: number;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  userid?: string | null;
  office?: IOffice | null;
}

export type NewTherapist = Omit<ITherapist, 'id'> & { id: null };
