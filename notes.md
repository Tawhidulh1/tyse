# SpiderBot/WebCrawler

Requirements:
* Save Urls, Meta Data and Page Data
* Follow rules such as the robots.txt in websites
* Fallback on errors and long hangs. Additionally ignore duplicate pages/content.
* Rank/Prioritize urls 
* Option to stop or limit the crawler to certain numbers of pages through inputs or estimation
* Should be able to store html, but maybe other formats. 

Big Picture: 
1. Receive seed url 
2. Set up Queue for Urls | note: this should be seperate frorm the Spider
3. Download the contents/html from the URL
4. Parse it
5. Check for duplicate content. If not duplicate, store the content. 
6. Get Links from the content
7. Filter the URL and ensure it follows robots.txt 
8. Check for duplicate URL. If not duplicate store the URL. 
9. Here we need a smart Queuing system for the URL Frontier
10. Repeat


The Queue should be a priority queue with a MaxHeap by rating (atleast for now) and should be the same URL base (ie. Wikipedia.com/Orange and Wikipedia.com/Red should within the same Queue)
Each item of the Queue should also be a Priority Queue based on importance.


- [x] Create a WebPage class that stores basic information and metadata for webpages such as Url and HTML -- note I can prolly drop the Pair and Url classes
- [x] Restructure the Spider Class
- [x] Accept SeedUrl as input for Spider Class constructor
- [x] Fetch the Document of the SeedUrl (create a method)
- [] Set up parsing for robots.txt after fetching
- [] Set up caching for robots.txt after fetching
- [] Parse the document and extract URLS 

