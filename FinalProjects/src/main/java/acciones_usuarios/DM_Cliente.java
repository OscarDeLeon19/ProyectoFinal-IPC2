package acciones_usuarios;

import acciones_servicios.DM_Historial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import principal.Conexion;
import principal.Encriptacion;
import servicios.Historial;
import usuarios.Cajero;
import usuarios.Cliente;

public class DM_Cliente {

    private Conexion clase = new Conexion();
    private Connection conexion = clase.getConnection();
    private Encriptacion encriptar = new Encriptacion();
    private java.util.Date fechaActual = new java.util.Date(); 
    private DM_Historial dmhis = new DM_Historial();

    public DM_Cliente() {

    }
    /**
     * Agrega un cliente a la base de datos
     * @param cliente El cliente que se agregara
     * @return Mensaje o error
     */
    public String agregarCliente(Cliente cliente) {
        String mensaje = "";
        try {
            String contraseña = encriptar.ObtenerEncriptacion(cliente.getContraseña());
            PreparedStatement PrSt;
            String Query = "INSERT INTO Cliente VALUES(?,?,?,?,?,?,?,?)";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, cliente.getCodigo());
            PrSt.setString(2, cliente.getNombre());
            PrSt.setString(3, cliente.getDpi());
            PrSt.setDate(4, cliente.getNacimiento());
            PrSt.setString(5, cliente.getDireccion());
            PrSt.setString(6, cliente.getSexo());
            PrSt.setString(7, cliente.getPdf_dpi());
            PrSt.setString(8, contraseña);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Agregado Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre();
            } else {
                mensaje = "Fallo al agregar Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre();
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = "Fallo al agregar Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre() + " Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Valida que el cliente exista en la base de datos
     * @param codigo El codigo del cliente
     * @param password La contraseña del cliente
     * @return El cliente
     */
    public Cliente ingresarCliente(String codigo, String password) {
        Cliente cliente = null;
        try {
            String contraseña = encriptar.ObtenerEncriptacion(password);
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cliente WHERE Codigo = ? AND Contraseña = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            PrSt.setString(2, contraseña);
            rs = PrSt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodigo(rs.getString("Codigo"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setDpi(rs.getString("DPI"));
                cliente.setNacimiento(rs.getDate("Nacimiento"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setSexo(rs.getString("Sexo"));
                cliente.setContraseña(rs.getString("Contraseña"));
            }
            PrSt.close();
            rs.close();
            return cliente;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Modifica un cliente en la base de datos. Guarda el historial con el cambio realizado
     * @param cliente El cliente que se modificara
     * @param codigo El codigo del gerente
     * @return Mensaje o error
     */
    public String modificarCliente(Cliente cliente, String codigo) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Cliente SET Nombre = ?, DPI = ?, Nacimiento = ?, Direccion = ?, Sexo = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, cliente.getNombre());
            PrSt.setString(2, cliente.getDpi());
            PrSt.setDate(3, cliente.getNacimiento());
            PrSt.setString(4, cliente.getDireccion());
            PrSt.setString(5, cliente.getSexo());
            PrSt.setString(6, cliente.getCodigo());
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Modificado Cliente Codigo No." + cliente.getCodigo() + " Nombre: " + cliente.getNombre();
                Historial historial = new Historial();
                historial.setCodigo_gerente(codigo);
                historial.setDescripcion(mensaje);
                historial.setEntidad("Cliente");
                java.sql.Date fecha = new java.sql.Date(fechaActual.getTime());
                historial.setFecha(fecha);
                dmhis.AgregarHistorial(historial);  
            } else {
                mensaje = "Fallo al modificar los datos";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = " Fallo al modificar los datos. Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Modifica la contraseña de un cliente de la base de datos
     * @param codigo El codigo del cliente
     * @param contraseña La contraseña del cliente
     * @return Mensaje o error
     */
    public String modificarContraseña(String codigo, String contraseña) {
        String mensaje = "";
        try {
            PreparedStatement PrSt;
            String Query = "UPDATE Cliente SET Contraseña = ? WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, encriptar.ObtenerEncriptacion(contraseña));
            PrSt.setString(2, codigo);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                mensaje = "Modificaste la contraseña de Cliente Codigo: " + codigo;
            } else {
                mensaje = "Fallo al modificar la contraseña";
            }
            PrSt.close();
        } catch (SQLException e) {
            mensaje = " Fallo al modificar la constraseña. Error: " + e.toString();
        }
        return mensaje;
    }
    /**
     * Elimina un cliente de la base de datos
     * @param codigo El codigo de el cliente
     * @return Si se elimino el cliente o no
     */
    public Boolean eliminarCliente(String codigo) {
        boolean eliminacion = false;
        try {
            PreparedStatement PrSt;
            String Query = "DELETE FROM Cliente WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            int resultado = PrSt.executeUpdate();
            if (resultado > 0) {
                eliminacion = true;
            } else {
                eliminacion = false;
            }
            PrSt.close();
        } catch (SQLException e) {
            eliminacion = false;
        }
        return eliminacion;
    }
    /**
     * Obtiene los clientes de la base de datos
     * @return  La lista de clientes
     */
    public ArrayList<Cliente> verClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cliente";
            PrSt = conexion.prepareStatement(Query);
            rs = PrSt.executeQuery();
            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getString("Codigo"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setDpi(rs.getString("DPI"));
                cliente.setNacimiento(rs.getDate("Nacimiento"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setSexo(rs.getString("Sexo"));
                cliente.setContraseña(rs.getString("Contraseña"));
                cliente.setPdf_dpi(rs.getString("PDF_DPI"));
                lista.add(cliente);
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    /**
     * Obtiene los clientes de la base de datos por nombre
     * @param nombre El nombre del cliente
     * @return La lista de clientes
     */
    public ArrayList<Cliente> verClientesPorNombre(String nombre) {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            nombre = "%" + nombre + "%";
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cliente WHERE Nombre LIKE ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, nombre);
            rs = PrSt.executeQuery();
            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getString("Codigo"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setDpi(rs.getString("DPI"));
                cliente.setNacimiento(rs.getDate("Nacimiento"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setSexo(rs.getString("Sexo"));
                cliente.setContraseña(rs.getString("Contraseña"));
                cliente.setPdf_dpi(rs.getString("PDF_DPI"));
                lista.add(cliente);
            }
            PrSt.close();
            rs.close();
            return lista;
        } catch (Exception e) {
            return lista;
        }
    }
    /**
     * Obtiene un cliente por codigo de la base de datos
     * @param codigo El codigo del cliente
     * @return El cliente
     */
    public Cliente verClientePorCodigo(String codigo) {
        Cliente cliente = null;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cliente WHERE Codigo = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, codigo);
            rs = PrSt.executeQuery();
            while (rs.next()){
                cliente = new Cliente();
                cliente.setCodigo(rs.getString("Codigo"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setDpi(rs.getString("DPI"));
                cliente.setNacimiento(rs.getDate("Nacimiento"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setSexo(rs.getString("Sexo"));
                cliente.setContraseña(rs.getString("Contraseña"));
                cliente.setPdf_dpi(rs.getString("PDF_DPI"));
            }
            PrSt.close();
            rs.close();
            return cliente;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Valida si existe un cliente en la base de datos en base al nombre y dpi
     * @param nombre el nombre del cliente
     * @param dpi El dpi del cliente
     * @return El cliente
     */
    public Cliente validarCliente(String nombre, String dpi) {
        Cliente cliente = null;
        try {
            PreparedStatement PrSt;
            ResultSet rs = null;
            String Query = "SELECT * FROM Cliente WHERE Nombre = ? AND DPI = ?";
            PrSt = conexion.prepareStatement(Query);
            PrSt.setString(1, nombre);
            PrSt.setString(2, dpi);
            rs = PrSt.executeQuery();
            while (rs.next()){
                cliente = new Cliente();
                cliente.setCodigo(rs.getString("Codigo"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setDpi(rs.getString("DPI"));
                cliente.setNacimiento(rs.getDate("Nacimiento"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setSexo(rs.getString("Sexo"));
                cliente.setContraseña(rs.getString("Contraseña"));
                cliente.setPdf_dpi(rs.getString("PDF_DPI"));
            }
            PrSt.close();
            rs.close();
            return cliente;
        } catch (Exception e) {
            return null;
        }
    }
}
