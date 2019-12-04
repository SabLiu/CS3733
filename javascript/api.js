// all access driven through BASE. Must end with a SLASH
var base_url = "https://xkdxootdqe.execute-api.us-east-2.amazonaws.com/HotspurAPI"; 

// segments
var upload_url = base_url + "/segment";     // POST
var list_local_segments_url = base_url + "/localSegments"; //GET
var delete_segment_url = base_url + "/deleteSegment"; 	// POST
var mark_segment_url = base_url + "/markSegment"; 
var unmark_segment_url = base_url + "/unmarkSegment"; 
var search_url = base_url + "/segmentSearch"; 

// playlists 
var create_playlist_url = base_url + "/playlist";	// POST
var delete_playlist_url = base_url + "/deletePlaylist"; 	// POST
var list_playlists_url = base_url + "/playlists"; //GET
var append_url = base_url + "/appendToPlaylist"; 
var get_playlist_url = base_url + "/getPlaylist";


// admin mark/unmark remote sites
var delete_remote_site_url = base_url + "/removeRemoteSite";// Can't send JSON to DELETE request. This is POST
var remote_site_url = base_url + "/remoteSite"; //	POST: add remote site
												// 	GET: list remote sites

// S3 bucket: 
var s3_segments_url = "https://hotspurproject.s3.us-east-2.amazonaws.com/segments/"; 