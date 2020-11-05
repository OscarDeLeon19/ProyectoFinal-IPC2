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
                    } catch (SQLException e) {
                        mensaje = e.toString();
                    }
                }
            }
        }
        return mensaje;
    }

    public boolean comprobarSolicitudPendiene(Solicitud solicitud) {
        boolean comprobacion = false;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteS = ? AND Codigo_ClienteR = ? AND Estado = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, solicitud.getEmisor());
            PrSt.setString(2, solicitud.getReceptor());
            PrSt.setString(3, solicitud.getEstado());
            rs = PrSt.executeQuery();
            while (rs.next()) {
                comprobacion = true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return comprobacion;
    }

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
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conteo;
    }

    public boolean comprobarSolicitudesAceptadas(Solicitud solicitud) {
        boolean comprobacion = false;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteS = ?, Codigo_Cuenta = ? AND Estado = 'Aceptada'";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, solicitud.getEmisor());
            PrSt.setString(2, solicitud.getCodigo_cuenta());
            rs = PrSt.executeQuery();
            if (rs.next()) {
                comprobacion = true;
            } 
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return comprobacion;
    }

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
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

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
        } catch (SQLException e) {
            mensaje = e.toString();
        }
        return mensaje;
    }
    
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
        } catch (SQLException e) {
            mensaje = e.toString();
        }
        return mensaje;
    }
     
     
}
