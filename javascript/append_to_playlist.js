
function processAppendToPlaylist(segURL) {
	
	var data = {};
	// need to make ID objects for segment and playlist
	data["segmentUrl"] = segURL; 
	data["playlistId"]= currentPlaylistID; 
	
	console.log("currentPlaylistID in append: " + currentPlaylistID);
	  
	var js = JSON.stringify(data);
	
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", append_playlist_url, true);
	
	xhr.send(js);

  // This will process results and update HTML as appropriate. 
   
	 xhr.onloadend = function () {
		  console.log(xhr);
		  console.log(xhr.request);
		  if (xhr.readyState == XMLHttpRequest.DONE) {
			  if (xhr.status == 200) {
				  console.log ("XHR:" + xhr.responseText);
				  processViewPlaylistResponse(xhr.responseText);
				  // depending on JSON response. 
			  } else {
				  console.log("actual:" + xhr.responseText)
				  var js = JSON.parse(xhr.responseText);
				  var err = js["error"];
				  alert (err);
			  }
		  } else {
			  processViewPlaylistResponse("N/A");
		  }
	  };
	}
