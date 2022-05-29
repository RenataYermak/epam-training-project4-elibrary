// function preventBack() {
//     window.history.forward();
// }
// setTimeout("preventBack()", 0);
//      window.onunload = function () {
//     null
// }
function show() {
    var a = document.getElementById("pwd");
    if (a.type === "password") {
        a.type = "text";
    } else {
        a.type = "password";
    }
}
