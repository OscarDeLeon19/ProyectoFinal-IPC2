
package servicios;

import java.sql.Date;


public class Historial {
    
    private int id;
    private String codigo_gerente;
    private String entidad;
    private String descripcion;
    private Date fecha;

    public Historial() {
    }

    public Historial(int id, String codigo_gerente, String entidad, String descripcion, Date fecha) {
        this.id = id;
        this.codigo_gerente = codigo_gerente;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_gerente() {
        return codigo_gerente;
    }

    public void setCodigo_gerente(String codigo_gerente) {
        this.codigo_gerente = codigo_gerente;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
