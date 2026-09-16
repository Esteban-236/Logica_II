import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Carta {

    private static final String[] NOMBRES_TEXTO = {
            "As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    };

    private static final String[] PINTAS_TEXTO = {
            "Trébol", "Pica", "Corazón", "Diamante"
    };

    private int indice;

    // metodo constructor
    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        String rutaImagen = "imagenes/CARTA" + indice + ".JPG";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(rutaImagen));

        JLabel lblCarta = new JLabel(imgCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());
        pnl.add(lblCarta);

        // evento para mostrar la identidad de la carta
        lblCarta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                JOptionPane.showMessageDialog(null, getNombre() + " de " + getPinta());
            }
        });
    }

    public Pinta getPinta() {

        if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13;
        }
        return NombreCarta.values()[residuo - 1];
    }

    public String getNombreTexto() {
        return NOMBRES_TEXTO[getNombre().ordinal()];
    }

    public String getPintaTexto() {
        return PINTAS_TEXTO[getPinta().ordinal()];
    }

    public int getValor() {
        int valor = getNombre().ordinal() + 1;
        if (valor > 10) {
            valor = 10;
        }
        return valor;
    }
}
