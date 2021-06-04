<script>
  import { afetch } from "../../../../../utils/fetch";
  import { goto } from "@roxi/routify";

  export let id;
  let date = "2021-05-05 13:19:45";
  let capacity;
  let note;

  function save() {
    return afetch("API_ENDPOINT/v1/subjects/" + id + "/exams", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ localDateTime: date, capacity, note }),
    }).then(() => {
      $goto("/app/subjects/" + id + "/exams");
    });
  }
</script>

<div class="px-4 mt-10 sm:mt-0">
  <div class="md:grid md:gap-6">
    <div class="md:col-span-1">
      <div class="px-4 sm:px-0">
        <h3 class="text-lg font-medium leading-6 text-gray-900">
          Exam Information
        </h3>
      </div>
    </div>
    <div class="mt-5 md:mt-0 md:col-span-2">
      <form on:submit|preventDefault={save} action="#" method="POST">
        <div class="overflow-hidden shadow sm:rounded-md">
          <div class="px-4 py-5 bg-white sm:p-6">
            <div class="grid grid-cols-6 gap-6">
              <div class="col-span-6 sm:col-span-4">
                <label
                  for="date"
                  class="block text-sm font-medium text-gray-700"
                  >Date and time of the exam</label
                >
                <input
                  bind:value={date}
                  type="text"
                  name="date"
                  id="date"
                  class="block w-full mt-1 border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>

              <div class="col-span-6 sm:col-span-4">
                <label
                  for="capacity"
                  class="block text-sm font-medium text-gray-700"
                  >Capacity</label
                >
                <input
                  bind:value={capacity}
                  type="text"
                  name="capacity"
                  id="capacity"
                  class="block w-full mt-1 border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>

              <div class="col-span-6 sm:col-span-4">
                <label
                  for="note"
                  class="block text-sm font-medium text-gray-700">Note</label
                >
                <input
                  bind:value={note}
                  type="text"
                  name="note"
                  id="note"
                  class="block w-full mt-1 border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>
            </div>
          </div>
          <div class="px-4 py-3 text-right bg-gray-50 sm:px-6">
            <button
              type="submit"
              class="inline-flex justify-center px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Save
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
