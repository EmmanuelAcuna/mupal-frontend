import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './watch.reducer';
import { IWatch } from 'app/shared/model/watch.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWatchDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchDetail = (props: IWatchDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { watchEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mupalApp.watch.detail.title">Watch</Translate> [<b>{watchEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="validFrom">
              <Translate contentKey="mupalApp.watch.validFrom">Valid From</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchEntity.validFrom} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="validTo">
              <Translate contentKey="mupalApp.watch.validTo">Valid To</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchEntity.validTo} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="startOn">
              <Translate contentKey="mupalApp.watch.startOn">Start On</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchEntity.startOn} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="endOn">
              <Translate contentKey="mupalApp.watch.endOn">End On</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchEntity.endOn} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="mupalApp.watch.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="mupalApp.watch.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/watch" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/watch/${watchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ watch }: IRootState) => ({
  watchEntity: watch.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchDetail);
