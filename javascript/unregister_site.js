function processUnregisterSiteResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("unregistered :" + result);
  
  refreshRemoteSitesList();
}

function processUnregisterSite(val) {
  var data = {};
  data["id"] = val;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", delete_remote_site_url, true);  // Can't be DELETE since then no data sent via JSON

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processUnregisterSiteResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processUnregisterSiteResponse("N/A");
	  }
	  processViewSitesResponse(xhr.responseText); // refresh list of remote sites to reflect changes 
  };
}

