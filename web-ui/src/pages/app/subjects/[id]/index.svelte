<script>
  import { isActive, metatags } from "@roxi/routify";
  import { ChevronLeftIcon } from "svelte-feather-icons";
  import { getSubject } from "../../../../api/subject";

  metatags.title = "HELP - Předměty";

  /** @type {number} */
  export let id;

  /** @type {Promise<import('../../../../lib/subject').Subject>} */
  let subjectPromise;
  $: id && (subjectPromise = getSubject(id));
</script>

{#await subjectPromise then subject}
  <div class="w-4/6 max-w-full p-8 m-auto md:w-5/7">
    <div class="relative mb-8 title">
      <div class="absolute left-0 pr-4 -mt-0.5 transform -translate-x-full ">
        <button
          class="flex items-center justify-center w-10 h-10 text-gray-400 rounded-full bg-bg"
          on:click={() => window.history.back()}
        >
          <ChevronLeftIcon size="1x" />
        </button>
      </div>

      <pre class="inline pr-1">{subject.code}</pre>
      {subject.name}
    </div>

    <nav class="mb-2">
      <ul class="flex flex-row">
        <li>
          <a
            href="/app/subjects/{id}"
            class="mr-4 text-xl"
            class:active={$isActive("/app/subjects/" + id)}
          >
            Informace o předmětu
          </a>
        </li>

        <!-- <li>
          <a
            href="/app/subjects/{id}/materials"
            class="mr-4 text-xl"
            class:active={$isActive("./materials")}
          >
            Studijní materiály
          </a>
        </li> -->

        <li>
          <a
            href="/app/subjects/{id}/exams"
            class="mr-4 text-xl"
            class:active={$isActive("./exams")}
          >
            Termíny zkoušky
          </a>
        </li>

        <li>
          <a
            href="/app/subjects/{id}/students"
            class="mr-4 text-xl"
            class:active={$isActive("./students")}
          >
            Studenty
          </a>
        </li>
      </ul>
    </nav>

    <div class="px-10 py-8 rounded-lg bg-bg">
      <div class="grid grid-cols-6">
        <div class="pb-6 font-semibold">Způsob zakončení:</div>
        <div class="pb-6">
          {subject.completionText}
        </div>

        <div class="pb-6 font-semibold">Vyučující:</div>
        <div class="col-span-3 pb-6">
          {#each subject.tutors as tutor, i (tutor.id)}
            {#if i != 0},{/if}
            <a href="mailto:{tutor.email}" class="text-primary">
              {tutor.firstName}
              {tutor.lastName}
            </a>
          {:else}
            &mdash;
          {/each}
        </div>

        <div class="pb-6 font-semibold">Kredity:</div>
        <div class="col-span-5 pb-6">{subject.credits}</div>

        <div class="pb-6 font-semibold">Semestr výuky:</div>
        <div class="pb-6">Letní</div>

        <div class="pb-6 font-semibold">Jazyk vyuky:</div>
        <div class="col-span-3 pb-6">Český</div>

        <div class="pb-6 font-semibold">Předmět zajišťuje fakulta:</div>
        <div class="pb-6">FEL</div>

        <div class="pb-6 font-semibold">Katedra:</div>
        <div class="col-span-3 pb-6">Počítačů 13136</div>

        <div class="pb-6 font-semibold">Anotace předmětu:</div>
        <div class="col-span-5 pb-6">
          Předmět je koncipován jako základní databázový kurz, v němž je důraz
          kladen zejména na schopnost samostatného návrhu datového modelu,
          zvládnutí jazyka SQL a schopnosti zvolit vhodný stupeň izolovanosti
          transakcí. Studenti se dále seznámí s nejběžněji používanými
          technikami indexace, architekturou databázových systémů a jejich
          správou. Své poznatky si ověří při vypracování samostatné úlohy, která
          bude kontrolována v průběhu semestru ve dvou kontrolních bodech.
        </div>

        <div class="pb-6 font-semibold">Požadavky:</div>
        <div class="col-span-5 pb-6">
          Pro pochopení přednášené látky jsou nezbytné středoškolské znalosti
          matematiky, zejména pojmy funkce, zobrazení a kartézský součin. Pro
          úspěšné zvládnutí látky přednášené ve druhé polovině semestru je
          potřeba znalost základů programovacího jazyka Java.
        </div>

        <div class="pb-6 font-semibold">Zápočet:</div>
        <div class="col-span-5 pb-6">
          Zápočet bude udělen za aktivní účast na cvičeních (bude specifikováno
          jednotlivými cvičícími) a získání alepoň 8 bodů, přičemž alespoň 4x
          musí student(ka) získat kladný počet bodů (tj. maximálně jednou lze
          úkol neodevzdat nebo jej odevzdat téměř prázdný).
        </div>

        <div class="font-semibold ">Zkouška:</div>
        <div class="col-span-5 ">
          Zkouška bude mít povinnou písemnou a nepovinnou ústní část.<br />
          Z písemné části bude možné získat až 100 bodů, výsledné hodnocení je:<br
          />
          A ... 90-100 bodů<br />
          B ... 80-89 bodů<br />
          C ... 70-79 bodů<br />
          D ... 60-69 bodů<br />
          E ... 50-59 bodů<br />
          F ... 0-49 bodů.<br />
          <br />
          Získá-li student(ka) alespoň 65 bodů, může absolvovat ústní zkoušku, z
          níž může získat až 10 bodů, tj. může si zlepšit hodnocení o 1 stupeň (v
          případě perfektního výkonu výjimečně i o 2 stupně), avšak při základních
          neznalostech mu/jí může být známka snížena až na stupeň F.<br />
          Ukázková zkoušková písemka a její řešení:
        </div>
      </div>
    </div>
  </div>
{/await}

<style>
  .title {
    font-style: normal;
    font-weight: normal;
    font-size: 24px;
    line-height: 36px;
    color: #2b2b28;
  }

  nav a:not(.active) {
    opacity: 0.5;
  }
</style>
