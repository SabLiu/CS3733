/* Heineman's : 
var num0 = document.getElementById("num0");
num0.addEventListener("ended", function() {num1.play(); });
var num1 = document.getElementById("num1");
num1.addEventListener("ended", function() {num2.play(); });
*/


function playAllVideos(currentPlaylistLength){
	console.log("playing videos"); 
	console.log("currentplaylistLength:" + currentPlaylistLength);
	
	for (var i = 0; i < currentPlaylistLength; i++) {
		var segmentID = "" + i;
		var nextSegmentID = "" + (i + 1); 
		var currentSegment = document.getElementById(segmentID);
		currentSegment.addEventListener("ended", function() {nextSegmentID.play(); });
	}
}


// need this function to write the HTML script for adding event listeners to each element 





//function playAllVideos(currentPlaylistLength, currentIndex){
////	console.log("playing videos"); 
////	console.log("currentplaylistLength:" + currentPlaylistLength);
//	return ("" + i); 
//		var segmentID = "" + i;
//		var nextSegmentID = "" + (i + 1); 
//		var currentSegment = document.getElementById(segmentID);
//		currentSegment.addEventListener("ended", function() {nextSegmentID.play(); });
//	}
//}


//put in participant page? 
/*
<script>
for (var i = 0; i < document.getElementById(currentPlaylist).length; i++) {
	var segmentID = "" + i;
	var nextSegmentID = "" + (i + 1); 
	var currentSegment = document.getElementById(currentPlaylist)[i];
	
	currentSegment.addEventListener("ended", function() {nextSegmentID.play(); });
	console.log("Playing video: " + segmentID); 
}

</script>
*/

// attempted play function in html : 
/* 
<script type="text/javascript">
function playVideos(currentPlaylistLength){
	console.log("playing videos"); 
	if (currentPlaylistLength > 0){
	for (var i = 0; i < currentPlaylistLength; i++) {
		var currentSegmentID = "" + i;
		var nextSegmentID = "" + (i + 1); 

		var currentSeg = document.getElementById(currentSegmentID); 
		var nextSeg = document.getElementById(nextSegmentID);
		currentSeg.addEventListener("ended", function() {nextSeg.play(); });
	
}
	}}
 Heineman's : 
var num0 = document.getElementById("num0");
num0.addEventListener("ended", function() {num1.play(); });
var num1 = document.getElementById("num1");
num1.addEventListener("ended", function() {num2.play(); });

</script>
*/