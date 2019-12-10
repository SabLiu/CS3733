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
function refreshRemoteSegmentsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", remote_site_url, true); 
   // get all remote sites (call our API) 
   xhr.send();
   
   console.log("sent");
   console.log("REFRESHING SEGMENTS : " + isAdmin);

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processRemoteSitesListResponse(xhr.responseText);
    } else {
      processRemoteSitesListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object: list of OUR registered sites.
 *
 */
function processRemoteSitesListResponse(remSitesList) {
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
    callRemoteAPI(url, apikey); 
    // need to make an API call to this site with this API key. 
  }
  
}
}

/**
 *  Call the remote API of site in our registered sites list
 *  Get a list of available segments from each site. 
 */
function callRemoteAPI(siteURL, apikey) {
	   var xhr = new XMLHttpRequest();
	   xhr.open("GET", siteURL, true); 
	   xhr.setRequestHeader("x-api-key", apikey);
	   // want to get their list of available segments
	   xhr.send();
	   
	  // This will process results and update HTML as appropriate. 
	   
	  xhr.onloadend = function () {
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processRemoteSegmentsListResponse(xhr.responseText);
	    } else {
	    	processRemoteSegmentsListResponse("N/A");
	    }
	  };
	}

/**
 * Respond to JSON object from remote site.
 *
 */
function processRemoteSegmentsListResponse(result) {

	var js = JSON.parse(result);// array of segments
  var remoteSegmentsList = document.getElementById('remoteSegments');
  
  if(remoteSegmentsList == null){
	  console.log("IN CSS FILE");
	  remoteSegmentsList = document.getElementById('segmentsColumn');
  }
  
  var output = "";
  for (var i = 0; i < js.segments.length; i++) { 
	//grabs stuff out of json
	var remoteSegJson = js.segments[i]; // this is a segment. 
    console.log("REMOTE SEG: " + remoteSegJson);
    
    var segURL 			= remoteSegJson["url"];
    var sent			= remoteSegJson["text"];
    var character 		= remoteSegJson["character"];
    
    // updates html
    	// character : sentence
    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL+ "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	output = output + "<p><input type=\"button\" value=\"Append to current playlist\" onClick=\"JavaScript:processAppendToPlaylist('" + segURL + "')\"></p></br>";
  }
  // Update computation result
  remoteSegmentsList.innerHTML = remoteSegmentsList.innerHTML + output;
  
}
