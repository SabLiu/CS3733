
/**
 * Refresh list of remote sites
 *
 */
function refreshRemoteSitesList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", remote_site_url, true);
   xhr.send();
   
   console.log("sent");
   
  // This will process results and update HTML as appropriate. 
   
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processViewSitesResponse(xhr.responseText);
    } else {
      processViewSitesResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 */
function processViewSitesResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var remoteSitesList = document.getElementById('remoteSites');
  
  var output = "";
  for (var i = 0; i < js.model.length; i++) {
	//grabs stuff out of json
	var remSiteJson = js.model[i];
    console.log(remSiteJson);
    
    var url = remSiteJson["url"];
    var id = remSiteJson["id"]["id"];

    output = output + "<p> " + url + " &nbsp;<input name=\"" + id + "\" type=\"button\" value=\"Unregister site\" onClick=\"JavaScript:processUnregisterSite('" + id + "')\"/></p></br>";  
  }

  // Update computation result
  remoteSitesList.innerHTML = output;
  console.log(output);
}
