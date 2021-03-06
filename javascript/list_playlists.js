
/**
 * Refresh list of playlists
 */
function refreshPlaylistsList() {
	try{
		   viewPlaylist = 0;
		   var i = 0;
		   while(true){
			   document.getElementById('appendButton' + i).disabled = true;
			   i = i + 1;
		   }
	   }
	catch(e){}

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
  var addPlaylist = ""
  if (localPlaylistsList == null){
	  console.log("in CSS");
	  localPlaylistsList = document.getElementById('playlistsColumn');
	  addPlaylist = "<h1>Playlists</h1><div>&nbsp;</div>" + "<form name='playlistForm' method='post'>"+  
	 	"<input name='PLname'  type='text' placeHolder='Enter playlist name' style='width:75%'/>  " +
	 	 	"<input type='button' id='createPLButton' value='Create Playlist' onClick='JavaScript:handleCreatePlaylistClick(this)'>"+
	 	"</form>" +
	 	"<p>&nbsp;</p>";
  }
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) {
	//grabs stuff out of json
	var playlistJson = js.model[i]; // this is a single playlist 
    console.log(playlistJson);
    
    var name = playlistJson["name"];
    var id = playlistJson["id"]["id"];
    output = output + "<p><input type=\"button\" value=\"View\" onClick=\"JavaScript:processViewPlaylist('" + id + "', this)\" />  <input type=\"button\" value=\"Delete\" onClick=\"JavaScript:processDeletePlaylist('" + id + "')\" />    "  + name + "</p><p>&nbsp;</p>";    
  }

  // Update computation result
  localPlaylistsList.innerHTML = addPlaylist + output;
  console.log("View playlist: " + output);
}
