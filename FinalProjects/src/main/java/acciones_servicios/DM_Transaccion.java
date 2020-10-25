
package acciones_servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import principal.Conexion;
import servicios.Cuenta;
import servicios.Transaccion;


public class DM_Transaccion {
    
    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();

    public DM_Transaccion() {
    }
    
    public String agregarTransaccion(Transaccion transaccion){
        String mensaje  = "";
        try {
          PreparedStatement PrSt;
          String Query = "INSERT INTO Transaccion VALUES(?,?,?,?,?,?,?)";
          PrSt = conexion.prepareStatement(Query);
          PrSt.setInt(1, transaccion.getCodigo());
          PrSt.setString(2, transaccion.getCodigo_cuenta());
          PrSt.setDate(3, transaccion.getFecha());
          PrSt.setString(4, transaccion.getHora());
          PrSt.setString(5, transaccion.getTipo());
          PrSt.setDouble(6, transaccion.getMonto());
          PrSt.setString(7, transaccion.getCodigo_cajero());
          int resultado = PrSt.executeUpdate();
          if (resultado > 0){
              mensaje = "Agregada Transaccion Codigo No." + transaccion.getCodigo() + " Para la cuenta: " + transaccion.getCodigo_cuenta();
          } else {
              mensaje = "Fallo al agregar Transaccion Codigo No." + transaccion.getCodigo() + " Para la cuenta: " + transaccion.getCodigo_cuenta();
          }
        } catch (SQLException e) {
            mensaje = "Fallo al agregar Transaccion Codigo No." + transaccion.getCodigo() + " Para la cuenta: " + transaccion.getCodigo_cuenta() + " Error: " + e.toString();
        }         
        return mensaje;
    }
    
}
