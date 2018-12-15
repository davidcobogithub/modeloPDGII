package modelo;

public class Sala {

	//Atributos de  la clase Sala
	private String nombre;
	private String tipo;
	private int capacidad;
	
	//Relacion entre Sala y computador:
	//Una sala tiene computadores con las mismas caracteristicas de hardware
	private Computador computadores;

	//Constructor de  la clase Sala
	public Sala(String nom, String tip, int capa, Computador comp) {

		nombre=nom;
		tipo=tip;
		capacidad= capa;
		computadores= comp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Computador getComputadores() {
		return computadores;
	}

	public void setComputadores(Computador computadores) {
		this.computadores = computadores;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


}
