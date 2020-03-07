import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './patrol.reducer';
import { IPatrol } from 'app/shared/model/patrol.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPatrolDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PatrolDetail = (props: IPatrolDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { patrolEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mupalApp.patrol.detail.title">Patrol</Translate> [<b>{patrolEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="mupalApp.patrol.name">Name</Translate>
            </span>
          </dt>
          <dd>{patrolEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/patrol" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patrol/${patrolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ patrol }: IRootState) => ({
  patrolEntity: patrol.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PatrolDetail);
