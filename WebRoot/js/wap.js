/*
 *公共函数
 */
function $$(id) {
    return document.getElementById(id);
}

/*ajax请求模块*/
var h_ajax = function(type,url,param,callback) {
    var xmlhttp;
    if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }else{// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function(){
        if (xmlhttp.readyState === 4){
            if (xmlhttp.status === 200) {
                callback(xmlhttp.responseText);
            }else if (xmlhttp.status === 404) {
                alert("请求地址错误！");
            }else if (xmlhttp.status === 403) {
                alert("无请求权限！");
            }else if (xmlhttp.status === 504) {
                alert("链接超时，请稍后重试！");
            }else{
                alert("未知状态：" + xmlhttp.status);
            }
        }
    };
    xmlhttp.open(type, url, true);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.send(param);
};

/*清空提示信息*/
function clearTips(obj) {
    if (obj.value == obj.defaultValue)
        obj.value = '';
    obj.style.color = '#666';
}

/*出现提示信息*/
function showTips(obj) {
    if (obj.value == '') {
        obj.value = obj.defaultValue;
        obj.style.color = '#9c9c9c';
    }
}

/*关闭弹窗*/
function closeLayer(obj) {
    if ($$('shade'))
        $$('shade').style.display = 'none';
    if ($$('layer'))
        $$('layer').style.display = 'none';
}

/*
 *首页
 */
function showModule(obj) {
    var a = obj.getElementsByTagName('a')[0];
    var className = a.className;
    if (className == 'Down') {
        a.className = '';
        obj.parentNode.getElementsByTagName('ul')[0].style.display = 'table';
        a.title = '折叠';
        a.innerHTML = '折叠';
    }
    else {
        a.className = 'Down';
        obj.parentNode.getElementsByTagName('ul')[0].style.display = 'none';
        a.title = '打开';
        a.innerHTML = '打开';
    }
}

/*
 *帖子详情页
 */
function showShareList() {
    if ($$('shade'))
        $$('shade').style.display = 'block';
    if ($$('layer'))
        $$('layer').style.display = 'block';
}

function setCookie(c_name, value, exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = c_name + "=" + c_value;
}

/*关闭去玩广告*/
function closeQuwan() {
    $$("quwanDownload").style.display = 'none';
}

// 使用原生js 封装ajax
// 兼容xhr对象
function createXHR(){
  if(typeof XMLHttpRequest != "undefined"){ // 非IE6浏览器
    return new XMLHttpRequest();
  }else if(typeof ActiveXObject != "undefined"){   // IE6浏览器
    var version = [
          "MSXML2.XMLHttp.6.0",
          "MSXML2.XMLHttp.3.0",
          "MSXML2.XMLHttp",
    ];
    for(var i = 0; i < version.length; i++){
      try{
        return new ActiveXObject(version[i]);
      }catch(e){
        //跳过
      }
    }
  }else{
    throw new Error("您的系统或浏览器不支持XHR对象！");
  }
}
// 转义字符
function params(data){
  var arr = [];
  for(var i in data){
    arr.push(encodeURIComponent(i) + "=" + encodeURIComponent(data[i]));
  }
  return arr.join("&");
}
// 封装ajax
function ga_ajax(obj){
  var xhr = createXHR();
  obj.url = obj.url + "?rand=" + Math.random(); // 清除缓存
  obj.data = params(obj.data);      // 转义字符串
  if(obj.method === "get"){      // 判断使用的是否是get方式发送
    obj.url += obj.url.indexOf("?") == "-1" ? "?" + obj.data : "&" + obj.data;
  }
  // 异步
  if(obj.async === true){
    // 异步的时候需要触发onreadystatechange事件
    xhr.onreadystatechange = function(){
      // 执行完成
      if(xhr.readyState == 4){
        callBack();
      }
    }
  }
  xhr.open(obj.method,obj.url,obj.async);  // false是同步 true是异步 // "demo.php?rand="+Math.random()+"&name=ga&ga",
  if(obj.method === "post"){
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.send(obj.data);
  }else{
    xhr.send(null);
  }
  // xhr.abort(); // 取消异步请求
  // 同步
  if(obj.async === false){
    callBack();
  }
  // 返回数据
  function callBack(){
    // 判断是否返回正确
    if(xhr.status == 200){
      obj.success(xhr.responseText);
    }else{
      obj.Error("获取数据失败，错误代号为："+xhr.status+"错误信息为："+xhr.statusText);
    }
  }
}

