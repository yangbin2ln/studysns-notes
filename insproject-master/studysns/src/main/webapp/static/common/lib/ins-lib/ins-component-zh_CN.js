/**
 * 工具类 
 */ 
var Utils = {
	
	
	/**
	 * 判断浏览器是否为IE
	 */
	isIE: function(){
		return !!window.ActiveXObject
	},
	
	/**
	 * 判断浏览器是否是IE8
	 * @return {}
	 */
	isIE8: function(){
		var userAgent = navigator.userAgent.toLowerCase();
		return userAgent.indexOf('msie')!=-1 && userAgent.indexOf('8.0')!=-1;
	},
	
	/**
	 * 小于等于IE8
	 * @return {Boolean}
	 */
	ltIE8: function(){
		var DEFAULT_VERSION = "8.0";
		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("msie")>-1;
		var safariVersion;
		if(isIE){
		    safariVersion =  ua.match(/msie ([\d.]+)/)[1];
		    if(safariVersion <= DEFAULT_VERSION ){
		      	return true;
		    }else{
		        return false;
		    }
		}
		return false;
	},
	
	/**
	 * html编码
	 * @param {} str
	 * @return {String}
	 */   
    htmlEncode: function (str){  
          var s = "";
          if(str.length == 0) return "";
          s = str.replace(/&/g,"&#38;");
		  s = s.replace(/\"/g,"&#34;");
		  s = s.replace(/\'/g,"&#39;");         
          s = s.replace(/</g,"&#60;");
          s = s.replace(/>/g,"&#62;");         
          return s;  
    },
   
    /**
     * html解码
     * @param {} str
     * @return {String}
     */
    htmlDecode: function (str){  
          var s = "";
          if(str.length == 0) return "";
          s = str.replace(/&#62;/g,">");
          s = s.replace(/&#60;/g,"<");
          s = s.replace(/&#39;/g,"'");
          s = s.replace(/&#34;/g,"\"");
          s = s.replace(/&#38;/g,"&");        
          return s;  
    },
	
	/**
	 * 给对象绑定属性或方法
	 */
	apply: function(object, config){
		if (object && config && typeof config === 'object') {
            var i;
            for (i in config) {
                object[i] = config[i];
            }            
        }
        return object;
	},
	
	/**
	 * 给对象绑定属性或方法(如果存在，则不绑定)
	 */
	applyIf: function(object, config) {
        var property;
        if (object) {
            for (property in config) {
                if (object[property] === undefined) {
                    object[property] = config[property];
                }
            }
        }
        return object;
    },       
    
    /**
     * 克隆对象
     * @param {} item
     * @return {}
     */
    clone: function(item) {
        if (item === null || item === undefined) {
            return item;
        }
        if (item.nodeType && item.cloneNode) {
            return item.cloneNode(true);
        }
        var type = toString.call(item),
            i, _item, key;   
        if (type === '[object Date]') {
            return new Date(item.getTime());
        }        
        if (type === '[object Array]') {
            i = item.length;
            _item = [];
            while (i--) {
                _item[i] = Utils.clone(item[i]);
            }
        }
        else if (type === '[object Object]' && item.constructor === Object) {
            _item = {};
            for (key in item) {
                _item[key] = Utils.clone(item[key]);
            }            
        }
        return _item || item;
    },
    
    /**
     * 给元素添加事件
     * @param {} element
     * @param {} type
     * @param {} fn
     */
    on: function (element, type, fn){
    	if (element.addEventListener) {
    		element.addEventListener(type, fn, false);
    	}else{
    		element.attachEvent('on' + type, fn);
    	}          
    },
    
    /**
     * 取消元素的事件
     * @param {} element
     * @param {} type
     * @param {} fn
     */
    off: function (element, type, fn) {       
        if (element.removeEventListener) {
            element.removeEventListener(type, fn, false);
        } else {           
           element.detachEvent('on' + type, fn);
        }        
    },  
    
    /**
     * 阻止事件冒泡
     * @param {} event
     */
    stopPropagation: function(event){
    	if (event.stopPropagation){
       		event.stopPropagation();
     	}else{
      		event.cancelBubble = true;
      	}
    },
    
    /**
     * 阻止浏览器默认事件
     * @param {} event
     */
    preventDefault: function(event){
    	if(event.preventDefault){
      		event.preventDefault();     
      	}else{       
      		event.returnValue = false;
     	}
    },
    
    /**
     * 获取事件的引用
     * @param {} event
     */
    getEvent: function(event){
    	return event ? event : window.event;
    },
    
    /**
     * 获取事件的目标元素的引用
     * @param {} event
     * @return {}
     */
    getEventTarget: function(event){    
    	return event.target || event.srcElement;
    },
    
    /**
     * 获取url的参数值
     * @param {} key
     * @return {String}
     */
    getParameter: function(key){
		var parameters = unescape(window.location.search.substr(1)).split("&");
		for( var i = 0 ; i < parameters.length ; i++ )
		{
			var paramCell = parameters[i].split("=");
			if( paramCell.length == 2 && paramCell[0].toUpperCase() == key.toUpperCase() )
			{
				return paramCell[1];
			}
		}
		return null;
	},  
	
	/**
	 * 替换url中的参数
	 * @param {} paramName
	 * @param {} replaceWith
	 * @param {} url
	 * @return {}
	 */
	replaceParamVal: function(paramName, replaceWith, url) {
		var oUrl = location.href.toString();
		if(url != undefined && url != null){
			oUrl = url;
		}	
		var nUrl = null;
		if(oUrl.indexOf(paramName + "=") != -1){
			var re=eval('/('+ paramName+'=)([^&]*)/gi');
			nUrl = oUrl.replace(re,paramName+'='+replaceWith);
		}else{
			nUrl = oUrl; 
			if(nUrl.indexOf("?") != -1){
				nUrl += "&" + paramName + "=" + replaceWith;
			}else{
				nUrl += "?" + paramName + "=" + replaceWith;
			}
		}
		return nUrl;
	},
    
	/**
	 * 动态添加多个文件（按顺序加载）
	 * @param {} path
	 */
	require : function(path, callback, index){		
		
		//第一次进入，index为空，设置为0
		if(index==undefined || index==null){
			index = 0;
		}
		//判断文件类型
		var type = Utils.getExtName(path[index]);	
		
		//index到了数组的最后一个元素，直接执行加载单个js，加载完成执行结束
		if(index == path.length - 1){
			if(type === 'css'){
				Utils.addCss(path[index], callback);
			}else if(type === 'js'){
				Utils.addScript(path[index], callback);
			}else{				
				Utils.getTemplate(path[index], callback);
			}			
		}
		//先执行加载单个文件，执行成功后回调方法内给index++，并且递归调用自身
		else{
			if(type === 'css'){
				Utils.addCss(path[index], function(){
					index++;
					Utils.require(path, callback, index);
				});	
			}else if(type === 'js'){
				Utils.addScript(path[index], function(){
					index++;
					Utils.require(path, callback, index);
				});	
			}else{
				Utils.getTemplate(path[index], function(){
					index++;
					Utils.require(path, callback, index);
				});	
			}				
		}		
	},
	
	/**
	 * 动态加载单个js(异步)
	 */
	addScript: function(path, callback){		
		/*$.ajax({
			type : "GET",
			url : path,			
			//async : false,		
			success : function(data) {					
				if(callback){
					callback();
				}
			}
		});*/	
		path = path + '?v=' + STATIC_VERSION;
		var head = document.getElementsByTagName("head")[0];
		var scripts = head.getElementsByTagName("script");
		var hasThisScript = false;
		for(var i=0;i<scripts.length;i++){			
			var src = scripts[i].getAttribute("src");			
			if(src == path){
				hasThisScript = true;
				break;
			}
		}
		if(hasThisScript){
			if(callback && typeof callback === "function") {
               callback();
            }
		}
		else{
			var js = document.createElement('script');
		    js.setAttribute('src', path);
		    js.setAttribute('type', 'text/javascript');
		    js.onload = js.onreadystatechange = function() {
		        if (!this.readyState || this.readyState === 'loaded' || this.readyState === 'complete') {
		                if(callback && typeof callback === "function") {
		                   callback();
		                }
		            js.onload = js.onreadystatechange = null;
		        }
		    };
		    head.appendChild(js);	
		}
	},
	
	/**
     * 动态添加单个css
     * @param {} path
     */
	addCss: function(path, callback){	
		path = path + '?v=' + STATIC_VERSION;
		var head = document.getElementsByTagName("head")[0];
		var links = head.getElementsByTagName("link");
		var hasThisCss = false;
		for(var i=0;i<links.length;i++){			
			var href = links[i].getAttribute("href");			
			if(href == path){
				hasThisCss = true;
				break;
			}
		}
		if(!hasThisCss){
			var link = document.createElement("link");
			link.setAttribute("type","text/css");
			link.setAttribute("rel","stylesheet");
			link.setAttribute("href", path);
			head.appendChild(link);
		}
		if(callback && typeof callback === "function") {
           callback();
        }	
	},	
	
	templateCache: [],
	/**
	 * 动态加载单个模板文件
	 * @param {} path
	 * @param {} callback
	 */
	getTemplate: function(path, callback){
		path = path + '?v=' + STATIC_VERSION;
		var content = "";		
		for(var i=0;i<Utils.templateCache.length;i++){		
			var view = Utils.templateCache[i];
			if(path == view.path){				
				if(callback && typeof callback === "function") {
	               callback(view.content);
	            }
				return;
			}
		}		
		Utils.Messager.send({
			type : "GET",
			url: path,
			cache: true,
			onSuccess: function(data){
				content = data;
				var view = {
					path: path,
					content: content
				}
				Utils.templateCache.push(view);
				if(callback && typeof callback === "function") {
	               callback(content);
	            }
			}
		});		
	},	
	    
    /**
	 * 是否不为空
	 * @param {} val
	 * @return {}
	 */
	isNotEmpty:  function(val){
   		return !this.isEmpty(val);
    },
    
    /**
     * 是否为空
     * @param {} val
     * @return {Boolean}
     */
    isEmpty: function(val){
        if ((val==null || typeof(val)=="undefined")|| (typeof(val)=="string"&&val==""&&val!="undefined")){
           return true;
        }else{
            return false;
        }
    },
    
    /**
     * 判断对象是否为数字
     * @param {} obj
     * @return {}
     */
    isNumber: function(obj){
    	return !isNaN(obj);    	
    },
    
    /**
     * 循环数组或对象
     * @param {} obj
     * @param {} iterator
     * @param {} context
     */
    each : function(obj, iterator, context) {
        if (obj == null) return;
        if (obj.length === +obj.length) {
            for (var i = 0, l = obj.length; i < l; i++) {
                if(iterator.call(context, obj[i], i, obj) === false)
                    return false;
            }
        } else {
            for (var key in obj) {
                if (obj.hasOwnProperty(key)) {
                    if(iterator.call(context, obj[key], key, obj) === false)
                        return false;
                }
            }
        }
    },
    
    /**
     * 对象是否在数组中
     * @param {} arr
     * @param {} item
     * @return {}
     */
    inArray : function(arr,item){
        var index = -1;
        this.each(arr,function(v,i){
            if(v === item){
                index = i;
                return false;
            }
        });
        return index;
    },
    
    /**
     * 向数组中追加元素（如果存在则不追加）
     * @param {} arr
     * @param {} item
     */    
    pushItem : function(arr,item){
        if(this.inArray(arr,item)==-1){
            arr.push(item)
        }
    },
    
    /**
     * 移除数组array中所有的元素item
     * @method removeItem
     * @param { Array } array 要移除元素的目标数组
     * @param { * } item 将要被移除的元素
     * @remind 该方法的匹配过程使用的是恒等“===”
     * @example
     * ```javascript
     * var arr = [ 4, 5, 7, 1, 3, 4, 6 ];
     *
     * UE.utils.removeItem( arr, 4 );
     * //output: [ 5, 7, 1, 3, 6 ]
     * console.log( arr );
     *
     * ```
     */
    removeItem:function (array, item) {
        for (var i = 0, l = array.length; i < l; i++) {
            if (array[i] === item) {
                array.splice(i, 1);
                i--;
            }
        }
    },
    
    /**
     * 全部替换
     * @param {} str
     * @param {} a
     * @param {} b
     * @return {}
     */
    replaceAll: function(str, a, b){
    	raRegExp = new RegExp(a, "g"); 
    	return str.replace(raRegExp, b)
    },
    
    /**
     * 字符串前后空格处理
     * @param {} str
     * @return {}
     */
    trim: function (str) {
        return str.replace(/(^\s*)|(\s*$)/g,"");
    },
    
    /**
     * 获取文件扩展名
     * @param {} path
     */
    getExtName: function(path){
    	return path.substring(path.lastIndexOf(".")+1).toLowerCase();
    },
    
    /**
     * 将数值四舍五入(保留2位小数)后格式化成金额形式
     *
     * @param num 数值(Number或者String)
     * @return 金额格式的字符串,如'1,234,567.45'
     * @type String
     */
    formatCurrency: function(num) {
        num = num.toString().replace(/\$|\,/g,'');
        if(isNaN(num))
            num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num*100+0.50000000001);
        cents = num%100;
        num = Math.floor(num/100).toString();
        if(cents<10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
            num = num.substring(0,num.length-(4*i+3))+','+
                    num.substring(num.length-(4*i+3));
        // return (((sign)?'':'-') + num + (cents == 0 ? "" : '.' + cents) );
          return (((sign)?'':'-') + num + ('.' + cents) );
    },    
   
    /**
     * 将日期格式化成字符串
     * @param date 日期
     * @param pattern 格式
     * @return String
     * 用法：
     * Pattern                       Sample
	 * YYYY-MM-DD hh:mm:ss          2001-07-04 12:08:56
	 * YYYY-MM-DDThh:mm:ss          2001-07-04T12:08:56
	 * YYYY/MM/DDThh:mm:ss          2001/07/04T12:08:56
	 * YYYY年MM月DD日,周w            2008年12月12日,周3
	 * hh:mm                          12:08
     */
	formatDate: function(data, pattern) {
		function dl(data, format) {//3
			format = format.length;
			data = data || 0;
			return format = 1 ? data : String(Math.pow(10, format) + data)
					.substr(-format);
		}
		return pattern.replace(/([YMDhsm])\1*/g, function(format) {
			switch (format.charAt()) {
			case 'Y':
				return dl(data.getFullYear(), format);
			case 'M':
				return Utils.leftZeroPad(dl(data.getMonth() + 1, format), 2);
			case 'D':
				return Utils.leftZeroPad(dl(data.getDate(), format), 2);
			case 'w':
				return data.getDay() + 1;
			case 'h':
				return Utils.leftZeroPad(dl(data.getHours(), format), 2);
			case 'm':
				return Utils.leftZeroPad(dl(data.getMinutes(), format), 2);
			case 's':
				return Utils.leftZeroPad(dl(data.getSeconds(), format), 2);
			}
		});
	},	
	 
    /**
     * 字符串左补零     
     * @param val
     * @return minLength
     * @return String
     */
    leftZeroPad: function(val, minLength){
    	var MANY_ZEROS = "00000000000000000000";
		if (typeof (val) != "string")
			val = String(val);
		return (MANY_ZEROS.substring(0, minLength - val.length)) + val;
    },    
    
    
    /**
     * 获取uuid
     * @return {}
     */
    getUUID: function() {
	    var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "-";
	  
	    var uuid = s.join("");
	    return uuid;
	}
	
};

/**
 * Map类 key 不能重复
 * 用法 var map = new Utils.Map(); 
 */
Utils.Map = function(){
	this.map = {};	
	this.length = 0;
};
Utils.Map.prototype = {
	
	put: function(key, value) {
	   var me = this;
       var map = me.map;
       if(!me.containsKey(key)){
       		++me.length;	       
       }
       map[key] = value;
       return me;
    },
    
	get: function(key) {
        var map = this.map;
        return map.hasOwnProperty(key) ? map[key] : undefined;
    },
    
    containsKey: function(key) {
        var map = this.map;
        return map.hasOwnProperty(key) && map[key] !== undefined;
    },
    
    removeAtKey: function(key) {
        var me = this;           
        if (me.containsKey(key)) {           
            delete me.map[key];
            --me.length; 
            return true;
        }
        return false;
    },
    
    clear: function(){
    	var me = this;
    	me.map = {};
        me.length = 0;
        return me;
    },
    
    getKeys: function() {
        return this.getArray(true);
    },
    
    getValues: function() {
        return this.getArray(false);
    },
    
    getArray: function(isKey) {
        var arr = [],
            key,
            map = this.map;
        for (key in map) {
            if (map.hasOwnProperty(key)) {
                arr.push(isKey ? key : map[key]);
            }
        }
        return arr;
    },
    
    each: function(fn, scope) {        
        var items = Utils.apply({}, this.map),
            key,
            length = this.length;
        scope = scope || this;
        for (key in items) {
            if (items.hasOwnProperty(key)) {
                if (fn.call(scope, key, items[key], length) === false) {
                    break;
                }
            }
        }
        return this;
    }
    
};

/**
 * 原生ajax通讯类
 * @type 
 */
Utils.Messager = {	
	/**
	 * cfg.url		default null
	 * cfg.method	type POST
	 * cfg.data 	default null
	 * cfg.sync		同步 default false
	 * cfg.cache	default false
	 * cfg.contentType	default application/x-www-form-urlencoded
	 * cfg.onSuccess	default null
	 * cfg.onError		default null
	 * @param {} cfg
	 */
	send: function(cfg){
		
		var me = this;
		
		var http_request = me.makeRequest();
		
		cfg = me.buildCfg(cfg);  	
		        
		http_request.onreadystatechange = function(){ 
			me.readyStateChange(http_request, cfg)
		};
		
        http_request.open(cfg.method, cfg.url, cfg.async);
        
        //3.发送请求
	    if(cfg.method === 'GET')    
	        http_request.send(null);
	    else{
	        http_request.setRequestHeader("Content-type", cfg.contentType);
	        http_request.send(cfg.data);
	    }   	     
		
	},
	
	makeRequest: function(){		
		var http_request = false; 
        if (window.XMLHttpRequest) {
            http_request = new XMLHttpRequest();           
        } else if (window.ActiveXObject) {
            try {
                http_request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    http_request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        }
        return http_request;
	},
	
	buildCfg: function(cfg){
		
		cfg.method = cfg.type==undefined||cfg.type==null ? 'POST' : cfg.type.toUpperCase();;
        cfg.async = cfg.sync==undefined||cfg.sync==null ? true : !cfg.sync;
        cfg.cache = cfg.cache==undefined||cfg.cache==null ? false : cfg.cache;
        cfg.contentType = cfg.contentType==undefined||cfg.contentType==null ? 'application/x-www-form-urlencoded' : cfg.contentType;
             
        if(!cfg.cache){
        	if(cfg.url.indexOf('?') != -1){
        		cfg.url += '&';
        	}else{
        		cfg.url += '?';
        	}
        	cfg.url += 't=' + new Date().getTime();
        }
        
        if(typeof cfg.data === 'object'){    //处理 data
	        var str = '';
	        for(var key in cfg.data){
	            str += key + '=' + cfg.data[key] + '&';
	        }
	        cfg.data = str.substring(0, str.length - 1);
    	}        
        
		return cfg;
	},
	
	readyStateChange: function(http_request, cfg){
		var me = this;
		if (http_request.readyState == 4) {
            if (http_request.status == 200 || http_request.status == 304) {
                if(cfg.onSuccess){   
                	cfg.onSuccess(http_request.responseText);
                	//cfg.onSuccess.call(me, http_request.responseText);
                }
            } else {
               if(cfg.onError){
                	cfg.onError();
                }
            }
        }
	}	
},

/**
 * 数字处理类
 * @type 
 */
Utils.Math = {
	//获取范围内随机数(只适用于整数)
	random : function(begin,end){
		return Math.floor(Math.random()*(end-begin))+begin;			
	},
	
	//加法函数，用来得到精确的加法结果 		
	add : function(arg1,arg2){
		var r1,r2,m; 
		try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
		try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
		m=Math.pow(10,Math.max(r1,r2)) 
		return (arg1*m+arg2*m)/m; 
	},
	
	//减法函数，用来得到精确的减法结果 
	sub : function(arg1,arg2){
		var r1,r2,m,n;
	    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	    m=Math.pow(10,Math.max(r1,r2));
	    //last modify by deeka
	    //动态控制精度长度
	    n=(r1>=r2)?r1:r2;
	    return ((arg1*m-arg2*m)/m).toFixed(n);
	},
	
	//乘法函数，用来得到精确的乘法结果 
	mul : function(arg1,arg2){
		var m=0,s1=arg1.toString(),s2=arg2.toString(); 
		try{m+=s1.split(".")[1].length}catch(e){} 
		try{m+=s2.split(".")[1].length}catch(e){} 
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m); 		
	},
	
	//除法函数，用来得到精确的除法结果 
	div : function(arg1,arg2){
		var t1=0,t2=0,r1,r2; 
		try{t1=arg1.toString().split(".")[1].length}catch(e){} 
		try{t2=arg2.toString().split(".")[1].length}catch(e){} 
		with(Math){ 
			r1=Number(arg1.toString().replace(".","")) 
			r2=Number(arg2.toString().replace(".","")) 
			return (r1/r2)*pow(10,t2-t1); 
		} 
	}
},

/**
 * cookie操作类
 * @type 
 */
Utils.Cookie = {
	/***************
	 判断cookie是否开启
	 返回 boolean类型
	 **************/
	openCookie : function() {
		//判断cookie是否开启
		var cookieEnabled = (navigator.cookieEnabled) ? true : false;
		//如果浏览器不是ie4+或ns6+
		if (typeof navigator.cookieEnabled == "undefined" && !cookieEnabled) {
			document.cookie = "testcookie";
			cookieEnabled = (document.cookie == "testcookie") ? true
					: falsedocument.cookie = "";
		}

		if (cookieEnabled) {
			return true;
		} else {
			return false;
		}
	},

	/**
	 * 设置cooike
	 * @param name 
	 * @param value
	 * @param minute 过期时间  分钟
	 */
	setCookie : function(name, value, minute) {
		//document.cookie = name+"="+value
		date = new Date();
		//默认过期时间为一天
		if(minute ==null || minute == undefined){
			minute = 1440;
		}
		date.setTime(date.getTime() + 60 * minute * 1000);
		document.cookie = name
				+ "="
				+ escape(value)
				+ ";expires="
				+ date.toGMTString() + ";path=/";
	},

	/***************
	 * 简单的读取Cookie
	 **************/
	getCookie : function(name) {
		var re = new RegExp(name + "=[^;]+", "i");
		if (document.cookie.match(re)) {
			return document.cookie.match(re)[0].split("=")[1];
		} else {
			return "";
		}
	}
}
;/**
 * 消息框
 */
var Message = {
	/**
	 * 展示消息对话框
	 * 
	 * @param title
	 * @param content
	 * @param type
	 */
	msg : function(content, type, fn){
		var title = "提示";
		if(type==undefined || type==null){
			type = 'INFO';
		}		
		var icon = Ext.Msg.INFO;//默认为消息类型
		if(type.toUpperCase() === "INFO"){
			icon = Ext.Msg.INFO;
		}else if(type.toUpperCase() === "WARN"){
			icon = Ext.Msg.WARNING;
			title = "警告";
		}else if(type.toUpperCase() === "ERROR"){
			icon = Ext.Msg.ERROR;
			title = "错误";
		}
		
		Ext.Msg.show({
		   title: title,
		   msg: content,
		   buttons: Ext.MessageBox.OK,
		   icon: icon,
		   fn: fn==undefined||fn==null ? function(){} : fn
	   });
	},
	 
	/**
	 * 输入对话框
	 * 
	 * @param title
	 * @param content
	 * @param onFinish
	 */
	input : function(title, onFinish){
		
		Ext.Msg.prompt(title, '', function(btn, val){
			var flag = false;
			if(btn.toUpperCase()==='OK'){
				flag = true;
			}
			onFinish(flag, val);
		});
	},
	
	/**
	 * 显示询问对话框
	 * 
	 * @param title
	 * @param content
	 * @param onSelect
	 */
	ask : function(content, onSelect){
		
		Ext.Msg.confirm('询问', content, function(btn){
			var flag = false;
			if(btn.toUpperCase()==='YES'){
				flag = true;
			}
			onSelect(flag);
		});
	}	
}
;/**
 * 顶部消息
 */
;TopMessage = function(){
    var msgCt;

    return {
    	
    	info : function(content){
    		TopMessage.msg(content, '消息', 'info');
    	},
    	
    	warn : function(content){
    		TopMessage.msg(content, '警告', 'warn');
    	},
    	
    	error: function(content){
    		TopMessage.msg(content, '错误', 'error');
    	},  
    	
        msg : function(content, title, type) {                 	
            if (msgCt) {
                document.body.appendChild(msgCt.dom);
            } else {
                msgCt = Ext.DomHelper.append(document.body, {id:'top-msg-div'}, true);
            }
          
            var html = "";
            
            if(title==undefined || title==null){
            	title = '消息';
            }
            
            var box = document.createElement("div");            
        	box.setAttribute("class",'top-msg ' + Ext.baseCSSPrefix + 'border-box');   
        	
        	if(type!=undefined && type!=null){
        		if(type.toUpperCase() === 'INFO'){ 
                	box.style.border = "2px solid #3892d3";
                	box.style.color = "#3892d3";
                }else if(type.toUpperCase() === 'WARN'){
                	box.style.border = "2px solid #eb7e23";
                	box.style.color = "#eb7e23";                	
                }else if(type.toUpperCase() === 'ERROR'){
                	box.style.border = "2px solid red";
                	box.style.color = "red";                	
                }
        	}else{
        		box.style.border = "2px solid #ccc";        		
        	}
        	
        	Ext.DomHelper.append(box, '<h3>'+title+'</h3><p>' + content + '</p>', true);
        	var outerDiv = document.createElement("outerDiv");
        	outerDiv.appendChild(box);
            html = outerDiv.innerHTML;   
            outerDiv = null;
            
            var m = Ext.DomHelper.append(msgCt, html, true);           
            m.hide();
            m.slideIn('t').ghost("t", { delay: 3000, remove: true});
        }
    };
}();;/**
 * AJAX消息通讯
 */
var	Messager = {
			
		
	/**
	 * 向服务器发送消息 post
	 * 
	 * @param cfg
	 */
	send : function(cfg) {

		var me = this;
		
		if(cfg.confirm != undefined){
			
			Message.ask(cfg.confirm, function(select){
				
				if(select == true){
					
					me.base(cfg);
				}
			});
		}else{
		
			me.base(cfg);
		}

	},

	/**
	 * 向服务器发送消息 get
	 */
	base : function(cfg) {
		
		var loadMarsk = null;
		if(cfg.maskCmp != undefined){
			
			loadMarsk = new Ext.LoadMask({  
			      msg: (cfg.maskText != undefined && cfg.maskText != null) ? cfg.maskText : "正在加载，请稍后...",  
			      target: cfg.maskCmp
			});
		};
		
		if(loadMarsk != null){
			 loadMarsk.show();
		}
		
		var flag = true;
		if (cfg.onBefore != undefined) {
			flag = cfg.onBefore();
		}
		
		if (flag) {
			Ext.Ajax.request({
			    url: cfg.url,
			    params: cfg.data,	
			    async : cfg.sync == undefined || cfg.sync == null ? true : !cfg.sync,	
			    success: function(response){		    	
			    	if(loadMarsk != null){		    		
						 Ext.destroy(loadMarsk);
					}				
					if (cfg.onSuccess != undefined) {
						var json = null;
						if(response.responseText!=undefined&&response.responseText!=null&&response.responseText!=''){
							try{
								json = Ext.JSON.decode(response.responseText);
							}catch(exception){
								json = response.responseText;
							}						
						}
						cfg.onSuccess(json);
					}			        
			    },
			    failure: function(response, opts) {
			    	if(loadMarsk != null){		    		
						 Ext.destroy(loadMarsk);
					}	
			    	
			        if (cfg.onError != undefined) {
						cfg.onError(response, opts);
					}
			    }
			});			
		}
					
	}		

}

;/**
 * 数据源
 */
;Ext.define("Ins.data.Store", {
	extend:'Ext.data.Store',
	alias : [ "widget.ins_store" ],
	config : {
		url: null	
	},	
	
	autoLoad: true,
	pageSize: 1000,
	
	//因为Store类没有继承component，所以不能用initComponent
	constructor: function(){	
		var me = this;
		me.callParent(arguments);
		if(me.url != null){
			me.setProxy({
				type: 'ajax',
	            url : me.url,
	            simpleSortMode: true,
	            actionMethods: {  
	                read: 'POST'  
	            },
	            reader: {
	                type: 'json',
	                rootProperty: 'data',
	                totalProperty: 'total'
	            },
	            extraParams : (me.params == undefined || me.params == null) ? {} : me.params
			});
		}else{			
			me.setProxy({
	            type: 'memory',  
	            reader: {
	                type: 'json'
	            }
	        });
		}		
	},
	
	
	/**
	 * 获取总记录树
	 * 
	 * @returns
	 */
	getTotle : function(){
		
		var me = this;
		
		return me.getTotalCount();
	},
	
	/**
	 * 根据 id 查找记录
	 * <br/>
	 * 注意：该方法只能查询到该页的数据，因为服务器一次仅加载一页
	 * 
	 * @param idValue
	 * @returns
	 */
	findRecordById : function(idField,idValue){
		
		var me = this;
		
		return me.findRecord(idField, idValue);
	},
	
	/**
	 * 根据指定索引的记录
	 * <br/>
	 * 注意：该方法只能查询到该页的数据，因为服务器一次仅加载一页
	 * 
	 * @param index
	 * @returns
	 */
	findRecordByIndex : function(index){
		
		var me = this;
		
		return me.getAt(index);
	},
	
	/**
	 * 获取指定列的数据集合
	 * 
	 * @param columnName
	 * @returns
	 */
	getColumnData : function(columnName){
		
		var me = this;
		
		var data = [];
		for(var i = 0; i < me.getTotle(); i ++){
			
			var record = me.findRecordByIndex(i);
			if(record != undefined && record != null){
				
				data.push(record.get(columnName));
			}else{
				
				break;
			}
		}
		
		return {
			//序列化值
			ser : Ext.JSON.encode(data),
			//数组
			data : data
		};
	},
	
	/**
	 * 获取当页所有记录对象
	 */
	getCurrentPageData : function(){
		
		var me = this;
		
		return me.data.items;
	},
	
	/**
	 * 添加记录
	 * 
	 * @param records
	 */
	addRecords : function(records){
		
		var me = this;
		
		me.add(records);
	},
	
	/**
	 * 移除全部记录
	 */
	removeAllRecords : function(){
		
		var me = this;
		
		me.removeAll();
	}
});;;Ext.define("Ins.panel.Panel", {
	extend:'Ext.panel.Panel',
	alias: 'widget.ins_panel',
	
	config : {
		//视图成员
		views : [],
		//标题工具按钮
		headButtons : [],
		//工具栏按钮
		actionButtons : [],
		//工具栏按钮方向
		actionButtonsAlign : 'top',
		//工具栏样式 footer/default 。。。。
		actionButtonsStyle: 'default',
		//渲染成功后事件
		onAfterRender : null,
		//panel大小改变后事件		
		onAfterResize : null,
		//使用背景色
		useAppBg: false
	},
	
	layout: 'fit',
	
	//border: false,
	
	//标题方向
	headerPosition: 'top',
	
	initComponent:function(){
		
		var me = this;		
		
		if(me.useAppBg){
			me.bodyStyle = 'background-image:url('+App.LAYOUT_BG+')'
		}
		
		//设置工具栏按钮
		if(me.actionButtons.length > 0){
			
			//实例化一个工具条
			var toolbar = {
				id: me.getId() + '_toolbar',
				xtype: 'toolbar',
			    items: me.actionButtons,
			    dock: me.actionButtonsAlign,
			    ui: me.actionButtonsStyle
			};
			
			Ext.apply(me, {				
				dockedItems : toolbar
			});
		}
		
		//设置标题工具按钮
		if(me.headButtons.length > 0){
			
			Ext.apply(me, {
				
				tools : me.headButtons
			});
		}		
		
		Ext.apply(me, {			
			items : me.views
		});
				
		
		if(me.onAfterRender != null){
			me.addListener("afterrender", me.onAfterRender);
		}
		
		if(me.onAfterResize != null){
			me.addListener("resize", me.onAfterResize);
		}
				
		this.callParent(arguments);
	}
});;/**
 * 周边布局器
 */
;Ext.define("Ins.layout.BorderLayout", {
	extend:'Ext.panel.Panel',
	
	alias : ["widget.ins_borderlayout"],
	
	config : {
		views : [],
		//定义组件位置
		location : [],
		//定义组件是否可折叠
		fold : [],
		//使用背景色
		useAppBg: false
	},
	
	//方向映射
	locationMapping  : {
		
		"top" : "north",
		"bottom" : "south",
		"left" : "west",
		"right" : "east",
		"center" : "center"
	},
	
	border: false,	
	
	initComponent: function(){
		
		var me = this;
		
		if(me.useAppBg){
			me.bodyStyle = 'background-image:url('+App.LAYOUT_BG+')'
			//me.bodyStyle = App.LAYOUT_BG_COLOR;
		}
		
		var items = [];
		for(var i in me.views){
			
			var item = me.views[i];
			
			//取得该组件的位置
			var locaStr = me.locationMapping[me.location[i]];
			Ext.apply(item, {
				region: locaStr
			});
			
			//取得该组件是否可折叠，如果设置的话
			if(me.fold != undefined && me.fold != null && me.fold.length > i){
				
				var foldable = me.fold[i];
				if(foldable != undefined && foldable != null && foldable == true){
					
					Ext.apply(item, {
						split: true,
				    	collapsible: true,
				    	collapseMode: 'undefinde'
					});
				}
			}
			
			items.push(item);
		}
		
		Ext.apply(me, {
			layout: 'border',
			items : items
		});
		
		this.callParent(arguments);
	},
	
	/**
	 * 折叠指定视图
	 * 
	 * @param panelId
	 * @param direction
	 */
	foldView : function(panelId){
		
		var me = this;
		
		Ext.getCmp(panelId).collapse();
	},
	
	/**
	 * 展示指定视图
	 * 
	 * @param panelId
	 */
	unfoldView : function(panelId){
		
		var me = this;
		
		Ext.getCmp(panelId).expand();
	}
});;/**
 * 周边布局器
 */
;Ext.define("Ins.layout.BoxLayout", {
	extend:'Ext.panel.Panel',
	
	alias : ["widget.ins_boxlayout"],
	
	config : {
		views : [],
		//定义组件位置
		direction : "-",
		//定义组件是否可折叠
		comSize : [],
		//使用背景色
		useAppBg: false
	},
	
	border: false,	
	
	initComponent: function(){
		
		var me = this;
		
		if(me.useAppBg){
			me.bodyStyle = 'background-image:url('+App.LAYOUT_BG+')'
		}
		
		var layout = null;
		if(me.direction == '-'){
			layout = {
		        type: 'hbox',
		        align: 'stretch'
		    };
		}else{
			layout = {
		        type: 'vbox',
		        align: 'stretch'
		    };
		}
						
		for(var i=0;i<me.views.length;i++){
			if(me.comSize[i]==undefined || me.comSize[i]==null){
				//me.views[i].flex = 1;
			}else{
				Ext.apply(me.views[i], me.comSize[i]);
			}		
		}
				
		Ext.apply(me, {		
			layout: layout,
			items: me.views
		});
		
		this.callParent(arguments);
	}
});;/**
 * 折叠布局器
 */
;Ext.define("Ins.layout.AccrodionLayout", {
	extend:'Ext.panel.Panel',
	
	alias : ["widget.ins_accrodionlayout"],
	
	config: {		
		views: []		
	},
	
	layout: {	        
        type: 'accordion',
        titleCollapse: true,
        animate: true
    },
	initComponent:	function(){
		var me = this;
		if(me.views.length > 0){
			Ext.apply(me, {			
				items : me.views
			});
		}
		
		this.callParent(arguments);
		
	}
	
	
});
	;/**
 * 标签布局器
 */
;Ext.define("Ins.layout.tab.TabLayout", {
	extend : 'Ext.tab.Panel',

	alias : [ "widget.ins_tablayout" ],

	config : {
		views : [],
		direction : 'top',
		
		//tab 切换事件
		onTabChange : null,
		//使用背景色
		useAppBg: false
	},
	
	activeTab: 0,
    flex:1, 
    border: false,
    deferredRender: false,
			

	initComponent : function() {
		
		var me = this;
		
		if(me.useAppBg){
			me.bodyStyle = 'background-image:url('+App.LAYOUT_BG+')'
		}
		
		if(me.views.length != 0){
			
			var items = [];
			for(var i in me.views){
				
				var item = me.views[i];
				Ext.apply(item,{					
					tabConfig: {			            
			            tooltip: me.views[i].title
			        }
				});
				items.push(item);
			}
			
			Ext.apply(me, {
				
				items : items,
				defaults : {
					closeText : "关闭此标签"
				}
			});
		}
		
		me.setTabPosition(me.direction);
		
		//如果有事件，则注册
		if(me.onTabChange != undefined && me.onTabChange != null){
			
			me.addListener("tabchange", function(tabPanel, newCard, oldCard, eOpts ){
				
				me.onTabChange(me.getSelectedTabTitle(), oldCard, newCard);
			});
		}

		this.callParent(arguments);
		
	},
	
	/**
	 * 重写父类的setDisabled方法
	 * @param {} disabled
	 */
	setDisabled: function(disabled){
		var me = this;		
    	me.callParent([disabled]);
    	me.selectTabByIndex(1);
		me.selectTabByIndex(0);
	},
	
	/**
	 * 选择指定下标的标签
	 * 
	 * @param index
	 */
	selectTabByIndex : function(index){
		
		var me = this;
		
		me.setActiveTab(index);
	},
	
	/**
	 * 选择指定编号的标签
	 * 
	 * @param id
	 */
	selectTabById : function(id){
		
		var me = this;
		
		var tabObj = Ext.getCmp(id);
		
		//如果 tab 被禁用，则先启用
		tabObj.setDisabled(false);
		me.setActiveTab(tabObj);
	},
	
	/**
	 * 获取所选的 tab 名称
	 * 
	 * @returns
	 */
	getSelectedTabTitle : function(){
		
		var me = this;
		
		return me.getActiveTab().title;
	}

});
;
Ext.define('Ins.layout.tab.TabCloseMenu', {
    extend: 'Ext.plugin.Abstract',

    alias: 'plugin.ins_tabclosemenu',

    mixins: {
        observable: 'Ext.util.Observable'
    },

    /**
     * @cfg {String} closeTabText
     * The text for closing the current tab.
     */
    closeTabText: '关闭标签',

    /**
     * @cfg {Boolean} showCloseOthers
     * Indicates whether to show the 'Close Others' option.
     */
    showCloseOthers: true,

    /**
     * @cfg {String} closeOthersTabsText
     * The text for closing all tabs except the current one.
     */
    closeOthersTabsText: '关闭其它标签',

    /**
     * @cfg {Boolean} showCloseAll
     * Indicates whether to show the 'Close All' option.
     */
    showCloseAll: true,

    /**
     * @cfg {String} closeAllTabsText
     * The text for closing all tabs.
     */
    closeAllTabsText: '关闭所有标签',

    /**
     * @cfg {Array} extraItemsHead
     * An array of additional context menu items to add to the front of the context menu.
     */
    extraItemsHead: null,

    /**
     * @cfg {Array} extraItemsTail
     * An array of additional context menu items to add to the end of the context menu.
     */
    extraItemsTail: null,

    //public
    constructor: function (config) {
        this.callParent([config]);
        this.mixins.observable.constructor.call(this, config);
    },

    init : function(tabpanel){
        this.tabPanel = tabpanel;
        this.tabBar = tabpanel.down("tabbar");

        this.mon(this.tabPanel, {
            scope: this,
            afterlayout: this.onAfterLayout,
            single: true
        });
    },

    onAfterLayout: function() {
        this.mon(this.tabBar.el, {
            scope: this,
            contextmenu: this.onContextMenu,
            delegate: '.x-tab'
        });
    },

    destroy : function(){
        this.callParent();
        Ext.destroy(this.menu);
    },

    // private
    onContextMenu : function(event, target){
        var me = this,
            menu = me.createMenu(),
            disableAll = true,
            disableOthers = true,
            tab = me.tabBar.getChildByElement(target),
            index = me.tabBar.items.indexOf(tab);

        me.item = me.tabPanel.getComponent(index);
        //设置活跃tab
        //me.tabPanel.setActiveTab(index);
        
        menu.child('#close').setDisabled(!me.item.closable);

        if (me.showCloseAll || me.showCloseOthers) {
            me.tabPanel.items.each(function(item) {
                if (item.closable) {
                    disableAll = false;
                    if (item !== me.item) {
                        disableOthers = false;
                        return false;
                    }
                }
                return true;
            });

            if (me.showCloseAll) {
                menu.child('#closeAll').setDisabled(disableAll);
            }

            if (me.showCloseOthers) {
                menu.child('#closeOthers').setDisabled(disableOthers);
            }
        }

       	if(event.preventDefault){
      		event.preventDefault();     
      	}else{       
      		event.returnValue = false;
     	}
        me.fireEvent('beforemenu', menu, me.item, me);

        menu.showAt(event.getXY());
    },

    createMenu : function() {
        var me = this;

        if (!me.menu) {
            var items = [{
                itemId: 'close',
                iconCls: 'icon-cross',
                text: me.closeTabText,
                scope: me,
                handler: me.onClose
            }];

            if (me.showCloseAll || me.showCloseOthers) {
                items.push('-');
            }

            if (me.showCloseOthers) {
                items.push({
                    itemId: 'closeOthers',
                    text: me.closeOthersTabsText,
                    scope: me,
                    handler: me.onCloseOthers
                });
            }

            if (me.showCloseAll) {
                items.push({
                    itemId: 'closeAll',
                    text: me.closeAllTabsText,
                    scope: me,
                    handler: me.onCloseAll
                });
            }

            if (me.extraItemsHead) {
                items = me.extraItemsHead.concat(items);
            }

            if (me.extraItemsTail) {
                items = items.concat(me.extraItemsTail);
            }

            me.menu = Ext.create('Ext.menu.Menu', {
                items: items,
                listeners: {
                    hide: me.onHideMenu,
                    scope: me
                }
            });
        }

        return me.menu;
    },

    onHideMenu: function () {
        var me = this;
        me.fireEvent('aftermenu', me.menu, me);
    },

    onClose : function(){
        this.tabPanel.remove(this.item);
    },

    onCloseOthers : function(){
        this.doClose(true);
    },

    onCloseAll : function(){
        this.doClose(false);
    },

    doClose : function(excludeActive){
        var items = [];

        this.tabPanel.items.each(function(item){
            if(item.closable){
                if(!excludeActive || item !== this.item){
                    items.push(item);
                }
            }
        }, this);

        Ext.suspendLayouts();
        Ext.Array.forEach(items, function(item){
            this.tabPanel.remove(item);
        }, this);
        Ext.resumeLayouts(true);
    }
});
;/**
 * 周边布局器
 */
;Ext.define("Ins.layout.CenterLayout", {
	extend: 'Ins.panel.Panel',
	
	alias : ["widget.ins_centerlayout"],
	
	layout: 'center',
	
	initComponent: function(){
		
		var me = this;
		
		
		this.callParent(arguments);
	}
});;/**
 * 窗口
 */
;Ext.define("Ins.window.Window", {
	extend : 'Ext.window.Window',

	alias : [ "widget.ins_window" ],

	config : {
		views : [],	
		hideTitle: false,
		//标题工具按钮
		headButtons : [],
		//工具栏按钮
		actionButtons : [],
		//工具栏按钮方向
		actionButtonsAlign : 'bottom',
		//工具栏样式 footer/default 。。。。
		actionButtonsStyle: 'footer',
		//窗口显示前事件
		onBeforeShow : null,
		//窗口关闭前事件
		onBeforeHide : null,
		//使用背景色
		useAppBg: false
	},

	title : '未命名窗体',
	//width : 400,
	//height : 300,
	//点击关闭工具时的操作
	closeAction : 'hide',
	//是否显示折叠工具
	collapsible : true,
	resizable : true,
	//是否显示遮罩
	modal: true,
	layout : 'fit', 
	
	//标题方向
	headerPosition: 'top',
	
	//是否显示最大化工具
	maximizable: false,
	//是够显示最小化工具
	minimizable: false,
	
	//添加约束
	constrain: true,
	constrainHeader: true,

	initComponent : function() {
		var me = this;
		
		if(me.useAppBg){
			me.bodyStyle = 'background-image:url('+App.LAYOUT_BG+')'
		}
		
		me.header = !me.hideTitle;

		//设置工具栏按钮
		if(me.actionButtons.length > 0){			
			//实例化一个工具条
			var toolbar = {
				id:　me.getId() + '_toolbar',
				xtype: 'toolbar',
				dock: me.actionButtonsAlign,
			    items: me.actionButtons,
			    ui: me.actionButtonsStyle
			};
			
			Ext.apply(me, {				
				dockedItems : toolbar
			});
		}
		
		//设置标题工具按钮
		if(me.headButtons.length > 0){
			
			Ext.apply(me, {
				
				tools : me.headButtons
			});
		}		
		
		Ext.apply(me, {
			items : me.views
		});

		//注册事件
		if (me.onBeforeShow != undefined && me.onBeforeShow != null) {
			me.addListener("beforeshow", me.onBeforeShow);
		}
		if (me.onBeforeHide != undefined && me.onBeforeHide != null) {
			me.addListener("beforehide", me.onBeforeHide);
		}

		this.callParent(arguments);

	},

	/**
	 * 对齐到中央
	 */
	alignToCenter : function() {

		var me = this;

		me.center();
	}

});
;/**
 * menu菜单
 */
;Ext.define("Ins.menu.Menu", {
	extend:'Ext.menu.Menu',
	
	alias : ["widget.ins_menu"],
	
	config : {
	},
		
	//floating : true,    
	
	initComponent:function(){
		
		var me = this;
		
		this.callParent(arguments);
	},
	
	/**
	 * 根据鼠标位置
	 */
	showMenu : function(event){
		
		var me = this;
		
		me.showAt(event.pageX, event.pageY);
	}
});;/**
 * 普通树
 */
;
Ext.define("Ins.tree.Tree", {
	extend : 'Ext.tree.Panel',
	
	config : {
		url : null,
		fields : [],
		//主键
		pkField : 'id',
		//双击事件
		onDblClick : null,
		//右键点击事件
		onContextMenuClick : null,
		//左键单击事件
		onClick : null,
		//是否自动加载
		loadOnShow : true,
		clearOnLoad: true,
		//隐藏标题
		hideTitle : false,

		//是否可查询
		searchFiled : false,
		searchFieldText : '查询',

		actionButtons : [],
		
		//复选框级联父节点
		checkedAsParent: true,
		//取消复选框级联父节点
		noCheckedAsParent: true,
		//复选框级联子节点
		checkedAsChild: true,
		//取消复选框级联子节点
		noCheckedAsChild: true,

		//仅供内部使用
		onCtxClick : null,
		//保存所有处于展开状态的节点，以便于刷新后恢复，内部使用
		NODE_EXPAND_STATE : [],
		afterChecked: function(){}//改变选中后
	},

	//reserveScrollbar: true,
	title : '未命名标题',
	useArrows : true,
	rootVisible : false,
	animate: false,

	//多选
	multiSelect : false,

	//给定默认宽度，否则在配合 roundlayout 时，树显示不正常
	width : 240,

	initComponent : function() {		

		var me = this;

		//隐藏标题		
		if (me.getHeader() != null || me.hideTitle) {

			Ext.apply(me, {
						header : !me.hideTitle
					});
		}

		//自动添加 tree 的传输协议中包含的字段（如果不存在时才添加）
		var sysFields = ["id", "pid", "text"];
		for (var i in sysFields) {

			var field = sysFields[i];

			var isContains = Ext.Array.contains(me.fields, field);
			if (!isContains) {

				me.fields.push(field);
			}
		}

		Ext.apply(me, {

					store : new Ext.data.TreeStore({
								fields : me.fields,
								clearOnLoad: me.clearOnLoad,
								autoLoad : me.loadOnShow,
								idProperty : me.pkField,
								proxy : {
									actionMethods : {
										read : 'POST'
									},
									type : 'ajax',
									url : me.url
								}
							})
				});

		//添加导航工具按钮
		me.tools = [{
					type : 'collapse',
					tooltip : '全部展开',
					listeners : {
						click : function() {
							me.expandAll();
						}
					}
				}, {
					type : 'expand',
					tooltip : '全部折叠',
					listeners : {
						click : function() {
							me.collapseAll();
						}
					}
				}, {
					type : 'refresh',
					tooltip : '刷新',
					handler : function() {
						me.reloadExpand()
					}
				}];

		me.listeners = {

			itemdblclick : function(view, record, item, index, e) {

				//记录当前单击的节点
				Ext.apply(me, {
							currNode : record
						});
				if (me.onDblClick != null) {
					me.onDblClick(record, e);
				}

			},
			itemclick : function(obj, record, item, index, e, eOpts) {

				Ext.apply(me, {
							currNode : record
						});

				if (me.onClick != null) {

					me.onClick(record);
				}
			},
			itemcontextmenu : function(menutree, record, items, index, e) {

				//记录当前点击的节点
				Ext.apply(me, {
							currNode : record
						});

				if (me.onContextMenuClick != null) {

					rsFlag = me.onContextMenuClick(record, e);
				}

				if (me.onCtxClick != null) {
					me.onCtxClick(record, e);
				}
			},
			checkchange : function(node, flag) {
				
				var records = me.setChecked(node, flag);
				
				me.afterChecked();
				
			},
			selectionchange : function(obj, selected, eOpts) {
				
			}			
		};

		var _dockedItems = [];

		if (me.actionButtons.length > 0) {
			_dockedItems.push({
						xtype : 'toolbar',						
						items : me.actionButtons
					});
		}

		if (me.searchFiled) {
			
			var lastFilterValue;
			
			if(me.xtype != 'ins_gridtree' && me.xtype != 'ins_crudgridtree'){
				me.hideHeaders = true;
				me.columns = [{
					xtype : 'treecolumn',
					flex : 1,
					dataIndex : 'text',
					scope : me,
					renderer : function(value) {
						var searchString = this.searchField.getValue();

						if (searchString.length > 0) {
							return this.strMarkRedPlus(searchString, value);
						}

						return value;
					}
				}];
			}else{
				for(var i=0; i<me.columns.length; i++){
					if(me.columns[i].dataIndex == 'text'){
						Ext.apply(me.columns[i],{
							xtype : 'treecolumn',
							dataIndex : 'text',
							scope : me,
							renderer : function(value) {
								var searchString = this.searchField.getValue();
		
								if (searchString.length > 0) {
									return this.strMarkRedPlus(searchString, value);
								}
		
								return value;
							}
						});						
					}
				}
			}			

			_dockedItems.push({
						xtype : 'textfield',
						dock : 'top',
						emptyText : me.searchFieldText,
						enableKeyEvents : true,

						triggers : {
							clear : {
								cls : 'x-form-clear-trigger',
								handler : 'onClearTriggerClick',
								hidden : true,
								scope : 'this'
							},
							search : {
								cls : 'x-form-search-trigger',
								weight : 1,
								handler : 'onSearchTriggerClick',
								scope : 'this'

							}
						},

						onClearTriggerClick : function() {
							this.setValue();
							me.store.clearFilter();
							this.getTrigger('clear').hide();
						},

						onSearchTriggerClick : function() {
							me.filterStore(this.getValue());
						},

						listeners : {
							keyup : {
								fn : function(field, event, eOpts) {
									var value = field.getValue();

									// Only filter if they actually changed the field value.
									// Otherwise the view refreshes and scrolls to top.
									if (value && value !== lastFilterValue) {
										field.getTrigger('clear')[(value.length > 0)
												? 'show'
												: 'hide']();
										me.filterStore(value);
										lastFilterValue = value;
									}
								},
								buffer : 200
							},

							render : function(field) {
								this.searchField = field;
							},

							scope : me
						}
					});
		}

		if (_dockedItems.length > 0) {
			Ext.apply(me, {
						dockedItems : _dockedItems
					});
		}

		this.callParent(arguments);
	},

	/**
	 * 获取当前节点对象
	 * 
	 * @returns
	 */
	getCurrentNode : function() {

		var me = this;

		return me.currNode;
	},

	/**
	 * 获取选择视图模型
	 */
	getSelectedView : function() {

		var me = this;

		var selection = me.getSelectionModel().getSelection();

		//拼接 id 序列
		var ids = "[";
		for (var i in selection) {

			var id = selection[i].data[me.pkField];

			ids += "\"" + id + "\",";
		}
		ids = ids.substring(0, ids.length - 1);
		ids += "]";

		//如果没有选择任何记录，则 ids 会为 "]"，因此这次需要特别处理
		if (ids == "]") {

			ids = "[]";
		}

		return {

			count : selection.length,
			idSer : ids,
			idArr : Ext.JSON.decode(ids),
			records : selection
		};
	},

	/**
	 * 获取选中模型
	 */
	getCheckedView : function() {

		var me = this;

		var selection = me.getView().getChecked();

		//拼接 id 序列
		var ids = "[";
		for (var i in selection) {

			var id = selection[i].data[me.pkField];

			ids += "\"" + id + "\",";
		}
		ids = ids.substring(0, ids.length - 1);
		ids += "]";

		//如果没有选择任何记录，则 ids 会为 "]"，因此这次需要特别处理
		if (ids == "]") {

			ids = "[]";
		}

		return {

			count : selection.length,
			idSer : ids,
			idArr : Ext.JSON.decode(ids),
			records : selection
		};
	},

	/**
	 * 重新加载
	 */
	load : function(params) {

		var me = this;

		//首先启用组件
		me.setDisabled(false);

		//必须在 params 有值时才赋值，否则会覆盖之前的查询条件的值
		if (params != undefined && params != null) {

			//将条件赋给对loadParams属性，便于以后使用
			me.loadParams = params;

			me.getStore().proxy.extraParams = me.loadParams;
		}

		me.store.reload();
	},

	/**
	 * 刷新并展开
	 */
	reloadExpand : function() {

		var me = this;
		
		//将所有展开的节点放在数组中
		me.NODE_EXPAND_STATE = [];
		me.getRootNode().cascadeBy(function(child) {			
			if(child.data.expanded == true){					
				me.NODE_EXPAND_STATE.push(child.getPath());
			}
		});		
		me.store.reload({
					callback : function() {
						//恢复树的展开状态
						var i;
						for(i=0; i<me.NODE_EXPAND_STATE.length; i++){
							me.expandPath(me.NODE_EXPAND_STATE[i]);
						}
					}
				});

	},

	/**
	 * 清空选中(复选框)
	 */
	clearChecked : function() {

		var me = this;

		me.getRootNode().cascadeBy(function(child) {

					child.set("checked", false);
				});
	},
	
	/**
	 * 取消选中的记录
	 */
	clearSelect: function(){
		
		var me = this;
		
    	if(me.getStore().getTotalCount() > 0){    		
    		me.getSelectionModel().selectAll();
    	}    	
    	
    	me.getSelectionModel().deselectAll();
	},

	/**
	 * 设置选中
	 * @param {} node
	 * @param {} flag
	 */
	setChecked : function(node, flag) {
		var me = this;
		
		//是否级联父节点
		if((me.checkedAsParent && flag) || (me.noCheckedAsParent && !flag)){
			me.setParentNodeChecked(node,flag);
		}			
		
		node.set("checked", flag);		
		
		if((me.checkedAsChild && flag) || (me.noCheckedAsChild && !flag)){//是否级联选择或级联取消子节点
			node.cascadeBy(function(child) {					
				child.set("checked", flag);
			});		
		}
	},

	/**
	 * 设置父节点
	 * @param {} node
	 * @param {} flag
	 */
	setParentNodeChecked : function(node, flag) {
		var me = this;
		var parentNode = node.parentNode;
		if(!parentNode) return;
		var childNodes = parentNode.childNodes;
		var hasCheckChildFlag = false;
		for(var i=0;i<childNodes.length;i++){
			var child = childNodes[i];
			if(child.get("checked")){
				hasCheckChildFlag = true;
				break;
			}
		}
		if(hasCheckChildFlag){
			node.parentNode.set("checked", true);
		}else{
			node.parentNode.set("checked", false);
		}
		me.setParentNodeChecked(parentNode);
		
	},
	
	/**
	 * 用于查询（私有方法）
	 * @param {} value
	 */
	filterStore : function(value) {
		var me = this, store = me.store, searchString = value.toLowerCase(), filterFn = function(
				node) {
			var children = node.childNodes, len = children && children.length, visible = v
					.test(node.get('text')), i;

			// If the current node does NOT match the search condition
			// specified by the user...
			if (!visible) {

				// Check to see if any of the child nodes of this node
				// match the search condition.  If they do then we will
				// mark the current node as visible as well.
				for (i = 0; i < len; i++) {
					if (children[i].isLeaf()) {
						visible = children[i].get('visible');
					} else {
						visible = filterFn(children[i]);
					}
					if (visible) {
						break;
					}
				}

			}

			else { // Current node matches the search condition...

				// Force all of its child nodes to be visible as well so
				// that the user is able to select an example to display.
				for (i = 0; i < len; i++) {
					children[i].set('visible', true);
				}

			}

			return visible;
		}, v;

		if (searchString.length < 1) {
			store.clearFilter();
		} else {
			v = new RegExp(searchString, 'i');
			store.getFilters().replaceAll({
						filterFn : filterFn
					});
		}
	},

	/**
	 * 用于查询（私有方法）
	 * @param {} search
	 * @param {} subject
	 * @return {}
	 */
	strMarkRedPlus : function(search, subject) {
		return subject.replace(new RegExp('(' + search + ')', "gi"),
				"<span style='color: red;'><b>$1</b></span>");
	}

});
;/**
 * 增删改查树
 */
Ext.define('Ins.tree.CrudTree',{
	extend: 'Ins.tree.Tree',	
	
	config: {
		urlConfig : {
			/*
			loadAll: CTX_PATH + '/link/loadAll',
			add: CTX_PATH + '/link/add',
			load:  CTX_PATH + '/link/load',
			update:  CTX_PATH + '/link/update',
			remove:  CTX_PATH + '/link/remove'
			*/
		},		
		/*
		 *数据集
		dataSet:[
			{	name : 'id',
	    		text : '主键'
			},{
				name: 'name',
				text: '名字'
			}
		]	
		 * */
		dataSet : [],
		/*
		 * 表单字段
		 * formFields: [
			{
				forData: 'id',
				xtype: 'ins_hiddenfield'
				
			},{
				forData: 'name',				
				allowBlank: false,
				colspan: 2
			}]
		*/
		formFields : [],	
		
		//权限
		permission : {
			add : true,
			update : true,
			remove : true,
			view: true
		},
		
		//默认表单字段宽度
		defaultFormFieldWidth: 230,
		
		//右键菜单
		customMenu : [],
		
		//在新增表单展示之前触发的函数
		onBeforeShowAddForm : null,
		//在编辑表单展示之前触发的函数
		onBeforeShowEditForm : null,
		//在查看明细之前触发的函数
		onBeforeShowViewForm: null,
		
		//在新增表单展示之后
		onAfterShowAddForm: null,
		//在编辑表单展示之后
		onAfterShowEditForm: null,
		//在查看明细后显示
		onAfterShowViewForm: null,
		
		
		//在执行删除之前执行的函数
		onBeforeDelete : null,
		//添加保存之前执行的函数
		onBeforeSaveAdd : null,
		//编辑保存之前执行的函数
		onBeforeSaveEdit : null,
		//右键菜单显示前
		onBeforeShowCxtMenu: null,
		//右键菜单显示后
		onAfterShowCxtMenu: null,
		
		//在删除操作成功之后
		onAfterDelete: null,
		
		//增加或编辑表单执行成功时调用，如果返回true则执行重置表单，topmessage等操作，返回false则不执行后续操作
		onAfterFormSubmit: null,
		
		//操作表单列数
		columnSize : 2,
		
		//自定义标签
		labelConfig : {
			add : {
				text : null,
				iconCls : null
			},
			update : {
				text : null,
				iconCls : null
			},
			view : {
				text : null,
				iconCls : null
			},
			remove : {
				text : null,
				iconCls : null
			}
		},
		
		//操作表单高度
		formWindowHeight : null,	
		//操作表单宽度
		formWindowWidth: 510,
		//操作表单最大高度
		formWindowMaxHeight: null,
		//操作表单最大宽度
		formWindowMaxWidth: null,
		//操作表单窗体是否遮罩
		formWindowModal: false
	},
	
	initComponent: function(){
		
		var me = this;
		
		//构造 store fields 数组
		var _fields = [];
		for(var i in me.dataSet){			
			var record = me.dataSet[i];
			_fields.push(record.name);
		}
		Ext.apply(me, {
			fields : _fields
		});
		
		
		//生成表单字段数组
		var _formFields = [];	
		for(var i in me.formFields){			
			var item = me.formFields[i];
			var field = null;
			//不分组时
			if((item.forData!=undefined && item.forData!=null) || (item.name!=undefined && item.name!=null)){
				field = me.getFieldDefByName(Ext.clone(item));				
				//设置默认表单字段宽度（默认宽度只在没有指定宽度时有效）
				if(field != null && field.width == undefined && field.xtype!='component' && field.xtype != 'container' && field.xtype != 'panel' && field.xtype != 'ins_panel' && field.xtype != 'ins_fieldcontainer'){							
					Ext.apply(field, {
						width : me.defaultFormFieldWidth
					});
				}				
			}
			//分组时
			else if(item.items!=undefined && item.items!=null){
				field = Ext.clone(item);
				field.items = [];
				for(var j in item.items){
					if(item.items[j] != null && item.items[j].width == undefined && item.items[j].xtype!='component' && item.items[j].xtype != 'container' && item.items[j].xtype != 'panel' && item.items[j].xtype != 'ins_panel' && item.items[j].xtype != 'ins_fieldcontainer'){							
						Ext.apply(item.items[j], {
							width : me.defaultFormFieldWidth
						});
					}					
					field.items.push(me.getFieldDefByName(Ext.clone(item.items[j])));
				}
			}
			
			_formFields.push(field);
		}
		
		
		//操作表单配置
		var formConfig = {	
			fields : _formFields,
			modal: me.formWindowModal,
			defaultFormFieldWidth : me.defaultFormFieldWidth,
			columnSize : me.columnSize,
			onBeforeHide: function(obj){
				obj.getForm().reset();
			}
		};
		
		if(me.onAfterFormSubmit != null){
			formConfig.onAfterSubmit = me.onAfterFormSubmit;
		}
		
		if(me.formWindowHeight != null){
			formConfig.height = me.formWindowHeight;
		}		
		if(me.formWindowWidth != null){
			formConfig.width = me.formWindowWidth;
		}
		if(me.formWindowMaxHeight != null){
			formConfig.maxHeight = me.formWindowMaxHeight;
		}		
		if(me.formWindowMaxWidth != null){
			formConfig.maxWidth = me.formWindowMaxWidth;
		}	
		
		//操作表单
		var form = Ext.create("Ins.form.OprFormWindow", formConfig);	
		
		//覆盖基础属性
		Ext.apply(me, {
			form : form,//将 form 也注册到组件中，以便其他地方调用			
			url : me.urlConfig.loadAll
		});	
		
		//右键菜单项
		var _menu = [];
		
		
		//是否允许新增
		if(me.urlConfig.add!=undefined && me.urlConfig.add!=null && me.permission.add){
			_menu.push({
				id : me.getId() + "_add_btn",
				text: me.labelConfig.add.text != null ? me.labelConfig.add.text : '添加',
				iconCls: me.labelConfig.add.iconCls != null ? me.labelConfig.add.iconCls : 'icon-add',
				handler: function(btn){
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowAddForm != null){						
						flag = me.onBeforeShowAddForm(me.getForm());
					}
					if(!flag){
						return;
					}
					form.showAsAddMode({
						url: me.urlConfig.add,
						onBeforeSave: me.onBeforeSaveAdd,
						success: function(data){
							me.reloadExpand();
						}
					});
					
					//如果注册有事件监听器，则注册
					if(me.onAfterShowAddForm != null){
						me.onAfterShowAddForm(me.getForm());
					}
				}
			});
		}
		
		//是否允许编辑
		if(me.urlConfig.update!=undefined && me.urlConfig.update!=null 
				&& me.urlConfig.load!=undefined && me.urlConfig.load!=null && me.permission.update){			
			_menu.push({
				id : me.getId() + "_edit_btn",
				iconCls: me.labelConfig.update.iconCls != null ? me.labelConfig.update.iconCls : 'icon-edit',
				text: me.labelConfig.update.text != null ? me.labelConfig.update.text : '编辑',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowEditForm != undefined && me.onBeforeShowEditForm != null){						
						flag = me.onBeforeShowEditForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};					
					params[me.pkField] = me.getCurrentNode().data[me.pkField];	
					
					form.showAsEditMode({
						//告诉后台此操作不记录日志
						loadUrl: me.urlConfig.load + '?nolog=y',
						updateUrl: me.urlConfig.update,
						onBeforeSave: me.onBeforeSaveEdit,
						params: params,
						success: function(data){
							me.reloadExpand();
						},
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowEditForm != null){
								me.onAfterShowEditForm(form, data);
							}	
						}
					});
				}	
			});
		}
		
		//是否允许删除
		if(me.urlConfig.remove!=undefined && me.urlConfig.remove!=null && me.permission.remove){			
			_menu.push({
				id : me.getId() + "_delete_btn",
		        text: me.labelConfig.remove.text != null ? me.labelConfig.remove.text : '删除',
		        iconCls: me.labelConfig.remove.iconCls != null ? me.labelConfig.remove.iconCls : 'icon-delete',
				handler: function(btn){					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeDelete != undefined && me.onBeforeDelete != null){
						
						flag = me.onBeforeDelete(me.getSelectedView());
					}
					if(!flag){
						return;
					}
										
					var id = me.getCurrentNode().data[me.pkField];
					
					Messager.send({
					  confirm: "确认要删除该节点吗？",
					  maskCmp: me,
					  url: me.urlConfig.remove,
					  data: {
						  id : id
					  },
					  onSuccess:function(data){							  	 
						  me.reloadExpand();
						  
						  if(me.onAfterDelete!=null){
						  	  me.onAfterDelete();
						  }
						  
						  TopMessage.info("操作成功！");
					  }
					});
				}
		    });
		}
		
		//是否允许查看明细
		if(me.urlConfig.load!=undefined && me.urlConfig.load!=null && me.permission.view){			
			_menu.push({
				id : me.getId() + "_view_btn",
				iconCls: me.labelConfig.view.iconCls != null ? me.labelConfig.view.iconCls : 'icon-view',
				text: me.labelConfig.view.text != null ? me.labelConfig.view.text : '查看明细',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowViewForm != undefined && me.onBeforeShowViewForm != null){						
						flag = me.onBeforeShowViewForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};
					params[me.pkField] = me.getCurrentNode().data[me.pkField];
					
					form.showAsViewMode({
						loadUrl: me.urlConfig.load,
						params: params,
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowViewForm != null){
								me.onAfterShowViewForm(form, data);
							}	
						}
					});
				}	
			});
		}
		
		
		//添加用户自定义菜单项
		if(me.customMenu != undefined && me.customMenu.length > 0){			
			if(_menu.length > 0){
				_menu.push("-");
			}			
			for(var i in me.customMenu){				
				var item = me.customMenu[i];				
				_menu.push(item);
			}
		}
		
		//构建右键菜单
		if(_menu.length > 0){			
			var nodemenu = new Ins.menu.Menu({  	            
	            items : _menu
	        }); 			
			Ext.apply(me, {				
				contextMenu : nodemenu,
				onCtxClick : function(record, e){	
					if(e.preventDefault){
			      		e.preventDefault();     
			      	}else{       
			      		e.returnValue = false;
			     	}
					var flag = true;
					if(me.onBeforeShowCxtMenu != null){
						flag = me.onBeforeShowCxtMenu(nodemenu);
					}
					if(flag && _menu.length > 0){
						me.contextMenu.showAt(e.getXY());
						if(me.onAfterShowCxtMenu!=null){
							me.onAfterShowCxtMenu(nodemenu);
						}						
					}			        
				}
			});
		}		
		
		//增加销毁时事件,清空资源
		me.addListener("beforedestroy", function(){
			//销毁表单
			if(me.form!=undefined || me.form!=null){
				Ext.destroy(me.form);
			}
			//销毁右键菜单
			if(me.contextMenu!=undefined || me.contextMenu!=null){
				Ext.destroy(me.contextMenu);
			}
		});			
		
		me.callParent(arguments);
		
	},
	
	/**
	 * 根据字段名构造字段对象
	 * 
	 * @param fieldName
	 * @returns {Object}
	 */
	getFieldDefByName : function(fieldOld){	
		var me = this;		
		//遍历数据集并定位到该字段对应的配置		
		for(var i in me.dataSet){			
			var record = me.dataSet[i];	
			if(record.name == fieldOld.forData){				
				//更新属性
				Ext.apply(fieldOld, {
					fieldLabel: record.text,
					text : record.text,
					name : record.name
				});				
				return fieldOld;
			}						
		}	
		
		if(fieldOld.name!=null && fieldOld.name!=undefined){
			//更新属性
			Ext.apply(fieldOld, {
				fieldLabel: fieldOld.text,
				text : fieldOld.text					
			});				
			return fieldOld;
		}
		if(window.console){
			console.error('没有发现 dataSet 中配置的该表单字段。');
		}
	},
	
	/**
	 * 获取表单对象
	 * 
	 * @returns
	 */
	getForm : function(){
		
		var me = this;
		
		return me.form.getForm();
	}
	
});;/**
 *  表格树
 */
