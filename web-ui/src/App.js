import { writable } from "svelte/store";
import { afetch, catchNPersisAuthToken, forgetToken } from "./utils/fetch";

export class AppContext {
  constructor() {
    /** @type {import('svelte/store').Writable<boolean>}*/
    this._isLogged = writable(false);
    /** @type {import('svelte/store').Writable<object|null>}*/
    this._user = writable(null);
    this._loadUserInfo();
  }

  /**
   * @returns {import("svelte/store").Readable<boolean>}
   */
  get isLogged() {
    return {
      subscribe: this._isLogged.subscribe,
    };
  }

  /**
   * @returns {import("svelte/store").Readable<object|null>}
   */
  get user() {
    return {
      subscribe: this._user.subscribe,
    };
  }

  /**
   * @returns {Promise<void>}
   */
  _loadUserInfo() {
    return afetch("API_ENDPOINT/v1/whoami")
      .then((res) => res.json())
      .then((data) => {
        this._user.set(data);
        this._isLogged.set(true);
      });
  }

  /**
   * @param {string} email
   * @param {string} password
   * @returns {Promise<void>}
   */
  login(email, password) {
    return fetch("API_ENDPOINT/v1/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    })
      .then(catchNPersisAuthToken)
      .then(() => this._loadUserInfo());
  }

  logout() {
    this._isLogged.set(false);
    forgetToken();
  }
}
