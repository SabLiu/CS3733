function processAppendToPlaylist(seg) {
	
	var data = {};
	data["id"] = seg;  
	console.log("currentPlaylistID in append: " + currentPlaylistID);
	  
	var js = JSON.stringify(data);
	
	data["id"] = currentPlaylistID;
	
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

