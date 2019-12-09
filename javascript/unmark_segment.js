// called when administrator clicks "Unmark segment" button next to segment 
// pass in ID of segment they want to unmark
function processUnmarkSegment(val) {
	
	var data = {};
	data["id"] = val;  
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", unmark_segment_url, true);
	
	xhr.send(js);

  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText); 	// calls function from list_local_segment.js 
    } else {
      processListResponse("N/A");
    }
  };
}
