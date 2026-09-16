import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int DISTANCIA = 40;

    private final String[] GRUPO_TEXTO = {
            "", "", "Par", "Terna", "Cuarta", "Quinta", "Sexta", "Séptima", "Octava", "Novena", "Décima"
    };

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.setLayout(null);
        pnl.removeAll();
        int posicion = MARGEN + TOTAL_CARTAS * DISTANCIA;
        for (Carta carta : cartas) {
            posicion -= DISTANCIA;
            carta.mostrar(pnl, posicion, MARGEN);
        }
        pnl.repaint();
    }

    public String getGrupos() {
        boolean[] usada = new boolean[TOTAL_CARTAS];

        String gruposPorNumero = obtenerGruposPorNumero(usada);
        String gruposPorPinta = obtenerGruposPorPinta(usada);
        String sobrantes = obtenerSobrantes(usada);
        int puntos = calcularPuntos(usada);

        String resultado = gruposPorNumero;
        if (!gruposPorNumero.isEmpty() && !gruposPorPinta.isEmpty()) {
            resultado += "\n";
        }
        resultado += gruposPorPinta;

        if (resultado.isEmpty()) {
            resultado = "No se encontraron grupos\n";
        }
        resultado += "\n";

        resultado += "Sobran:\n";
        if (sobrantes.isEmpty()) {
            resultado += "Ninguna\n";
        } else {
            resultado += sobrantes;
        }
        resultado += "\n";

        resultado += "Puntos:\n" + puntos;

        return resultado;
    }

    // busca cartas con el mismo numero (pares, ternas, etc)
    private String obtenerGruposPorNumero(boolean[] usada) {
        String resultado = "";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                String texto = "";
                for (int j = 0; j < TOTAL_CARTAS; j++) {
                    if (cartas[j].getNombre().ordinal() == i) {
                        texto = cartas[j].getNombreTexto();
                        usada[j] = true;
                    }
                }
                resultado += GRUPO_TEXTO[contadores[i]] + " de " + texto + "\n";
            }
        }

        return resultado;
    }

    // busca cartas de la misma pinta con numeros consecutivos
    private String obtenerGruposPorPinta(boolean[] usada) {
        String resultado = "";
        int totalNombres = NombreCarta.values().length;

        for (Pinta pinta : Pinta.values()) {
            boolean[] presente = new boolean[totalNombres];
            int[] indiceCarta = new int[totalNombres];

            for (int j = 0; j < TOTAL_CARTAS; j++) {
                if (cartas[j].getPinta() == pinta) {
                    int rango = cartas[j].getNombre().ordinal();
                    presente[rango] = true;
                    indiceCarta[rango] = j;
                }
            }

            int i = totalNombres - 1;
            while (i >= 0) {
                if (presente[i]) {
                    int rangoAlto = i;
                    int cantidad = 0;
                    while (i >= 0 && presente[i]) {
                        cantidad++;
                        i--;
                    }
                    int rangoBajo = i + 1;

                    if (cantidad >= 2) {
                        for (int k = rangoBajo; k <= rangoAlto; k++) {
                            usada[indiceCarta[k]] = true;
                        }
                        String textoBajo = cartas[indiceCarta[rangoBajo]].getNombreTexto();
                        String textoAlto = cartas[indiceCarta[rangoAlto]].getNombreTexto();
                        String textoPinta = cartas[indiceCarta[rangoAlto]].getPintaTexto();
                        resultado += GRUPO_TEXTO[cantidad] + " de " + textoPinta + " de " + textoBajo + " a "
                                + textoAlto + "\n";
                    }
                } else {
                    i--;
                }
            }
        }

        return resultado;
    }

    // arma la lista de cartas que no quedaron en ningun grupo
    private String obtenerSobrantes(boolean[] usada) {
        String resultado = "";
        for (int i = TOTAL_CARTAS - 1; i >= 0; i--) {
            if (!usada[i]) {
                resultado += cartas[i].getNombreTexto() + " de " + cartas[i].getPintaTexto() + "\n";
            }
        }
        return resultado;
    }

    // suma los puntos de las cartas que sobraron
    private int calcularPuntos(boolean[] usada) {
        int puntos = 0;
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!usada[i]) {
                puntos += cartas[i].getValor();
            }
        }
        return puntos;
    }

}
