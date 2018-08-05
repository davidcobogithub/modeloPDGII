package modelo;

public class Software {

	private String nombre;
	private String tipo;
	private String procesador;
	private String arquitectura;
	private String sistemaOperativo;
	private int memoriaRAM;
	private int discoDuro;
	private String version;
	private boolean ejecutable;
	private int tamanoEjecutable;
	
	public Software(String nom, String tip, String proces, int veloc, String arqui, String sisOpe, int ram, int disc, String vers, boolean eje, int tamEjecu) {

		nombre=nom;
		tipo=tip;
		procesador=proces;
		arquitectura=arqui;
		sistemaOperativo=sisOpe;
		memoriaRAM=ram;
		discoDuro=disc;
		version=vers;
		ejecutable=eje;
		tamanoEjecutable=tamEjecu;
		

	}

	public String getProcesador() {
		return procesador;
	}

	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}

	public String getArquitectura() {
		return arquitectura;
	}

	public void setArquitectura(String arquitectura) {
		this.arquitectura = arquitectura;
	}

	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public void setSistemaOperativo(String sistemaOperativo) {
		this.sistemaOperativo = sistemaOperativo;
	}

	public int getMemoriaRAM() {
		return memoriaRAM;
	}

	public void setMemoriaRAM(int memoriaRAM) {
		this.memoriaRAM = memoriaRAM;
	}

	public int getDiscoDuro() {
		return discoDuro;
	}

	public void setDiscoDuro(int discoDuro) {
		this.discoDuro = discoDuro;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isEjecutable() {
		return ejecutable;
	}

	public void setEjecutable(boolean ejecutable) {
		this.ejecutable = ejecutable;
	}

	public int getTamanoEjecutable() {
		return tamanoEjecutable;
	}

	public void setTamanoEjecutable(int tamanoEjecutable) {
		this.tamanoEjecutable = tamanoEjecutable;
	}
}
