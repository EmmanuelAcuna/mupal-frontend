import { Moment } from 'moment';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IWatch {
  id?: number;
  validFrom?: Moment;
  validTo?: Moment;
  startOn?: Moment;
  endOn?: Moment;
  createdAt?: Moment;
  updatedAt?: Moment;
  idEstablishes?: IEstablishment[];
}

export const defaultValue: Readonly<IWatch> = {};
