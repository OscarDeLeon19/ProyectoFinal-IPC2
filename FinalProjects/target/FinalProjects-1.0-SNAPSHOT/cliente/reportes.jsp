<%-- 
    Document   : reportes
    Created on : 4/11/2020, 17:47:01
    Author     : oscar
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
        <title>Reportes Cliente</title>
    </head>
    <body>
        <nav class="navbar bg-info">
            <div>
                <a class="nav-item   btn btn-outline-light" href="Controlador?accion=PrimeroL">Inicio</a>
                <a class="nav-item  btn btn-outline-light" href="Controlador?accion=FuncionesL">Funciones</a>
                <a class="nav-item active btn btn-outline-light" href="Controlador?accion=ReportesL">Reportes</a>
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
        <div style="width: 1000px; border: 1px solid black; padding: 50px" class="container h-100">
            <h1>Reportes del Cliente</h1>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesCliente?accion=Ultima">Las ultimas 15 transacciones mas grandes realizadas</a><br> 
            <a class="btn btn-primary btn-lg btn-block" href="ReportesCliente?accion=Transacciones">Ver Transacciones en intervalo de tiempo</a><br> 
            <a  class="btn btn-primary btn-lg btn-block" href="ReportesCliente?accion=VerCuenta">Cuentas con mas dinero</a><br> 
            <a class="btn btn-primary btn-lg btn-block" href="ReportesCliente?accion=Realizadas">Historial de solicitudes realizadas</a><br>
            <a class="btn btn-primary btn-lg btn-block" href="ReportesCliente?accion=Recibidas">Historial de solicitudes recibidas</a><br>
        </div>
    </body>
</html>
