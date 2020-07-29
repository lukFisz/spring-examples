let stompClient = null;
let chatRoomsList = null;
let chatRoomID = null;

function connect(id) {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    connectWithRoom(id)
}

function connectWithRoom(id, func) {
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/room/' + id, function (responds) {
            displayMessage(JSON.parse(responds.body));
        });
    });
}

function displayMessage(respond) {
    console.log(respond)
    $('div#messages').append(`<p>${respond.sender}: ${respond.content}</p>`)
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function changeRoom(id) {
    disconnect()
    connect(id)
}

function sendMessage(chatRoomId, message) {
    if (stompClient !== null)
        stompClient.send("/chat/room/" + chatRoomId, {}, JSON.stringify({'content': message}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $.ajax({
        url : "/fetch/chatrooms",
        method : "post",
        dataType : "json",
        success : function(response) {
            chatRoomsList = response
            chatRoomsList.forEach(function (e) {
                $("#rooms").append(`<option value="${e.id}">${e.name}</option>`)
            })
        },
        error : function() {} // TODO: error ajax chat rooms fetch
    })

    $('#btnChooseRoom').on('click', function(){
        disconnect();
        chatRoomID = $("select#rooms").children("option:selected").val();
        changeRoom(chatRoomID)
    });

    $('button#btnSend').on('click', function(){
        sendMessage(chatRoomID, $("#message").val())
    })

})