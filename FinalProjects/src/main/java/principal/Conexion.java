
package principal;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/ProyectoFinal";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mariobros99";
    private static boolean conexion_exitosa;

    public Conexion() {
    }

    /**
     * Regresa un valor para comprobar que la conexion fue exitosa
     *
     * @return El valor de la conexion
     */
    public boolean isConexion_Exitosa() {
        return conexion_exitosa;
    }
    
    /**
     * Metodo que conecta la aplicacion con la base de datos
     * @return La conexion entre la aplicacion y la base de datos
     */
    public static Connection getConnection() {

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conexion_exitosa = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