Ext.define('Ins.tree.GridTree',{
	extend: 'Ins.tree.Tree',
	alias: 'widget.ins_gridtree',
	
	config: {		
		//实际列
		gridColumns: null,	
		//隐藏表头
		hideTableHead: false,
		//折叠列
		treeAt: 'text',
		//默认列宽
		defaultColumnWidth: 120,		
		//禁用所有列的排序
		disabledColumnsSortable : false		
	},
	
	animate: false,
	rowLines : true,
    columnLines : true,
    width: '100%',
    
    multiSelect: false,
    
    viewConfig: { 
		stripeRows: true, 
		enableTextSelection: true
	},
	
	initComponent: function(){
		
		var me = this;
		
		//隐藏表头	
		Ext.apply(me, {    	
    		hideHeaders: me.hideTableHead		//隐藏表头
    	});
		
		if(me.gridColumns != null){			
			for(var i in me.gridColumns){    			
    			var column = me.gridColumns[i];    			
    			//设置默认宽度（只给列设置，不给分组列设置）
    			if(column.dataIndex != undefined){
    				Ext.applyIf(column, {
        				width : me.defaultColumnWidth
        			});
    			}
    			
    			//默认列均可以排序
				if(column.dataIndex != undefined && column.sortable == undefined){    			
					Ext.apply(column, {    				
						sortable: !me.disabledColumnsSortable
					});
				}
				
    			//处理 treeAt 属性
    			if(column.dataIndex == me.treeAt){    				
    				Ext.applyIf(column, {    					
    					xtype : "treecolumn"
    				});
    			}
    		}		
		}		
		
    	Ext.apply(me, {    		
            columns: me.gridColumns
    	});
    	
    	
    	
    	//设置工具栏按钮
		/*if(me.actionButtons.length > 0){
			
			//不允许查询框    	
	    	if(me.searchFiled){
	    		me.searchFiled = false
	    	}    	
			
			//实例化一个工具条
			var toolbar = {
				xtype: 'toolbar',
			    items: me.actionButtons,
			    dock: 'top'			   
			};
			
			Ext.apply(me, {				
				dockedItems : toolbar
			});
		}*/
    	
	
		me.callParent(arguments);
	}
	
});;/**
 *  表格树
 */
