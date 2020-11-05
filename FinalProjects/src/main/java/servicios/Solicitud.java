package servicios;

import java.sql.Date;

public class Solicitud {

    private int id;
    private String emisor;
    private String receptor;
    private String codigo_cuenta;
    private String estado;
    private Date fecha;

    public Solicitud() {
    }

    public Solicitud(int id, String emisor, String receptor, String codigo_cuenta, String estado, Date fecha) {
        this.id = id;
        this.emisor = emisor;
        this.receptor = receptor;
        this.codigo_cuenta = codigo_cuenta;
        this.estado = estado;
        this.fecha = fecha;
    }

    public String getCodigo_cuenta() {
        return codigo_cuenta;
    }

    public void setCodigo_cuenta(String codigo_cuenta) {
        this.codigo_cuenta = codigo_cuenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
