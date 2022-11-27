//console.log(domText)
//获取导航栏元素对象,添加事件委托
var navbar = document.querySelector("#content #left ul")
var temporary = ""
navbar.addEventListener("click", function (e) {
    temporary = e.target.text
    console.log(temporary)
}, false)


//获取购物车元素对象
var shoppingCar = document.querySelector("#seek #center #box .shoppingCar")
shoppingCar.addEventListener("click", function () {


}, false)








