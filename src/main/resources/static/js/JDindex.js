var scrolls = document.getElementById("scrolls");
var width = 0,
    page = 0;
width = window.innerWidth;
scrolls.style.width = width + "px";
window.addEventListener("scroll", function (e) {
    var ding = document.getElementById("ding");
    var top = 0;
    var innerTop = window.innerHeight;
    page = window.pageYOffset;
    top = innerTop - page;
    if (page > 300) {
        scrolls.style.top = "0px";
        scrolls.style.borderBottom = "2px solid #c81623";
    } else {
        scrolls.style.top = "-50px";
        scrolls.style.borderBottom = "none";
    }
    if (top < 70) {
        top = 70;
    }
    ding.style.top = top + "px";

}, false)


var mia = document.getElementsByClassName("mia")[0];
var dian = document.getElementsByClassName("dian")[0].children;
var leftSize = 0;
var time = 1;
var i = 0;

function mn() {
    leftSize = leftSize - 5;
    i = Math.floor((-(leftSize - 250) / 590));
    if (i > 7) {
        i = 0;
    }
    if (i > 0) {
        dian[i - 1].style.backgroundColor = "rgba(95, 108, 96, 0.2)";
        dian[i - 1].style.border = "none";
        dian[i].style.backgroundColor = "#FFFFFF";
        dian[i].style.border = "3px solid rgba(0, 0, 0, 0.3)";
    } else {
        dian[dian.length - 1].style.backgroundColor = "rgba(95, 108, 96, 0.2)";
        dian[dian.length - 1].style.border = "none";
        dian[i].style.backgroundColor = "#FFFFFF";
        dian[i].style.border = "3px solid rgba(0, 0, 0, 0.3)";
    }

    if (leftSize <= -4720) {
        mia.style.left = "0px"
        leftSize = 0;
    } else {
        time = 1;
        mia.style.left = leftSize + "px";
    }
    if (leftSize % 590 == 0) {
        time = 2000;
    }
    setTimeout(mn, time);
}

mn();


/* var ul = document.getElementsByTagName("ul")[3];
ul.addEventListener("mouseover", function(e){
    var li = e.target;
    var eject = li.lastElementChild;
    console.log(eject)
    eject.style.width = "1000px";
}, false);

ul.addEventListener("mouseout", function(e){
    var li = e.target;
    var eject = li.lastElementChild;
    console.log(eject)
    eject.style.width = "0px";
}, false); */