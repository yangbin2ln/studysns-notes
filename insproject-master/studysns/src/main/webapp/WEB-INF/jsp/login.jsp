<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/common/common-include.jsp"%>
<title><spring:message code="login.pageTitle"/></title>
<link rel="stylesheet" href="${CURRENT_STATIC_PATH}/css/login.css">
</head>
<body>
<div class="p-login">
    <div class="header">
        <div class="wrap clearfix">
            <div class="logo"><a href=""><img src="${CURRENT_STATIC_PATH}/image/login/logo.png" alt="" style="max-height: 40px"/></a></div>
        </div>
    </div>
    <div class="body">
        <div class="wrap clearfix">
            <div class="login-logo"></div>
            <div class="login-area">
                <div class="title"><spring:message code="login.title"/></div>
                <div class="login tang-pass-login">                   
                        <div class="pass-generalErrorWrapper" style="visibility: visible;">
                            <span id="invalidIcon" class="pass-generalError pass-generalError-error" style="display:none"></span>
                            <span id="invalidMessage"></span>
                        </div>
                        <div class="pass-form-item pass-form-item-userName">
                            <label class="pass-label pass-label-userName"></label>
                            <input type="text" name="l_userName" id="l_userName" class="pass-text-input pass-text-input-userName open"
                                   autocomplete="off" placeholder="<spring:message code="login.account"/>">
                            <span class="pass-clearbtn pass-clearbtn-userName"></span>
                        </div>
                        <div class="pass-form-item pass-form-item-password" id='passwordDiv'>
                            <label class="pass-label pass-text-input-password"></label>
                            <input type="text" name="l_password" id="l_password" class="pass-text-input pass-text-input-userName open"
                                   autocomplete="off" placeholder="<spring:message code="login.password"/>">
                            <span class="pass-clearbtn pass-clearbtn-userName"></span>
                        </div>
                        <div class="pass-form-item pass-form-item-verifyCode clearfix">
                            <input type="text" name="captcha" id="captcha" class="pass-text-input pass-text-input-verifyCode fl"
                                   maxlength="6" placeholder="<spring:message code="login.captcha"/>">
                            <span class="pass-clearbtn pass-clearbtn-verifyCode"></span>
                            <span class="pass-verifyCodeImgParent">
                                <img id="captchaImg" class="pass-verifyCode fl"
                                     src="${CTX_PATH}/captcha/generate?type=SYSTEM_LOGIN_CAPTCHA&t=<%=System.nanoTime()%>"/>
                            </span>
                            <a href="javascript:refreshCaptcha();" class="pass-change-verifyCode">换一张</a>
                        </div>
                        <div class="pass-form-item pass-form-item-submit">
                            <input id="loginBtn" type="button" value="<spring:message code="login.enterBtn"/>" class="pass-button pass-button-submit">
                        </div>                  
                </div>
            </div>
        </div>
    </div>
    <div class="w-footer-mini">
        <div class="wrap">
            <div class="site-info">
                <span class="copyright">Copyright © 2017 Westerasoft Inc. All Rights Reserved.</span>
            </div>
        </div>
    </div>
</div>
<script src="${STATIC_PATH}/common/lib/jquery/plugins/jquery.placeholder.min.js"></script>
<script type="text/javascript">
$(function(){
    $('input').placeholder();
    
    $("#l_userName").focus();
	$("#l_password").focus(function() {
		if (ltAndIE8()) {
			$(this).remove();
			var placeholder = '<spring:message code="login.password" />';
			/* $("#passwordDiv")
					.append("<input name='l_password' id='l_password' type='password' placeholder='"+placeholder+"' autocomplete='off'>"); */
			$("#passwordDiv").append("<input type=\"password\" name=\"l_password\" id=\"l_password\" class=\"pass-text-input pass-text-input-userName open\"\n" +
							"                                   autocomplete=\"off\" placeholder=\""+placeholder+"\">");
			$("#l_password").focus()
		} else {
			$(this).attr("type", "password")
		}
	});
	$("#captchaImg").click(function() {
		refreshCaptcha();
	});
	document.onkeydown = function(e) {
		var ev = (typeof event != "undefined") ? window.event : e;
		if (ev.keyCode == 13) {
			$("#loginBtn").click()
		}
	};
	$("#loginBtn").click(function() {
				if(ltAndIE8()){
					alert('当前浏览器版本过低，请升级!');
					return false;
				}
				if ($("#l_userName").val() == "") {
					$("#invalidMessage").html('<spring:message code="login.msg.emptyAccount"/>');
					$("#l_userName").focus();
					$("#invalidIcon").show();
					return
				}
				if ($("#l_password").val() == "") {
					$("#invalidMessage").html('<spring:message code="login.msg.emptyPassword"/>');
					$("#l_password").focus();
					$("#invalidIcon").show();
					return
				}
				if ($("#captcha").val() == "") {
					$("#invalidMessage").html('<spring:message code="login.msg.emptyCaptcha"/>');
					$("#captcha").focus();
					$("#invalidIcon").show();
					return
				}
				doLogin()
			});
	function doLogin() {
		$.ajax({
					type : "POST",
					url : CTX_PATH + "/login/enter",
					dataType : "json",
					cache : false,
					data : {
						userName : $("#l_userName").val(),
						password : $("#l_password").val(),
						captcha : $("#captcha").val()
					},
					beforeSend : function() {
						$("#loginBtn").attr("disabled", "disabled")
					},
					success : function(data) {
						if (data.result == "CAPTCHA_ERROR") {
							$("#invalidMessage").html('<spring:message code="login.msg.errorCaptcha"/>');
							$("#captcha").focus();
							$("#invalidIcon").show();
							$("#loginBtn").removeAttr("disabled");
							//refreshCaptcha();
							return
						} else {
							if (data.result == "ACCOUNT_ERROR") { 
								$("#invalidMessage").html('<spring:message code="login.msg.errorAccountOrPassword"/>');
								$("#l_userName").focus();
								$("#invalidIcon").show();
								$("#loginBtn").removeAttr("disabled");
								//refreshCaptcha();
								return;
							} else {
								if (data.result == "ACCOUNT_LOCKED") {
									$("#invalidMessage").html('<spring:message code="login.msg.lock"/>');
									$("#l_userName").focus();
									$("#invalidIcon").show();
									$("#loginBtn").removeAttr("disabled");
									//refreshCaptcha();
									return;
								} else {
									if (data.result == "ACCOUNT_SUCCESS") {
										$("#invalidIcon").hide();
										$("#invalidMessage").html('');
										location.href = CTX_PATH + "/main"
									}
								}
							}
						}
					}
				})
	}
});

function changeLocale(type){	
	$.get(CTX_PATH + '/locale/change/' + type +'?t='+ new Date().getTime(), function(){
		location.reload();
	});	
}
function refreshCaptcha(){
	$("#captchaImg").attr("src",
			CTX_PATH + "/captcha/generate?type=SYSTEM_LOGIN_CAPTCHA&t=" + new Date().getTime())
} 
function ltAndIE8() {
	var DEFAULT_VERSION = "8.0";
	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("msie") > -1;
	var safariVersion;
	if (isIE) {
		safariVersion = ua.match(/msie ([\d.]+)/)[1];
		if (safariVersion <= DEFAULT_VERSION) {
			return true
		} else {
			return false
		}
	}
	return false
};
</script>
</body>
</html>