/*!
 * artTemplate - Template Engine
 * https://github.com/aui/artTemplate
 * Released under the MIT, BSD, and GPL Licenses
 */
 
 
/**
 * # artTemplate-3.0

    新一代 javascript 模板引擎

##      	目录

*	[特性](#特性)
*	[快速上手](#快速上手)
*	[模板语法](#模板语法)
*	[下载](#下载)
*	[方法](#方法)
*	[NodeJS](#nodejs)
*	[使用预编译](#使用预编译)
*	[更新日志](#更新日志)
*	[授权协议](#授权协议)

##	特性

1.	性能卓越，执行速度通常是 Mustache 与 tmpl 的 20 多倍（[性能测试](http://aui.github.com/artTemplate/test/test-speed.html)）
2.	支持运行时调试，可精确定位异常模板所在语句（[演示](http://aui.github.io/artTemplate/demo/debug.html)）
3.	对 NodeJS Express 友好支持
4.	安全，默认对输出进行转义、在沙箱中运行编译后的代码（Node版本可以安全执行用户上传的模板）
5.	支持``include``语句
6.	可在浏览器端实现按路径加载模板（[详情](#使用预编译)）
7.	支持预编译，可将模板转换成为非常精简的 js 文件
8.	模板语句简洁，无需前缀引用数据，有简洁版本与原生语法版本可选
9.	支持所有流行的浏览器

## 快速上手

### 编写模板

使用一个``type="text/html"``的``script``标签存放模板：
	
	<script id="test" type="text/html">
	<h1>{{title}}</h1>
	<ul>
	    {{each list as value i}}
	        <li>索引 {{i + 1}} ：{{value}}</li>
	    {{/each}}
	</ul>
	</script>

### 渲染模板
	
	var data = {
		title: '标签',
		list: ['文艺', '博客', '摄影', '电影', '民谣', '旅行', '吉他']
	};
	var html = template('test', data);
	document.getElementById('content').innerHTML = html;


[演示](http://aui.github.com/artTemplate/demo/basic.html)

##	模板语法

有两个版本的模板语法可以选择。

###	简洁语法

推荐使用，语法简单实用，利于读写。

	{{if admin}}
		{{include 'admin_content'}}
		
		{{each list}}
			<div>{{$index}}. {{$value.user}}</div>
		{{/each}}
	{{/if}}
	
[查看语法与演示](https://github.com/aui/artTemplate/wiki/syntax:simple)

###	原生语法
	
	<%if (admin){%>
		<%include('admin_content')%>
	
		<%for (var i=0;i<list.length;i++) {%>
			<div><%=i%>. <%=list[i].user%></div>
		<%}%>
	<%}%>

[查看语法与演示](https://github.com/aui/artTemplate/wiki/syntax:native)

##	下载

* [template.js](https://raw.github.com/aui/artTemplate/master/dist/template.js) *(简洁语法版, 2.7kb)* 
* [template-native.js](https://raw.github.com/aui/artTemplate/master/dist/template-native.js) *(原生语法版, 2.3kb)*

## 方法

###	template(id, data)

根据 id 渲染模板。内部会根据``document.getElementById(id)``查找模板。

如果没有 data 参数，那么将返回一渲染函数。

###	template.``compile``(source, options)

将返回一个渲染函数。[演示](http://aui.github.com/artTemplate/demo/compile.html)

###	template.``render``(source, options)

将返回渲染结果。

###	template.``helper``(name, callback)

添加辅助方法。

例如时间格式器：[演示](http://aui.github.com/artTemplate/demo/helper.html)

###	template.``config``(name, value)

更改引擎的默认配置。

字段 | 类型 | 默认值| 说明
------------ | ------------- | ------------ | ------------
openTag | String | ``'{{'`` | 逻辑语法开始标签
closeTag | String | ``"}}"`` | 逻辑语法结束标签
escape | Boolean | ``true`` | 是否编码输出 HTML 字符
cache | Boolean | ``true`` | 是否开启缓存（依赖 options 的 filename 字段）
compress | Boolean | ``false`` | 是否压缩 HTML 多余空白字符
	
##	使用预编译 

可突破浏览器限制，让前端模板拥有后端模板一样的同步“文件”加载能力：

一、**按文件与目录组织模板**

```
template('tpl/home/main', data)
```

二、**模板支持引入子模板**


	{{include '../public/header'}}

###	基于预编译：

*	可将模板转换成为非常精简的 js 文件（不依赖引擎）
*	使用同步模板加载接口
*	支持多种 js 模块输出：AMD、CMD、CommonJS
*	支持作为 GruntJS 插件构建
*	前端模板可共享给 NodeJS 执行
*	自动压缩打包模板

预编译工具：[TmodJS](http://github.com/aui/tmodjs/)

##	NodeJS

###	安装

	npm install art-template
	
###	使用

	var template = require('art-template');
	var data = {list: ["aui", "test"]};
	
	var html = template(__dirname + '/index/main', data);

###	配置

NodeJS 版本新增了如下默认配置：
	
字段 | 类型 | 默认值| 说明
------------ | ------------- | ------------ | ------------
base | String | ``''`` | 指定模板目录
extname | String | ``'.html'`` | 指定模板后缀名
encoding | String | ``'utf-8'`` | 指定模板编码
	
配置``base``指定模板目录可以缩短模板的路径，并且能够避免``include``语句越级访问任意路径引发安全隐患，例如：
	
	template.config('base', __dirname);
	var html = template('index/main', data)
	
###	NodeJS + Express

	var template = require('art-template');
	template.config('base', '');
	template.config('extname', '.html');
	app.engine('.html', template.__express);
	app.set('view engine', 'html');
	//app.set('views', __dirname + '/views');
	
运行 demo:

	node demo/node-template-express.js
	
> 若使用 js 原生语法作为模板语法，请改用 ``require('art-template/node/template-native.js')``

##	升级参考

为了适配 NodeJS express，artTemplate v3.0.0 接口有调整。

###	接口变更

1.	默认使用简洁语法
2. ``template.render()``方法的第一个参数不再是 id，而是模板字符串
3. 使用新的配置接口``template.config()``并且字段名有修改
4. ``template.compile()``方法不支持 id 参数
5. helper 方法不再强制原文输出，是否编码取决于模板语句
6. ``template.helpers`` 中的``$string``、``$escape``、``$each``已迁移到``template.utils``中
7. ``template()``方法不支持传入模板直接编译

###	升级方法

1. 如果想继续使用 js 原生语法作为模板语言，请使用 [template-native.js](https://raw.github.com/aui/artTemplate/master/dist/template-native.js)
2. 查找项目```template.render```替换为```template```
3. 使用``template.config(name, value)``来替换以前的配置
4. ``template()``方法直接传入的模板改用``template.compile()``（v2初期版本）

## 更新日志

###	v3.0.3

1. 解决``template.helper()``方法传入的数据被转成字符串的问题 #96
2. 解决``{{value || value2}}``被识别为管道语句的问题 #105 <https://github.com/aui/tmodjs/issues/48>

###	v3.0.2

1.	~~解决管道语法必须使用空格分隔的问题~~

### v3.0.1

1.	适配 express3.x 与 4.x，修复路径 BUG

### v3.0.0

1. 提供 NodeJS 专属版本，支持使用路径加载模板，并且模板的``include``语句也支持相对路径
2. 适配 [express](http://expressjs.com) 框架
3. 内置``print``语句支持传入多个参数
4. 支持全局缓存配置
5. 简洁语法版支持管道风格的 helper 调用，例如：``{{time | dateFormat:'yyyy年 MM月 dd日 hh:mm:ss'}}``

当前版本接口有调整，请阅读 [升级参考](#升级参考)

> artTemplate 预编译工具 [TmodJS](https://github.com/aui/tmodjs) 已更新

###	v2.0.4

1.	修复低版本安卓浏览器编译后可能产生语法错误的问题（因为此版本浏览器 js 引擎存在 BUG）

###	v2.0.3

1.	优化辅助方法性能
2.	NodeJS 用户可以通过 npm 获取 artTemplate：``$ npm install art-template -g``
3.	不转义输出语句推荐使用``<%=#value%>``（兼容 v2.0.3 版本之前使用的``<%==value%>``），而简版语法则可以使用``{{#value}}``
4.	提供简版语法的合并版本 dist/[template-simple.js](https://raw.github.com/aui/artTemplate/master/dist/template-simple.js)

### v2.0.2

1.	优化自定义语法扩展，减少体积
2.	[重要]为了最大化兼容第三方库，自定义语法扩展默认界定符修改为``{{``与``}}``。
3.	修复合并工具的BUG [#25](https://github.com/aui/artTemplate/issues/25)
4.	公开了内部缓存，可以通过``template.cache``访问到编译后的函数
5.	公开了辅助方法缓存，可以通过``template.helpers``访问到
6.	优化了调试信息

### v2.0.1

1.	修复模板变量静态分析的[BUG](https://github.com/aui/artTemplate/pull/22)

### v2.0 release

1.	~~编译工具更名为 atc，成为 artTemplate 的子项目单独维护：<https://github.com/cdc-im/atc>~~

### v2.0 beta5

1. 修复编译工具可能存在重复依赖的问题。感谢 @warmhug
2. 修复预编译``include``内部实现可能产生上下文不一致的问题。感谢 @warmhug
3. 编译工具支持使用拖拽文件进行单独编译

### v2.0 beta4

1. 修复编译工具在压缩模板可能导致 HTML 意外截断的问题。感谢 @warmhug
2. 完善编译工具对``include``支持支持，可以支持不同目录之间模板嵌套
3. 修复编译工具没能正确处理自定义语法插件的辅助方法

### v2.0 beta1

1.	对非 String、Number 类型的数据不输出，而 Function 类型求值后输出。
2.	默认对 html 进行转义输出，原文输出可使用``<%==value%>``（备注：v2.0.3 推荐使用``<%=#value%>``），也可以关闭默认的转义功能``template.defaults.escape = false``。
3.	增加批处理工具支持把模板编译成不依赖模板引擎的 js 文件，可通过 RequireJS、SeaJS 等模块加载器进行异步加载。

## 授权协议

Released under the MIT, BSD, and GPL Licenses

============

[所有演示例子](http://aui.github.com/artTemplate/demo/index.html) | [引擎原理](http://cdc.tencent.com/?p=5723)

© tencent.com

  */
 
 
