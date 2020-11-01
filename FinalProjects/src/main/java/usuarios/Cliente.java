package usuarios;

import java.sql.Date;

public class Cliente {

    private String codigo;
    private String nombre;
    private String dpi;
    private Date nacimiento;
    private String direccion;
    private String sexo;
    private String pdf_dpi;
    private String contraseña;

    public Cliente() {
    }

    public Cliente(String codigo, String nombre, String dpi, Date nacimiento, String direccion, String sexo, String pdf_dpi, String contraseña) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dpi = dpi;
        this.nacimiento = nacimiento;
        this.direccion = direccion;
        this.sexo = sexo;
        this.pdf_dpi = pdf_dpi;
        this.contraseña = contraseña;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPdf_dpi() {
        return pdf_dpi;
    }

    public void setPdf_dpi(String pdf_dpi) {
        this.pdf_dpi = pdf_dpi;
    }

}
