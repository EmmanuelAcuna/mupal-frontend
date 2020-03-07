import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
import sessions, { SessionsState } from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import client, {
  ClientState
} from 'app/entities/client/client.reducer';
// prettier-ignore
import establishment, {
  EstablishmentState
} from 'app/entities/establishment/establishment.reducer';
// prettier-ignore
import contact, {
  ContactState
} from 'app/entities/contact/contact.reducer';
// prettier-ignore
import watch, {
  WatchState
} from 'app/entities/watch/watch.reducer';
// prettier-ignore
import patrol, {
  PatrolState
} from 'app/entities/patrol/patrol.reducer';
// prettier-ignore
import watchGuard, {
  WatchGuardState
} from 'app/entities/watch-guard/watch-guard.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly sessions: SessionsState;
  readonly client: ClientState;
  readonly establishment: EstablishmentState;
  readonly contact: ContactState;
  readonly watch: WatchState;
  readonly patrol: PatrolState;
  readonly watchGuard: WatchGuardState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  client,
  establishment,
  contact,
  watch,
  patrol,
  watchGuard,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
