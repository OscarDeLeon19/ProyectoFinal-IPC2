/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Cuenta;
import acciones_servicios.DM_Historial;
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
import servicios.Historial;
import servicios.Transaccion;

/**
 *
 * @author oscar
 */
public class ReportesGerente extends HttpServlet {
    DM_Historial dmhis = new DM_Historial();
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
            out.println("<title>Servlet ReportesGerente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportesGerente at " + request.getContextPath() + "</h1>");
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
        if (accion.equalsIgnoreCase("VerHistorial")) {
            ArrayList<Historial> lista = new ArrayList<>();
            request.getSession().setAttribute("historial", lista);
            acceder = "gerente/reportes/historial_gerente.jsp";
        } else if (accion.equalsIgnoreCase("VerTransacciones")){
            double limite = Double.parseDouble((String) request.getSession().getAttribute("limite1"));
            ArrayList<Transaccion> lista = dmtra.verTransacciones(limite);
            request.getSession().setAttribute("transacciones", lista);
            acceder = "gerente/reportes/ver_transacciones.jsp";
        } else if (accion.equalsIgnoreCase("VerSumadas")){
            double limite = Double.parseDouble((String) request.getSession().getAttribute("limite2"));
            ArrayList<Transaccion> lista = dmtra.verTransaccionesSumadas(limite);
            request.getSession().setAttribute("transacciones_sumadas", lista);
            acceder = "gerente/reportes/transacciones_sumadas.jsp";
        } else if (accion.equalsIgnoreCase("ClientesDinero")){
            ArrayList<Cuenta> lista = dmcue.verClientesConMasDinero();
            request.getSession().setAttribute("clientes_dinero", lista);
            acceder = "gerente/reportes/clientes_dinero.jsp";
        } else if (accion.equalsIgnoreCase("TransaccionesCajero")){
            ArrayList<Transaccion> lista = new ArrayList<>();
            request.getSession().setAttribute("transacciones_cajero", lista);
            request.getSession().setAttribute("fecha1", null);
            request.getSession().setAttribute("fecha2", null);
            acceder = "gerente/reportes/transacciones_cajero.jsp";
        } else if (accion.equalsIgnoreCase("ClientesSinTransacciones")){
            ArrayList<Cuenta> lista = new ArrayList<>();
            request.getSession().setAttribute("clientes_sin_transacciones", lista);
            request.getSession().setAttribute("fecha1", null);
            request.getSession().setAttribute("fecha2", null);
            acceder = "gerente/reportes/clientes_sin_transacciones.jsp";
        } else if (accion.equalsIgnoreCase("HistorialCliente")){
            ArrayList<Transaccion> lista = new ArrayList<>();
            request.getSession().setAttribute("historial_transacciones", lista);
            request.getSession().setAttribute("nomCliente", null);
            acceder = "gerente/reportes/historial_transacciones.jsp";
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
        if (accion.equalsIgnoreCase("Historial Gerente")) {
            ArrayList<Historial> lista = dmhis.verHistorial("Gerente");
            request.getSession().setAttribute("historial", lista);
            acceder = "gerente/reportes/historial_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Historial Cajero")) {
            ArrayList<Historial> lista = dmhis.verHistorial("Cajero");
            request.getSession().setAttribute("historial", lista);
            acceder = "gerente/reportes/historial_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Historial Cliente")) {
            ArrayList<Historial> lista = dmhis.verHistorial("Cliente");
            request.getSession().setAttribute("historial", lista);
            acceder = "gerente/reportes/historial_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Buscar Cajeros")) {
            String f1 = request.getParameter("fecha1");
            String f2 = request.getParameter("fecha2");
            ArrayList<Transaccion> lista = dmtra.cajerosConMasTransacciones(f1,f2);
            request.getSession().setAttribute("transacciones_cajero", lista);
            request.getSession().setAttribute("fecha1", f1);
            request.getSession().setAttribute("fecha2", f2);
            acceder = "gerente/reportes/transacciones_cajero.jsp";
        } else if (accion.equalsIgnoreCase("Buscar Clientes")) {
            String f1 = request.getParameter("fecha1");
            String f2 = request.getParameter("fecha2");
            ArrayList<Cuenta> lista = dmcue.clientesSinTransacciones(f1, f2);
            request.getSession().setAttribute("clientes_sin_transacciones", lista);
            request.getSession().setAttribute("fecha1", f1);
            request.getSession().setAttribute("fecha2", f2);
            acceder = "gerente/reportes/clientes_sin_transacciones.jsp";
        } else if (accion.equalsIgnoreCase("Ver Historial")){
            String nombre = request.getParameter("nomCliente");
            ArrayList<Transaccion> lista = dmtra.historalTransacciones(nombre);
            request.getSession().setAttribute("historial_transacciones", lista);
            request.getSession().setAttribute("nomCliente", nombre);
            acceder = "gerente/reportes/historial_transacciones.jsp";
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
