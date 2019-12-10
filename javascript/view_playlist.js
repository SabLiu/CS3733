// called when participant clicks "view" button next to a playlist
// pass in ID of playlist they want to view
function processViewPlaylist(val,e) {
	
	var data = {};
	data["id"] = val;  
	currentPlaylistID = val; // update global variable in ParticipantPage.html
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", get_playlist_url, true);
	
	xhr.send(js);

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processViewPlaylistResponse(xhr.responseText,e);
    } else {
      processViewPlaylistResponse("N/A",e);
    }
  };
}

// generate information of segments 
function processViewPlaylistResponse(result,e) {  
	var js = JSON.parse(result);
  console.log("response to view: " + js); 
  
  currentPlaylistLength = js.model.segmentUrls.length; // change global variable in Participant Page, used by play_playlist
  currentPLJS = js; // change global variable
  registerAll(e);
/*// THIS CODE WAS FOR NON-PLAYABLE PLAYLISTS. 
  
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

  for (var i = 0; i < js.model.segmentUrls.length; i++) {
	  // however many segments is in segmentUrls
	  // segmentUrls is a string that is a URL 
	var segURL = js.model.segmentUrls[i]; // is a segment URL

    // updates html
    var nextSegNum = i + 1; 
    	// first: this video needs controls
    	if (i == 0){ 
    		output = output + "<p><video controls=\"\" id= \"vidNum"+ i + "\" width=\"320\" height=\"240\" controls><source src=" + segURL +  " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p> <input type=\"button\" value=\"Remove from playlist\" onClick=\"JavaScript:processDeleteFromPlaylist('" + segURL + "')\">" ;
    	}
    	else { 
    		output = output + "<p><video controls=\"\" id= \"vidNum"+ i + "\" width=\"320\" height=\"240\"><source src=" + segURL + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p><input type=\"button\" value=\"Remove from playlist\" onClick=\"JavaScript:processDeleteFromPlaylist('" + segURL + "')\">" ;
    	}
    
  }
  
// Update computation result
  playlistSegmentsList.innerHTML = backButtonHTML + output;
  */
  
}





// Oh this is tricky. The outer 'makePlayFunction' returns a function
// that is used (at runtime) to play the given id
// e = event handler
function makePlayFunction(id) {

  // we are returning a function to be the event handler that plays 'id'
  return function(e) {
    document.getElementById(id).play();
  };
}

function registerAll(e) {
	var allScripts = currentPLJS.model.segmentUrls;
  var contents = "";
  var i;
  for (i = 0; i < currentPlaylistLength; i++) {
     var id = "vidNum" + i;
     var segURL = allScripts[i];
     var vidBlock = "<p><video id='" + id + "' width=320 height=240";
     if (i == 0) { vidBlock += " controls"; }
     vidBlock += "><source src=\"" + segURL + "\" type=\"video/ogg\"></video></p><input type=\"button\" value=\"Remove from playlist\" onClick=\"JavaScript:processDeleteFromPlaylist('" + segURL + "')\">";
     console.log("REGISTERING: vidNum"+i);
     console.log(vidBlock);
     contents += vidBlock;
  }

  var output = document.getElementById("currentPlaylist");
  output.innerHTML = contents;

  // now that videos are in place, we can locate them and register the 
  // necessary callback functions, which is a tricky use of "closures" in Javascript.
  for (i = 0; i < currentPlaylistLength-1; i++) {
    var priorVid = document.getElementById("vidNum" + i);
    callBackFunction = makePlayFunction("vidNum" + (i+1));
    priorVid.addEventListener("ended", callBackFunction);
  }
}



