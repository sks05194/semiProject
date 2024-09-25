<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="javax.servlet.http.Cookie"%>

<%
    Cookie[] cookies = request.getCookies();
    String userName = null;

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("mem_info".equals(cookie.getName())) {
                String[] memInfo = cookie.getValue().split("\\+");
                if (memInfo.length > 1) {
                    userName = memInfo[1];
                }
                break;
            }
        }
    }
%>

<!-- 유저 정보 상단에 배치 -->
<div class="user-info">
    <% if (userName == null) { %>
        <a href="/BeansPaM/loginMenu.l">Login</a>
    <% } else { %>
        <a href="/BeansPaM/fms/mypage"><%= userName %>님</a>
        <a href="/BeansPaM/logout.l">로그아웃</a>
    <% } %>
</div>

<nav class="main-nav">
    <div class="nav-left">
        <div class="menu-logo">
            <img src="/BeansPaM/img/logo.png" alt="logo">
        </div>
        <!-- 메뉴 토글 버튼 (모바일) -->
        <span class="menu-toggle">&#9776;</span>
        <ul class="main-menu">
            <% if (userName != null) { %>
                <% if ("관리자".equals(userName)) { %>
                    <li><a href="/BeansPaM/fms/admin">관리자 메뉴</a></li>
                <% } %>
            	<li><a href="/BeansPaM/fms/inform">사원검색</a></li>
                <li class="has-submenu">
                    <span class="menu-title">ERP</span>
                    <ul class="sub-menu">
                        <li><a href="#">시설관리</a></li>
                        <li><a href="/BeansPaM/fms/vmi">자재관리</a></li>
                    </ul>
                </li>
                <li class="has-submenu">
                    <span class="menu-title">전자결재</span>
                    <ul class="sub-menu">
                        <li><a href="#">결재상신</a></li>
                        <li><a href="/BeansPaM/fms/report">결재문서</a></li>
                    </ul>
                </li>
            <% } %>
            <!-- 로그인 여부와 관계없이 항상 표시되는 게시판 메뉴 -->
            <li class="has-submenu">
                <span class="menu-title">게시판</span>
                <ul class="sub-menu">
                    <li><a href="/BeansPaM/b/notice">공지사항</a></li>
                    <li><a href="/BeansPaM/b/qna">Q&amp;A</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
