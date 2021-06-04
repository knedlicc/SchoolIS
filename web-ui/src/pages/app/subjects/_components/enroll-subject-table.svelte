<script>
  import { enrollIntoSubject } from "../../../../api/subject";
  import SubjectTable from "./subject-table.svelte";

  /** @type {import('../../../../lib/subject').Subject[]} */
  export let subjects = [];

  let disabled = false;

  /**
   * @param {import('../../../../lib/subject').Subject} subject
   */
  function enroll(subject) {
    disabled = true;
    enrollIntoSubject(subject.id)
      .then(() => {
        subjects = subjects.filter((s) => s.id !== subject.id);
      })
      .catch(console.error)
      .finally(() => {
        disabled = false;
      });
  }
</script>

<SubjectTable {subjects}>
  <button
    type="button"
    class="px-4 py-1 font-semibold border-4 rounded-full"
    slot="button"
    let:subject
    {disabled}
    on:click={() => enroll(subject)}
  >
    Zapsat
  </button>
</SubjectTable>

<style>
  button {
    color: #e5e5e5;
    border-color: #e5e5e5;
  }

  button:hover {
    @apply hover\:text-primary hover\:border-primary;
  }
</style>
