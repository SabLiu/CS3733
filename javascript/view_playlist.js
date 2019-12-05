
/**
 * Refresh list of playlist segments
 *
 *    GET list_url
 *    RESPONSE  list of [name, value] constants 
 */
function processViewPlaylist(val) {
	
	var data = {};
	data["id"] = val;  
	  
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
  var output = "<input type=\"button\" value=\"Play\" onClick=\"JavaScript:playPlaylist('" + js.model.segments + "')\" />";
  for (var i = 0; i < js.model.segments.length; i++) {
	//grabs stuff out of json
	var playlistSegsJson = js.model.segments[i]; // is a segment 
    console.log(playlistSegsJson);
    
    var segID 			= playlistSegsJson["id"]["id"];
    var isRemAvailable 	= playlistSegsJson["remotelyAvailable"];
    var sent			= playlistSegsJson["sentence"];
    var character 		= playlistSegsJson["character"];
    
    // updates html
    
    	// character : sentence
    	output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    	output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    	output = output + "<p><input type=\"button\" value=\"Delete From Playlist\" /></p></br>";
//    console.log("Printed a segment: " + id); 
  
  }
  // Update computation result
  playlistSegmentsList.innerHTML = output;
//}
//  console.log(output);
}
