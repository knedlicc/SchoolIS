<script>
    import {afetch} from "../../../utils/fetch";
    import {
        SearchIcon,
        ArrowLeftIcon
    } from "svelte-feather-icons";

    let promise = Promise.resolve([]);
    let searchText = "Search for people...";
    let isFocused = null;
    let isBlur = null;
    let searchResultShow = false;
    $: myIcon = isFocused ? ArrowLeftIcon : SearchIcon;
    let inputValue = "";
    inputValue = isBlur ? "" : inputValue;
    const inputHandler = event => {
        if (event.target.value.length > 2) {
			inputValue = event.target.value;
            fetchSearch();
			searchResultShow = true;
		} else {
			searchResultShow = false;
		}
    };
    const blurHandler = event => {
        if (event.relatedTarget == null || 
            !event.relatedTarget.className.includes("link-to-user")) {
                endFocus();
        }
    };

    function endFocus() {
        isFocused = false;
        inputValue = "";
        searchResultShow = false;
    }

    function fetchSearch() {
        promise = afetch(`API_ENDPOINT/v1/users/search/${inputValue}`).then((res)=>res.json())
    }

</script>

<div
    class="mb-8">
    <div
        class="flex flex-row w-4/6 p-8 px-5 py-2 m-auto font-light text-white rounded-lg search-bar "
        class:serach-bar-bg={isFocused}>
    <svelte:component this={myIcon} size="2x" class="icon {isFocused ? 'focused-icon' : 'blurred-icon'}"/>
    <input
            type="text"
            class="container pl-1 text-gray-500 bg-transparent"
            placeholder={searchText}
            value={inputValue}
            on:input={inputHandler}
            on:focus={() => (isFocused = true)}
            on:blur={blurHandler}/>
    </div>

    {#if searchResultShow}
        <div
            class="z-10 search-window"
            >
            {#await promise}
            <p>Loading...</p>
            {:then searchResults}
                {#if searchResults.length == 0}
                    <p>No users...</p>
                {/if}
                {#each searchResults as result}
                    <a on:click={() => {endFocus()}} href="/app/profile/{result.id}" class="text-primary link-to-user">
                        {result.firstName} {result.lastName}
                    </a>
                {/each}
            {/await}
        </div>
    {/if}
</div>

<style>
    .search-bar {
        background: rgba(0, 133, 213, 0.4);
        width: 60%;
        border-radius: 3px;
        margin-bottom: 1em;
    }

    .serach-bar-bg {
        background: white;
    }

    input:focus {
        outline: 0;
    }

    input:focus::placeholder {
        color: rgb(79, 78, 78);
    }

    input::placeholder {
        color: white;
        font-weight: bold;
    }

    @media (min-width: 1024px) {
        .search-bar {
            position: center;
            background-color: rgba(0, 133, 213, 0.4);

        }
    }

    .search-window {
        background: white;
        position: absolute;
        left: 18rem; 
        right: 0; 
        margin-left: auto; 
        margin-right: auto;
        padding: 2em 2em;
        border-radius: 15px;
        box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
        width: 54.5%;
    }

    .search-window p {
        color: #2b2b2b;
        z-index: 999999;
    }
</style>