Ext.define('Ins.tree.CrudGridTree',{
	extend: 'Ins.tree.Tree',
	alias: 'widget.ins_crudgridtree',
	
	config: {	
		urlConfig : {
			/*
			loadAll: CTX_PATH + '/link/loadAll',
			add: CTX_PATH + '/link/add',
			load:  CTX_PATH + '/link/load',
			update:  CTX_PATH + '/link/update',
			remove:  CTX_PATH + '/link/remove'
			*/
		},		
		/*
		 *数据集
		dataSet:[
			{	name : 'id',
	    		text : '主键'
			},{
				name: 'name',
				text: '名字'
			}
		]	
		 * */
		dataSet : [],
		/*
		 * 表单字段
		 * formFields: [
			{
				forData: 'id',
				xtype: 'ins_hiddenfield'
				
			},{
				forData: 'name',				
				allowBlank: false,
				colspan: 2
			}]
		*/
		formFields : [],	
		
		//权限
		permission : {
			add : true,
			update : true,
			remove : true,
			view: true
		},
		
		//默认表单字段宽度
		defaultFormFieldWidth: 230,
		
		//右键菜单
		customMenu : [],
		
		//在新增表单展示之前触发的函数
		onBeforeShowAddForm : null,
		//在编辑表单展示之前触发的函数
		onBeforeShowEditForm : null,
		//在查看明细之前触发的函数
		onBeforeShowViewForm: null,
		
		//在新增表单展示之后
		onAfterShowAddForm: null,
		//在编辑表单展示之后
		onAfterShowEditForm: null,
		//在查看明细后显示
		onAfterShowViewForm: null,
		
		
		//在执行删除之前执行的函数
		onBeforeDelete : null,
		//添加保存之前执行的函数
		onBeforeSaveAdd : null,
		//编辑保存之前执行的函数
		onBeforeSaveEdit : null,
		//右键菜单显示前
		onBeforeShowCxtMenu: null,
		//右键菜单显示后
		onAfterShowCxtMenu: null,
		
		//在删除操作成功之后
		onAfterDelete: null,
		
		//增加或编辑表单执行成功时调用，如果返回true则执行重置表单，topmessage等操作，返回false则不执行后续操作
		onAfterFormSubmit: null,
		
		//操作表单列数
		columnSize : 2,
		
		//自定义标签
		labelConfig : {
			add : {
				text : null,
				iconCls : null
			},
			update : {
				text : null,
				iconCls : null
			},
			view : {
				text : null,
				iconCls : null
			},
			remove : {
				text : null,
				iconCls : null
			}
		},
		
		//实际列
		gridColumns: null,	
		//隐藏表头
		hideTableHead: false,
		//折叠列
		treeAt: 'text',
		//默认列宽
		defaultColumnWidth: 120,		
		//禁用所有列的排序
		disabledColumnsSortable : false,
		
		
		//操作表单高度
		formWindowHeight : null,	
		//操作表单宽度
		formWindowWidth: 510,
		//操作表单最大高度
		formWindowMaxHeight: null,
		//操作表单最大宽度
		formWindowMaxWidth: null,
		//操作表单窗体是否遮罩
		formWindowModal: false
		
	},
	
	animate: false,
	rowLines : true,
    columnLines : true,
    width: '100%',
    
    multiSelect: false,
    
    viewConfig: { 
		stripeRows: true, 
		enableTextSelection: true
	},
	
	initComponent: function(){
		
		var me = this;
		
		//隐藏表头	
		Ext.apply(me, {    	
    		hideHeaders: me.hideTableHead		//隐藏表头
    	});
    	
    	//构造 store fields 数组
		var _fields = [];
		for(var i in me.dataSet){			
			var record = me.dataSet[i];
			_fields.push(record.name);
		}
		Ext.apply(me, {
			fields : _fields
		});
		
		//构造列
		var _gridColumns = [];
		for(var i in me.gridColumns){
			
			var columnObj = Ext.clone(me.gridColumns[i]);  
			
			var _columnObj = me.getColumnDefByName(columnObj);
			
			Ext.applyIf(_columnObj, {
				width : me.defaultColumnWidth,
				sortable: !me.disabledColumnsSortable
			});
			
			//处理 treeAt 属性
			if(_columnObj.dataIndex == me.treeAt){    				
				Ext.applyIf(_columnObj, {    					
					xtype : "treecolumn"
				});
			}
			
			_gridColumns.push(_columnObj);
			
		}
		Ext.apply(me,{
			columns: _gridColumns		
		});
		
		
		//生成表单字段数组
		var _formFields = [];	
		for(var i in me.formFields){			
			var item = me.formFields[i];
			var field = null;
			//不分组时
			if((item.forData!=undefined && item.forData!=null) || (item.name!=undefined && item.name!=null)){
				field = me.getFieldDefByName(Ext.clone(item));				
				//设置默认表单字段宽度（默认宽度只在没有指定宽度时有效）
				if(field != null && field.width == undefined && field.xtype!='component' && field.xtype != 'container' && field.xtype != 'panel' && field.xtype != 'ins_panel' && field.xtype != 'ins_fieldcontainer'){							
					Ext.apply(field, {
						width : me.defaultFormFieldWidth
					});
				}				
			}
			//分组时
			else if(item.items!=undefined && item.items!=null){
				field = Ext.clone(item);
				field.items = [];
				for(var j in item.items){
					if(item.items[j] != null && item.items[j].width == undefined && item.items[j].xtype!='component' && item.items[j].xtype != 'container' && item.items[j].xtype != 'panel' && item.items[j].xtype != 'ins_panel' && item.items[j].xtype != 'ins_fieldcontainer'){							
						Ext.apply(item.items[j], {
							width : me.defaultFormFieldWidth
						});
					}					
					field.items.push(me.getFieldDefByName(Ext.clone(item.items[j])));
				}
			}
			
			_formFields.push(field);
		}
		
		//操作表单配置
		var formConfig = {	
			fields : _formFields,
			modal: me.formWindowModal,
			defaultFormFieldWidth : me.defaultFormFieldWidth,
			columnSize : me.columnSize,
			onBeforeHide: function(obj){
				obj.getForm().reset();
			}
		};
		
		if(me.onAfterFormSubmit != null){
			formConfig.onAfterSubmit = me.onAfterFormSubmit;
		}
		
		if(me.formWindowHeight != null){
			formConfig.height = me.formWindowHeight;
		}		
		if(me.formWindowWidth != null){
			formConfig.width = me.formWindowWidth;
		}
		if(me.formWindowMaxHeight != null){
			formConfig.maxHeight = me.formWindowMaxHeight;
		}		
		if(me.formWindowMaxWidth != null){
			formConfig.maxWidth = me.formWindowMaxWidth;
		}	
		
		//操作表单
		var form = Ext.create("Ins.form.OprFormWindow", formConfig);	
		
		//覆盖基础属性
		Ext.apply(me, {
			form : form,//将 form 也注册到组件中，以便其他地方调用			
			url : me.urlConfig.loadAll
		});	
		
		//右键菜单项
		var _menu = [];
		
		//是否允许新增
		if(me.urlConfig.add!=undefined && me.urlConfig.add!=null){
			_menu.push({
				id : me.getId() + "_add_btn",
				hidden: !me.permission.add,
				text: me.labelConfig.add.text != null ? me.labelConfig.add.text : '添加',
				iconCls: me.labelConfig.add.iconCls != null ? me.labelConfig.add.iconCls : 'icon-add',
				handler: function(btn){
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowAddForm != null){						
						flag = me.onBeforeShowAddForm(me.getForm());
					}
					if(!flag){
						return;
					}
					form.showAsAddMode({
						url: me.urlConfig.add,
						onBeforeSave: me.onBeforeSaveAdd,
						success: function(data){
							me.reloadExpand();
						}
					});
					
					//如果注册有事件监听器，则注册
					if(me.onAfterShowAddForm != null){
						me.onAfterShowAddForm(me.getForm());
					}
				}
			});
		}
		
		//是否允许编辑
		if(me.urlConfig.update!=undefined && me.urlConfig.update!=null 
				&& me.urlConfig.load!=undefined && me.urlConfig.load!=null){			
			_menu.push({
				id : me.getId() + "_edit_btn",
				hidden: !me.permission.update,
				iconCls: me.labelConfig.update.iconCls != null ? me.labelConfig.update.iconCls : 'icon-edit',
				text: me.labelConfig.update.text != null ? me.labelConfig.update.text : '编辑',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowEditForm != undefined && me.onBeforeShowEditForm != null){						
						flag = me.onBeforeShowEditForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};					
					params[me.pkField] = me.getCurrentNode().data[me.pkField];	
					
					form.showAsEditMode({
						//告诉后台此操作不记录日志
						loadUrl: me.urlConfig.load + '?nolog=y',
						updateUrl: me.urlConfig.update,
						onBeforeSave: me.onBeforeSaveEdit,
						params: params,
						success: function(data){
							me.reloadExpand();
						},
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowEditForm != null){
								me.onAfterShowEditForm(form, data);
							}	
						}
					});
				}	
			});
		}
		
		//是否允许删除
		if(me.urlConfig.remove!=undefined && me.urlConfig.remove!=null){			
			_menu.push({
				id : me.getId() + "_delete_btn",
				hidden: !me.permission.remove,
		        text: me.labelConfig.remove.text != null ? me.labelConfig.remove.text : '删除',
		        iconCls: me.labelConfig.remove.iconCls != null ? me.labelConfig.remove.iconCls : 'icon-delete',
				handler: function(btn){					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeDelete != undefined && me.onBeforeDelete != null){
						
						flag = me.onBeforeDelete(me.getSelectedView());
					}
					if(!flag){
						return;
					}
										
					var id = me.getCurrentNode().data[me.pkField];
					
					Messager.send({
					  confirm: "确认要删除该节点吗？",
					  maskCmp: me,
					  url: me.urlConfig.remove,
					  data: {
						  id : id
					  },
					  onSuccess:function(data){							  	 
						  me.reloadExpand();
						  
						  if(me.onAfterDelete!=null){
						  	  me.onAfterDelete();
						  }
						  
						  TopMessage.info("操作成功！");
					  }
					});
				}
		    });
		}
		
		//是否允许查看明细
		if(me.urlConfig.load!=undefined && me.urlConfig.load!=null){			
			_menu.push({
				id : me.getId() + "_view_btn",
				hidden: !me.permission.view,
				iconCls: me.labelConfig.view.iconCls != null ? me.labelConfig.view.iconCls : 'icon-view',
				text: me.labelConfig.view.text != null ? me.labelConfig.view.text : '查看明细',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowViewForm != undefined && me.onBeforeShowViewForm != null){						
						flag = me.onBeforeShowViewForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};
					params[me.pkField] = me.getCurrentNode().data[me.pkField];
					
					form.showAsViewMode({
						loadUrl: me.urlConfig.load,
						params: params,
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowViewForm != null){
								me.onAfterShowViewForm(form, data);
							}	
						}
					});
				}	
			});
		}
		
		
		//添加用户自定义菜单项
		if(me.customMenu != undefined && me.customMenu.length > 0){			
			if(_menu.length > 0){
				_menu.push("-");
			}			
			for(var i in me.customMenu){				
				var item = me.customMenu[i];				
				_menu.push(item);
			}
		}
		
		//构建右键菜单
		if(_menu.length > 0){			
			var nodemenu = new Ins.menu.Menu({  	            
	            items : _menu
	        }); 			
			Ext.apply(me, {				
				contextMenu : nodemenu,
				onCtxClick : function(record, e){	
					if(e.preventDefault){
			      		e.preventDefault();     
			      	}else{       
			      		e.returnValue = false;
			     	}
					var flag = true;
					if(me.onBeforeShowCxtMenu != null){
						flag = me.onBeforeShowCxtMenu(nodemenu);
					}
					if(flag && _menu.length > 0){
						me.contextMenu.showAt(e.getXY());
						if(me.onAfterShowCxtMenu!=null){
							me.onAfterShowCxtMenu(nodemenu);
						}						
					}			        
				}
			});
		}		
		
		//增加销毁时事件,清空资源
		me.addListener("beforedestroy", function(){
			//销毁表单
			if(me.form!=undefined || me.form!=null){
				Ext.destroy(me.form);
			}
			//销毁右键菜单
			if(me.contextMenu!=undefined || me.contextMenu!=null){
				Ext.destroy(me.contextMenu);
			}
		});			
		
		me.callParent(arguments);
		
		
	},
	
		/**
	 * 根据列名构造列对象
	 * 
	 * @param column
	 * @returns {Object}
	 */
	getColumnDefByName : function(columnObj){
		
		var me = this;		
		var _columnObj = null;			
		
		if(typeof columnObj == 'string'){
			_columnObj = {
				forData: columnObj
			};	
		}else if(typeof columnObj == 'object'){
			_columnObj = columnObj;
		}	
		for(var i in me.dataSet){				
			var record = me.dataSet[i];			
			if(record.name == _columnObj.forData){					
				Ext.apply(_columnObj, {					
					text : record.text,
					dataIndex : record.name
				});					
				break;
			}
		}			
		return _columnObj;
	},
	
	/**
	 * 根据字段名构造字段对象
	 * 
	 * @param fieldName
	 * @returns {Object}
	 */
	getFieldDefByName : function(fieldOld){	
		var me = this;		
		//遍历数据集并定位到该字段对应的配置		
		for(var i in me.dataSet){			
			var record = me.dataSet[i];	
			if(record.name == fieldOld.forData){				
				//更新属性
				Ext.apply(fieldOld, {
					fieldLabel: record.text,
					text : record.text,
					name : record.name
				});				
				return fieldOld;
			}						
		}	
		
		if(fieldOld.name!=null && fieldOld.name!=undefined){
			//更新属性
			Ext.apply(fieldOld, {
				fieldLabel: fieldOld.text,
				text : fieldOld.text					
			});				
			return fieldOld;
		}
		if(window.console){
			console.error('没有发现 dataSet 中配置的该表单字段。');
		}
	},
	
	/**
	 * 获取表单对象
	 * 
	 * @returns
	 */
	getForm : function(){
		
		var me = this;
		
		return me.form.getForm();
	}
	
});;/**
 * 选择器树
 */
