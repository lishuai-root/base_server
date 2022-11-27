var targets = {
    urls: null,
    arr: null,
    last: null,
    mymolds: null,
    ul: document.querySelector(".commodity-warpper"),
    url: null,
    stt: "",
    objs: null,
    text: null,
    obj: null,
    service: "../images/phoneImages/face.png",
    count: 1,
    cut: null
}
/* 
	对编码的href进行解码,解决href传参时中文出现乱码的问题
 */
targets.urls = decodeURI(window.location.href)
console.log(targets.urls)
targets.arr = targets.urls.split("=")
console.log(targets.arr)
targets.last = parseInt(targets.arr[1].charAt(targets.arr[1].length - 1))
console.log(targets.last)
targets.mymolds = targets.arr[1].slice(0, targets.arr[1].length - 1)
console.log(targets.mymolds)

function addEven() {
    for (var i = 0; i < targets.obj.length; i++) {
        //console.log(targets.obj[i])
        if (targets.obj[i].shop == null) {
            targets.obj[i].shop = ""
        }
        targets.stt += `
			<li class="commodity">
			    <div class="war">
					<div class="collect" value="${targets.obj[i].commodityId}">加入<br>购物车</div>
					 <a href="#"><img src= ${targets.obj[i].images} class="images"></a>
					 <p>
					  <img src= ${targets.obj[i].images}>
					  <img src= ${targets.obj[i].images}>
					  <img src= ${targets.obj[i].images}>
					 </p>
					 <p class="price">￥${targets.obj[i].price}.00</p>
					 <p class="text"><a href="#"> ${targets.obj[i].synopsis}</ a></p>
					 <p class="appraise"><a href="#"> ${targets.obj[i].sales}万+</a><span>条评价</span></p>
					 <p class="shopping"><span class="shop"><a href="#"> ${targets.obj[i].shop}</a><img src=${targets.service}></span></p>
					<p class="autarky"><span class="zi">自营</span> <span class="sh">放心购</span></p>
			    </div>
			</li>
		`
    }
    targets.ul.innerHTML = ""
    targets.ul.innerHTML = targets.stt
    targets.stt = ""
}

function sends(jumps, limits) {

    console.log(document.getElementsByTagName("head")[0])
    console.log(document.querySelector("body title"))
    /* $(function(){
        $("head").removeChild($("head title"))
        $("head").innerHTML += `<title>${targets.mymolds}-商品搜索-京东</title>`
    }) */

    var ajx = new XMLHttpRequest()
    var objs = {}
    objs.mold = targets.mymolds || "手机"
    objs.jump = jumps || 0
    objs.limit = limits || 20
    console.log(objs)
    targets.cut = JSON.stringify(objs)

    document.getElementsByTagName("head")[0].removeChild(document.querySelector("head title"))
    document.getElementsByTagName("head")[0].innerHTML += `<title>${objs.mold}-商品搜索-京东</title>`

    if (targets.last === 1) {
        targets.url = "http://localhost:8081/search/commodity"
    } else {
        targets.url = "http://localhost:8081/seek/commodity"
    }

    ajx.open("POST", targets.url, false)
    ajx.setRequestHeader("contentType", "application/x-www-form-urlencoded")
    ajx.onload = function () {
        targets.text = ajx.responseText
        if (targets.text.length < 10 || ajx.readyState != 4 && ajx.status != 200) {
            targets.ul.innerHTML = targets.text
        } else {
            targets.obj = JSON.parse(targets.text)
            console.log(Math.ceil(targets.obj[0].count / limits))
            targets.count = Math.ceil(targets.obj[0].count / limits)
            console.log(targets.obj)
            addEven()
        }
    }
    console.log(targets.cut)
    ajx.send(targets.cut)
}


function fn() {
    targets.ul.addEventListener("click", function (e) {
        let commodityId = e.target.getAttribute("value")
        if (commodityId != null) {
            console.log(commodityId)
            addCollect(commodityId)
        }

    }, false)
}


function addCollect(commodityId) {
    var ajx = new XMLHttpRequest()
    var url = "http://localhost:8081/shopping/add?commodityId="

    ajx.open("get", url + commodityId, true)
    //ajx.withCredentials = true
    ajx.onload = function () {
        console.log(ajx.responseText)
        if (ajx.responseText != "true" || ajx.readyState != 4 || ajx.status != 200) {
            alert("添加失败")
        } else {
            alert("添加成功")

        }
    }
    ajx.send()
}





