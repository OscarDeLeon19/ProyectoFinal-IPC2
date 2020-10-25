
package acciones_usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import principal.Conexion;
import principal.Encriptar;
import usuarios.Cajero;


public class DM_Cajero {
    
    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptar encriptar = new Encriptar();
    
    public DM_Cajero() {
    }
    
    public String agregarCajero(Cajero cajero){
        String mensaje  = "";
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
          if (resultado > 0){
              mensaje = "Agregado cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre();
          } else {
              mensaje = "Fallo al agregar cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre();
          }
        } catch (SQLException e) {
            mensaje = "Fallo al agregar cajero Codigo No." + cajero.getCodigo() + " Nombre: " + cajero.getNombre() + " Error: " + e.toString();
        }         
        return mensaje;
    }
}
