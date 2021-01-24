<%@ page import="ru.itis.javalab.models.Student" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: almaz
  Date: 25.10.2020
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> </title>
</head>
<body>
<div>
    <h1 style="color: ${cookie.get("color").value}">STUDENTS</h1>
    <form action="" method="post">
        <select name="color">
            <option value="red">RED</option>
            <option value="green">GREEN</option>
            <option value="blue">BLUE</option>
        </select>
        <input type="submit" value="OK">
    </form>
</div>

<div>
    <table>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        <%
            List<Student> students = (List<Student>) request.getAttribute("studentsForJsp");
            for (Student student : students) {
        %>
        <tr>
            <td><%=student.getFirstName()%></>
            <td><%=student.getLastName()%></>
        </tr>
        <%}%>
    </table>
</div>

</body>
</html>
