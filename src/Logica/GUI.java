package Logica;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private Logica l;
	private JPanel panelTitulo; 
	private JPanel panelInfo;   
	private JPanel panelButacas;
	private JPanel panelReservar; 
	private JPanel panelReservaInfo; 
	private JPanel panelCeldas; 
	private JButton [][] botones; 
	private JButton botonReservar;
	private JComboBox<String> peliculas;
	private JComboBox<String> cantEntradas;
	private JLabel pelicula;
	private JLabel entradas;
	private JLabel total;


	public GUI(){ 
		super("Cinema");
		l = new Logica();    

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(800, 800));
		setFocusable(true);
		setLocationRelativeTo(null);

		// Icono
		ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/pochoclos.png"));
		setIconImage(icono.getImage());

		inicializarGUI();
		setVisible(true);


	}

	private void inicializarGUI(){

		this.setLayout(new BorderLayout());

		pelicula = new JLabel("Seleccione la película");
		entradas = new JLabel("Seleccione cantidad de entradas");
		total = new JLabel("Total a pagar: $0.00");

		botonReservar= new JButton("Comprar");
		botonReservar.addActionListener(new OyenteBotonReservar());
		botonReservar.setEnabled(false); //inicialmente se encuentra deshabilitado

		panelTitulo= new JPanel();
		panelTitulo.setPreferredSize(new Dimension(800,120));
		JLabel lbImage = new JLabel();
		lbImage.setSize(new Dimension(800,120));
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/Imagenes/banner.png"));
		lbImage.setIcon(imgIcon);
		panelTitulo.add(lbImage);
		panelTitulo.setBackground(new Color(255,255,255));

		panelReservaInfo = new JPanel();
		panelReservaInfo.setPreferredSize(new Dimension(800,200));
		panelReservaInfo.setBackground(new Color(255,255,255));
		panelReservaInfo.setLayout(new BorderLayout());

		JPanel panelPeliculas = new JPanel(new FlowLayout());
		panelPeliculas.setBackground(new Color(255,255,255));
		inicializarPeliculas();
		panelPeliculas.add(pelicula);
		panelPeliculas.add(peliculas);

		JPanel panelEntradas = new JPanel(new FlowLayout());
		panelEntradas.setBackground(new Color(255,255,255));
		inicializarEntradas();
		panelEntradas.add(entradas);
		panelEntradas.add(cantEntradas);
		panelEntradas.add(total);

		JPanel infoEntradas = new JPanel(new GridLayout(2,1));
		infoEntradas.setBackground(new Color(255,255,255));
		infoEntradas.add(panelEntradas);
		infoEntradas.add(total);

		panelReservaInfo.add(panelPeliculas,BorderLayout.WEST);
		panelReservaInfo.add(infoEntradas,BorderLayout.CENTER);

		panelInfo= new JPanel();
		panelInfo.setLayout(new GridLayout(2,1));
		panelInfo.setPreferredSize(new Dimension(800,280));
		panelInfo.setBackground(new Color(0,255,0));
		panelInfo.add(panelTitulo);
		panelInfo.add(panelReservaInfo);

		panelCeldas= new JPanel(new FlowLayout());
		panelButacas = new JPanel();
		panelButacas.setPreferredSize(new Dimension(400,375));
		panelButacas.setLayout(new GridLayout(15,15)); 
		inicializarBotones(); 
		panelCeldas.add(panelButacas);

		panelReservar= new JPanel();
		panelReservar.setPreferredSize(new Dimension(800,80));
		panelReservar.setBackground(new Color(255,255,255));
		panelReservar.add(botonReservar);

		this.add(panelInfo,BorderLayout.NORTH);
		this.add(panelCeldas,BorderLayout.CENTER);
		this.add(panelReservar,BorderLayout.SOUTH);
		this.setResizable(false);
	}

	private void inicializarBotones(){

		botones = new JButton[15][15];

		OyenteBotonCelda o = new OyenteBotonCelda();

		for(int f = 0; f < botones.length; f++)
			for(int c = 0; c < botones[0].length; c++) {
				botones[f][c] = new JButton(new ImageIcon("src/Imagenes/butaca.png"));
				botones[f][c].setActionCommand(f + "," + c);
				botones[f][c].addActionListener(o);
				botones[f][c].setEnabled(false);
				panelButacas.add(botones[f][c]);
			}
	}

	private void actualizarBotones(){
		ImageIcon disponible = new ImageIcon("src/Imagenes/butaca.png");
		ImageIcon ocupada = new ImageIcon("src/Imagenes/butacaOcupada.png");
		
		for(int f = 0; f < botones.length; f++) {
			for(int c = 0; c < botones[0].length; c++) {
				if(l.obtenerPeliElegida().obtenerButaca(f,c)) {
					botones[f][c].setEnabled(true);
					botones[f][c].setIcon(disponible);
				} else {
					botones[f][c].setEnabled(false);
					botones[f][c].setIcon(ocupada);
				}
			}      
		}
	}

	private void inicializarPeliculas(){

		OyenteBotonPelicula o = new OyenteBotonPelicula();
		peliculas = new JComboBox<String>();

		for(int i = 0; i < l.obtenerCine().cantPeliculas(); i++)
			peliculas.addItem(l.obtenerCine().obtenerPelicula(i).obtenerNombre());

		peliculas.addActionListener(o);
	}

	private void inicializarEntradas(){
		cantEntradas = new JComboBox<String>();
		
		cantEntradas.addItem("1");
		cantEntradas.addItem("2");
		cantEntradas.addItem("3");
		cantEntradas.addItem("4");
		cantEntradas.addItem("5");
		cantEntradas.addItem("6"); 
		
		cantEntradas.setEnabled(false); 
		cantEntradas.addActionListener(new OyenteBotonEntradas());
	}

	private class OyenteBotonEntradas implements ActionListener{

		public void actionPerformed(ActionEvent e){

			String item = cantEntradas.getSelectedItem().toString();
			int cantidad = Integer.parseInt(item);
			l.modificarCantEntradas(cantidad);

			actualizarBotones();

			float totalAPagar = (float)(l.obtenerCine().obtenerValorEntrada() * l.obtenerCantEntradas());
			total.setText("Total a pagar $" + totalAPagar);

			cantEntradas.setEnabled(false);
		} 
	}

	private class OyenteBotonPelicula implements ActionListener{

		public void actionPerformed(ActionEvent e){

			String opcion = peliculas.getSelectedItem().toString();
			l.elegirPelicula(opcion); 
			cantEntradas.setEnabled(true); 
			float costoTotal = l.obtenerCantEntradas() * l.obtenerCine().obtenerValorEntrada();
			total.setText("Total a pagar: $" + costoTotal);
		} 
	}

	private class OyenteBotonCelda implements ActionListener{

		public void actionPerformed(ActionEvent e){

			String indices=e.getActionCommand();
			String[] iyj= indices.split(",");
			int i=Integer.parseInt(iyj[0]); 
			int j=Integer.parseInt(iyj[1]); 
			Pelicula peli = l.obtenerPeliElegida();

			if(!peli.obtenerButaca(i,j) ){
				peli.desocuparButaca(i,j); 
				l.disminuirEntradasElegidas(); 
				botones[i][j].setIcon(new ImageIcon("src/Imagenes/butaca.png")); 
				
			} else {
				if(peli.obtenerButaca(i,j) && l.obtenerCantEntradasElegidas()<l.obtenerCantEntradas()){
					peli.ocuparButaca(i,j); 
					l.aumentarEntradasElegidas(); 
					botones[i][j].setIcon(new ImageIcon("src/Imagenes/butacaSeleccionada.png")); 
				}  
			}

			if(l.todasEntradasElegidas()) 
				botonReservar.setEnabled(true);
			else 
				botonReservar.setEnabled(false); 
		} 
	}

	private class OyenteBotonReservar implements ActionListener{

		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent e){

			// Mostrar mensaje de agradecimiento
			Object a = "Gracias por tu compra!";
			ImageIcon iconoPochoclos = new ImageIcon("src/Imagenes/pochoclos.png");
			Image imagenRedimensionada = iconoPochoclos.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Redimensionar a 100x100 píxeles
			ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
			JOptionPane pd = new JOptionPane();

			pd.showMessageDialog(null, a, "Cinema", 0, iconoRedimensionado);

			peliculas.setSelectedIndex(0); 
			cantEntradas.setSelectedIndex(0);
			cantEntradas.setEnabled(false);
			total.setText("$0.00"); 

			for (int f = 0; f < botones.length; f++) {
				for (int c = 0; c < botones[f].length; c++) {
					botones[f][c].setEnabled(false); 
					botones[f][c].setIcon(new ImageIcon("src/Imagenes/butaca.png"));  
				}
			}

			botonReservar.setEnabled(false);
		}
	}
}
