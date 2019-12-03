// all access driven through BASE. Must end with a SLASH
var base_url = "https://xkdxootdqe.execute-api.us-east-2.amazonaws.com/HotspurAPI"; 

var list_local_segments_url = base_url + "/localSegments"; //GET
var list_playlists_url = base_url + "/playlists"; //GET
var upload_url = base_url + "/segment";     // POST
var delete_remote_site_url = base_url + "/removeRemoteSite";// Can't send JSON to DELETE request. This is POST
var add_remote_site_url = base_url + "/remoteSite";
//var list_url   = base_url + "constants";    // GET




// S3 bucket: 
var s3_segments_url = "https://hotspurproject.s3.us-east-2.amazonaws.com/segments/"; 