
function fire_form_submit() {
    document.getElementById("quiz-form").submit();

    setTimeout(fire_form_submit, 10000);

}

window.onload = jQuery(document).ready(function() {
    setTimeout(fire_form_submit, 10000);
});

