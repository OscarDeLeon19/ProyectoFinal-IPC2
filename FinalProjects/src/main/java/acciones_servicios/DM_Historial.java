package acciones_servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import principal.Conexion;
import principal.Encriptar;
import servicios.Historial;

public class DM_Historial {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();

    public DM_Historial() {
    }

    public void AgregarHistorial(Historial historial) {
        try {
            PreparedStatement PrSt;
            String Query = "INSERT INTO Historial (Codigo_Gerente, Entidad, Descripcion, Fecha) VALUES(?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, historial.getCodigo_gerente());
            PrSt.setString(2, historial.getEntidad());
            PrSt.setString(3, historial.getDescripcion());
            PrSt.setDate(4, historial.getFecha());
            PrSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
