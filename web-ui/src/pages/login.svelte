<script>
  import { metatags } from "@roxi/routify";
  import { getContext } from "svelte";
  import { createForm } from "svelte-forms-lib";
  import * as yup from "yup";
  metatags.title = "HELP - Login";
  // metatags.description = "Description coming soon...";

  /** @type {import('../App.js').AppContext} */
  const appContext = getContext("app");

  let isLoading = false;
  let isError = false;

  const { form, errors, handleChange, handleSubmit } = createForm({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: yup.object().shape({
      email: yup.string().email().required(),
      password: yup.string().required(),
    }),
    onSubmit: ({ email, password }) => {
      isLoading = true;

      appContext
        .login(email, password)
        .then(() => {
          isError = false;
        })
        .catch((error) => {
          isError = true;
          // show some fancy-ass notification that login has failed
          window.alert(
            "Authentication failed, user does not exist or password is incorrect."
          );
        })
        .finally(() => {
          isLoading = false;
        });
    },
  });
</script>

<div class="flex flex-col h-screen max-w-full m-auto w-96">
  <div class="flex justify-center flex-grow">
    <div class="flex flex-col self-center">
      <header class="mb-8 text-center">Přihlaste se</header>

      <form
        on:submit|preventDefault={handleSubmit}
        class="flex flex-col px-10 py-8 card bg-bg"
        class:error={isError}
      >
        {#if isError}
          <span class="mb-8 text-center text-error">Chyba vstupu!</span>
        {/if}

        <div class="flex flex-col mb-4">
          <input
            id="email"
            name="email"
            class="px-6 py-2"
            class:error={$errors.email}
            type="email"
            placeholder="email..."
            autocomplete="email"
            disabled={isLoading}
            on:change={handleChange}
            on:blur={handleChange}
            bind:value={$form.email}
          />
          {#if $errors.email}
            <small class="text-error">{$errors.email}</small>
          {/if}
        </div>

        <div class="flex flex-col mb-8">
          <input
            id="password"
            name="password"
            autocomplete="current-password"
            class="px-6 py-2"
            class:error={$errors.password}
            type="password"
            placeholder="heslo..."
            disabled={isLoading}
            on:change={handleChange}
            on:blur={handleChange}
            bind:value={$form.password}
          />
          {#if $errors.password}
            <small class="text-error">{$errors.password}</small>
          {/if}
        </div>

        <div class="flex justify-center">
          <button type="submit" class="px-6 py-2" disabled={isLoading}
            >přihlasit se</button
          >
        </div>
      </form>
    </div>
  </div>

  <div class="flex justify-center pb-8">
    <picture class="logo">
      <source srcset="/logo_cvut_en.jpg" />
      <img src="/logo_cvut_en.svg" alt="FEL LOGO" />
    </picture>
  </div>
</div>

<style>
  header {
    font-style: normal;
    font-weight: normal;
    font-size: 36px;
    line-height: 54px;

    /* #0065BD */
    color: #0065bd;
  }

  .card {
    /* #F2F6FC */
    border: 5px solid #f2f6fc;
    box-sizing: border-box;
    border-radius: 30px;
  }

  .card.error {
    border: 5px solid #f4a0a0;
  }

  .card input {
    /* #F2F6FC */
    background: #f2f6fc;
    border-radius: 1.5rem;

    /* Text */
    font-style: normal;
    font-weight: normal;
    font-size: 14px;
    line-height: 21px;

    color: black;
  }

  .card input.error {
    background: rgba(232, 44, 44, 0.05);
  }

  .card button {
    background: #0065bd;
    border-radius: 1.5rem;

    /* Text */
    font-style: normal;
    font-weight: 600;
    font-size: 16px;
    line-height: 24px;
    text-transform: lowercase;

    color: #ffffff;
  }

  .card button:disabled {
    opacity: 0.5;
  }

  .card small {
    font-style: normal;
    font-weight: normal;
    font-size: 14px;
    line-height: 21px;
  }

  .logo {
    width: 40%;
  }
</style>
