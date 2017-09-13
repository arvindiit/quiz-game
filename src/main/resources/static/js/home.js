$(document).ready(function () {

    $("#join-form").submit(function (event) {
        event.preventDefault();

        var search;

        search = $("#username").val();
        if(search && search.trim().length != 0) {
            if(search.trim().length > 6){
                var htmlWelcome = "<h4>Please enter a name less than 6 letter !!</h4>";
                $('#login').html(htmlWelcome);
            }else {
                fire_ajax_submit(search.trim());
                document.getElementById('form-id').style.display = 'none';
            }
        }else{
            var htmlWelcome = "<h4>You don't have a name ????</h4>";
            $('#login').html(htmlWelcome);
        }

    });

});

function fire_ajax_submit(search) {

    var htmlWelcome = "<h4>Welcome "+search+"</h4>";
    $('#login').html(htmlWelcome);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/login",
        data: search,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if(data==50) {
                var html = "<p><h4><pre>There is already a user with same name. Select a different one !!!</pre></h4></p>";
                $('#timer-id').html(html);
                document.getElementById('form-id').style.display = 'block';
            }else if(data>30) {
                var html = "<p><h4><pre>Please wait for game to start</pre></h4></p>";
                $('#timer-id').html(html);
               timer.repeat();
            }else{
                timer.start(data);
            }

            console.log("SUCCESS : ", data);

        },
        error: function (e) {

            var html = "<h4>Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#login').html(html);

            console.log("ERROR : ", e);

        }
    });
}

var timer = (function() {
    var timerElement;
    var timeoutRef;
    var count;

    return {
        start : function(givenTime) {
            count = givenTime;
            timerElement = document.getElementById('timer-id');
            timer.run();
        },

        run: function() {
            if (timeoutRef) clearInterval(timeoutRef);
            if (timerElement) {
                timerElement.innerHTML = count;
            }
            if (count > 0) {
                timeoutRef = setTimeout(timer.run, 1000);
            }
            count--;
            if(count == 0){
                location.href = "/quiz-page"
            }
        },

        repeat : function() {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/getTimeValue",
                cache: false,
                timeout: 600000,
                success: function (data) {
                    if (timeoutRef) clearInterval(timeoutRef);

                    if(data>30) {
                        timeoutRef = setTimeout(timer.repeat, 2000);
                    }else{
                        timer.start(data);
                    }

                },
                error: function (e) {

                    var html = "<h4>Ajax Response</h4><pre>"
                        + e.responseText + "</pre>";
                    $('#login').html(html);

                    console.log("ERROR : ", e);

                }
            });
        }
    }

}());