var targets = {
    form: document.querySelector("#myform"),
    username: document.querySelector(".myusername"),
    userpassword: document.querySelector(".myuserpassword"),
    name: null,
    password: null,
    url: "http://localhost:8081/user/register",
    text: null,
    json: null
}
targets.form.addEventListener("submit", function (e) {
    e.preventDefault()
    targets.name = targets.username.value;
    targets.password = targets.userpassword.value;
    console.log(targets.url);
    sends(targets.name, targets.password);
}, false)

function sends(name, passwords) {
    var ajx = new XMLHttpRequest();
    console.log(targets.url);
    ajx.open("POST", targets.url, true);
    ajx.withCredentials = true
    var obj = {};
    obj.username = name;
    obj.password = passwords;
    targets.json = JSON.stringify(obj);
    ajx.onload = function () {
        console.log(document.cookie)
        console.log(ajx.responseText);
        targets.text = parseInt(ajx.responseText);
        console.log(targets.text);
        console.log("ssssssssssssssssss");
        console.log(typeof text);
        if (targets.text > 0 && ajx.readyState == 4 && ajx.status == 200) {
            window.alert("登录成功");
            window.location.href = "JDindex.html";
        } else {
            window.alert("用户名或密码错误,请重新登录");
        }
    }
    ajx.send(targets.json)
}

console.log(document.cookie)