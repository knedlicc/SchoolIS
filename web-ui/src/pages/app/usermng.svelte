<script>
  import {metatags} from "@roxi/routify";
  import {afetch} from "../../utils/fetch";


  metatags.title = "HELP - User Management";

  let email = "";
  let name = "";
  let surname = "";
  let pass = "";
  let role = "";
  let isEmptyEmail = false;
  let isEmptyPassword = false;
  let isEmptyRole = false;
  let isError = false;
  let errMsg = "";
  let added = false;
  let emailExist = false;
  let deleted = false;
  let updated = false;



  function handleFind(email) {
    emailExist = false;
    added = false;
    updated = false;
    deleted = false;

    if (email === ""){
      isEmptyEmail = true;
      return;
    }
    isEmptyEmail = false;
    const promise = afetch("API_ENDPOINT/v1/users/" + email+"/")
      .then((res) => {
        isError = false;
        return res.json();
      })
      .catch((error) => {
        isError = true;
        errMsg = email;
        isEmptyEmail = true;

      })
    .then((res) =>
    {
      email = res.email;
      name = res.firstName;
      surname = res.lastName;
      role = res.role.replace('ROLE_','');
    })
  }

  function handleAdd(){
    updated = false;
    deleted = false;
    emailExist = false;
    let err = false;
    if((email === "")){
      isEmptyEmail = true;
      err = true;
    } else {
      isEmptyEmail = false;
    }
    if( (pass === "")){
      isEmptyPassword = true;
      err = true;
    } else{
      isEmptyPassword = false;
    }
    if( role === ""){
      isEmptyRole = true;
      err = true;
    } else {
      isEmptyRole = false;
    }
    if(err) {
      added = false;
      return;
    }

    let raw = JSON.stringify({
      "email":email,
      "name":name,
      "surname": surname,
      "password": pass,
      "role":role
    });

    let requestOptions = {
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: raw,
    };

    isEmptyEmail = false;
    isEmptyRole = false;
    isEmptyPassword = false;

    const promise = afetch("API_ENDPOINT/v1/users/addUser",requestOptions)
      .then((res) => {
        if(res.status === 200){
          added = true;
          emailExist = false;
        }
        res.json()
      })
      .catch((error)=> {
        emailExist = true;
      })
  }


  function handleUpdate(){
    deleted = false;
    emailExist = false;
    added = false;
    if(email === ""){
      isEmptyEmail = true;
      return;
    }

    isEmptyEmail = false;

    let raw = JSON.stringify({
      "email":email,
      "name":name,
      "surname": surname,
      "password": pass === '' ? null : pass,
      "role": role,
    });


    let requestOptions = {
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: raw,
    };
    const promise = afetch("API_ENDPOINT/v1/users/update",requestOptions)
      .then((res) => {
        if(res.status === 200){
          updated = true;
        }
        isError = false;
        return res.json();
      })
      .catch((error) => {
        isError = true;
        errMsg = email;
        isEmptyEmail = true;
      })
      .then((res) =>
      {
        console.log(res);
        email = res.email;
        name = res.name;
        surname = res.surname;
        role = res.role.replace('ROLE_','');
      })

  }

  function handleRemove() {
    updated = false;
    emailExist = false;
    added = false;
    if (email === ""){
      isEmptyEmail = true;
      return;
    }
    isEmptyEmail = false;

    const promise = afetch("API_ENDPOINT/v1/users/" + email+"/", {
      method: "DELETE",
    })
      .then((res) => {
        if(res.status === 200){
          deleted = true;
          isError = false;
          name = "";
          surname = "";
          pass = "";
          role = "";
        }
        return res.json();
      })
    .catch((error) => {
      isError = true;
      errMsg = email;
      isEmptyEmail = true;
    })
  }


</script>

<div class="w-4/6 max-w-full p-8 m-auto md:w-5/7">
  <div class="mb-8 title text-2xl" style="font-style: normal;
      font-weight: normal;
      font-size: 24px;
      line-height: 36px;
      color: #2b2b28;">
    User Management
  </div>
