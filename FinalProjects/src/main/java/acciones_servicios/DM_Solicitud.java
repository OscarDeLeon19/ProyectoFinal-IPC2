package acciones_servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import servicios.Solicitud;

public class DM_Solicitud {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();

    public DM_Solicitud() {
    }

    /**
     * Agrega una solicitud a la base de datos
     *
     * @param solicitud La solicitud que se agregara
     * @return Mensaje o error
     */
    public String agregarSolicitud(Solicitud solicitud) {
        String mensaje = "";
        if (comprobarSolicitudPendiene(solicitud) == true) {
            mensaje = "Debes esperar a que el cliente de una respuesta";
        } else {
            int conteo = contarSolicitudesCanceladas(solicitud);
            if (conteo >= 3) {
                mensaje = "Has superado el limite de solicitudes disponibles";
            } else {
                if (comprobarSolicitudesAceptadas(solicitud) == true) {
                    mensaje = "La cuenta ya esta asociada";
                } else {
                    try {
                        PreparedStatement PrSt;
                        String Query = "INSERT INTO Solicitud (Codigo_ClienteS, Codigo_ClienteR, Codigo_Cuenta, Estado, Fecha) VALUES(?,?,?,?,?)";
                        PrSt = conexion.prepareStatement(Query);
                        PrSt.setString(1, solicitud.getEmisor());
                        PrSt.setString(2, solicitud.getReceptor());
                        PrSt.setString(3, solicitud.getCodigo_cuenta());
                        PrSt.setString(4, solicitud.getEstado());
                        PrSt.setDate(5, solicitud.getFecha());
                        int resultado = PrSt.executeUpdate();
                        if (resultado > 0) {
                            mensaje = "Solicitud enviada";
                        } else {
                            mensaje = "Fallo al enviar solicitud";
                        }
                        PrSt.close();
                    } catch (SQLException e) {
                        mensaje = e.toString();
                    }
                }
            }
        }
        return mensaje;
    }

    /**
     * Comprueba si la solicitud esta pendiente de ser respondida
     *
     * @param solicitud La solicitud que se comprobara
     * @return Si la solicitud esta pendiente o no
     */
    public boolean comprobarSolicitudPendiene(Solicitud solicitud) {
        boolean comprobacion = false;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteS = ? AND Codigo_ClienteR = ? AND Codigo_Cuenta = ? AND Estado = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, solicitud.getEmisor());
            PrSt.setString(2, solicitud.getReceptor());
            PrSt.setString(3, solicitud.getCodigo_cuenta());
            PrSt.setString(4, solicitud.getEstado());
            rs = PrSt.executeQuery();
            while (rs.next()) {
                comprobacion = true;
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return comprobacion;
    }

    /**
     * Cuenta el numero de solicitudes que han sido canceladas
     *
     * @param solicitud El tipo de solicitud que se contara
     * @return El numero de veces que ha sido cancelada la solicitud
     */
    public int contarSolicitudesCanceladas(Solicitud solicitud) {
        int conteo = 0;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteS = ? AND Codigo_ClienteR = ? AND Codigo_Cuenta = ? AND Estado = 'Rechazada'";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, solicitud.getEmisor());
            PrSt.setString(2, solicitud.getReceptor());
            PrSt.setString(3, solicitud.getCodigo_cuenta());
            rs = PrSt.executeQuery();
            while (rs.next()) {
                conteo++;
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conteo;
    }

    /**
     * Comprueba si una solicitud ya fue aceptada con anterioridad
     *
     * @param solicitud
     * @return
     */
    public boolean comprobarSolicitudesAceptadas(Solicitud solicitud) {
        boolean comprobacion = false;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteS = ? AND Codigo_Cuenta = ? AND Estado = 'Aceptada'";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, solicitud.getEmisor());
            PrSt.setString(2, solicitud.getCodigo_cuenta());
            rs = PrSt.executeQuery();
            if (rs.next()) {
                comprobacion = true;
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return comprobacion;
    }

    /**
     * Obtiene las solicitudes pendiendes de un cliente
     *
     * @param codigo El codigo del cliente
     * @return La lista de solicitudes
     */
    public ArrayList<Solicitud> obtenerSolcitudes(String codigo) {
        ArrayList<Solicitud> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteR = ? AND Estado = 'Pendiente'";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(rs.getInt("ID"));
                solicitud.setEmisor(rs.getString("Codigo_ClienteS"));
                solicitud.setReceptor(rs.getString("Codigo_ClienteR"));
                solicitud.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                solicitud.setEstado(rs.getString("Estado"));
                solicitud.setFecha(rs.getDate("Fecha"));
                lista.add(solicitud);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    /**
     * Cambia el estado de una solicitud a aceptada
     *
     * @param id El id de la solicitud
     * @return El mensaje de confirmacion
     */
    public String aceptarSolicitud(int id) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Solicitud SET Estado = 'Aceptada' WHERE ID = ? ";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setInt(1, id);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Solicitud aceptada";
            } else {
                mensaje = "Fallo al aceptar solicitud";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = e.toString();
        }
        return mensaje;
    }

    /**
     * Cambia el estado de una solicitud a rechazada
     *
     * @param id El id de la solicitud
     * @return El mensaje de confirmacion
     */
    public String rechazarSolicitud(int id) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Solicitud SET Estado = 'Rechazada' WHERE ID = ? ";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setInt(1, id);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Solicitud rechazada";
            } else {
                mensaje = "Fallo al rechazada solicitud";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = e.toString();
        }
        return mensaje;
    }

    /**
     * Obtiene las solicitudes realizadas por cliente
     *
     * @param codigo El codigo del cliente
     * @return La lista de solicitudes
     */
    public ArrayList<Solicitud> verSolicitudesRealizadas(String codigo) {
        ArrayList<Solicitud> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT s.ID, c.Nombre, s.Codigo_Cuenta, s.Estado, s.Fecha FROM Solicitud s join Cliente c on c.Codigo = s.Codigo_ClienteR WHERE s.Codigo_ClienteS = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(rs.getInt("ID"));
                solicitud.setReceptor(rs.getString("Nombre"));
                solicitud.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                solicitud.setEstado(rs.getString("Estado"));
                solicitud.setFecha(rs.getDate("Fecha"));
                lista.add(solicitud);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    /**
     * Obtiene las solicitudes recibidas de un cliente
     *
     * @param codigo El codigo de el cliente
     * @return La lista de solicitudes
     */
    public ArrayList<Solicitud> verSolicitudesRecibidas(String codigo) {
        ArrayList<Solicitud> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT s.ID, c.Nombre, s.Codigo_Cuenta, s.Estado, s.Fecha FROM Solicitud s join Cliente c on c.Codigo = s.Codigo_ClienteS WHERE s.Codigo_ClienteR = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(rs.getInt("ID"));
                solicitud.setEmisor(rs.getString("Nombre"));
                solicitud.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                solicitud.setEstado(rs.getString("Estado"));
                solicitud.setFecha(rs.getDate("Fecha"));
                lista.add(solicitud);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

}
