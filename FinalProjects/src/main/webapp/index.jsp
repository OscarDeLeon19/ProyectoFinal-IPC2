<%-- 
    Document   : index
    Created on : 24/10/2020, 18:24:11
    Author     : oscar19
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="usuarios.Gerente"%>
<%@page import="acciones_usuarios.DM_Gerente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">        
        <script src="javascript/acciones.js"></script>
        <title>Inicio</title>
    </head>
    <body>
        <%
            DM_Gerente dmgen = new DM_Gerente();
            ArrayList<Gerente> lista = new ArrayList<>();
            lista = dmgen.verGerentes();
            if (lista.isEmpty()) {

        %>
        <div>
            <form action ="Controlador" method = "POST" class="form-group">
                <div class="form-group">
                    <label>Ingresar Ruta del archivo XML</label>
                    <input type = "input" name = "dataxml"  class="form-control">
                </div>
                <div class="form-group">
                    <input type="submit" name ="accion" value="Subir" class="btn btn-primary">
                </div>
            </form>
        </div>
        <%            }
        %>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
        <div>
            <h1>Login Cliente</h1>
            <form action ="Controlador" method = "POST" class="form-group">
                <div class="form-group">
                    <label>Codigo</label>
                    <input type = "text" name = "txcodigo"  class="form-control">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type = "password" name = "pass"  class="form-control">
                </div>
                <div class="form-group">
                    <input type="submit" name ="accion" value="Acceso Cliente" class="btn btn-primary">
                </div>
            </form>
            <div>
                <button class="btn btn-primary" onclick="salirCajero()()">Salir</button>
            </div>
        </div>
        <div>
            <h1>Login Gerente</h1>
            <form action ="Controlador" method = "POST" class="form-group">
                <div class="form-group">
                    <label>Codigo</label>
                    <input type = "text" name = "txcodigo"  class="form-control">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type = "password" name = "pass"  class="form-control">
                </div>
                <div class="form-group">
                    <input type="submit" name ="accion" value="Acceso Gerente" class="btn btn-primary">
                </div>
            </form>
            <div>
                <button class="btn btn-primary" onclick="salirGerente()()">Salir</button>
            </div>
        </div>
        <div>
            <h1>Login Cajero</h1>
            <form action ="Controlador" method = "POST" class="form-group">
                <div class="form-group">
                    <label>Codigo</label>
                    <input type = "text" name = "txcodigo"  class="form-control">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type = "password" name = "pass"  class="form-control">
                </div>
                <div class="form-group">
                    <input type="submit" name ="accion" value="Acceso Cajero" class="btn btn-primary">
                </div>
            </form>
            <div>
                <button class="btn btn-primary" onclick="salirCajero()()">Salir</button>
            </div>
        </div>
    </body>
</html>
