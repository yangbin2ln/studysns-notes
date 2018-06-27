var ws = new WebSocket("ws://127.0.0.1/studysns/websocket/notes");

ws.onmessage = function(evt) {
	  console.log( "Received Message: " + evt.data);
	  var data = JSON.parse(evt.data);
	  $('body').append('<div>'+data.title+'</div>');
};