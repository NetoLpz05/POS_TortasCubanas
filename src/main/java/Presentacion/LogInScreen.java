/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Datos.CajeroDAO;
import Dominio.Cajero;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class LogInScreen extends JFrame{
    private String pinIngresado = "";

    private JLabel pinLabel;

    public LogInScreen() {
        setTitle("Iniciar Sesión");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pinLabel = new JLabel("••••", SwingConstants.CENTER);
        pinLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        add(pinLabel, BorderLayout.NORTH);
        JPanel teclado = new JPanel(new GridLayout(4,3,10,10));
        
        for (int i = 1; i <= 9; i++) {
            agregarBotonNumero(teclado, String.valueOf(i));
        }
        
        agregarBotonNumero(teclado, "0");
        JButton borrar = new JButton("⌫");
        borrar.addActionListener(e -> borrarNumero());
        teclado.add(borrar);
        add(teclado, BorderLayout.CENTER);
        JButton login = new JButton("Iniciar Sesión");
        login.addActionListener(e -> iniciarSesion());
        add(login, BorderLayout.SOUTH);
    }

    private void agregarBotonNumero(JPanel panel, String numero) {
        JButton btn = new JButton(numero);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 30));
        btn.addActionListener(e -> {

            if (pinIngresado.length() < 4) {
                pinIngresado += numero;
                actualizarPin();
            }
        });

        panel.add(btn);
    }

    private void borrarNumero() {
        if (!pinIngresado.isEmpty()) {
            pinIngresado = pinIngresado.substring(0, pinIngresado.length() - 1);
            actualizarPin();
        }
    }

    private void actualizarPin() {
        pinLabel.setText("•".repeat(pinIngresado.length()));
    }

    private void iniciarSesion() {
        CajeroDAO dao = new CajeroDAO();
        Cajero cajero = dao.iniciarSesion(pinIngresado);

        if (cajero == null) {
            JOptionPane.showMessageDialog(this, "PIN incorrecto");
            pinIngresado = "";
            actualizarPin();
            return;
        }

        dispose();

        switch (cajero.getTipo().toUpperCase()) {

            case "ADMIN":
                new MenuAdministrador().setVisible(true);
                break;

            case "CAJERO":
                new Menu_Principal().setVisible(true);
                break;

            default:
                JOptionPane.showMessageDialog(this,"Tipo no válido");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LogInScreen().setVisible(true);
        });
    }
}