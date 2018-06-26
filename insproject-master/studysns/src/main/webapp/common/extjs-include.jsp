<%@page language="java" contentType="text/html; charset=utf-8"%>
<%
//EXTJS全局样式
Object EXT_STYPE = session.getAttribute("EXT_STYLE");	
if(EXT_STYPE == null || "".equals(EXT_STYPE)){
	EXT_STYPE = "crisp";
}
pageContext.setAttribute("EXT_STYLE", EXT_STYPE);
%>
<!-- extjs -->
<link href="${STATIC_PATH}/common/lib/extjs/5.1.1/packages/ext-theme-${EXT_STYLE}/build/resources/ext-theme-${EXT_STYLE}-all.css" rel="stylesheet" type="text/css"/>
<script src="${STATIC_PATH}/common/lib/extjs/5.1.1/ext-all${IS_DEV_MODEL eq 'Y' ? '-debug' : ''}.js"></script>
<script src="${STATIC_PATH}/common/lib/extjs/5.1.1/packages/ext-theme-${EXT_STYLE}/build/ext-theme-${EXT_STYLE}.js"></script>
<script src="${STATIC_PATH}/common/lib/extjs/5.1.1/packages/ext-locale/build/ext-locale-${LOCALE}.js"></script>
<script src="${STATIC_PATH}/common/lib/ins-lib/ins-component-${LOCALE}${IS_DEV_MODEL eq 'Y' ? '' : '.min'}.js"></script>
<script type="text/javascript">
EXT_STYLE = '${EXT_STYLE}';
</script>