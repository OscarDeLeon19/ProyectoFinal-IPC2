<%-- 
    Document   : gerentes
    Created on : 27/10/2020, 12:58:44
    Author     : oscar19
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="usuarios.Gerente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
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
        <%
            ArrayList<Gerente> lista = (ArrayList<Gerente>) request.getSession().getAttribute("listaGerentes");
        %> 
        <div style="width: 1000px; border: 1px solid black; padding: 50px" class="container h-100">
            <form action ="FuncionesGerente" method = "POST" class="form-group">
                <h3>Geretes de la empresa</h3>
                <div class="form-group">
                    <label>Nombre del gerente</label>
                    <input type = "text" name = "nomGerente" value="${gerente}" class="form-control">
                </div>
                <div class="form-group">
                    <input type="submit" name ="accion" value="Buscar Gerentes" class="btn btn-primary">
                    <input type="submit" name ="accion" value="Nuevo Gerente" class="btn btn-primary">
                    <input type="submit" name ="accion" value="Modificar mis datos" class="btn btn-primary">
                </div>
            </form>
        </div>
        <div style="width: 1000px; border: 1px solid black" class="container h-100">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Codigo</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Turno</th>
                        <th scope="col">DPI</th>
                        <th scope="col">Direccion</th>
                        <th scope="col">Sexo</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (lista.size() > 0) {
                            for (int i = 0; i < lista.size(); i++) {
                                Gerente gerente = lista.get(i);
                    %>
                    <tr>
                        <th scope="row"><%= gerente.getCodigo()%></th>
                        <td><%= gerente.getNombre()%></td>
                        <td><%= gerente.getTurno()%></td>
                        <td><%= gerente.getDpi()%></td> 
                        <td><%= gerente.getDireccion()%></td>
                        <td><%= gerente.getSexo()%></td>
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
