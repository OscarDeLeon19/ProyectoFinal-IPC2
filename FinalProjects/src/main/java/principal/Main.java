package principal;

import acciones_servicios.DM_Cuenta;
import acciones_usuarios.DM_Cajero;
import acciones_usuarios.DM_Cliente;
import acciones_usuarios.DM_Gerente;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import servicios.Cuenta;
import usuarios.Cajero;
import usuarios.Cliente;
import usuarios.Gerente;

public class Main {

    public static void main(String[] args) {

        DM_Cajero dmcaj = new DM_Cajero();
        DM_Cliente dmcli = new DM_Cliente();
        DM_Gerente dmgen = new DM_Gerente();
        DM_Cuenta dmcue = new DM_Cuenta();

//        Cajero cajero1 = new Cajero("1111", "Oscar DL", "matutino", "5464", "24 avenida", "men", "contra");
//        Cajero cajero2 = new Cajero("1111", "Oscar DL", "matutino", "5464", "24 avenida", "men", "contra");
//        String m1 = dmcaj.agregarCajero(cajero1);
//        String m2 = dmcaj.agregarCajero(cajero2);
//        System.out.println(m1);
//        System.out.println(m2);
//        Cliente cliente1 = new Cliente("1111", "Oscar DL", "matutino", Date.valueOf("2020-05-10"), "24 avenida", "men", "contra");
//        Cliente cliente2 = new Cliente("1111", "Oscar DL", "matutino", Date.valueOf("2020-05-10"), "24 avenida", "men", "contra");
//        String m1 = dmcli.agregarCliente(cliente1);
//        String m2 = dmcli.agregarCliente(cliente2);
//        System.out.println(m1);
//        System.out.println(m2);
//        Gerente gerente1 = new Gerente("1111", "Oscar DL", "matutino", "5464", "24 avenida", "men", "contra");
//        Gerente gerente2 = new Gerente("1111", "Oscar DL", "matutino", "5464", "24 avenida", "men", "contra");
//        String m1 = dmgen.agregarGerente(gerente1);
//        String m2 = dmgen.agregarGerente(gerente2);
//        System.out.println(m1);
//        System.out.println(m2);
//        
//        Cuenta cuenta1 = new Cuenta("5555", Date.valueOf("2020-05-10"), 800, "1111");
//        Cuenta cuenta2 = new Cuenta("5555", Date.valueOf("2020-05-10"), 800, "1111");
//        Cuenta cuenta3 = new Cuenta("4444", Date.valueOf("2020-05-10"), 800, "11115");
//        String m1 = dmcue.agregarCuenta(cuenta1);
//        String m2 = dmcue.agregarCuenta(cuenta2);
//        String m3 = dmcue.agregarCuenta(cuenta3);
//        System.out.println(m1);
//        System.out.println(m2);
//        System.out.println(m3);

        
        try {

            File file = new File("C:\\Users\\oscar\\Desktop\\GIT\\ProyectoFinal-IPC2\\FinalProjects\\src\\main\\webapp\\archivos\\data.xml");
            Carga f = new Carga();
            String c = f.ingresarDatos(file);
            System.out.println(c);
            ArrayList lista = f.getMesajes();
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }
        } catch (Exception e) {

        }

//    Conexion clase = new Conexion();
//    Connection con = clase.getConnection();
    
    }

}
