package proyectoSokonban;

import java.util.Scanner;

public class Sokoban {

	private boolean[][] paredes;
	private boolean[][] cajas;
	private boolean[][] casillasObjetivos;
	private int filaSokoban;
	private int columnaSokoban;
	private boolean salir;

	public Sokoban() {
		this.setParedes(new boolean[][] {
				{ false, false, false, false, true, true, true, true, true, false, false, false, false, false, false,
						false, false, false, false },
				{ false, false, false, false, true, false, false, false, true, false, false, false, false, false, false,
						false, false, false, false },
				{ false, false, false, false, true, false, false, false, true, false, false, false, false, false, false,
						false, false, false, false },
				{ false, false, true, true, true, false, false, false, true, true, false, false, false, false, false,
						false, false, false, false },
				{ false, false, true, false, false, false, false, false, false, true, false, false, false, false, false,
						false, false, false, false },
				{ true, true, true, false, true, false, true, true, false, true, false, false, false, true, true, true,
						true, true, true },
				{ true, false, false, false, true, false, true, true, false, true, true, true, true, true, false, false,
						false, false, true },
				{ true, false, false, false, false, false, false, false, false, false, false, false, false, false,
						false, false, false, false, true },
				{ true, true, true, true, true, false, true, true, true, false, true, false, true, true, false, false,
						false, false, true },
				{ false, false, false, false, true, false, false, false, false, false, true, true, true, true, true,
						true, true, true, true },
				{ false, false, false, false, true, true, true, true, true, true, true, false, false, false, false,
						false, false, false, false } });

		setCajas(new boolean[getParedes().length][getParedes()[0].length]);
		getCajas()[2][5] = true;
		getCajas()[3][7] = true;
		getCajas()[4][7] = true;
		getCajas()[4][5] = true;
		getCajas()[7][2] = true;
		getCajas()[7][5] = true;

		setCasillasObjetivos(new boolean[getParedes().length][getParedes()[0].length]);
		getCasillasObjetivos()[6][16] = true;
		getCasillasObjetivos()[6][17] = true;
		getCasillasObjetivos()[7][16] = true;
		getCasillasObjetivos()[7][17] = true;
		getCasillasObjetivos()[8][16] = true;
		getCasillasObjetivos()[8][17] = true;

		setFilaSokoban(8);
		setColumnaSokoban(11);

		this.setSalir(false);

	}

	public static final int DIRECCIÓN_ARRIBA = 1;
	public static final int DIRECCIÓN_DERECHA = 2;
	public static final int DIRECCIÓN_ABAJO = 3;
	public static final int DIRECCIÓN_IZQUIERDA = 4;

	public void actualizarCajas( int dirección) {
		switch (dirección) {
		case DIRECCIÓN_ARRIBA -> {
			if (getCajas()[getFilaSokoban() - 1][getColumnaSokoban()]) {
				getCajas()[getFilaSokoban() - 1][getColumnaSokoban()] = false;
				getCajas()[getFilaSokoban() - 2][getColumnaSokoban()] = true;
			}
		}
		case DIRECCIÓN_IZQUIERDA -> {
			if (getCajas()[getFilaSokoban()][getColumnaSokoban() - 1]) {
				getCajas()[getFilaSokoban()][getColumnaSokoban() - 1] = false;
				getCajas()[getFilaSokoban()][getColumnaSokoban() - 2] = true;
			}
		}
		case DIRECCIÓN_ABAJO -> {
			if (getCajas()[getFilaSokoban() + 1][getColumnaSokoban()]) {
				getCajas()[getFilaSokoban() + 1][getColumnaSokoban()] = false;
				getCajas()[getFilaSokoban() + 2][getColumnaSokoban()] = true;
			}
		}
		case DIRECCIÓN_DERECHA -> {
			if (getCajas()[getFilaSokoban()][getColumnaSokoban() + 1]) {
				getCajas()[getFilaSokoban()][getColumnaSokoban() + 1] = false;
				getCajas()[getFilaSokoban()][getColumnaSokoban() + 2] = true;
			}
		}
		}
	}

