package acciones_usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import principal.Conexion;
import principal.Encriptar;
import usuarios.Cliente;

public class DM_Cliente {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptar encriptar = new Encriptar();

    public DM_Cliente() {

    }

    public String agregarCliente(Cliente cliente) {
        String mensaje = "";
        try {
            String contraseña = encriptar.ObtenerEncriptacion(cliente.getContraseña());
            PreparedStatement PrSt;
            String Query = "INSERT INTO Cliente VALUES(?,?,?,?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, cliente.getCodigo());
            PrSt.setString(2, cliente.getNombre());
            PrSt.setString(3, cliente.getDpi());
            PrSt.setDate(4, cliente.getNacimiento());
            PrSt.setString(5, cliente.getDireccion());
            PrSt.setString(6, cliente.getSexo());
            PrSt.setString(7, contraseña);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Agregado Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre();
            } else {
                mensaje = "Fallo al agregar Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre();
            }
        } catch (SQLException e) {
            mensaje = "Fallo al agregar Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre() + " Error: " + e.toString();
        }
        return mensaje;
    }
}
