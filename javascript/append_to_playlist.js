function processAppendToPlaylist(seg) {
	
	var data = {};
	data["segmentId"] = seg;  
	data["playlistId"] = currentPlaylistID + ""; // global variable
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
//				  processViewPlaylistResponse(xhr.responseText);
				  // might need to reach into JSON differently than View playlist does
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
