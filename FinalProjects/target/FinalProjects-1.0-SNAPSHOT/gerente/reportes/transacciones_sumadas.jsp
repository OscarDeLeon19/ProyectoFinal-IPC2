<%-- 
    Document   : transacciones_sumadas
    Created on : 2/11/2020, 17:03:18
    Author     : oscar
--%>

<%@page import="servicios.Transaccion"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Transacciones sumadas mayores al limite</title>
    </head>
    <body>
        <nav class="navbar bg-info">
            <div>
                <a class="nav-item btn btn-outline-light" href="Controlador?accion=PrimeroG">Inicio</a>
                <a class="nav-item  btn btn-outline-light" href="Controlador?accion=FuncionesG"m>Funciones</a>
                <a class="nav-item active btn btn-outline-light" href="Controlador?accion=ReportesG">Reportes</a>
                <a class="nav-item btn btn-outline-light" href="Controlador?accion=CambiarHora">${hora}:00</a> 
            </div>
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Usuario Ingresado
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#">Tipo: Gerente</a>
                    <a class="dropdown-item" href="#">${login_gerente.getCodigo()}</a>
                    <a class="dropdown-item" href="#">${login_gerente.getNombre()}</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="Controlador?accion=Salir">Salir</a>      
                </div>
            </div>
        </nav>
        <%
            ArrayList<Transaccion> lista = (ArrayList<Transaccion>) request.getSession().getAttribute("transacciones_sumadas");
        %> 
        <div style="width: 1000px; border: 1px solid black" class="container h-100">
            <h2>Transacciones sumadas en cada cuenta</h2>
            <h2>Limite establecido: Q${limite2}</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Transacciones Sumadas</th>
                        <th scope="col">Codigo de la cuenta</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (lista.size() > 0) {
                            for (int i = 0; i < lista.size(); i++) {
                                Transaccion transaccion = lista.get(i);
                    %>
                    <tr>
                        <td><%= transaccion.getMonto()%></td>
                        <td><%= transaccion.getCodigo_cuenta()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
            <form method="GET" action="Reporte3G">
                <input type="submit" class="btn btn-primary" value="Exportar"/>
            </form>
        </div>
    </body>
</html>