	public boolean sePuedeMover(int dirección) {
		boolean sePuedeMover = true;
		if (dirección == DIRECCIÓN_ARRIBA) {
			if (getParedes()[getFilaSokoban() - 1][getColumnaSokoban()])
				sePuedeMover = false;
			if (getCajas()[getFilaSokoban() - 1][getColumnaSokoban()]
					&& (getCajas()[getFilaSokoban() - 2][getColumnaSokoban()] || getParedes()[getFilaSokoban() - 2][getColumnaSokoban()]))
				sePuedeMover = false;
		}
		if (dirección == DIRECCIÓN_ABAJO) {
			if (getParedes()[getFilaSokoban() + 1][getColumnaSokoban()])
				sePuedeMover = false;
			if (getCajas()[getFilaSokoban() + 1][getColumnaSokoban()]
					&& (getCajas()[getFilaSokoban() + 2][getColumnaSokoban()] || getParedes()[getFilaSokoban() + 2][getColumnaSokoban()]))
				sePuedeMover = false;
		}
		if (dirección == DIRECCIÓN_DERECHA) {
			if (getParedes()[getFilaSokoban()][getColumnaSokoban() + 1])
				sePuedeMover = false;
			if (getCajas()[getFilaSokoban()][getColumnaSokoban() + 1]
					&& (getCajas()[getFilaSokoban()][getColumnaSokoban() + 2] || getParedes()[getFilaSokoban()][getColumnaSokoban() + 2]))
				sePuedeMover = false;

		}
		if (dirección == DIRECCIÓN_IZQUIERDA) {
			if (getParedes()[getFilaSokoban()][getColumnaSokoban() - 1])
				sePuedeMover = false;
			if (getCajas()[getFilaSokoban()][getColumnaSokoban() - 1]
					&& (getCajas()[getFilaSokoban()][getColumnaSokoban() - 2] || getParedes()[getFilaSokoban()][getColumnaSokoban() - 2]))
				sePuedeMover = false;
		}

		return sePuedeMover;
	}

	public void mostrarNivel() {
		for (int f = 0; f < getParedes().length; f++) {
			for (int c = 0; c < getParedes()[0].length; c++) {
				if (getParedes()[f][c]) {
					System.out.print('\u2593');
					System.out.print('\u2593');
				} else if (getCajas()[f][c]) {
					System.out.print("[]");
				} else if (f == getFilaSokoban() && c == getColumnaSokoban())
					System.out.print("()");
				else if (getCasillasObjetivos()[f][c])
					System.out.print("<>");
				else // no hay nada
					System.out.print("  ");
			}
			System.out.println();
		}
	}

	public boolean hasGanado() {
		for (int f = 0; f < getCajas().length; f++)
			for (int c = 0; c < getCajas()[0].length; c++)
				if (getCajas()[f][c] && !getCasillasObjetivos()[f][c])
					return false;
		return true;
	}

	public boolean isSalir() {
		return salir;
	}

	public void setSalir(boolean salir) {
		this.salir = salir;
	}

	public int getColumnaSokoban() {
		return columnaSokoban;
	}

	public void setColumnaSokoban(int columnaSokoban) {
		this.columnaSokoban = columnaSokoban;
	}

	public int getFilaSokoban() {
		return filaSokoban;
	}

	public void setFilaSokoban(int filaSokoban) {
		this.filaSokoban = filaSokoban;
	}

	public boolean[][] getCasillasObjetivos() {
		return casillasObjetivos;
	}

	public void setCasillasObjetivos(boolean[][] casillasObjetivos) {
		this.casillasObjetivos = casillasObjetivos;
	}

	public boolean[][] getCajas() {
		return cajas;
	}

	public void setCajas(boolean[][] cajas) {
		this.cajas = cajas;
	}

	public boolean[][] getParedes() {
		return paredes;
	}

	public void setParedes(boolean[][] paredes) {
		this.paredes = paredes;
	}
	

}
