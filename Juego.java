package proyectoSokonban;

import java.util.Scanner;

public class Juego {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		Sokoban juego = new Sokoban();

		while (!juego.isSalir()) {
//		    mostrar estado
			juego.mostrarNivel();
//		    pedir inputs
			char entradaTeclado = scanner.nextLine().charAt(0);
//		    actualizar estado
			switch (entradaTeclado) {
			case ('w') -> {
				if (juego.sePuedeMover(Sokoban.DIRECCIÓN_ARRIBA)) {
					juego.actualizarCajas(Sokoban.DIRECCIÓN_ARRIBA);
					juego.setFilaSokoban(juego.getFilaSokoban() - 1);
				}

			}
			case 'a' -> {
				if (juego.sePuedeMover(Sokoban.DIRECCIÓN_IZQUIERDA)) {
					juego.actualizarCajas(Sokoban.DIRECCIÓN_IZQUIERDA);
					juego.setColumnaSokoban(juego.getColumnaSokoban() - 1);
				}
			}
			case 's' -> {
				if (juego.sePuedeMover(Sokoban.DIRECCIÓN_ABAJO)) {
					juego.actualizarCajas(Sokoban.DIRECCIÓN_ABAJO);
					juego.setFilaSokoban(juego.getFilaSokoban() + 1);
				}
			}
			case 'd' -> {
				if (juego.sePuedeMover(Sokoban.DIRECCIÓN_DERECHA)) {
					juego.actualizarCajas(Sokoban.DIRECCIÓN_DERECHA);
					juego.setColumnaSokoban(juego.getColumnaSokoban() + 1);
				}
			}
			case 'e' -> {
				juego.setSalir(true);
			}
			}

			if (juego.hasGanado()) {
				juego.mostrarNivel();
				System.out.println("Enhorabuena! has ganado!! :O");
				juego.setSalir(true);
			}
		}
	}

}
