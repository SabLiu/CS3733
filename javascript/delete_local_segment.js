function processDeleteSegmentResponse(result, isAdmin) {
  console.log("deleted :" + result);

//  refreshLocalSegmentsList(isAdmin);
}


function processDeleteSegment(val, isAdmin) {
	console.log("this is the isAdmin that is passed in " + isAdmin);
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
			  processDeleteSegmentResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processDeleteSegmentResponse("N/A");
	  }
	  var passMeThrough = isAdmin;
	  console.log("pass me through: " + passMeThrough);
	  processListResponse(xhr.responseText, passMeThrough); //replace w/ isAdmin
	  
  };
}
