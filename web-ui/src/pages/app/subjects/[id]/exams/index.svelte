<script>
  import { getContext } from "svelte";
  import { afetch } from "../../../../../utils/fetch";

  const appContext = getContext("app");
  const { user } = appContext;

  export let id;

  const examsPromise = afetch(
    "API_ENDPOINT/v1/subjects/" + id + "/exams"
  ).then((res) => res.json());
</script>

<div class="w-4/6 max-w-full p-8 m-auto md:w-5/7">
  <div class="w-4/6 p-8 m-auto rounded-lg bg-bg min-w-64">
    <div class="mb-8 title " style="font-size: 24px">Termíny</div>
    <div class="p-6 px-10 rounded-lg bg-bg">
      {#await examsPromise then exams}
        <table class="w-full">
          <thead>
            <tr>
              <th>Datum</th>
              <th>Kapacita</th>
              <th>Poznamka</th>
            </tr>
          </thead>

          <tbody>
            {#each exams as exam}
              <tr>
                <td>
                  {exam.localDateTime}
                </td>
                <td>{exam.capacity}</td>
                <td>
                  {exam.note}
                </td>
              </tr>
            {/each}
          </tbody>
        </table>
      {/await}
    </div>

    {#if $user.role === "ROLE_TEACHER"}
      <a href="/app/subjects/{id}/exams/newExam">
        <button
          type="button"
          class="px-4 py-1 font-semibold border-4 rounded-full btn-remove"
        >
          Nový termín
        </button>
      </a>
    {/if}
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

  table th:first-of-type {
    @apply text-left;
  }

  table tr > * {
    @apply py-4 px-1;
  }

  table thead tr,
  table tbody tr:not(:last-of-type) > * {
    @apply border-b-2;
  }

  table tbody tr td:not(:first-of-type) {
    @apply text-center;
  }
</style>