!(function () {


/**
 * 模板引擎
 * @name    template
 * @param   {String}            模板名
 * @param   {Object, String}    数据。如果为字符串则编译并缓存编译结果
 * @return  {String, Function}  渲染好的HTML字符串或者渲染方法
 */
var template = function (filename, content) {
    return typeof content === 'string'
    ?   compile(content, {
            filename: filename
        })
    :   renderFile(filename, content);
};


template.version = '3.0.0';


/**
 * 设置全局配置
 * @name    template.config
 * @param   {String}    名称
 * @param   {Any}       值
 */
template.config = function (name, value) {
    defaults[name] = value;
};



var defaults = template.defaults = {
    openTag: '<%',    // 逻辑语法开始标签
    closeTag: '%>',   // 逻辑语法结束标签
    escape: true,     // 是否编码输出变量的 HTML 字符
    cache: true,      // 是否开启缓存（依赖 options 的 filename 字段）
    compress: false,  // 是否压缩输出
    parser: null      // 自定义语法格式器 @see: template-syntax.js
};


var cacheStore = template.cache = {};


/**
 * 渲染模板
 * @name    template.render
 * @param   {String}    模板
 * @param   {Object}    数据
 * @return  {String}    渲染好的字符串
 */
template.render = function (source, options) {
    return compile(source, options);
};


/**
 * 渲染模板(根据模板名)
 * @name    template.render
 * @param   {String}    模板名
 * @param   {Object}    数据
 * @return  {String}    渲染好的字符串
 */
var renderFile = template.renderFile = function (filename, data) {
    var fn = template.get(filename) || showDebugInfo({
        filename: filename,
        name: 'Render Error',
        message: 'Template not found'
    });
    return data ? fn(data) : fn;
};


/**
 * 获取编译缓存（可由外部重写此方法）
 * @param   {String}    模板名
 * @param   {Function}  编译好的函数
 */
template.get = function (filename) {

    var cache;
    
    if (cacheStore[filename]) {
        // 使用内存缓存
        cache = cacheStore[filename];
    } else if (typeof document === 'object') {
        // 加载模板并编译
        var elem = document.getElementById(filename);
        
        if (elem) {
            var source = (elem.value || elem.innerHTML)
            .replace(/^\s*|\s*$/g, '');
            cache = compile(source, {
                filename: filename
            });
        }
    }

    return cache;
};


var toString = function (value, type) {

    if (typeof value !== 'string') {

        type = typeof value;
        if (type === 'number') {
            value += '';
        } else if (type === 'function') {
            value = toString(value.call(value));
        } else {
            value = '';
        }
    }

    return value;

};


var escapeMap = {
    "<": "&#60;",
    ">": "&#62;",
    '"': "&#34;",
    "'": "&#39;",
    "&": "&#38;"
};


var escapeFn = function (s) {
    return escapeMap[s];
};

var escapeHTML = function (content) {
    return toString(content)
    .replace(/&(?![\w#]+;)|[<>"']/g, escapeFn);
};


var isArray = Array.isArray || function (obj) {
    return ({}).toString.call(obj) === '[object Array]';
};


var each = function (data, callback) {
    var i, len;        
    if (isArray(data)) {
        for (i = 0, len = data.length; i < len; i++) {
            callback.call(data, data[i], i, data);
        }
    } else {
        for (i in data) {
            callback.call(data, data[i], i);
        }
    }
};


var utils = template.utils = {

	$helpers: {},

    $include: renderFile,

    $string: toString,

    $escape: escapeHTML,

    $each: each
    
};/**
 * 添加模板辅助方法
 * @name    template.helper
 * @param   {String}    名称
 * @param   {Function}  方法
 */
template.helper = function (name, helper) {
    helpers[name] = helper;
};

var helpers = template.helpers = utils.$helpers;




/**
 * 模板错误事件（可由外部重写此方法）
 * @name    template.onerror
 * @event
 */
template.onerror = function (e) {
    var message = 'Template Error\n\n';
    for (var name in e) {
        message += '<' + name + '>\n' + e[name] + '\n\n';
    }
    
    if (typeof console === 'object') {
        console.error(message);
    }
};


// 模板调试器
var showDebugInfo = function (e) {

    template.onerror(e);
    
    return function () {
        return '{Template Error}';
    };
};


/**
 * 编译模板
 * 2012-6-6 @TooBug: define 方法名改为 compile，与 Node Express 保持一致
 * @name    template.compile
 * @param   {String}    模板字符串
 * @param   {Object}    编译选项
 *
 *      - openTag       {String}
 *      - closeTag      {String}
 *      - filename      {String}
 *      - escape        {Boolean}
 *      - compress      {Boolean}
 *      - debug         {Boolean}
 *      - cache         {Boolean}
 *      - parser        {Function}
 *
 * @return  {Function}  渲染方法
 */
var compile = template.compile = function (source, options) {
    
    // 合并默认配置
    options = options || {};
    for (var name in defaults) {
        if (options[name] === undefined) {
            options[name] = defaults[name];
        }
    }


    var filename = options.filename;


    try {
        
        var Render = compiler(source, options);
        
    } catch (e) {
    
        e.filename = filename || 'anonymous';
        e.name = 'Syntax Error';

        return showDebugInfo(e);
        
    }
    
    
    // 对编译结果进行一次包装

    function render (data) {
        
        try {
            
            return new Render(data, filename) + '';
            
        } catch (e) {
            
            // 运行时出错后自动开启调试模式重新编译
            if (!options.debug) {
                options.debug = true;
                return compile(source, options)(data);
            }
            
            return showDebugInfo(e)();
            
        }
        
    }
    

    render.prototype = Render.prototype;
    render.toString = function () {
        return Render.toString();
    };


    if (filename && options.cache) {
        cacheStore[filename] = render;
    }

    
    return render;

};




// 数组迭代
var forEach = utils.$each;


// 静态分析模板变量
var KEYWORDS =
    // 关键字
    'break,case,catch,continue,debugger,default,delete,do,else,false'
    + ',finally,for,function,if,in,instanceof,new,null,return,switch,this'
    + ',throw,true,try,typeof,var,void,while,with'

    // 保留字
    + ',abstract,boolean,byte,char,class,const,double,enum,export,extends'
    + ',final,float,goto,implements,import,int,interface,long,native'
    + ',package,private,protected,public,short,static,super,synchronized'
    + ',throws,transient,volatile'

    // ECMA 5 - use strict
    + ',arguments,let,yield'

    + ',undefined';

var REMOVE_RE = /\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g;
var SPLIT_RE = /[^\w$]+/g;
var KEYWORDS_RE = new RegExp(["\\b" + KEYWORDS.replace(/,/g, '\\b|\\b') + "\\b"].join('|'), 'g');
var NUMBER_RE = /^\d[^,]*|,\d[^,]*/g;
var BOUNDARY_RE = /^,+|,+$/g;
var SPLIT2_RE = /^$|,+/;


// 获取变量
function getVariable (code) {
    return code
    .replace(REMOVE_RE, '')
    .replace(SPLIT_RE, ',')
    .replace(KEYWORDS_RE, '')
    .replace(NUMBER_RE, '')
    .replace(BOUNDARY_RE, '')
    .split(SPLIT2_RE);
};


// 字符串转义
function stringify (code) {
    return "'" + code
    // 单引号与反斜杠转义
    .replace(/('|\\)/g, '\\$1')
    // 换行符转义(windows + linux)
    .replace(/\r/g, '\\r')
    .replace(/\n/g, '\\n') + "'";
}


function compiler (source, options) {
    
    var debug = options.debug;
    var openTag = options.openTag;
    var closeTag = options.closeTag;
    var parser = options.parser;
    var compress = options.compress;
    var escape = options.escape;
    

    
    var line = 1;
    var uniq = {$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1};
    


    var isNewEngine = ''.trim;// '__proto__' in {}
    var replaces = isNewEngine
    ? ["$out='';", "$out+=", ";", "$out"]
    : ["$out=[];", "$out.push(", ");", "$out.join('')"];

    var concat = isNewEngine
        ? "$out+=text;return $out;"
        : "$out.push(text);";
          
    var print = "function(){"
    +      "var text=''.concat.apply('',arguments);"
    +       concat
    +  "}";

    var include = "function(filename,data){"
    +      "data=data||$data;"
    +      "var text=$utils.$include(filename,data,$filename);"
    +       concat
    +   "}";

    var headerCode = "'use strict';"
    + "var $utils=this,$helpers=$utils.$helpers,"
    + (debug ? "$line=0," : "");
    
    var mainCode = replaces[0];

    var footerCode = "return new String(" + replaces[3] + ");"
    
    // html与逻辑语法分离
    forEach(source.split(openTag), function (code) {
        code = code.split(closeTag);
        
        var $0 = code[0];
        var $1 = code[1];
        
        // code: [html]
        if (code.length === 1) {
            
            mainCode += html($0);
         
        // code: [logic, html]
        } else {
            
            mainCode += logic($0);
            
            if ($1) {
                mainCode += html($1);
            }
        }
        

    });
    
    var code = headerCode + mainCode + footerCode;
    
    // 调试语句
    if (debug) {
        code = "try{" + code + "}catch(e){"
        +       "throw {"
        +           "filename:$filename,"
        +           "name:'Render Error',"
        +           "message:e.message,"
        +           "line:$line,"
        +           "source:" + stringify(source)
        +           ".split(/\\n/)[$line-1].replace(/^\\s+/,'')"
        +       "};"
        + "}";
    }
    
    
    
    try {
        
        
        var Render = new Function("$data", "$filename", code);
        Render.prototype = utils;

        return Render;
        
    } catch (e) {
        e.temp = "function anonymous($data,$filename) {" + code + "}";
        throw e;
    }



    
    // 处理 HTML 语句
    function html (code) {
        
        // 记录行号
        line += code.split(/\n/).length - 1;

        // 压缩多余空白与注释
        if (compress) {
            code = code
            .replace(/\s+/g, ' ')
            .replace(/<!--[\w\W]*?-->/g, '');
        }
        
        if (code) {
            code = replaces[1] + stringify(code) + replaces[2] + "\n";
        }

        return code;
    }
    
    
    // 处理逻辑语句
    function logic (code) {

        var thisLine = line;
       
        if (parser) {
        
             // 语法转换插件钩子
            code = parser(code, options);
            
        } else if (debug) {
        
            // 记录行号
            code = code.replace(/\n/g, function () {
                line ++;
                return "$line=" + line +  ";";
            });
            
        }
        
        
        // 输出语句. 编码: <%=value%> 不编码:<%=#value%>
        // <%=#value%> 等同 v2.0.3 之前的 <%==value%>
        if (code.indexOf('=') === 0) {

            var escapeSyntax = escape && !/^=[=#]/.test(code);

            code = code.replace(/^=[=#]?|[\s;]*$/g, '');

            // 对内容编码
            if (escapeSyntax) {

                var name = code.replace(/\s*\([^\)]+\)/, '');

                // 排除 utils.* | include | print
                
                if (!utils[name] && !/^(include|print)$/.test(name)) {
                    code = "$escape(" + code + ")";
                }

            // 不编码
            } else {
                code = "$string(" + code + ")";
            }
            

            code = replaces[1] + code + replaces[2];

        }
        
        if (debug) {
            code = "$line=" + thisLine + ";" + code;
        }
        
        // 提取模板中的变量名
        forEach(getVariable(code), function (name) {
            
            // name 值可能为空，在安卓低版本浏览器下
            if (!name || uniq[name]) {
                return;
            }

            var value;

            // 声明模板变量
            // 赋值优先级:
            // [include, print] > utils > helpers > data
            if (name === 'print') {

                value = print;

            } else if (name === 'include') {
                
                value = include;
                
            } else if (utils[name]) {

                value = "$utils." + name;

            } else if (helpers[name]) {

                value = "$helpers." + name;

            } else {

                value = "$data." + name;
            }
            
            headerCode += name + "=" + value + ",";
            uniq[name] = true;
            
            
        });
        
        return code + "\n";
    }
    
    
};



// 定义模板引擎的语法


defaults.openTag = '{{';
defaults.closeTag = '}}';


var filtered = function (js, filter) {
    var parts = filter.split(':');
    var name = parts.shift();
    var args = parts.join(':') || '';

    if (args) {
        args = ', ' + args;
    }

    return '$helpers.' + name + '(' + js + args + ')';
}


defaults.parser = function (code, options) {

    // var match = code.match(/([\w\$]*)(\b.*)/);
    // var key = match[1];
    // var args = match[2];
    // var split = args.split(' ');
    // split.shift();

    code = code.replace(/^\s/, '');

    var split = code.split(' ');
    var key = split.shift();
    var args = split.join(' ');

    

    switch (key) {

        case 'if':

            code = 'if(' + args + '){';
            break;

        case 'else':
            
            if (split.shift() === 'if') {
                split = ' if(' + split.join(' ') + ')';
            } else {
                split = '';
            }

            code = '}else' + split + '{';
            break;

        case '/if':

            code = '}';
            break;

        case 'each':
            
            var object = split[0] || '$data';
            var as     = split[1] || 'as';
            var value  = split[2] || '$value';
            var index  = split[3] || '$index';
            
            var param   = value + ',' + index;
            
            if (as !== 'as') {
                object = '[]';
            }
            
            code =  '$each(' + object + ',function(' + param + '){';
            break;

        case '/each':

            code = '});';
            break;

        case 'echo':

            code = 'print(' + args + ');';
            break;

        case 'print':
        case 'include':

            code = key + '(' + split.join(',') + ');';
            break;

        default:

            // 过滤器（辅助方法）
            // {{value | filterA:'abcd' | filterB}}
            // >>> $helpers.filterB($helpers.filterA(value, 'abcd'))
            // TODO: {{ddd||aaa}} 不包含空格
            if (/^\s*\|\s*[\w\$]/.test(args)) {

                var escape = true;

                // {{#value | link}}
                if (code.indexOf('#') === 0) {
                    code = code.substr(1);
                    escape = false;
                }

                var i = 0;
                var array = code.split('|');
                var len = array.length;
                var val = array[i++];

                for (; i < len; i ++) {
                    val = filtered(val, array[i]);
                }

                code = (escape ? '=' : '=#') + val;

            // 即将弃用 {{helperName value}}
            } else if (template.helpers[key]) {
                
                code = '=#' + key + '(' + split.join(',') + ');';
            
            // 内容直接输出 {{value}}
            } else {

                code = '=' + code;
            }

            break;
    }
    
    
    return code;
};



// RequireJS && SeaJS
if (typeof define === 'function') {
    define(function() {
        return template;
    });

// NodeJS
} else if (typeof exports !== 'undefined') {
    module.exports = template;
} else {
    this.template = template;
}

})();