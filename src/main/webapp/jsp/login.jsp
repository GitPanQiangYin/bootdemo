<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/4/7
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>新增论文</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    基于SSM框架的管理系统：简单实现增、删、改、查。
                </h1>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增论文</small>
                </h1>
            </div>
        </div>
    </div>

        <form  class="form-signin" action="/login/form" method="post" >
            <h2 class="form-signin-heading">用户登录</h2>
            <table align="center">
                <tr>
                    <td>用户名:</td>
                    <td><input type="text" name="username"  class="form-control"  placeholder="请输入用户名"/></td>
                </tr>
                <tr>
                    <td> &nbsp;&nbsp;</td>
                    <td>&nbsp;&nbsp; </td>
                </tr>
                <tr>
                    <td>密码:</td>
                    <td><input type="password" name="password"  class="form-control" placeholder="请输入密码" /></td>
                </tr>
                <tr>

                    <td colspan="2">
                        <button type="submit"  class="btn btn-lg btn-primary btn-block" >登录</button>
                    </td>
                </tr>
            </table>
        </form>



    <script type="text/javascript">
        function addPaper() {
            var form = document.forms[0];
            form.action = "<%=basePath %>paper/addPaper";
            form.method = "post";
            form.submit();
        }
    </script>
</div>