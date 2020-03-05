import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './establishment.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablishmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EstablishmentDetail = (props: IEstablishmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { establishmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mupalApp.establishment.detail.title">Establishment</Translate> [<b>{establishmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="mupalApp.establishment.name">Name</Translate>
            </span>
          </dt>
          <dd>{establishmentEntity.name}</dd>
          <dt>
            <span id="contactId">
              <Translate contentKey="mupalApp.establishment.contactId">Contact Id</Translate>
            </span>
          </dt>
          <dd>{establishmentEntity.contactId}</dd>
        </dl>
        <Button tag={Link} to="/establishment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/establishment/${establishmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ establishment }: IRootState) => ({
  establishmentEntity: establishment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentDetail);
