<script context="module">
  /** @type {string[]} */
  const hours = [
    "7:00 - 8:00",
    "8:00 - 9:00",
    "9:00 - 10:00",
    "10:00 - 11:00",
    "11:00 - 12:00",
    "12:00 - 13:00",
    "13:00 - 14:00",
    "14:00 - 15:00",
    "15:00 - 16:00",
    "16:00 - 17:00",
  ];
</script>

<script>
  import { url } from "@roxi/routify";

  export let day;
  export let hour;
  export let subject = {};
</script>

<td class="p-0 border">
  {#if subject}
    <div class="flex flex-col flex-grow h-full text-center bg-gray-100">
      <a href={$url("./subjects/" + subject.id)} class="underline text-primary">
        <pre class="inline">{subject.code}</pre>
      </a>
      <small>{hours[hour]}</small>
    </div>
  {:else}
    &nbsp;
  {/if}
</td>
