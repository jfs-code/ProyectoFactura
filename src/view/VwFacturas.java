package view;

import controller.CtrFacturas;
import controller.CtrProductos;
import controller.CtrUtilitario;
import controller.CtrValidaCaracteres;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.MdlDetalles;
import model.MdlFacturas;
import model.MdlProductos;

/**
 *
 * @author scorpion
 */
public class VwFacturas extends javax.swing.JDialog {

    /**
     * Creates new form VwFacturasGeneradas
     */
    ArrayList<MdlProductos> listaproductos = new ArrayList();
    ArrayList<MdlDetalles> listadetalles = new ArrayList();
    MdlProductos producto = new MdlProductos();
    String tipodepago = "seleccione";

    public VwFacturas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicio();
    }

    void inicio() {
        ocultar();
        llenarComboBoxProductos();
        fechaHoy();
        numeroFactura();
        inhabilitar();
        txt_iva.setText("19");
        txt_subtotal.setText("0");
        txt_totaldescuento.setText("0");
        txt_totalimpuesto.setText("0");
        txt_total.setText("0");
    }

    void ocultar() {
        lbl_idf.setVisible(false);
        tbl_detalles.setVisible(false);
        txt_fecha.setEditable(false);
        txt_numerofactura.setEditable(false);
        btn_guardar.setEnabled(false);
    }
    
    void desocultar() {
        tbl_detalles.setVisible(true);
    }

    void inhabilitar() {
        txt_subtotal.setEditable(false);
        txt_totaldescuento.setEditable(false);
        txt_totalimpuesto.setEditable(false);
        txt_total.setEditable(false);
    }

    public void llenarComboBoxProductos() {
        CtrProductos cproducto = new CtrProductos();
        listaproductos = cproducto.consulta();
        cbb_productos.removeAllItems();
        cbb_productos.addItem("Seleccione");
        for (int posicion = 0; posicion < listaproductos.size(); posicion++) {
            cbb_productos.addItem(listaproductos.get(posicion).getProducto());
        }
    }

    void fechaHoy() {
        CtrUtilitario utilidad = new CtrUtilitario();
        txt_fecha.setText(utilidad.fechaHoy());
    }

    void numeroFactura() {
        CtrFacturas cfactura = new CtrFacturas();
        txt_numerofactura.setText(cfactura.codigoFactura());
    }

    void validaNumero(KeyEvent evt) {
        CtrValidaCaracteres vc = new CtrValidaCaracteres();
        vc.validaNumeros(evt);
    }

    void validaLetras(KeyEvent evt) {
        CtrValidaCaracteres vc = new CtrValidaCaracteres();
        vc.validaLetras(evt);
    }

    void seleccionProducto(java.awt.event.ItemEvent evt) {
        if (!evt.getItem().toString().equalsIgnoreCase("Seleccione")) {
            traerProducto(evt.getItem().toString());
        }
    }

    void seleccionTipodePago(java.awt.event.ItemEvent evt) {
        if (!evt.getItem().toString().equalsIgnoreCase("Seleccione")) {
            tipodepago = evt.getItem().toString();
        }
    }

    void traerProducto(String nombre) {
        for (int pos = 0; pos < listaproductos.size(); pos++) {
            if (nombre.equals((listaproductos.get(pos)).getProducto())) {
                producto = listaproductos.get(pos);
                break;
            }
        }
    }

    void agregar() {
        MdlDetalles detalle = new MdlDetalles();
        if (validaDatosFactura()) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro del producto seleccionado?"
                    + "\n¿Esta seguro de la cantidad y precio unitario escritos?", "Alerta!", JOptionPane.YES_NO_OPTION);
            if (respuesta == 0) {
                detalle.setCantidad(Integer.parseInt(txt_cantidad.getText()));
                detalle.setPreciounitario(Integer.parseInt(txt_preciounitario.getText()));
                detalle.setProducto(producto);
                listadetalles.add(detalle);
                btn_guardar.setEnabled(true);
            }
            desocultar();
            limpiarTabla();
            llenarTabla();
            limpiarCampos();
        }
    }

    void llenarTabla() {
        int total = 0;
        for (int i = 0; i < listadetalles.size(); i++) {
            total = listadetalles.get(i).getCantidad() * listadetalles.get(i).getPreciounitario();
            tbl_detalles.setValueAt(i + 1, i, 0);
            tbl_detalles.setValueAt(listadetalles.get(i).getProducto().getProducto(), i, 1);
            tbl_detalles.setValueAt(listadetalles.get(i).getCantidad(), i, 2);
            tbl_detalles.setValueAt(listadetalles.get(i).getPreciounitario(), i, 3);
            tbl_detalles.setValueAt(total, i, 4);
        }
        txt_subtotal.setText(String.valueOf(Integer.parseInt(txt_subtotal.getText()) + total));
        txt_totaldescuento.setText(String.valueOf(Integer.parseInt(txt_subtotal.getText()) * Integer.parseInt(txt_descuento.getText())/100));
        txt_totalimpuesto.setText(String.valueOf((Integer.parseInt(txt_subtotal.getText()) - Integer.parseInt(txt_totaldescuento.getText())) * Integer.parseInt(txt_iva.getText())/100));
        txt_total.setText(String.valueOf(Integer.parseInt(txt_subtotal.getText()) - Integer.parseInt(txt_totaldescuento.getText()) + Integer.parseInt(txt_totalimpuesto.getText())));
        
    }

    void limpiarTabla() {
        for (int consecutivo = 0; consecutivo < 100; consecutivo++) {
            tbl_detalles.setValueAt("", consecutivo, 0);
            tbl_detalles.setValueAt("", consecutivo, 1);
            tbl_detalles.setValueAt("", consecutivo, 2);
            tbl_detalles.setValueAt("", consecutivo, 3);
            tbl_detalles.setValueAt("", consecutivo, 4);
        }
    }

    void limpiarCampos() {
        txt_cantidad.setText("");
        txt_preciounitario.setText("");
        cbb_productos.setSelectedIndex(0);
    }

    boolean validaDatosFactura() {
        boolean bandera = false;
        if (tipodepago.equalsIgnoreCase("seleccione")
                || txt_documentocliente.getText().isEmpty()
                || txt_nombrecliente.getText().isEmpty()
                || txt_descuento.getText().isEmpty()
                || txt_iva.getText().isEmpty()
                || txt_cantidad.getText().isEmpty()
                || txt_preciounitario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo tipo de pago de estar seleccionado"
                    + "\n El campo documento cliente no debe estar vacio"
                    + "\n El campo nombre cliente no debe estar vacio"
                    + "\n El campo descuento no debe estar vacio"
                    + "\n El campo IVA no debe estar vacio"
                    + "\n El campo cantidad no debe estar vacio"
                    + "\n El campo precio unitario no debe estar vacio");
        } else {
            if (Integer.parseInt(txt_iva.getText()) == 0 || Integer.parseInt(txt_cantidad.getText()) == 0 || Integer.parseInt(txt_preciounitario.getText()) == 0) {
                JOptionPane.showMessageDialog(null, "El campo IVA no debe estar en 0"
                        + "\nEl campo cantidad no debe estar en 0\nEl campo precio unitario no debe estar en 0");
            } else {
                bandera = true;
            }
        }
        return bandera;
    }
    
    void cancelar(){
        cbb_tipodepago.setSelectedIndex(0);
        cbb_productos.setSelectedIndex(0);
        txt_documentocliente.setText("");
        txt_nombrecliente.setText("");
        txt_descuento.setText("");
        txt_iva.setText("");
        txt_cantidad.setText("");
        txt_preciounitario.setText("");
        limpiarTabla();
        tbl_detalles.setVisible(false);
        txt_iva.setText("19");
        txt_subtotal.setText("0");
        txt_totaldescuento.setText("0");
        txt_totalimpuesto.setText("0");
        txt_total.setText("0");
        listadetalles.clear();
        btn_guardar.setEnabled(false);
    }
    
    void guardar(){
        MdlFacturas factura = new MdlFacturas();
        CtrFacturas cfactura = new CtrFacturas();
        factura.setNumerofactura(txt_numerofactura.getText());
        factura.setFecha(txt_fecha.getText());
        factura.setTipodepago(tipodepago);
        factura.setDocumentocliente(txt_documentocliente.getText());
        factura.setNombrecliente(txt_nombrecliente.getText());
        factura.setSubtotal(Integer.parseInt(txt_subtotal.getText()));
        factura.setDescuento(Integer.parseInt(txt_descuento.getText()));
        factura.setIva(Integer.parseInt(txt_iva.getText()));
        factura.setTotaldescuento(Double.parseDouble(txt_totaldescuento.getText()));
        factura.setTotalimpuesto(Double.parseDouble(txt_totalimpuesto.getText()));
        factura.setTotal(Double.parseDouble(txt_total.getText()));
        factura.setDetallefactura(listadetalles);
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea Guardar esta factura?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if(respuesta == 0){
            cfactura.guardar(factura);
        } 
        cancelar();
        txt_fecha.setText("");
        txt_numerofactura.setText("");
        numeroFactura();
        fechaHoy();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_botones = new javax.swing.JPanel();
        btn_guardar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        pnl_factura = new javax.swing.JPanel();
        lbl_numerofactura = new javax.swing.JLabel();
        txt_numerofactura = new javax.swing.JTextField();
        lbl_fecha = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JTextField();
        cbb_tipodepago = new javax.swing.JComboBox<>();
        lbl_tipodepago = new javax.swing.JLabel();
        txt_documentocliente = new javax.swing.JTextField();
        lbl_documentocliente = new javax.swing.JLabel();
        txt_nombrecliente = new javax.swing.JTextField();
        lbl_nombrecliente = new javax.swing.JLabel();
        lbl_idf = new javax.swing.JLabel();
        lbl_descuento = new javax.swing.JLabel();
        txt_descuento = new javax.swing.JTextField();
        lbl_iva = new javax.swing.JLabel();
        txt_iva = new javax.swing.JTextField();
        lbl_signoporcentaje = new javax.swing.JLabel();
        pnl_detalle = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_detalles = new javax.swing.JTable();
        pnl_datosdetalle = new javax.swing.JPanel();
        cbb_productos = new javax.swing.JComboBox<>();
        lbl_producto = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        txt_preciounitario = new javax.swing.JTextField();
        lbl_cantidad = new javax.swing.JLabel();
        lbl_preciounitario = new javax.swing.JLabel();
        btn_agregar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_subtotal = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        lbl_totaldescuento = new javax.swing.JLabel();
        txt_totaldescuento = new javax.swing.JTextField();
        lbl_totalimpuesto = new javax.swing.JLabel();
        txt_totalimpuesto = new javax.swing.JTextField();
        lbl_total = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Facturas");

        btn_guardar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        btn_cancelar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botonesLayout = new javax.swing.GroupLayout(pnl_botones);
        pnl_botones.setLayout(pnl_botonesLayout);
        pnl_botonesLayout.setHorizontalGroup(
            pnl_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_botonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(pnl_botonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_botonesLayout.setVerticalGroup(
            pnl_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_numerofactura.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_numerofactura.setText("Número Factura");

        lbl_fecha.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_fecha.setText("Fecha");

        cbb_tipodepago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Crédito", "Contado" }));
        cbb_tipodepago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_tipodepagoItemStateChanged(evt);
            }
        });

        lbl_tipodepago.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_tipodepago.setText("Tipo de pago");

        txt_documentocliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_documentoclienteKeyTyped(evt);
            }
        });

        lbl_documentocliente.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_documentocliente.setText("Número Documento");

        txt_nombrecliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreclienteKeyTyped(evt);
            }
        });

        lbl_nombrecliente.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_nombrecliente.setText("Nombre Cliente");

        lbl_descuento.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_descuento.setText("Descuento");

        txt_descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descuentoKeyTyped(evt);
            }
        });

        lbl_iva.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_iva.setText("IVA");

        txt_iva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ivaKeyTyped(evt);
            }
        });

        lbl_signoporcentaje.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_signoporcentaje.setText("%");

        javax.swing.GroupLayout pnl_facturaLayout = new javax.swing.GroupLayout(pnl_factura);
        pnl_factura.setLayout(pnl_facturaLayout);
        pnl_facturaLayout.setHorizontalGroup(
            pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_facturaLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_nombrecliente)
                            .addComponent(txt_nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_descuento)
                            .addComponent(txt_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_iva)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addComponent(txt_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(lbl_signoporcentaje))))
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addComponent(lbl_numerofactura)
                                .addGap(2, 2, 2)
                                .addComponent(txt_numerofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_idf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addComponent(lbl_tipodepago)
                                .addGap(1, 1, 1)
                                .addComponent(cbb_tipodepago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(lbl_fecha)
                                .addGap(4, 4, 4)
                                .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addComponent(lbl_documentocliente)
                                .addGap(3, 3, 3)
                                .addComponent(txt_documentocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_facturaLayout.setVerticalGroup(
            pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_facturaLayout.createSequentialGroup()
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_numerofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_idf, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lbl_numerofactura)))
                        .addGap(6, 6, 6)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbb_tipodepago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lbl_tipodepago)))
                        .addGap(10, 10, 10)
                        .addComponent(lbl_nombrecliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lbl_fecha)))
                        .addGap(6, 6, 6)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_documentocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lbl_documentocliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_descuento)
                            .addComponent(lbl_iva))
                        .addGap(6, 6, 6)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_facturaLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lbl_signoporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbl_detalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Producto", "Cantidad", "Precio unitario", "total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_detalles);
        if (tbl_detalles.getColumnModel().getColumnCount() > 0) {
            tbl_detalles.getColumnModel().getColumn(0).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_detalles.getColumnModel().getColumn(1).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(1).setPreferredWidth(150);
            tbl_detalles.getColumnModel().getColumn(2).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbl_detalles.getColumnModel().getColumn(3).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(3).setPreferredWidth(125);
            tbl_detalles.getColumnModel().getColumn(4).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(4).setPreferredWidth(125);
        }

        javax.swing.GroupLayout pnl_detalleLayout = new javax.swing.GroupLayout(pnl_detalle);
        pnl_detalle.setLayout(pnl_detalleLayout);
        pnl_detalleLayout.setHorizontalGroup(
            pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnl_detalleLayout.setVerticalGroup(
            pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_detalleLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbb_productos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_productos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_productosItemStateChanged(evt);
            }
        });

        lbl_producto.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_producto.setText("Producto");

        txt_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cantidadKeyTyped(evt);
            }
        });

        txt_preciounitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_preciounitarioKeyTyped(evt);
            }
        });

        lbl_cantidad.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_cantidad.setText("Cantidad");

        lbl_preciounitario.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_preciounitario.setText("Precio Unitario");

        btn_agregar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_datosdetalleLayout = new javax.swing.GroupLayout(pnl_datosdetalle);
        pnl_datosdetalle.setLayout(pnl_datosdetalleLayout);
        pnl_datosdetalleLayout.setHorizontalGroup(
            pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_datosdetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_producto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_preciounitario)
                    .addComponent(txt_preciounitario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_datosdetalleLayout.setVerticalGroup(
            pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_datosdetalleLayout.createSequentialGroup()
                .addGroup(pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_datosdetalleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_producto)
                            .addComponent(lbl_cantidad)
                            .addComponent(lbl_preciounitario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_datosdetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbb_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_preciounitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_datosdetalleLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        lbl_subtotal.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_subtotal.setText("Subtotal");

        lbl_totaldescuento.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_totaldescuento.setText("Total Descuento");

        lbl_totalimpuesto.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_totalimpuesto.setText("Total Impuesto");

        lbl_total.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_total.setText("Total");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_totalimpuesto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_total, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_totaldescuento, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_subtotal, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_totalimpuesto, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(txt_total)
                    .addComponent(txt_totaldescuento)
                    .addComponent(txt_subtotal))
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_subtotal))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_totaldescuento)
                    .addComponent(txt_totaldescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalimpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_totalimpuesto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_total))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnl_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnl_factura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnl_datosdetalle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_botones, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl_botones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_datosdetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_documentoclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_documentoclienteKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_documentoclienteKeyTyped

    private void txt_nombreclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreclienteKeyTyped
        // TODO add your handling code here:
        validaLetras(evt);
    }//GEN-LAST:event_txt_nombreclienteKeyTyped

    private void txt_descuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descuentoKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_descuentoKeyTyped

    private void txt_ivaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ivaKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_ivaKeyTyped

    private void txt_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidadKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_cantidadKeyTyped

    private void txt_preciounitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_preciounitarioKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_preciounitarioKeyTyped

    private void cbb_productosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_productosItemStateChanged
        // TODO add your handling code here:
        seleccionProducto(evt);
    }//GEN-LAST:event_cbb_productosItemStateChanged

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        agregar();
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void cbb_tipodepagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_tipodepagoItemStateChanged
        // TODO add your handling code here:
        seleccionTipodePago(evt);
    }//GEN-LAST:event_cbb_tipodepagoItemStateChanged

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        cancelar();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_btn_guardarActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(VwFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VwFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VwFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VwFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                VwFacturas dialog = new VwFacturas(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JComboBox<String> cbb_productos;
    private javax.swing.JComboBox<String> cbb_tipodepago;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_descuento;
    private javax.swing.JLabel lbl_documentocliente;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JLabel lbl_idf;
    private javax.swing.JLabel lbl_iva;
    private javax.swing.JLabel lbl_nombrecliente;
    private javax.swing.JLabel lbl_numerofactura;
    private javax.swing.JLabel lbl_preciounitario;
    private javax.swing.JLabel lbl_producto;
    private javax.swing.JLabel lbl_signoporcentaje;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JLabel lbl_tipodepago;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_totaldescuento;
    private javax.swing.JLabel lbl_totalimpuesto;
    private javax.swing.JPanel pnl_botones;
    private javax.swing.JPanel pnl_datosdetalle;
    private javax.swing.JPanel pnl_detalle;
    private javax.swing.JPanel pnl_factura;
    private javax.swing.JTable tbl_detalles;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_descuento;
    private javax.swing.JTextField txt_documentocliente;
    private javax.swing.JTextField txt_fecha;
    private javax.swing.JTextField txt_iva;
    private javax.swing.JTextField txt_nombrecliente;
    private javax.swing.JTextField txt_numerofactura;
    private javax.swing.JTextField txt_preciounitario;
    private javax.swing.JTextField txt_subtotal;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_totaldescuento;
    private javax.swing.JTextField txt_totalimpuesto;
    // End of variables declaration//GEN-END:variables
}
