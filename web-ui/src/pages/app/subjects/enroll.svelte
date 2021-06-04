<script>
  import { ChevronLeftIcon } from "svelte-feather-icons";
  import { listSubjects, listSemesterSubjects } from "../../../api/subject";
  import EnrollSubjectTable from "./_components/enroll-subject-table.svelte";

  const subjectsPromise = Promise.all([
    listSubjects(),
    listSemesterSubjects(),
  ]).then(([subjects, semesterSubjects]) => {
    const subjectsCodes = subjects.map((s) => s.code);
    return semesterSubjects.filter(
      (semesterSubject) => subjectsCodes.indexOf(semesterSubject.code) === -1
    );
  });
</script>

<div class="flex flex-col w-4/6 max-w-full p-8 m-auto md:w-5/7">
  <div class="relative mb-8 title">
    <div class="absolute left-0 pr-4 -mt-0.5 transform -translate-x-full ">
      <button
        class="flex items-center justify-center w-10 h-10 text-gray-400 rounded-full bg-bg"
        on:click={() => window.history.back()}
      >
        <ChevronLeftIcon size="1x" />
      </button>
    </div>

    <span>Zapsat předmět</span>
  </div>

  <div class="p-6 px-10 rounded-lg bg-bg">
    {#await subjectsPromise then subjects}
      <EnrollSubjectTable {subjects} />
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
