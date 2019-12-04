	function handleAddRemoteSiteClick(e) {
	  var form = document.registerURL;
	 
	  var data = {};
	  // user provides this info
	  data["url"] = form.URLtoRegister.value; 

	  var js = JSON.stringify(data); // magic to convert data to JSON
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", remote_site_url, true);

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
	    // refresh list of sites based on JSON response to API call 
	    processViewSitesResponse(xhr.responseText);
	  };
	  
	}
//edited upload_segment code