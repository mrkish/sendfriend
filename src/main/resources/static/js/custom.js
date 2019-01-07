function hideOrShow() {
    var x = document.getElementById("myDiv");
    if (x.style.display == "none") {
        x.style.display = '';
    } else {
        x.style.display = "none";
    }
}

function hideOrShow(n) {
    var x = document.getElementById("myDiv");
    if (n == 0) {
        x.style.display = '';
    } else {
        x.style.display = "none";
    }
}
