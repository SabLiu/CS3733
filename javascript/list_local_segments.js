
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
  var js = JSON.parse(result);
  if (isAdmin<3){
	  localSegsJSON = js; 
  }
  var localSegmentsList = document.getElementById('localSegments');
  
  if(localSegmentsList == null){
	  console.log("IN CSS FILE");
	  localSegmentsList = document.getElementById('segmentsColumn');
  }
  // model is a list of segment objects
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) { // model is a list of segments
	//grabs stuff out of json
	var localSegsJson = js.model[i];
    console.log(localSegsJson);
    
    var segID 			= localSegsJson["id"]["id"];
    var isRemAvailable 	= localSegsJson["remotelyAvailable"];
    var sent			= localSegsJson["sentence"];
    var character 		= localSegsJson["character"];
    var segURL 			= localSegsJson["url"];
    
    var isDisabled = ""
    try{
    	if (viewPlaylist == 0){
    		isDisabled = "disabled";
    	}
    }catch(e){}
    
    
    // updates html
    // if it is the admin
    if (isAdmin > 3){
    	
    	var remoteToggle = "test"
    	if(isRemAvailable){
    		remoteToggle = "<input type=\"button\" value=\"Mark segment remotely UNavailable\" onClick=\"JavaScript:processUnmarkSegment('" + segID + "')\"/>";
    	} else{
    		remoteToggle = "<input type=\"button\" value=\"Mark segment remotely available\" onClick=\"JavaScript:processMarkSegment('" + segID + "')\"/>";
    	}
    	// character : sentence
    	output = output + "<p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	// video
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>";
    	// buttons: delete, mark available, mark unavailable 
    	output = output +  remoteToggle + "  <input type=\"button\" id=\"deleteSeg\" value=\"Delete Local Segment\" onClick=\"JavaScript:processDeleteSegment('" + segID + "')\"/><p></br>";
    }
    // if it's the participant
    else if (isAdmin < 3){
    	// character : sentence
    	output = output + "<br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + segURL + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	output = output + "<p><input type=\"button\" id = \"appendButton"+ i + "\"value=\"Append to current playlist\" " + isDisabled + " onClick=\"JavaScript:processAppendToPlaylist('" + segURL + "')\">  <input type=\"button\" id=\"deleteSeg\" value=\"Delete Local Segment\" onClick=\"JavaScript:processDeleteSegment('" + segID + "')\"></p></br>";
    } 
  
  }
  // Update computation result
  localSegmentsList.innerHTML = output;
}
