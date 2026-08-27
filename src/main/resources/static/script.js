document.getElementById('search').addEventListener('submit', async function(event) {
      event.preventDefault();
      const query = document.getElementById('searchInput').value;
      const response = await fetch('/search?q=' + query);
      const searchResults = await response.json();
      const results = document.createElement('div');
      for (const page of searchResults.pages) {
				var link = document.createElement('a');
				link.href = page.url;
				link.textContent = page.title;
        results.appendChild(link);
      }
      document.body.appendChild(results);
    });

