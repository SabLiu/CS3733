
/* Search Overview: ON SEARCH PRESS
 * 	
 * 	get JSON for list of all local segments
 * 	get JSON for list of all remote segments
 * 	get search parameter(s): character, text
 * 	
 * 	list of local results: list of local segments that match parameters 	
 * 	list of remote results: list of remote segments that match parameters 	
 *  (they have slightly different JSONs, so we need to access them for HTML differently)	
 * 
 * 	search segment characters for character
 * 	search segment text for text
 * 	
 * 	return list of results. 
 * 	
 */

function processSearch() {
	// grab user-entered data from form
	var form = document.searchForm;
	var characterSearch = form.searchBarCharacter.value.toLowerCase();
	var sentenceSearch = form.searchBarWords.value.toLowerCase();
	
	console.log("Searching: " + characterSearch + ", " + sentenceSearch); 
	console.log("Searching  " + remoteSegsJSON.length + " sites"); 

	//get all local+remote segments, come in as js
	localjs = localSegsJSON; 	// this is a global variable
	// local js is an array of local segments
	// localjs.model[i] is a segment
	remotejs = remoteSegsJSON; // this is a global variable
	console.log("GLOBAL REMOTE SITES JSON " + remoteSegsJSON);
	console.log("num of remote sites: " + remoteSegsJSON.length);
	console.log("a site" + remoteSegsJSON[0]);
	console.log("a segment: " + remoteSegsJSON[0].segments[0]);
	// remotejs is an array of JSONs 
	// remotejs[i].segments[i] is a segment 
	
	var localSearchResults = [];
	var remoteSearchResults = [];
	
	if(localjs.model != null){
		// check if any local segments match search
		for (var i = 0; i < localjs.model.length; i++) {
			var curSeg = localjs.model[i]; 
			var curChar = curSeg["character"].toLowerCase(); 
			var curText = curSeg["sentence"].toLowerCase(); 
			
				if (((curChar.includes(characterSearch))&&(curText.includes(sentenceSearch)))&&(!localSearchResults.includes(curSeg))){
					// make sure no duplicates 
					localSearchResults.push(curSeg); 
				}
		}
	}
	
	if (remotejs.length > 0){
		// for each remote site: 
		for (var j = 0; j < remotejs.length; j++){
			var currentRemSite = remotejs[j]; 
			console.log("Searching Site  #: " + j);
			console.log("currentSiteSegments list: "+ currentRemSite);
			if(remotejs.segments != null){
			// for each segment in the remote site: 
				for (var i = 0; i < currentRemSite.segments.length; i++) {
					console.log("for each segment");
				var curSeg = currentRemSite.segments[i]; // this is a remote segment 
				var curChar = curSeg["character"].toLowerCase(); 
				var curText = curSeg["text"].toLowerCase(); 
			
					if (((curChar.includes(characterSearch))&&(curText.includes(sentenceSearch)))&&(!remoteSearchResults.includes(curSeg))){
					// make sure no duplicates 
						remoteSearchResults.push(curSeg); 
					
					}
				}
			}
		}
	}
	//console.log("addLocalCount: " + addLocalCount); 
	//console.log("local length: " + localSearchResults.length);
	// pass everything in to generate HTML. 
	processSearchResponse(localSearchResults, remoteSearchResults);

}

// analyze search results 
// can't just reuse function in list_local_segments because need to generate different buttons 

// searchResults is a JSON: list of segments 
// need to pass in 2 arrays because the JSON naming conventions are different 

function processSearchResponse(localSearchResults, remoteSearchResults) {
	var localjs = localSearchResults; //JSON.parse(localSearchResults);
	var remotejs = remoteSearchResults; // JSON.parse(remoteSearchResults);
	
	var searchResultsList = document.getElementById('searchResultsList');
	  if(searchResultsList == null){
		  console.log("IN CSS FILE");
		  searchResultsList = document.getElementById('segmentsColumn');
	  }
	 
	var output = "";
	
	if(initalizing > 3){
		output = output + "<p>Warning, remote sites not fully loaded. For complete search results please try again in 10 seconds.</p><p>&nbsp;</p>"
	}
	console.log("Trying to print search results: " + localjs.length + remotejs.length);
	if (localjs.length > 0){
	// generate HTML for local segments in search result
	for (var i = 0; i < localjs.length; i++) {
		//grabs stuff out of json
		var localSRJson = localjs[i];
	    
	    var segURL 			= localSRJson["url"];
	    var sent			= localSRJson["sentence"];
	    var character 		= localSRJson["character"];

	    output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
	    output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
	    output = output + "<p><input type=\"button\" value=\"Append to current playlist\" onClick=\"JavaScript:processAppendToPlaylist('" + segURL + "')\"/></p></br>"; 
	  }
	}
	if (remotejs.length > 0){
		
	
	for (var i = 0; i < remotejs.length; i++) {
		//grabs stuff out of json
		var remoteSRJson = remotejs[i];
	    
	    var segURL 			= remoteSRJson["url"];
	    var sent			= remoteSRJson["text"];
	    var character 		= remoteSRJson["character"];

	    output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
	    output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
	    output = output + "<p><input type=\"button\" value=\"Append to current playlist\" onClick=\"JavaScript:processAppendToPlaylist('" + segURL + "')\"/></p></br>"; 
	  }
	}
	
	if ((remotejs.length ==0)&&(localjs.length ==0)) {
		output = output + "<p>No segments match the search criteria.</p>";
	}	  // Update computation result
	  searchResultsList.innerHTML = output;
	  
	}	