Ext.define('Ins.tree.ChooserTree',{
	extend: 'Ins.tree.Tree',
	alias: 'widget.ins_choosertree',
	
	config: {			
		//确认选择
		onChooserSelect: null,
		//选择前
		onBeforeChooserSelect: null,
		//取消按钮
		onChooserCancel: null,
		//是否可查询
		isSearch: false,
		showDefaultActBut: true,
	},
	
	initComponent: function(){
			
		var me = this;	
		me.searchFiled = me.isSearch;
		me.hideTitle = true;
		
		if(me.showDefaultActBut){
			me.actionButtons.unshift({
		         text:'取消',
	            iconCls:'icon-delete',
				handler: function(btn){
					if(me.onChooserCancel != null){
						me.onChooserCancel();
					}
				}
	        });
			
			me.actionButtons.unshift({
				text:'确定选择',
	            iconCls:'icon-ok',
				handler: function(btn){
					
					var selection = me.getSelectedView();				
					
					if(selection.count == 0){
						TopMessage.warn("请选择记录");
						return;
					}
					
					var rsFlag = true;
					if(me.onBeforeChooserSelect != undefined && me.onBeforeChooserSelect != null){					
						rsFlag = me.onBeforeChooserSelect(selection.records[0]);
					}
					if(!rsFlag){
						return;
					}
					
					if(me.onChooserSelect != null){
						me.onChooserSelect(selection.records[0]);
					}
					
				}
			});
		}
				
		me.callParent(arguments);
	}
});;;var FormToolkit = {
		
	//默认必填标志
	REQUIRED_TPL : '<span style="color:red;font-weight:bold" data-qtip="必输项">*</span>',
	
	config: {
		//label
		text : null,
		//获取焦点事件
		onFieldFocus : null,
		//失去焦点事件
		onFieldBlur : null,
		//改变内容事件
		onFieldChange : null,
		//验证提示消息显示在右边
		msgTarget: 'side'           
	},
	
	init: function(component){
		//只有定义的时候才设置属性,否则会造成原始属性失效
		if(component.text != null){
			
			Ext.apply(component, {
				
				fieldLabel : component.text
			});
		}
		
		//如果该项为必填项，则自动添加 *
		if(component.allowBlank!=undefined && !component.allowBlank){
			
			Ext.apply(component, {
				
				afterLabelTextTpl : FormToolkit.REQUIRED_TPL
			});
		}
		
		//注册事件
		if(component.onFieldFocus != null){			
			component.addListener("focus", component.onFieldFocus);
		}
		
		if(component.onFieldBlur != null){			
			component.addListener("blur", component.onFieldBlur);
		}
		
		if(component.onFieldChange != null){			
			component.addListener("change",component.onFieldChange);
		}
	},
	
	/**
	 * 设置表单字段是否只读
	 * 
	 * @param form
	 * @param flag
	 */
	setFormFieldsReadonly : function(form, flag){
		
		form.getForm().getFields().each(function(field) { 
			try{
				if(field.xtype != 'ins_htmleditor'){
					field.setReadOnly(flag);
					if(flag == true){
						field.setFieldStyle({backgroundColor:"#EBEBEB"});
					}else{
						field.setFieldStyle({backgroundColor:"#FFF"});
					}						
				}				
			}catch(exception){
				
			}
			
	    });
	}
		
};;/**
 * 文本框
 */
;Ext.define('Ins.form.field.TextField',{
	extend: 'Ext.form.field.Text',
	alias : ["widget.ins_textfield"],
	
	config : FormToolkit.config,
		
	maxLength: 32,
	
	initComponent:function(){
		
		var me = this;
		
		FormToolkit.init(this);
		
		if(me.allowBlank == false){
			me.allowOnlyWhitespace = false;
		}
		
		this.callParent(arguments);
	}
	
});;/**
 * 密码框
 */
;Ext.define('Ins.form.field.PasswordField',{
	extend: 'Ins.form.field.TextField',
	alias : ["widget.ins_passwordfield"],
	
	inputType: 'password'
});;/**
 * 隐藏域
 */
;Ext.define('Ins.form.field.HiddenField',{
	extend: 'Ext.form.field.Hidden',
	alias : ["widget.ins_hiddenfield"],
	
	config : {		
		//改变内容事件
		onFieldChange : null
	},
		
	initComponent:function(){
		
		var me = this;
		
		if(me.onFieldChange != null){			
			me.addListener("change",me.onFieldChange);
		}
		
		this.callParent(arguments);
	}
	
});;/**
 * 数字框
 */
;Ext.define('Ins.form.field.NumberField',{
	extend: 'Ext.form.field.Number',
	alias : ["widget.ins_numberfield"],
	
	config : FormToolkit.config,
	
	decimalPrecision : 12,
	
	maxLength: 32,
	
	minValue: 0,
	maxValue: 999999999999,
	
	initComponent:function(){
		
		FormToolkit.init(this);
		
		this.callParent(arguments);
	}
	
});;/**
 * 下拉框
 */
;Ext.define('Ins.form.field.List',{
	extend: 'Ext.form.field.ComboBox',
	alias : ["widget.ins_list"],	
	
	config : FormToolkit.config,
	
	constructor: function(config){		
		var me = this;
		Ext.apply(me,{
			list : [],
			fields: ["text", "value"]
		});		
		me.callParent([config]);		
	},
	
	editable: false,
	displayField: 'text',
    valueField: 'value',
    queryMode: 'local',
	
	initComponent:function(){		
		
		var me = this;
		
		FormToolkit.init(me);
		
		if(me.list != null && me.list.length > 0){
			//绑定列表
			me.store = {
				    fields: me.fields,
				    data : me.list
				};
		}		
		
		this.callParent(arguments);
	},
	
	/**
	 * 获取列表
	 * 
	 * @returns {Array}
	 */
	getList : function(){
		
		var me = this;
		
		return me.list;
	},
	
	/**
	 * 根据 value 获取 text
	 * 
	 * @param value
	 * @returns
	 */
	getTextByValue : function(value){
		
		var me = this;
				
		for(var i in me.list){
			
			var item = me.list[i];
			
			if(item.value == value){
				
				return item.text;
			}
		}
		
		return null;
	},
	
	/**
	 * 根据 text 获取 value
	 * 
	 * @param text
	 * @returns
	 */
	getValueByText : function(text){
		
		var me = this;
				
		for(var i in me.list){
			
			var item = me.list[i];
			
			if(item.text == text){
				
				return item.value;
			}
		}
		
		return null;
	}
	
});;/**
 * 动态下拉框
 */
;Ext.define('Ins.form.field.DynamicList',{
	extend: 'Ins.form.field.List',
	alias : ["widget.ins_dynamiclist"],
	
	config : {
		url: null,
		params: {}
	},
	
	initComponent:function(){
		
		var me = this;
				
		//必须判断地址是否为空
		if(me.url != null){
			
			Ext.apply(me, {
				
				store : me.buildStore(me.url, me.params)
			});
			
		}
		
		this.callParent(arguments);
	},
	
	/**
	 * 获取列表(重写父类的方法)
	 * 
	 * @returns {Array}
	 */
	getList : function(){
		
		var me = this;
		
		var list = [];
		
		me.getStore().each(function(record){
			
			list.push({
				text : record.get("text"),
				value : record.get("value")
			});
		});
		
		return list;
	},
	
	
	/**
	 * private 构造 store
	 * 
	 * @param url
	 * @param queryParams
	 */
	buildStore : function(url, params){
		
		var me = this;
		
		var store = new Ins.data.Store({			
            fields: me.fields,  
            url : url,
            params: params
        });
		
		return store;
	},
	
	/**
	 * 重新加载数据
	 * 
	 * @param cfg
	 * cfg 包含：	 
	 * params : 查询参数
	 */
	load : function(params){	
		
		var me = this;
		if(params != undefined && params != null){
    		
    		me.getStore().proxy.extraParams = params;
    	}		
			
		me.getStore().reload();
		
	}
	

	
	
});;/**
 * 多选列表
 */
;Ext.define('Ins.form.field.TagList',{
	extend: 'Ext.form.field.Tag',
	alias : ["widget.ins_taglist"],
	
	config : FormToolkit.config,
	
	constructor: function(config){		
		var me = this;
		Ext.apply(me,{
			url: null,
			list : [],
			fields: ["text", "value"]
		});		
		me.callParent([config]);		
	},
	
	
	displayField: 'text',
    valueField: 'value',
    //filterPickList: true,
	
	initComponent:function(){
		
		var me = this;
				
		//必须判断地址是否为空
		if(me.url != null){
			
			Ext.apply(me, {
				
				store : me.buildStore(me.url, me.params)
			});
			
		}
		
		//去重复
		Ext.apply(me, {			
			listeners : {
				change: function(obj, newValue, oldValue, eOpts ){
					if(newValue.length!=undefined && newValue.length>0){
						var array = [];
						for(var i=0;i<newValue.length;i++){	
							//IE8不支持数组indexOf
							//if(array.indexOf(newValue[i]) == -1){
							//	array.push(newValue[i]);
							//}
							var hasVal = false;
							for(var j=0;j<array.length;j++){
								if(array[j] == newValue[i]){
									hasVal = true;
									break;
								}
							}
							if(!hasVal){
								array.push(newValue[i]);
							}
						}
						me.setValue(array);
					}
				}				
			}			
		});
		
		FormToolkit.init(this);
				
		this.callParent(arguments);
	},
	
	/**
	 * 获取列表(重写父类的方法)
	 * 
	 * @returns {Array}
	 */
	getList : function(){
		
		var me = this;
		
		var list = [];
		
		me.getStore().each(function(record){
			
			list.push({
				text : record.get("text"),
				value : record.get("value")
			});
		});
		
		return list;
	},
	
	
	/**
	 * private 构造 store
	 * 
	 * @param url
	 * @param queryParams
	 */
	buildStore : function(url, params){
		
		var me = this;
		
		var store = new Ins.data.Store({	
            fields: me.fields,  
            url : url,
            params: params
        });
		
		return store;
	},
	
	/**
	 * 重新加载数据
	 * 
	 * @param cfg
	 * cfg 包含：	 
	 * params : 查询参数
	 */
	load : function(params){	
		
		var me = this;
		
		if(params != undefined && params != null){
    		
    		me.getStore().proxy.extraParams = params;
    		
    	}		
			
		me.getStore().load();
		
	}
	
});;/**
 * 日期选择器
 */
;Ext.define('Ins.form.field.DateField',{
	extend: 'Ext.form.field.Date',
	alias : ["widget.ins_datefield"],
	
	config : FormToolkit.config,
	
	format : "Y-m-d",
	
	initComponent:function(){
		
		FormToolkit.init(this);
		
		this.callParent(arguments);
	},
	
	/**
	 * 获取字符串形式的数值
	 * 
	 * @returns
	 */
	getStringValue : function(){
		
		var me = this;
		
		return me.getRawValue();
	}
	
});;/**
 * 日期时间选择器
 */
;Ext.define('Ins.form.field.DateTimeField',{
	extend:'Ext.form.field.Picker',
	alias : ["widget.ins_datetimefield"],
	
	config : FormToolkit.config,	
	
	initComponent : function(){
        
    	var me = this;
    	
    	FormToolkit.init(this);
    	
        me.callParent();
    },
      
    format : 'yyyy-MM-dd HH:mm:ss',
    //日期样式图标
    triggerCls : Ext.baseCSSPrefix + 'form-date-trigger',
    //下拉样式图标
    //triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
    //自动计算宽度
    matchFieldWidth: false,     
   
    //valuePublishEvent: ['select', 'blur'],
  
    
    //继承textfield
    getErrors: function(value) {
    	var me = this;
    	
    	var errors = me.callParent([value]);
    	if(errors.length > 0){
    		return errors;
    	}
    	svalue = value;
    	value = Ext.Date.parse(value, "Y-m-d H:i:s", true);
    	if(svalue != ''){
    		if(value == null){
	    		errors.push(svalue+" 是无效的日期时间格式 - 必须符合格式：Y-m-d H:i:s");
	            return errors;
	    	}
    	}    	
    },
    
    //初始化方法(继承自父类)
    createPicker: function() {
	
	    var me = this;
		
		var dateId = "d_" + new Date().getTime();
		Ext.apply(me, {
			dateId : dateId
		});
		
		var currTime = new Date();
		
		me.form = Ext.widget({
	        xtype: 'form',	               
	        layout: 'vbox',
	        items: [
	            {
				    xtype: 'datepicker',
				    colspan: 5,
				    id: dateId
				},	
				{
					xtype: 'container',
                    layout: 'hbox',
                    items:[{
    					xtype: 'numberfield',
    					name: 'hour',
    				    width: 55,
    				    minValue: 0,
    				    maxValue: 23,
    				    allowBlank: false,
    				    value : currTime.getHours()
    				},
    				{
    					xtype: 'ins_displayfield',
    					value: ':'
    				},
    				{
    					xtype: 'numberfield',
    					name: 'min',
    					minValue: 0,
    					maxValue: 59,
    				    width: 55,
    				    allowBlank: false,
    				    value : currTime.getMinutes()
    				},
    				{
    					xtype: 'ins_displayfield',
    					value: ':'
    				},
    				{
    					xtype: 'numberfield',
    					name: 'seconds',
    					minValue: 0,
    					maxValue: 59,
    				    width: 55,
    				    allowBlank: false,
    				    value : 0
    				}]
				}
				
	        ],
	        buttons: [{
	        	text: '选择',
	        	iconCls : "icon-ok",
	        	handler: function(){
	        		
	        		me.onSelect();
	        	}
	        }]
		});
		
		var picker = Ext.create('Ext.panel.Panel',{
			border: true,
	        floating: true,	       
			items: [me.form],
			//这个非常重要
			onMouseDown: function(e) {
				if(e.preventDefault){
		      		e.preventDefault();     
		      	}else{       
		      		e.returnValue = false;
		     	}		       
		    }
		});
						
		return picker;
       
    },

    onSelect: function(m, d) {
    	var me = this;
		
		if(me.form.isValid()){
			
			var dateVal = "";
			
			var date = Ext.getCmp(me.dateId).getValue();
			var form = me.form.getForm();
			
			var year = date.getFullYear();
			var month = (date.getMonth() + 1);
			var date = date.getDate();
			var hour = form.findField("hour").getValue();
	    	var min = form.findField("min").getValue();
	    	var seconds = form.findField("seconds").getValue();
	    	
	    	if(month < 10){
	    		month = "0" + month;
	    	}
	    	if(date < 10){
	    		date = "0" + date;
	    	}
	    	if(hour < 10){
	    		hour = "0" + hour;
	    	}
	    	if(min < 10){
	    		min = "0" + min;
	    	}
	    	if(seconds < 10){
	    		seconds = "0" + seconds;
	    	}
	    	
	    	var val = me.format
	    			.replace("yyyy", year)
	    			.replace("MM", month)
	    			.replace("dd", date)
	    			.replace("HH", hour)
	    			.replace("mm", min)
	    			.replace("ss", seconds);
	    	    	
			me.setValue(val);
			//me.picker.hide();
			me.collapse();
		}		
       
    },
    
    
    //展开 （继承自父类）
    onExpand: function() {
        
    },

    //失去焦点(继承成自textfield)
    onBlur: function(e) { 
    	//将blur事件继续传播
    	var me = this;
    	me.callParent([e]);
    }

});
;/**
 * 文本域
 */
;Ext.define('Ins.form.field.TextAreaField',{
	extend: 'Ext.form.field.TextArea',
	alias : ["widget.ins_textareafield"],
	
	config : FormToolkit.config,
	
	maxLength: 256,	
	
	initComponent:function(){
		
		var me = this;
		
		FormToolkit.init(this);
		
		if(me.allowBlank == false){
			me.allowOnlyWhitespace = false;
		}
		
		this.callParent(arguments);
	}
	
});;/**
 * 文件框
 */
;Ext.define('Ins.form.field.FileField',{
	extend: 'Ext.form.field.File',
	alias : ["widget.ins_filefield"],
	
	config : FormToolkit.config,
	
	/*buttonConfig: {
        text : '',
        iconCls: 'icon-filechooser'
    },*/
    
    buttonText: '请选择...',
	
	initComponent:function(){
		
		var me = this;
		
		FormToolkit.init(this);		
		
		this.callParent(arguments);
		
	}
	
});;/**
 * 显示框
 */
;Ext.define('Ins.form.field.DisplayField',{
	extend: 'Ext.form.field.Display',
	alias : ["widget.ins_displayfield"],
	
	config : FormToolkit.config,
	
	initComponent:function(){
		
		FormToolkit.init(this);
		
		this.callParent(arguments);
	}
	
});;/**
 * filed容器
 */
;Ext.define('Ins.form.FieldContainer',{
	extend: 'Ext.form.FieldContainer',
	alias : ["widget.ins_fieldcontainer"],
	
	config: {
		text : null
	},
	
	layout: 'hbox',
	defaults: {       
       // margin: '0 5 0 0'
    },
    
    defaultType: 'ins_textfield',	
	
	initComponent:function(){
		
		var me = this;
		
		//只有定义的时候才设置属性,否则会造成原始属性失效
		if(me.text != null){
			
			Ext.apply(me, {
				
				fieldLabel : me.text
			});
			
			//如果该项为必填项，则自动添加 *
			if(me.allowBlank!=undefined && !me.allowBlank){
				
				Ext.apply(me, {
					
					afterLabelTextTpl : FormToolkit.REQUIRED_TPL
				});
			}
			
		}
		
		this.callParent(arguments);
	}
	
	
});;/**
 * filed容器
 */
;Ext.define('Ins.form.FieldSet',{
	extend: 'Ext.form.FieldSet',
	alias : ["widget.ins_fieldset"],
	
	config: {
		text: null,
		//列数
		columnSize : 2,
		//字段列宽
		defaultFormFieldWidth : 230
	},	
	
	
	//默认保持间距，否则多个 FieldSet 对象会连接
	margin: "2 2 2 2",
	collapsible: true,
	
	padding: '5 5 10 5',
	
	defaultType: 'ins_textfield',	
	
	initComponent:function(){
		
		var me = this;		
		
		Ext.apply(me, {
			
			//标题加粗
			title : "<span style='font-weight:bold;'>" + me.text + "</span>",
			
			layout: {
		        type: 'table',
		        columns: me.columnSize
		    },
		    
		    defaults : {
		    	
		    	width : me.defaultFormFieldWidth
		    }
		});
		
		this.callParent(arguments);
	}
	
	
});;/**
 * 单选框
 */
;Ext.define('Ins.form.field.RaidoField',{
	extend: 'Ext.form.RadioGroup',
	alias : ["widget.ins_radiofield"],
	
	config : {
		//lebel
		text: null,	
		
		name: null,
		//列数
		columnSize: 2,	
		//单选项 例如 {text:'radio1',value:'radio1'}
		boxs: [],
		//改变内容事件
		onFieldChange : null
		
	},	
	
	msgTarget: 'side',           
		
	initComponent:function(){		
		
		var me = this;
		
		var items = [];
		
		//如果该项为必填项，则自动添加 *
		if(me.allowBlank!=undefined && !me.allowBlank){			
			Ext.apply(me, {				
				afterLabelTextTpl : FormToolkit.REQUIRED_TPL
			});
		}
		
		//将单选项添加到RadioGroup中		
		for(var i=0;i<me.boxs.length;i++){
			//如果单选项为设置checked ，则为false
			if(me.boxs[i].checked==undefined || me.boxs[i].checked==null){
				me.boxs[i].checked = false;
			}			
			//如果单选项的value和group的value相同，则checked为true
			if(me.value!=undefined&&me.value!=null&&me.value!=''){				
				if(me.value == me.boxs[i].value){
					me.boxs[i].checked = true;					
				}	
				//用于重置时使用
				me.initRadioValue = me.value;
			}	
			
			items.push({
				boxLabel: me.boxs[i].text,
				name: me.name,
				inputValue: me.boxs[i].value,
				checked: me.boxs[i].checked,
				hidden: me.boxs[i].hidden,
				disabled: me.boxs[i].disabled
			});			
			
		}
		
		Ext.apply(me,{
			columns: me.columnSize,
			fieldLabel: me.text,
			items: items
		});
		
		//注册事件		
		if(me.onFieldChange != null){			
			me.addListener("change",me.onFieldChange);
		}
		
		
		this.callParent(arguments);
	},
	
	//重写父类的setValue方法
	setValue : function(value){
		var me = this;
		eval("value={"+me.name+":value};");
		me.callParent([value]);
	},
	//重写父类的getValue方法，不过此方法调用时会返回两次
	//不影响使用
	getValue : function(){
		var me = this;		
		var obj = me.callParent();
		return obj[me.name];
	},
	//重写父类reset方法
	reset: function(){
		var me = this;
		me.setValue(me.initRadioValue);
	}
	
	
	
});;/**
 * 复选框
 */
