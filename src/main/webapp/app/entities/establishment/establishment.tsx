import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './establishment.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablishmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Establishment = (props: IEstablishmentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { establishmentList, match, loading } = props;
  return (
    <div>
      <h2 id="establishment-heading">
        <Translate contentKey="mupalApp.establishment.home.title">Establishments</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="mupalApp.establishment.home.createLabel">Create new Establishment</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {establishmentList && establishmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="mupalApp.establishment.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="mupalApp.establishment.contactId">Contact Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {establishmentList.map((establishment, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${establishment.id}`} color="link" size="sm">
                      {establishment.id}
                    </Button>
                  </td>
                  <td>{establishment.name}</td>
                  <td>{establishment.contactId}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${establishment.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${establishment.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${establishment.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="mupalApp.establishment.home.notFound">No Establishments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ establishment }: IRootState) => ({
  establishmentList: establishment.entities,
  loading: establishment.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Establishment);
