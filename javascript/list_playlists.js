
/**
 * Refresh list of local segments
 *
 *    GET list_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshPlaylistsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_playlists_url, true);
   xhr.send();
   
   console.log("sent");
  //yay
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
 *
 * Replace the contents of 'localSegmentsList' with a <br>-separated list of name,value pairs.
 */
function processPlaylistResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
//  var constList = document.getElementById('constantList'); this is from get_constantList.js
  var localPlaylistsList = document.getElementById('playlists');
  // model is a list of segment objects
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) {
	//grabs stuff out of json
	var playlistJson = js.model[i];
    console.log(playlistJson);
    
    var name = playlistJson["name"];
    output = output + "<p>" + name + "&nbsp;&nbsp;<input type=\"button\" value=\"View\" /><input type=\"button\" value=\"Play\" /><input type=\"button\" value=\"Delete\" /></p>"; 
    //<p>Sabrina&#39;s Symphonies &nbsp;&nbsp;<input type="button" value="View" /><input name="p1Play" type="button" value="Play" /><input name="Pdel1" type="button" value="Delete" /></p>
  }

  // Update computation result
  localPlaylistsList.innerHTML = output;
  console.log(output);
}