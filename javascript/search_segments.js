
// this takes in the global variable (which has been changed by refreshSegmentsList())
// only when "search" button is pressed 

function processSearch() {
	// grab user-entered data from form
	var form = document.searchForm;
	var characterSearch = form.searchBarCharacter.value;
	var sentenceSearch = form.searchBarWords.value;

	//get all segments from js
	js = currentPLJS; // this is a global variable we update on refresh.  
	//iterate through videos
	 
	  for (var i = 0; i < js.model.length; i++) { // model is a list of segments
		//grabs stuff out of json
		var localSegsJson = js.model[i];
	    console.log(localSegsJson);
	    
	    var sent			= localSegsJson["sentence"];
	    var character 		= localSegsJson["character"];	   
	    
	    var data = {};
	    
	    //find which Characters include the string which was searched
	    // make sure not to pass in "character" field if it's empty
	    // (this makes the JSON :( )
	    if (characterSearch != "") {
	    	if (character.includes(characterSearch)) {
	    		data["characterKeyphrase"] = character;
	    	} else {
	    		data["characterKeyphrase"] = characterSearch;
	    	}
	    }

		//find which Sentences include the string which was searched
	    
	    if (sent.includes(sentanceSearch)) {
	    	data["sentenceKeyphrase"] = sent;
	    } else {
	    	data["sentenceKeyphrase"] = sentenceSearch;
	    }
  
	    var js = JSON.stringify(data);
	    console.log("JS:" + js);
	    var xhr = new XMLHttpRequest();
	    xhr.open("POST", search_url, true);  
	    // send the collected data as JSON
	    xhr.send(js);

	    // This will process results and update HTML as appropriate. 
	    xhr.onloadend = function () {
	    	console.log(xhr);
	    	console.log(xhr.request);
	    	if (xhr.readyState == XMLHttpRequest.DONE) {
	    		if (xhr.status == 200) {
	    			console.log ("XHR:" + xhr.responseText);
	    			processSearchResponse(xhr.responseText);
	    		} else {
	    			console.log("actual:" + xhr.responseText)
	    			var js = JSON.parse(xhr.responseText);
	    			var err = js["error"];
	    			alert (err);
	    		}
	    	} else {
	    		processSearchResponse("N/A");
	    	}
	    };
	  }
}

// analyze search results 
// can't just reuse function in list_local_segments because need to generate different buttons 

function processSearchResponse(result) {
	var js = JSON.parse(result);
	var searchResultsList = document.getElementById('searchResultsList');
	// returns list of segments in model 
	  if(searchResultsList == null){
		  console.log("IN CSS FILE");
		  searchResultsList = document.getElementById('segmentsColumn');
	  }
	  
	
	var output = "";
	for (var i = 0; i < js.model.length; i++) {
		//grabs stuff out of json
		var localSRJson = js.model[i];
	    
	    var segID 			= localSRJson["id"]["id"];
//	    var isRemAvailable 	= localSRJson["remotelyAvailable"];
	    var sent			= localSRJson["sentence"];
	    var character 		= localSRJson["character"];

	    output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
	    output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
	    output = output + "<p><input type=\"button\" value=\"Append to current playlist\" /></p></br>";//need to add append handler here 
	  }
	  // Update computation result
	  searchResultsList.innerHTML = output;
	  
	}	
