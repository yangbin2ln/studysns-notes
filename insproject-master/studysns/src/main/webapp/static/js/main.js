SNS.component('yes-knowledge', function(){
	var id = '_v' + new Date().getTime();
	return `<div class="panel panel-default resolve-knowledge">
			  <div sns-mouseover="showDeleteIcon" sns-mouseout="hideDeleteIcon">
			  	<div class="panel-body">
		  			<div class='form-group'>
						<label class="control-label">知识点名称</label>
					    <input class="form-control" name="name">
					</div>
		  		 
		  			<div>
				    	<label>讲解程度</label>
				  	</div>					  
				  	<div class="form-group">
				    	<label class="radio-inline">
						  <input type="radio" name="explainDegree${id}" value="0" checked=checked>重点讲解
						</label>
						<label class="radio-inline">
						  <input type="radio" name="explainDegree${id}" value="1">提了一嘴
						</label>
				  	</div>
				  </div>
				  <i class="icon-minus sns-abs" sns-click="deleteKnowledge"></i>
			  </div>
			</div>`
});

SNS.component('no-knowledge', function(){
	var id = '_v' + new Date().getTime();
	return `<div class="panel panel-default noresolve-knowledge">
			  <div sns-mouseover="showDeleteIcon" sns-mouseout="hideDeleteIcon">
			  	<div class="panel-body">
		  			<div class='form-group'>
						<label class="control-label">描述</label>
					    <input class="form-control" name="description">
					</div>
		  		 	
		  		 	<div class='form-group'>
						<label class="control-label">知识点名称</label>
					    <input class="form-control" name="name">
					</div>
					
					<div class='form-group'>
						<label class="control-label">推荐书籍</label>
					    <input class="form-control" name="bookName">
					</div>
		  			
				  </div>
				  <i class="icon-minus sns-abs" sns-click="deleteKnowledge"></i>
			  </div>
			</div>`
});

SNS.component('flow-knowledge', function(){
	var id = '_v' + new Date().getTime();
	return `<div class="panel panel-default flow-knowledge">
				<div sns-mouseover="showDeleteIcon" sns-mouseout="hideDeleteIcon">
					<div class="panel-body">
						<div class='form-group'>
							<label class="control-label">标题</label>
						    <input name="title" class="form-control" placeholder="流程性的知识点本质是指具有顺序性、连接性的元素的集合。尽快将他们从复杂的笔记中抽取出来吧">
						</div>
						<div class='form-group'>
							<label class="control-label">描述</label>
						    <textarea class="form-control" id="exampleInputEmail1" name="description"> </textarea>
						</div>
					</div>
					<i class="icon-minus sns-abs" sns-click="deleteKnowledge"></i>
				</div>
			</div>`
});



var sns = new SNS(document);
sns.scope.addKnowledge = function(target){
	var nk = $("<yes-knowledge></yes-knowledge>");
	$(target).before(nk);
	
	sns.compile(nk.get(0));
	
	$(document).scrollTop($(document).scrollTop()+$('.resolve-knowledge').outerHeight() + 22);
	
}

sns.scope.addBlindKnowledge = function(target){
	var nk = $("<no-knowledge></no-knowledge>");
	$(target).before(nk);
	
	sns.compile(nk.get(0));
	
	$(document).scrollTop($(document).scrollTop()+$('.noresolve-knowledge').height() + 40);
	
}

sns.scope.addFLowKnowledge = function(target){
	var nk = $("<flow-knowledge></flow-knowledge>");
	$(target).before(nk);
	
	sns.compile(nk.get(0));
	
	$(document).scrollTop($(document).scrollTop()+$('.flow-knowledge').height() + 40);
}

sns.scope.deleteKnowledge = function(target){
	$(target).closest('.panel').fadeOut("fast", function(){
		$(this).remove();
	});
}

sns.scope.showDeleteIcon = function(target){
	$(target).find('.icon-minus').show();
}

sns.scope.hideDeleteIcon = function(target){
	$(target).find('.icon-minus').hide();
}

initUE();
initEvent();

//实例化ue
function initUE(){
	var config = {
			elementPathEnabled: false,
			wordCount: false,
			autoFloatEnabled: true,
			autoHeightEnabled: false,		
			enableContextMenu: false,
			enableAutoSave: false,	
			initialFrameWidth: '100%',
			initialFrameHeight: 900,
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
        var ueditorInstance = UE.getEditor('nodes-box', config);
        
        sns.scope.ueditorInstance = ueditorInstance;
        
}

sns.scope.submitNotes = function(){
	var obj = {
			yesKnowledgeList: [],
			noKnowledgeList: [],
			flowKnowledgeList: [],
			notesDetails: {}
	}
	
	var data = getValues('#notes-form');
	$.extend(obj.notesDetails, data);
	obj.notesDetails.title = $('[name=notesTitle]').val();
	
	var $resolveKnowledges = $('.resolve-knowledge');
	$resolveKnowledges.each(function(index, ele){
		obj.yesKnowledgeList.push(getValues(ele));
	})

	var $noKnowledges = $('.noresolve-knowledge');
	$noKnowledges.each(function(index, ele){
		obj.noKnowledgeList.push(getValues(ele));
	})
	
	var $flowKnowledges = $('.flow-knowledge');
	$flowKnowledges.each(function(index, ele){
		obj.flowKnowledgeList.push(getValues(ele));
	})
	
	
	
	var content = this.ueditorInstance.getContent();
	
	obj.content = content;
	
	$.post(CTX_PATH + '/notes/add', {obj: JSON.stringify(obj)}, function(res){
		res = JSON.parse(res);
		if(res.success){
			alert('保存成功')
		}
	});
	
}

sns.scope.taggleDetails = function(){
	var  $dom = $('#notes-details');
	if($dom.is(':hidden')){
		$dom.show();
	}else{
		$dom.hide();
	}
	
}


function getValues(sel){
	var data = {};
	
	var $inputs = $(sel).find('input,textarea');
	$inputs.each(function(index, ele){
		var $ele = $(ele);
		var name = $ele.attr('name');
		if(name.startsWith('explainDegree')){
			name = 'explainDegree';
		}
		
		var type = $ele.prop('type');
		if(type == 'text'){
			data[name] = $ele.val();
		}else if(type == 'radio'){
			if($ele.is(':checked')){
				data[name] = $ele.val();
			}
		}else{
			data[name] = $ele.val();
		}
	});
	
	return data;
}

function initEvent(){
	$(document).keydown(function (event){
      if (event.keyCode == 81 && event.altKey) {  //alt+q 
    	  sns.scope.taggleDetails();
        }
   });
}
