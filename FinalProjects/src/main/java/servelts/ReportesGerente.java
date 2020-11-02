/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import acciones_servicios.DM_Historial;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.Historial;

/**
 *
 * @author oscar
 */
public class ReportesGerente extends HttpServlet {
    DM_Historial dmhis = new DM_Historial();
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
            acceder = "gerente/historial_gerente.jsp";
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
            acceder = "gerente/historial_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Historial Cajero")) {
            ArrayList<Historial> lista = dmhis.verHistorial("Cajero");
            request.getSession().setAttribute("historial", lista);
            acceder = "gerente/historial_gerente.jsp";
        } else if (accion.equalsIgnoreCase("Historial Cliente")) {
            ArrayList<Historial> lista = dmhis.verHistorial("Cliente");
            request.getSession().setAttribute("historial", lista);
            acceder = "gerente/historial_gerente.jsp";
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
