import { Moment } from 'moment';

export interface IContact {
  id?: number;
  email?: string;
  phone?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export const defaultValue: Readonly<IContact> = {};
