
package servicios;

import java.sql.Date;


public class Transaccion {
    
    private int codigo;
    private String codigo_cuenta;
    private Date fecha;
    private String hora;
    private String tipo;
    private double monto;
    private String codigo_cajero;

    public Transaccion() {
    }

    public Transaccion(int codigo, String codigo_cuenta, Date fecha, String hora, String tipo, double monto, String codigo_cajero) {
        this.codigo = codigo;
        this.codigo_cuenta = codigo_cuenta;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.monto = monto;
        this.codigo_cajero = codigo_cajero;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodigo_cuenta() {
        return codigo_cuenta;
    }

    public void setCodigo_cuenta(String codigo_cuenta) {
        this.codigo_cuenta = codigo_cuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCodigo_cajero() {
        return codigo_cajero;
    }

    public void setCodigo_cajero(String codigo_cajero) {
        this.codigo_cajero = codigo_cajero;
    }
    
    
}
