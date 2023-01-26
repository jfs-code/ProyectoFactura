package controller;

/**
 *
 * @author scorpion
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CtrUtilitario {

    public String fechaHoy() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