;Ext.define('Ins.form.field.CheckboxField',{
	extend: 'Ext.form.CheckboxGroup',
	alias : ["widget.ins_checkboxfield"],
	
	config : {
		//lebel
		text: null,	
		
		name: null,
		//列数
		columnSize: 2,	
		//单选项 例如 {text:'checkbox1',value:'checkbox1'}
		boxs: [],
		//改变内容事件
		onFieldChange : null
	},	
	
	blankText : "该输入项为必输项",
	
	msgTarget: 'side',
		
	initComponent:function(){		
		
		var me = this;
		
		//如果该项为必填项，则自动添加 *
		if(me.allowBlank!=undefined && !me.allowBlank){			
			Ext.apply(me, {				
				afterLabelTextTpl : FormToolkit.REQUIRED_TPL
			});
		}
		
		var items = [];
		
		//将选项添加到RadioGroup中		
		for(var i=0;i<me.boxs.length;i++){
			//如果选项为设置checked ，则为false
			if(me.boxs[i].checked==undefined || me.boxs[i].checked==null){
				me.boxs[i].checked = false;
			}
			
			//如果group的value中包含此项的值，则checked=true
			if(me.value!=undefined&&me.value!=null&&me.value!=''){			
				//IE8不支持
				/*if(me.value.indexOf(me.boxs[i].value) != -1){
					me.boxs[i].checked = true;
				}*/	
				for(var j in me.value){
					if(me.value[j] == me.boxs[i].value){
						me.boxs[i].checked = true;
						break;
					}			
				}
				//用于重置时使用
				me.initRadioValue = me.value;
			}	
			
			items.push({
				boxLabel: me.boxs[i].text,
				name: me.name,
				inputValue: me.boxs[i].value,
				checked: me.boxs[i].checked,
				hidden: me.boxs[i].hidden,
				disabled: me.boxs[i].disabled
			});
		}
		
		Ext.apply(me,{
			columns: me.columnSize,
			fieldLabel: me.text,
			items: items
		});
		
		//注册事件		
		if(me.onFieldChange != null){			
			me.addListener("change",me.onFieldChange);
		}
		
		this.callParent(arguments);
		
	},
	
	//重写父类的setValue方法
	setValue : function(value){
		var me = this;
		eval("value={"+me.name+":value};");
		me.callParent([value]);
	},
	
	//重写父类的getValue方法	
	getValue : function(){
		var me = this;		
		var obj = me.callParent();
		return obj[me.name];
	},
	
	//重写父类reset方法
	reset: function(){
		var me = this;
		me.setValue(me.initRadioValue);
	}
	
});;/**
 * 滑动条
 */
;Ext.define('Ins.form.field.SliderField',{
	extend: 'Ext.slider.Single',
	alias : ["widget.ins_sliderfield"],
	
	config : {
		
		text: null,
		//拖拽事件
		onFieldDrag: null,
		//改变内容事件
		onFieldChange : null
	},
	
	
	//默认宽度
	width : 270,
	//默认最小值
	minValue : 0,
	//默认最大值
	maxValue : 100,
	//默认初始值
	value : 0,
	//是否隐藏text
	hideLabel : false,
	//拖动时是否提示
	useTips : true,
			
	initComponent:function(){
		
		var me = this;
		
		//只有定义的时候才设置属性,否则会造成原始属性失效
		if(me.text != null){
			
			Ext.apply(me, {
				
				fieldLabel : me.text
			});
		}
		
		
		//注册事件
		if(me.onFieldDrag != null){			
			me.addListener("drag", me.onFieldDrag);
		}
		
		if(me.onFieldChange != null){			
			me.addListener("change", me.onFieldChange);
		}
				
		this.callParent(arguments);
	}
	
});;/**
 * 富文本编辑器
 */
;Ext.define('Ins.form.field.HtmlEditor',{
	extend: 'Ext.form.FieldContainer',
	//多重继承
	mixins: [        
        'Ext.form.field.Field'
    ], 
	alias : ["widget.ins_htmleditor"],
	
	//使用公有的配置项
	config: FormToolkit.config,	
	
	//编辑器实例
	ueditorInstance: null,
	
	//是否初始化
    initialized: false,    
       
	initComponent: function () {
		
        var me = this;  
        
        //var _value = me.value;
        
        //只有定义的时候才设置属性,否则会造成原始属性失效
		if(me.text != null){
			
			Ext.apply(me, {
				
				fieldLabel : me.text
			});
		}
		
		//如果该项为必填项，则自动添加 *
		if(me.allowBlank!=undefined && !me.allowBlank){
			
			Ext.apply(me, {
				
				afterLabelTextTpl : FormToolkit.REQUIRED_TPL
			});
		}
		
        //如果id为空用随机数生成id
        var id = ((me.id==undefined||me.id==null) ? '' : me.id) + '-ueditor-'+new Date().getTime();
        
        me.html = '<script id="' + id + '" type="text/plain" name="' + me.name + '"></script>';
        
        me.callParent(arguments);
        
        //调用Ext.form.field.Field中的方法
        me.initField();
        
        
        me.on('render', function () {
        	
        	//ueditor配置项       
			var config = {
				elementPathEnabled: false,
				wordCount: false,
				autoFloatEnabled: true,
				autoHeightEnabled: false,		
				enableContextMenu: false,
				enableAutoSave: false,	
				initialFrameWidth: '100%',
				initialFrameHeight: me.ueheight,
				pasteplain: false,
				toolbars: [[
					'fontsize',
			          //'source',
			          'bold',
			          'underline',
			          'italic',
			          'forecolor',
			          'backcolor',
					'|',
			          'blockquote',
			          'insertorderedlist',
			          'insertunorderedlist',
			          'justifyleft', //居左对齐
			          'justifycenter',
			          'justifyright', //居右对齐          
			          '|',
			          'link',//超链接
			          'insertimage',
			          'insertvideo', //视频
			          'preview' //预览
			          //'fullscreen'
			      ]]
				
			};
            //实例化ueditor
            me.ueditorInstance = UE.getEditor(id, config);
            me.ueditorInstance.ready(function () {
                me.initialized = true;
                //me.fireEvent('initialize', me);                
               
                
                if(me._value!=undefined && me._value!=null && me._value!=''){                	
                	me.setValue(me._value);
                }
                
                //添加事件
                if(me.onFieldFocus != null){
                	 me.ueditorInstance.addListener('focus',function(){
			       		me.onFieldFocus(me);
			    	});
                }                
                me.ueditorInstance.addListener('blur',function(){
            		me.isValid();
	                if(me.onFieldBlur != null){
			       		me.onFieldBlur(me);
                	}                 
		    	});              
                me.ueditorInstance.addListener('contentChange',function(){
        	 		me.isValid();
	                if(me.onFieldChange != null){
				       	me.onFieldChange(me);
	                }    
		    	}); 
                			   
            });
            
        });
    },
    
    getValue: function () {
        var me = this,
            value = '';
        if (me.initialized) {
            value = me.ueditorInstance.getContent();
        }
        me.value = value;
        return value;
    },
    
    setValue: function (value) {
        var me = this;
        if (value === null || value === undefined) {
            value = '';
        }
        if (me.initialized) {
            me.ueditorInstance.setContent(value);
        } else {
        	me._value = value;
        }
        return me;
    },
    
    setReadOnly: function(flag){
    	
    	var me = this; 
    	
    	if(flag){
    		me.ueditorInstance.setDisabled();    		
    	}else{
    		me.ueditorInstance.setEnabled();
    	}
    	
    },
    /**
     * 重写校验
     */
    isValid:function(){
    	var me = this;
		if (me.allowBlank==false) {
			if (me.getValue()==''||me.getValue()==undefined||me.getValue()==null) {
		    	$("#"+me.getId()+"-innerCt iframe").css({"border":"1px solid red"});
		    	return false;
			}
	    	$("#"+me.getId()+"-innerCt iframe").css({"border":''});
		}
    	return true;
    },
    
	/**
	 * 重写重置方法
	 */
    reset:function( ){
    	var me = this;
    	//重置值
    	me.setValue('');
    	//重置必填框
    	$("#"+me.getId()+"-innerCt iframe").css({"border":''});
    }
	
});;/**
 * 表单面板
 */
;Ext.define('Ins.form.Form',{
	extend: 'Ext.form.Panel',
	alias : ["widget.ins_form"],
	
	config: {
		fields : [],
		//列数
		columnSize : 2,
		//字段默认宽度
		defaultFormFieldWidth : 230,
		//工具栏按钮(默认在表单底部)
		actionButtons : [],
		//label宽度
		textWidth: 100,
		//label位置
		textAlign: 'top',
		//labelStyle
		textStyle: ''
		
	},
		
	//默认filed类型为文本框
	defaultType: 'ins_textfield',	
	
	bodyPadding: 6,
	
	scrollable : true,

	//保存表单额外参数
    extraValues : {},
        
		
	initComponent:function(){
		
		var me = this;
		
		if(me.fields.length > 0){
			
			for(var i in me.fields){
				
				var field = me.fields[i];
				//设置默认表单字段宽度（默认宽度只在没有指定宽度时有效）				
				if(field != null && field.width == undefined){
					//不包括自定义字段
					if(field.xtype != 'component' && field.xtype != 'container' && field.xtype != 'panel' && field.xtype != 'ins_panel'){
						if(field.xtype != 'ins_fieldset'){
							Ext.apply(field, {							
								width : me.defaultFormFieldWidth
							});
						}else{
							Ext.apply(field, {							
								width : '100%'
							});
						}
					}
				}
			}
		}
		
		if(me.actionButtons.length > 0){
			Ext.apply(me, {	
				buttons : me.actionButtons
			});
		}
		
		Ext.apply(me, {			
			items : me.fields,			
			layout: {
		        type: 'table',
		        columns: me.columnSize
		    },
		    defaults: {
		        //anchor: '100%',
		        //allowBlank: false,
				//labelWidth: me.textWidth				
		    },
		    fieldDefaults: {
				//text的对齐方向
				labelAlign: me.textAlign,
				margin:'2 5 2 2',
				labelStyle: me.textStyle
		    }
		});
		
		
		this.callParent(arguments);
	},
	
	/**
	 * 获取指定表单中的指定字段
	 * 
	 * @param name
	 * @returns
	 */
	getField : function(name){
		
		var me = this;
		
		return me.getForm().findField(name);
	},
	
	/**
	 * 获取指定字段的值
	 * 
	 * @param name
	 * @returns
	 */
	getValue : function(name){
		
		var me = this;
		
		var field = me.getForm().findField(name);
		
		//如果不在表单字段中，则在拓展字段列表中查找
		if(field == undefined || field == null){
			
			return me.extraValues[name];
		}
		
		//对于 ins_datefield, ins_timefield 进行特殊处理
		if(field.xtype == "ins_datefield"){
			
			return field.getRawValue();
		}
		
		return field.getValue();
	},
	
	/**
	 * 获取表单参数(重写父类方法)
	 * 
	 * @returns
	 */
	getValues : function(){
		
		var me = this;
		
		var data = me.getForm().getValues();
		Ext.applyIf(data, me.extraValues);
				
		return data;
	},
	
	/**
	 * 设置表单额外参数
	 * 
	 * @param vals
	 */
	setExtraValues : function(vals){
		
		var me = this;
		
		Ext.applyIf(me.extraValues, vals);
	},
	
	/**
	 * 设置指定字段的值
	 * 
	 * @param name
	 * @param value
	 */
	setValue : function(name, value){
		
		var me = this;
		
		me.getField(name).setValue(value);
	},
	
	/**
	 * 验证
	 */
	valid : function(cfg){
		
		var me = this;
		
		if(me.getForm().isValid()){
			
			if(cfg.onSuccess != undefined && cfg.onSuccess != null){
				
				cfg.onSuccess(me);
			}
		}else{
			
			if(cfg.onError != undefined && cfg.onError != null){
				
				cfg.onError(me);
			}
		}
	},
	
	/**
	 * 加载数据
	 * 
	 * @param cfg
	 */
	loadData : function(cfg){
		
		var me = this;
		
		me.getForm().load({
			url: cfg.url,
			waitMsg: '正在加载数据，请稍后...',
			params: cfg.params,
			failure: function(response, options){				
				//如果数据加载失败，则重置表单
				me.getForm().reset();
		    },
		    success : function(form, data){
		    	
		    	if(cfg.onSuccess != undefined && cfg.onSuccess != null){
		    		
		    		cfg.onSuccess(me, data);
		    	}
		    }
		});
	},
	
	/**
	 * 发送请求
	 * 
	 * @param cfg
	 */
	send : function(cfg){		
		var me = this;		
		//验证有效性
		me.valid({			
			onSuccess : function(form){				
				//如果包含提示，则询问
				if(cfg.confirm != undefined && cfg.confirm != null){					
					Message.ask(cfg.confirm, function(flag){						
						if(flag){							
							me.sendBase(cfg);
						}
					});
				}else{					
					//没有提示，不询问
					me.sendBase(cfg);
				}
			},			
			//验证错误
			onError : function(form){				
				if(cfg.onValidError != undefined && cfg.onValidError != null){					
					cfg.onValidError(form);
				}
			}
		});
	},
	
	/**
	 * private 发送请求基类
	 * 
	 * @param cfg
	 */
	sendBase : function(cfg){
		
		var me = this;
		
		var maskText = (cfg.maskText == undefined || cfg.maskText == null) ? '正在执行，请稍后...': cfg.maskText;
					
		//使用原生提交方式，以便支持文件上传
		me.submit({
            url: cfg.url,
            waitMsg: maskText,
            success: function(form, action) {
                
				if(cfg.onSuccess != null){
					
					cfg.onSuccess(action.result);
				}
            },
            failure: function(form, action) {                    	
            	try{
            		return App.errorHandler(action.response);		
            	}catch(exception){
            		var msg = '操作失败或系统错误';
            		TopMessage.error(msg);
					return false;
            	}                    	
            }
        });			
		
	},	
	
	/**
	 * 隐藏指定 name 的字段组件
	 * 
	 * @param fieldNames
	 */
	hideFields : function(fieldNames){
		
		var me = this;
		
		if(fieldNames != undefined && fieldNames.length > 0){
			
			for(var i in fieldNames){
				
				var name = fieldNames[i];
				
				me.getField(name).hide();
			}
		}
	},
	
	/**
	 * 显示指定 name 的字段组件
	 * 
	 * @param fieldNames
	 */
	showFields : function(fieldNames){
		
		var me = this;
		
		if(fieldNames != undefined && fieldNames.length > 0){
			
			for(var i in fieldNames){
				
				var name = fieldNames[i];
				
				me.getField(name).show();
			}
		}
	},
	
	/** 
	 * 重置表单(重写父类)
	 */
	reset : function(resetHidden){
		
		var me = this;
		
		//重置拓展字段
		me.extraValues = {};
		
        var fields = me.getForm().getFields().items;
        
        for(var i in fields){
        	
        	var field = fields[i];
        	var fieldType = field.xtype;
        	
        	if(resetHidden == undefined || resetHidden == null){
        		//不清空隐藏域的值
	        	if(fieldType == "ins_hiddenfield"){
	        		
	        		continue;
	        	}
        	}
        	
        	field.reset();
        }
	}
	

});;/**
 * 可操作的表单窗体
 */
Ext.define('Ins.form.OprFormWindow',{
	extend: 'Ins.window.Window',
	alias: 'widget.ins_oprformwindow',
	
	config: {
		fields: [],
		url: null,
		success : null,
		mode : null,
		//默认表单字段宽度
		defaultFormFieldWidth : 230,
		//保存之前的事件
		onBeforeSave : null,
		//点击重置按钮后的事件
		onAfterClickResetButton: null,		
		//表单列数
		columnSize : 2,
		onBeforeShow : null,
		onBeforeHide : null,
		autoMessage : true,
		//此方法在新增或删除成功时执行，如果返回true则执行重置表单，topmessage等操作，返回false则不执行后续操作
		onAfterSubmit: null
	},
	
	modal: false,
	//resizable : false,	
	maximizable: true,
	
	initComponent:function(){
		
		var me = this;
		
		//表单对象
		var form = Ext.create('Ins.form.Form',{
			xtype: 'ins_form',			
			columnSize : me.columnSize,	
			defaultFormFieldWidth: me.defaultFormFieldWidth,
			fields: me.fields			
		});
				
		me.form = form;
		
		Ext.apply(me,{
			views: [form],
			dockedItems: [{
				id : "toolbar_" + me.getId(),
			    xtype: 'toolbar',
			    dock: 'bottom',
			    ui: 'footer',
			    items: ['->',{
					id : "resetBtn_" + me.getId(),
			    	text: '重置',
			    	iconCls : "icon-clear",
			    	handler: function(){			    		
			    		form.reset();
			    		if(me.onAfterClickResetButton!=null){
			    			me.onAfterClickResetButton(form);
			    		}
			    	}
				},{
					text: '保存',
			    	id : "saveBtn_" + me.getId(),
			    	iconCls : "icon-ok",
			    	handler: function(){			    		
			    		if(form.getForm().isValid()){	            		
			    			//如果有事件监听器，则执行
			    			var flag = true;
			    			if(me.onBeforeSave != null && me.onBeforeSave!=undefined){		    				
			    				flag = me.onBeforeSave(me.getForm());
			    			}
			    			if(!flag){
			    				return;
			    			}		    			
			    			me.oprRecord(form);
			    		}
			    	}
				}]
			}],
			listeners : {
				beforehide : function(){
					
					if(me.onBeforeHide != undefined && me.onBeforeHide != null){
						
						return me.onBeforeHide(me.getForm());
					}
					return true;
				},
				close: function(){					
					//关闭窗口前，清除表单内容
					me.getForm().reset();
				}, 
				beforeshow : function(){
					
					if(me.onBeforeShow != undefined && me.onBeforeShow != null){
						
						return me.onBeforeShow(me.getForm());
					}
					return true;
				}
			}
		});		
		
		
		me.callParent(arguments);
		
	},
	
	/**
	 * 以新增模式显示
	 * 
	 * @param cfg
	 */
	showAsAddMode: function(cfg){		
		
		var me = this;
		
		//隐藏重置按钮
		App.getCom("resetBtn_" + me.getId()).setHidden(false);
		
		if(!me.isHidden()){
			TopMessage.warn('请先完成当前操作，或先关闭此窗体再进行其它操作！');
			return false;
		}
		
		//优先使用自定义标题
		me.setTitle(cfg.title != undefined && cfg.title != null ? cfg.title : "添加");
		
		App.getCom("toolbar_" + me.getId()).setVisible(true);
		FormToolkit.setFormFieldsReadonly(me.getForm(), false);
		
		me.mode = "ADD";
		me.url = cfg.url;
		
		me.success = cfg.success;		
		
		me.onBeforeSave = cfg.onBeforeSave;		
		
		me.onBeforeHide = cfg.onBeforeHide;		
		
		me.onBeforeShow = cfg.onBeforeShow;		
			
		me.show();
		
		me.alignToCenter();
	},
	
	/**
	 * 以编辑模式显示
	 * 
	 * @param cfg
	 */
	showAsEditMode : function(cfg){		
		
		var me = this;
		
		//隐藏重置按钮
		App.getCom("resetBtn_" + me.getId()).setHidden(true);
		
		if(!me.isHidden()){
			TopMessage.warn('请先完成当前操作，或先关闭此窗体再进行其它操作！');
			return false;
		}
		
		//优先使用自定义标题
		me.setTitle(cfg.title != undefined && cfg.title != null ? cfg.title : "编辑");
		
		App.getCom("toolbar_" + me.getId()).setVisible(true);
		FormToolkit.setFormFieldsReadonly(me.getForm(), false);
		
		me.mode = "EDIT";
		me.url = cfg.updateUrl;
		
		me.success = cfg.success;		
		
		me.onBeforeSave = cfg.onBeforeSave;		
		
		me.onBeforeHide = cfg.onBeforeHide;		
		
		me.onBeforeShow = cfg.onBeforeShow;		
		
		me.getForm().loadData({
			url: cfg.loadUrl,
			params: cfg.params,
			onSuccess: function(form, data){
				
				me.show();
		
				me.alignToCenter();
				
				if(cfg.onSuccess!=null){
					cfg.onSuccess(form, data)
				}
			}
		});
		
		
	},
	
	/**
	 * 已查看模式打开
	 * @param {} cfg
	 */
	showAsViewMode : function(cfg){
		
		var me = this;
		
		if(!me.isHidden()){
			TopMessage.warn('请先完成当前操作，或先关闭此窗体再进行其它操作！');
			return false;
		}
		
		//优先使用自定义标题
		me.setTitle(cfg.title != undefined && cfg.title != null ? cfg.title : "查看明细");
		
		App.getCom("toolbar_" + me.getId()).setVisible(false);
		 
		me.mode = "VIEW";
		
		FormToolkit.setFormFieldsReadonly(me.getForm(), true);
		
		me.onBeforeHide = cfg.onBeforeHide;		
		
		me.onBeforeShow = cfg.onBeforeShow;		
		
		me.getForm().loadData({
			url: cfg.loadUrl,
			params: cfg.params,
			onSuccess: function(form, data){
				
				me.show();
		
				me.alignToCenter();
				
				if(cfg.onSuccess!=null){
					cfg.onSuccess(form, data)
				}
			}
		});			
		
				
	},
	
	
	/**
	 * 获取表单对象
	 * 
	 * @returns
	 */
	getForm : function(){
		
		var me = this;
		
		return me.form;
	},
	
	/**
	 * 获取表单的字段对象
	 * 
	 * @param name
	 * @returns
	 */
	getField : function(name){
		
		var me = this;
		
		return me.getForm().getField(name);
	},
	
	/**
	 * 设置表单字段值
	 * 
	 * @param name
	 * @param value
	 */
	setValue : function(name, value){
		
		var me = this;
		
		me.getForm().setValue(name, value);
	},
	
	/**
	 * 获取表单字段值
	 * 
	 * @param name
	 */
	getValue : function(name){
		
		var me = this;
		
		me.getForm().getValue(name);
	},
	
	
	/**
	 * 操作记录
	 * 
	 * @param form
	 */
	oprRecord : function(form){
		      
		var me = this;
		
		Message.ask("确定要执行该操作吗？", function(select){
			
			if(select){				
				
				form.submit({
                    url: me.url,
                    waitMsg: '正在执行，请稍后...',
                    success: function(fp, o) {
                    	
                    	var flag = true;
                    	
                    	if(me.onAfterSubmit != null){
                    		flag = me.onAfterSubmit(fp, o);
                    	}
                    	
                    	if(flag){
                    		
                    		me.getForm().reset();
                    	
	                    	try{ 
								me.getForm().setValue('id','');
							} 
							catch (e){ 						
								throw new Error("不存在id字段") 
							} 
							   
							me.hide();
							
							if(me.autoMessage){
								TopMessage.info("操作成功！");
							}
							
							if(me.success != null){							    
								me.success();
							}
                    	}
                       
                    	
                    },
                    
                    failure: function(form, action) {                    	
                    	try{
                    		return App.errorHandler(action.response);		
                    	}catch(exception){
                    		var msg = '操作失败或系统错误';
                    		TopMessage.error(msg);
							return false;
                    	}                    	
                    }
                    
                });
					
				
			}
		});
	}
	
});;/**
 * 普通表格
 */
