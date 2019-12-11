
/* Search Overview: ON SEARCH PRESS
 * 	
 * 	get JSON for list of all local segments
 * 	get JSON for list of all remote segments
 * 	get search parameter(s): character, text
 * 	
 * 	list of results: list of segments that match parameters 	
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
	var characterSearch = form.searchBarCharacter.value;
	var sentenceSearch = form.searchBarWords.value;
	
	console.log("Searching: " + characterSearch + ", " + sentenceSearch); 

	//get all local+remote segments, come in as js
	localjs = localSegsJSON; 	// this is a global variable we update on refresh.  
	// localjs.model[i] is a segment
	remotejs = remoteSegsJSON; // this is a global variable
	// remotejs.segments[i] is a segment 
	
	var localSearchResults = [];
	var remoteSearchResults = [];
	console.log("starting search results length: "+ localSearchResults.length);
	
	var addLocalCount = 0;
	// check if any local segments match search
	for (var i = 0; i < localjs.model.length; i++) {
		var curSeg = localjs.model[i]; 
		var curChar = curSeg["character"]; 
		var curText = curSeg["sentence"]; 
		
		if (curChar.includes(characterSearch)){
			console.log("I think these match:"+ curChar + ", "+ characterSearch);
		}
		if (curText.includes(sentenceSearch)){
			console.log("I think these match:"+ curText + ", "+ sentenceSearch);
		}
			if (((curChar.includes(characterSearch))&&(curText.includes(sentenceSearch)))&&(!localSearchResults.includes(curSeg))){
				// make sure no duplicates 
				localSearchResults.push(curSeg); 
				addLocalCount++; 
			}
	}
	// check if any remote segments match search
	for (var i = 0; i < remotejs.segments.length; i++) {
		var curSeg = remotejs.segments[i]; 
		var curChar = curSeg["character"]; 
		var curText = curSeg["text"]; 
		
			if (((curChar.includes(characterSearch))&&(curText.includes(sentenceSearch)))&&(!remoteSearchResults.includes(curSeg))){
				// make sure no duplicates 
				remoteSearchResults.push(curSeg); 
			
	}
	console.log("addLocalCount: " + addLocalCount); 
	console.log("local length: " + localSearchResults.length);
	// pass everything in to generate HTML. 
	processSearchResponse(localSearchResults, remoteSearchResults);
}
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
	if ((remotejs.length ==0)&&(localjs.length ==0)){
		output = output + "<p>No segments match the search criteria.</p>";
	}	  // Update computation result
	  searchResultsList.innerHTML = output;
	  
	}	