//APP下载引导
(function() {
    //s20是代表20秒
    //h是指小时，如12小时则是：h12
    //d是天数，30天则：d30
    //setCookie("name", "hayden", "s20"); //在谷歌浏览器中需要在服务器中才能响应
    function setCookie(name, value, time) {
        var strsec = getsec(time);
        var exp = new Date();
        exp.setTime(exp.getTime() + strsec * 1);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }

    function getsec(str) {
        //alert(str);
        var str1 = str.substring(1, str.length) * 1;
        var str2 = str.substring(0, 1);
        if (str2 == "s") {
            return str1 * 1000;
        } else if (str2 == "h") {
            return str1 * 60 * 60 * 1000;
        } else if (str2 == "d") {
            return str1 * 24 * 60 * 60 * 1000;
        }
    }
    //读取cookies
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }
    //删除cookies
    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
    //下载app
    function downloadApp(elem) {
        document.getElementById(elem).addEventListener("click", function() {
            var appLink = "mofang://www.doyouhike.net/",
                appLinks = {
                    androidDownloadUrl: "http://www.doyouhike.net/appdownload/mofangapp/download",
                    downloadUrl: "http://www.doyouhike.net/appdownload/mofangapp/download",
                    iosDownloadUrl: "https://itunes.apple.com/us/app/mo-fang-she-qu/id655510481?ls=1&mt=8"
                },
                ua = navigator.userAgent;
            ua.indexOf("SAMSUNG") > 0 ||
                ua.indexOf("Samsung") > 0 ||
                ua.indexOf("GT-N7100") > 0 ||
                ua.indexOf("GT-N7102") > 0 ? window.location.href = "http://www.doyouhike.net/appdownload/mofangapp/download" : down(appLink, appLinks)
        });
        var b = navigator.userAgent,
            c = b.match(/android/i),
            d = b.match(/(ipad|iphone|ipod).*os\s([\d_]+)/i),
            down = function(a, e) {
                var f = +new Date,
                    g = function() {
                        var a = c && e.androidDownloadUrl ? e.downloadUrl : d && e.iosDownloadUrl ? e.iosDownloadUrl : e.downloadUrl;
                        a && (location.href = a)
                    },
                    h = function() {
                        g()
                    },
                    i = function(a) {
                        var b = {};
                        return b.isWeChat = /MicroMessenger/i.test(a),
                            b.isChrome = a.match(/Chrome/),
                            b.isChrome && (b.version = +a.match(/Chrome\/?(\d*)/)[1]),
                            b
                    }
                    (b),
                    j = function() {
                        setTimeout(function() {
                            var a = +new Date - f;
                            1e3 > a && h()
                        }, 800)
                    },
                    k = function() {
                        var b = document.createElement("div");
                        b.style.visibility = "hidden",
                        b.innerHTML = '<iframe src="' + a + '" scrolling="no" width="1" height="1"></iframe>',
                        document.body.appendChild(b),
                        j()
                    },
                    l = function() {
                        location.href = "mofang://www.doyouhike.net/",
                        j()
                    };
                c ? i.isWeChat ? k() : c ? l() : k() : d ? (j(),
                    window.location = a) : g()
            }
    }
    window.addEventListener('load', function() {
        if(window.location.href.indexOf('jhsign=')<0 && window.location.pathname == '/mobile'){//判断不是app跳转过来
            //立即下载磨房客户端
            downloadApp('appInDownload'); 
            if(!getCookie('app_check')){
              document.getElementById('appIn').style.display = 'block';
            }
            document.getElementById('appInDownload').addEventListener("click", function(e) {
              setCookie('app_check', true, "d7");
            });
            //继续访问触屏版
            document.getElementById('appInContinue').addEventListener("click", function(e) {
                e.preventDefault();
                document.getElementById('appIn').style.display = 'none';
                setCookie('app_check', true, "d7");
            });
            //底部app下载提示框
            if(!getCookie('app_alert')){
              document.getElementById('appAlert').style.display = 'block';
            }
            downloadApp('appAlertDownload');
            //底部app下载提示框关闭
            document.getElementById('appAlertClose').addEventListener("click", function(e) {
                e.preventDefault();
                document.getElementById('appAlert').style.display = 'none';
                setCookie('app_alert', true, "d7");
            });
        }else{
            setCookie('app_check', true, "d7");
            setCookie('app_alert', true, "d7");
        }
    });
})();