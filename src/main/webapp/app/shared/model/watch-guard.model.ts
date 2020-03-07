import { Moment } from 'moment';
import { IWatch } from 'app/shared/model/watch.model';

export interface IWatchGuard {
  id?: number;
  idGuard?: string;
  assignDate?: Moment;
  idWatch?: IWatch;
}

export const defaultValue: Readonly<IWatchGuard> = {};
