package acciones_usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import principal.Conexion;
import principal.Encriptar;
import usuarios.Gerente;

public class DM_Gerente {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptar encriptar = new Encriptar();
    
    public DM_Gerente() {
    }

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
        } catch (SQLException e) {
            mensaje = "Fallo al agregar gerente Codigo No." + gerente.getCodigo() + " Nombre: " + gerente.getNombre() + " Error: " + e.toString();
        }
        return mensaje;
    }
}
