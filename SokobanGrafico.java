import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SokobanGrafico extends JFrame {
	private Sokoban sokoban;
	private ImageIcon iconoPared;
	private ImageIcon iconoCaja;
	private ImageIcon iconoPersonaje;
	private ImageIcon iconoCasillaObjetivo;
	private ImageIcon fondoTransparente;
	private ImageIcon iconoCajaEnObjetivo;
	private JPanel panelJuego;
	private JButton reiniciar;
	private int filaInicial;
	private int columnaInicial;
	private boolean[][] cajasIniciales;
	private int contador;

	public SokobanGrafico() {
		this.contador = 0;
		setFocusable(true);
		addKeyListener(movimiento);
		requestFocusInWindow();

		sokoban = new Sokoban();
		filaInicial = sokoban.getFilaSokoban();
		columnaInicial = sokoban.getColumnaSokoban();
		cajasIniciales = new boolean[sokoban.getCajas().length][sokoban.getCajas()[0].length];
		for (int i = 0; i < sokoban.getCajas().length; i++) {
			for (int j = 0; j < sokoban.getCajas()[0].length; j++) {
				cajasIniciales[i][j] = sokoban.getCajas()[i][j];
			}
		}

		iconoPared = new ImageIcon("pixel_wall_20x20.png");
		iconoCaja = new ImageIcon("pixel_box_20x20.png");
		iconoPersonaje = new ImageIcon("pixel_character_20x20.png");
		iconoCasillaObjetivo = new ImageIcon("pixel_target_20x20.png");
		fondoTransparente = new ImageIcon("pixel_empty_20x20.png");
		iconoCajaEnObjetivo = new ImageIcon("pixel_box_on_target_20x20.png");

		Container gui = getContentPane();
		gui.setLayout(new BorderLayout());

		JPanel contenedorPrincipal = new JPanel();
		contenedorPrincipal.setLayout(new GridBagLayout());

		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(11, 19));

		for (int j = 0; j < sokoban.getParedes().length; j++) {
			for (int k = 0; k < sokoban.getParedes()[0].length; k++) {

				if (j == sokoban.getFilaSokoban() && k == sokoban.getColumnaSokoban()) {
					panelJuego.add(new JLabel(iconoPersonaje));
				} else if (sokoban.getParedes()[j][k] == true) {
					panelJuego.add(new JLabel(iconoPared));
				} else if (sokoban.getCasillasObjetivos()[j][k] == true && sokoban.getCajas()[j][k] == true) {
					panelJuego.add(new JLabel(iconoCajaEnObjetivo));
				} else if (sokoban.getCajas()[j][k] == true) {
					panelJuego.add(new JLabel(iconoCaja));
				} else if (sokoban.getCasillasObjetivos()[j][k] == true) {
					panelJuego.add(new JLabel(iconoCasillaObjetivo));
				} else {
					panelJuego.add(new JLabel(fondoTransparente));
				}
			}
		}

		GridBagConstraints panelJuegoGbc = new GridBagConstraints();
		panelJuegoGbc.gridx = 0;
		panelJuegoGbc.gridy = 0;
		panelJuegoGbc.gridheight = 4;
		panelJuegoGbc.gridwidth = 3;
		panelJuegoGbc.insets = new Insets(10, 10, 10, 10);
		contenedorPrincipal.add(panelJuego, panelJuegoGbc);

		reiniciar = new JButton("Reiniciar");
		reiniciar.addActionListener(accionReiniciar);
		GridBagConstraints reiniciarGbc = new GridBagConstraints();
		reiniciarGbc.gridx = 0;
		reiniciarGbc.weightx = 0;
		reiniciarGbc.gridy = 4;
		reiniciarGbc.weightx = 1;
		reiniciarGbc.anchor = GridBagConstraints.EAST;
		contenedorPrincipal.add(reiniciar, reiniciarGbc);

		JPanel controles = new JPanel();
		controles.setLayout(new BorderLayout());

		arriba = new JButton("W");

		controles.add(arriba, BorderLayout.NORTH);

		abajo = new JButton("S");
		controles.add(abajo, BorderLayout.SOUTH);

		derecha = new JButton("D");
		controles.add(derecha, BorderLayout.EAST);
		izquierda = new JButton("A");
		controles.add(izquierda, BorderLayout.WEST);

		GridBagConstraints controlesGbc = new GridBagConstraints();
		controlesGbc.gridx = 2;
		controlesGbc.gridy = 4;
		controlesGbc.weightx = 1;
		controlesGbc.anchor = GridBagConstraints.WEST;
		contenedorPrincipal.add(controles, controlesGbc);

		JLabel tituloJuego = new JLabel("Bienvenido a Sokoban", JLabel.CENTER);
		tituloJuego.setFont(new Font("Arial", 1, 35));
		gui.add(tituloJuego, BorderLayout.NORTH);

		etiquetaContador = new JLabel("Número de movimientos: " + String.valueOf(contador), JLabel.CENTER);
		GridBagConstraints contadorGbc = new GridBagConstraints();
		contadorGbc.gridx = 1;
		contadorGbc.gridy = 4;
		contadorGbc.anchor = GridBagConstraints.CENTER;
		contadorGbc.fill = GridBagConstraints.NONE;
		contadorGbc.weightx = 1;

		contenedorPrincipal.add(etiquetaContador, contadorGbc);

		gui.add(contenedorPrincipal, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setVisible(true);

	}

	private KeyListener movimiento = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			arriba.setBackground(null);
			abajo.setBackground(null);
			izquierda.setBackground(null);
			derecha.setBackground(null);

		}

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyChar() == 'w') {
				if (sokoban.sePuedeMover(Sokoban.DIRECCIÓN_ARRIBA)) {
					sokoban.actualizarCajas(Sokoban.DIRECCIÓN_ARRIBA);
					sokoban.setFilaSokoban(sokoban.getFilaSokoban() - 1);

				}

				arriba.setBackground(Color.red);
			} else if (e.getKeyChar() == 'a') {

				if (sokoban.sePuedeMover(Sokoban.DIRECCIÓN_IZQUIERDA)) {
					sokoban.actualizarCajas(Sokoban.DIRECCIÓN_IZQUIERDA);
					sokoban.setColumnaSokoban(sokoban.getColumnaSokoban() - 1);
				}

				izquierda.setBackground(Color.red);

			} else if (e.getKeyChar() == 's') {

				if (sokoban.sePuedeMover(Sokoban.DIRECCIÓN_ABAJO)) {
					sokoban.actualizarCajas(Sokoban.DIRECCIÓN_ABAJO);
					sokoban.setFilaSokoban(sokoban.getFilaSokoban() + 1);
				}
				abajo.setBackground(Color.red);

			} else if (e.getKeyChar() == 'd') {

				if (sokoban.sePuedeMover(Sokoban.DIRECCIÓN_DERECHA)) {
					sokoban.actualizarCajas(Sokoban.DIRECCIÓN_DERECHA);
					sokoban.setColumnaSokoban(sokoban.getColumnaSokoban() + 1);
				}
				derecha.setBackground(Color.red);

			}
			contador++;
			etiquetaContador.setText("Número de movimientos: " + String.valueOf(contador));
			actualizarTablero();
			panelJuego.revalidate();

		}
	};

	ActionListener accionReiniciar = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			sokoban.setFilaSokoban(filaInicial);
			sokoban.setColumnaSokoban(columnaInicial);

			for (int i = 0; i < sokoban.getCajas().length; i++) {
				for (int j = 0; j < sokoban.getCajas()[0].length; j++) {
					sokoban.getCajas()[i][j] = cajasIniciales[i][j];
				}
			}

			panelJuego.removeAll();
			actualizarTablero();
			panelJuego.revalidate();
			contador = 0;
			etiquetaContador.setText("Número de movimientos: " + String.valueOf(contador));

			setFocusable(true);
			requestFocusInWindow();

		}
	};
	private JButton arriba;
	private JButton abajo;
	private JButton derecha;
	private JButton izquierda;
	private JLabel etiquetaContador;

	private void actualizarTablero() {
		panelJuego.removeAll();
		for (int j = 0; j < sokoban.getParedes().length; j++) {
			for (int k = 0; k < sokoban.getParedes()[0].length; k++) {

				if (j == sokoban.getFilaSokoban() && k == sokoban.getColumnaSokoban()) {
					panelJuego.add(new JLabel(iconoPersonaje));
				} else if (sokoban.getParedes()[j][k] == true) {
					panelJuego.add(new JLabel(iconoPared));
				} else if (sokoban.getCasillasObjetivos()[j][k] == true && sokoban.getCajas()[j][k] == true) {
					panelJuego.add(new JLabel(iconoCajaEnObjetivo));
				} else if (sokoban.getCajas()[j][k] == true) {
					panelJuego.add(new JLabel(iconoCaja));
				} else if (sokoban.getCasillasObjetivos()[j][k] == true) {
					panelJuego.add(new JLabel(iconoCasillaObjetivo));
				} else {
					panelJuego.add(new JLabel(fondoTransparente));
				}
			}
		}
	}

	public static void main(String[] args) {
		new SokobanGrafico();
	}

}
