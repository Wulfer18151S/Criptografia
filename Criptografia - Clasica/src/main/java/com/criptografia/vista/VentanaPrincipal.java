package com.criptografia.vista;

import com.criptografia.modelo.CifradoAtbash;
import com.criptografia.modelo.CifradoCesar;
import com.criptografia.modelo.CifradoPlayfair;
import com.criptografia.modelo.CifradoRailFence;
import com.criptografia.modelo.CifradoVigenere;
import com.criptografia.modelo.EstrategiaCifrado;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JTextField txtClave;
    private JLabel lblDescripcion;
    private JTextArea txtOrigen;
    private JTextArea txtDestino;
    private JPanel panelClave;
    private EstrategiaCifrado estrategiaActual;

    public VentanaPrincipal() {
        setTitle("Sistema de Cifrado Clásico");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        estrategiaActual = new CifradoCesar();
        actualizarDescripcion();
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));

        panelClave = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelClave.add(new JLabel("CLAVE: "));
        txtClave = new JTextField(20);
        panelClave.add(txtClave);

        JPanel panelBotonesMetodo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonesMetodo.add(new JLabel("MÉTODO: "));

        List<EstrategiaCifrado> metodos = List.of(
                new CifradoCesar(),
                new CifradoAtbash(),
                new CifradoVigenere(),
                new CifradoRailFence(),
                new CifradoPlayfair()
        );

        for (EstrategiaCifrado metodo : metodos) {
            JButton btnMetodo = new JButton(metodo.obtenerNombre());
            btnMetodo.addActionListener(e -> {
                estrategiaActual = metodo;
                actualizarDescripcion();
            });
            panelBotonesMetodo.add(btnMetodo);
        }

        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panelBotonesMetodo.add(btnLimpiar);

        panelSuperior.add(panelClave, BorderLayout.NORTH);
        panelSuperior.add(panelBotonesMetodo, BorderLayout.CENTER);

        lblDescripcion = new JLabel();
        lblDescripcion.setBorder(BorderFactory.createEtchedBorder());
        panelSuperior.add(lblDescripcion, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 10, 10));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(new JLabel("TEXTO ORIGINAL"), BorderLayout.NORTH);
        txtOrigen = new JTextArea();
        panelIzquierdo.add(new JScrollPane(txtOrigen), BorderLayout.CENTER);

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

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(new JLabel("TEXTO CIFRADO"), BorderLayout.NORTH);
        txtDestino = new JTextArea();
        panelDerecho.add(new JScrollPane(txtDestino), BorderLayout.CENTER);

        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelAcciones);
        panelCentral.add(panelDerecho);

        add(panelCentral, BorderLayout.CENTER);

        btnCifrar.addActionListener(e -> ejecutarCifrado(true));
        btnDescifrar.addActionListener(e -> ejecutarCifrado(false));
    }

    private void actualizarDescripcion() {
        if (estrategiaActual != null) {
            lblDescripcion.setText(" " + estrategiaActual.obtenerDescripcion());
        }
    }

    private void limpiarCampos() {
        txtOrigen.setText("");
        txtDestino.setText("");
        txtClave.setText("");
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