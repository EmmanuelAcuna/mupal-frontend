import { Moment } from 'moment';
import { IContact } from 'app/shared/model/contact.model';

export interface IClient {
  id?: number;
  name?: string;
  logoUrl?: string;
  salary?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  contactId?: IContact;
}

export const defaultValue: Readonly<IClient> = {};
