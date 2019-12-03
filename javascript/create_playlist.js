// from heineman create.js
	function handleUploadClick(e) {
	  var form = document.createForm;
	 
	  var data = {};
	  // user provides this info
	  data["sentence"] = form.sentence.value; 
	  data["character"] = form.character.value;
	  
	  // we generate this info and send it to backend as JSON 
	  // generate UUID on backend 
	  data["remotelyAvailable"] = false; // default set to false
	  
	  // base64EncodedValue":"data:text/plain;base64,My4xND....."
	  var segments = document.createForm.base64Encoding.value.split(',');
	  data["base64EncodedValue"] = segments[1];  // skip first one 

	  var js = JSON.stringify(data); // magic to convert data to JSON
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", upload_url, true);

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
	  };
	  refreshLocalSegmentsList(false); // isAdmin = false since only participant uploads segments
	}

	
	
	// then getBase64, handleFileSelect, eventListener in html run 
	// from Professor Heineman's AWSCalculator code: script inside calculator.html