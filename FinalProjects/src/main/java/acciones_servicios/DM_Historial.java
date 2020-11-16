package acciones_servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import principal.Encriptacion;
import servicios.Historial;

public class DM_Historial {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();

    public DM_Historial() {
    }
    /**
     * Agrega un historial a la base de datos
     * @param historial El historial que se agregara
     */
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
            PrSt.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    /**
     * Obtiene el historial en base a una entidad
     * @param entidad La entidad que se desea ver
     * @return La lista del historial
     */
    public ArrayList<Historial> verHistorial(String entidad) {
        ArrayList<Historial> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Historial WHERE Entidad = ? ORDER BY Fecha";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, entidad);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Historial historial = new Historial();
                historial.setId(rs.getInt("ID"));
                historial.setCodigo_gerente(rs.getString("Codigo_Gerente"));
                historial.setEntidad(rs.getString("Entidad"));
                historial.setDescripcion(rs.getString("Descripcion"));
                historial.setFecha(rs.getDate("Fecha"));
                lista.add(historial);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
}
