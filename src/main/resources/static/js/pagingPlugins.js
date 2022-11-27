function TurnPage(options) {
    // 当前页码
    this.currentPage = options.currentPage || 1;
    // 总页码
    this.allPage = options.allPage || 1;
    // 翻页结构插入的包裹层
    this.wrap = options.wrap || document.body;
    // 当前翻页包裹的dom节点
    this.turnPageWrapper = null;
    // 切换页码的回调函数
    this.changePage = options.changePage || function () {};
}
TurnPage.prototype.init = function () {
     this.fillHTML();
     this.bindEvent();
}
// 填充翻页的结构
TurnPage.prototype.fillHTML = function () {
    // 如果之前没有创建过翻页包裹层 那么创建一个包裹层
    if (!this.turnPageWrapper) {
        var turnPageWrapper = document.createElement('ul');
        turnPageWrapper.className = 'turn-page-wrapper';
        this.turnPageWrapper = turnPageWrapper;
        this.wrap.appendChild(this.turnPageWrapper);
    }
    // 如果之前有创建过结构 就要清空里面的内容
    this.turnPageWrapper.innerHTML = ''
    // 插入上一页按钮
    if (this.currentPage > 1) {
        var oLi = document.createElement('li');
        oLi.innerText = '上一页';
        oLi.className = 'prev';
        this.turnPageWrapper.appendChild(oLi)
    }
    // 插入第一页
    var firstLi = document.createElement('li');
    firstLi.innerText = 1;
    firstLi.className = 'num';
    if (this.currentPage == 1) {
        firstLi.classList.add('current-page');
    }
    this.turnPageWrapper.appendChild(firstLi);
    // 前面的省略号
    if(this.currentPage - 2 > 2) {
        var oSpan = document.createElement('span');
        oSpan.innerText = '...';
        this.turnPageWrapper.appendChild(oSpan)
    }
    // 中间五页
    for (var i = this.currentPage - 2; i <= this.currentPage + 2; i ++) {
        if (i > 1 && i < this.allPage) {
            var oLi = document.createElement('li');
            oLi.innerText = i;
            oLi.className = 'num';
            if (this.currentPage == i) {
                oLi.classList.add('current-page');
            }
            this.turnPageWrapper.appendChild(oLi)
        }
    }
    // 后面的省略号
    if(this.currentPage + 2 < this.allPage - 1) {
        var oSpan = document.createElement('span');
        oSpan.innerText = '...';
        this.turnPageWrapper.appendChild(oSpan);
    }
    // 最后一页
    if(this.allPage != 1) {
        var lastLi = document.createElement('li');
        lastLi.innerText = this.allPage;
        lastLi.className = 'num';
        if (this.currentPage == this.allPage) {
            lastLi.classList.add('current-page');
        }
        this.turnPageWrapper.appendChild(lastLi);
    }
    // 下一页
    if (this.currentPage < this.allPage) {
        var oLi = document.createElement('li');
        oLi.innerText = '下一页';
        oLi.className = 'next';
        this.turnPageWrapper.appendChild(oLi)
    }
    
}
// 绑定事件
TurnPage.prototype.bindEvent = function () {
    // 保存翻页实例的this指向（指向的是翻页组件的结构）
    var self = this;
	
    // 绑定事件
    this.turnPageWrapper.onclick = function (e) {
		
		console.log(self);
        // 点击上一页按钮
        if (e.target.classList.contains('prev')) {
            self.currentPage --;
            self.fillHTML();
            self.changePage(self.currentPage)
            // 点击下一页按钮
			sends((self.currentPage - 1)*bugs.content, bugs.content)
        } else if (e.target.classList.contains('next')) {
            self.currentPage ++;
            self.fillHTML()
            self.changePage(self.currentPage)
            // 点击页码
			sends((self.currentPage - 1)*bugs.content, bugs.content)
        } else if (e.target.tagName == 'LI') {
            var page = parseInt(e.target.innerText);
            self.currentPage = page;
            self.fillHTML()
            self.changePage(self.currentPage)
			sends((self.currentPage - 1)*bugs.content, bugs.content)
        }
    }
}