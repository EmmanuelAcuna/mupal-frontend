import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WatchGuard from './watch-guard';
import WatchGuardDetail from './watch-guard-detail';
import WatchGuardUpdate from './watch-guard-update';
import WatchGuardDeleteDialog from './watch-guard-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WatchGuardDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WatchGuardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WatchGuardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WatchGuardDetail} />
      <ErrorBoundaryRoute path={match.url} component={WatchGuard} />
    </Switch>
  </>
);

export default Routes;
