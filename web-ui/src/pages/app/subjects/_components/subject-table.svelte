<script>
  import { url } from "@roxi/routify";

  /** @type {import('../../../../lib/subject').Subject[]} */
  export let subjects = [];
</script>

<table class="w-full">
  <thead>
    <tr>
      <th>Předmět</th>
      <th>Role</th>
      <th>Kredity</th>
      <th>Zakončení</th>
      <slot name="head-last" />
      <th>&nbsp;</th>
    </tr>
  </thead>

  <tbody>
    {#each subjects as subject (subject.id)}
      <tr>
        <td>
          <a href={$url("./" + subject.id)} class="text-primary">
            <pre class="inline pr-2">{subject.code}</pre>
            {subject.name}
          </a>
        </td>
        <td>{subject.typeText}</td>
        <td>{subject.credits}</td>
        <td>{subject.completionText}</td>
        <slot name="body-last" {subject} />
        <td class="">
          <slot name="button" {subject}>&nbsp;</slot>
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
  table tbody tr:not(:last-of-type) {
    @apply border-b-2;
  }

  table tbody tr td:not(:first-of-type) {
    @apply text-center;
  }
</style>
