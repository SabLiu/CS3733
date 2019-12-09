
/**
 * Refresh list of local segments
 *
 */
function refreshLocalSegmentsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_local_segments_url, true);
   xhr.send();
   
   console.log("sent");
   console.log("REFRESHING SEGMENTS : " + isAdmin);

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 */
function processListResponse(result) {
//  var localIsAdmin = isAdmin; 
  console.log("ISADMIN to list segments = " + isAdmin);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  //  var constList = document.getElementById('constantList'); this is from get_constantList.js
  var localSegmentsList = document.getElementById('model');
  // model is a list of segment objects
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) {
	//grabs stuff out of json
	var localSegsJson = js.model[i];
    console.log(localSegsJson);
    
    var segID 			= localSegsJson["id"]["id"];
    var isRemAvailable 	= localSegsJson["remotelyAvailable"];
    var sent			= localSegsJson["sentence"];
    var character 		= localSegsJson["character"];
    
    // updates html
    // if it is the admin
    if (isAdmin > 3){
    	// video
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	// character : sentence
    	output = output + "<p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	// buttons: delete, mark available, mark unavailable 
    	output = output + "<p> Remotely available? " + isRemAvailable +  "   <input type=\"button\" id=\"deleteSeg\" value=\"Delete Local Segment\" onClick=\"JavaScript:processDeleteSegment('" + segID + "')\"/> <input type=\"button\" value=\"Mark segment remotely available\" onClick=\"JavaScript:processMarkSegment('" + segID + "')\"/><input type=\"button\" value=\"Mark segment remotely UNavailable\" onClick=\"JavaScript:processUnmarkSegment('" + segID + "')\"/></p></br>";
    }
    // if it's not the admin
    else if (isAdmin < 3){
    	// character : sentence
    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	output = output + "<p><input type=\"button\" value=\"Append to current playlist\" onClick=\"JavaScript:processAppendToPlaylist('" + segID + "')\"><input type=\"button\" id=\"deleteSeg\" value=\"Delete Local Segment\" onClick=\"JavaScript:processDeleteSegment('" + segID + "')\"></p></br>";
    } 
    // catch weird errors/troubleshooting
    else {
    	console.log ("I don't know what isAdmin is");
    }
  
  }
  // Update computation result
  localSegmentsList.innerHTML = output;
  
//  console.log(output);
}
