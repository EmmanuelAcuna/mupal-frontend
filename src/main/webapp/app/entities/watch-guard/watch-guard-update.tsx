import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWatch } from 'app/shared/model/watch.model';
import { getEntities as getWatches } from 'app/entities/watch/watch.reducer';
import { getEntity, updateEntity, createEntity, reset } from './watch-guard.reducer';
import { IWatchGuard } from 'app/shared/model/watch-guard.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWatchGuardUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchGuardUpdate = (props: IWatchGuardUpdateProps) => {
  const [idWatchId, setIdWatchId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { watchGuardEntity, watches, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/watch-guard' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getWatches();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.assignDate = convertDateTimeToServer(values.assignDate);

    if (errors.length === 0) {
      const entity = {
        ...watchGuardEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mupalApp.watchGuard.home.createOrEditLabel">
            <Translate contentKey="mupalApp.watchGuard.home.createOrEditLabel">Create or edit a WatchGuard</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : watchGuardEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="watch-guard-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="watch-guard-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idGuardLabel" for="watch-guard-idGuard">
                  <Translate contentKey="mupalApp.watchGuard.idGuard">Id Guard</Translate>
                </Label>
                <AvField id="watch-guard-idGuard" type="text" name="idGuard" />
              </AvGroup>
              <AvGroup>
                <Label id="assignDateLabel" for="watch-guard-assignDate">
                  <Translate contentKey="mupalApp.watchGuard.assignDate">Assign Date</Translate>
                </Label>
                <AvInput
                  id="watch-guard-assignDate"
                  type="datetime-local"
                  className="form-control"
                  name="assignDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchGuardEntity.assignDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="watch-guard-idWatch">
                  <Translate contentKey="mupalApp.watchGuard.idWatch">Id Watch</Translate>
                </Label>
                <AvInput id="watch-guard-idWatch" type="select" className="form-control" name="idWatch.id">
                  <option value="" key="0" />
                  {watches
                    ? watches.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/watch-guard" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  watches: storeState.watch.entities,
  watchGuardEntity: storeState.watchGuard.entity,
  loading: storeState.watchGuard.loading,
  updating: storeState.watchGuard.updating,
  updateSuccess: storeState.watchGuard.updateSuccess
});

const mapDispatchToProps = {
  getWatches,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchGuardUpdate);
