var ws = new WebSocket("ws:www.nizi521.club:99/studysns/websocket/notes");

ws.onmessage = function(evt) {
	  console.log( "Received Message: " + evt.data);
	  var data = JSON.parse(evt.data);
	  $('body').append('<div>'+data.title+'</div>');
};