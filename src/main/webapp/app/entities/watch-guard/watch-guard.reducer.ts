import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWatchGuard, defaultValue } from 'app/shared/model/watch-guard.model';

export const ACTION_TYPES = {
  FETCH_WATCHGUARD_LIST: 'watchGuard/FETCH_WATCHGUARD_LIST',
  FETCH_WATCHGUARD: 'watchGuard/FETCH_WATCHGUARD',
  CREATE_WATCHGUARD: 'watchGuard/CREATE_WATCHGUARD',
  UPDATE_WATCHGUARD: 'watchGuard/UPDATE_WATCHGUARD',
  DELETE_WATCHGUARD: 'watchGuard/DELETE_WATCHGUARD',
  RESET: 'watchGuard/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWatchGuard>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type WatchGuardState = Readonly<typeof initialState>;

// Reducer

export default (state: WatchGuardState = initialState, action): WatchGuardState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WATCHGUARD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WATCHGUARD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WATCHGUARD):
    case REQUEST(ACTION_TYPES.UPDATE_WATCHGUARD):
    case REQUEST(ACTION_TYPES.DELETE_WATCHGUARD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WATCHGUARD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WATCHGUARD):
    case FAILURE(ACTION_TYPES.CREATE_WATCHGUARD):
    case FAILURE(ACTION_TYPES.UPDATE_WATCHGUARD):
    case FAILURE(ACTION_TYPES.DELETE_WATCHGUARD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WATCHGUARD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_WATCHGUARD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WATCHGUARD):
    case SUCCESS(ACTION_TYPES.UPDATE_WATCHGUARD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WATCHGUARD):
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

const apiUrl = 'api/watch-guards';

// Actions

export const getEntities: ICrudGetAllAction<IWatchGuard> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WATCHGUARD_LIST,
    payload: axios.get<IWatchGuard>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IWatchGuard> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WATCHGUARD,
    payload: axios.get<IWatchGuard>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWatchGuard> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WATCHGUARD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWatchGuard> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WATCHGUARD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWatchGuard> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WATCHGUARD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
