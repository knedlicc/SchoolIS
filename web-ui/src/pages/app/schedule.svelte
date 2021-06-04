<script>
  import { metatags } from "@roxi/routify";
  import { afetch } from "../../utils/fetch";
  import { getContext } from "svelte";
  import ScheduleSubject from "./_components/ScheduleSubject.svelte";

  metatags.title = "HELP - Rozvrh";

  const context = getContext("app");
  const { user } = context;
  const schedulePromise = afetch(
    "API_ENDPOINT/v1/semester_subject_student/schedule"
  ).then((res) => res.json());

  const days = [...Array(5).keys()];
  const hours = [...Array(10).keys()];

  function findSubject(subjects, day, hour) {
    const subject = subjects[day][hour];
    return subject;
  }

  function getDayAsStr(day) {
    return ["PO", "UT", "ST", "CT", "PA"][day];
  }
</script>

<div class="w-4/6 max-w-full p-8 m-auto md:w-5/7">
  <div class="mb-8 title">Rozvrh</div>

  <div class="p-6 px-10 rounded-lg bg-bg">
    {#await schedulePromise then subjects}
      <table class="w-full table-fixed">
        <thead>
          <tr>
            <th>&nbsp;</th>
            <th>7:00</th>
            <th>8:00</th>
            <th>9:00</th>
            <th>10:00</th>
            <th>11:00</th>
            <th>12:00</th>
            <th>13:00</th>
            <th>14:00</th>
            <th>15:00</th>
            <th>16:00</th>
          </tr>
        </thead>

        <tbody>
          {#each days as day}
            <tr>
              <td>{getDayAsStr(day)}</td>

              {#each hours as hour}
                <ScheduleSubject {day} {hour} subject={findSubject(subjects, day, hour)} />
              {/each}
            </tr>
          {/each}
        </tbody>
      </table>
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

  table .btn-remove {
    color: #e5e5e5;
    border-color: #e5e5e5;
  }

  table .btn-remove:hover {
    @apply hover\:text-primary hover\:border-primary;
  }
</style>
