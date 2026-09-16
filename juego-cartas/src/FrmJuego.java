import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    // variables globales
    private JPanel pnlJugador1, pnlJugador2;
    private JTabbedPane tpJugadores;

    // metodo constructor
    public FrmJuego() {
        setSize(500, 300);
        setTitle("Juguemos al Apuntado!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        // agregar el panel de pestañas
        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 50, 470, 200);
        add(tpJugadores);

        // agregar el panel para la primera pestaña
        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(0, 255, 0));
        tpJugadores.add("Esteban²", pnlJugador1);

        // agregar el panel para la segunda pestaña
        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));
        tpJugadores.add("Jonatan Alvares", pnlJugador2);

        // eventos
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                repartir();
            }
        });

        btnVerificar.addActionListener(evento -> {
            verificar();
        });

    }

    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();

    private void repartir() {
        jugador1.repartir();
        jugador2.repartir();

        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificar() {
        String mensaje = "";
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                mensaje = jugador1.getGrupos();
                break;
            case 1:
                mensaje = jugador2.getGrupos();
                break;
        }
        if (!mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }
}
