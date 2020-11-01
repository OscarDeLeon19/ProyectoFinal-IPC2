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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import principal.Carga;
import usuarios.Gerente;

/**
 *
 * @author oscar19
 */
public class Controlador extends HttpServlet {

    DM_Cajero dmcaj = new DM_Cajero();
    DM_Cliente dmcli = new DM_Cliente();
    DM_Gerente dmgen = new DM_Gerente();
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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controlador at " + request.getContextPath() + "</h1>");
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
        if (accion.equalsIgnoreCase("Salir")) {
            request.getSession().setAttribute("error", null);
            acceder = "index.jsp";
        } else if (accion.equalsIgnoreCase("FuncionesG")) {
            acceder = "gerente/funciones.jsp";
        } else if (accion.equalsIgnoreCase("PrimeroG")) {
            acceder = "gerente/interfaz.jsp";
        } else if (accion.equalsIgnoreCase("ReportesG")) {
            acceder = "gerente/reportes.jsp";
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
        if (accion.equalsIgnoreCase("Subir")) {
            Carga funcion = new Carga();
            String fichero = request.getParameter("dataxml");
            File file = new File(fichero);
            funcion.ingresarDatos(file);
            ArrayList mensajes = funcion.getMesajes();
            request.setAttribute("lista", mensajes);
            acceder = "datos.jsp";
        } else if (accion.equalsIgnoreCase("Acceso Gerente")) {
            String codigo = request.getParameter("txcodigo");
            String contraseña = request.getParameter("pass");
            Gerente gerente = dmgen.ingresarGerente(codigo, contraseña);
            if (gerente != null) {
                request.getSession().setAttribute("login_gerente", gerente);
                acceder = "gerente/interfaz.jsp";
            } else {
                String mensaje = "Codigo o contraseña incorrecta";
                request.getSession().setAttribute("error", mensaje);
                acceder = "index.jsp";

            }
        } else if (accion.equalsIgnoreCase("Regresar a Principal")) {
            request.getSession().setAttribute("error", null);
            acceder = "index.jsp";
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
