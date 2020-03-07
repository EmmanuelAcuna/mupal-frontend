import { IContact } from 'app/shared/model/contact.model';
import { IWatch } from 'app/shared/model/watch.model';
import { IPatrol } from 'app/shared/model/patrol.model';

export interface IEstablishment {
  id?: number;
  name?: string;
  paydDate?: number;
  lat?: number;
  lng?: number;
  contactId?: IContact;
  watch?: IWatch;
  patrol?: IPatrol;
}

export const defaultValue: Readonly<IEstablishment> = {};
