/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Cuenta;
import acciones_servicios.DM_Transaccion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.Cuenta;
import servicios.Transaccion;
import usuarios.Cajero;

/**
 *
 * @author oscar
 */
public class FuncionesCajero extends HttpServlet {

    DM_Transaccion dmtra = new DM_Transaccion();
    DM_Cuenta dmcue = new DM_Cuenta();

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
            out.println("<title>Servlet FuncionesCajero</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FuncionesCajero at " + request.getContextPath() + "</h1>");
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
        if (accion.equalsIgnoreCase("Deposito")) {
            request.getSession().setAttribute("error", null);
            request.getSession().setAttribute("codigo", null);
            request.getSession().setAttribute("monto", null);
            acceder = "cajero/deposito.jsp";
        } else if (accion.equalsIgnoreCase("Retiro")) {
            request.getSession().setAttribute("error", null);
            request.getSession().setAttribute("codigo", null);
            request.getSession().setAttribute("monto", null);
            acceder = "cajero/retiro.jsp";
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
        if (accion.equalsIgnoreCase("Realizar Deposito")) {
            String mensaje;
            String codigo = request.getParameter("codigo");
            String monto = request.getParameter("monto");
            try {
                Cajero cajero = (Cajero) request.getSession().getAttribute("login_cajero");
                String hora = (String.valueOf(request.getSession().getAttribute("hora")));
                double dinero = Double.parseDouble(monto);
                if (dinero < 0) {
                    mensaje = "El monto no puede ser negativo";
                } else {
                    java.util.Date d = new java.util.Date();
                    java.sql.Date fecha = new java.sql.Date(d.getTime());
                    Transaccion transaccion = new Transaccion();
                    transaccion.setCodigo_cuenta(codigo);
                    transaccion.setFecha(fecha);
                    transaccion.setHora(hora + ":00");
                    transaccion.setTipo("CREDITO");
                    transaccion.setMonto(dinero);
                    transaccion.setCodigo_cajero(cajero.getCodigo());
                    mensaje = dmtra.agregarTransaccion(transaccion);
                    if (mensaje.startsWith("Agregada")) {
                        dmcue.realizarDeposito(codigo, dinero);
                    }
                }
            } catch (Exception e) {
                mensaje = "Error: " + e.toString();
            }
            request.getSession().setAttribute("error", mensaje);
            request.getSession().setAttribute("codigo", codigo);
            request.getSession().setAttribute("monto", monto);
            acceder = "cajero/deposito.jsp";
        } else if (accion.equalsIgnoreCase("Realizar Retiro")) {
            String mensaje;
            String codigo = request.getParameter("codigo");
            String monto = request.getParameter("monto");
            try {
                Cajero cajero = (Cajero) request.getSession().getAttribute("login_cajero");
                String hora = (String.valueOf(request.getSession().getAttribute("hora")));
                double dinero = Double.parseDouble(monto);
                if (dinero < 0) {
                    mensaje = "El monto no puede ser negativo";
                } else {
                    java.util.Date d = new java.util.Date();
                    java.sql.Date fecha = new java.sql.Date(d.getTime());
                    Transaccion transaccion = new Transaccion();
                    transaccion.setCodigo_cuenta(codigo);
                    transaccion.setFecha(fecha);
                    transaccion.setHora(hora + ":00");
                    transaccion.setTipo("DEBITO");
                    transaccion.setMonto(dinero);
                    transaccion.setCodigo_cajero(cajero.getCodigo());
                    Cuenta cuenta = dmcue.obtenerCuenta(codigo);
                    if (dinero > cuenta.getCredito()) {
                        mensaje = "El retiro es mayor al numero de la cuenta";
                    } else {
                        mensaje = dmtra.agregarTransaccion(transaccion);
                        if (mensaje.startsWith("Agregada")) {
                            dmcue.realizarRetiro(codigo, cuenta.getCredito() - dinero);
                        }
                    }
                }
            } catch (Exception e) {
                mensaje = "Error: " + e.toString();
            }
            request.getSession().setAttribute("error", mensaje);
            request.getSession().setAttribute("codigo", codigo);
            request.getSession().setAttribute("monto", monto);
            acceder = "cajero/retiro.jsp";
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
