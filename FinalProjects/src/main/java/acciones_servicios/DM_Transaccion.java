package acciones_servicios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import servicios.Transaccion;

public class DM_Transaccion {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private DM_Cuenta dmcue = new DM_Cuenta();

    public DM_Transaccion() {
    }
    /**
     * Agrega una transaccion  a la base de datos.
     * @param transaccion La transaccion que se agregara
     * @return Un mensaje o error
     */
    public String agregarTransaccion(Transaccion transaccion) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "INSERT INTO Transaccion (Codigo_Cuenta, Fecha, Hora, Tipo, Monto, Codigo_Cajero) VALUES(?,?,?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, transaccion.getCodigo_cuenta());
            PrSt.setDate(2, transaccion.getFecha());
            PrSt.setString(3, transaccion.getHora());
            PrSt.setString(4, transaccion.getTipo());
            PrSt.setDouble(5, transaccion.getMonto());
            PrSt.setString(6, transaccion.getCodigo_cajero());
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Agregada Transaccion Codigo No." + transaccion.getCodigo() + " Para la cuenta: " + transaccion.getCodigo_cuenta();
            } else {
                mensaje = "Fallo al agregar Transaccion Codigo No." + transaccion.getCodigo() + " Para la cuenta: " + transaccion.getCodigo_cuenta();
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = "Fallo al agregar Transaccion Codigo No." + transaccion.getCodigo() + " Para la cuenta: " + transaccion.getCodigo_cuenta() + " Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Obtiene las transacciones mayores a un limite establecido
     * @param limite El limite que se establecio
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> verTransacciones(double limite) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Transaccion WHERE Monto > ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setDouble(1, limite);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Codigo"));
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setFecha(rs.getDate("Fecha"));
                transaccion.setHora(rs.getString("Hora"));
                transaccion.setTipo(rs.getString("Tipo"));
                transaccion.setMonto(rs.getDouble("Monto"));
                transaccion.setCodigo_cajero(rs.getString("Codigo_Cajero"));
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene las trasacciones sumadas de cada cuenta en base a un limite
     * @param limite El limite establecido
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> verTransaccionesSumadas(double limite) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT sum(Monto) as Monto, Codigo_Cuenta FROM Transaccion group by Codigo_Cuenta  order by sum(Monto) desc;";
            PrSt = conexion.prepareStatement(Query);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setMonto(rs.getDouble("Monto"));
                if (transaccion.getMonto() >= limite) {
                    lista.add(transaccion);
                }
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene el listado de cajeros que mas transacciones han realizado en el banco
     * @param f1 La primera fecha
     * @param f2 La segunda fecha
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> cajerosConMasTransacciones(String f1, String f2) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            Date fecha1 = Date.valueOf(f1);
            Date fecha2 = Date.valueOf(f2);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "select count(Codigo_Cajero) as Transacciones, t.Codigo_Cajero as Codigo_Cajero, c.Nombre as Nombre from Transaccion t join Cajero c on t.Codigo_Cajero = c.Codigo where t.Fecha between ? and ? group by Codigo_Cajero order by count(Codigo_Cajero) desc limit 10";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setDate(1, fecha1);
            PrSt.setDate(2, fecha2);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Transacciones"));
                transaccion.setCodigo_cajero(rs.getString("Codigo_Cajero"));
                transaccion.setTipo(rs.getString("Nombre"));
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene el historial de transacciones de un cliente en base a su nombre
     * @param nombre El nombre del cliente
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> historalTransacciones(String nombre) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            nombre = "%" + nombre + "%";
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "select t.Codigo as Codigo,  t.Codigo_Cuenta as Codigo_Cuenta, t.Fecha as Fecha, t.Monto as Monto, l.Nombre as Nombre from Transaccion t join Cuenta c join Cliente l on c.Codigo = t.Codigo_Cuenta and c.Codigo_Cliente = l.Codigo where l.Nombre like ? order by t.Fecha desc";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, nombre);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo_cajero(rs.getString("Codigo"));
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setFecha(rs.getDate("Fecha"));
                transaccion.setMonto(rs.getDouble("Monto"));
                transaccion.setTipo(rs.getString("Nombre"));
                if (!"%%".equals(nombre)) {
                    lista.add(transaccion);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene los depositos y retiros hechos por un cajero en el dia
     * @param codigo_cajero El codigo del cajero
     * @param fecha La fecha del dia actual
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> verDepositosRetiros(String codigo_cajero, Date fecha) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Transaccion WHERE Codigo_Cajero = ? AND Fecha = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cajero);
            PrSt.setDate(2, fecha);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Codigo"));
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setFecha(rs.getDate("Fecha"));
                transaccion.setHora(rs.getString("Hora"));
                transaccion.setTipo(rs.getString("Tipo"));
                transaccion.setMonto(rs.getDouble("Monto"));
                transaccion.setCodigo_cajero(rs.getString("Codigo_Cajero"));
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene el balance de una lista de transacciones
     * @param lista La lista de transacciones
     * @return El balance
     */    
    public double obtenerBalance(ArrayList<Transaccion> lista) {
        double deposito = 0;
        double retiro = 0;

        for (int i = 0; i < lista.size(); i++) {
            Transaccion transaccion = lista.get(i);
            if ("CREDITO".equals(transaccion.getTipo().toUpperCase())) {
                deposito = deposito + transaccion.getMonto();
            } else if ("DEBITO".equals(transaccion.getTipo().toUpperCase())) {
                retiro = retiro + transaccion.getMonto();
            }
        }
        System.out.println(deposito);
        System.out.println(retiro);
        double balance = deposito - retiro;
        return balance;
    }
    /**
     * Obtiene la lista de transacciones por fecha de un cajero
     * @param codigo_cajero El codigo del cajero
     * @param f1 La primera fecha
     * @param f2 La segunda fecha
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> verListaDeTransacciones(String codigo_cajero, String f1, String f2) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            Date fecha1 = Date.valueOf(f1);
            Date fecha2 = Date.valueOf(f2);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT count(Fecha) as Transacciones, Fecha from Transaccion where Codigo_Cajero = ? and fecha between ? and ? group by Fecha order by Fecha desc";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cajero);
            PrSt.setDate(2, fecha1);
            PrSt.setDate(3, fecha2);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Transacciones"));
                transaccion.setFecha(rs.getDate("Fecha"));
                ArrayList<Transaccion> transacciones = verDepositosRetiros(codigo_cajero, transaccion.getFecha());
                double balance = obtenerBalance(transacciones);
                transaccion.setMonto(balance);
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene el balance final de una lista de transacciones
     * @param lista La lista de transacciones
     * @return El balance final
     */
    public double obtenerBalanceFinal(ArrayList<Transaccion> lista) {
        double balance = 0;
        for (int i = 0; i < lista.size(); i++) {
            Transaccion transaccion = lista.get(i);
            balance = balance + transaccion.getMonto();
        }
        return balance;
    }
    /**
     * Obtiene las ultimas 15 mas grandes transacciones realizadas por un cliente
     * @param codigo El codigo de el cliente
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> verUltimas15Transacciones(String codigo) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT t.Codigo, t.Codigo_Cuenta, t.Fecha, t.Hora, t.Tipo, t.Monto FROM Transaccion t join Cuenta c on t.Codigo_Cuenta = c.Codigo where c.Codigo_Cliente = ? order by t.Monto desc Limit 15";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Codigo"));
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setFecha(rs.getDate("Fecha"));
                transaccion.setHora(rs.getString("Hora"));
                transaccion.setTipo(rs.getString("Tipo"));
                transaccion.setMonto(rs.getDouble("Monto"));
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene las transacciones de un cliente en un intervalo
     * @param codigo El codigo del cliente
     * @param f1 La primera fecha
     * @param f2 La segunda fecha
     * @return 
     */
    public ArrayList<Transaccion> verTransaccionesEnIntervalo(String codigo, String f1, String f2) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            Date fecha1 = Date.valueOf(f1);
            Date fecha2 = Date.valueOf(f2);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT t.Codigo, t.Codigo_Cuenta, t.Fecha, t.Hora, t.Tipo, t.Monto FROM Transaccion t join Cuenta c on t.Codigo_Cuenta = c.Codigo where c.Codigo_Cliente = ? and t.Fecha between ? and ? order by t.Fecha desc";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            PrSt.setDate(2, fecha1);
            PrSt.setDate(3, fecha2);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Codigo"));
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setFecha(rs.getDate("Fecha"));
                transaccion.setHora(rs.getString("Hora"));
                transaccion.setTipo(rs.getString("Tipo"));
                transaccion.setMonto(rs.getDouble("Monto"));
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    /**
     * Obtiene las transacciones de una cueta
     * @param codigo_cuenta El codigo de la cuenta
     * @param f1 La primera fecha
     * @return La lista de transacciones
     */
    public ArrayList<Transaccion> obtenerTransaccionesDeCuenta(String codigo_cuenta, String f1) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        try {
            java.util.Date d = new java.util.Date();
            java.sql.Date fecha2 = new java.sql.Date(d.getTime());
            Date fecha1 = Date.valueOf(f1);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "select * from Transaccion WHERE Codigo_Cuenta = ? AND Fecha BETWEEN ? AND ? ORDER BY Fecha DESC";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cuenta);
            PrSt.setDate(2, fecha1);
            PrSt.setDate(3, fecha2);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCodigo(rs.getInt("Codigo"));
                transaccion.setCodigo_cuenta(rs.getString("Codigo_Cuenta"));
                transaccion.setFecha(rs.getDate("Fecha"));
                transaccion.setHora(rs.getString("Hora"));
                transaccion.setTipo(rs.getString("Tipo"));
                transaccion.setMonto(rs.getDouble("Monto"));
                lista.add(transaccion);
            }
            PrSt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }
}
