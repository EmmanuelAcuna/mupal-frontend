import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetail = (props: IClientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mupalApp.client.detail.title">Client</Translate> [<b>{clientEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="mupalApp.client.name">Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.name}</dd>
          <dt>
            <span id="logoUrl">
              <Translate contentKey="mupalApp.client.logoUrl">Logo Url</Translate>
            </span>
          </dt>
          <dd>{clientEntity.logoUrl}</dd>
          <dt>
            <span id="salary">
              <Translate contentKey="mupalApp.client.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{clientEntity.salary}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="mupalApp.client.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={clientEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="mupalApp.client.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={clientEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="mupalApp.client.contactId">Contact Id</Translate>
          </dt>
          <dd>{clientEntity.contactId ? clientEntity.contactId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetail);