;Ext.define('Ins.grid.Grid',{
	extend: 'Ext.grid.Panel',
	
	config: {
		url: null,
		//列数组
		fields: [],
		//实际列
		gridColumns: [],
		//主键字段
		pkField : "id",
		//是否自动加载数据
		loadOnShow: true,		
		//隐藏标题
		hideTitle: false,
		//隐藏表头
		hideTableHead: false,
		//是否隐藏分页工具
		hidePagingBar: false,
		//工具栏按钮
		actionButtons: [],
		//按钮排序
		//['新增','删除','查询','|','自定义']
		actionButtonsSort: [],
		//是否显示复选框
		checkable: true,
		//默认列宽
		defaultColumnWidth: 120,
		//列是否自适应撑满布局(如果为true，不会出现滚动条)
		autoColunmWidth: false,
		//列值映射器
		valueMapper: null,
		
		//选择模式：MULTI、SINGLE
		selectMode : "MULTI",		
		//是否只点击复选框才选中, 跨页选择时，此属性为true
		selectCheckOnly: false,
		
		//是否允许导出
		exportToExcel : false,
		
		//每页多少条数据
		pageSize: 30,
		//自定义分页数组(使用这个必须配置pageSize,并且pageSize必须登录其中的某项)		
		customPage : [10,20,30,50,100],
		
		//查询前调用
		onBeforeSearch: null,		
		
		//表格是否可编辑
		editable: false,
		//表格编辑类型row/cell
		editType: 'row',
		
		//禁用所有列的排序
		disabledColumnsSortable : false,
		
		
		//加载完成事件
		onLoadComplete: null,
		//记录单击监听器
		onRowClick : null,
		//记录双击监听器
		onRowDbClick : null,
		//记录右键单击事件
		onContextMenuClick: null,
		
		//复选框选中事件
		onRowSelect: null,
		//复选框取消选中事件
		onRowDeSelect: null,
		//复选框状态变化
		onRowSelectChange: null,
		
		//是否显示行编号
		hideRowNumber: true,	
		//编号title
		rowNumberText: '编号',
		//编号列宽
		rowNumberWidth: 50,
		
		//是否显示操作列
		showActionColumn: true,
		//操作列按钮
		actionColumnMenu: [],
		//行记录菜单单击前
		actionColumnClick : null,
		
		actionColumnLocked: true,
		
		//跨页选择模式
		acrossSelect : false,
		
		//模式，分为服务器端表格（server）与本地表格（local）
		model : "server",
		//本地表格时的数据
		localData: [],		
		
		//查询条件
		condition : {
			//fields : [],			
			//buttonText : "查询",
			
			//下面属性为默认值，程序后面会使用 Ext.applyIf 设置
			//columnSize : 2,
			//defaultFieldWidth : 200
		},	
		
		onConditionShow: null,
		
		//针对子类定义的button
		//private
		_childdActionButtons: [],
		
		//复选框是否显示 参数：function(record,rowIndex)
		showSelCheck: null
		
	},
	
	
    loadMask: true,						//刷新时候显示遮罩层
    columnLines: true,					//单元格分割线    
    invalidateScrollerOnRefresh: false, //刷新时保留滚动条位置
    enableLocking: false,				//启用表头锁定 default: false
    //shadow : false,    
    //border : true,
    disableSelection: false,    		//真正的禁用选择模型 default : false	
    
    resizable: false,
   
    
    
    viewConfig: { 
		stripeRows: true,				//行交替
		enableTextSelection: true		//真正可以选择文本
	},
	
	
	initComponent: function(){
		
		var me = this;	
		
		//定义跨页选择模式对象
    	if(me.acrossSelect){    		
    		Ext.apply(me, {    			
    			acrossSelection : new Ext.util.HashMap()
    		});
    	}
    	
    	//标题
    	Ext.apply(me, {
    		title: me.title == undefined || me.title == null ? '未命名表格' : me.title
    	});
		
				
		//列自适应撑满布局
		Ext.apply(me,{
			forceFit: me.autoColunmWidth
		});		
		
		//如果隐藏分页，每页大小1000
		if(me.hidePagingBar){
			me.pageSize = 1000;
		}
		
		//数据源
		if(me.store==undefined || me.store==null){
			var store = null;
	    	if(me.model === 'server'){
		    	store = new Ins.data.Store({
		    		//model: 'model',
		    		remoteSort : true,  
		            fields: me.fields,  
		            url : me.url,
		            idProperty: me.pkField,
		            pageSize: me.pageSize,
		            autoLoad: me.loadOnShow,            
		        	listeners: {
		        		beforeload: function(obj, operation, eOpts){        			
		           			
		        		},
		        		//数据加载完成
		           		load: function(obj, records, successful, eOpts){	           			
		           			if(me.onLoadComplete != null){
		           				me.onLoadComplete(me.getStore());
		           			}    
		           			//如果开启跨页选择
		           			if(me.acrossSelect){           				
		           				me.getSelectionModel().select(me.acrossSelection.getValues(), true);
		           			}
		           		}           		
		           	}
		        }); 		
	    	}else if(me.model === 'local'){
	    		store = Ext.create('Ins.data.Store', {		    
			   		fields: me.fields,
				    data: me.localData,
				    idProperty: me.pkField,
				    pageSize: me.pageSize,
				    autoLoad: me.loadOnShow            
				});
	    	}       
	       
	    	//绑定数据源表格
	    	Ext.apply(me, {    		
	    		store : store		//数据源    
	    	}); 
		}
    	
    	
    	
    	//隐藏标题/表头	
		Ext.apply(me, {
    		header : !me.hideTitle,				//隐藏标题
    		hideHeaders: me.hideTableHead		//隐藏表头
    	});
    	
    	
    	//该表格是否可编辑
    	if(me.editable){    		
    		var editPlug = null;
			if(me.editType === "row"){				
				editPlug = Ext.create('Ext.grid.plugin.RowEditing', {
        	        clicksToMoveEditor: 1,
        	        autoCancel: false
        	    });
			}else if(me.editType === "cell"){
				editPlug = new Ext.grid.plugin.CellEditing({
	                clicksToEdit: 1
	            });
			}				
    		Ext.apply(me, {
    			plugins: [editPlug]
        	});
    	}
    	
    	
    	//事件
    	var _listeners = {
    		//记录单击
    		itemclick : function(obj, record, item, index, e, eOpts){    
    			//记录当前记录行
    			me.currentRecord = record;
    			if(me.onRowClick != null){
    				me.onRowClick(record, index, e);
    			}
    		},
    		//记录双击
    		itemdblclick: function(obj, record, item, index, e, eOpts ){
    			//记录当前记录行
    			me.currentRecord = record;
    			if(me.onRowDbClick != null){
    				me.onRowDbClick(record, index, e);
    			}
    		},
    		//记录右键单击事件
    		itemcontextmenu: function(obj, record, item, index, e, eOpts ){
    			//记录当前记录行
    			me.currentRecord = record;
    			if(me.onContextMenuClick != null){
    				
    				if(e.preventDefault){
			      		e.preventDefault();     
			      	}else{       
			      		e.returnValue = false;
			     	}
    				
    				me.onContextMenuClick(record, index, e);
    			}
    		},
    		//复选框选中事件
    		select: function(obj, record, index, eOpts ){
    			//记录当前记录行
    			me.currentRecord = record;
    			
    			if(me.onRowSelect != null){
    				me.onRowSelect(record, index);
    			}  
    			//如果开启跨页选择
    			if(me.acrossSelect){
    				if(!me.acrossSelection.containsKey(record.data[me.pkField])){
    					me.acrossSelection.add(record.data[me.pkField], record);
    				}
       			}
       			
       			//如果存在隐藏复选框的方法
       			if(me.showSelCheck!=null){
       				var flag = me.showSelCheck(record, index);         			
	       			if(!flag){       				
	       				me.getSelectionModel().deselect(record);
	       			}
       			}      
    		},
    		//复选框取消选中事件
    		deselect: function(obj, record, index, eOpts ){    
    			//记录当前记录行
    			me.currentRecord = record;
    			
    			if(me.onRowDeSelect != null){
    				me.onRowDeSelect(record, index);
    			}  	
    			//如果开启跨页选择
    			if(me.acrossSelect){
    				if(me.acrossSelection.containsKey(record.data[me.pkField])){
    					me.acrossSelection.removeAtKey(record.data[me.pkField]);
    				}
       			}
    		},
    		//复选框状态变化
    		selectionchange: function(obj, selected, eOpts ){
    			if(me.onRowSelectChange != null){
    				me.onRowSelectChange(selected);
    			}  		
    		}
    		
    	}    	
    	Ext.apply(me, {
    		listeners: _listeners
    	});
    	
    	if(me.acrossSelect){
    		me.selectCheckOnly = true;
    	}
    	
    	//是否显示复选框
    	if(me.checkable){
    		Ext.apply(me, {
    			selModel: {
    				selType: 'checkboxmodel',
    				checkOnly: me.selectCheckOnly,
    				mode: me.selectMode,
    				renderer: function(value,metaData,record,rowIndex,colIndex,store,view){ 
    					metaData.tdStyle = "vertical-align:middle;";
    					var flag = true;
    					if(me.showSelCheck!=null){
    						flag = me.showSelCheck(record, rowIndex);
    					}    					
			            if(!flag){  
			                return  '';  
			            }else{  
			                return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker"> </div>';  
			            }  			              
			        }  
    			}    			
    		});
    	}   
    	
    	
    	//最终的列数组
    	var columnsTmp = [];
    	
    	//行编号
    	if(!me.hideRowNumber){    		
    		columnsTmp.push({
                xtype: 'rownumberer',
                dataIndex: 'ROWNUMBER_COL',//给行号列指定特殊标识，以便识别
                text : me.rowNumberText,                
                width : me.rowNumberWidth,
                locked: true
            });
    	}
    	    	
    	
    	//操作列
    	if(me.actionColumnMenu.length>0){     		
    		me.quickMenu = Ext.create('Ins.menu.Menu',{
    			id: me.getId() +　'_action_menu',
    			items: me.actionColumnMenu
    		})
    		var actionColumnContent = [{
				iconCls: 'icon-list2',
				tooltip: '操作列表，点击以查看更多操作。',				
				handler: function(grid, rowIndex, colIndex, d, evt){					
					
					//当前行
					var thisRecord = grid.getStore().getAt(rowIndex);
					
					//触发行单击事件
					me.fireEvent('itemclick', grid, thisRecord, null, rowIndex, evt, null);
					
					//记录当前记录行
    				me.currentRecord = thisRecord;
					
					var rsFlag = true;
					if(me.actionColumnClick != null){						
						rsFlag = me.actionColumnClick(thisRecord);
					}
					//如果actionColumnClick()返回false，则不弹出menu
					if(!rsFlag){
						return;
					}					
					//定义行记录上下文菜单			    	
					me.quickMenu.showAt(evt.getXY()); 
				}
			}];
			
			if(me.showActionColumn){
				columnsTmp.push({
	    			dataIndex: 'OPRATE_COL',//给操作列指定特殊标识，以便识别
	    			text: "操作",
	    			align: 'center',
	    			menuDisabled: true,
	    			sortable: false,
	    			xtype: 'actioncolumn',
	    			hideable: false,
	    			locked: me.actionColumnLocked,
	    			resizable:false,
	    			width: 50,
	    			items: actionColumnContent,
	    			renderer: function(value,metaData,record,rowIndex,colIndex,store,view){ 
    					metaData.tdStyle = "vertical-align:middle;";
    					return value;             
			        }  
	    	    });
			}			
    	}
    	
    	//列    	
    	for(var i in me.gridColumns){    		
    		var columnObj = Ext.clone(me.gridColumns[i]);    
    		//如果是列分组
    		if(columnObj.columns!=undefined && columnObj.columns!=null && columnObj.columns.length>0){
    			for(var j=0;j<columnObj.columns.length;j++){    				
    				me._setColumn(columnObj.columns[j]);
    			}
    		}
    		//单列
    		else{
    			me._setColumn(columnObj);    			
    		}
    		
    		columnsTmp.push(columnObj);
    	
    	}
    	 	
    	
    	//绑定列
    	Ext.apply(me, {
    		columns : columnsTmp
    	});    	
    	    	
    	
    	//工具栏按钮
    	var actionButtonsTemp = []; 
  		
    	//针对增删改查表格
    	if(me._childdActionButtons.length > 0){
    		for(var i in me._childdActionButtons){
    			actionButtonsTemp.push(me._childdActionButtons[i]);
    		}
    	}
    	
    	//如果有查询条件，则添加
    	if(me.condition.fields != undefined && me.condition.fields != null && me.condition.fields.length > 0){
    		
    		//设置默认值
    		Ext.applyIf(me.condition, {
    			columnSize : 2,
    			defaultFieldWidth : 200
    		});
    		
    		var queryBtnId = me.getId() + '_query_btn' ;
    		    		
    		var conditionForm = Ext.create('Ins.form.Form',{    			
    			xtype: 'ins_form',
    			title: '查询条件（清空可查看全部记录）',
    			columnSize : me.condition.columnSize,
    			//floating : true,
    			textAlign: 'top',
    			textStyle: 'font-weight:bold',
    			defaultFormFieldWidth: me.condition.defaultFieldWidth,
    			fields: me.condition.fields,
    			tools : [
				    {
				    	type : "close",
				    	tooltip : "单击关闭查询条件。",
				    	handler : function(){
				    		//conditionForm.hide();
				    		App.getCom(me.getId() + '_query_btn_menu').hide();
				    	}
				    }
				],
    			actionButtons: [{
					text: '清空',
					iconCls : "icon-clear",
					handler: function() {						
						conditionForm.reset(true);
					}
				},{
					text: '查询',
					iconCls : "icon-ok",
					handler: function() {
						
						//如果有相应对调方法，则执行
						var rsFlag = true;
						if(me.onBeforeSearch != undefined && me.onBeforeSearch != null){
							
							rsFlag = me.onBeforeSearch(conditionForm);
						}
						if(rsFlag == false){							
							return;
						}
						
						//计算查询条件个数
						var availableCondCount = 0;
						
						for(var i in me.condition.fields){								
							var fieldObj = me.condition.fields[i];
							var xtype = fieldObj.xtype;
							//排除隐藏域
							if(xtype == "ins_hiddenfield"){
								continue;
							}else{
								//此处不支持分组
								var val = conditionForm.getField(fieldObj.name).getValue();								
								if(val != null && val != ""){	
									availableCondCount ++;
								}
							}
						}
						
						//更新查询按钮文本文字
						var btnText = me.condition.buttonText != undefined && me.condition.buttonText != null ? me.condition.buttonText : "查询";
						if(availableCondCount > 0){
							btnText += "&nbsp;<span style='color:blue;font-weight:bold;'>&nbsp;(+" + availableCondCount + ")</span>";
						}
						//更新查询按钮字
						Ext.getCmp(queryBtnId).setText(btnText);
						//隐藏窗口
						App.getCom(me.getId() + '_query_btn_menu').hide();
						//conditionForm.hide();
						//查询
						me.search(conditionForm.getForm().getValues());
					}
				}]
    			
    		});   		
    		
    		
    		Ext.apply(me, {
    			conditionForm: conditionForm    			
    		});
    		    		    		    		
    		actionButtonsTemp.push({
    			id: queryBtnId,
            	iconCls : "icon-search",
            	text : me.condition.buttonText != undefined && me.condition.buttonText != null ? me.condition.buttonText : "查询",
            	/*handler : function(obj,event){              		
					conditionForm.showAt(obj.getX(),obj.getY()+20);              		
                }*/
            	menu: new Ins.menu.Menu({
            		id: me.getId() + '_query_btn_menu',
			        items: [
			            conditionForm
			        ]
    			}),
    			listeners: {
    				menushow: function(){
    					if(me.onConditionShow != null){
    						me.onConditionShow(me.getConditionForm());
    					}
    				}
    			}
            });
            
            //actionButtonsTemp.push('-');
            
    	}
    	
    	
    	//如果需要导出则添加导出按钮
    	if(me.exportToExcel){
    		actionButtonsTemp.push({
    			id: me.getId() + '_export_btn',
            	iconCls : "icon-export",
            	text : "导出",
    			handler: function(){    				
 					var grid = me;
					var columns = grid.columns;
					var gridColumns = grid.gridColumns;
					var json = Ext.JSON.encode(gridColumns);
					var columnsJson = "[";
					for (var i = 0; i < columns.length; i++) {
						if (columns[i].dataIndex != 'OPRATE_COL') {
				          	columnsJson += '{"text":"'+columns[i].text+'","dataIndex":"'+columns[i].dataIndex+'","width":"'+columns[i].cellWidth+'"}';
							if (columns.length-1!=i) {
				           		columnsJson += ',';
				            }
						}
			        }
					columnsJson += "]";
					var gridUrl = grid.url;
					//收集参数
					var data = new Object();
					//查询框参数
					if(me.condition.fields != undefined && me.condition.fields != null && me.condition.fields.length > 0){
						var params = me.getConditionForm().getForm().getValues();
						 //必须在 params 有值时才赋值，否则会覆盖之前的查询条件的值
				    	if(params != undefined && params != null){
				    		data = Ext.clone(params);  
				    	}
					}
		    		if(me.loadParams!=undefined && me.loadParams!=null){
		    			//将查询条件合并到loadParams中 
		    			Ext.applyIf(data, me.loadParams);
		    		}
					//导出excel必须数据
					data.gridColumns = json;
					data.columnsJson = columnsJson;
					data.gridUrl = gridUrl;
					data.total =grid.getStore().totalCount
					//发送请求获取excel名称
					Messager.send({
					 	url: CTX_PATH + '/insexport/excel',
					 	data: data,
					 	confirm: '确认要导出查询到的所有数据吗？',
                        maskCmp: grid,
                        maskText: "正在导出数据，请稍后...",
					 	onSuccess: function(result){
					 		if (result.success) {
						 		window.location.href= result.fileInfo.baseUrl;
						 		TopMessage.info('导出成功');
					 		}else{
					 			TopMessage.info('导出失败');
					 		}
					 	}
					});    				
    			}
            });           
            
    	}
    	
    	
    	
    	//组成最终工具栏
    	for(var i=0;i<me.actionButtons.length;i++){    		
    		actionButtonsTemp.push(me.actionButtons[i]);
    		//actionButtonsTemp.push('-');
    	} 
    	if(actionButtonsTemp.length>0 && actionButtonsTemp[actionButtonsTemp.length-1] == '-'){
    		actionButtonsTemp.pop();
    	}
    	//工具栏按钮排序    	
    	if(me.actionButtonsSort.length > 0){    	
    		var _actionButtonsTemp = [];
    		for(var i in me.actionButtonsSort){    			
    			if(me.actionButtonsSort[i] == '|'){
					_actionButtonsTemp.push('-');
					continue;
				}   
				for(var j in actionButtonsTemp){
    				var btnText = actionButtonsTemp[j].text;    				
    				if(btnText==undefined || btnText==null || btnText==''){
    					btnText = actionButtonsTemp[j].html;
    				}
    				if(btnText == me.actionButtonsSort[i]){
    					_actionButtonsTemp.push(actionButtonsTemp[j]);
    					delete actionButtonsTemp[j];
    				}				
    			}    			
    		}  
    		for(var i in actionButtonsTemp){
    			if(actionButtonsTemp[i]=='-' || actionButtonsTemp[i]=='|'){
    				continue;
    			}
    			_actionButtonsTemp.push(actionButtonsTemp[i]);
    		}
    		actionButtonsTemp = Ext.clone(_actionButtonsTemp);
    	}
    	
    	me.actionButtons = actionButtonsTemp;		
    	
    	
    	var toolbar = [];
    	//工具栏
    	if(me.actionButtons.length > 0){
    		
    		var actionToolbar = {
    			id: me.getId() + '_toolbar',
    			xtype: 'toolbar',
    			items: me.actionButtons,
			    dock: 'top'
			    //ui: 'footer'
    		};
    		
    		Ext.apply(me, {
    			actionToolbar: actionToolbar
    		})
    		
			toolbar.push(actionToolbar);		
    	}
    	
    	  	
    	//分页
    	if(!me.hidePagingBar){    		
    		var items = [];
    		var flag = false;
    		var l = [];
    		for(var i in me.customPage){
				if(me.pageSize == me.customPage[i]){
					flag = true;
				}
				var item = {
					text : me.customPage[i],
					value : me.customPage[i]
				};
				l.push(item);
				if(flag){									
					items = [ "-", "每页 ", {
						xtype : "ins_list",
						width : 58,
						list :l ,
						value : me.pageSize,
						onFieldChange : function(obj, newValue, oldValue, eOpts ){								
							//将页数重置到第一页
							me.getStore().pageSize = newValue;
							//me.getStore().currentPage = 1;
							me.getStore().load({
								page : 1,
								start : 0
							});
						}
					}, "条" ]
				}
			}
			if(!flag){
				if(window.console){
					console.error('pageSize 必须等于 customPage中的配置项！');
				}
				return;
			}			
			toolbar.push({
					xtype: 'pagingtoolbar',
					dock: 'bottom',
	                xtype: 'pagingtoolbar',
	                displayInfo: true,
	                store: me.store,
	                //displayMsg: '当前第 {0} - {1} 条，共计 {2} 条', 
	                displayMsg: '共 {2} 条',
	                emptyMsg: '无数据',
	        		beforePageText:'第',
	        		afterPageText:'页，共 {0} 页', 
	        		items : items
			});
    	}    
    	
		Ext.apply(me, {				
			dockedItems : toolbar
		});
		
		//增加销毁时事件,清空资源
		me.addListener("beforedestroy", function(){		
			//销毁查询表单
			if(me.conditionForm!=undefined || me.conditionForm!=null){
				Ext.destroy(me.conditionForm);
			}
			//销毁操作列的menu
			if(me.quickMenu!=undefined || me.quickMenu!=null){
				Ext.destroy(me.quickMenu);
			}
		});		
    	
		me.callParent(arguments);
	},
	
	/**
	 * 私有方法，设置column
	 * @param {} obj
	 */
	_setColumn: function(columnObj){	
		var me = this;
		//默认列均可以排序
		if(columnObj.dataIndex != undefined && columnObj.sortable == undefined){    			
			Ext.apply(columnObj, {    				
				sortable: !me.disabledColumnsSortable
			});
		}  
		//列宽
		if(columnObj.width == undefined && !me.autoColunmWidth){        			
			Ext.apply(columnObj, {
    			width : me.defaultColumnWidth
    		});
		}
		//列映射
		for(var columnName in me.valueMapper){
			var rendererFunc = me.valueMapper[columnName];
			if(columnObj.dataIndex == columnName){
				Ext.apply(columnObj, {					
					renderer : me.valueMapper[columnName]
	    		});
	    		break;
			}
			
		}
		
		//如果valumapper中不存在此列，则进行html编码
		if(columnObj.renderer == undefined || columnObj.renderer == null){			
			if(columnObj.xtype != 'checkcolumn'){
				Ext.apply(columnObj, {					
					renderer : function(value, v){		
						v.tdStyle = "vertical-align:middle;";
				        return Ext.util.Format.htmlEncode(value);
				    }
	    		});
			}
		}
		
	},
	
	/**
     * 重新加载该表格数据
     * 
     * @param params
     */
    load : function(params){
    	
    	
    	var me = this;
    	
    	//首先启用表格
    	me.setDisabled(false);
    	
    	//必须在 params 有值时才赋值，否则会覆盖之前的查询条件的值
    	if(params != undefined && params != null){
    		
    		//将条件赋给对loadParams属性，便于以后使用
    		me.loadParams = params;
    		
    		me.getStore().proxy.extraParams = me.loadParams;
    	}
    	
    	me.getStore().reload();
    },
    
    /**
     * 搜索记录
     * 
     * @param params
     */
    search : function(params){ 
    	
    	var me = this;
    	
    	//首先启用表格
    	me.setDisabled(false);
    	
    	//必须在 params 有值时才赋值，否则会覆盖之前的查询条件的值
    	if(params != undefined && params != null){
    		
    		var searchParams = Ext.clone(params);  
    		
    		if(me.loadParams!=undefined && me.loadParams!=null){
    			//将查询条件合并到loadParams中    			
    			Ext.applyIf(searchParams, me.loadParams);
    		}   
    		
    		me.getStore().proxy.extraParams = searchParams;
    	}
    	
    	//重置到第一页后搜索
    	me.getStore().currentPage=1;
    	me.getStore().reload({
    		page : 1,
    		start : 0
    	});
    },
    
    /**
     * 根据查询字段进行查询
     */
    searchByCondition: function(){
    	var me = this;
    	me.search(me.getConditionForm().getForm().getValues());
    },
    
    
    /**
     * 获取当前所选行记录
     * 
     * @returns
     */
    getCurrentRecord : function(){
    	
    	var me = this;
    	
    	return me.currentRecord;
    },
    
    
    /**
     * 获取选择的记录
     */
    getSelectedView : function(){
    	
    	var me = this;
    	
    	var selection = null;
    	//是否开启跨页选择
    	if(me.acrossSelect){
    		selection = me.acrossSelection.getValues();
    	}else{
    		selection = me.getSelectionModel().getSelection();
    	}    	
    	
		//拼接 id 序列
		var ids = me.getIdSer(selection, me.pkField);		
		
		return {
			
			count : selection.length,
			idSer : ids,
			idArr : Ext.JSON.decode(ids),
			records : selection
		};
    },
    
    getIdSer: function(selection, field){
    	
    	//拼接 id 序列
		var ids = "[";
		for(var i in selection){
			
			var id = selection[i].data[field];
									
			ids += "\"" + id + "\",";
		}
		ids = ids.substring(0, ids.length - 1);
		ids += "]";
		
		//如果没有选择任何记录，则 ids 会为 "]"，因此这次需要特别处理
		if(ids == "]"){
			
			ids = "[]";
		}
		
		return ids;
		
    },
    
    /**
	 * 针对于单页选择模式下的选择记录方法
	 * 
	 * @param ids id 数组
	 */
	select : function(ids){
		
		var me = this;
		
		var records = [];
		for(var i in ids){
			
			var id = ids[i];
			
			var record = me.getStore().findRecordById(me.pkField, id);
			
			if(record != null){
				records.push(record);
			}
		}
		
		me.getSelectionModel().select(records);
	},
	
	
	/**
	 * 针对于跨页选择模式下的选择记录方法
	 * 
	 * @param records
	 */
	selectAcrossSelect : function(records){		
		var me = this;		
		for(var i in records){			
			var record = records[i];			
			me.acrossSelection.add(record.data[me.pkField], record);
		}
		me.getSelectionModel().select(me.acrossSelection.getValues(), true);
	},
    
	
	/**
	 * 取消选中的记录
	 */
	clearSelect: function(){
		var me = this;
		
		//是否开启跨页选择
    	if(me.acrossSelect){
    		me.acrossSelection.clear();
    	}
		
		//由于 extjs 的特性，如果本页没有选择任何记录，则触发  deselectAll 不会更新界面，因此这里先手动选择一条
    	if(me.getStore().getTotle() > 0){    		
    		me.getSelectionModel().selectAll();
    	}    	
    	me.getSelectionModel().deselectAll();
	},
	
	/**
	 * 全选本页记录
	 */
	selectCurrentPageRecords : function(){
		
		var me = this;
		
		me.getSelectionModel().selectAll();
	},
	
	/**
	 * 获取查询条件的表单对象
	 * 
	 * @returns
	 */
	getConditionForm : function(){
		
		var me = this;
		
		return me.conditionForm;
	},	
	
	/**
	 * 设置工具栏是否禁用
	 * 
	 * @param status
	 */
	setToolbarDisabled : function(status){
		
		var me = this;
		
		me.actionToolbar.setDisabled(status);
	}
	
	
});;/**
 * 增删改查表格
 * 加星号的属性和父类同名，但用法不同
 */
