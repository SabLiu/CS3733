
/**
 * Refresh list of playlist segments
 *
 *    GET list_url
 *    RESPONSE  list of [name, value] constants 
 */
function processViewPlaylist(val) {
	
	var data = {};
	data["id"] = val;  
	currentPlaylistID = val; // update global variable in ParticipantPage.html
	  
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", get_playlist_url, true);
	
	xhr.send(js);
   
	console.log("playlist retrieved");

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processViewPlaylistResponse(xhr.responseText);
    } else {
      processViewPlaylistResponse("N/A");
    }
  };
}


function processViewPlaylistResponse(result) {
  console.log("Process view playlist"); 
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var playlistSegmentsList = document.getElementById('currentPlaylist');
  
  var output = ""; 
  
  var scriptOutput1 = ""; // this defines all the variables
  var scriptOutput2 = ""; // this adds eventListeners
  
  
  currentPlaylistLength = js.model.segments.length; // change global variable in Participant Page, used by play_playlist
  console.log("currentPlaylist length: " + currentPlaylistLength); 

  for (var i = 0; i < js.model.segments.length; i++) {
	//grabs stuff out of json
	var playlistSegsJson = js.model.segments[i]; // is a segment 
    console.log(playlistSegsJson);
    
    var segID 			= playlistSegsJson["id"]["id"];
    var isRemAvailable 	= playlistSegsJson["remotelyAvailable"];
    var sent			= playlistSegsJson["sentence"];
    var character 		= playlistSegsJson["character"];
    
    // updates html
    var nextSegNum = i + 1; 
    	// take this line out if don't want to display char and sent, just video 
    	// character : sentence
    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    
    	// first: open script, this video needs controls
    	// display video, define var and add event listener
    	if (i == 0){ 
    		output = output + "<p><video controls=\"\" id= vidNum"+ i + " width=\"320\" height=\"240\" controls><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    		scriptOutput1 = scriptOutput1 + "<script> var vidNum" + i + " = document.getElementById(\"vidNum" + i + "\"); ";
    		scriptOutput2 = scriptOutput2 + "vidNum" + i + ".addEventListener(\"ended\", function() {vidNum" + nextSegNum + ".play(); }); " ; 
        	
    	}
    	// all the ones in the middle up to before second to last 
    	// display video, define var and add event listener
    	else if (i < (js.model.segments.length - 1) ){ 
    		output = output + "<p><video controls=\"\"  id= vidNum"+ i + " width=\"320\" height=\"240\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    		scriptOutput1 = scriptOutput1 + " var vidNum" + i + " = document.getElementById(\"vidNum" + i + "\"); "
    		scriptOutput2 = "vidNum" + i + ".addEventListener(\"ended\", function() {vidNum" + nextSegNum + ".play(); }); "; 
    	}
    	// 2nd to last one. Shouldn't an event listener to this one
    	else if (i == (js.model.segments.length - 1)){ 
    		output = output + "<p><video controls=\"\" id= vidNum"+ i + " width=\"320\" height=\"240\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>";
    		scriptOutput1 = scriptOutput1 + " var vidNum" + i + " = document.getElementById(\"vidNum" + i + "\"); ";
    	}
    	
    	// make a button for each segment in the playlist
    	output = output + "<p><input type=\"button\" value=\"Delete From Playlist\" /></p></br>";
    	console.log("ScriptOut1: " + scriptOutput1);
    	console.log("ScriptOut2: " + scriptOutput2);
  }
  
// Update computation result
  playlistSegmentsList.innerHTML = output  + scriptOutput1 + scriptOutput2 + "</script>";
  
}
