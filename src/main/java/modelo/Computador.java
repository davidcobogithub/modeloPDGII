package modelo;

public class Computador {

	
	//Atributos de  la clase Computador
	private String procesador;
	private String arquitectura;
	private String sistemaOperativo;
	private int memoriaRAM;
	private int discoDuro;

	//Constructor de  la clase Computador
	public Computador(String proces, String arqui, String sisOpe, int ram, int disc) {

		procesador=proces;
		arquitectura=arqui;
		sistemaOperativo=sisOpe;
		memoriaRAM=ram;
		discoDuro=disc;
	

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


}
