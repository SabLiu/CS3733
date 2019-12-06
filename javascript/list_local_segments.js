
/**
 * Refresh list of local segments
 *
 *    GET list_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshLocalSegmentsList(isAdmin) {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_local_segments_url, true);
   xhr.send();
   
   console.log("sent");
   console.log("REFRESHING SEGMENTS : " + isAdmin);

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText, isAdmin);
    } else {
      processListResponse("N/A", isAdmin);
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'localSegmentsList' with a <br>-separated list of name,value pairs.
 */
function processListResponse(result, isAdmin) {
  var localIsAdmin = isAdmin; 
  console.log("localISADMIN  = " + localIsAdmin);
  console.log("this segment " + 5);
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
    if (isAdmin > 1){
    	// video
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	// character : sentence
    	output = output + "<p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	// buttons: delete, mark available, mark unavailable 
    	output = output + "<p>   <input type=\"button\" id=\"deleteSeg\" value=\"Delete Local Segment\" onClick=\"JavaScript:processDeleteSegment('" + segID + "', '" + localIsAdmin + "')\"> <input type=\"button\" value=\"Mark segment remotely available\" /><input type=\"button\" value=\"Mark segment remotely UNavailable\" /></p></br>";
    }
    else if (isAdmin < 1){
    	// character : sentence
    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	output = output + "<p><input type=\"button\" value=\"Append to current playlist\" /><input type=\"button\" id=\"deleteSeg\" value=\"Delete Local Segment\" onClick=\"JavaScript:processDeleteSegment('" + segID + "', '" + localIsAdmin + "')\"></p></br>";
    } else {
    	console.log ("I don't know what isAdmin is");
    }
  
  }
  // Update computation result
  localSegmentsList.innerHTML = output;
  
//  console.log(output);
}
