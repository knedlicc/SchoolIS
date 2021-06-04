import { isLocalStorageSupported } from "./utils";

export const HttpStatus = {
  OK: 200,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
};
Object.freeze(HttpStatus);

const TOKEN_KEY = "token";

/** @type {string|null} */
let token = null;

/**
 * @param {Response} response
 * @returns {Response}
 */
function isResponseOk(response) {
  if (!response.ok) throw { response, message: "response is not ok" };
  return response;
}

/**
 * @param {Response} response
 * @returns {Promise<Response>}
 */
export async function validateResponseStatus(response) {
  switch (response.status) {
    case HttpStatus.UNAUTHORIZED:
      throw { status: HttpStatus.UNAUTHORIZED, response };
    case HttpStatus.FORBIDDEN:
      throw { status: HttpStatus.FORBIDDEN, response };
  }

  return response;
}

/**
 * @param {Response} response
 * @returns {Promise<void>}
 */
export async function catchNPersisAuthToken(response) {
  switch (response.status) {
    case HttpStatus.OK:
      const data = await response.json();
      if (Object.prototype.hasOwnProperty.call(data, TOKEN_KEY)) {
        if (isLocalStorageSupported) {
          localStorage.setItem(TOKEN_KEY, data.token);
        } else {
          // If local storage is unsupported then store token in memory. It will be lost in case of page reload.
          token = data.token;
        }
      } else {
        throw { response, message: "Missing token" };
      }
      break;

    default:
      throw { response, message: "Wrong status" };
  }
}

export function forgetToken() {
  if (isLocalStorageSupported) {
    localStorage.removeItem(TOKEN_KEY);
  }
  token = null;
}

/**
 * @param {RequestInfo} input
 * @param {RequestInit} init
 * @returns {Promise<Response>}
 */
export function afetch(input, init = {}) {
  let _token;
  if (isLocalStorageSupported) {
    _token = localStorage.getItem(TOKEN_KEY);
  } else if (token) {
    // if local storage is unsupported and token is present in memory
    _token = token;
  }

  if (_token) {
    init.headers = Object.assign({}, init.headers, {
      Authorization: "Bearer " + _token,
    });
  }

  return fetch(input, init).then(isResponseOk).then(validateResponseStatus);
}
