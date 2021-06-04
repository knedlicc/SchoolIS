import path from "path";
import dotenv from "dotenv";

/**
 * Loads environment specific variables
 * @param {Object} config
 */
export const setupEnv = (() => {
  const DEVELOPMENT_ENV = "development";
  const PRODUCTION_ENV = "production";
  const DEFAULT_ENV = DEVELOPMENT_ENV;

  let dotEnv = {
    [DEVELOPMENT_ENV]: undefined,
    [PRODUCTION_ENV]: undefined,
  };

  /**
   * @param {string} env
   */
  function getEnvFilenameSuffix(env) {
    let suffix = "." + env;

    if (env === DEVELOPMENT_ENV) {
      suffix = "";
    } else if (env === PRODUCTION_ENV) {
      suffix = ".prod";
    }

    return suffix;
  }

  function loadEnv(config) {
    let env = config.env;

    if (!env) env = DEFAULT_ENV;

    if (dotEnv[env]) return dotEnv[env];

    // Get
    const suffix = getEnvFilenameSuffix(env);
    const localEnvConfig = dotenv.config({
      path: path.resolve(process.cwd(), "./.env" + suffix + ".local"),
    });

    const envConfig = dotenv.config({
      path: path.resolve(process.cwd(), "./.env" + suffix),
    });

    dotEnv[env] = Object.assign(
      {},
      env != DEVELOPMENT_ENV ? loadEnv({ env: DEVELOPMENT_ENV }) : {},
      envConfig.parsed,
      localEnvConfig.parsed
    );

    return dotEnv[env];
  }

  return loadEnv;
})();

export function getReplaceObj({ env }) {
  const loadedEnv = setupEnv({ env });
  const replaceObj = Object.assign({}, loadedEnv, {
    "process.env.NODE_ENV": JSON.stringify(env),
  });

  return replaceObj;
}
