
window.onload = startTimer;

function startTimer(){
    timer.start(30);
}

var timer = (function() {
    var timerElement;
    var timeoutRef;
    var count;

    return {
        start: function (givenTime) {
            count = givenTime;
            timerElement = document.getElementById('timer-id');
            timer.run();
        },

        run: function () {
            if (timeoutRef) clearInterval(timeoutRef);
            if (timerElement) {
                var html = "<span class=\"col-sm-offset-4\">"+count+"</span>" +
                    "<span style=\"font-size:100px; width:300px;\"> min</span>"
                timerElement.innerHTML = html;
            }
            count--;
            if (count > 0) {
                timeoutRef = setTimeout(timer.run, 60000);
            }

        }
    }

}());

