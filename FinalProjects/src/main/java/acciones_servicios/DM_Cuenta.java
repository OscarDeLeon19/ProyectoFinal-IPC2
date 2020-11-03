
package acciones_servicios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import servicios.Cuenta;


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
    
    public ArrayList<Cuenta> verClientesConMasDinero() {
        ArrayList<Cuenta> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT sum(Credito) as Dinero, c.Codigo_Cliente as Codigo, l.Nombre as Nombre FROM Cuenta c join Cliente l on c.Codigo_Cliente = l.Codigo group by Codigo_Cliente order by sum(Credito) desc limit 10";
            PrSt = conexion.prepareStatement(Query);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCredito(rs.getDouble("Dinero"));
                cuenta.setCodigo_cliente(rs.getString("Codigo"));
                cuenta.setCodigo(rs.getString("Nombre"));
                lista.add(cuenta);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    
    public ArrayList<Cuenta> clientesSinTransacciones(String f1, String f2) {
        ArrayList<Cuenta> lista = new ArrayList<>();
        try {
            Date fecha1 = Date.valueOf(f1);
            Date fecha2 = Date.valueOf(f2);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "select c.Codigo as Codigo, c.Codigo_Cliente as Codigo_Cliente, l.Nombre as Nombre from Cuenta c join Cliente l on c.Codigo_Cliente = l.Codigo where c.Codigo not in (select Codigo_Cuenta from Transaccion where Fecha between ? and ?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setDate(1, fecha1);
            PrSt.setDate(2, fecha2);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(rs.getString("Codigo"));
                cuenta.setCodigo_cliente(rs.getString("Codigo_Cliente"));
                cuenta.setNombre(rs.getString("Nombre"));
                lista.add(cuenta);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }
}
