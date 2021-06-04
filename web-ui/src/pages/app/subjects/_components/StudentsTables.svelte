<script context="module">
  const marks = ["A", "B", "C", "D", "E", "NO_MARK"];
</script>

<script>
  import { getContext } from "svelte";
  import {
    addMarkToStudent,
    addSemesterStudentToSemesterSubject,
    removeSemesterStudentFromSemesterSubjectByEmployee,
  } from "../../../../api/subject";

  const appContext = getContext("app");
  const { user } = appContext;

  export let subject = {};
  export let students = [];
  export let allStudents = [];

  $: allStudents = allStudents.filter((s) => !isAlreadyEnrolled(s));

  function markChanged(subject, student, e) {
    addMarkToStudent(subject.semesterSubjectId, student.id, e.target.value);
  }

  function removeStudent(subject, student) {
    removeSemesterStudentFromSemesterSubjectByEmployee(
      subject.semesterSubjectId,
      student.id
    ).then(() => {
      students = students.filter((s) => s.id !== student.id);
      allStudents = [...allStudents, student];
    });
  }

  function addStudent(subject, student) {
    addSemesterStudentToSemesterSubject(
      subject.semesterSubjectId,
      student.id
    ).then(() => {
      students = [...students, student];
      allStudents = allStudents.filter((s) => s.id !== student.id);
    });
  }

  function isAlreadyEnrolled(s) {
    for (const student of students) {
      if (student.id === s.id) return true;
    }
    return false;
  }
</script>

<table class="w-full table-fixed">
  <thead>
    <tr>
      <th>Jmeno</th>
      <th>Email</th>
      {#if $user.role === "ROLE_TEACHER"}
        <th>Znamka</th>
      {/if}
      {#if $user.role === "ROLE_EMPLOYEE"}
        <th>&nbsp;</th>
      {/if}
    </tr>
  </thead>

  <tbody>
    {#each students as student (student.id)}
      <tr>
        <td>
          {student.firstName}
          {student.lastName}
        </td>

        <td>
          <a href="mailto:{student.email}" class="underline text-primary">
            {student.email}
          </a>
        </td>

        {#if $user.role === "ROLE_TEACHER"}
          <td>
            <!-- svelte-ignore a11y-no-onchange -->
            <select
              name="mark"
              value={student.mark}
              on:change={(e) => markChanged(subject, student, e)}
            >
              {#each marks as mark}
                <option value={mark}>
                  {#if mark === "NO_MARK"}
                    &mdash;
                  {:else}
                    {mark}
                  {/if}
                </option>
              {/each}
            </select>
          </td>
        {/if}

        {#if $user.role === "ROLE_EMPLOYEE"}
          <td>
            <button
              type="button"
              class="px-4 py-1 font-semibold border-4 rounded-full hover:border-primary"
              on:click={() => removeStudent(subject, student)}
            >
              Zrusit
            </button>
          </td>
        {/if}
      </tr>
    {/each}
  </tbody>
</table>

{#if $user.role === "ROLE_EMPLOYEE"}
  <hr class="my-4" />

  <h2 class="text-xl font-semibold">Pridat studenty</h2>

  <table class="w-full table-fixed">
    <thead>
      <tr>
        <th>Jmeno</th>
        <th>Email</th>
        <th>&nbsp;</th>
      </tr>
    </thead>

    <tbody>
      {#each allStudents as student (student.id)}
        <tr>
          <td>
            {student.firstName}
            {student.lastName}
          </td>

          <td>
            <a href="mailto:{student.email}" class="underline text-primary">
              {student.email}
            </a>
          </td>

          <td>
            <button
              type="button"
              class="px-4 py-1 font-semibold border-4 rounded-full hover:border-primary"
              on:click={() => addStudent(subject, student)}
            >
              Pridat
            </button>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
{/if}

<style>
  table th:first-of-type {
    @apply text-left;
  }

  table tr > * {
    @apply py-4 px-1;
  }

  table thead tr,
  table tbody tr:not(:last-of-type) {
    @apply border-b-2;
  }

  table tbody tr td:not(:first-of-type) {
    @apply text-center;
  }

  table tbody tr td:last-of-type {
    @apply text-right;
  }
</style>
