---------------------------------------------------------


CS 3733 TEAM HOTSPUR FINAL PROJECT - STAR TREK MASHUP APP


---------------------------------------------------------
TWO PUBLIC URLS: 
----------------
PARTICIPANT PAGE 
https://hotspurproject.s3.us-east-2.amazonaws.com/participant.html

ADMINISTRATOR PAGE
https://hotspurproject.s3.us-east-2.amazonaws.com/admin.html

QUIRKS: 
-------
- might take a while to load local/remote segments
- if you enter a search query too quickly and remote segments haven't loaded, might get a warning message (we generate). 

How to Use: 
-----------
Participant page
- open URL 
- site will automatically load local segments and playlists
- to append a segment to a playlist, first view a playlist on the right-hand side
- to delete a segment from a playlist, click the "remove from playlist" button in the playlist next to each segment
- to view remote segments, switch to that tab. 
- to search, first allow remote segments to load (this takes a while)
- search is not case sensitive. 
- to search, enter either a character name to search by, spoken text, or both
- searching by both character and text is the most restrictive. 
- to upload a segment, enter the character name and the text spoken, select a file, and click upload
	- button will be enabled once a file is selected. 

Administrator page
- open URL 
- site will automatically load local segments 
- to register a site, type the URL and click register site
- to unregister a site, click the 'unregister' button next to the site you want to remove.  

Logistics
---------
- Works in Chrome
- Search results include any segment with matching characters in the string
	(ie, searching "no" will return segments that include the word "enough") 

____________________________________________________________________________________________________________________
What this project includes: 
---------------------------
HTML 
	participant.html
	admin.html 
JavaScript
	add remote site
	api
	append to playlist
	create playlist
	delete from playlist
	delete local segment
	delete playlist
	list local segments
	list playlists
	list remote segments
	list remote sites
	mark segment
	search segments
	unmark segment
	unregister site
	upload segment
	view playlist 

SRC Folder:
-----------
databases
	DAO
	DatabaseUtil
	PlaylistDAO
	SegmentDAO
	SiteDAO
definitions 
	Id
	Playlist
	Response
	SearchRequest
	Segment
	Segment and Playlist Request
	Site 
	remote API
		Segments Send Response
		Segments To Send
paths : lambda function handlers for each functionality in Javascript 
	playlist
	segment
	site
test cases


