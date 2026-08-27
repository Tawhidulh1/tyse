async function runSearch(query) {
			history.pushState(null, '', '/search?q='+query);
      const response = await fetch('/api/search?q=' + query);
      const searchResults = await response.json();
      const results = document.createElement('div');
      for (const page of searchResults.pages) {
				var link = document.createElement('a');
				link.href = page.url;
				link.textContent = page.title;
        results.appendChild(link);
      }
      document.body.appendChild(results);
    }

document.getElementById('search').addEventListener('submit', async function(event) {
      event.preventDefault();
      const query = document.getElementById('searchInput').value;
			runSearch(query);	
});
const param = new URLSearchParams(window.location.search);
const query = param.get('q');
if (query) {
	runSearch(query);
}
