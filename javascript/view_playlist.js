// called when participant clicks "view" button next to a playlist
// pass in ID of playlist they want to view
function processViewPlaylist(val, e) {
	registerAll(e); // need this to play the playlist
	var data = {};
	data["id"] = val;  
	currentPlaylistID = val; // update global variable in ParticipantPage.html
	
	
	
	console.log("currentPlaylistID: " + currentPlaylistID);
	  
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", get_playlist_url, true);
	
	xhr.send(js);

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

// generate information of segments 
// later: generate list of segments in the playlist and print to html 
// script is to play the playlist 
function processViewPlaylistResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  console.log("response to view: " + js); 
  var playlistSegmentsList = document.getElementById('currentPlaylist');

  // change label on current playlist
//  var playlistName = document.getElementById('currentPlaylistName');
//  playlistName.innerHTML = "" + js.model.name; // get playlist name 
  // in HTML : took out; <div id="currentPlaylistName"></div></p>
  
  var backButtonHTML = "";
  if(playlistSegmentsList == null){
	  console.log("In CSS");
	  backButtonHTML = "<button type='button' onClick = 'JavaScript:refreshPlaylistsList()'>Back to list of playlists</button>";
	  playlistSegmentsList = document.getElementById('playlistsColumn');
  }

  var output = ""; 
  
  currentPlaylistLength = js.model.segmentUrls.length; // change global variable in Participant Page, used by play_playlist
  console.log("currentPlaylist length: " + currentPlaylistLength); 

  for (var i = 0; i < js.model.segmentUrls.length; i++) {
	  // however many segments is in segmentUrls
	  // segmentUrls is a string that is a URL 
	var segURL = js.model.segmentUrls[i]; // is a segment URL
    console.log(segURL);
    
    // updates html
    var nextSegNum = i + 1; 
    	// first: this video needs controls
    	if (i == 0){ 
    		output = output + "<p><video id= vidNum"+ i + " width=\"320\" height=\"240\" controls><source src=" + segURL +  " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p> <input type=\"button\" value=\"Remove from playlist\" onClick=\"JavaScript:processDeleteFromPlaylist('" + segURL + "')\">" ;
    	}
    	else { 
    		output = output + "<p><video id= vidNum"+ i + " width=\"320\" height=\"240\"><source src=" + segURL + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p><input type=\"button\" value=\"Remove from playlist\" onClick=\"JavaScript:processDeleteFromPlaylist('" + segURL + "')\">" ;
    	}
    
  }
  
// Update computation result
  playlistSegmentsList.innerHTML = backButtonHTML + output;
  
}
