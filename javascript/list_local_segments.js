/**
 * Refresh list of local segments
 *
 *    GET list_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshLocalSegmentsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_local_segments_url, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'localSegmentsList' with a <br>-separated list of name,value pairs.
 */
function processListResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
//  var constList = document.getElementById('constantList');
  var localSegmentsList = document.getElementById('model');
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    
	//grabs stuff out of json
	var localSegsJson = js.list[i];
    console.log(localSegsJson);
    
    var segID 			= localSegsJson["id"];
    var isRemAvailable 	= localSegsJson["remotelyAvailable"];
    var sent			= localSegsJson["sentence"];
    var character 		= localSegsJson["character"];
    var segAddr 		= localSegsJson["videoFileAddress"];
    //updates html
    output = output + "<p><video controls="" height="240" id=" + segID + " width="320"><source src=" + segAddr + "type="video/ogg" /> Your browser does not support the video tag.</video></p>" + "<p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";

    /* HTML for showing a video (template) : 
     *<p><video controls="" height="240" id="vid1" width="320"><source src="https://hotspurproject.s3.us-east-2.amazonaws.com/segments/NewOne.ogg" type="video/ogg" /> Your browser does not support the video tag.</video></p>
     show character and speech 
     <p>McCoy: &quot;Then I need a drink.&quot;&nbsp;</p>
     */
  }

  // Update computation result
  localSegmentsList.innerHTML = output;
}

