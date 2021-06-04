<script>
  import { getContext } from "svelte";
  import { removeSemesterStudentFromSemesterSubject } from "../../../../api/subject";
  import SubjectTable from "./subject-table.svelte";

  const context = getContext("app");
  const { user } = context;

  /** @type {import('../../../../lib/subject').Subject[]} */
  export let subjects = [];

  let disabled = false;

  /**
   * @param {number} id
   */
  function remove(id) {
    disabled = true;
    removeSemesterStudentFromSemesterSubject(id)
      .then(() => {
        subjects = subjects.filter((s) => s.id !== id);
      })
      .catch(console.error)
      .finally(() => {
        disabled = false;
      });
  }
</script>

{#if $user.role === "ROLE_USER"}
  <SubjectTable {subjects} let:subject>
    <th slot="head-last">Znamka</th>

    <td slot="body-last">
      {#if subject.mark === "NO_MARK"}
        &mdash;
      {:else}
        {subject.mark}
      {/if}
    </td>

    <button
      type="button"
      class="px-4 py-1 font-semibold border-4 rounded-full btn-remove"
      slot="button"
      {disabled}
      on:click={() => remove(subject.id)}
    >
      Zrušit
    </button>
  </SubjectTable>
{:else}
  <SubjectTable {subjects} />
{/if}

<style>
  button {
    color: #e5e5e5;
    border-color: #e5e5e5;
  }

  button:hover {
    @apply hover\:text-primary hover\:border-primary;
  }

  td {
    @apply text-center;
  }
</style>
