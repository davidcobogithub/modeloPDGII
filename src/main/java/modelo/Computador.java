package modelo;

public class Computador {

	private String procesador;
	private int velocidadProcesador;
	private String arquitectura;
	private String sistemaOperativo;
	private int memoriaRAM;
	private int discoDuro;
	private int nucleos;

	public Computador(String proces,int veloc, String arqui, String sisOpe, int ram, int disc, int nuc) {

		procesador=proces;
		velocidadProcesador=veloc;
		arquitectura=arqui;
		sistemaOperativo=sisOpe;
		memoriaRAM=ram;
		discoDuro=disc;
		nucleos=nuc;

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

	public int getNucleos() {
		return nucleos;
	}

	public void setNucleos(int nucleos) {
		this.nucleos = nucleos;
	}

	public int getVelocidadProcesador() {
		return velocidadProcesador;
	}

	public void setVelocidadProcesador(int velocidadProcesador) {
		this.velocidadProcesador = velocidadProcesador;
	}

}
