<script>
  import { getContext } from "svelte";
  import { redirect } from "@roxi/routify";

  /** @type {import("../App.svelte").AppContext} */
  const appContext = getContext("app");

  appContext.isLogged.subscribe((isLogged) =>
    !isLogged
      ? !window.location.pathname.startsWith("/login") && $redirect("/login")
      : !window.location.pathname.startsWith("/app") && $redirect("/app")
  );
</script>

<!-- routify:options preload="proximity" -->

<slot />
