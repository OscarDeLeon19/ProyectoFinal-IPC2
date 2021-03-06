<%-- 
    Document   : reportes
    Created on : 27/10/2020, 12:51:39
    Author     : oscar19
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <title>Reportes Gerente</title>
    </head>
    <body>
        <nav class="navbar bg-info">
            <div>
                <a class="nav-item btn btn-outline-light" href="Controlador?accion=PrimeroG">Inicio</a>
                <a class="nav-item  btn btn-outline-light" href="Controlador?accion=FuncionesG"m>Funciones</a>
                <a class="nav-item active btn btn-outline-light" href="">Reportes</a>
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
        <% String limite2 = (String) request.getSession().getAttribute("limite2"); %>
        <div style="width: 1000px; border: 1px solid black; padding: 50px" class="container h-100">
            <h1>Reportes que puede realizar el gerente</h1>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=VerHistorial">Ver Historial de cambios</a><br>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=ClientesDinero">Los 10 clientes con mas dinero</a><br>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=ClientesSinTransacciones">Clientes sin transacciones</a><br>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=HistorialCliente">Historial de transacciones de un cliente</a><br>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=TransaccionesCajero">Cajeros que mas transacciones han realizado</a><br>
            <%
                if (limite2 != null) {
            %>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=VerTransacciones"> Ver Transacciones</a><br>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesGerente?accion=VerSumadas"> Ver Transacciones Sumadas</a><br>
            <%
            } else {
            %>
            <a class="btn btn-secondary btn-lg btn-block"> Ver Transacciones</a><br>
            <a class="btn btn-secondary btn-lg btn-block"> Ver Transacciones Sumadas</a><br>
            <%
                }

            %>
        </div>
    </body>
</html>
