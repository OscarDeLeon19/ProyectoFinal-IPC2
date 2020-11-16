package acciones_usuarios;

import acciones_servicios.DM_Historial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import principal.Encriptacion;
import servicios.Historial;
import usuarios.Gerente;

public class DM_Gerente {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptacion encriptar = new Encriptacion();
    private java.util.Date fechaActual = new java.util.Date(); 
    private DM_Historial dmhis = new DM_Historial();

    public DM_Gerente() {
    }
    /**
     * Agrega un gerente a la base de datos
     * @param gerente El gerente
     * @return Mensaje o error
     */
    public String agregarGerente(Gerente gerente) {
        String mensaje = "";
        try {
            String contraseña = encriptar.ObtenerEncriptacion(gerente.getContraseña());
            PreparedStatement PrSt;
            String Query = "INSERT INTO Gerente VALUES(?,?,?,?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, gerente.getCodigo());
            PrSt.setString(2, gerente.getNombre());
            PrSt.setString(3, gerente.getTurno());
            PrSt.setString(4, gerente.getDpi());
            PrSt.setString(5, gerente.getDireccion());
            PrSt.setString(6, gerente.getSexo());
            PrSt.setString(7, contraseña);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Agregado gerente Codigo No." + gerente.getCodigo() + " Nombre: " + gerente.getNombre();
            } else {
                mensaje = "Fallo al agregar gerente Codigo No." + gerente.getCodigo() + " Nombre: " + gerente.getNombre();
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = "Fallo al agregar gerente Codigo No." + gerente.getCodigo() + " Nombre: " + gerente.getNombre() + " Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Valida si existe un gerente en la base de datos
     * @param codigo El codigo del gerente
     * @param password La contraseña del gerente
     * @return El gerente
     */
    public Gerente ingresarGerente(String codigo, String password) {
        Gerente gerente = null;
        try {
            String contraseña = encriptar.ObtenerEncriptacion(password);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Gerente WHERE Codigo = ? AND Contraseña = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            PrSt.setString(2, contraseña);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                gerente = new Gerente();
                gerente.setCodigo(rs.getString("Codigo"));
                gerente.setNombre(rs.getString("Nombre"));
                gerente.setTurno(rs.getString("Turno"));
                gerente.setDpi(rs.getString("DPI"));
                gerente.setDireccion(rs.getString("Direccion"));
                gerente.setSexo(rs.getString("Sexo"));
                gerente.setContraseña(rs.getString("Contraseña"));
            }
            PrSt.close();
            rs.close();
            return gerente;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Modifica los datos de un gerente en la base de datos
     * @param gerente El gerente que se modificara
     * @return La lista de gerentes
     */
    public String modificarGerente(Gerente gerente) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Gerente SET Nombre = ?, Turno = ?, DPI = ?, Direccion = ?, Sexo = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, gerente.getNombre());
            PrSt.setString(2, gerente.getTurno());
            PrSt.setString(3, gerente.getDpi());
            PrSt.setString(4, gerente.getDireccion());
            PrSt.setString(5, gerente.getSexo());
            PrSt.setString(6, gerente.getCodigo());
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Modificados datos de Gerete Codigo: " + gerente.getCodigo() + " Nombre: " + gerente.getNombre();
                Historial historial = new Historial();
                historial.setCodigo_gerente(gerente.getCodigo());
                historial.setDescripcion(mensaje);
                historial.setEntidad("Gerente");
                java.sql.Date fecha = new java.sql.Date(fechaActual.getTime());
                historial.setFecha(fecha);
                dmhis.AgregarHistorial(historial);  
            } else {
                mensaje = "Fallo al modificar tus datos";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = " Fallo al modificar tus datos. Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Modifica la contraseña de un gerente en la base de datos
     * @param codigo El codigo del gerente
     * @param contraseña La contraseña del gerente
     * @return Mesaje o error
     */
    public String modificarContraseña(String codigo, String contraseña) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Gerente SET Contraseña = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, encriptar.ObtenerEncriptacion(contraseña));
            PrSt.setString(2, codigo);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Modificaste tu contraseña";
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
     * Elimina a un gerente de la base de datos
     * @param codigo El codigo del gerente
     * @return Si se elimino o no
     */
    public Boolean eliminarInformacion(String codigo) {
        boolean eliminacion = false;
        try {
            PreparedStatement PrSt;
            String Query = "DELETE FROM Gerente WHERE Codigo = ?";
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
     * Obtiene los gerentes de la base de datos
     * @return La lista del gerente
     */
    public ArrayList<Gerente> verGerentes() {
        ArrayList<Gerente> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Gerente";
            PrSt = conexion.prepareStatement(Query);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Gerente gerente = new Gerente();
                gerente.setCodigo(rs.getString("Codigo"));
                gerente.setNombre(rs.getString("Nombre"));
                gerente.setTurno(rs.getString("Turno"));
                gerente.setDpi(rs.getString("DPI"));
                gerente.setDireccion(rs.getString("Direccion"));
                gerente.setSexo(rs.getString("Sexo"));
                gerente.setContraseña(rs.getString("Contraseña"));
                lista.add(gerente);
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    /**
     * Obtiene los gerentes de la base de datos en base al nombre
     * @param nombre El nombre del gerente
     * @return La lista de gerentes
     */
    public ArrayList<Gerente> verGerentesPorNombre(String nombre) {
        ArrayList<Gerente> lista = new ArrayList<>();
        try {
            nombre = "%" + nombre + "%";
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Gerente WHERE Nombre LIKE ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, nombre);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Gerente gerente = new Gerente();
                gerente.setCodigo(rs.getString("Codigo"));
                gerente.setNombre(rs.getString("Nombre"));
                gerente.setTurno(rs.getString("Turno"));
                gerente.setDpi(rs.getString("DPI"));
                gerente.setDireccion(rs.getString("Direccion"));
                gerente.setSexo(rs.getString("Sexo"));
                gerente.setContraseña(rs.getString("Contraseña"));
                lista.add(gerente);
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    /**
     * Otiene un gerente de la base de datos en base al codigo
     * @param codigo El codigo del gerente
     * @return El gerente
     */
    public Gerente verGerentePorCodigo(String codigo) {
        Gerente gerente = null;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Gerente WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                gerente = new Gerente();
                gerente.setCodigo(rs.getString("Codigo"));
                gerente.setNombre(rs.getString("Nombre"));
                gerente.setTurno(rs.getString("Turno"));
                gerente.setDpi(rs.getString("DPI"));
                gerente.setDireccion(rs.getString("Direccion"));
                gerente.setSexo(rs.getString("Sexo"));
                gerente.setContraseña(rs.getString("Contraseña"));
            }
            PrSt.close();
            rs.close();
            return gerente;
        } catch (Exception e) {
            return null;
        }
    }
}
