<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat app example</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">

    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="app.js"></script>
</head>
<body>

<#--<label for="rooms">Rooms</label>-->
<#--<select name="rooms" id="rooms">-->
<#--    <option value=""></option>-->
<#--</select>-->
<#--<p>Logged in user: <strong id="username">${username!}</strong></p>-->
<#--<button type="button" id="btnChooseRoom">Connect to room</button>-->
<#--<input type="text" id="message">-->
<#--<button type="button" id="btnSend">Send message</button>-->

<#--<div id="messages"></div>-->

<div id="frame">
    <div id="sidepanel">
        <div id="profile">
            <div class="wrap">

                <p><span class="name-circle">${username!?substring(0,1)?upper_case}</span> <span id="username_">${username!}</span></p>

            </div>
        </div>

        <div id="search">
            <input type="text" placeholder="Search room..." />
        </div>

        <div id="contacts">
            <ul>
            </ul>
        </div>


    </div>
    <div class="content">
        <div class="contact-profile">
            <p id="roomHeaderName">Choose room...</p>
        </div>
        <div id="messages" class="messages">
            <ul>
            </ul>
        </div>
        <div class="message-input">
            <div class="wrap">
                <input id="inputMessageContent" type="text" placeholder="Write your message..." />
                <button id="btnSend" class="submit">Send</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>