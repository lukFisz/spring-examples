let stompClient = null;
let stompClientNotify = null;
let chatRoomsList = null;
let chatRoomID = null;

function connect(oldRoomId, newRoomId) {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    connectWithRoom(oldRoomId, newRoomId)
}

function fetchRoomParticipants(roomId, func) {
    $.ajax({
        url : "/fetch/participants/",
        method : "post",
        dataType : "json",
        data : { roomID : roomId },
        success : function(response) {
            func(response)
        },
        error : function() {} // TODO: error ajax fetch participants
    })
}

function connectWithRoom(oldRoomId, newRoomId) {

    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/room/' + newRoomId, function (respond) {
            displayMessage(JSON.parse(respond.body));
            fetchRoomParticipants(newRoomId, function (participants) {
                console.log(participants)
            })
        });
    });
}

function displayMessage(respond) {
        $('div#messages').append(`<p>${respond.sender}: ${respond.content}</p>`)
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function changeRoom(oldRoomId, newRoomId) {
    disconnect()
    connect(oldRoomId, newRoomId)
    notifyRoomChange(oldRoomId, newRoomId)
}

function sendMessage(chatRoomId, message) {
    if (stompClient !== null)
        stompClient.send("/chat/room/" + chatRoomId, {}, JSON.stringify({'content': message}));
}

function notifyRoomChange(oldRoomId, newRoomId) {
    if (stompClientNotify !== null)
        stompClientNotify.send("/chat/notify", {}, JSON.stringify({'oldRoomId': oldRoomId, 'newRoomId': newRoomId}));
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

    const socket = new SockJS('/ws-chat');
    stompClientNotify = Stomp.over(socket);

    $('#btnChooseRoom').on('click', function(){
        let oldRoomId = chatRoomID;
        chatRoomID = $("select#rooms").children("option:selected").val();
        changeRoom(oldRoomId, chatRoomID)
    });

    $('button#btnSend').on('click', function(){
        sendMessage(chatRoomID, $("#message").val())
    })

})