/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Cuenta;
import acciones_servicios.DM_Solicitud;
import acciones_servicios.DM_Transaccion;
import acciones_usuarios.DM_Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.Cuenta;
import servicios.Solicitud;
import servicios.Transaccion;
import usuarios.Cliente;

/**
 *
 * @author oscar
 */
public class FuncionesCliente extends HttpServlet {

    DM_Cuenta dmcue = new DM_Cuenta();
    DM_Solicitud dmsol = new DM_Solicitud();
    DM_Cliente dmcli = new DM_Cliente();
    DM_Transaccion dmtra = new DM_Transaccion();

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
            out.println("<title>Servlet FuncionesCliente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FuncionesCliente at " + request.getContextPath() + "</h1>");
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
        if (accion.equalsIgnoreCase("Estado")) {
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Cuenta> lista = dmcue.verCuentasDeCliente(cliente.getCodigo());
            request.getSession().setAttribute("cuentas", lista);
            acceder = "cliente/cuentas.jsp";
        } else if (accion.equalsIgnoreCase("Asociar")) {
            request.getSession().setAttribute("error", null);
            request.getSession().setAttribute("nombre", null);
            request.getSession().setAttribute("dpi", null);
            request.getSession().setAttribute("codigo", null);
            acceder = "cliente/asociar_cuenta.jsp";
        } else if (accion.equalsIgnoreCase("Solicitudes")) {
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Solicitud> lista = dmsol.obtenerSolcitudes(cliente.getCodigo());
            request.getSession().setAttribute("solicitudes", lista);
            request.getSession().setAttribute("error", null);
            acceder = "cliente/solicitudes.jsp";
        } else if (accion.equalsIgnoreCase("VerEmisor")) {
            String codigo = request.getParameter("id");
            System.out.println(codigo);
            Cliente cliente = dmcli.verClientePorCodigo(codigo);
            request.getSession().setAttribute("cliente", cliente);
            acceder = "cliente/info_cliente.jsp";
        } else if (accion.equalsIgnoreCase("Aceptar")) {
            String codigo = request.getParameter("id");
            int id = Integer.parseInt(codigo);
            String mensaje = dmsol.aceptarSolicitud(id);
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Solicitud> lista = dmsol.obtenerSolcitudes(cliente.getCodigo());
            request.getSession().setAttribute("solicitudes", lista);
            request.getSession().setAttribute("error", mensaje);
            acceder = "cliente/solicitudes.jsp";
        } else if (accion.equalsIgnoreCase("Rechazar")) {
            String codigo = request.getParameter("id");
            int id = Integer.parseInt(codigo);
            String mensaje = dmsol.rechazarSolicitud(id);
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Solicitud> lista = dmsol.obtenerSolcitudes(cliente.getCodigo());
            request.getSession().setAttribute("solicitudes", lista);
            request.getSession().setAttribute("error", mensaje);
            acceder = "cliente/solicitudes.jsp";
        } else if (accion.equalsIgnoreCase("Transferencias")) {
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Cuenta> lista = dmcue.obtenerCuentasAsociadas(cliente.getCodigo());
            request.getSession().setAttribute("cuentas", lista);
            acceder = "cliente/transferencias.jsp";
        } else if (accion.equalsIgnoreCase("Trasferir")) {
            String codigo = request.getParameter("id");
            request.getSession().setAttribute("cuenta", null);
            request.getSession().setAttribute("monto", null);
            request.getSession().setAttribute("asociada", codigo);
            request.getSession().setAttribute("error", null);
            acceder = "cliente/transferir_dinero.jsp";
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
        if (accion.equalsIgnoreCase("Asociar Cuenta")) {
            String nombre = request.getParameter("nombre");
            String dpi = request.getParameter("dpi");
            String codigo = request.getParameter("codigo");
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            String mensaje = dmcue.validarAsociacion(nombre, dpi, codigo, cliente.getCodigo());
            request.getSession().setAttribute("error", mensaje);
            request.getSession().setAttribute("nombre", nombre);
            request.getSession().setAttribute("dpi", dpi);
            request.getSession().setAttribute("codigo", codigo);
            acceder = "cliente/asociar_cuenta.jsp";
        } else if (accion.equalsIgnoreCase("Regresar")) {
            acceder = "cliente/solicitudes.jsp";
        } else if (accion.equalsIgnoreCase("Transferir Dinero")) {
            String mensaje = "";
            String codigo = request.getParameter("cuenta");
            String dinero = request.getParameter("monto");
            String codigo2 = request.getParameter("asociada");
            if (codigo.equals(codigo2)) {
                mensaje = "No te puedes transferir a la misma cuenta";
            } else {
                try {
                    String hora = (String.valueOf(request.getSession().getAttribute("hora")));
                    Cuenta cuenta1 = dmcue.obtenerCuenta(codigo);
                    Cuenta cuenta2 = dmcue.obtenerCuenta(codigo2);
                    double monto = Double.parseDouble(dinero);
                    if (monto < 1) {
                        mensaje = "No puedes ingresar un negativo o cero";
                    } else {
                        if (monto > cuenta1.getCredito()) {
                            mensaje = "La cuenta no tiene el dinero suficiente";
                        } else {
                            java.util.Date d = new java.util.Date();
                            java.sql.Date fecha = new java.sql.Date(d.getTime());
                            Transaccion transaccion = new Transaccion();
                            transaccion.setCodigo_cuenta(cuenta2.getCodigo());
                            transaccion.setFecha(fecha);
                            transaccion.setHora(hora + ":00");
                            transaccion.setTipo("CREDITO");
                            transaccion.setMonto(monto);
                            transaccion.setCodigo_cajero("101");
                            String m = dmtra.agregarTransaccion(transaccion);
                            System.out.println(m);
                            if (m.startsWith("Agregada")) {
                                dmcue.realizarDeposito(cuenta2.getCodigo(), monto);
                                Transaccion transaccion2 = new Transaccion();
                                transaccion2.setCodigo_cuenta(cuenta1.getCodigo());
                                transaccion2.setFecha(fecha);
                                transaccion2.setHora(hora + ":00");
                                transaccion2.setTipo("DEBITO");
                                transaccion2.setMonto(monto);
                                transaccion2.setCodigo_cajero("101");
                                String p = dmtra.agregarTransaccion(transaccion2);
                                if (p.startsWith("Agregada")) {
                                    System.out.println(p);
                                    double dinero_nuevo = cuenta1.getCredito() - monto;
                                    dmcue.realizarRetiro(cuenta1.getCodigo(), dinero_nuevo);
                                    mensaje = "Transferencia exitosa";
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    mensaje = e.toString();
                }
            }
            request.getSession().setAttribute("cuenta", codigo);
            request.getSession().setAttribute("monto", dinero);
            request.getSession().setAttribute("error", mensaje);
            acceder = "cliente/transferir_dinero.jsp";
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
