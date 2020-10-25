
package principal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Encriptar {

    public Encriptar() {
    }
    
    /**
     * Encripta una contraseña para guardarla en la base de datos
     * @param contraseña La contraseña que se va a encriptar
     * @return La contraseña encriptada
     */
    public String ObtenerEncriptacion(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encriptar_bytes = md.digest(contraseña.getBytes());
            BigInteger numero = new BigInteger(1, encriptar_bytes);
            String encriptar_string = numero.toString(16);
            while (encriptar_string.length() < 32) {
                encriptar_string = "0" + encriptar_string;
            }
            return encriptar_string;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
