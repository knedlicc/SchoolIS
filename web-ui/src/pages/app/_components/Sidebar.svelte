<script>
  import { url, isActive } from "@roxi/routify";
  import { getContext } from "svelte";
  import {
    UserIcon,
    CalendarIcon,
    BookmarkIcon,
    EditIcon,
    CheckSquareIcon,
    LogOutIcon,
    UsersIcon,
    SettingsIcon,
  } from "svelte-feather-icons";

  /** @type {import('../../../App.js').AppContext} */
  const appContext = getContext("app");
  const { user } = appContext;

  let items = [];

  switch ($user.role) {
    case "ROLE_USER":
      items = [
        { url: "./index", title: "Profil", icon: UserIcon },
        { url: "./schedule", title: "Rozvrh", icon: CalendarIcon },
        { url: "./subjects", title: "Předměty", icon: BookmarkIcon },
        // { url: "./homework", title: "Domací úkoly", icon: EditIcon },
        // { url: "./exams", title: "Zápočty a zkoušky", icon: CheckSquareIcon },
        { url: "./teachers", title: "Učitele", icon: UsersIcon },
      ];
      break;

    case "ROLE_TEACHER":
      items = [
        { url: "./index", title: "Profil", icon: UserIcon },
        // { url: "./schedule", title: "Rozvrh", icon: CalendarIcon },
        { url: "./subjects", title: "Předměty", icon: BookmarkIcon },
        // { url: "./exams", title: "Zkoušky", icon: CheckSquareIcon },
        { url: "./teachers", title: "Učitele", icon: UsersIcon },
      ];
      break;

    case "ROLE_EMPLOYEE":
      items = [
        { url: "./index", title: "Profil", icon: UserIcon },
        { url: "./subjects", title: "Předměty", icon: BookmarkIcon },
        // { url: "./students", title: "Studenti", icon: UsersIcon },
        { url: "./teachers", title: "Učitele", icon: UsersIcon },
        { url: "./usermng", title: "User Management", icon: SettingsIcon },
      ];
      break;
  }
</script>

<div class="flex flex-col h-full">
  <div class="flex justify-center py-8">
    <picture class="logo">
      <source srcset="/logo_cvut_en.jpg" />
      <img src="/logo_cvut_en.svg" alt="FEL LOGO" />
    </picture>
  </div>

  <nav class="flex flex-grow">
    <ul class="w-full">
      {#each items as item}
        <li>
          <a
            href={$url(item.url)}
            class:active={$isActive(item.url)}
            class="flex flex-row items-center px-4 py-3 border-transparent"
          >
            <svelte:component this={item.icon} size="1.75x" />
            <span>{item.title}</span>
          </a>
        </li>
      {/each}
    </ul>
  </nav>

  <button
    on:click={() => appContext.logout()}
    class="flex flex-row justify-center logout"
  >
    <div class="flex flex-row items-center p-4">
      <LogOutIcon size="1.75x" />
      <span>Log out</span>
    </div>
  </button>
</div>

<style>
  .logo {
    width: 80%;
  }

  li a {
    border-width: 0 5px 0 0;
  }

  li a.active {
    background-color: rgba(0, 101, 189, 0.1);
    border-color: #0065bd;
  }

  li a span {
    font-style: normal;
    font-weight: 600;
    font-size: 16px;
    line-height: 24px;

    /* #2B2B28 */
    color: #2b2b28;

    padding-left: 1rem;
  }

  .logout {
    font-style: normal;
    font-weight: 600;
    font-size: 16px;
    line-height: 24px;
    /* identical to box height */

    /* #2B2B28 */
    color: #2b2b28;
  }

  .logout span {
    padding-left: 1rem;
  }
</style>
