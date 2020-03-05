export interface IClient {
  id?: number;
  name?: string;
  contactId?: string;
  logUrl?: string;
}

export const defaultValue: Readonly<IClient> = {};
