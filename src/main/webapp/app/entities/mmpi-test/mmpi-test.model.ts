import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';
import { IMMPIAnswer } from 'app/entities/mmpi-answer/mmpi-answer.model';

export interface IMMPITest {
  id: number;
  order?: number | null;
  date?: dayjs.Dayjs | null;
  client?: IClient | null;
  mMPIAnswers?: IMMPIAnswer[] | null;
}

export type NewMMPITest = Omit<IMMPITest, 'id'> & { id: null };
