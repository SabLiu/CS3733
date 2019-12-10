
/**
 * Refresh list of playlists
 */
function refreshPlaylistsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_playlists_url, true);
   xhr.send();
   
   console.log("sent");
   
  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processPlaylistResponse(xhr.responseText);
    } else {
      processplayListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *	Takes playlists returned in JSON and generates appropriate buttons 
 */
function processPlaylistResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var localPlaylistsList = document.getElementById('playlists');
  
  if (localPlaylistsList == null){
	  console.log("in CSS");
	  localPlaylistsList = document.getElementById('playlistsColumn');
  }
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) {
	//grabs stuff out of json
	var playlistJson = js.model[i]; // this is a single playlist 
    console.log(playlistJson);
    
    var name = playlistJson["name"];
    var id = playlistJson["id"]["id"];
    output = output + "<p>" + name + "&nbsp;&nbsp;<input type=\"button\" value=\"View\" onClick=\"JavaScript:processViewPlaylist('" + id + "', this)\" /><input type=\"button\" value=\"Delete\" onClick=\"JavaScript:processDeletePlaylist('" + id + "')\" /></p>";    
  }

  // Update computation result
  localPlaylistsList.innerHTML = output;
  console.log(output);
}