<div class="p-8 m-auto rounded-lg bg-bg min-w-64">
  <div class="">
  <label  class="block mt-3 text-sm text-gray-700  font-semibold">

  </label>
  <form method="#" action="#" >
    <div class="m-auto">
      {#if isError}
        <span class="text-red-500">
          Chyba, User with email: {errMsg} does not exist
        </span>
        {/if}
      {#if added}
        <span class="text-green-500">
            User with email: {email} successfully added
        </span>
        {/if}
      {#if emailExist}
        <span class="text-red-500">
            Chyba, check if the email already exist -> click Find User by email
        </span>
        {/if}
      {#if updated}
        <span class="text-green-500">
            User with email: {email} was successfully updated
        </span>
        {/if}
      {#if deleted}
        <span class="text-green-500">
            User with email: {email} was successfully deleted
        </span>
      {/if}
    <div >
      <label>
        <input bind:value={email} id="email" type="email" placeholder="Email" class="mt-1 block w-full p-4  border-none bg-gray-100 h-11 rounded-xl shadow-lg hover:bg-blue-100 focus:bg-blue-100 focus:ring-0 outline-none {isEmptyEmail ? 'bg-red-200' : '' } " >
      </label>
    </div>

    <div class="mt-7">
      <label>
        <input bind:value={name} type="text" placeholder="Name" class="mt-1 p-4  block w-full border-none bg-gray-100 h-11 rounded-xl shadow-lg hover:bg-blue-100 focus:bg-blue-100 focus:ring-0 outline-none">
      </label>
    </div>
    <div class="mt-7">
      <label>
        <input bind:value={surname} type="text" placeholder="Surname" class="mt-1  p-4 block w-full border-none bg-gray-100 h-11 rounded-xl shadow-lg hover:bg-blue-100 focus:bg-blue-100 focus:ring-0 outline-none">
      </label>
    </div>
    <div class="mt-7">
      <label>
        <input bind:value={pass} type="text" placeholder="Password" class="mt-1 p-4  block w-full border-none bg-gray-100 h-11 rounded-xl shadow-lg hover:bg-blue-100 focus:bg-blue-100 focus:ring-0 outline-none {isEmptyPassword ? 'bg-red-200' : '' }">
      </label>
    </div>
    <div class="mt-7">
      <label >
        <select  bind:value={role} class="px-3  block w-full border-none bg-gray-100 h-11 rounded-xl shadow-lg hover:bg-blue-100 focus:bg-blue-100 focus:ring-0 outline-none {isEmptyRole ? 'bg-red-200' : '' }">
          <option value="" disabled selected hidden>Choose role:</option>
          <option value="TEACHER">Teacher</option>
          <option value="USER">Student</option>
          <option value="EMPLOYEE">Employee</option>
        </select>
      </label>
    </div>
    </div>




    <div class="flex mt-7 items-center text-center">
      <hr class="border-gray-300 border-1 w-full rounded-md">
      <label class="block font-medium text-sm text-gray-600 w-full">
        Operations
      </label>
      <hr class="border-gray-300 border-1 w-full rounded-md">
    </div>

    <div class="flex mt-7 justify-center w-full">
      <button type="button" on:click={handleFind(email)} class="focus:outline-none mr-5 bg-yellow-500 border-none px-4 py-2 rounded-xl cursor-pointer text-white shadow-xl hover:shadow-inner transition duration-500 ease-in-out  transform hover:-translate-x hover:scale-105">

        Find user by email
      </button>
      <button type="button" on:click={handleAdd} class="focus:outline-none mr-5 bg-green-500 border-none px-4 py-2 rounded-xl cursor-pointer text-white shadow-xl hover:shadow-inner transition duration-500 ease-in-out  transform hover:-translate-x hover:scale-105">

        Add user
      </button>

      <button type="button" on:click= {handleUpdate} class="focus:outline-none mr-5 bg-blue-500 border-none px-4 py-2 rounded-xl cursor-pointer text-white shadow-xl hover:shadow-inner transition duration-500 ease-in-out  transform hover:-translate-x hover:scale-105">

        Update user
      </button>

      <button type="button" on:click={handleRemove} class="focus:outline-none bg-red-500 border-none px-4 py-2 rounded-xl cursor-pointer text-white shadow-xl hover:shadow-inner transition duration-500 ease-in-out  transform hover:-translate-x hover:scale-105">

        Delete user
      </button>
    </div>


  </form>

  </div>
</div>
</div>

