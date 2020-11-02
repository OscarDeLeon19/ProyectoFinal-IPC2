/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Cuenta;
import acciones_usuarios.DM_Cajero;
import acciones_usuarios.DM_Cliente;
import acciones_usuarios.DM_Gerente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.Cuenta;
import sun.jvm.hotspot.oops.java_lang_Class;
import usuarios.Cajero;
import usuarios.Cliente;
import usuarios.Gerente;

/**
 *
 * @author oscar19
 */
public class FuncionesGerente extends HttpServlet {

    DM_Cuenta dmcue = new DM_Cuenta();
    DM_Gerente dmgen = new DM_Gerente();
    DM_Cajero dmcaj = new DM_Cajero();
    DM_Cliente dmcli = new DM_Cliente();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FuncionesGerente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FuncionesGerente at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acceder = "";
        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("CrearCuenta")) {
            request.getSession().setAttribute("error", null);
            request.getSession().setAttribute("cuenta", null);
            acceder = "gerente/cuenta.jsp";
        } else if (accion.equalsIgnoreCase("Gerentes")) {
            ArrayList<Gerente> lista = new ArrayList<>();
            request.getSession().setAttribute("listaGerentes", lista);
            request.getSession().setAttribute("gerente", null);
            acceder = "gerente/gerentes.jsp";
        } else if (accion.equalsIgnoreCase("Cajeros")) {
            ArrayList<Cajero> lista = new ArrayList<>();
            request.getSession().setAttribute("listaCajeros", lista);
            request.getSession().setAttribute("cajero", null);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/cajeros.jsp";
        } else if (accion.equalsIgnoreCase("mod_cajero")) {
            String codigo = request.getParameter("codigo");
            Cajero cajero = dmcaj.verCajeroPorCodigo(codigo);
            request.getSession().setAttribute("modCajero", cajero);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/modificar_cajero.jsp";
        } else if (accion.equalsIgnoreCase("eli_cajero")) {
            String mensaje;
            String codigo = request.getParameter("codigo");
            boolean comprobacion = dmcaj.eliminarCajero(codigo);
            if (comprobacion == true) {
                mensaje = "Cajero Eliminado";
            } else {
                mensaje = "Fallo al eliminar";
            }
            ArrayList<Cajero> lista = dmcaj.verCajeros();
            request.getSession().setAttribute("cajero", null);
            request.getSession().setAttribute("listaCajeros", lista);
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/cajeros.jsp";
        } else if (accion.equalsIgnoreCase("Clientes")) {
            ArrayList<Cliente> lista = new ArrayList<>();
            request.getSession().setAttribute("listaClientes", lista);
            request.getSession().setAttribute("cliente", null);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/clientes.jsp";
        } else if (accion.equalsIgnoreCase("mod_cliente")) {
            String codigo = request.getParameter("codigo");
            Cliente cliente = dmcli.verClientePorCodigo(codigo);
            request.getSession().setAttribute("modCliente", cliente);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/modificar_cliente.jsp";
        } else if (accion.equalsIgnoreCase("eli_cliente")) {
            String mensaje;
            String codigo = request.getParameter("codigo");
            boolean comprobacion = dmcli.eliminarCliente(codigo);
            if (comprobacion == true) {
                mensaje = "Cliente Eliminado";
            } else {
                mensaje = "Fallo al eliminar";
            }
            ArrayList<Cliente> lista = dmcli.verClientes();
            request.getSession().setAttribute("cliente", null);
            request.getSession().setAttribute("listaClientes", lista);
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/clientes.jsp";
        } else if (accion.equalsIgnoreCase("Limites")) {
            request.getSession().setAttribute("error", null);
            acceder = "gerente/limites.jsp";
        }
        RequestDispatcher pagina = request.getRequestDispatcher(acceder);
        pagina.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acceder = "";
        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("Crear Cuenta")) {
            Cuenta cuenta = new Cuenta();
            String codigo = request.getParameter("txcodigo");
            String credito = request.getParameter("monto");
            String codigo_cliente = request.getParameter("codigo_cliente");
            if (!"".equals(codigo)) {
                cuenta.setCodigo(codigo);
            }
            if (!"".equals(codigo_cliente)) {
                cuenta.setCodigo_cliente(codigo_cliente);
            }
            try {
                double monto = Double.parseDouble(credito);
                if (monto > 0) {
                    cuenta.setCredito(monto);
                    java.util.Date d = new java.util.Date();
                    java.sql.Date fecha = new java.sql.Date(d.getTime());
                    cuenta.setCreacion(fecha);
                    String mensaje = dmcue.agregarCuenta(cuenta);
                    request.getSession().setAttribute("error", mensaje);

                } else {
                    request.getSession().setAttribute("error", "El monto no puede ser negativo");
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Error al ingresar el monto");
            }
            request.getSession().setAttribute("cuenta", cuenta);
            acceder = "gerente/cuenta.jsp";
        } else if (accion.equalsIgnoreCase("Buscar Gerentes")) {
            String nombre = request.getParameter("nomGerente");
            ArrayList<Gerente> lista = dmgen.verGerentesPorNombre(nombre);
            request.getSession().setAttribute("gerente", nombre);
            request.getSession().setAttribute("listaGerentes", lista);
            acceder = "gerente/gerentes.jsp";
        } else if (accion.equalsIgnoreCase("Nuevo Gerente")) {
            request.getSession().setAttribute("newGerente", null);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/agregar_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Agregar Gerente")) {
            String mensaje;
            Gerente gerente = new Gerente();
            String codigo = request.getParameter("codigoGerente");
            String nombre = request.getParameter("nomGerente");
            String turno = request.getParameter("turnoGerente");
            String dpi = request.getParameter("dpiGerente");
            String direccion = request.getParameter("direccionGerente");
            String sexo = request.getParameter("sexoGerente");
            String contraseña = request.getParameter("passGerente");
            if (!"".equals(codigo)) {
                gerente.setCodigo(codigo);
            }
            if (!"".equals(nombre)) {
                gerente.setNombre(nombre);
            }
            if (!"".equals(turno)) {
                gerente.setTurno(turno);
            }
            if (!"".equals(dpi)) {
                gerente.setDpi(dpi);
            }
            if (!"".equals(direccion)) {
                gerente.setDireccion(direccion);
            }
            if (!"".equals(sexo)) {
                gerente.setSexo(sexo);
            }
            if (!"".equals(contraseña)) {
                gerente.setContraseña(contraseña);
                mensaje = dmgen.agregarGerente(gerente);
            } else {
                mensaje = "La contraseña no puede ser vacia";
            }
            request.getSession().setAttribute("error", mensaje);
            request.getSession().setAttribute("newGerente", gerente);
            acceder = "gerente/agregar_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Volver a los gerentes")) {
            ArrayList<Gerente> lista = dmgen.verGerentes();
            request.getSession().setAttribute("gerente", null);
            request.getSession().setAttribute("listaGerentes", lista);
            acceder = "gerente/gerentes.jsp";
        } else if (accion.equalsIgnoreCase("Modificar mis datos")) {
            request.getSession().setAttribute("error", null);
            acceder = "gerente/modificar_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Modificar Datos")) {
            Gerente gerente = new Gerente();
            String codigo = request.getParameter("codigoGerente");
            String nombre = request.getParameter("nomGerente");
            String turno = request.getParameter("turnoGerente");
            String dpi = request.getParameter("dpiGerente");
            String direccion = request.getParameter("direccionGerente");
            String sexo = request.getParameter("sexoGerente");
            if (!"".equals(codigo)) {
                gerente.setCodigo(codigo);
            }
            if (!"".equals(nombre)) {
                gerente.setNombre(nombre);
            }
            if (!"".equals(turno)) {
                gerente.setTurno(turno);
            }
            if (!"".equals(dpi)) {
                gerente.setDpi(dpi);
            }
            if (!"".equals(direccion)) {
                gerente.setDireccion(direccion);
            }
            if (!"".equals(sexo)) {
                gerente.setSexo(sexo);
            }
            String mensaje = dmgen.modificarGerente(gerente);
            Gerente log = dmgen.verGerentePorCodigo(codigo);
            request.getSession().setAttribute("login_gerente", log);
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/modificar_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Cambiar")) {
            String mensaje;
            String codigo = request.getParameter("codigoGerente");
            String contraseña = request.getParameter("pass");
            if (!"".equals(contraseña)) {
                mensaje = dmgen.modificarContraseña(codigo, contraseña);
            } else {
                mensaje = "La contraseña no puede estar vacia";
            }
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/modificar_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Buscar Cajeros")) {
            String nombre = request.getParameter("nomCajero");
            ArrayList<Cajero> lista = dmcaj.verCajerosPorNombre(nombre);
            request.getSession().setAttribute("cajero", nombre);
            request.getSession().setAttribute("listaCajeros", lista);
            acceder = "gerente/cajeros.jsp";
        } else if (accion.equalsIgnoreCase("Nuevo Cajero")) {
            request.getSession().setAttribute("newCajero", null);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/agregar_cajero.jsp";
        } else if (accion.equalsIgnoreCase("Agregar Cajero")) {
            String mensaje;
            Cajero cajero = new Cajero();
            String codigo = request.getParameter("codigoCajero");
            String nombre = request.getParameter("nomCajero");
            String turno = request.getParameter("turnoCajero");
            String dpi = request.getParameter("dpiCajero");
            String direccion = request.getParameter("dirCajero");
            String sexo = request.getParameter("sexoCajero");
            String contraseña = request.getParameter("passCajero");
            if (!"".equals(codigo)) {
                cajero.setCodigo(codigo);
            }
            if (!"".equals(nombre)) {
                cajero.setNombre(nombre);
            }
            if (!"".equals(turno)) {
                cajero.setTurno(turno);
            }
            if (!"".equals(dpi)) {
                cajero.setDpi(dpi);
            }
            if (!"".equals(direccion)) {
                cajero.setDireccion(direccion);
            }
            if (!"".equals(sexo)) {
                cajero.setSexo(sexo);
            }
            if (!"".equals(contraseña)) {
                cajero.setContraseña(contraseña);
                mensaje = dmcaj.agregarCajero(cajero);
            } else {
                mensaje = "La contraseña no puede ser vacia";
            }
            request.getSession().setAttribute("error", mensaje);
            request.getSession().setAttribute("newCajero", cajero);
            acceder = "gerente/agregar_cajero.jsp";
        } else if (accion.equalsIgnoreCase("Volver a los cajeros")) {
            ArrayList<Cajero> lista = dmcaj.verCajeros();
            request.getSession().setAttribute("cajero", null);
            request.getSession().setAttribute("error", null);
            request.getSession().setAttribute("listaCajeros", lista);
            acceder = "gerente/cajeros.jsp";
        } else if (accion.equalsIgnoreCase("Modificar Cajero")) {
            String mensaje;
            Cajero cajero = new Cajero();
            String codigo = request.getParameter("codigoCajero");
            String nombre = request.getParameter("nomCajero");
            String turno = request.getParameter("turnoCajero");
            String dpi = request.getParameter("dpiCajero");
            String direccion = request.getParameter("dirCajero");
            String sexo = request.getParameter("sexoCajero");
            String codigo_ger = request.getParameter("codigo_ger");
            if (!"".equals(codigo)) {
                cajero.setCodigo(codigo);
            }
            if (!"".equals(nombre)) {
                cajero.setNombre(nombre);
            }
            if (!"".equals(turno)) {
                cajero.setTurno(turno);
            }
            if (!"".equals(dpi)) {
                cajero.setDpi(dpi);
            }
            if (!"".equals(direccion)) {
                cajero.setDireccion(direccion);
            }
            if (!"".equals(sexo)) {
                cajero.setSexo(sexo);
            }
            mensaje = dmcaj.modificarCajero(cajero, codigo_ger);
            request.getSession().setAttribute("error", mensaje);
            ArrayList<Cajero> lista = dmcaj.verCajeros();
            request.getSession().setAttribute("listaCajeros", lista);
            acceder = "gerente/cajeros.jsp";
        } else if (accion.equalsIgnoreCase("Change")) {
            String mensaje;
            String codigo = request.getParameter("codigoCajero");
            String contraseña = request.getParameter("pass");
            if (!"".equals(contraseña)) {
                mensaje = dmcaj.modificarContraseña(codigo, contraseña);
            } else {
                mensaje = "La contraseña no puede estar vacia";
            }
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/modificar_cajero.jsp";
        } else if (accion.equalsIgnoreCase("Buscar Clientes")) {
            String nombre = request.getParameter("nomCliente");
            ArrayList<Cliente> lista = dmcli.verClientesPorNombre(nombre);
            request.getSession().setAttribute("cliente", nombre);
            request.getSession().setAttribute("listaClientes", lista);
            acceder = "gerente/clientes.jsp";
        } else if (accion.equalsIgnoreCase("Subir DPI")) {
            String pdf = request.getParameter("pdf_dpi");
            if ("".equals(pdf)) {
                request.getSession().setAttribute("error", "No haz subido ningun archivo");
                acceder = "gerente/subir_dpi.jsp";
            } else {
                if (pdf.endsWith(".pdf")) {
                    request.getSession().setAttribute("pdf_cliente", pdf);
                    request.getSession().setAttribute("newCliente", null);
                    request.getSession().setAttribute("error", null);
                    acceder = "gerente/agregar_cliente.jsp";
                } else {
                    request.getSession().setAttribute("error", "Archivo incorrecto, se requiere un PDF");
                    acceder = "gerente/subir_dpi.jsp";
                }
            }
        } else if (accion.equalsIgnoreCase("Nuevo Cliente")) {
            request.getSession().setAttribute("pdf_cliente", null);
            request.getSession().setAttribute("error", null);
            acceder = "gerente/subir_dpi.jsp";
        } else if (accion.equalsIgnoreCase("Volver a los clientes")) {
            ArrayList<Cliente> lista = dmcli.verClientes();
            request.getSession().setAttribute("cliente", null);
            request.getSession().setAttribute("error", null);
            request.getSession().setAttribute("listaClientes", lista);
            acceder = "gerente/clientes.jsp";
        } else if (accion.equalsIgnoreCase("Agregar Cliente")) {
            String mensaje;
            Cliente cliente = new Cliente();
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nom");
            String dpi = request.getParameter("dpi");
            String nacimiento = request.getParameter("fechaN");
            String direccion = request.getParameter("dir");
            String sexo = request.getParameter("sexo");
            String contraseña = request.getParameter("pass");
            try {
                Date fecha = Date.valueOf(nacimiento);
                cliente.setNacimiento(fecha);
            } catch (Exception e) {
                cliente.setNacimiento(null);
            }
            if (!"".equals(codigo)) {
                cliente.setCodigo(codigo);
            }
            if (!"".equals(nombre)) {
                cliente.setNombre(nombre);
            }
            if (!"".equals(dpi)) {
                cliente.setDpi(dpi);
            }
            if (!"".equals(direccion)) {
                cliente.setDireccion(direccion);
            }
            if (!"".equals(sexo)) {
                cliente.setSexo(sexo);
            }
            if (!"".equals(contraseña)) {
                cliente.setContraseña(contraseña);
                cliente.setPdf_dpi("archivos/" + String.valueOf(request.getSession().getAttribute("pdf_cliente")));
                mensaje = dmcli.agregarCliente(cliente);
            } else {
                mensaje = "La contraseña no puede ser vacia";
            }
            request.getSession().setAttribute("error", mensaje);
            request.getSession().setAttribute("newCliente", cliente);
            acceder = "gerente/agregar_cliente.jsp";
        } else if (accion.equalsIgnoreCase("Modificar Cliente")) {
            String mensaje;
            Cliente cliente = new Cliente();
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nom");
            String dpi = request.getParameter("dpi");
            String nacimiento = request.getParameter("fechaN");
            String direccion = request.getParameter("dir");
            String sexo = request.getParameter("sexo");
            String codigo_ger = request.getParameter("codigo_ger");
            try {
                Date fecha = Date.valueOf(nacimiento);
                cliente.setNacimiento(fecha);
            } catch (Exception e) {
                cliente.setNacimiento(null);
            }
            if (!"".equals(codigo)) {
                cliente.setCodigo(codigo);
            }
            if (!"".equals(nombre)) {
                cliente.setNombre(nombre);
            }
            if (!"".equals(dpi)) {
                cliente.setDpi(dpi);
            }
            if (!"".equals(direccion)) {
                cliente.setDireccion(direccion);
            }
            if (!"".equals(sexo)) {
                cliente.setSexo(sexo);
            }
            mensaje = dmcli.modificarCliente(cliente, codigo_ger);
            request.getSession().setAttribute("error", mensaje);
            ArrayList<Cliente> lista = dmcli.verClientes();
            request.getSession().setAttribute("listaClientes", lista);
            acceder = "gerente/clientes.jsp";
        } else if (accion.equalsIgnoreCase("Cambio")) {
            String mensaje;
            String codigo = request.getParameter("codigoCliente");
            String contraseña = request.getParameter("pass");
            if (!"".equals(contraseña)) {
                mensaje = dmcli.modificarContraseña(codigo, contraseña);
            } else {
                mensaje = "La contraseña no puede estar vacia";
            }
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/modificar_cliente.jsp";
        } else if (accion.equalsIgnoreCase("Ingresar Limites")) {
            String mensaje = "";
            String l1 = request.getParameter("lim1");
            String l2 = request.getParameter("lim2");
            try {
                int limite1 = Integer.parseInt(l1);
                int limite2 = Integer.parseInt(l2);
                if (limite1 < limite2) {
                    request.getSession().setAttribute("limite1", limite1);
                    request.getSession().setAttribute("limite2", limite2);
                    mensaje = "Limites ingresados satisfactoriamente";
                } else{
                    request.getSession().setAttribute("limite1", null);
                    request.getSession().setAttribute("limite2", null);
                    mensaje = "El limite de las transacciones sumadas debe ser mayor";
                }

            } catch (Exception e) {
                mensaje = "Debes ingresar numeros";
            }
            request.getSession().setAttribute("error", mensaje);
            acceder = "gerente/limites.jsp";
        }
        RequestDispatcher pagina = request.getRequestDispatcher(acceder);
        pagina.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
