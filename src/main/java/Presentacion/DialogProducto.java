/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Datos.ProductoDAO;
import Dominio.Producto;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Usuario
 */
public class DialogProducto extends JDialog {

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCategoria;

    private Producto producto;

    public DialogProducto(JFrame parent, Producto productoEditar) {

        super(parent, true);
        this.producto = productoEditar;
        setTitle(producto == null ?
                "Añadir Producto" :
                "Editar Producto");

        setSize(400,300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4,2,10,10));

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtCategoria = new JTextField();

        add(new JLabel("Nombre"));
        add(txtNombre);
        add(new JLabel("Precio"));
        add(txtPrecio);
        add(new JLabel("Categoría"));
        add(txtCategoria);
        JButton guardar = new JButton("Guardar");
        add(new JLabel());
        add(guardar);

        if(producto != null) {
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecioBase()));
            txtCategoria.setText(producto.getCategoria());
        }

        guardar.addActionListener(e -> guardarProducto());
    }

    private void guardarProducto() {

        ProductoDAO dao = new ProductoDAO();

        if(producto == null) {
            producto = new Producto();
        }

        producto.setNombre(txtNombre.getText());
        producto.setPrecioBase(
                Double.parseDouble(txtPrecio.getText())
        );

        producto.setCategoria(txtCategoria.getText());
        boolean exito;

        if(producto.getIdProducto() == 0) {
            exito = dao.agregar(producto);
        } else {
            exito = dao.actualizar(producto);
        }

        if(exito) {
            JOptionPane.showMessageDialog(this, "Producto guardado");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar");
        }
    }
}