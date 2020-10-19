<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<spring:message code="error.title" var="title"/>
<o:header title="${title}" />
<o:topbar pageName="Error" />
<div class="container-fluid main">
    <div class="row-fluid">
        <div class="offset1 span10">
            <div class="hero-unit">
                <h1><span>Server Error</span>
                    <span class="text-error">Error Detected</span>
                </h1>
                <p>
                <blockquote class="text-error">Check server error logs</blockquote>
                </p>

            </div>

        </div>
    </div>
</div>
<o:footer />
