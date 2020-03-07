import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPatrol, defaultValue } from 'app/shared/model/patrol.model';

export const ACTION_TYPES = {
  FETCH_PATROL_LIST: 'patrol/FETCH_PATROL_LIST',
  FETCH_PATROL: 'patrol/FETCH_PATROL',
  CREATE_PATROL: 'patrol/CREATE_PATROL',
  UPDATE_PATROL: 'patrol/UPDATE_PATROL',
  DELETE_PATROL: 'patrol/DELETE_PATROL',
  RESET: 'patrol/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPatrol>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PatrolState = Readonly<typeof initialState>;

// Reducer

export default (state: PatrolState = initialState, action): PatrolState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PATROL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PATROL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PATROL):
    case REQUEST(ACTION_TYPES.UPDATE_PATROL):
    case REQUEST(ACTION_TYPES.DELETE_PATROL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PATROL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PATROL):
    case FAILURE(ACTION_TYPES.CREATE_PATROL):
    case FAILURE(ACTION_TYPES.UPDATE_PATROL):
    case FAILURE(ACTION_TYPES.DELETE_PATROL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PATROL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PATROL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PATROL):
    case SUCCESS(ACTION_TYPES.UPDATE_PATROL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PATROL):
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

const apiUrl = 'api/patrols';

// Actions

export const getEntities: ICrudGetAllAction<IPatrol> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PATROL_LIST,
    payload: axios.get<IPatrol>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPatrol> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PATROL,
    payload: axios.get<IPatrol>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPatrol> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PATROL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPatrol> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PATROL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPatrol> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PATROL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