Ext.define('Ins.grid.CrudGrid',{
	extend: 'Ins.grid.Grid',
	
	config: {		
		urlConfig : {
			/*
			loadAll: CTX_PATH + '/link/loadAll',
			add: CTX_PATH + '/link/add',
			load:  CTX_PATH + '/link/load',
			update:  CTX_PATH + '/link/update',
			remove:  CTX_PATH + '/link/remove'
			*/
		},
		
		/*
		 *
		dataSet:[
			{	name : 'id',
	    		text : '主键'
			},{
				name: 'name',
				text: '名字'
			}
		]	
		 * */
		dataSet : [],	
		
		/*formFields: [
			{
				forData: 'id',
				xtype: 'ins_hiddenfield'
				
			},{
				forData: 'name',				
				allowBlank: false,
				colspan: 2
			}]
		*/
		formFields : [],
		
		//权限
		permission : {
			add : true,
			update : true,
			remove : true,
			view: true
		},
		
		//实际列
		//******
		gridColumns: [],
		
		//如果查询条件的配置是字符串，则在 formfields 中查找该字段的详细定义，
		//如果查询条件的配置是对象，则优先是用该配置，而不去 formfields 中查找
		//******
		//也可以按照父类grid中的condition使用方法使用
		condition : [],		
		//查询表单列数
		conditionColumnSize : 2,
		//查询表单字段宽度
		conditionWidth: 200,
		
		//操作表单列数
		columnSize : 2,
		
		//默认表单字段宽度
		defaultFormFieldWidth : 230,
		//默认查询条件字段宽度
		defaultCondFieldWidth : 200,
		
		//查找前被回调的方法
		onBeforeSearch : null,
		//在新增表单展示之前触发的函数
		onBeforeShowAddForm : null,
		//在编辑表单展示之前触发的函数
		onBeforeShowEditForm : null,
		//在查看明细之前触发的函数
		onBeforeShowViewForm: null,
		
		//在新增表单展示之后
		onAfterShowAddForm: null,
		//在编辑表单展示之后
		onAfterShowEditForm: null,
		//在查看明细后显示
		onAfterShowViewForm: null,
		
		//在删除操作成功之后
		onAfterDelete: null,
		
		//在执行删除之前执行的函数
		onBeforeDelete : null,
		//添加保存之前执行的函数
		onBeforeSaveAdd : null,
		//编辑保存之前执行的函数
		onBeforeSaveEdit : null,
		
		//增加或编辑表单执行成功时调用，如果返回true则执行重置表单，topmessage等操作，返回false则不执行后续操作
		onAfterFormSubmit: null,
		
		//操作表单高度
		formWindowHeight : null,	
		//操作表单宽度
		formWindowWidth: 510,
		//操作表单最大高度
		formWindowMaxHeight: null,
		//操作表单最大宽度
		formWindowMaxWidth: null,
		//操作表单窗体是否遮罩
		formWindowModal: false,
		
		//自定义标签
		labelConfig : {
			add : {
				text : null,
				iconCls : null
			},
			update : {
				text : null,
				iconCls : null
			},
			view : {
				text : null,
				iconCls : null
			},
			remove : {
				text : null,
				iconCls : null
			},
			query : {
				text : null,
				iconCls : null
			}
		}
	},
	
	
	initComponent: function(){
		
		var me = this;
		
		
		//构造 store fields 数组
		var _fields = [];
		for(var i in me.dataSet){			
			var record = me.dataSet[i];
			_fields.push(record.name);
		}
		Ext.apply(me, {
			fields : _fields
		});
		
		//构造列
		var _gridColumns = [];
		for(var i in me.gridColumns){
			var columnObj = Ext.clone(me.gridColumns[i]);   
			var _columnObj = null;
			//有分组
			if(columnObj.columns!=undefined && columnObj.columns!=null && columnObj.columns.length>0){
				_columnObj = Ext.clone(columnObj);
				_columnObj.columns = [];
				for(var j=0;j<columnObj.columns.length;j++){					
					_columnObj.columns.push(me.getColumnDefByName(columnObj.columns[j]));    				
    			}
			}
			//无分组
			else{
				_columnObj = me.getColumnDefByName(columnObj);
			}
			_gridColumns.push(_columnObj);
			
		}
		Ext.apply(me,{
			gridColumns: _gridColumns		
		});		
		
		
		//生成表单字段数组
		var _formFields = [];	
		for(var i in me.formFields){			
			var item = me.formFields[i];
			var field = null;
			//不分组时
			if((item.forData!=undefined && item.forData!=null) || (item.name!=undefined && item.name!=null)){
				field = me.getFieldDefByName(Ext.clone(item));				
				//设置默认表单字段宽度（默认宽度只在没有指定宽度时有效）
				if(field != null && field.width == undefined && field.xtype!='component' && field.xtype != 'container' && field.xtype != 'panel' && field.xtype != 'ins_panel' && field.xtype != 'ins_fieldcontainer'){							
					Ext.apply(field, {
						width : me.defaultFormFieldWidth
					});
				}				
			}
			//分组时
			else if(item.items!=undefined && item.items!=null){
				field = Ext.clone(item);
				field.items = [];
				for(var j in item.items){
					if(item.items[j] != null && item.items[j].width == undefined && item.items[j].xtype!='component' && item.items[j].xtype != 'container' && item.items[j].xtype != 'panel' && item.items[j].xtype != 'ins_panel' && item.items[j].xtype != 'ins_fieldcontainer'){							
						Ext.apply(item.items[j], {
							width : me.defaultFormFieldWidth
						});
					}					
					field.items.push(me.getFieldDefByName(Ext.clone(item.items[j])));
				}
			}
			
			_formFields.push(field);
		}
				
		//操作表单配置
		var formConfig = {	
			fields : _formFields,	
			modal: me.formWindowModal,
			defaultFormFieldWidth : me.defaultFormFieldWidth,
			columnSize : me.columnSize,
			onBeforeHide: function(obj){
				obj.getForm().reset();
			}
		};
		
		if(me.onAfterFormSubmit != null){
			formConfig.onAfterSubmit = me.onAfterFormSubmit;
		}
		
		if(me.formWindowHeight != null){
			formConfig.height = me.formWindowHeight;
		}		
		if(me.formWindowWidth != null){
			formConfig.width = me.formWindowWidth;
		}
		if(me.formWindowMaxHeight != null){
			formConfig.maxHeight = me.formWindowMaxHeight;
		}		
		if(me.formWindowMaxWidth != null){
			formConfig.maxWidth = me.formWindowMaxWidth;
		}	
		
		var form = Ext.create("Ins.form.OprFormWindow", formConfig);	
		
		//覆盖表格基础属性
		Ext.apply(me, {
			form : form,//将 form 也注册到组件中，以便其他地方调用			
			url : me.urlConfig.loadAll
		});	
				
		//操作按钮
		//只有具备该权限才会添加相关按钮
		
		//是否允许新增
		if(me.urlConfig.add!=undefined &&  me.urlConfig.add!=null && me.permission.add){
			me._childdActionButtons.push({
				id : me.getId() + "_add_btn",
				text: me.labelConfig.add.text != null ? me.labelConfig.add.text : '添加',
				iconCls: me.labelConfig.add.iconCls != null ? me.labelConfig.add.iconCls : 'icon-add',
				handler: function(btn){
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowAddForm != null){						
						flag = me.onBeforeShowAddForm(me.getForm());
					}
					if(!flag){
						return;
					}
					form.showAsAddMode({
						url: me.urlConfig.add,
						onBeforeSave: me.onBeforeSaveAdd,
						success: function(data){
							me.load();
						}
					});
					
					//如果注册有事件监听器，则注册
					if(me.onAfterShowAddForm != null){
						me.onAfterShowAddForm(me.getForm());
					}	
					
				}
			});
		}
		
		//是否允许删除
		if(me.urlConfig.remove!=undefined &&  me.urlConfig.remove!=null && me.permission.remove){			
			me._childdActionButtons.push({
				id : me.getId() + "_delete_btn",
		        text: me.labelConfig.remove.text != null ? me.labelConfig.remove.text : '删除',
		        iconCls: me.labelConfig.remove.iconCls != null ? me.labelConfig.remove.iconCls : 'icon-delete',
				handler: function(btn){
					
					var selection = me.getSelectedView();					
					if(selection.count == 0){						
						Message.msg('请至少选择一条需要删除记录！', 'WARN');
						return;
					}
					
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeDelete != undefined && me.onBeforeDelete != null){
						
						flag = me.onBeforeDelete(me.getSelectedView());
					}
					if(!flag){
						return;
					}
					
					
					Messager.send({
					  confirm: "确认要删除选定的这 <span style='font-size:25px;color:red'>" + selection.count + "</span> 条记录吗？",
					  maskCmp: me,
					  url: me.urlConfig.remove,
					  data: {
						  SELECT_ITEMS : selection.idSer
					  },
					  onSuccess:function(data){							  	 
						  me.load();
						  
						  if(me.onAfterDelete!=null){
						  	  me.onAfterDelete();
						  }
						  
						  TopMessage.info("操作成功！");
					  }
					});
				}
		    });
		}
		
		//用户自定义actionmenu的长度
		var actionColumnMenuStartLength = me.actionColumnMenu.length;
		//加横线
		if(((me.permission.update && me.urlConfig.update!=undefined &&  me.urlConfig.update!=null && me.urlConfig.load!=undefined &&  me.urlConfig.load!=null) 
				|| (me.permission.view && me.urlConfig.load!=undefined && me.urlConfig.load!=null))
			&& actionColumnMenuStartLength > 0
			){
			me.actionColumnMenu.unshift('-');	
		}		
		
		//是否允许查看明细
		if(me.urlConfig.load!=undefined && me.urlConfig.load!=null && me.permission.view){			
			me.actionColumnMenu.unshift({
				id : me.getId() + "_view_btn",
				iconCls: me.labelConfig.view.iconCls != null ? me.labelConfig.view.iconCls : 'icon-view',
				text: me.labelConfig.view.text != null ? me.labelConfig.view.text : '查看明细',
				handler: function() {
					me.viewCurrentDataForm();
				}	
			});
		}
		
		//是否允许编辑
		if(me.urlConfig.update!=undefined &&  me.urlConfig.update!=null 
			&& me.urlConfig.load!=undefined &&  me.urlConfig.load!=null && me.permission.update){			
			me.actionColumnMenu.unshift({
				id : me.getId() + "_edit_btn",
				iconCls: me.labelConfig.update.iconCls != null ? me.labelConfig.update.iconCls : 'icon-edit',
				text: me.labelConfig.update.text != null ? me.labelConfig.update.text : '编辑记录',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowEditForm != undefined && me.onBeforeShowEditForm != null){						
						flag = me.onBeforeShowEditForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};
					params[me.pkField] = me.getCurrentRecord().data[me.pkField];
					
					form.showAsEditMode({
						//告诉后台此操作不记录日志
						loadUrl: me.urlConfig.load + '?nolog=y',
						updateUrl: me.urlConfig.update,
						onBeforeSave: me.onBeforeSaveEdit,
						params: params,
						success: function(data){
							me.load();
						},
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowEditForm != null){
								me.onAfterShowEditForm(form, data);
							}	
						}
					});
					
					
				}	
			});
		}				
		
		//查询条件
		var _condition = {};
		if(me.condition.length > 0){
			_condition.columnSize = me.conditionColumnSize;
			_condition.defaultFieldWidth = me.conditionWidth;
			_condition.onBeforeSearch = me.onBeforeSearch;
			var _condition_fields = [];
			for(var i in me.condition){
				var item = me.condition[i];
				var condFieldObj = null;				
				if(typeof item == "string"){					
					condFieldObj = me.getConditionField(item);
				}else if(typeof item == "object"){
					condFieldObj = item;
				}
				_condition_fields.push(condFieldObj);	
				_condition.fields = _condition_fields;
			}
			Ext.apply(me.condition, _condition);
		}
		
		
		//增加销毁时事件,清空资源
		me.addListener("beforedestroy", function(){
			//销毁表单
			if(me.form!=undefined || me.form!=null){
				Ext.destroy(me.form);
			}
		});		
						
		me.callParent(arguments);
		
	},
	
	
	/**
	 * 根据字段名构造字段对象
	 * 
	 * @param fieldName
	 * @returns {Object}
	 */
	getFieldDefByName : function(fieldOld){	
		var me = this;	
		
		
		//遍历数据集并定位到该字段对应的配置		
		for(var i in me.dataSet){			
			var record = me.dataSet[i];	
			if(record.name == fieldOld.forData){				
				//更新属性
				Ext.apply(fieldOld, {
					fieldLabel: record.text,
					text : record.text,
					name : record.name
				});				
				return fieldOld;
			}			
		}	
		
		if(fieldOld.name!=null && fieldOld.name!=undefined){
			//更新属性
			Ext.apply(fieldOld, {
				fieldLabel: fieldOld.text,
				text : fieldOld.text					
			});				
			return fieldOld;
		}		
		if(window.console){
			console.error('没有发现 dataSet 中配置的该表单字段。');
		}
	},
	
	/**
	 * 根据列名构造列对象
	 * 
	 * @param column
	 * @returns {Object}
	 */
	getColumnDefByName : function(columnObj){
		
		var me = this;		
		var _columnObj = null;			
		
		if(typeof columnObj == 'string'){
			_columnObj = {
				forData: columnObj
			};	
		}else if(typeof columnObj == 'object'){
			_columnObj = columnObj;
		}	
		for(var i in me.dataSet){				
			var record = me.dataSet[i];			
			if(record.name == _columnObj.forData){					
				Ext.apply(_columnObj, {					
					text : record.text,
					dataIndex : record.name
				});					
				break;
			}
		}			
		return _columnObj;
	},
	
	/**
	 * 获得查询条件字段
	 * 
	 * @param conditionName
	 */
	getConditionField : function(conditionName){
		
		var me = this;		
		//得到对应的 form field 对象
		var formField = null;
		var stop = false;
		for(var i in me.formFields){
			//有分组时
			if(me.formFields[i].items != undefined){				
				for(var j in me.formFields[i].items){
					if(me.formFields[i].items[j].forData == conditionName){				
						formField = Ext.clone(me.formFields[i].items[j]);
						//获取的字段不允许合并单元格
						formField.colspan = 1;
						//获取的字段宽度改为默认
						formField.width = me.conditionWidth;
						stop = true;
						break;
					}
				}
			}
			//未分组
			else{
				var item = me.formFields[i];
				if(item.forData == conditionName){				
					formField = Ext.clone(item);
					//获取的字段不允许合并单元格
					formField.colspan = 1;
					//获取的字段宽度改为默认
					formField.width = me.conditionWidth;
					stop = true;
				}
			}
			if(stop){
				break;
			}
		}
		
		//查询条件字段均可为空
		Ext.apply(formField, {
			name: conditionName,
			allowBlank : true,
			afterLabelTextTpl : "",
			suffixText : null			
		});
				
		return me.getFieldDefByName(formField);
	},
	
	
	
	/**
	 * 获取表单对象
	 * 
	 * @returns
	 */
	getForm : function(){
		
		var me = this;
		
		return me.form.getForm();
	},
	
	/**直接打开选中的的记录*/
	viewCurrentDataForm : function(){
		var me = this;
		//如果注册有事件监听器，则注册
		var flag = true;
		if(me.onBeforeShowViewForm != undefined && me.onBeforeShowViewForm != null){						
			flag = me.onBeforeShowViewForm(me.getForm());
		}
		if(!flag){
			return;
		}
		
		//构造查询参数
		var params = {};
		params[me.pkField] = me.getCurrentRecord().data[me.pkField];
		
		me.form.showAsViewMode({
			loadUrl: me.urlConfig.load,
			params: params,
			onSuccess: function(form, data){
				//如果注册有事件监听器，则注册
				if(me.onAfterShowViewForm != null){
					me.onAfterShowViewForm(form, data);
				}	
			}
		});
		
		
	
	}
	
	
});/**
 * 选择器表格
 */
Ext.define('Ins.grid.ChooserGrid',{
	extend: 'Ins.grid.Grid',
	alias: 'widget.ins_choosergrid',
	
	config: {			
		//确认选择
		onChooserSelect: null,
		//选择前
		onBeforeChooserSelect: null,
		//取消按钮
		onChooserCancel: null,
		//是否弹确认框
		isAskWindow: true
		
	},
	
	//选择模式：MULTI、SINGLE
	selectMode : "MULTI",	
	
	initComponent: function(){
			
		var me = this;		
				
		me._childdActionButtons = [{
            text:'确定选择',
            iconCls:'icon-ok',
			handler: function(btn){
				
				var selection = me.getSelectedView();
				if(selection.count == 0){
					TopMessage.warn("请至少选择一条记录");	
					return;
				}	
				
				var rsFlag = true;
				if(me.onBeforeChooserSelect != undefined && me.onBeforeChooserSelect != null){					
					rsFlag = me.onBeforeChooserSelect();
				}
				if(!rsFlag){
					return;
				}
				
				
				if(me.isAskWindow){
					//询问是否确定选择
					Message.ask("确定要添加所选的 <span style='font-size:25px;color:red'>" + selection.count + "</span> 条记录吗？", function(select){
						if(select){				
							if(me.onChooserSelect != null){
								me.onChooserSelect(selection);
							}
						}
					});
				}else{
					if(me.onChooserSelect != null){
						me.onChooserSelect(selection);
					}
				}				
			}
        },{
            text:'取消',
            iconCls:'icon-delete',
			handler: function(btn){
				if(me.onChooserCancel != null){
					me.onChooserCancel();
				}
			}
        }]
		
        if(me.selectMode == 'MULTI'){
        	me._childdActionButtons.push("-",{
		    	iconCls : "icon-clear",
		    	text : "清空选择",
		    	handler : function(){	    		
		    		me.clearSelect();
		    	}
		    });
        }else if(me.selectMode == 'SINGLE'){
        	//单选时点击记录直接执行onChooserSelect事件
        	me.onRowClick = function(record){
        		var rsFlag = true;
				if(me.onBeforeChooserSelect != undefined && me.onBeforeChooserSelect != null){					
					rsFlag = me.onBeforeChooserSelect();
				}
				if(!rsFlag){
					return;
				}
				//这句话有问题
        		//me.select(record.data[me.pkField]);
        		var selection = me.getSelectedView();
        		if(me.onChooserSelect != null){
					me.onChooserSelect(selection);
				}
        	}			
        }
        
	    Ext.apply(me, {
	    	hideTitle: true,
	    	hideRowNumber: true,
	    	checkable : me.selectMode == "MULTI",
	    	acrossSelect : me.selectMode == "MULTI"
	    });
		
		me.callParent(arguments);
	}
});;/**
 * 表格选择器窗口
 */
Ext.define('Ins.chooser.ChooserGirdWindow',{
	extend: 'Ins.window.Window',
	alias: 'widget.ins_choosergridwindow',
	
	config: {	
		
		//确认选择事件
		onSelect: null,
		//选择前
		onBeforeSelect: null,
		
		beforeShowClear: true,
		//以下属性对应普通表格的属性		
		//选择模式：MULTI、SINGLE
		selectMode : "MULTI",	
		url : null,
		fields: null,	
		pkField : "id",
		hidePagingBar: false,
		pageSize: 20,
		gridColumns: null,		
		loadOnShow: true,
		actionButtons: [],
		actionButtonsSort: [],
		valueMapper: null,
		condition:{
		}
	},
	
	width: 595,
	height: 380,
	//closeAction: 'destroy',
	title: '未命名选择器',	
	modal: true,
	
	initComponent: function(){
		
		var me = this;
			
		var grid = Ext.create('Ins.grid.ChooserGrid',{
			url: me.url,
			fields: me.fields,
			pkField: me.pkField,
			pageSize: me.pageSize,
			gridColumns: me.gridColumns,
			loadOnShow: me.loadOnShow,
			actionButtons: me.actionButtons,
			actionButtonsSort: me.actionButtonsSort,
			condition: me.condition,
			hidePagingBar: me.hidePagingBar,
			selectMode: me.selectMode,
			valueMapper: me.valueMapper,
			onChooserCancel: function(){
				me.hide();
			},
			onBeforeChooserSelect: me.onBeforeSelect,
			onChooserSelect: function(selection){
				if(me.onSelect != null){					
					me.onSelect(selection);									
				}
				me.hide();
			}
			
		});
		
		Ext.apply(me, {
			grid: grid,			
			views: [grid]			
		});	
		
		me.onBeforeShow = function(){
			if(me.beforeShowClear){
				me.grid.clearSelect();
			}			
			me.grid.load();
		}
		
		me.actionButtons = [];
		
		me.callParent(arguments);
	},
	
	/**
	 * 获得表格对象
	 * @return {}
	 */
	getGrid: function(){
		var me = this;
		return me.grid;
	}
	
	
	
});;/**
 * 表格选择器字段
 */
;Ext.define('Ins.chooser.ChooserGirdField',{
	extend:'Ext.form.field.Picker',
	alias : ["widget.ins_choosergridfield"],
	
	config : FormToolkit.config,	
	
	initComponent : function(){
        
    	var me = this;
    	
    	FormToolkit.init(this);
    	
        me.callParent();
    },
    
    editable: false,
    
    constructor: function(config){		
		var me = this;		
		Ext.apply(me, {
			//确认选择事件
			onSelect: null,
			//选择前
			onBeforeSelect: null,
			
			chooserWidth: me.chooserWidth==undefined||me.chooserWidth==null ? 595 : me.chooserWidth,
			chooserHeight: me.chooserHeight==undefined||me.chooserHeight==null ? 330 : me.chooserHeight,
			
			//以下属性对应普通表格的属性		
			//选择模式：MULTI、SINGLE
			selectMode : (me.selectMode == undefined || me.selectMode == null) ? "SINGLE" : "MULTI",	
			url : null,
			fields: null,	
			pkField : "id",
			hidePagingBar: me.isHidePagingBar==undefined||me.isHidePagingBar==null ? false : me.isHidePagingBar,
			gridColumns: null,	
			valueMapper: null,
			loadOnShow: true,
			actionButtons: [],
			actionButtonsSort: [],
			condition:{
			}
		});		
		me.callParent([config]);	
		
		Ext.apply(me, {
			grid: Ext.create('Ins.grid.ChooserGrid',{    		
			url: me.url,
			fields: me.fields,
			pkField: me.pkField,
			hidePagingBar: me.hidePagingBar,
			gridColumns: me.gridColumns,
			loadOnShow: me.loadOnShow,
			actionButtons: me.actionButtons,
			actionButtonsSort: me.actionButtonsSort,
			condition: me.condition,
			valueMapper: me.valueMapper,
			selectMode: me.selectMode,
			isAskWindow: false,
			onChooserCancel: function(){
				me.collapse();
			},
			onBeforeChooserSelect: me.onBeforeSelect,
			onChooserSelect: function(selection){
				if(me.onSelect != null){					
					me.onSelect(selection);									
				}
				me.collapse();
			}
			
		})
		});
	},
      
    //下拉样式图标
    triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
	//自动计算宽度
    matchFieldWidth: false,     

  	grid: null,
    
    //初始化方法(继承自父类)
    createPicker: function() {
	
	    var me = this;
		
		
		
		var picker = Ext.create('Ext.panel.Panel',{			
			border: true,
	        floating: true,	 
	        width: me.chooserWidth,	  
	        height: me.chooserHeight,
	        layout: 'fit',	       
	        shadow: true,  
	        maxHeight: 350,
			items: [me.grid]
		});
						
		return picker;
       
    },
    
     //展开 （继承自父类）
    onExpand: function() {   
       var me = this;
       me.grid.clearSelect();    
       me.grid.load();
    },

     //失去焦点(继承成自textfield)
    onBlur: function(e) { 
    	//将blur事件继续传播
    	var me = this;
    	me.callParent([e]);
    }

});;/**
 * 树选择器字段
 */
;Ext.define('Ins.chooser.ChooserTreeField',{
	extend:'Ext.form.field.Picker',
	alias : ["widget.ins_choosertreefield"],
	
	config : FormToolkit.config,	
	
	initComponent : function(){
        
    	var me = this;
    	
    	FormToolkit.init(this);
    	
        me.callParent();
    },
    
    editable: false,
    
    constructor: function(config){		
		var me = this;		
		
		Ext.apply(me, {
			//确认选择事件
			onSelect: null,
			//选择前
			onBeforeSelect: null,
			width : me.chooserWidth==undefined||me.chooserWidth==null ? 250 : me.chooserWidth,
			url : null,
			//是否可查询
			isSearch: me.isSearch==null||me.isSearch==undefined ? true : me.isSearch,
		});		
		me.callParent([config]);

		var defaultTreeConfig = {
				clearOnLoad: true,
				loadOnShow: true,
				showDefaultActBut: true,
				afterChecked: null,
				checkedAsParent: true,
				noCheckedAsParent: true,
				checkedAsChild: true,
				noCheckedAsChild: true,
		}
		
		Ext.apply(me, defaultTreeConfig);	
		for(key in defaultTreeConfig){
			if(config[key] != null && config[key] != undefined){
				me[key] = config[key];	
			}
		}
		
		var onFocusLeaveOld = me.onFocusLeave;
		me.onFocusLeave = function(e){
			var me = this;
			if($(e.target).hasClass('x-tree-checkbox')) return;
			onFocusLeaveOld.call(me,e);
		}
		
		
		me.tree = Ext.create('Ins.tree.ChooserTree',{ 
			isSearch: me.isSearch,
			url: me.url,			
			clearOnLoad: me.clearOnLoad,
			loadOnShow: me.loadOnShow,
			checkedAsParent: me.checkedAsParent,
			noCheckedAsParent: me.noCheckedAsParent,
			checkedAsChild: me.checkedAsChild,
			noCheckedAsChild: me.noCheckedAsChild,
			afterChecked: function(){
				me.afterChecked(me.tree.getChecked());
			},
			showDefaultActBut: me.showDefaultActBut,
			onChooserCancel: function(){
				me.collapse();
			},
			onBeforeChooserSelect: me.onBeforeSelect,
			onChooserSelect: function(record){
				if(me.onSelect != null){					
					me.onSelect(record);									
				}
				me.collapse();
			}
			
		});		

	},
	
    //下拉样式图标
    triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
	//自动计算宽度
    matchFieldWidth: false,     
    
    //初始化方法(继承自父类)
    createPicker: function() {
	
	    var me = this;
		
		var picker = Ext.create('Ext.panel.Panel',{			
			border: true,
	        floating: true,	 
	        width: me.chooserWidth,	  
	        height: me.chooserHeight,
	        layout: 'fit',	
	        maxHeight: 450,
	        minHeight: 250,
	        shadow: true,  
			items: [me.tree]
		});
						
		return picker;
       
    },
    
     //展开 （继承自父类）
    onExpand: function() { 
    	var me = this;
     	me.tree.reloadExpand();
    },

     //失去焦点(继承成自textfield)
    onBlur: function(e) { 
    	//将blur事件继续传播
    	var me = this;
    	me.callParent([e]);
    }

});;/**
 * 按钮选择器，一般用于多选
 */
Ext.define('Ins.chooser.ChooserGridButton', {
	extend: 'Ext.button.Button',
	alias: 'widget.ins_choosergridbutton',
	
	config: {
	
		//确认选择事件
		onSelect: null,
		//选择前
		onBeforeSelect: null,
		
		beforeShowClear: true,
		//以下属性对应普通表格的属性		
		//选择模式：MULTI、SINGLE
		selectMode : "MULTI",	
		gridUrl : null,
		fields: null,	
		pkField : "id",
		hidePagingBar: false,
		pageSize: 20,
		gridColumns: null,		
		loadOnShow: true,
		actionButtons: [],
		actionButtonsSort: [],
		valueMapper: null,
		condition:{
		},
		chooserWidth: 595,
		chooserHeight: 380,
		onBeforeShowChooser: null
		
	},
	
	initComponent: function(){
	
		var me = this;
		
		
		if(me.text == undefined || me.text == null || me.text == ''){
			me.text = '未命名选择器'
		}
		
		if(me.iconCls == undefined || me.iconCls == null || me.iconCls == ''){
			me.iconCls = 'icon-pageadd'
		}
		
		
		var grid = Ext.create('Ins.grid.ChooserGrid',{
			isAskWindow:false,
			width: me.chooserWidth,
			height: me.chooserHeight,
			url: me.gridUrl,
			fields: me.fields,
			pkField: me.pkField,
			pageSize: me.pageSize,
			gridColumns: me.gridColumns,
			loadOnShow: me.loadOnShow,
			actionButtons: me.actionButtons,
			actionButtonsSort: me.actionButtonsSort,
			condition: me.condition,
			hidePagingBar: me.hidePagingBar,
			selectMode: me.selectMode,
			valueMapper: me.valueMapper,
			onChooserCancel: function(){
				me.hideMenu();
			},
			onBeforeChooserSelect: me.onBeforeSelect,
			onChooserSelect: function(selection){
				if(me.onSelect != null){					
					me.onSelect(selection);									
				}
				me.hideMenu();					
			}
			
		});
		
		me.grid = grid;
		
		
		me.menu = new Ins.menu.Menu({
    		id: me.getId() + '_chooser_menu',
	        items: [
	            grid
	        ],
	        listeners: {
	        	beforeshow: function(){
	        		if(me.beforeShowClear){
						me.grid.clearSelect();
					}
					var flag = false;
					if(me.onBeforeShowChooser != null){
						flag = me.onBeforeShowChooser();
					}else{
						me.grid.load();
						flag = true;
					}
					return flag;
	        	}
	        },
	         //这个非常重要
			onMouseDown: function(e) {
				if(e.preventDefault){
		      		e.preventDefault();     
		      	}else{       
		      		e.returnValue = false;
		     	}		       
		    }
		});		
		
		/**
		 * 如果不设置这两个，表格的滚动条隐藏不了
		 */
		//菜单隐藏时事件
		me.addListener("menuhide", function(){		
			me.grid.setHidden(true);
		});		
		//菜单显示时时间
		me.addListener("menushow", function(){		
			me.grid.setHidden(false);
		});		
		
		
		//增加销毁时事件,清空资源
		me.addListener("beforedestroy", function(){		
			if(me.quickMenu!=undefined || me.quickMenu!=null){
				Ext.destroy(me.getMenu());
			}
		});		
		
		
		this.callParent(arguments);
	
	},
	
	getGrid: function(){
		return this.grid;
	}			
	
	
	
});;/**
 * 媒体播放器
 */
