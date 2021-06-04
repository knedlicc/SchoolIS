<script>
  import { url } from "@roxi/routify";
  import { afetch } from "../../../utils/fetch";

  export let teachers = [];

  export let filter = "";

  let displayTeachers = teachers;
  $: displayTeachers = teachers.filter((t) =>
    (t.firstName + t.lastName).toLowerCase().includes(filter.toLowerCase())
  );
</script>

<table class="w-full">
  <thead>
    <tr>
      <th>Učitel</th>
      <th>Email</th>
      <th>Předměty</th>
    </tr>
  </thead>

  <tbody>
    {#each displayTeachers as teacher}
      <tr>
        <td>
          {teacher.firstName}
          {teacher.lastName}
        </td>
        <td>
          <a class="text-primary" href="mailto:{teacher.email}"
            >{teacher.email}</a
          >
        </td>
        <td class="space-y-1">
          {#await afetch("API_ENDPOINT/v1/subjects/teachingList/" + teacher.id).then(
            (res) => res.json()
          ) then subjects}
            {#each subjects as subject}
              <a
                href={$url("./subjects/" + subject.id)}
                class="block subject text-primary"
              >
                <pre class="inline-block">{subject.code}</pre>
                <span class="underline">{subject.name}</span>
              </a>
            {/each}
          {/await}
        </td>
      </tr>
    {/each}
  </tbody>
</table>

<style>
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

  .subject:not(:last-of-type) {
    @apply border-b-1;
  }
</style>
