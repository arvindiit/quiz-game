
window.onload = jQuery(document).ready(function() {
    timer.start(20)
});


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
                var html = "<span class=\"col-sm-offset-5\">" + count + "</span>"
                timerElement.innerHTML = html;
            }
            if (count > 0) {
                timeoutRef = setTimeout(timer.run, 1000);
            }
            count--;
            if (count == 0) {
                document.getElementById("quiz-form").submit();
            }
        }
    }

}());

