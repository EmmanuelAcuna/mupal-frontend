import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Watch from './watch';
import WatchDetail from './watch-detail';
import WatchUpdate from './watch-update';
import WatchDeleteDialog from './watch-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WatchDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WatchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WatchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WatchDetail} />
      <ErrorBoundaryRoute path={match.url} component={Watch} />
    </Switch>
  </>
);

export default Routes;
