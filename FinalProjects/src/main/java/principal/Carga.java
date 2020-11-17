package principal;

import acciones_servicios.DM_Cuenta;
import acciones_servicios.DM_Transaccion;
import acciones_usuarios.DM_Cajero;
import acciones_usuarios.DM_Cliente;
import acciones_usuarios.DM_Gerente;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import servicios.Cuenta;
import servicios.Transaccion;
import usuarios.Cajero;
import usuarios.Cliente;
import usuarios.Gerente;

public class Carga {

    private DM_Cajero dmcaj = new DM_Cajero();
    private DM_Cliente dmcli = new DM_Cliente();
    private DM_Gerente dmgen = new DM_Gerente();
    private DM_Cuenta dmcue = new DM_Cuenta();
    private DM_Transaccion dmtra = new DM_Transaccion();
    private ArrayList<String> mesajes = new ArrayList<>();

    public Carga() {
    }

    public ArrayList<String> getMesajes() {
        return mesajes;
    }

    public void setMesajes(ArrayList<String> mesajes) {
        this.mesajes = mesajes;
    }

    /**
     * Carga los datos del archivo xml a la base de datos
     *
     * @param file La ruta del archivo
     * @return Mensaje de confirmacion
     */
    public String ingresarDatos(File file) {
        String mensaje = "";
        if (file.getName().endsWith(".xml")) {
            mensaje = "Datos cargados";
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();

                NodeList gerentes = document.getElementsByTagName("GERENTE");
                try {

                    for (int i = 0; i < gerentes.getLength(); i++) {
                        Node nodo = gerentes.item(i);

                        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) nodo;
                            String codigo = element.getElementsByTagName("CODIGO").item(0).getTextContent();
                            String nombre = element.getElementsByTagName("NOMBRE").item(0).getTextContent();
                            String turno = element.getElementsByTagName("TURNO").item(0).getTextContent();
                            String dpi = element.getElementsByTagName("DPI").item(0).getTextContent();
                            String direccion = element.getElementsByTagName("DIRECCION").item(0).getTextContent();
                            String sexo = element.getElementsByTagName("SEXO").item(0).getTextContent();
                            String contraseña = element.getElementsByTagName("PASSWORD").item(0).getTextContent();
                            Gerente gerente = new Gerente(codigo, nombre, turno, dpi, direccion, sexo, contraseña);
                            String msj = dmgen.agregarGerente(gerente);
                            mesajes.add(msj);
                        }
                    }
                } catch (Exception e) {
                    mesajes.add("Error en dato del archivo XML" + e.toString());
                    e.printStackTrace();
                }

                NodeList cajeros = document.getElementsByTagName("CAJERO");
                try {
                    for (int i = 0; i < cajeros.getLength(); i++) {
                        Node nodo = cajeros.item(i);

                        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) nodo;
                            String codigo = element.getElementsByTagName("CODIGO").item(0).getTextContent();
                            String nombre = element.getElementsByTagName("NOMBRE").item(0).getTextContent();
                            String turno = element.getElementsByTagName("TURNO").item(0).getTextContent();
                            String dpi = element.getElementsByTagName("DPI").item(0).getTextContent();
                            String direccion = element.getElementsByTagName("DIRECCION").item(0).getTextContent();
                            String sexo = element.getElementsByTagName("SEXO").item(0).getTextContent();
                            String contraseña = element.getElementsByTagName("PASSWORD").item(0).getTextContent();
                            Cajero cajero = new Cajero(codigo, nombre, turno, dpi, direccion, sexo, contraseña);
                            String msj = dmcaj.agregarCajero(cajero);
                            mesajes.add(msj);
                        }
                    }
                } catch (Exception e) {
                    mesajes.add("Error en dato del archivo XML" + e.toString());
                }

                NodeList clientes = document.getElementsByTagName("CLIENTE");
                try {
                    for (int i = 0; i < clientes.getLength(); i++) {
                        Node nodo = clientes.item(i);

                        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) nodo;
                            String codigo = element.getElementsByTagName("CODIGO").item(0).getTextContent();
                            String nombre = element.getElementsByTagName("NOMBRE").item(0).getTextContent();
                            String dpi = element.getElementsByTagName("DPI").item(0).getTextContent();
                            String nacimiento = element.getElementsByTagName("BIRTH").item(0).getTextContent();
                            String direccion = element.getElementsByTagName("DIRECCION").item(0).getTextContent();
                            String sexo = element.getElementsByTagName("SEXO").item(0).getTextContent();
                            String pdf_dpi = element.getElementsByTagName("DPI-PDF").item(0).getTextContent();
                            String contraseña = element.getElementsByTagName("PASSWORD").item(0).getTextContent();
                            Cliente cliente = new Cliente(codigo, nombre, dpi, Date.valueOf(nacimiento), direccion, sexo, "archivos/" + pdf_dpi, contraseña);
                            String msj = dmcli.agregarCliente(cliente);
                            mesajes.add(msj);
                            Node cuenta = element.getElementsByTagName("CUENTAS").item(0).getFirstChild();
                            while (cuenta != null) {
                                cuenta = cuenta.getNextSibling();
                                if (cuenta != null) {
                                    if (cuenta.getNodeType() == Node.ELEMENT_NODE) {
                                        NodeList c = cuenta.getChildNodes();
                                        String code = c.item(1).getTextContent();
                                        String creacion = c.item(3).getTextContent();
                                        String credito = c.item(5).getTextContent();
                                        Cuenta nCuenta = new Cuenta(code, Date.valueOf(creacion), Double.parseDouble(credito), codigo);
                                        String ms = dmcue.agregarCuenta(nCuenta);
                                        mesajes.add(ms);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    mesajes.add("Error en dato del archivo XML" + e.toString());

                }

                NodeList transacciones = document.getElementsByTagName("TRANSACCION");

                try {
                    for (int i = 0; i < transacciones.getLength(); i++) {
                        Node nodo = transacciones.item(i);
                        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) nodo;
                            String codigo = element.getElementsByTagName("CODIGO").item(0).getTextContent();
                            String codigo_cuenta = element.getElementsByTagName("CUENTA-ID").item(0).getTextContent();
                            String fecha = element.getElementsByTagName("FECHA").item(0).getTextContent();
                            String hora = element.getElementsByTagName("HORA").item(0).getTextContent();
                            String tipo = element.getElementsByTagName("TIPO").item(0).getTextContent();
                            String monto = element.getElementsByTagName("MONTO").item(0).getTextContent();
                            String codigo_cajero = element.getElementsByTagName("CAJERO-ID").item(0).getTextContent();

                            Transaccion transaccion = new Transaccion(Integer.parseInt(codigo), codigo_cuenta, Date.valueOf(fecha), hora, tipo, Double.parseDouble(monto), codigo_cajero);
                            String msj = dmtra.agregarTransaccion(transaccion);
                            mesajes.add(msj);
                        }
                    }
                } catch (Exception e) {
                    mesajes.add("Error en dato del archivo XML" + e.toString());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                mensaje = "Error en el archivo de carga" + e.toString();

            }
        } else {
            mensaje = "Error";
        }
        return mensaje;
    }

}
