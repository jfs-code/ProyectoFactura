package view;

import controller.CtrDetalles;
import controller.CtrFacturas;
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
public class VwFacturasGeneradas extends javax.swing.JDialog {

    /**
     * Creates new form VwFacturasGeneradas
     */
    ArrayList<MdlFacturas> listafacturas = new ArrayList();
    ArrayList<MdlProductos> listaproductos = new ArrayList();
    ArrayList<MdlDetalles> listadetalles = new ArrayList();
    String tipodepago = "seleccione";
    
    public VwFacturasGeneradas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicio();
    }

    void inicio() {
        llenarTabla();
        ocultar();
    }
    
    void ocultar(){
        lbl_idf.setVisible(false);
        tbl_detalles.setVisible(false);
        txt_fecha.setEditable(false);
        txt_numerofactura.setEditable(false);
        lbl_idd.setVisible(false);
        lbl_idf2.setVisible(false);
    }
    
    void desocultar(){
        tbl_detalles.setVisible(true);
    }

    void llenarTabla() {
        CtrFacturas cfactura = new CtrFacturas();
        listafacturas = cfactura.consulta();
        for (int i = 0; i < listafacturas.size(); i++) {
            tbl_facturas.setValueAt(i + 1, i, 0);
            tbl_facturas.setValueAt(listafacturas.get(i).getFecha(), i, 1);
            tbl_facturas.setValueAt(listafacturas.get(i).getNumerofactura(), i, 2);
            tbl_facturas.setValueAt(listafacturas.get(i).getNombrecliente(), i, 3);
            tbl_facturas.setValueAt(listafacturas.get(i).getTotal(), i, 4);
        }
    }

    void limpiarTabla() {
        for (int consecutivo = 0; consecutivo < 100; consecutivo++) {
            tbl_facturas.setValueAt("", consecutivo, 0);
            tbl_facturas.setValueAt("", consecutivo, 1);
            tbl_facturas.setValueAt("", consecutivo, 2);
            tbl_facturas.setValueAt("", consecutivo, 3);
            tbl_facturas.setValueAt("", consecutivo, 4);
        }
    }
    
    void limpiarTablaD() {
        for (int consecutivo = 0; consecutivo < 100; consecutivo++) {
            tbl_detalles.setValueAt("", consecutivo, 0);
            tbl_detalles.setValueAt("", consecutivo, 1);
            tbl_detalles.setValueAt("", consecutivo, 2);
            tbl_detalles.setValueAt("", consecutivo, 3);
            tbl_detalles.setValueAt("", consecutivo, 4);
        }
    }

    public void traerDato() {
        MdlFacturas factura = new MdlFacturas();
        CtrDetalles cdetalle = new CtrDetalles();
        for (int posicion = 0; posicion < listafacturas.size(); posicion++) {
            if (tbl_facturas.getSelectedRow() == posicion) {
                factura = listafacturas.get(posicion);
            }
        }
        llenarDatos(factura);
        listadetalles = cdetalle.consulta(factura);
        llenarTablaDetalle();
        desocultar();
    }
    
    public void traerDatoD() {
        MdlDetalles detalle = new MdlDetalles();
        for (int posicion = 0; posicion < listadetalles.size(); posicion++) {
            if (tbl_detalles.getSelectedRow() == posicion) {
                detalle = listadetalles.get(posicion);
            }
        }
        llenarDatosD(detalle);
    }
    
    void llenarTablaDetalle() {
        for (int i = 0; i < listadetalles.size(); i++) {
            tbl_detalles.setValueAt(i + 1, i, 0);
            tbl_detalles.setValueAt(listadetalles.get(i).getProducto().getProducto(), i, 1);
            tbl_detalles.setValueAt(listadetalles.get(i).getCantidad(), i, 2);
            tbl_detalles.setValueAt(listadetalles.get(i).getPreciounitario(), i, 3);
            tbl_detalles.setValueAt(listadetalles.get(i).getCantidad()*listadetalles.get(i).getPreciounitario(), i, 4);
        }
    }    

    public void llenarDatosD(MdlDetalles detalle) {
        txt_cantidad.setText(String.valueOf(detalle.getCantidad()));
        txt_preciounitario.setText(String.valueOf(detalle.getPreciounitario())); 
        lbl_idd.setText(String.valueOf(detalle.getIddetalle()));
    }
    
    public void llenarDatos(MdlFacturas factura) {
        lbl_idf.setText(String.valueOf(factura.getIdfactura()));
        txt_documentocliente.setText(factura.getDocumentocliente());
        txt_fecha.setText(factura.getFecha());
        txt_nombrecliente.setText(factura.getNombrecliente());
        txt_numerofactura.setText(factura.getNumerofactura());
        txt_descuento.setText(String.valueOf(factura.getDescuento()));
        txt_iva.setText(String.valueOf(factura.getIva()));
        txt_subtotal.setText(String.valueOf(factura.getSubtotal()));
        txt_total.setText(String.valueOf(factura.getTotal()));
        txt_totaldescuento.setText(String.valueOf(factura.getTotaldescuento()));
        txt_totalimpuesto.setText(String.valueOf(factura.getTotalimpuesto()));
        
        if (factura.getTipodepago().equals("Crédito")) {
            cbb_tipodepago.setSelectedIndex(1);
        } else {
            if (factura.getTipodepago().equals("Contado")) {
                cbb_tipodepago.setSelectedIndex(2);
            } 
        }        
    }
    
    void seleccionTipodePago(java.awt.event.ItemEvent evt) {
        if (!evt.getItem().toString().equalsIgnoreCase("Seleccione")) {
            tipodepago = evt.getItem().toString();
        }
    }
    
    void eliminar(){
        MdlFacturas factura = new MdlFacturas();
        CtrFacturas cfactura = new CtrFacturas();
        factura.setIdfactura(Integer.parseInt(lbl_idf.getText()));
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar esta factura?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if(respuesta == 0){
            cfactura.eliminar(factura);
        }  
        limpiar();
        limpiarTabla();
        limpiarTablaD();
        llenarTabla();
    }
    
    void modificar(){
        MdlFacturas factura = new MdlFacturas();
        CtrFacturas cfactura = new CtrFacturas();
        factura.setIdfactura(Integer.parseInt(lbl_idf.getText()));
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
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea Modificar esta factura?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if(respuesta == 0){
            cfactura.modificar(factura);
        } 
        limpiar();
        limpiarTabla();
        limpiarTablaD();
        llenarTabla();
    }
    
    void limpiar(){
        cbb_tipodepago.setSelectedIndex(0);
        txt_documentocliente.setText("");
        txt_nombrecliente.setText("");
        txt_descuento.setText("");
        txt_iva.setText("");
        txt_cantidad.setText("");
        txt_preciounitario.setText("");
        limpiarTabla();
        tbl_detalles.setVisible(false);
        txt_iva.setText("");
        txt_subtotal.setText("");
        txt_totaldescuento.setText("");
        txt_totalimpuesto.setText("");
        txt_total.setText("");
        listadetalles.clear();
    }
    
    void corregir(){
        int total = 0;
        for (int i = 0; i < listadetalles.size(); i++) {
            if(listadetalles.get(i).getIddetalle()== Integer.parseInt(lbl_idd.getText())){
                listadetalles.get(i).setCantidad(Integer.parseInt(txt_cantidad.getText()));
                listadetalles.get(i).setPreciounitario(Integer.parseInt(txt_preciounitario.getText()));
            }
            total+= listadetalles.get(i).getCantidad() * listadetalles.get(i).getPreciounitario();
        }
        txt_subtotal.setText(String.valueOf(total));
        txt_totaldescuento.setText(String.valueOf(Integer.parseInt(txt_subtotal.getText()) * Integer.parseInt(txt_descuento.getText())/100));
        txt_totalimpuesto.setText(String.valueOf((Integer.parseInt(txt_subtotal.getText()) - Integer.parseInt(txt_totaldescuento.getText())) * Integer.parseInt(txt_iva.getText())/100));
        txt_total.setText(String.valueOf(Integer.parseInt(txt_subtotal.getText()) - Integer.parseInt(txt_totaldescuento.getText()) + Integer.parseInt(txt_totalimpuesto.getText())));
        limpiarTablaD();
        llenarTablaDetalle();
        limpiarD();
    }
    
    void limpiarD(){
        txt_cantidad.setText("");
        txt_preciounitario.setText("");
    }
    
    void validaNumero(KeyEvent evt) {
        CtrValidaCaracteres vc = new CtrValidaCaracteres();
        vc.validaNumeros(evt);
    }

    void validaLetras(KeyEvent evt) {
        CtrValidaCaracteres vc = new CtrValidaCaracteres();
        vc.validaLetras(evt);
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
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        pnl_facturasgeneradas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_facturas = new javax.swing.JTable();
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
        lbl_subtotal = new javax.swing.JLabel();
        lbl_descuento = new javax.swing.JLabel();
        lbl_iva = new javax.swing.JLabel();
        lbl_totaldescuento = new javax.swing.JLabel();
        lbl_totalimpuesto = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        txt_descuento = new javax.swing.JTextField();
        txt_iva = new javax.swing.JTextField();
        txt_totaldescuento = new javax.swing.JTextField();
        txt_totalimpuesto = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        lbl_idf = new javax.swing.JLabel();
        lbl_signoporcentaje = new javax.swing.JLabel();
        pnl_detalle = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_detalles = new javax.swing.JTable();
        txt_cantidad = new javax.swing.JTextField();
        txt_preciounitario = new javax.swing.JTextField();
        lbl_cantidad = new javax.swing.JLabel();
        lbl_preciounitario = new javax.swing.JLabel();
        btn_corregir = new javax.swing.JButton();
        lbl_idd = new javax.swing.JLabel();
        lbl_idf2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Facturas");

        btn_modificar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_modificar.setText("Modificar");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        btn_eliminar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_limpiar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botonesLayout = new javax.swing.GroupLayout(pnl_botones);
        pnl_botones.setLayout(pnl_botonesLayout);
        pnl_botonesLayout.setHorizontalGroup(
            pnl_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_botonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );
        pnl_botonesLayout.setVerticalGroup(
            pnl_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botonesLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_facturasgeneradas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Facturas Generadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbl_facturas.setModel(new javax.swing.table.DefaultTableModel(
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
                "#", "Fecha", "Número Factura", "Nombre Cliente", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_facturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_facturasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_facturas);
        if (tbl_facturas.getColumnModel().getColumnCount() > 0) {
            tbl_facturas.getColumnModel().getColumn(0).setResizable(false);
            tbl_facturas.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_facturas.getColumnModel().getColumn(1).setResizable(false);
            tbl_facturas.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbl_facturas.getColumnModel().getColumn(2).setResizable(false);
            tbl_facturas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tbl_facturas.getColumnModel().getColumn(3).setResizable(false);
            tbl_facturas.getColumnModel().getColumn(3).setPreferredWidth(280);
            tbl_facturas.getColumnModel().getColumn(4).setResizable(false);
            tbl_facturas.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        javax.swing.GroupLayout pnl_facturasgeneradasLayout = new javax.swing.GroupLayout(pnl_facturasgeneradas);
        pnl_facturasgeneradas.setLayout(pnl_facturasgeneradasLayout);
        pnl_facturasgeneradasLayout.setHorizontalGroup(
            pnl_facturasgeneradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_facturasgeneradasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnl_facturasgeneradasLayout.setVerticalGroup(
            pnl_facturasgeneradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_facturasgeneradasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_numerofactura.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_numerofactura.setText("Número Factura");

        lbl_fecha.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_fecha.setText("Fecha");

        cbb_tipodepago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Crédito", "Contado" }));

        lbl_tipodepago.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_tipodepago.setText("Tipo de pago");

        lbl_documentocliente.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_documentocliente.setText("Número Documento");

        lbl_nombrecliente.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_nombrecliente.setText("Nombre Cliente");

        lbl_subtotal.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_subtotal.setText("Subtotal");

        lbl_descuento.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_descuento.setText("Descuento");

        lbl_iva.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_iva.setText("IVA");

        lbl_totaldescuento.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_totaldescuento.setText("Total Descuento");

        lbl_totalimpuesto.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_totalimpuesto.setText("Total Impuesto");

        lbl_total.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_total.setText("Total");

        txt_descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descuentoKeyTyped(evt);
            }
        });

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
                        .addComponent(lbl_numerofactura)
                        .addGap(2, 2, 2)
                        .addComponent(txt_numerofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_idf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(237, 237, 237)
                        .addComponent(lbl_fecha)
                        .addGap(4, 4, 4)
                        .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addComponent(lbl_tipodepago)
                        .addGap(1, 1, 1)
                        .addComponent(cbb_tipodepago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111)
                        .addComponent(lbl_documentocliente)
                        .addGap(3, 3, 3)
                        .addComponent(txt_documentocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addComponent(lbl_nombrecliente)
                        .addGap(4, 4, 4)
                        .addComponent(txt_nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addComponent(lbl_subtotal)
                        .addGap(29, 29, 29)
                        .addComponent(lbl_descuento)
                        .addGap(19, 19, 19)
                        .addComponent(lbl_iva)
                        .addGap(63, 63, 63)
                        .addComponent(lbl_totaldescuento)
                        .addGap(12, 12, 12)
                        .addComponent(lbl_totalimpuesto)
                        .addGap(55, 55, 55)
                        .addComponent(lbl_total))
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txt_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lbl_signoporcentaje)
                        .addGap(14, 14, 14)
                        .addComponent(txt_totaldescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_totalimpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnl_facturaLayout.setVerticalGroup(
            pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_facturaLayout.createSequentialGroup()
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_numerofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_idf, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_numerofactura)
                            .addComponent(lbl_fecha))))
                .addGap(6, 6, 6)
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_tipodepago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_documentocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_tipodepago)
                            .addComponent(lbl_documentocliente))))
                .addGap(6, 6, 6)
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lbl_nombrecliente))
                    .addComponent(txt_nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_subtotal)
                    .addComponent(lbl_descuento)
                    .addComponent(lbl_iva)
                    .addComponent(lbl_totaldescuento)
                    .addComponent(lbl_totalimpuesto)
                    .addComponent(lbl_total))
                .addGap(6, 6, 6)
                .addGroup(pnl_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_facturaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbl_signoporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_totaldescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_totalimpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        tbl_detalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_detallesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_detalles);
        if (tbl_detalles.getColumnModel().getColumnCount() > 0) {
            tbl_detalles.getColumnModel().getColumn(0).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_detalles.getColumnModel().getColumn(1).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(1).setPreferredWidth(200);
            tbl_detalles.getColumnModel().getColumn(2).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbl_detalles.getColumnModel().getColumn(3).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(3).setPreferredWidth(125);
            tbl_detalles.getColumnModel().getColumn(4).setResizable(false);
            tbl_detalles.getColumnModel().getColumn(4).setPreferredWidth(125);
        }

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
        lbl_preciounitario.setText("Precio");

        btn_corregir.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btn_corregir.setText("Corregir");
        btn_corregir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_corregirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_detalleLayout = new javax.swing.GroupLayout(pnl_detalle);
        pnl_detalle.setLayout(pnl_detalleLayout);
        pnl_detalleLayout.setHorizontalGroup(
            pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_detalleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_detalleLayout.createSequentialGroup()
                        .addGroup(pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_preciounitario))
                            .addComponent(lbl_cantidad)
                            .addComponent(txt_preciounitario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_idd, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_corregir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(lbl_idf2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnl_detalleLayout.setVerticalGroup(
            pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_detalleLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_detalleLayout.createSequentialGroup()
                            .addGap(103, 103, 103)
                            .addGroup(pnl_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_idf2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnl_detalleLayout.createSequentialGroup()
                                    .addComponent(lbl_idd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(61, 61, 61))))
                        .addGroup(pnl_detalleLayout.createSequentialGroup()
                            .addComponent(lbl_cantidad)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_preciounitario)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_preciounitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_corregir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(25, Short.MAX_VALUE)))
                    .addGroup(pnl_detalleLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnl_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnl_botones, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnl_facturasgeneradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_facturasgeneradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_facturasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_facturasMouseClicked
        // TODO add your handling code here:
        traerDato();
    }//GEN-LAST:event_tbl_facturasMouseClicked

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_corregirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_corregirActionPerformed
        // TODO add your handling code here:
        corregir();
    }//GEN-LAST:event_btn_corregirActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        // TODO add your handling code here:
        modificar();
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void tbl_detallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_detallesMouseClicked
        // TODO add your handling code here:
        traerDatoD();
    }//GEN-LAST:event_tbl_detallesMouseClicked

    private void txt_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidadKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_cantidadKeyTyped

    private void txt_preciounitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_preciounitarioKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_preciounitarioKeyTyped

    private void txt_descuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descuentoKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_descuentoKeyTyped

    private void txt_ivaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ivaKeyTyped
        // TODO add your handling code here:
        validaNumero(evt);
    }//GEN-LAST:event_txt_ivaKeyTyped

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
//            java.util.logging.Logger.getLogger(VwFacturasGeneradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VwFacturasGeneradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VwFacturasGeneradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VwFacturasGeneradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                VwFacturasGeneradas dialog = new VwFacturasGeneradas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_corregir;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cbb_tipodepago;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_descuento;
    private javax.swing.JLabel lbl_documentocliente;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JLabel lbl_idd;
    private javax.swing.JLabel lbl_idf;
    private javax.swing.JLabel lbl_idf2;
    private javax.swing.JLabel lbl_iva;
    private javax.swing.JLabel lbl_nombrecliente;
    private javax.swing.JLabel lbl_numerofactura;
    private javax.swing.JLabel lbl_preciounitario;
    private javax.swing.JLabel lbl_signoporcentaje;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JLabel lbl_tipodepago;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_totaldescuento;
    private javax.swing.JLabel lbl_totalimpuesto;
    private javax.swing.JPanel pnl_botones;
    private javax.swing.JPanel pnl_detalle;
    private javax.swing.JPanel pnl_factura;
    private javax.swing.JPanel pnl_facturasgeneradas;
    private javax.swing.JTable tbl_detalles;
    private javax.swing.JTable tbl_facturas;
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
