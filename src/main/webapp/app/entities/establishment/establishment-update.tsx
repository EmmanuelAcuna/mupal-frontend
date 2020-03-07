import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IContact } from 'app/shared/model/contact.model';
import { getEntities as getContacts } from 'app/entities/contact/contact.reducer';
import { IWatch } from 'app/shared/model/watch.model';
import { getEntities as getWatches } from 'app/entities/watch/watch.reducer';
import { IPatrol } from 'app/shared/model/patrol.model';
import { getEntities as getPatrols } from 'app/entities/patrol/patrol.reducer';
import { getEntity, updateEntity, createEntity, reset } from './establishment.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEstablishmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EstablishmentUpdate = (props: IEstablishmentUpdateProps) => {
  const [contactIdId, setContactIdId] = useState('0');
  const [watchId, setWatchId] = useState('0');
  const [patrolId, setPatrolId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { establishmentEntity, contacts, watches, patrols, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/establishment' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getContacts();
    props.getWatches();
    props.getPatrols();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...establishmentEntity,
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
          <h2 id="mupalApp.establishment.home.createOrEditLabel">
            <Translate contentKey="mupalApp.establishment.home.createOrEditLabel">Create or edit a Establishment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : establishmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="establishment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="establishment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="establishment-name">
                  <Translate contentKey="mupalApp.establishment.name">Name</Translate>
                </Label>
                <AvField id="establishment-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="paydDateLabel" for="establishment-paydDate">
                  <Translate contentKey="mupalApp.establishment.paydDate">Payd Date</Translate>
                </Label>
                <AvField id="establishment-paydDate" type="string" className="form-control" name="paydDate" />
              </AvGroup>
              <AvGroup>
                <Label id="latLabel" for="establishment-lat">
                  <Translate contentKey="mupalApp.establishment.lat">Lat</Translate>
                </Label>
                <AvField id="establishment-lat" type="string" className="form-control" name="lat" />
              </AvGroup>
              <AvGroup>
                <Label id="lngLabel" for="establishment-lng">
                  <Translate contentKey="mupalApp.establishment.lng">Lng</Translate>
                </Label>
                <AvField id="establishment-lng" type="string" className="form-control" name="lng" />
              </AvGroup>
              <AvGroup>
                <Label for="establishment-contactId">
                  <Translate contentKey="mupalApp.establishment.contactId">Contact Id</Translate>
                </Label>
                <AvInput id="establishment-contactId" type="select" className="form-control" name="contactId.id">
                  <option value="" key="0" />
                  {contacts
                    ? contacts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="establishment-watch">
                  <Translate contentKey="mupalApp.establishment.watch">Watch</Translate>
                </Label>
                <AvInput id="establishment-watch" type="select" className="form-control" name="watch.id">
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
              <AvGroup>
                <Label for="establishment-patrol">
                  <Translate contentKey="mupalApp.establishment.patrol">Patrol</Translate>
                </Label>
                <AvInput id="establishment-patrol" type="select" className="form-control" name="patrol.id">
                  <option value="" key="0" />
                  {patrols
                    ? patrols.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/establishment" replace color="info">
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
  contacts: storeState.contact.entities,
  watches: storeState.watch.entities,
  patrols: storeState.patrol.entities,
  establishmentEntity: storeState.establishment.entity,
  loading: storeState.establishment.loading,
  updating: storeState.establishment.updating,
  updateSuccess: storeState.establishment.updateSuccess
});

const mapDispatchToProps = {
  getContacts,
  getWatches,
  getPatrols,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentUpdate);
