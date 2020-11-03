
package servicios;

import java.sql.Date;


public class Cuenta {
    
    private String codigo;
    private Date creacion;
    private double credito;
    private String codigo_cliente;
    private String nombre;

    public Cuenta() {
    }

    public Cuenta(String codigo, Date creacion, double credito, String codigo_cliente) {
        this.codigo = codigo;
        this.creacion = creacion;
        this.credito = credito;
        this.codigo_cliente = codigo_cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
