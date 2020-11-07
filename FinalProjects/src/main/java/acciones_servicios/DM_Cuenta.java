package acciones_servicios;

import acciones_usuarios.DM_Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import servicios.Cuenta;
import servicios.Solicitud;
import usuarios.Cliente;

public class DM_Cuenta {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();

    public DM_Cuenta() {
    }

    public String agregarCuenta(Cuenta cuenta) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "INSERT INTO Cuenta VALUES(?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, cuenta.getCodigo());
            PrSt.setDate(2, cuenta.getCreacion());
            PrSt.setDouble(3, cuenta.getCredito());
            PrSt.setString(4, cuenta.getCodigo_cliente());
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
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

    public Cuenta obtenerCuenta(String codigo_cuenta) {
        Cuenta cuenta = new Cuenta();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cuenta WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cuenta);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                cuenta.setCodigo(rs.getString("Codigo"));
                cuenta.setCreacion(rs.getDate("Creacion"));
                cuenta.setCredito(rs.getDouble("Credito"));
                cuenta.setCodigo_cliente(rs.getString("Codigo_Cliente"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cuenta;
    }

    public void realizarDeposito(String codigo_cuenta, double monto) {
        try {
            Cuenta cuenta = obtenerCuenta(codigo_cuenta);
            double credito_total = cuenta.getCredito() + monto;
            PreparedStatement PrSt;
            String Query = "UPDATE Cuenta SET Credito = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setDouble(1, credito_total);
            PrSt.setString(2, codigo_cuenta);
            PrSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void realizarRetiro(String codigo_cuenta, double credito) {
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Cuenta SET Credito = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setDouble(1, credito);
            PrSt.setString(2, codigo_cuenta);
            PrSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public ArrayList<Cuenta> verCuentasDeCliente(String codigo_cliente) {
        ArrayList<Cuenta> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cuenta where Codigo_Cliente = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cliente);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(rs.getString("Codigo"));
                cuenta.setCreacion(rs.getDate("Creacion"));
                cuenta.setCredito(rs.getDouble("Credito"));
                lista.add(cuenta);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    public String validarAsociacion(String nombre, String dpi, String codigo_cuenta, String codigo_cliente) {
        String mensaje = "";
        DM_Cliente dmcli = new DM_Cliente();
        DM_Solicitud dmsol = new DM_Solicitud();
        try {
            Cliente cliente = dmcli.validarCliente(nombre, dpi);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cuenta WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cuenta);
            if (cliente != null) {
                rs = PrSt.executeQuery();
                if (rs.next()) {
                    java.util.Date d = new java.util.Date();
                    java.sql.Date fecha = new java.sql.Date(d.getTime());
                    Solicitud solicitud = new Solicitud();
                    solicitud.setEmisor(codigo_cliente);
                    solicitud.setReceptor(cliente.getCodigo());
                    solicitud.setCodigo_cuenta(codigo_cuenta);
                    solicitud.setEstado("Pendiente");
                    solicitud.setFecha(fecha);
                    mensaje = dmsol.agregarSolicitud(solicitud);
                } else {
                    mensaje = "La cuenta no existe";
                }
            } else {
                mensaje = "El dueño de la cuenta no existe";
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return mensaje;
    }

    public ArrayList<Cuenta> obtenerCuentasAsociadas(String codigo_cliente) {
        ArrayList<Cuenta> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            PreparedStatement PrSt2;
            ResultSet rs = null;
            ResultSet rs2 = null;
            String Query = "SELECT * FROM Solicitud WHERE Codigo_ClienteS = ? AND Estado = 'Aceptada'";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cliente);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                Cuenta cuenta = obtenerCuenta(rs.getString("Codigo_Cuenta"));
                lista.add(cuenta);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
    
    public Cuenta obtenerCuentaConMasDinero(String codigo_cliente) {
        Cuenta cuenta = new Cuenta();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cuenta WHERE Codigo_Cliente = ? order by Credito desc limit 1";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo_cliente);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                cuenta.setCodigo(rs.getString("Codigo"));
                cuenta.setCreacion(rs.getDate("Creacion"));
                cuenta.setCredito(rs.getDouble("Credito"));
                cuenta.setCodigo_cliente(rs.getString("Codigo_Cliente"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cuenta;
    }
}
