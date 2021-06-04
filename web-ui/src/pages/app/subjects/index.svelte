<script>
  import { metatags, url } from "@roxi/routify";
  import { getContext } from "svelte";
  import { listSubjects } from "../../../api/subject";
  import IndexSubjectTable from "./_components/index-subject-table.svelte";

  metatags.title = "HELP - Předměty";

  const context = getContext("app");
  const { user } = context;
  const subjectsPromise = listSubjects();
</script>

<div class="flex flex-col w-4/6 max-w-full p-8 m-auto md:w-5/7">
  {#if $user.role === "ROLE_USER"}
    <div class="flex flex-row items-center flex-grow w-full mb-8 ">
      <div class="flex-grow title">Zapsané předměty</div>

      <a
        href={$url("./enroll")}
        class="px-4 py-1 font-semibold border-4 rounded-full border-primary"
        >Zapsat předmět</a
      >
    </div>
  {:else}
    <div class="mb-8 title">Předměty</div>
  {/if}

  <div class="p-6 px-10 rounded-lg bg-bg">
    {#await subjectsPromise then subjects}
      <IndexSubjectTable {subjects} />
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
