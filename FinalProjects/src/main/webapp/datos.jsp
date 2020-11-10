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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <title>Datos Subidos</title>
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
        <div>
            <form action ="Controlador" method = "POST" class="form-group">
                <div class="form-group">
                    <input type="submit" name ="accion" value="Regresar a Principal" class="btn btn-primary">
                </div>
            </form>
        </div>
    </body>
</html>
