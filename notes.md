# SpiderBot/WebCrawler

Requirements:
* Save Urls, Meta Data and Page Data
* Follow rules such as the robots.txt in websites
* Avoid being blocked by sites 
* Fallback on errors and long hangs. Additionally ignore duplicate pages/content.
* Rank/Prioritize urls 
* Option to stop or limit the crawler to certain numbers of pages through inputs or estimation
* Should be able to store html, but maybe other formats. 

Big Picture: 
1. Receive seed url 
2. Set up Queue for Urls
3. Download the contents/html from the URL
4. Parse it
5. Check for duplicate content. If not duplicate, store the content. 
6. Get Links from the content
7. Filter the URL and ensure it follows robots.txt 
8. Check for duplicate URL. If not duplicate store the URL. 
9. Here we need a smart Queuing system for the URL Frontier
10. Repeat



- [x] Initialize git
- [x] Intilialize project
- [] Accept seed URL input
- [] Get Ranking of sites
- [] Organize Sites into neat queue based on ranking and same site root
- [] 



