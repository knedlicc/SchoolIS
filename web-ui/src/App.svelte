<script>
  import { Router } from "@roxi/routify";
  import { routes } from "../.routify/routes";
  import { setContext } from "svelte";
  import { AppContext } from "./App";

  const app = new AppContext();
  setContext("app", app);
</script>

<Router {routes} />
