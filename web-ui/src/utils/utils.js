/** @type {boolean} */
export const isLocalStorageSupported = (() => {
  /** @type {Storage} */
  const _localStorage = window.localStorage;

  const test = "test";
  try {
    _localStorage.setItem(test, test);
    _localStorage.removeItem(test);
    return true;
  } catch (e) {
    return false;
  }
})();
