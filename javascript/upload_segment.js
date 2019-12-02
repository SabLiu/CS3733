  // from heineman create.js
	function handleUploadClick(e) {
	  var form = document.createForm;
	 
	  var data = {};
	  //user provides this info
	  data["sentence"] = form.sentence.value; 
	  data["character"] = form.character.value;
	  
	  //we generate this info
//	  data["id"]["id"] = generate UUID 
//	  data["videoFileAddress"] = whatever its URL is in the bucket
	  data["remotelyAvailable"] = false; // default set to false
	  
	    var segID 			= localSegsJson["id"]["id"];
	    var isRemAvailable 	= localSegsJson["remotelyAvailable"];
	    var sent			= localSegsJson["sentence"];
	    var character 		= localSegsJson["character"];
	    var segAddr 		= localSegsJson["videoFileAddress"];

	 /*this is what we need to put into the html file 
	   * 
<form name="createForm" method="post">
    
<input name="character" value="character" />
   <input name="sentence" value="sentence" />
   <input name="base64Encoding" hidden value=""/>
   Select a constant in file: <input type="file" id="constantValue" name="constantValue">
   <input type="button" id="uploadButton" value="Upload segment" disabled onClick="JavaScript:handleUploadClick(this)">
</form>
	   */
	  
	  // base64EncodedValue":"data:text/plain;base64,My4xND....."
	  var segments = document.createForm.base64Encoding.value.split(',');
	  data["base64EncodedValue"] = segments[1];  // skip first one 

	  var js = JSON.stringify(data);
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
