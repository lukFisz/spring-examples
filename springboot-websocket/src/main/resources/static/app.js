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
    let username = $('#username_').text();
    /*let or var?*/
    let type = 'sent';
    if (respond.sender === username)
        type = 'replies'
    $('div#messages ul').append(`
                <li class="${type}">
                    <p><span class="sender-info"><strong style="font-size: 1.2em">${respond.sender}</strong> ${new Date().toLocaleString()}</span><br>${respond.content}</p>
                </li>`)
    $('div#messages').scrollTop($('div#messages').height()+1000);
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function changeRoom(oldRoomId, newRoomId) {
    $('div#messages ul').text('')
    disconnect()
    connect(oldRoomId, newRoomId)
    notifyRoomChange(oldRoomId, newRoomId)
    for (let i = 0; i < chatRoomsList.length; i++){
        if (chatRoomsList[i].id === newRoomId) {
            $('#roomHeaderName').html(`<span class="name-circle" style="background-color: #27ae60">${chatRoomsList[i].name[0].toUpperCase()}</span> ${chatRoomsList[i].name}`)
        }
    }
}

function sendMessage(chatRoomId, message) {
    $("#inputMessageContent").val('')
    if (stompClient !== null)
        stompClient.send("/chat/room/" + chatRoomId, {}, JSON.stringify({'content': message}));
}

function notifyRoomChange(oldRoomId, newRoomId) {
    if (stompClientNotify !== null)
        stompClientNotify.send("/chat/notify", {}, JSON.stringify({'oldRoomId': oldRoomId, 'newRoomId': newRoomId}));
}

function chooseRoom(e) {
    let oldRoomId = chatRoomID;
    chatRoomID = $(e).children('input').val();
    changeRoom(oldRoomId, chatRoomID)
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
                $('#contacts ul').append(`
                <li class="contact" onclick="chooseRoom(this)">
                    <input type="hidden" value="${e.id}">
                    <div class="wrap">
                        <div class="meta">
                            <p class="name">${e.name}</p>
                        </div>
                    </div>
                </li>`)
                $("#rooms").append(`<option value="${e.id}">${e.name}</option>`)
            })
        },
        error : function() {} // TODO: error ajax chat rooms fetch
    })

    const socket = new SockJS('/ws-chat');
    stompClientNotify = Stomp.over(socket);

    $('button#btnSend').on('click', function(){
        sendMessage(chatRoomID, $("#inputMessageContent").val())
    })

})