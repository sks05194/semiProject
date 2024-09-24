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
        <a href="loginMenu.l">로그인</a>
    <% } else { %>
        <a href="fms/mypage"><%= userName %>님</a>
        <a href="logout.l">로그아웃</a>
    <% } %>
</div>

<nav class="main-nav">
    <div class="nav-left">
        <div class="logo">
            <img src="/BeansPaM/img/logo.png" alt="logo">
        </div>
        <!-- 메뉴 토글 버튼 (모바일) -->
        <span class="menu-toggle">&#9776;</span>
        <ul class="main-menu">
            <% if (userName != null) { %>
                <% if ("관리자".equals(userName)) { %>
                    <li><a href="fms/admin">관리자 메뉴</a></li>
                <% } %>
                <li><a href="fms/inform">사원검색</a></li>
                <li class="has-submenu">
                    <span class="menu-title">ERP</span>
                </li>
                <li class="has-submenu">
                    <span class="menu-title">전자결재</span>
                </li>
            <% } %>
            <!-- 로그인 여부와 관계없이 항상 표시되는 게시판 메뉴 -->
            <li class="has-submenu">
                <span class="menu-title">게시판</span>
            </li>
        </ul>
    </div>
    <!-- 서브 메뉴 컨테이너 -->
    <div class="sub-menus">
        <% if (userName != null) { %>
            <!-- ERP 서브 메뉴 -->
            <ul class="sub-menu">
                <li><a href="#">시설관리</a></li>
                <li><a href="fms/vmi">자재관리</a></li>
            </ul>
            <!-- 전자결재 서브 메뉴 -->
            <ul class="sub-menu">
                <li><a href="#">결재상신</a></li>
                <li><a href="fms/report">결재문서</a></li>
            </ul>
        <% } %>
        <!-- 게시판 서브 메뉴 -->
        <ul class="sub-menu">
            <li><a href="b/notice">공지사항</a></li>
            <li><a href="b/qna">Q&A</a></li>
        </ul>
    </div>
</nav>