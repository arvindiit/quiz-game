
function load_answer_page() {
    location.href = "/quiz-page"

}

window.onload = jQuery(document).ready(function() {
    setTimeout(load_answer_page, 5000);
});