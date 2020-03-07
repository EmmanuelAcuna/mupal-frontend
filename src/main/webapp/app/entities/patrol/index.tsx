import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Patrol from './patrol';
import PatrolDetail from './patrol-detail';
import PatrolUpdate from './patrol-update';
import PatrolDeleteDialog from './patrol-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PatrolDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PatrolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PatrolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PatrolDetail} />
      <ErrorBoundaryRoute path={match.url} component={Patrol} />
    </Switch>
  </>
);

export default Routes;
