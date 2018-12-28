public class logica {

	private char[][] matrizMinas;
	private int[][] matrizNumeros;
	private int columnas, filas;

	public logica(int i, int j, int cantMinas) {
		columnas = i;
		filas = j;
		matrizMinas = new char[i][j];
		matrizNumeros = new int[i][j];
		ponerMinas(cantMinas);
		ponerNumeros();
	}

	public void ponerMinas(int cant) {
		int columna = 0, fila = 0;
		boolean listo = false;
		for (int i = 0; i < cant; i++) {
			listo = false;
			while (!listo) {
				columna = (int) (Math.random() * columnas - 1);
				fila = (int) (Math.random() * filas - 1);
				if (matrizMinas[columna][fila] != '*') {
					matrizMinas[columna][fila] = '*';
					listo = true;
				}

			}
		}
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				if (matrizMinas[i][j] == '*')
					System.out.print(matrizMinas[i][j] + " ");
				else
					System.out.print("- ");
			}
			System.out.println();
		}
	}

	public void ponerNumeros() {
		int cant = 0;
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				if (matrizMinas[i][j] != '*') {
					cant = obtenerMinasAdyacentes(i, j);
					matrizNumeros[i][j] = cant;
				}
			}
		}
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				System.out.print(matrizNumeros[i][j] + " ");
			}
			System.out.println();
		}

	}

	public int[][] obtenerMatrizNumeros() {
		return matrizNumeros;
	}

	public char[][] obtenerMatrizMinas() {
		return matrizMinas;
	}

	private int obtenerMinasAdyacentes(int i, int j) {
		int cant = 0;
		for (int m = -1; m < 2; m++) {
			for (int n = -1; n < 2; n++) {
				if (i + m >= 0 && i + m < filas && j + n >= 0 && j + n < columnas)
					if (matrizMinas[i + m][j + n] == '*') {
						cant++;
					}
			}
		}

		return cant;
	}

}
