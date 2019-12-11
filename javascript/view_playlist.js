// called when participant clicks "view" button next to a playlist
// pass in ID of playlist they want to view
function processViewPlaylist(val,e) {
	try{
		   viewPlaylist = 1;
		   var i = 0;
		   while(true){
			   document.getElementById('appendButton' + i).disabled = false;
			   i = i + 1;
		   }
	   }
	catch(e){}
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
  
}

// outer 'makePlayFunction' returns a function that's used (at runtime) to play the given id
// e = event handler
function makePlayFunction(id) {
  // we are returning a function to be the event handler that plays 'id'
  return function(e) {
	// scrolling?!
	  document.getElementById(id).scrollIntoView();
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
     var vidBlock = "<p><video id='" + id + "' width=100% height: 100%";
     if (i == 0) { vidBlock += " controls"; }
     vidBlock += "><source src=\"" + segURL + "\" type=\"video/ogg\"></video></p><input type=\"button\" value=\"Remove from playlist\" onClick=\"JavaScript:processDeleteFromPlaylist('" + segURL + "')\"><p>&nbsp;</p>";
     console.log("REGISTERING: vidNum"+i);
     console.log(vidBlock);
     contents += vidBlock;
  }

  var output = document.getElementById("currentPlaylist");
  var backButtonHTML = "";
  if(output == null){
	  output = document.getElementById("playlistsColumn");
	  backButtonHTML = "<div style='text-align:right'><input type=\"button\" value=\"Back To Playlists List\" onClick=\"JavaScript:refreshPlaylistsList()\" /></div> <h1>" + currentPLJS.model.name + "</h1><div>&nbsp;</div>";
  }
  output.innerHTML = backButtonHTML + contents;

  // now that videos are in place, we can locate them and register the 
  // necessary callback functions, which is a tricky use of "closures" in Javascript.
  for (i = 0; i < currentPlaylistLength-1; i++) {
    var priorVid = document.getElementById("vidNum" + i);
    callBackFunction = makePlayFunction("vidNum" + (i+1));
    priorVid.addEventListener("ended", callBackFunction);
  }
}



