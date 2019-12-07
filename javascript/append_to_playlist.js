function processAppendToPlaylist(seg, currentPlaylist) {
	
	var data = {};
	data["id"] = seg;  
	currentPlaylistID = currentPlaylist; // update global variable in ParticipantPage.html
	  
	var js = JSON.stringify(data);
	
	data["id"] = currentPlaylist;
	
	js = js + JSON.stringify(data);
	  
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", append_playlist_url, true);
	
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

