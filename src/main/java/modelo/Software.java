package modelo;

public class Software {

	private String nombreMateria;
	private String nombreSala;
	private int numeroCursos;
	private int demandaCurso;
	private int ofertaCurso;
	private String nombre;
	private String tipoDepartamento;
	private String procesador;
	private double velocidadProcesador;
	private String arquitectura;
	private String sistemaOperativo;
	private int memoriaRAM;
	private int discoDuro;
	private String version;
	private int cantLicencias;
	private boolean ejecutable;
	private int tamanoEjecutable;
	
	public Software(String nomMateria, String nomSala, int numCurso, int demanCurso, int oferCurso,
			String nombreSoft, String tipoSoft, String proces, double velProces, String arqui, String sisOpe, 
			int ram, int disc, String vers, int cantLicen, boolean eje, int tamEjecu) {

		nombreMateria=nomMateria;
		nombreSala=nomSala;
		numeroCursos=numCurso;
		demandaCurso=demanCurso;
		ofertaCurso=oferCurso;
		nombre=nombreSoft;
		tipoDepartamento=tipoSoft;
		procesador=proces;
		velocidadProcesador=velProces;
		arquitectura=arqui;
		sistemaOperativo=sisOpe;
		memoriaRAM=ram;
		discoDuro=disc;
		version=vers;
		cantLicencias=cantLicen;
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

	public String getTipoDepartamento() {
		return tipoDepartamento;
	}

	public void setTipoDepartamento(String tipo) {
		this.tipoDepartamento = tipo;
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

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public double getVelocidadProcesador() {
		return velocidadProcesador;
	}

	public void setVelocidadProcesador(double velocidadProcesador) {
		this.velocidadProcesador = velocidadProcesador;
	}

	public int getCantLicencias() {
		return cantLicencias;
	}

	public void setCantLicencias(int cantLicencias) {
		this.cantLicencias = cantLicencias;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public int getNumeroCursos() {
		return numeroCursos;
	}

	public void setNumeroCursos(int numeroCursos) {
		this.numeroCursos = numeroCursos;
	}

	public int getDemandaCurso() {
		return demandaCurso;
	}

	public void setDemandaCurso(int demandaCurso) {
		this.demandaCurso = demandaCurso;
	}

	public int getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(int ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}
}
