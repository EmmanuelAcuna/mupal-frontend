import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './watch.reducer';
import { IWatch } from 'app/shared/model/watch.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWatchUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchUpdate = (props: IWatchUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { watchEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/watch' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.validFrom = convertDateTimeToServer(values.validFrom);
    values.validTo = convertDateTimeToServer(values.validTo);
    values.startOn = convertDateTimeToServer(values.startOn);
    values.endOn = convertDateTimeToServer(values.endOn);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    if (errors.length === 0) {
      const entity = {
        ...watchEntity,
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
          <h2 id="mupalApp.watch.home.createOrEditLabel">
            <Translate contentKey="mupalApp.watch.home.createOrEditLabel">Create or edit a Watch</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : watchEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="watch-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="watch-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="validFromLabel" for="watch-validFrom">
                  <Translate contentKey="mupalApp.watch.validFrom">Valid From</Translate>
                </Label>
                <AvInput
                  id="watch-validFrom"
                  type="datetime-local"
                  className="form-control"
                  name="validFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchEntity.validFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="validToLabel" for="watch-validTo">
                  <Translate contentKey="mupalApp.watch.validTo">Valid To</Translate>
                </Label>
                <AvInput
                  id="watch-validTo"
                  type="datetime-local"
                  className="form-control"
                  name="validTo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchEntity.validTo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="startOnLabel" for="watch-startOn">
                  <Translate contentKey="mupalApp.watch.startOn">Start On</Translate>
                </Label>
                <AvInput
                  id="watch-startOn"
                  type="datetime-local"
                  className="form-control"
                  name="startOn"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchEntity.startOn)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endOnLabel" for="watch-endOn">
                  <Translate contentKey="mupalApp.watch.endOn">End On</Translate>
                </Label>
                <AvInput
                  id="watch-endOn"
                  type="datetime-local"
                  className="form-control"
                  name="endOn"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchEntity.endOn)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdAtLabel" for="watch-createdAt">
                  <Translate contentKey="mupalApp.watch.createdAt">Created At</Translate>
                </Label>
                <AvInput
                  id="watch-createdAt"
                  type="datetime-local"
                  className="form-control"
                  name="createdAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchEntity.createdAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedAtLabel" for="watch-updatedAt">
                  <Translate contentKey="mupalApp.watch.updatedAt">Updated At</Translate>
                </Label>
                <AvInput
                  id="watch-updatedAt"
                  type="datetime-local"
                  className="form-control"
                  name="updatedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchEntity.updatedAt)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/watch" replace color="info">
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
  watchEntity: storeState.watch.entity,
  loading: storeState.watch.loading,
  updating: storeState.watch.updating,
  updateSuccess: storeState.watch.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchUpdate);
