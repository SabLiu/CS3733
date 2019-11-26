
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
  //yay
  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
  console.log("sentuuuuuuu\n\n\n\n");
// var testHtml = "<input name=\"regSite\" type=\"button\" value=\"Register site\" />"; 
   var localSegmentsList = document.getElementById('model');
   localSegmentsList.innerHTML = "euhrvbierbviehrbviehrbviehrbv";
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
//  var constList = document.getElementById('constantList'); this is from get_constantList.js
  var localSegmentsList = document.getElementById('model');
  // model is a list of segment objects
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) {
	  console.log(i + "sent\n\n\n\n\n\n\n\n\n");
	//grabs stuff out of json
	var localSegsJson = js.model[i];
    console.log(localSegsJson);
    
    var segID 			= localSegsJson["id"]["id"];
    var isRemAvailable 	= localSegsJson["remotelyAvailable"];
    var sent			= localSegsJson["sentence"];
    var character 		= localSegsJson["character"];
    var segAddr 		= localSegsJson["videoFileAddress"];
    
    // updates html
    
    // video
//    output = output + "<p><video controls="" height="240" id=" + segID + " width="320"><source src=" + segAddr + "type="video/ogg" /> Your browser does not support the video tag.</video></p>" ;
    output = output + "<p><video controls=\"\" height=\"240\" id=\"\" width=\"320\"><source src=" + "\"" + s3_segments_url  + segID + "\"" + " type=\"video/ogg\" /> Your browser does not support the video tag.</video></p>" ;
    
    // character : sentence
    output = output + "<p>" + character + ": &quot;" + sent + "&quot;&nbsp;</p>";
    // buttons: delete, mark available, mark unavailable 
    // these don't need names since when you delete them, you just "redraw" and only the segments still in the list will create these buttons 
    output = output + "<p>  <input type=\"button\" value=\"Delete segment\" /> <input type=\"button\" value=\"Mark segment remotely available\" /><input type=\"button\" value=\"Mark segment remotely UNavailable\" /></p></br>"; 
    
    /*
     * Original code from administratorLandingPage.html
<p>
<input name="del2" type="button" value="Delete segment" />
<input name="remy2" type="button" value="Mark segment remotely available" />
<input name="remn2" type="button" value="Mark segment remotely UNavailable" />
</p>

</br>
video (template) : 
     *<p><video controls="" height="240" id="vid1" width="320"><source src="https://hotspurproject.s3.us-east-2.amazonaws.com/segments/NewOne.ogg" type="video/ogg" /> Your browser does not support the video tag.</video></p>
     show character and speech 
     <p>McCoy: &quot;Then I need a drink.&quot;&nbsp;</p>
     */
  }

  // Update computation result
  console.log("sentjjjj\n\n\n\n");
  localSegmentsList.innerHTML = output;
  console.log(output);
}
