<script>
  import { isActive } from "@roxi/routify";
  import { ChevronLeftIcon } from "svelte-feather-icons";
  import {
    getSubject,
    listAllStudents,
    listStudentsForSubject,
  } from "../../../../api/subject";
  import StudentsTables from "../_components/StudentsTables.svelte";

  export let id;

  let promises;
  $: id &&
    (promises = Promise.all([
      getSubject(id),
      listStudentsForSubject(id),
      listAllStudents(),
    ]));
</script>

{#await promises then [subject, students, allStudents]}
  <div class="w-4/6 max-w-full p-8 m-auto md:w-5/7">
    <div class="relative mb-8 title">
      <div class="absolute left-0 pr-4 -mt-0.5 transform -translate-x-full ">
        <button
          class="flex items-center justify-center w-10 h-10 text-gray-400 rounded-full bg-bg"
          on:click={() => window.history.back()}
        >
          <ChevronLeftIcon size="1x" />
        </button>
      </div>

      <pre class="inline pr-1">{subject.code}</pre>
      {subject.name}
    </div>

    <nav class="mb-2">
      <ul class="flex flex-row">
        <li>
          <a
            href="/app/subjects/{id}"
            class="mr-4 text-xl"
            class:active={$isActive("./")}
          >
            Informace o předmětu
          </a>
        </li>

        <!-- <li>
          <a
            href="/app/subjects/{id}/materials"
            class="mr-4 text-xl"
            class:active={$isActive("./materials")}
          >
            Studijní materiály
          </a>
        </li> -->

        <li>
          <a
            href="/app/subjects/{id}/exams"
            class="mr-4 text-xl"
            class:active={$isActive("./exams")}
          >
            Termíny zkoušky
          </a>
        </li>

        <li>
          <a
            href="/app/subjects/{id}/students"
            class="mr-4 text-xl"
            class:active={$isActive("./students")}
          >
            Studenty
          </a>
        </li>
      </ul>
    </nav>

    <div class="px-10 py-8 rounded-lg bg-bg">
      <StudentsTables {subject} {students} {allStudents} />
    </div>
  </div>
{/await}

<style>
  .title {
    font-style: normal;
    font-weight: normal;
    font-size: 24px;
    line-height: 36px;
    color: #2b2b28;
  }

  nav a:not(.active) {
    opacity: 0.5;
  }
</style>
