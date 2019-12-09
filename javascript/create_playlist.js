 // from heineman create.js

// called when participant clicks "create playlist" button 
// pass in the name the user wants 
	function handleCreatePlaylistClick(e) {
	  var form = document.playlistForm;
	 
	  var data = {};
	  // user provides this info
	  data["name"] = form.PLname.value; 

	  var js = JSON.stringify(data); // magic to convert data to JSON
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", create_playlist_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	    	 if (xhr.status == 200) {
		      console.log ("XHR:" + xhr.responseText);
	    	 } else {
	    		 console.log("actual:" + xhr.responseText)
				  var js = JSON.parse(xhr.responseText);
				  var err = js["response"];
				  alert (err);
	    	 }
	    }
	    // takes JSON and refreshes the list with the response of API call
	    // calls function in list_playlists
	    processPlaylistResponse(xhr.responseText);
	  };
	  
	}
//edited upload_segment code