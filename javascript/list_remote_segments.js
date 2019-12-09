
/**
 * Refresh list of remote segments
 *
 */
function refreshRemoteSegmentsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_remote_segments_url, true); // need to call the URLs in our remote sites list 
   xhr.send();
   
   console.log("sent");
   console.log("REFRESHING SEGMENTS : " + isAdmin);

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processRemoteListResponse(xhr.responseText);
    } else {
      processRemoteListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 */
function processRemoteListResponse(result) {
//  var localIsAdmin = isAdmin; 
  console.log("ISADMIN to list segments = " + isAdmin);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var remoteSegmentsList = document.getElementById('remoteSegments');
  // model is a list of segment objects
  
  var output = "";
  for (var i = 0; i < js.segments.length; i++) { // array of segments
	//grabs stuff out of json
	var remoteSegJson = js.model[i];
    console.log(remoteSegJson);
    
    var segURL 			= remoteSegJson["URL"];
    var sent			= remoteSegJson["text"];
    var character 		= remoteSegJson["character"];
    
    // updates html
    
    // if it's not the admin
    	// character : sentence
    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL+ "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	output = output + "<p><input type=\"button\" value=\"Append to current playlist\" onClick=\"JavaScript:processAppendToPlaylist(" + segURL + ")\"></p></br>";
  }
  // Update computation result
  remoteSegmentsList.innerHTML = output;
  
//  console.log(output);
}
