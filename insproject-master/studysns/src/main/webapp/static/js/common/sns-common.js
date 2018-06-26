(function(win, $, document){
	var _SNS = function(clazz){
		this.scope = {};
		this.compile(clazz);
		this.addEvent(clazz);
	}
	
	var consistantEvent = {
			'sns-click': 'click',
			'sns-mouseover': 'mouseover',
			'sns-mouseout': 'mouseout',
	}
	
	var pro = _SNS.prototype = {};
	
	pro.compile = function(clazz){
		var me = this;
		if(!clazz){
			return;
		}
		me.parseComponent(clazz);
	}
	
	pro.component = function(key, value){
		SNS.componentList[key] = value;
	}
	
	pro.parseComponent = function(clazz){
		var me = this;
		 
		for(key in SNS.componentList){
			var html = SNS.componentList[key]();
			var components = $(clazz).find(key);
			if($(clazz).get(0).localName == key){
				components.push($(clazz).get(0));
			}
			
			for(var i=0;i<components.length;i++){
				var $dom = $(html);
				components.eq(i).after($dom).end().remove();
				this.addEvent($dom.get(0));
			}
		}
		
	}
	
	pro.addEvent = function(clazz){
		var me = this;
		
		for(key in consistantEvent){
			var arr = $(clazz).find('['+key+']');
			if($(clazz).attr(key)){
				arr.add($(clazz));
			}
			
			for(var i=0;i<arr.length;i++){
				var obj = arr.eq(i);
				var clickFun = obj.attr(key);
				var evalEvent = "me.scope['"+clickFun+"'](";
				
				var reg = /\((.*)\)/;
				var regVal = clickFun.match(reg);
				var hasParams = false;
				if(regVal != null){
					clickFun = clickFun.substring(0, clickFun.indexOf('('));
					evalEvent = "me.scope['"+clickFun+"'](";
					
					var fStr = regVal[1];
					var fArr = fStr.split(',');
					fArr.forEach(function(ele){
						evalEvent += ele + ",";
					})
					if(fArr.length > 0){
						evalEvent = evalEvent.substring(0, evalEvent.length-1);
						hasParams = true;
					}
				}
				
				(function(me, hasParams, evalEvent){
					obj.on(consistantEvent[key], function(){
						var target = this;
						var _evalEvent;
						if(hasParams){
							_evalEvent = evalEvent + ",target)"
						}else{
							_evalEvent = evalEvent + "target)"
						}
						
						eval(_evalEvent);
					});
				})(me, hasParams, evalEvent);
			}	
		}
		
	}
	
	var SNS = function(clazz){
		_SNS.call(this, clazz);
	};
	win.SNS = SNS;
	SNS.componentList = {};
	SNS.component = pro.component;
	SNS.prototype = new _SNS();

	//重新post请求
	var originalPost = $.post;
	$.post = function(url,data,callBack){
		originalPost.call($,url,data,callBack).fail(function(){
			alert('请求失败')
		});
	}
	
})(window, $, document);

