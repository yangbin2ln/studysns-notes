<<<<<<< HEAD
var ws = new WebSocket("ws:www.nizi521.club:99/studysns/websocket/notes");
=======
var ws = new WebSocket("ws://127.0.0.1/studysns/websocket/notes");
>>>>>>> fdd57bd248689cd62f64af3d8ea99d76c27f24b8

ws.onmessage = function(evt) {
	  console.log( "Received Message: " + evt.data);
	  var data = JSON.parse(evt.data);
	  $('body').append('<div>'+data.title+'</div>');
};