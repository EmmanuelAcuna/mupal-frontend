import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEstablishment, defaultValue } from 'app/shared/model/establishment.model';

export const ACTION_TYPES = {
  FETCH_ESTABLISHMENT_LIST: 'establishment/FETCH_ESTABLISHMENT_LIST',
  FETCH_ESTABLISHMENT: 'establishment/FETCH_ESTABLISHMENT',
  CREATE_ESTABLISHMENT: 'establishment/CREATE_ESTABLISHMENT',
  UPDATE_ESTABLISHMENT: 'establishment/UPDATE_ESTABLISHMENT',
  DELETE_ESTABLISHMENT: 'establishment/DELETE_ESTABLISHMENT',
  RESET: 'establishment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEstablishment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EstablishmentState = Readonly<typeof initialState>;

// Reducer

export default (state: EstablishmentState = initialState, action): EstablishmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ESTABLISHMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ESTABLISHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ESTABLISHMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ESTABLISHMENT):
    case REQUEST(ACTION_TYPES.DELETE_ESTABLISHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ESTABLISHMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ESTABLISHMENT):
    case FAILURE(ACTION_TYPES.CREATE_ESTABLISHMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ESTABLISHMENT):
    case FAILURE(ACTION_TYPES.DELETE_ESTABLISHMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESTABLISHMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESTABLISHMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ESTABLISHMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ESTABLISHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ESTABLISHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/establishments';

// Actions

export const getEntities: ICrudGetAllAction<IEstablishment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ESTABLISHMENT_LIST,
    payload: axios.get<IEstablishment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEstablishment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ESTABLISHMENT,
    payload: axios.get<IEstablishment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEstablishment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ESTABLISHMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEstablishment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ESTABLISHMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEstablishment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ESTABLISHMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
