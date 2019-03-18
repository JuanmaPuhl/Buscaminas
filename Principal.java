import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Principal {

	private JFrame frame;
	private boolean[][] seleccionado;
	private int columnas = 20;
	private int filas = 20;
	private int minas = 80;
	private int cantFrames = (columnas * filas) - minas;
	private int tam = 15;
	private logica l;
	private JLabel[][] arreglo; 
	private boolean [][] auxiliar;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		initialize();
	}

	private void initialize() {
		//marcarVacios(0,0);
//		JTextField columns = new JTextField();
//		JTextField rows = new JTextField();
//		JTextField cantMinas = new JTextField();
//		Object[] input = { "Columnas:", columns, "Filas:", rows, "CantidadMinas:", cantMinas };
//
//		while (columns.getText().trim().equals("") || rows.getText().trim().equals("")
//				|| cantMinas.getText().trim().equals("")) {
//			JOptionPane.showMessageDialog(null, input);
//			try {
//				columnas = Integer.parseInt(columns.getText());
//				filas = Integer.parseInt(rows.getText());
//				minas = Integer.parseInt(cantMinas.getText());
//				if(columnas<10 || filas<10 || minas<10) {
//					columns.setText("");
//					rows.setText("");
//					cantMinas.setText("");
//				}
//			} catch (NumberFormatException err) {
//				columns.setText("");
//				rows.setText("");
//				cantMinas.setText("");
//			}
//		}
		auxiliar=new boolean[columnas][filas];
		cantFrames = (columnas * filas) - minas;
		frame = new JFrame("Buscaminas");
		frame.setBounds(0, 0, tam * columnas + 200, tam * filas + 30);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		l = new logica(columnas, filas, minas);
		arreglo = new JLabel[columnas][filas];
		frame.getContentPane().setLayout(null);
		seleccionado = new boolean[filas][columnas];
		JLabel remainingLbl = new JLabel("Restantes:");
		remainingLbl.setBounds(tam * columnas + 25, -50, 300, 200);
		remainingLbl.setForeground(Color.BLACK);
		remainingLbl.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		frame.getContentPane().add(remainingLbl, 0);
		JLabel remaining = new JLabel("" + cantFrames);
		remaining.setBounds(tam * columnas + 70, -20, 100, 200);
		remaining.setForeground(Color.BLACK);
		remaining.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		frame.getContentPane().add(remaining, 0);

		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {

				seleccionado[i][j] = false;
				auxiliar[i][j]=false;
				arreglo[i][j] = new JLabel("");
				arreglo[i][j].setBackground(Color.LIGHT_GRAY);
				arreglo[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				arreglo[i][j].setBounds(tam * i, tam * j, tam, tam);
				frame.getContentPane().add(arreglo[i][j], 0);
				arreglo[i][j].setFocusable(false);
				arreglo[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				int aux = i;
				int aux2 = j;
				arreglo[i][j].addMouseListener(new MouseListener() {
					
					public void mouseClicked(MouseEvent arg0) {
					}

					
					public void mouseEntered(MouseEvent arg0) {
					}

					public void mouseExited(MouseEvent arg0) {
					}

					public void mousePressed(MouseEvent arg0) {
						if (!auxiliar[aux][aux2]) {
							if ((arg0.getButton()) == MouseEvent.BUTTON1) {
								String valor = "";
								valor = "" + l.obtenerMatrizMinas()[aux2][aux];
								if (valor.trim().equals("")) {
									valor = "" + l.obtenerMatrizNumeros()[aux2][aux];
								}
								setearImagen(aux,aux2);
								if(valor.trim().equals("0")) {
									marcarVacios(aux,aux2);
								}
								auxiliar[aux][aux2]=true;
								arreglo[aux][aux2].setFont(new Font("Segoe UI", Font.PLAIN, 6));
								arreglo[aux][aux2].setBackground(Color.DARK_GRAY);
								
								if (valor.equals("*")) {
									JOptionPane.showMessageDialog(null, "HAS PERDIDO", "SE TERMINO EL JUEGO",
											JOptionPane.INFORMATION_MESSAGE,new ImageIcon(getClass().getResource("/Resources/icons8_Sad_50px.png")));
									System.exit(0);
								} else {
									cantFrames--;
									remaining.setText("" + cantFrames);
									if (cantFrames == 0) {
										JOptionPane.showMessageDialog(null, "HAS GANADO", "SE TERMINO EL JUEGO",
												JOptionPane.INFORMATION_MESSAGE,new ImageIcon(getClass().getResource("/Resources/icons8_Trophy_48px.png")));
										System.exit(0);
									}
								}
							} else {
								if ((arg0.getButton()) == MouseEvent.BUTTON3) {
									if (!seleccionado[aux][aux2]) {
										arreglo[aux][aux2].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_Flag_Filled__20px.png")));
										seleccionado[aux][aux2] = true;
									} else {
										arreglo[aux][aux2].setIcon(null);
										seleccionado[aux][aux2] = false;
										
									}
								}
							}
						}
					}
					public void mouseReleased(MouseEvent arg0) {
					}
				});
			}
		}
	}
	
	private void marcarVacios(int i,int j) {
		for (int m = -1; m < 2; m++) {
			for (int n = -1; n < 2; n++) {
				if (i + m >= 0 && i + m < filas && j + n >= 0 && j + n < columnas) {
					if (l.obtenerMatrizNumeros()[j+n][i+m]==0 && arreglo[i+m][j+n].getIcon()==null && l.obtenerMatrizMinas()[j+n][i+m]!='*') {
						setearImagen(i+m,j+n);
						marcarVacios(i+m,j+n);
						cantFrames--;
						auxiliar[i+m][j+n]=true;
					}
					else {
						if(arreglo[i+m][j+n].getIcon()==null) {
							setearImagen(i+m,j+n);
							cantFrames--;
							auxiliar[i+m][j+n]=true;
						}
					}
			}
		}
		}

	}
	private void setearImagen(int i,int j) {
		String valor = "";
		valor = "" + l.obtenerMatrizMinas()[j][i];
		if (valor.trim().equals("")) {
			valor = "" + l.obtenerMatrizNumeros()[j][i];
		}
		
		if(valor.trim().equals("0"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_0_20px.png")));
		if(valor.trim().equals("1"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_1_20px.png")));
		if(valor.trim().equals("2"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_2_20px.png")));
		if(valor.trim().equals("3"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_3_20px.png")));
		if(valor.trim().equals("4"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_4_20px.png")));
		if(valor.trim().equals("5"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_5_20px.png")));
		if(valor.trim().equals("6"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_6_20px.png")));
		if(valor.trim().equals("7"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_7_20px.png")));
		if(valor.trim().equals("8"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_8_20px.png")));
		if(valor.trim().equals("9"))	
			arreglo[i][j].setIcon(new ImageIcon(getClass().getResource("/Resources/icons8_9_20px.png")));
		if(valor.trim().equals("*"))	
			arreglo[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/Resources/icons8_Naval_Mine_10px.png")));
		
	}
	
}
