package com.criptografia.vista;

import main.java.com.criptografia.modelo.CifradoAtbash;
import main.java.com.criptografia.modelo.CifradoCesar;
import main.java.com.criptografia.modelo.EstrategiaCifrado;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JTextField txtClave;
    private JLabel lblDescripcion;
    private JTextArea txtOrigen;
    private JTextArea txtDestino;
    private EstrategiaCifrado estrategiaActual;

    public VentanaPrincipal() {
        setTitle("Sistema de Cifrado Clásico");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        estrategiaActual = new CifradoCesar(); // Estrategia por defecto
        actualizarDescripcion();
    }

    private void inicializarComponentes() {
        // Panel Superior: Clave y Botones de Selección
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));

        JPanel panelClave = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelClave.add(new JLabel("ESCRIBA SU PALABRA (CLAVE): "));
        txtClave = new JTextField(20);
        panelClave.add(txtClave);

        JPanel panelBotonesMetodo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonesMetodo.add(new JLabel("ELIJA SU MÉTODO: "));

        // Cargar estrategias
        List<EstrategiaCifrado> metodos = List.of(
                new CifradoCesar(),
                new CifradoAtbash()
                // Instanciar aquí: new CifradoVigenere(), new CifradoRailFence(), new CifradoPlayfair()
        );

        for (EstrategiaCifrado metodo : metodos) {
            JButton btnMetodo = new JButton(metodo.obtenerNombre());
            btnMetodo.addActionListener(e -> {
                estrategiaActual = metodo;
                actualizarDescripcion();
            });
            panelBotonesMetodo.add(btnMetodo);
        }

        panelSuperior.add(panelClave, BorderLayout.NORTH);
        panelSuperior.add(panelBotonesMetodo, BorderLayout.CENTER);

        // Descripción del Método
        lblDescripcion = new JLabel("EXPLICACIÓN DE MÉTODO: ");
        lblDescripcion.setBorder(BorderFactory.createEtchedBorder());
        panelSuperior.add(lblDescripcion, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel Central: Áreas de Texto y Flechas de Acción
        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 10, 10));

        // Área Izquierda (Texto a cifrar)
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(new JLabel("TEXTO A CIFRAR"), BorderLayout.NORTH);
        txtOrigen = new JTextArea();
        panelIzquierdo.add(new JScrollPane(txtOrigen), BorderLayout.CENTER);

        // Área Central (Botones de acción)
        JPanel panelAcciones = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JButton btnCifrar = new JButton("CIFRAR ⟹");
        gbc.gridy = 0;
        panelAcciones.add(btnCifrar, gbc);

        JButton btnDescifrar = new JButton("⟸ DESCIFRAR");
        gbc.gridy = 1;
        panelAcciones.add(btnDescifrar, gbc);

        // Área Derecha (Texto descifrado/cifrado)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(new JLabel("TEXTO PROCESADO"), BorderLayout.NORTH);
        txtDestino = new JTextArea();
        panelDerecho.add(new JScrollPane(txtDestino), BorderLayout.CENTER);

        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelAcciones);
        panelCentral.add(panelDerecho);

        add(panelCentral, BorderLayout.CENTER);

        // Listeners de Acción
        btnCifrar.addActionListener(e -> ejecutarCifrado(true));
        btnDescifrar.addActionListener(e -> ejecutarCifrado(false));
    }

    private void actualizarDescripcion() {
        if (estrategiaActual != null) {
            lblDescripcion.setText(" EXPLICACIÓN DE MÉTODO: " + estrategiaActual.obtenerDescripcion());
        }
    }

    private void ejecutarCifrado(boolean esCifrado) {
        try {
            String texto = esCifrado ? txtOrigen.getText() : txtDestino.getText();
            String clave = txtClave.getText();

            if (texto.isEmpty()) return;

            String resultado = esCifrado ?
                    estrategiaActual.cifrar(texto, clave) :
                    estrategiaActual.descifrar(texto, clave);

            if (esCifrado) {
                txtDestino.setText(resultado);
            } else {
                txtOrigen.setText(resultado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en el procesamiento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}