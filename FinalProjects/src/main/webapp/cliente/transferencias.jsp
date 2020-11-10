<%-- 
    Document   : transferencias
    Created on : 5/11/2020, 00:04:28
    Author     : oscar
--%>

<%@page import="servicios.Cuenta"%>
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
        <title>Cuentas Asociadas</title>
    </head>
    <body>
        <nav class="navbar bg-info">
            <div>
                <a class="nav-item   btn btn-outline-light" href="Controlador?accion=PrimeroL">Inicio</a>
                <a class="nav-item active  btn btn-outline-light" href="Controlador?accion=FuncionesL">Funciones</a>
                <a class="nav-item  btn btn-outline-light" href="Controlador?accion=ReportesL">Reportes</a>
                <a class="nav-item btn btn-outline-light" href="Controlador?accion=CambiarHoraL">${hora}:00</a> 
            </div>
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Usuario Ingresado
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#">Tipo: Cliente</a>
                    <a class="dropdown-item" href="#">${login_cliente.getCodigo()}</a>
                    <a class="dropdown-item" href="#">${login_cliente.getNombre()}</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="Controlador?accion=Salir">Salir</a>      
                </div>
            </div>
        </nav>
        <%
            ArrayList<Cuenta> lista = (ArrayList<Cuenta>) request.getSession().getAttribute("cuentas");
        %>
        <div style="width: 1000px; border: 1px solid black" class="container h-100">
            <h1>Cuentas asociadas del cliente</h1>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Codigo de la cuenta</th>
                        <th scope="col">Fecha de creacion</th> 
                        <th scope="col">Credito</th> 
                        <th scope="col">Codigo de la cuenta</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (lista.size() > 0) {
                            for (int i = 0; i < lista.size(); i++) {
                                Cuenta cuenta = lista.get(i);
                    %>
                    <tr>
                        <td><%= cuenta.getCodigo()%></td>
                        <td><%= cuenta.getCreacion()%></td>
                        <td><%= cuenta.getCredito()%></td>
                        <td><%= cuenta.getCodigo_cliente()%></td>
                        <td><a href="FuncionesCliente?accion=Trasferir&id=<%= cuenta.getCodigo()%>">Transferir </a></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
