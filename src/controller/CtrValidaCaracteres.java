package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author scorpion
 */
public class CtrValidaCaracteres extends KeyAdapter {
    //Valida solo numeros
    public void validaNumeros(KeyEvent evt) {
        int car = (int) evt.getKeyChar();
        if (!((car >= 48) && (car <= 57) || (car > 7 && car < 9))) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(null, "Solo debe escribir numeros", "Ventana Error Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Valida solo letras
    public void validaLetras(KeyEvent evt) {
        int k = (int) evt.getKeyChar();
        if (k >= 33 && k < 65 || k >= 91 && k < 97 || k >= 123 && k < 128) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(null, "No puede ingresar Caracteres especiales\nSólo letras!!!", "Ventana Error Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}
