function timer() {
    var timerElement;
    var timeoutRef;
    var count = 30;
    alert("hellp");
    timerElement = document.getElementById('timer-id');
    if (timeoutRef) clearInterval(timeoutRef);
    if (timerElement) {
        var html = "<span class=\"col-sm-offset-4\">"+count+"</span>"
        timerElement.innerHTML = html;
    }
    if (count > 0) {
        count--;
        setTimeout(timer(count), 60000);
    }

}
document.onload = timer();