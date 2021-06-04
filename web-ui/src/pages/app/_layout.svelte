<script>
	import Sidebar from "./_components/Sidebar.svelte";
	import Searchbar from "./_components/Searchbar.svelte"
</script>

<aside class="fixed top-0 left-0 h-full w-72 bg-bg">
	<Sidebar />
</aside>

<main class="flex-grow min-h-full pt-8 ml-72 bg-app-main">
	<Searchbar />
	<slot />
</main>
