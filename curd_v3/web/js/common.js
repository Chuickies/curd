function my$(id) {
    return document.getElementById(id);
}

var body = document.body;

function getScroll() {
    return {
        left: window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft || 0,
        top: window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0
    }
}

function getStyle(element, attr) {
    return window.getComputedStyle ? window.getComputedStyle(element, null)[attr] : element.currentStyle[attr] || 0;
}

/**
 * 变速移动函数
 * @param element 任意一个元素
 * @param json json数组对象
 * @param fn 回调函数
 */
function animationOfSpeedChangeV1(element, json, fn) {
    //如果有定时器清除定时器
    clearInterval(element.timeId);
    element.timeId = window.setInterval(function () {
        var flag = true;
        for (var attr in json) {
            if (attr === "opacity") {
                //获取元素的当前的位置值
                var current = getStyle(element, attr) * 100;
                //目标位置的值
                var target = json[attr] * 100;
                //每次移动的距离
                var step = (target - current) / 10;
                //判断step的是否小于0
                step = step > 0 ? Math.ceil(step) : Math.floor(step);
                //移动后的值
                current += step;
                element.style[attr] = current / 100;

            } else if (attr === "zIndex") {
                element.style[attr] = json[attr];
            } else {

                //获取元素的当前的位置值
                var current = parseInt(getStyle(element, attr));
                //目标位置的值
                var target = json[attr];
                //每次移动的距离
                var step = (target - current) / 10;
                //判断step的是否小于0
                step = step > 0 ? Math.ceil(step) : Math.floor(step);
                //移动后的值
                current += step;
                element.style[attr] = current + "px";
            }
            if (current !== target) {
                flag = false;
            }
            if (flag) {
                clearInterval(element.timeId);
                if (fn) {
                    fn();
                }
            }
        }
        //测试代码
        console.log("目标位置:" + target + ",当前位置:" + current + ",每次移动步数:" + step);
    }, 50)
}

function confirmDelete(name) {
    //点击时设置遮挡层显示出来
    my$("mask").style.display = "block";
    //创建最外层盒子
    var element = document.createElement("div");
    element.className = "popup";
    animationOfSpeedChangeV1(element, {"opacity": 1});
    //将最外层div装进body
    body.appendChild(element);
    //创建title部分的盒子
    var tbObj = document.createElement("div");
    var tbDivObj = element.appendChild(tbObj);
    tbDivObj.className = "title";
    tbDivObj.innerHTML = "确认删除用户 &nbsp" + name;
    var bd = document.createElement("div");
    var bdDivObj = element.appendChild(bd);
    var confirmBtn = document.createElement("a");
    confirmBtn.className = "confirm";
    confirmBtn.innerHTML = "确认";
    confirmBtn.onclick = function () {
        window.location.href = "user?action=delete&id=" + id;
    };
    bdDivObj.appendChild(confirmBtn);
    var cancelBtn = document.createElement("a");
    cancelBtn.className = "confirm";
    cancelBtn.innerHTML = "取消";
    cancelBtn.onclick = function () {
        body.children[body.children.length - 1].remove();
        my$("mask").style.display = "none";
    };
    bdDivObj.appendChild(cancelBtn);
}