package Logica;

public class Logica {
	private Cine 	 cine;
	private Pelicula peliElegida;
	private int 	 cantEntradas;
	private int 	 cantEntradasElegidas;


	public Logica(){

		String [] titulosPelis = {
				"Interstellar",
				"2001: A Space Odyssey",
				"The Matrix",
				"Inception",
				"Gravity",
				"The Martian",
				"Ex Machina",
				"Blade Runner",
				"Star Trek",
				"Star Wars: A New Hope",
				"Star Wars: The Empire Strikes Back"
		};

		cine = new Cine(titulosPelis.length);

		for(int i = 0; i<titulosPelis.length; i++){
			cine.agregarPelicula(titulosPelis[i]);
		}
		
		peliElegida = cine.obtenerPelicula("Interstellar");
		cantEntradas = 1;
		cantEntradasElegidas = 0;
	}

	public Cine obtenerCine(){
		return cine;
	}

	public Pelicula obtenerPeliElegida(){
		return peliElegida;
	}

	public int obtenerCantEntradas(){
		return cantEntradas;
	}

	public int obtenerCantEntradasElegidas(){
		return cantEntradasElegidas;
	}

	public boolean todasEntradasElegidas(){
		return cantEntradas == cantEntradasElegidas;
	}

	public void elegirPelicula(String nom){
		peliElegida = cine.obtenerPelicula(nom);
	}

	public void modificarCantEntradas(int cant){
		cantEntradas = cant;
		cantEntradasElegidas = 0;
	}

	public void aumentarEntradasElegidas(){
		cantEntradasElegidas++;
	}

	public void disminuirEntradasElegidas(){
		cantEntradasElegidas--;
	}

}

