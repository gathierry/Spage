<html>

    <%@ page import="java.util.List"%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
    <head lang="en">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.js"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <style>body{padding-left:30px;padding-right:30px;padding-top:150px;padding-bottom:40px;}</style>
        <title>Spage</title>
    </head>
    <body>
        <center>
            <div>
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Sujet</th>
                            <th>Entreprise</th>
                            <th>Référence</th>
                        </tr>
                    </thead>
                    <tbody>
                         <c:forEach var="post" items="${posts}">
                            <tr>
                              <td id="title">
                                <a href=${post.link} target="_blank">
                                    ${post.title}
                                </a>
                              </td>
                              <td id="enterprise">${post.enterprise}</td>
                              <td id="reference">${post.reference}</td>
                            </tr>
                         </c:forEach>
                    </tbody>
                </table>
            <div>
        </center>
    </body>
</html>