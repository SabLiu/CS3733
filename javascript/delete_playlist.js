// when Participant clicks "Delete" playlist

// pass in which playlist participant wants to delete
function processDeletePlaylist(val) {
  var data = {};
  data["id"] = val;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", delete_playlist_url, true);  // Can't be DELETE since then no data sent via JSON

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processDeletePlaylistResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processDeletePlaylistResponse("N/A");
	  }
  };
}

// update playlists list by calling function in list_playlist
function processDeletePlaylistResponse(result) {
  console.log("deleted :" + result);
  processPlaylistResponse(result); // refresh list of playlists 
}
