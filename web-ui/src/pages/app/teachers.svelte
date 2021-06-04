<script>
  import { metatags } from "@roxi/routify";
  import { afetch } from "../../utils/fetch";
  import TeachersTable from "./_components/TeachersTable.svelte";

  metatags.title = "HELP - Učitele";

  const teachersPromise = afetch("API_ENDPOINT/v1/users/teachers").then((res) =>
    res.json()
  );
</script>

<div class="w-4/6 max-w-full p-8 m-auto md:w-5/7">
  <div class="mb-8 title " style="font-size: 24px">Učitele</div>

  <div class="p-6 px-10 rounded-lg bg-bg">
    {#await teachersPromise then teachers}
      <TeachersTable {teachers} />
    {/await}
  </div>
</div>

<style>
  .title {
    font-style: normal;
    font-weight: normal;
    font-size: 24px;
    line-height: 36px;
    color: #2b2b28;
  }
</style>
