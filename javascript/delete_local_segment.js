
// called when delete button is pressed corresponding to a segment
// pass in whether current user is an admin and the ID of the segment they want to delete

function processDeleteSegment(val, isAdmin) {
	console.log("this is the isAdmin that is passed into processDeleteSegment: " + isAdmin);
  var data = {};
  data["id"] = val;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", delete_segment_url, true);  // Can't be DELETE since then no data sent via JSON

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processListResponse(xhr.responseText, isAdmin);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processListResponse("N/A", isAdmin);
	  }
	  
  };
}

// refresh list of segments by calling function in list local segments 
function processDeleteSegmentResponse(result, isAdmin) {
	  console.log("deleted :" + result);
	  processListResponse(xhr.responseText, isAdmin); 
	}

