<%-- 
    Document   : modificar_cajero
    Created on : 27/10/2020, 19:58:15
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
        <script src="gerente/accionesJS/funciones.js"></script>
        <title>Interfaz Gerente</title>
    </head>
    <body>
        <nav class="navbar bg-info">
            <div>
                <a class="nav-item btn btn-outline-light" href="Controlador?accion=PrimeroG">Inicio</a>
                <a class="nav-item active btn btn-outline-light" href="Controlador?accion=FuncionesG"m>Funciones</a>
                <a class="nav-item btn btn-outline-light" href="Controlador?accion=ReportesG">Reportes</a>
                <a hidden class="nav-item btn btn-outline-light" href="#">${login_gerente.getTurno()}</a>
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
        <div>
            <p class ="alert alert-primary">${error}</p>
        </div>            
        <div style="width: 1000px; border: 1px solid black; padding: 50px" class="container h-100">
            <form action ="FuncionesGerente" method = "POST" class="form-group">
                <h3>Nuevo Cajero</h3>
                <small class="form-text text-muted"> Todos los campos son obligatorios </small>
                <div class="form-group">
                    <input type = "hidden" name = "codigoCajero" value="${modCajero.getCodigo()}" class="form-control">
                </div>
                <div class="form-group">
                    <input type = "hidden" name = "codigo_ger" value="${login_gerente.getCodigo()}" class="form-control">
                </div>
                <div class="form-group">
                    <label>Nombre</label>
                    <input type = "text" name = "nomCajero" value="${modCajero.getNombre()}" class="form-control">
                </div> 
                <div class="form-group">
                    <label>Turno</label>
                    <select class="form-control" name ="turnoCajero">
                        <option>MATUTINO</option>
                        <option>VESPERTINO</option>
                    </select>
                </div> 
                <div class="form-group">
                    <label>DPI</label>
                    <input type = "text" name = "dpiCajero" value="${modCajero.getDpi()}" class="form-control">
                </div> 
                <div class="form-group">
                    <label>Direccion</label>
                    <input type = "text" name = "dirCajero" value="${modCajero.getDireccion()}" class="form-control">
                </div> 
                <div class="form-group">
                    <label>Sexo</label>
                    <select class="form-control" name ="sexoCajero">
                        <option>Masculino</option>
                        <option>Femenino</option>
                    </select>
                </div> 
                <div class="form-group">
                    <input type="submit" name ="accion" value="Modificar Cajero" class="btn btn-primary">
                    <input type="submit" name ="accion" value="Volver a los cajeros" class="btn btn-primary">
                </div>
            </form>
            <button class="btn btn-primary" onclick="modCajero()">Cambiar Contraseña</button>
        </div>
        <div hidden id ="divCajero" style="width: 1000px; border: 1px solid black; padding: 50px" class="container h-100">
            <form action ="FuncionesGerente" method = "POST" class="form-group">
                <h3>Modificar contraseña</h3>
                <div class="form-group">
                    <input type = "hidden" name = "codigoCajero" value="${modCajero.getCodigo()}" class="form-control">
                </div>
                <div class="form-group">
                    <label>Ingresa la nueva contraseña</label>
                    <input type = "text" name = "pass"  class="form-control">
                </div> 
                <div class="form-group">
                    <input type="submit" name ="accion" value="Change" class="btn btn-primary">
                </div>
            </form>
        </div> 
    </body>
</html>
