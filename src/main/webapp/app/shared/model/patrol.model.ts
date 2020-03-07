import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IPatrol {
  id?: number;
  name?: string;
  idEstablishes?: IEstablishment[];
}

export const defaultValue: Readonly<IPatrol> = {};
