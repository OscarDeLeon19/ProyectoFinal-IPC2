package acciones_usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import principal.Encriptar;
import usuarios.Cajero;
import usuarios.Gerente;

public class DM_Cajero {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptar encriptar = new Encriptar();

    public DM_Cajero() {
    }

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

    public String modificarCajero(Cajero cajero) {
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
            } else {
                mensaje = "Fallo al modificar los datos";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = " Fallo al modificar los datos. Error: " + e.toString();
        }
        return mensaje;
    }
    
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
    
    public ArrayList<Cajero> verCajeros() {
        ArrayList<Cajero> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cajero";
            PrSt = conexion.prepareStatement(Query);
            rs = PrSt.executeQuery();
            while (rs.next()){
                Cajero cajero = new Cajero();
                cajero.setCodigo(rs.getString("Codigo"));
                cajero.setNombre(rs.getString("Nombre"));
                cajero.setTurno(rs.getString("Turno"));
                cajero.setDpi(rs.getString("DPI"));
                cajero.setDireccion(rs.getString("Direccion"));
                cajero.setSexo(rs.getString("Sexo"));
                cajero.setContraseña(rs.getString("Contraseña"));
                lista.add(cajero);
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    
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
            while (rs.next()){
                Cajero cajero = new Cajero();
                cajero.setCodigo(rs.getString("Codigo"));
                cajero.setNombre(rs.getString("Nombre"));
                cajero.setTurno(rs.getString("Turno"));
                cajero.setDpi(rs.getString("DPI"));
                cajero.setDireccion(rs.getString("Direccion"));
                cajero.setSexo(rs.getString("Sexo"));
                cajero.setContraseña(rs.getString("Contraseña"));
                lista.add(cajero);
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    
    public Cajero verCajeroPorCodigo(String codigo) {
        Cajero cajero = null;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cajero WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()){
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
