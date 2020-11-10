package acciones_usuarios;

import acciones_servicios.DM_Historial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import principal.Encriptar;
import servicios.Historial;
import usuarios.Cajero;

public class DM_Cajero {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptar encriptar = new Encriptar();
    private java.util.Date fechaActual = new java.util.Date();
    private DM_Historial dmhis = new DM_Historial();

    public DM_Cajero() {
    }
    /**
     * Agrega un cajero a la base de datos
     * @param cajero El cajero que se va a agregar
     * @return Mensaje o error
     */
    public String agregarCajero(Cajero cajero) {
        String mensaje = "";
        try {
            String contraseña = encriptar.ObtenerEncriptacion(cajero.getContraseña());
            PreparedStatement PrSt;
            String Query = "INSERT INTO Cajero VALUES(?,?,?,?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, cajero.getCodigo());
            PrSt.setString(2, cajero.getNombre());
            PrSt.setString(3, cajero.getTurno());
            PrSt.setString(4, cajero.getDpi());
            PrSt.setString(5, cajero.getDireccion());
            PrSt.setString(6, cajero.getSexo());
            PrSt.setString(7, contraseña);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Agregado cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre();
            } else {
                mensaje = "Fallo al agregar cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre();
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = "Fallo al agregar cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre() + " Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Comprueba que un cajero exista en la base de datos
     * @param codigo El codigo del cajero
     * @param password La contraseña del cajero
     * @return El cajero
     */
    public Cajero ingresarCajero(String codigo, String password) {
        Cajero cajero = null;
        try {
            String contraseña = encriptar.ObtenerEncriptacion(password);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cajero WHERE Codigo = ? AND Contraseña = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            PrSt.setString(2, contraseña);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                cajero = new Cajero();
                cajero.setCodigo(rs.getString("Codigo"));
                cajero.setNombre(rs.getString("Nombre"));
                cajero.setTurno(rs.getString("Turno"));
                cajero.setDpi(rs.getString("DPI"));
                cajero.setDireccion(rs.getString("Direccion"));
                cajero.setSexo(rs.getString("Sexo"));
                cajero.setContraseña(rs.getString("Contraseña"));
            }
            PrSt.close();
            rs.close();
            return cajero;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Modifica los datos de un cajero en la base de datos. Guarda un historial en la base de datos
     * @param cajero El cajero que se modificara
     * @param codigo El codigo del gerente
     * @return Mensaje o error
     */
    public String modificarCajero(Cajero cajero, String codigo) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Cajero SET Nombre = ?, Turno = ?, DPI = ?, Direccion = ?, Sexo = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, cajero.getNombre());
            PrSt.setString(2, cajero.getTurno());
            PrSt.setString(3, cajero.getDpi());
            PrSt.setString(4, cajero.getDireccion());
            PrSt.setString(5, cajero.getSexo());
            PrSt.setString(6, cajero.getCodigo());
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Modificado cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre();
                Historial historial = new Historial();
                historial.setCodigo_gerente(codigo);
                historial.setDescripcion(mensaje);
                historial.setEntidad("Cajero");
                java.sql.Date fecha = new java.sql.Date(fechaActual.getTime());
                historial.setFecha(fecha);
                dmhis.AgregarHistorial(historial);
            } else {
                mensaje = "Fallo al modificar los datos";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = " Fallo al modificar los datos. Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Modifica la contraseña de un cajero en la base de datos
     * @param codigo El codigo de el cajero
     * @param contraseña La contraseña nueva
     * @return Mensaje o error
     */
    public String modificarContraseña(String codigo, String contraseña) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Cajero SET Contraseña = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, encriptar.ObtenerEncriptacion(contraseña));
            PrSt.setString(2, codigo);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Modificaste la contraseña de Cajero Codigo: " + codigo;
            } else {
                mensaje = "Fallo al modificar la contraseña";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = " Fallo al modificar la constraseña. Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Elimina un cajero de la base de datos
     * @param codigo El codigo del cajero
     * @return mensaje o error
     */
    public Boolean eliminarCajero(String codigo) {
        boolean eliminacion = false;
        try {
            PreparedStatement PrSt;
            String Query = "DELETE FROM Cajero WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                eliminacion = true;
            } else {
                eliminacion = false;
            }
            PrSt.close();
        } catch (SQLException e) {
            eliminacion = false;
        }
        return eliminacion;
    }
    /**
     * Obtiene los cajeros de la base de datos
     * @return La lista de cajeros
     */
    public ArrayList<Cajero> verCajeros() {
        ArrayList<Cajero> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cajero";
            PrSt = conexion.prepareStatement(Query);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Cajero cajero = new Cajero();
                cajero.setCodigo(rs.getString("Codigo"));
                cajero.setNombre(rs.getString("Nombre"));
                cajero.setTurno(rs.getString("Turno"));
                cajero.setDpi(rs.getString("DPI"));
                cajero.setDireccion(rs.getString("Direccion"));
                cajero.setSexo(rs.getString("Sexo"));
                cajero.setContraseña(rs.getString("Contraseña"));
                if (!"101".equals(cajero.getCodigo())) {
                    lista.add(cajero);
                }
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    /**
     * Obtiene a los cajeros en base al nombre
     * @param nombre El nombre del cajero
     * @return La lista del cajeros
     */
    public ArrayList<Cajero> verCajerosPorNombre(String nombre) {
        ArrayList<Cajero> lista = new ArrayList<>();
        try {
            nombre = "%" + nombre + "%";
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cajero WHERE Nombre LIKE ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, nombre);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Cajero cajero = new Cajero();
                cajero.setCodigo(rs.getString("Codigo"));
                cajero.setNombre(rs.getString("Nombre"));
                cajero.setTurno(rs.getString("Turno"));
                cajero.setDpi(rs.getString("DPI"));
                cajero.setDireccion(rs.getString("Direccion"));
                cajero.setSexo(rs.getString("Sexo"));
                cajero.setContraseña(rs.getString("Contraseña"));
                if (!"101".equals(cajero.getCodigo())) {
                    lista.add(cajero);
                }
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    /**
     * Obtiene un cajero de la base de datos en base al codigo
     * @param codigo El codigo del cajero
     * @return El cajero 
     */
    public Cajero verCajeroPorCodigo(String codigo) {
        Cajero cajero = null;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cajero WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                cajero = new Cajero();
                cajero.setCodigo(rs.getString("Codigo"));
                cajero.setNombre(rs.getString("Nombre"));
                cajero.setTurno(rs.getString("Turno"));
                cajero.setDpi(rs.getString("DPI"));
                cajero.setDireccion(rs.getString("Direccion"));
                cajero.setSexo(rs.getString("Sexo"));
                cajero.setContraseña(rs.getString("Contraseña"));
            }
            PrSt.close();
            rs.close();
            return cajero;
        } catch (Exception e) {
            return null;
        }
    }
}
