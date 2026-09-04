import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CajaRegistradora_v2 extends JFrame {

    // Denominaciones ordenadas de mayor a menor
    private int[] denominaciones = {100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    
    // Existencias iniciando en 0
    private int[] existencias = new int[11];

    // Componentes de la ventana
    private JLabel lblDenominacion;
    private JComboBox<String> cbDenominaciones;
    private JButton btnActualizar;
    private JTextField txtCantidadExistencia;

    private JLabel lblValorDevolver;
    private JTextField txtValorDevolver;
    private JButton btnDevolver;

    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

    // Constructor de la clase para la pantalla
    public CajaRegistradora_v2() {
        setTitle("Caja registradora");
        setSize(480, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 

        // Etiqueta para Denominación
        lblDenominacion = new JLabel("Denominación");
        lblDenominacion.setBounds(80, 20, 110, 25);
        add(lblDenominacion);

        // Combo con la lista de denominaciones 
        String[] nombresDenominaciones = {"100000", "50000", "20000", "10000", "5000", "2000", "1000", "500", "200", "100", "50"};
        cbDenominaciones = new JComboBox<>(nombresDenominaciones);
        cbDenominaciones.setBounds(200, 20, 100, 25);
        add(cbDenominaciones);

        // Botón para actualizar la existencia
        btnActualizar = new JButton("Actualizar Existencia");
        btnActualizar.setBounds(60, 60, 180, 25);
        add(btnActualizar);

        // Caja de texto para ingresar las existencias
        txtCantidadExistencia = new JTextField(String.valueOf(existencias[cbDenominaciones.getSelectedIndex()]));
        txtCantidadExistencia.setBounds(250, 60, 80, 25);
        add(txtCantidadExistencia);

        // Etiqueta para el Valor a Devolver
        lblValorDevolver = new JLabel("Valor a Devolver");
        lblValorDevolver.setBounds(60, 110, 110, 25);
        add(lblValorDevolver);

        // Caja de texto para ingresar el valor de la devuelta
        txtValorDevolver = new JTextField();
        txtValorDevolver.setBounds(180, 110, 100, 25);
        add(txtValorDevolver);

        // Botón para calcular y devolver
        btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(290, 110, 100, 25);
        add(btnDevolver);

        // Configuración de la tabla para mostrar la devuelta
        String[] cabecera = {"Cantidad", "Presentacion", "Denominación"};
        modeloTabla = new DefaultTableModel(cabecera, 0);
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setEnabled(false);
        scrollTabla = new JScrollPane(tablaResultados);
        scrollTabla.setBounds(30, 160, 400, 220);
        add(scrollTabla);

        // Muestra el cambio en la denominación que tiene guardada
        cbDenominaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int pos = cbDenominaciones.getSelectedIndex();
                txtCantidadExistencia.setText(String.valueOf(existencias[pos]));
            }
        });

        // Botón para guardar la nueva cantidad de existencias
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int pos = cbDenominaciones.getSelectedIndex();
                    int nuevaCantidad = Integer.parseInt(txtCantidadExistencia.getText());

                    if (nuevaCantidad < 0) {
                        JOptionPane.showMessageDialog(null, "La cantidad no puede ser negativa.");
                        return;
                    }

                    // Guardamos la nueva cantidad de existencias
                    if (nuevaCantidad == 0) {
                    JOptionPane.showMessageDialog(null, "por favor ingrese una cantidad mayor a 0 para la denominación $" + denominaciones[pos]);
                    
                    }
                    else {
                    existencias[pos] = nuevaCantidad > 0 ? nuevaCantidad : existencias[pos];
                    JOptionPane.showMessageDialog(null, "Existencia actualizada para $" + denominaciones[pos]);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Por favor ingresa un número entero válido.");
                }
            }
        });

        // Botón para calcular la devuelta
        btnDevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int valorADevolver = Integer.parseInt(txtValorDevolver.getText());

                    if (valorADevolver < 0) {
                        JOptionPane.showMessageDialog(null, "El valor a devolver no puede ser negativo.");
                        return;
                    }

                    if (valorADevolver == 0) {
                        JOptionPane.showMessageDialog(null, "El valor a devolver es 0, no hay nada que entregar.");
                        return;
                    }

                    // Reinicia lo que había antes en la tabla
                    modeloTabla.setRowCount(0);

                    // Copia de las existencias para probar la devuelta sin dañar los datos reales
                    int[] copiaExistencias = new int[11];
                    for (int i = 0; i < 11; i++) {
                        copiaExistencias[i] = existencias[i];
                    }

                    // Guardamos cuántos billetes/monedas de cada denominación se van a entregar
                    int[] deVuelta = new int[11];
                    int restante = valorADevolver;

                    // Ordena la devuelta de mayor a menor 
                    for (int i = 0; i < 11; i++) {
                        while (restante >= denominaciones[i] && copiaExistencias[i] > 0) {
                            restante = restante - denominaciones[i];
                            copiaExistencias[i] = copiaExistencias[i] - 1;
                            deVuelta[i] = deVuelta[i] + 1;
                        }
                    }

                    // Si sobró dinero que no se puede devolver, mostramos un mensae de error
                    if (restante > 0) {
                        JOptionPane.showMessageDialog(null, "No hay suficiente dinero o denominaciones para completar la devuelta exacta.");
                        return;
                    }

                    // Guardamos la copia como las existencias actuales
                    for (int i = 0; i < 11; i++) {
                        existencias[i] = copiaExistencias[i];
                    }

                    // Actualizamos el campo de existencia 
                    int posActual = cbDenominaciones.getSelectedIndex();
                    txtCantidadExistencia.setText(String.valueOf(existencias[posActual]));

                    // Muestra la devuelta en la tabla y en un mensaje
                    String mensaje = "La devuelta se compone de:\n";
                    for (int i = 0; i < 11; i++) {
                        if (deVuelta[i] > 0) {
                            String presentacion = "";
                            if (denominaciones[i] >= 1000) {
                                presentacion = "billete";
                            } else {
                                presentacion = "moneda";
                            }

                            Object[] fila = {deVuelta[i], presentacion, denominaciones[i]};
                            modeloTabla.addRow(fila);

                            mensaje += deVuelta[i] + " " + presentacion + " de $ " + denominaciones[i] + "\n";
                        }
                    }

                    JOptionPane.showMessageDialog(null, mensaje);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Por favor ingresa un valor numérico entero en el campo de devuelta.");
                }
            }
        });
    }
}
    