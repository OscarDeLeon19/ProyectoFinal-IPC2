/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Cuenta;
import acciones_servicios.DM_Solicitud;
import acciones_servicios.DM_Transaccion;
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
public class ReportesCliente extends HttpServlet {

    DM_Transaccion dmtra = new DM_Transaccion();
    DM_Cuenta dmcue = new DM_Cuenta();
    DM_Solicitud dmsol = new DM_Solicitud();
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
            out.println("<title>Servlet ReportesCliente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportesCliente at " + request.getContextPath() + "</h1>");
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
        if (accion.equalsIgnoreCase("Ultima")) {
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Transaccion> lista = dmtra.verUltimas15Transacciones(cliente.getCodigo());
            request.getSession().setAttribute("transacciones", lista);
            acceder = "cliente/ultimas_transacciones.jsp";
        } else if (accion.equalsIgnoreCase("Transacciones")) {
            ArrayList<Transaccion> lista = new ArrayList<>();
            request.getSession().setAttribute("transacciones", lista);
            request.getSession().setAttribute("fecha1", null);
            request.getSession().setAttribute("fecha2", null);
            acceder = "cliente/transacciones_realizadas.jsp";
        } else if (accion.equalsIgnoreCase("VerCuenta")) {
            ArrayList<Transaccion> lista = new ArrayList<>();
            request.getSession().setAttribute("transacciones", lista);
            request.getSession().setAttribute("fecha", null);
            request.getSession().setAttribute("credito", null);
            request.getSession().setAttribute("cuenta", null);
            acceder = "cliente/cuenta_mayor.jsp";
        } else if (accion.equalsIgnoreCase("Realizadas")) {
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Solicitud> lista = dmsol.verSolicitudesRealizadas(cliente.getCodigo());
            request.getSession().setAttribute("solicitudes", lista);
            acceder = "cliente/solicitudes_realizadas.jsp";
        } else if (accion.equalsIgnoreCase("Recibidas")) {
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Solicitud> lista = dmsol.verSolicitudesRecibidas(cliente.getCodigo());
            request.getSession().setAttribute("solicitudes", lista);
            acceder = "cliente/solicitudes_recibidas.jsp";
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
        if (accion.equalsIgnoreCase("Ver Transacciones")) {
            String f1 = request.getParameter("fecha1");
            String f2 = request.getParameter("fecha2");
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            ArrayList<Transaccion> lista = dmtra.verTransaccionesEnIntervalo(cliente.getCodigo(), f1, f2);
            request.getSession().setAttribute("fecha1", f1);
            request.getSession().setAttribute("fecha2", f2);
            request.getSession().setAttribute("transacciones", lista);
            acceder = "cliente/transacciones_realizadas.jsp";
        } else if (accion.equalsIgnoreCase("Ver Cuenta")){
            Cliente cliente = (Cliente) request.getSession().getAttribute("login_cliente");
            String f1 = request.getParameter("fecha");
            Cuenta cuenta = dmcue.obtenerCuentaConMasDinero(cliente.getCodigo());
            ArrayList<Transaccion> lista = dmtra.obtenerTransaccionesDeCuenta(cuenta.getCodigo(), f1);
            request.getSession().setAttribute("transacciones", lista);
            request.getSession().setAttribute("fecha", f1);
            request.getSession().setAttribute("credito", cuenta.getCredito());
            request.getSession().setAttribute("cuenta", cuenta.getCodigo());
            acceder = "cliente/cuenta_mayor.jsp";
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
