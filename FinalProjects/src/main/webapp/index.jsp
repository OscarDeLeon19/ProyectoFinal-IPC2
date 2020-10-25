<%-- 
    Document   : index
    Created on : 24/10/2020, 18:24:11
    Author     : oscar19
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div><!-- El formulario para el login de el paciente -->
            <h1>Login Paciente</h1>
            <form action ="Controlador" method = "POST" class="form-group">
                <div class="form-group">
                    <label>Codigo</label>
                    <input type = "input" name = "dataxml"  class="form-control">
                </div>
                <div class="form-group">
                    <input type="submit" name ="accion" value="Subir" class="btn btn-primary">
                </div>
            </form>
        </div>
    </body>
</html>
