<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<o:header title="Log In"/>
<link rel="stylesheet" href="static/css/app.css" rel="stylesheet">

<script type="text/javascript">
    <!--

    $(document).ready(function () {
        // select the appropriate field based on context
        $('#<c:out value="${ login_hint != null ? 'j_password' : 'j_username' }" />').focus();
    });

    //-->
</script>
<%--<o:topbar/>--%>
<div class="container-fluid full-screen login-form">

    <c:if test="${ param.error != null }">
        <div class="alert alert-error"><spring:message code="login.error"/></div>
    </c:if>

    <div class="row-fluid">
        <div class="span4 offset4">
            <form action="<%=request.getContextPath()%>/j_spring_security_check"
                  method="POST">
                <div>
                    <img src="static/images/company-logo.png"/>
                </div>
                <div>
                    <div class="input-prepend input-block-level">
                        <span class="add-on"><i class="icon-user"></i></span>
                        <input type="text" placeholder="Email Address" autocorrect="off" autocapitalize="off" required
                               autocomplete="off" spellcheck="false" value="<c:out value="${ login_hint }" />"
                               id="j_username" name="j_username">
                    </div>
                </div>
                <div>
                    <div class="input-prepend input-block-level">
                        <span class="add-on"><i class="icon-lock"></i></span>
                        <input type="password" placeholder="Password" autocorrect="off" autocapitalize="off" required
                               autocomplete="off" spellcheck="false" id="j_password" name="j_password">
                    </div>
                </div>
                <div>
                    <input type="submit" class="btn-block login-form-btn" value="Sign in" name="submit">
                </div>
                <div>
					<span class="help-block pull-left">
                        <spring:eval var="newUserUrl" expression="@ldapProperties.newUserUrl"/>
                        <c:if test="${not empty fn:trim(newUserUrl)}">
                            <a href="${newUserUrl}" name="register_new_account"
                               target="_self" class="login-form-text">Create New Account</a>
                        </c:if>
     				</span>
                    <span class="help-block pull-right">
                        <spring:eval var="forgotPasswordUrl" expression="@ldapProperties.forgotPasswordUrl"/>
                        <c:if test="${not empty fn:trim(forgotPasswordUrl)}">
                            <a href="${forgotPasswordUrl}" name="forgot_password"
                               target="_self" class="login-form-text">Forgot Password?</a>
                        </c:if>
                    </span>
                </div>
            </form>
        </div>
        <!-- a little space -->
        <div class="span12" style="height:50px;"></div>
    </div>
</div>

