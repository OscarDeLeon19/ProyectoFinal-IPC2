
package acciones_servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import principal.Conexion;
import servicios.Cuenta;
import usuarios.Gerente;


public class DM_Cuenta {
    
    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();

    public DM_Cuenta() {
    }
    
    public String agregarCuenta(Cuenta cuenta){
        String mensaje  = "";
        try {
          PreparedStatement PrSt;
          String Query = "INSERT INTO Cuenta VALUES(?,?,?,?)";
          PrSt = conexion.prepareStatement(Query);
          PrSt.setString(1, cuenta.getCodigo());
          PrSt.setDate(2, cuenta.getCreacion());
          PrSt.setDouble(3, cuenta.getCredito());
          PrSt.setString(4, cuenta.getCodigo_cliente());
          int resultado = PrSt.executeUpdate();
          if (resultado > 0){
              mensaje = "Agregada Cuenta Codigo No." + cuenta.getCodigo() + " Para el cliente con codigo: " + cuenta.getCodigo_cliente();
          } else {
              mensaje = "Fallo al agregar Cuenta Codigo No." + cuenta.getCodigo() + " Para el cliente con codigo: " + cuenta.getCodigo_cliente();
          }
        } catch (SQLException e) {
            mensaje = "Fallo al agregar Cuenta Codigo No." + cuenta.getCodigo() + " Para el cliente con codigo: " + cuenta.getCodigo_cliente() + " Error: " + e.toString();
        }         
        return mensaje;
    }
}
