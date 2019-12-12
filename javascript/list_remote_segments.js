/*
 * How this works: 
 * 1. call our API to get a list of our registered sites
 * 2. call the API of each URL to get the list of segments
 * 3. generate necessary HTML for each list of segments
 */


/**
 * Refresh list of remote segments
 *
 */
function refreshRemoteSegmentsList(initalizing) {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", remote_site_url, true); 
   // get all remote sites (call our API) 
   xhr.send();
   
  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processRemoteSitesListResponse(initalizing, xhr.responseText);
    } else {
      processRemoteSitesListResponse(initalizing, "N/A");
    }
  };
  
}

/**
 * Respond to server JSON object: list of OUR registered sites.
 *
 */
function processRemoteSitesListResponse(initalizing, remSitesList) {
  var js = JSON.parse(remSitesList);
  

  // model is a list of registered remote sites
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) { // array of sites
	var aSite = js.model[i];
    
    var siteURL = aSite["url"];
    var q = siteURL.indexOf("?apikey=");
    if (q == -1) {
      alert("Your input must be of the form 'url?apikey=...'");
    } else {
      var url = siteURL.substring(0, q);
      var apikey = siteURL.substring(q+8);
    callRemoteAPI(initalizing, url, apikey); 
    // need to make an API call to this site with this API key. 
    }
  
  }
}

/**
 *  Call the remote API of site in our registered sites list
 *  Get a list of available segments from each site. 
 */
function callRemoteAPI(initalizing, siteURL, apikey) {
	   var xhr = new XMLHttpRequest();
	   xhr.open("GET", siteURL, true); 
	   xhr.setRequestHeader("x-api-key", apikey);
	   // want to get their list of available segments
	   xhr.send();
	   
	  // This will process results and update HTML as appropriate. 
	   
	  xhr.onloadend = function () {
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processRemoteSegmentsListResponse(initalizing, xhr.responseText);
	    } else {
	    	processRemoteSegmentsListResponse(initalizing, "N/A");
	    }
	  };
	}

/**
 * Respond to JSON object from remote site.
 *
 */
function processRemoteSegmentsListResponse(initalizing, result) {
	var js = JSON.parse(result);
	// js.segments[i] is a remote segment
	
	if (remoteSegsGlobal.length > 0){
		// check to see if any segments match the current segment 
		if (js != null){
			addNewSegs(js);//(js);
			console.log("added new segments. "+ remoteSegsGlobal.length);
		}
		
	}
	else if (remoteSegsGlobal.length == 0){
		// just add in all the segments 
		for (var k = 0; k < js.segments.length; k++){
			remoteSegsGlobal.push(js.segments[k]);
		}
		console.log("FIRST REMOTE SITE. " + remoteSegsGlobal.length);
	}
	if(initalizing < 3){
			  
		var remoteSegmentsList = document.getElementById('remoteSegments');
  
		if(remoteSegmentsList == null){
			console.log("IN CSS FILE");
			remoteSegmentsList = document.getElementById('segmentsColumn');
		}
  
		var output = "";
		for (var i = 0; i < js.segments.length; i++) { 
			//grabs stuff out of json
			var remoteSegJson = js.segments[i]; // this is a segment. 
			
		    var segURL 			= remoteSegJson["url"];
		    var sent			= remoteSegJson["text"];
		    var character 		= remoteSegJson["character"];
		    
		    var isDisabled = "";
		        try{
		        	if (viewPlaylist == 0){
		        		isDisabled = "disabled";
		        	}
		        }catch(e){}
    
		    // updates html
	    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
	    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL+ "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
	    	output = output + "<p><input type=\"button\" id = \"appendButton" + i + "\"value=\"Append to current playlist\" " + isDisabled + " onClick=\"JavaScript:processAppendToPlaylist('" + segURL + "')\"></p></br>";  
		}
		if(initalizing < 3){
			// Update computation result
			remoteSegmentsList.innerHTML = remoteSegmentsList.innerHTML + output;
		}
	}
	  
}

/**
 * This function sees if a remote site has already been registered
**/

	// if remote segment from a remote site has the same url, character, text as the segment in the list you're trying to add
	// remoteSegsJSON: a list of remote lists of segments
	// remoteSegsJSON[i]: a JSON of a list of segments from 1 site ????????
	// remoteSegsJSON[i]["segments"]: a list of remote segments ??????


function addNewSegs(js){
//	var js = JSON.parse(result);
	var foundMatch = 3; // 3 is "false", 5 is "true"
	
	// for each segment in the passed-in response
	for (var j = 0; j < js.segments.length; j++){
		foundMatch = 3; // reset boolean to toggle
		var remoteSegJson = js.segments[j]; // this is a segment. 
		
        var curSegURL 			= remoteSegJson["url"].toLowerCase();
        var curSent			= remoteSegJson["text"].toLowerCase();
        var curCharacter 		= remoteSegJson["character"].toLowerCase();
        
        // for each segment already in remoteSegsGlobal
        for (var i = 0; i < remoteSegsGlobal.length; i++){
        	var existingURL = remoteSegsGlobal[i]["url"].toLowerCase();
        	var existingSent = remoteSegsGlobal[i]["text"].toLowerCase();
        	var existingChar = remoteSegsGlobal[i]["character"].toLowerCase();
        	
        	if (((existingURL===curSegURL)&&(existingSent===curSent)&&(existingChar===curCharacter))){
        		foundMatch = 5;
        		// found a matching segment. do not add again.
        	}
        }
        if (foundMatch < 4){ // if still haven't set off the warning
        	remoteSegsGlobal.push(remoteSegJson);
        	// didn't find a matching segment. add. 
        }
        
	}
    
}