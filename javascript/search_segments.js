function processSearch() {
	  var form = document.searchForm;
	  var character = form.searchBarCharacter.value;
	  var sentence = form.searchBarWords.value;

  var data = {};
  data["characterKeyphrase"] = character;
  data["sentenceKeyphrase"] = sentence;
  
  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", search_url, true);  
  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processSearchResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processSearchResponse("N/A");
	  }
  };
}

function processSearchResponse(result) {
	var js = JSON.parse(result);
	var searchResultsList = document.getElementById('searchResultsList');
	// returns list of segments in model 
	
	var output = "";
	for (var i = 0; i < js.model.length; i++) {
		//grabs stuff out of json
		var localSRJson = js.model[i];
	    
	    var segID 			= localSRJson["id"]["id"];
//	    var isRemAvailable 	= localSRJson["remotelyAvailable"];
	    var sent			= localSRJson["sentence"];
	    var character 		= localSRJson["character"];

	    output = output + "</br><p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
	    output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
	    output = output + "<p><input type=\"button\" value=\"Append to current playlist\" /></p></br>";//need to add append handler here 
	  }
	  // Update computation result
	  searchResultsList.innerHTML = output;
	  
	}	
