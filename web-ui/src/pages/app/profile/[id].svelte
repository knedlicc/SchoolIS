<script>
  import { metatags } from "@roxi/routify";
  import {afetch} from "../../../utils/fetch";
  metatags.title = "HELP - Profil";

  export let id;

  let userPromise;
  $: id && (userPromise = getUserData(id));

  function getUserData(id) {
    return afetch(`API_ENDPOINT/v1/users/${id}`).then((res) => res.json())
  }

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
  {:catch error}
    <p>Uživatel nenalezen</p>
  {/await}

</div>

