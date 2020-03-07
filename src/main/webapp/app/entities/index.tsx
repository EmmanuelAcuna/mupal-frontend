import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Client from './client';
import Establishment from './establishment';
import Contact from './contact';
import Watch from './watch';
import Patrol from './patrol';
import WatchGuard from './watch-guard';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}establishment`} component={Establishment} />
      <ErrorBoundaryRoute path={`${match.url}contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}watch`} component={Watch} />
      <ErrorBoundaryRoute path={`${match.url}patrol`} component={Patrol} />
      <ErrorBoundaryRoute path={`${match.url}watch-guard`} component={WatchGuard} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
