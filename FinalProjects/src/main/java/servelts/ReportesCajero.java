/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Transaccion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.Transaccion;
import usuarios.Cajero;

/**
 *
 * @author oscar
 */
public class ReportesCajero extends HttpServlet {

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
            out.println("<title>Servlet ReportesCajero</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportesCajero at " + request.getContextPath() + "</h1>");
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
        if (accion.equalsIgnoreCase("Dia")) {
            Cajero cajero = (Cajero) request.getSession().getAttribute("login_cajero");
            java.util.Date d = new java.util.Date();
            java.sql.Date fecha = new java.sql.Date(d.getTime());
            ArrayList<Transaccion> lista = dmtra.verDepositosRetiros(cajero.getCodigo(), fecha);
            double balance = dmtra.obtenerBalance(lista);
            request.getSession().setAttribute("transacciones", lista);
            request.getSession().setAttribute("balance", balance);
            acceder = "cajero/transacciones_realizadas.jsp";
        } else if (accion.equalsIgnoreCase("Trasaccion")){
            ArrayList<Transaccion> lista = new ArrayList<>();
            request.getSession().setAttribute("lista_transacciones", lista);
            request.getSession().setAttribute("fecha1", null);
            request.getSession().setAttribute("fecha2", null);
            request.getSession().setAttribute("balance", null);
            acceder = "cajero/lista_transacciones.jsp";
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
            Cajero cajero = (Cajero) request.getSession().getAttribute("login_cajero");
            ArrayList<Transaccion> lista = dmtra.verListaDeTransacciones(cajero.getCodigo(), f1, f2);
            double balance = dmtra.obtenerBalanceFinal(lista);
            request.getSession().setAttribute("lista_transacciones", lista);
            request.getSession().setAttribute("balance", balance);
            request.getSession().setAttribute("fecha1", f1);
            request.getSession().setAttribute("fecha2", f2);
            acceder = "cajero/lista_transacciones.jsp";
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
