// called when administrator clicks "Mark segment" button next to segment 
// pass in ID of segment they want to mark
function processMarkSegment(val) {
	
	var data = {};
	data["id"] = val;  
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", mark_segment_url, true);
	
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
