<%-- 
    Document   : datos
    Created on : 24/10/2020, 18:29:30
    Author     : oscar19
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Datos subidos</h1>
        <div>
            <% ArrayList<String> lista = (ArrayList<String>) request.getAttribute("lista");
                for (int i = 0; i < lista.size(); i++) {
                    out.println(lista.get(i)); %><br><%                    
                }
            %>
        </div>
    </body>
</html>
