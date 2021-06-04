<script>
  import { metatags } from "@roxi/routify";
  import { getContext } from "svelte";
  import {afetch} from "../../utils/fetch";
  metatags.title = "HELP - Profil";

  const app = getContext("app");
  const { user } = app;

  const userPromise = afetch("API_ENDPOINT/v1/users")
    .then((res) => res.json());

</script>

<div class="w-4/6 p-8 m-auto rounded-lg bg-bg min-w-64">
  {#await userPromise then currentUser}
    <div class ="p-6 px-10 rounded-lg bg-bg">
    <div class="mb-8 title " style="font-size: 24px">Osobní profil</div>
    <table class="w-full" >
      <tr>
        <th>Jméno: </th> {currentUser.firstName}
        <th>Email: </th> {currentUser.email}
      </tr>
      <tr>
        <th>Příjmení: </th> {currentUser.lastName}
        <th> Telefonní číslo: </th>
        {#if currentUser.phoneNumber !== undefined }
          {currentUser.phoneNumber}
        {:else}
          <p> - </p>
        {/if}
      </tr>
      <tr>
        <th>Datum narození: </th>
        {#if currentUser.phoneNumber !== undefined }
          {currentUser.birthDate}
        {:else}
          <p> - </p>
        {/if}
      </tr>
    </table>
    </div>

    {#if $user.role === "ROLE_TEACHER"}
      <div class="p-6 px-10 rounded-lg bg-bg">

          {#await afetch(`API_ENDPOINT/v1/subjects/teachingList/${currentUser.id}`).then((res)=>res.json()) then subjects}
              <table class="w-full">
                <tr> <th>Předměty: </th>
                {#each subjects as subject}
                <p>{subject.name}</p>
              {/each}
              </tr>
              </table>
          {/await}

      </div>
    {/if}
  {/await}

</div>