Ext.define('Ins.player.Player', {
	extend: 'Ext.Component',
	alias: 'widget.ins_player',
	
	config: {
		//视频文件地址，支持mp4，flv
		file: null,
		//视频开始图片
		image: null,	
		//是否自动播放(手机上不起作用)
		autostart: false,
		//私有属性
		//设置宽改请使用width/height
		_playerWidth: '100%',
		_playerHeight: 360,		
		
		//播放器准备就绪事件
		onPlayerReady: null,
		//改变音量事件
		onPlayerVolume: null,
		//播放事件
		onPlayerPlay: null,
		//暂停事件
		onPlayerPause: null,
		//播放完成事件
		onPlayerComplete: null,
		//改变播放进度时事件
		onPlayerSeek: null,
		//播放事件事件，每秒执行好几次
		onPlayerTime: null,
		//全屏时事件
		onPlayerFullscreen: null
	},
	
	initComponent: function(){
		
		var me = this;		
		//设置播放器的id
		var playerId = ((me.id==undefined||me.id==null) ? '' : me.id) + '_player';
		me.playerId = playerId; 
		me.html = '<div id="'+playerId+'"></div>';
		
		//设置播放器的宽和高
		if(me.width != undefined){			
			me._playerWidth = me.width;	
		}		
		if(me.height != undefined){		
			me._playerHeight = me.height;
		}
				
		me.on('render', function(){
			me.newInstance(playerId);
		});
		
		//增加销毁时事件,清空资源
		me.on('beforedestroy', function(){
			me.remove();
		});
		
		me.callParent(arguments);
		
	},
	
	newInstance: function(playerId){
		
		var me = this;
		
		//设置播放器
		jwplayer(playerId).setup({
			//primary: 'flash',	//是否直接使用flash播放		
			//flash播放器路径
			flashplayer: STATIC_PATH + '/lib/jwplayer/6.12.5/bin-release/jwplayer.flash.swf',
			file: me.file,
			image: me.image,
			autostart: me.autostart,
			width: me._playerWidth,
			height: me._playerHeight,
			//保持原始尺寸
			//stretching: 'none',
			//title: '播放',
			//description: "点击这里开始播放",	
			/**
			 * 以下属性暂时不用
			mute: false, //开始时是否为静音
			controls: false, //隐藏控制条
			repeat: false, //是否循环播放列表
			//右侧列表菜单
			listbar: {
				position: "right",
				size: 240
			},
			//播放列表 使用这个的时候不用指定me.file、me.image就不用指定了
			playlist: [
				{title: "Sintel Trailer1", description: "SintSintelel Trailer1", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer2", description: "SSintelintel Trailer2", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer3", description: "SiSintelntel Trailer3", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer1", description: "SintSintelel Trailer1", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer2", description: "SSintelintel Trailer2", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer3", description: "SiSintelntel Trailer3", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer1", description: "SintSintelel Trailer1", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer2", description: "SSintelintel Trailer2", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"},
				{title: "Sintel Trailer3", description: "SiSintelntel Trailer3", file: "http://192.168.100.13/e/H.mp4", image:"player.jpg"}
			],**/
			
			//事件
			events: {
				 onReady: function() { 
				 	if(me.onPlayerReady != null){
				 		me.onPlayerReady();
				 	}			 	
				 },
				 onVolume: function(obj) { 
				 	//obj.volume 当前音量
				 	if(me.onPlayerReady != null){
				 		me.onPlayerReady(obj.volume);
				 	}					 	
				 },
				 onPlay: function(){
				 	if(me.onPlayerPlay != null){
				 		me.onPlayerPlay();
				 	} 	
				 },
				 onPause: function(){
				 	if(me.onPlayerPause != null){
				 		me.onPlayerPause();					 		
				 	}
				 },
				 onComplete: function(){
				 	if(me.onPlayerComplete != null){
				 		me.onPlayerComplete();
				 	}
				 },
				 onSeek: function(obj){
				 	//console.log("之前的位置" + obj.position);
				 	//console.log("现在的位置" + obj.offset);
				 	if(me.onPlayerSeek != null){
				 		me.onPlayerSeek(obj.position, obj.offset);
				 	}
				 },					 
				 onTime: function(obj){
				 	//obj.position 当前秒
				 	if(me.onPlayerTime != null){
				 		me.onPlayerTime(obj.position);
				 	}
				 },
				 onFullscreen: function(obj){
				 	//obj.fullscreen  true/false
				 	if(me.onPlayerFullscreen != null){
				 		me.onPlayerFullscreen(obj.fullscreen);
				 	}
				 }
			}
		});
	},
	
	/**
	 * 获取播放器，注意必须等组件渲染好了才能获取到	 
	 */
	getPlayer: function(){		
		return jwplayer(this.playerId);
	},
	
	/**
	 * 播放
	 */
	play: function(){
		this.getPlayer().play(true);
	},
	
	/**
	 * 暂停
	 */
	pause: function(){
		this.getPlayer().pause(true);
	},
	
	getState: function(){
		/*
		 * idle: either playback has not started or playback was stopped (due to a stop() call or an error). Either the play or the error icon is visible in the display.
buffering: user pressed play, but sufficient data to start playback has to be loaded first. The buffering icon is visible in the display.
playing: the video is currently playing. No icon is visible in the display.
paused: the video is currently paused. The play icon is visible in the display.
		 * */
		return this.getPlayer().getState();
	},
	
	/**
	 * 停止播放
	 */
	stop: function(){
		this.getPlayer().stop();
	},
	
	/**
	 * 删除播放器
	 */
	remove: function(){
		this.getPlayer().remove();		
	},
	
	/**
	 * 加载视频
	 */
	load: function(url){
		this.getPlayer().load([{file:url}]);
	},
	
	/**
	 * 跳转到某个时间(秒)
	 */
	seek: function(second){
		this.getPlayer().seek(second);		
	},
	
	/**
	 * 获取当前播放到几秒 
	 */
	getPosition: function(){
		return this.getPlayer().getPosition(); 
	},
	
	/**
	 * 获取是否静音
	 */
	getMute: function(){
		return this.getPlayer().getMute();
	},
	
	/**
	 * 设置是否静音	 
	 */
	setMute: function(state){
		this.getPlayer().setMute(state);		
	},
	
	/**
	 * 获取音量0-100
	 */
	getVolume: function(){
		return this.getPlayer().getVolume();
	},
	
	/**
	 * 设置音量0-100	
	 */
	setVolume: function(volume){
		this.getPlayer().setVolume(volume);		
	},
	
	/**
	 * 获取高度
	 */
	getHeight: function(){
		return this.getPlayer().getHeight();
	},
	
	/**
	 * 获取宽度	 
	 */
	getWidth: function(){
		return this.getPlayer().getWidth();
	},
	
	/**
	 * 改变大小
	 * @param {} width
	 * @param {} height
	 */
	resize: function(width, height){
		this.getPlayer().resize(width, height);		
	}
	
});;Ext.define('Ins.list.CrudList',{
	extend: 'Ins.panel.Panel',
	alias: 'ins_crudlist',
	
	config: {
		
		urlConfig : {
			/*
			loadAll: CTX_PATH + '/link/loadAll',
			add: CTX_PATH + '/link/add',
			load:  CTX_PATH + '/link/load',
			update:  CTX_PATH + '/link/update',
			remove:  CTX_PATH + '/link/remove'
			*/
		},
		
		/*
		 *
		dataSet:[
			{	name : 'id',
	    		text : '主键'
			},{
				name: 'name',
				text: '名字'
			}
		]	
		 * */
		dataSet : [],	
		
		/*formFields: [
			{
				forData: 'id',
				xtype: 'ins_hiddenfield'
				
			},{
				forData: 'name',				
				allowBlank: false,
				colspan: 2
			}]
		*/
		formFields : [],
		
		//权限
		permission : {
			add : true,
			update : true,
			remove : true,
			view: true
		},
		
		//显示列图标
		tplImgUrl: '{icon}',
		//显示列字段
		tplField: '{text}',
		
		//操作表单列数
		columnSize : 2,
		
		//默认表单字段宽度
		defaultFormFieldWidth : 230,
		
		//主键字段
		pkField : "id",
		//是否自动加载数据
		loadOnShow: true,
		
		//右键菜单
		customMenu : [],
		
		//隐藏标题
		hideTitle: false,
		
		//记录单击监听器
		onRowClick : null,
		
		
		//在新增表单展示之前触发的函数
		onBeforeShowAddForm : null,
		//在编辑表单展示之前触发的函数
		onBeforeShowEditForm : null,
		//在查看明细之前触发的函数
		onBeforeShowViewForm: null,
		
		//右键菜单显示前
		onBeforeShowCxtMenu: null,
		//右键菜单显示后
		onAfterShowCxtMenu: null,
		
		//在新增表单展示之后
		onAfterShowAddForm: null,
		//在编辑表单展示之后
		onAfterShowEditForm: null,
		//在查看明细后显示
		onAfterShowViewForm: null,
		
		//在执行删除之前执行的函数
		onBeforeDelete : null,
		//添加保存之前执行的函数
		onBeforeSaveAdd : null,
		//编辑保存之前执行的函数
		onBeforeSaveEdit : null,
		
		//在删除操作成功之后
		onAfterDelete: null,
		//增加或编辑表单执行成功时调用，如果返回true则执行重置表单，topmessage等操作，返回false则不执行后续操作
		onAfterFormSubmit: null,		
		
		//操作表单高度
		formWindowHeight : null,	
		//操作表单宽度
		formWindowWidth: 510,
		//操作表单最大高度
		formWindowMaxHeight: null,
		//操作表单最大宽度
		formWindowMaxWidth: null,
		//操作表单窗体是否遮罩
		formWindowModal: false,
		
		//自定义标签
		labelConfig : {
			add : {
				text : null,
				iconCls : null
			},
			update : {
				text : null,
				iconCls : null
			},
			view : {
				text : null,
				iconCls : null
			},
			remove : {
				text : null,
				iconCls : null
			},
			query : {
				text : null,
				iconCls : null
			}
		}
		
	},
	
	
	
	initComponent: function(){
		
		var me = this;
		
		//隐藏标题		
		if (me.getHeader() != null || me.hideTitle) {

			Ext.apply(me, {
						header : !me.hideTitle
					});
		}
		
		//构造 store fields 数组
		var _fields = [];
		for(var i in me.dataSet){			
			var record = me.dataSet[i];
			_fields.push(record.name);
		}
		Ext.apply(me, {
			fields : _fields
		});
		
		
		var store = Ext.create('Ins.data.Store', {
			url: me.urlConfig.loadAll,	  		
			fields: me.fields,
			idProperty: me.pkField,
			pageSize: 1000,
			autoLoad: me.loadOnShow
		});
		Ext.apply(me, {
			store : store
		});
		
		
		//生成表单字段数组
		var _formFields = [];	
		for(var i in me.formFields){			
			var item = me.formFields[i];
			var field = null;
			//不分组时
			if((item.forData!=undefined && item.forData!=null) || (item.name!=undefined && item.name!=null)){
				field = me.getFieldDefByName(Ext.clone(item));				
				//设置默认表单字段宽度（默认宽度只在没有指定宽度时有效）
				if(field != null && field.width == undefined && field.xtype!='component' && field.xtype != 'container' && field.xtype != 'panel' && field.xtype != 'ins_panel' && field.xtype != 'ins_fieldcontainer'){							
					Ext.apply(field, {
						width : me.defaultFormFieldWidth
					});
				}				
			}
			//分组时
			else if(item.items!=undefined && item.items!=null){
				field = Ext.clone(item);
				field.items = [];
				for(var j in item.items){
					if(item.items[j] != null && item.items[j].width == undefined && item.items[j].xtype!='component' && item.items[j].xtype != 'container' && item.items[j].xtype != 'panel' && item.items[j].xtype != 'ins_panel' && item.items[j].xtype != 'ins_fieldcontainer'){							
						Ext.apply(item.items[j], {
							width : me.defaultFormFieldWidth
						});
					}					
					field.items.push(me.getFieldDefByName(Ext.clone(item.items[j])));
				}
			}
			
			_formFields.push(field);
		}
		
		
		//操作表单配置
		var formConfig = {	
			fields : _formFields,
			modal: me.formWindowModal,
			defaultFormFieldWidth : me.defaultFormFieldWidth,
			columnSize : me.columnSize,
			onBeforeHide: function(obj){
				obj.getForm().reset();
			}
		};
		
		if(me.onAfterFormSubmit != null){
			formConfig.onAfterSubmit = me.onAfterFormSubmit;
		}
		
		if(me.formWindowHeight != null){
			formConfig.height = me.formWindowHeight;
		}		
		if(me.formWindowWidth != null){
			formConfig.width = me.formWindowWidth;
		}
		if(me.formWindowMaxHeight != null){
			formConfig.maxHeight = me.formWindowMaxHeight;
		}		
		if(me.formWindowMaxWidth != null){
			formConfig.maxWidth = me.formWindowMaxWidth;
		}	
		
		//操作表单
		var form = Ext.create("Ins.form.OprFormWindow", formConfig);	
		
		//覆盖基础属性
		Ext.apply(me, {
			form : form,//将 form 也注册到组件中，以便其他地方调用			
			url : me.urlConfig.loadAll
		});
		
		//右键菜单项
		var _menu = [];
		
		
		//是否允许编辑
		if(me.urlConfig.update!=undefined && me.urlConfig.update!=null 
				&& me.urlConfig.load!=undefined && me.urlConfig.load!=null && me.permission.update){			
			_menu.push({
				id : me.getId() + "_edit_btn",
				iconCls: me.labelConfig.update.iconCls != null ? me.labelConfig.update.iconCls : 'icon-edit',
				text: me.labelConfig.update.text != null ? me.labelConfig.update.text : '编辑',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowEditForm != undefined && me.onBeforeShowEditForm != null){						
						flag = me.onBeforeShowEditForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};					
					params[me.pkField] = me.getCurrentRecord().data[me.pkField];	
					
					form.showAsEditMode({
						//告诉后台此操作不记录日志
						loadUrl: me.urlConfig.load + '?nolog=y',
						updateUrl: me.urlConfig.update,
						onBeforeSave: me.onBeforeSaveEdit,
						params: params,
						success: function(data){
							me.load();
						},
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowEditForm != null){
								me.onAfterShowEditForm(form, data);
							}	
						}
					});
				}	
			});
		}
		
		//是否允许删除
		if(me.urlConfig.remove!=undefined && me.urlConfig.remove!=null && me.permission.remove){			
			_menu.push({
				id : me.getId() + "_delete_btn",
		        text: me.labelConfig.remove.text != null ? me.labelConfig.remove.text : '删除',
		        iconCls: me.labelConfig.remove.iconCls != null ? me.labelConfig.remove.iconCls : 'icon-delete',
				handler: function(btn){					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeDelete != undefined && me.onBeforeDelete != null){
						
						flag = me.onBeforeDelete(me.getCurrentRecord());
					}
					if(!flag){
						return;
					}
										
					var id = me.getCurrentRecord().data[me.pkField];
					
					Messager.send({
					  confirm: "确认要删除该节点吗？",
					  maskCmp: me,
					  url: me.urlConfig.remove,
					  data: {
						  id : id
					  },
					  onSuccess:function(data){							  	 
						  me.load();
						  
						  if(me.onAfterDelete!=null){
						  	  me.onAfterDelete();
						  }
						  
						  TopMessage.info("操作成功！");
					  }
					});
				}
		    });
		}
		
		//是否允许查看明细
		if(me.urlConfig.load!=undefined && me.urlConfig.load!=null && me.permission.view){			
			_menu.push({
				id : me.getId() + "_view_btn",
				iconCls: me.labelConfig.view.iconCls != null ? me.labelConfig.view.iconCls : 'icon-view',
				text: me.labelConfig.view.text != null ? me.labelConfig.view.text : '查看明细',
				handler: function() {	
					
					//如果注册有事件监听器，则注册
					var flag = true;
					if(me.onBeforeShowViewForm != undefined && me.onBeforeShowViewForm != null){						
						flag = me.onBeforeShowViewForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					//构造查询参数
					var params = {};
					params[me.pkField] = me.getCurrentRecord().data[me.pkField];
					
					form.showAsViewMode({
						loadUrl: me.urlConfig.load,
						params: params,
						onSuccess: function(form, data){
							//如果注册有事件监听器，则注册
							if(me.onAfterShowViewForm != null){
								me.onAfterShowViewForm(form, data);
							}	
						}
					});
				}	
			});
		}
		//scrollable: 'y',
		//itemSelector: 'div',
		
		//添加用户自定义菜单项
		if(me.customMenu != undefined && me.customMenu.length > 0){			
			if(_menu.length > 0){
				_menu.push("-");
			}			
			for(var i in me.customMenu){				
				var item = me.customMenu[i];				
				_menu.push(item);
			}
		}
		
		//构建右键菜单		
		if(_menu.length > 0){			
			var nodemenu = new Ins.menu.Menu({  	            
	            items : _menu
	        }); 			
			Ext.apply(me, {				
				contextMenu : nodemenu				
			});
		}	
		
		
		//dataview				
		if(me.tplField.indexOf("{") == -1){
			me.tplField = '{' + me.tplField + '}';
		}
		var dataView = Ext.create('Ext.view.View', {
			scrollable: 'y',
			itemSelector: 'div',
			store: me.store,
			tpl: '<tpl for=".">' +
				'<div style="background: url(' + me.tplImgUrl + ') no-repeat 8px 2px;margin-top: 3px;margin-bottom: 3px;padding-left: 29px;font-size: 13px;line-height: 20px;cursor: pointer;border: 1px solid #fff;">'+me.tplField+'</div>' +
				'</tpl>',
			listeners: {
				itemclick: function(obj, record, item, index, e, eOpts) {
					//记录当前记录行
	    			me.currentRecord = record;
	    			if(me.onRowClick != null){
	    				me.onRowClick(record, index, e);
	    			}
				},
				itemcontextmenu: function(obj, record, item, index, e, eOpts) {					
					App.utils.preventDefault(e);
					//供右键菜单使用的record
					me.currentRecord = record;
					
					var flag = true;
					if(me.onBeforeShowCxtMenu != null){
						flag = me.onBeforeShowCxtMenu(nodemenu);
					}
					if(flag && _menu.length > 0){
						me.contextMenu.showAt(e.getXY());
						if(me.onAfterShowCxtMenu!=null){
							me.onAfterShowCxtMenu(nodemenu);
						}						
					}
				}
			}
		});		
		me.dataView = dataView;		
		
		var headButtons = [];
		if(me.permission.add){
			headButtons.push({
				type: 'plus',
				tooltip: '添加',
				handler: function(event, toolEl, panelHeader){		
					
					var flag = true;
					if(me.onBeforeShowAddForm != null){						
						flag = me.onBeforeShowAddForm(me.getForm());
					}
					if(!flag){
						return;
					}
					
					form.showAsAddMode({
						url: me.urlConfig.add,	
						onBeforeSave: me.onBeforeSaveAdd,
						success: function(data){
							me.load();
						}
					});
					
					//如果注册有事件监听器，则注册
					if(me.onAfterShowAddForm != null){
						me.onAfterShowAddForm(me.getForm());
					}
				}
			});
		}
		
		headButtons.push({
			type:'refresh',
		    tooltip: 'Refresh',
		    handler: function(event, toolEl, panelHeader) {
		        me.load();
		    }
		});
		
		
		Ext.apply(me, {		
			views: [dataView],
			headButtons: headButtons
			
		});
		
		
		//增加销毁时事件,清空资源
		me.addListener("beforedestroy", function(){
			//销毁表单
			if(me.form!=undefined || me.form!=null){
				Ext.destroy(me.form);
			}
			//销毁右键菜单
			if(me.contextMenu!=undefined || me.contextMenu!=null){
				Ext.destroy(me.contextMenu);
			}
		});			
		
		me.callParent(arguments);
		
	},
	
	/**
	 * 根据字段名构造字段对象
	 * 
	 * @param fieldName
	 * @returns {Object}
	 */
	getFieldDefByName : function(fieldOld){	
		var me = this;		
		//遍历数据集并定位到该字段对应的配置		
		for(var i in me.dataSet){			
			var record = me.dataSet[i];	
			if(record.name == fieldOld.forData){				
				//更新属性
				Ext.apply(fieldOld, {
					fieldLabel: record.text,
					text : record.text,
					name : record.name
				});				
				return fieldOld;
			}						
		}	
		
		if(fieldOld.name!=null && fieldOld.name!=undefined){
			//更新属性
			Ext.apply(fieldOld, {
				fieldLabel: fieldOld.text,
				text : fieldOld.text					
			});				
			return fieldOld;
		}		
		if(window.console){
			console.error('没有发现 dataSet 中配置的该表单字段。');
		}
	},
	
	/**
	 * 获取表单对象
	 * 
	 * @returns
	 */
	getForm : function(){
		
		var me = this;
		
		return me.form.getForm();
	},
	
	/**
	 * 获取当前行
	 * @return {}
	 */
	getCurrentRecord: function(){
		var me = this;
		return me.currentRecord;
	},
	
	/**
	 * 获取dataVIew
	 * @return {}
	 */
	getDataView: function(){
		var me = this;
		return me.dataView;
	},
	
	/**
     * 重新加载该表格数据
     * 
     * @param params
     */
    load : function(params){
    	
    	var me = this;
    	
    	//首先启用表格
    	me.setDisabled(false);
    	
    	//必须在 params 有值时才赋值，否则会覆盖之前的查询条件的值
    	if(params != undefined && params != null){
    		
    		//将条件赋给对loadParams属性，便于以后使用
    		me.loadParams = params;
    		
    		me.dataView.getStore().proxy.extraParams = me.loadParams;
    	}
    	
    	me.dataView.getStore().reload();
    }
	
});;Ext.define('Ins.grid.GridHeaderUtil',{
	init:function(me){
		
		var _saveGridColums = me.saveGridColums;
		if (_saveGridColums&&_saveGridColums.enabled!=false) {
			
			if (CURRENT_USER==null&&!CURRENT_USER.id) {
				if(window.console){
					console.error('没有用户id，不能使用自定义表头功能。');
				}
				return;
			}
			if (!_saveGridColums.key) {
				if(window.console){
					console.error('saveGridColums中没有发现key值，key值是必选属性');
				}
				return;
			}
			
			/**
		     * 保存表头
		     * @param {} ct
		     */
			me._saveColumns=function(ct){
				var saveFields = ['width','text','dataIndex','hidden','locked']
				var cm = {};
				Ext.Array.each(ct.getGridColumns(),function(o,i){
					cm = {sortNum:i};
					Ext.Array.each(saveFields,function(fd){
						cm[fd]=o[fd];
					});
					if (o.dataIndex) {
						me.INS_GRIDCOLUMSMAP[o.dataIndex]=cm;
					}
				
				});
			}
			
			/**
			 * 排序
			 */
			me.sortBySortNum=function(_gridColums){
				return Ext.Array.sort(_gridColums,function compare(a, b){
				    if (a.sortNum<b.sortNum)
				        return -1;
				    if (a.sortNum>b.sortNum)
				       return 1;
				    return 0;
				});
			}

			//crud表格需要添加dataIndex字段
			Ext.Array.each(me.gridColumns,function(o,i){
				if (!o.dataIndex) {
					o.dataIndex = o.forData;
				}
				if(o!=undefined && o.columns!=null && o.columns.length>0){
					Ext.Array.each(o.columns,function(columnObj,j){
						if (!columnObj.dataIndex) {
							columnObj.dataIndex = columnObj.forData;
						}
					})
				}
			})
			
			me.INS_GRIDCOLUMSMAP={};
			//添加监听事件
			me.addListener("columnschanged", function(ct){
				me._saveColumns(ct);
			});		
			me.addListener("columnresize", function(ct){	
				me._saveColumns(ct);
			});
			
			//如果配置了保存表头数据
			//添加保存按钮
			var button = {
				text: '保存表头',
				iconCls: 'icon-ok'
			}
			if (_saveGridColums.button) {
				Ext.apply(button,_saveGridColums.button)
			}
			var _handler = button.handler;
			button.handler=function(){
				//保存表头
				 Messager.send({
                    url: CTX_PATH + '/grid/header/saveGridColums',
                    data:{
                    	userId:CURRENT_USER.id,
                    	key:_saveGridColums.key,
                    	columsJson:JSON.stringify(me.INS_GRIDCOLUMSMAP)
                    },
                    onSuccess: function(data){
                    	var flag = true;
                        if (_handler!=null) {
							flag = _handler();
						}
						if (flag) {TopMessage.info("表头保存成功");}
                    },
                    onError:function(){
                        alert("请求异常");
                    }
                })
			}
			
			if (!me.actionButtons) {
				me.actionButtons=[];
			}
			me.actionButtons.push('->',button);
			
			
			//获取表头
			var cms = null;
			Messager.send({
                url: CTX_PATH + '/grid/header/getGridColums',
                sync: true,
                data:{
                	userId:CURRENT_USER.id,
                	key:_saveGridColums.key
                },
                onSuccess: function(data){
                	if (data&&data.columsJson) {
	                	cms = JSON.parse(data.columsJson);
                	}
                },
                onError:function(){
                    alert("获取表头失败");
                }
            })
			
			//如果查询到表头信息则进行以下处理
			if (cms) {
				//替换表头数据
				var _gridColums = [];
				var tmp;
				var _columns=[];
				Ext.Array.each(me.gridColumns,function(o,i){
					tmp=cms[o.dataIndex];
					if (tmp) {
						Ext.apply(o,tmp);
					}
					//处理有子表头
					if(o!=undefined && o.columns!=null && o.columns.length>0){
						_columns = [];
						Ext.Array.each(o.columns,function(columnObj,j){
							tmp=cms[columnObj.dataIndex];
							if (tmp) {
								Ext.apply(columnObj,tmp);
							}
							if (columnObj.locked===true) {
								_gridColums.push(columnObj);
							}else{
								_columns.push(columnObj);
								if (!o.sortNum) {
									o.sortNum = columnObj.sortNum;
								}
							}
		    			})
		    			o.columns = me.sortBySortNum(_columns);
		    			if (o.columns.length>0) {
		    				_gridColums.push(o);
		    			}
					}else{
						_gridColums.push(o);
					}
					
				});
				//表头排序,替换表头
				me.gridColumns = me.sortBySortNum(_gridColums);
				
			}
			
		}
	
	}
});Ext.define('Ins.grid.GridDev',{
	extend: 'Ins.grid.Grid',
	alias : ["widget.ins_griddev"],
	//存放表格
	initComponent:function(){
		var me = this;
		App.getClass('Ins.grid.GridHeaderUtil').init(me);
		this.callParent(arguments);
    }
});Ext.define('Ins.grid.CrudGridDev',{
	extend: 'Ins.grid.CrudGrid',
	alias : ["widget.ins_crudgriddev"],
	//存放表格
	initComponent:function(){
		var me = this;
		App.getClass('Ins.grid.GridHeaderUtil').init(me);
		this.callParent(arguments);
    }
})