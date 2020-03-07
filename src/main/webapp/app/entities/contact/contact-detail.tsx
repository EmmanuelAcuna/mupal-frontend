import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContactDetail = (props: IContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { contactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mupalApp.contact.detail.title">Contact</Translate> [<b>{contactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="email">
              <Translate contentKey="mupalApp.contact.email">Email</Translate>
            </span>
          </dt>
          <dd>{contactEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="mupalApp.contact.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{contactEntity.phone}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="mupalApp.contact.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={contactEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="mupalApp.contact.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={contactEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ contact }: IRootState) => ({
  contactEntity: contact.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContactDetail);
