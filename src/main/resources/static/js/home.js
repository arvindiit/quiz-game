$(document).ready(function () {

    $("#join-form").submit(function (event) {
        event.preventDefault();

        var search;

        search = $("#username").val();
        if(search && search.trim().length != 0) {
            if(search.trim().length > 6 || search.trim().length < 3){
                var htmlWelcome = "<h4 class=\"col-sm-offset-1\"><pre style=\"font-size:20px\">Please enter a name more than 2 and less than 6 letter!!</pre></h4>";
                $('#login').html(htmlWelcome);
            }else {
                document.getElementById("join-form").submit();
            }


        }else{
            var htmlWelcome = "<h4 class=\"col-sm-offset-1\"><pre style=\"font-size:20px\">You don't have a name ????</pre></h4>";
            $('#login').html(htmlWelcome);
        }

    });

});