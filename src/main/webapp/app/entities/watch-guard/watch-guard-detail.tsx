import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './watch-guard.reducer';
import { IWatchGuard } from 'app/shared/model/watch-guard.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWatchGuardDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchGuardDetail = (props: IWatchGuardDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { watchGuardEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mupalApp.watchGuard.detail.title">WatchGuard</Translate> [<b>{watchGuardEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idGuard">
              <Translate contentKey="mupalApp.watchGuard.idGuard">Id Guard</Translate>
            </span>
          </dt>
          <dd>{watchGuardEntity.idGuard}</dd>
          <dt>
            <span id="assignDate">
              <Translate contentKey="mupalApp.watchGuard.assignDate">Assign Date</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={watchGuardEntity.assignDate} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="mupalApp.watchGuard.idWatch">Id Watch</Translate>
          </dt>
          <dd>{watchGuardEntity.idWatch ? watchGuardEntity.idWatch.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/watch-guard" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/watch-guard/${watchGuardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ watchGuard }: IRootState) => ({
  watchGuardEntity: watchGuard.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchGuardDetail);
