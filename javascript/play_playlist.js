/* Heineman's : 
var num0 = document.getElementById("num0");
num0.addEventListener("ended", function() {num1.play(); });
var num1 = document.getElementById("num1");
num1.addEventListener("ended", function() {num2.play(); });
*/


function playAllVideos(playlistLength){
	for (var i = 0; i < playlistLength; i++) {
		var segmentID = "" + i;
		var nextSegmentID = "" + (i + 1); 
		var currentSegment = document.getElementById(segmentID);
		currentSegment.addEventListener("ended", function() {nextSegmentID.play(); });
	}
}

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