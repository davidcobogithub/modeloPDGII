package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LectorDeArchivos {

	public static final String SEPARATOR=";";
	public static final int AJUSTE_DISTRIBUCION=3;
	
	private ArrayList<Sala> salas;
	
	private static ArrayList<Software> toolSoftware;
	private static ArrayList<Software> toolSoftwareBasico;

	private static String reporte;

	public LectorDeArchivos() {

		leerCSVSalas();
		leerSoftwareBasico();

	}

	public void leerCSVSoftware(String path) {

		toolSoftware= new ArrayList<Software>();
		reporte="Archivo Cargado: " + path+"\n"+"\n";

		BufferedReader br = null;

		try {

			br =new BufferedReader(new FileReader(path));
			String line = br.readLine();
			int numLine=1;
			while (line != null) {

				if (numLine > 2) {

					String [] fields = line.split(SEPARATOR);

					if (fields.length == 13) {

						String nombreMateria=fields[3];
						String nombreSala=fields[2];
						int numeroCursos=Integer.parseInt(fields[10]);
						int demandaCurso=Integer.parseInt(fields[11]);
						int ofertaCurso=Integer.parseInt(fields[12]);
						String nombre=fields[4];
						String tipo=fields[1];
						String procesador="";
						double velocidadProcesador=0.0;
						String arquitectura="";
						String sistemaOperativo="";
						int memoriaRAM=0;
						int discoDuro=0;
						String version="";
						int cantLicencias=0;
						boolean ejecutable=false;
						int tamanoEjecutable=0;

						toolSoftware.add(new Software(nombreMateria, nombreSala, numeroCursos, demandaCurso, 
								ofertaCurso, nombre, tipo, procesador, velocidadProcesador,arquitectura,sistemaOperativo,
								memoriaRAM, discoDuro, version, cantLicencias,ejecutable,tamanoEjecutable));
					}

					else if (fields.length == 16) {

						String nombreMateria=fields[3];
						String nombreSala=fields[2];
						int numeroCursos=Integer.parseInt(fields[10]);
						int demandaCurso=Integer.parseInt(fields[11]);
						int ofertaCurso=Integer.parseInt(fields[12]);
						String nombre=fields[4];
						String tipo=fields[1];
						String procesador=fields[13];
						double velocidadProcesador=0.0;
						String arquitectura=fields[15];
						String sistemaOperativo="";
						int memoriaRAM=0;
						int discoDuro=0;
						String version=fields[6];
						int cantLicencias=0;
						boolean ejecutable=false;
						int tamanoEjecutable=0;

						toolSoftware.add(new Software(nombreMateria, nombreSala, numeroCursos, demandaCurso, 
								ofertaCurso, nombre, tipo, procesador, velocidadProcesador,arquitectura,sistemaOperativo,
								memoriaRAM, discoDuro, version, cantLicencias,ejecutable,tamanoEjecutable));
					}

					else if (fields.length == 17) {

						String nombreMateria=fields[3];
						String nombreSala=fields[2];
						int numeroCursos=Integer.parseInt(fields[10]);
						int demandaCurso=Integer.parseInt(fields[11]);
						int ofertaCurso=Integer.parseInt(fields[12]);
						String nombre=fields[4];
						String tipo=fields[1];
						String procesador=fields[13];
						double velocidadProcesador=0.0;
						String arquitectura=fields[15];
						String sistemaOperativo=fields[16];
						int memoriaRAM=0;
						int discoDuro=0;
						String version=fields[6];
						int cantLicencias=0;
						boolean ejecutable=false;
						int tamanoEjecutable=0;

						toolSoftware.add(new Software(nombreMateria, nombreSala, numeroCursos, demandaCurso, 
								ofertaCurso, nombre, tipo, procesador, velocidadProcesador,arquitectura,sistemaOperativo,
								memoriaRAM, discoDuro, version, cantLicencias,ejecutable,tamanoEjecutable));
					}

					else {

						String nombreMateria=fields[3];
						String nombreSala=fields[2];
						int numeroCursos=Integer.parseInt(fields[10]);
						int demandaCurso=Integer.parseInt(fields[11]);
						int ofertaCurso=Integer.parseInt(fields[12]);
						String nombre=fields[4];
						String tipo=fields[1];
						String procesador=fields[13];
						double velocidadProcesador=0.0d;
						String arquitectura=fields[15];
						String sistemaOperativo=fields[16];
						int memoriaRAM=Integer.parseInt(fields[17].trim());
						int discoDuro=Integer.parseInt(fields[18].trim());
						String version=fields[6];
						int cantLicencias=0;
						boolean ejecutable=false;
						int tamanoEjecutable=0;

						if (!fields[9].equals("") &&  !fields[14].equals("")) {

							cantLicencias=Integer.parseInt(fields[9]);
							velocidadProcesador=Double.parseDouble(fields[14].trim().substring(0, fields[14].trim().length()-4));

							toolSoftware.add(new Software(nombreMateria, nombreSala, numeroCursos, demandaCurso, 
									ofertaCurso, nombre, tipo, procesador, velocidadProcesador,arquitectura,sistemaOperativo,
									memoriaRAM, discoDuro, version, cantLicencias,ejecutable,tamanoEjecutable));

						}
						else {

							toolSoftware.add(new Software(nombreMateria, nombreSala, numeroCursos, demandaCurso, 
									ofertaCurso, nombre, tipo, procesador, velocidadProcesador,arquitectura,sistemaOperativo,
									memoriaRAM, discoDuro, version, cantLicencias,ejecutable,tamanoEjecutable));

						}
					}

				}

				numLine++;
				line = br.readLine();
			}
			
			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void leerCSVSalas() {

		salas= new ArrayList<Sala>();

		BufferedReader br = null;

		try {

			br =new BufferedReader(new FileReader("specs/dataset-pc.csv"));
			String line = br.readLine();
			int numLine=1;
			while (line != null) {

				if (numLine > 1) {

					String [] fields = line.split(SEPARATOR);

					String nombreSala=fields[0];
					String tipo=fields[1];
					int capacidad=Integer.parseInt(fields[7]);

					String procesador=fields[4];
					String arquitectura=fields[5];
					String sistemaOperativo=fields[6];
					int memoriaRAM=Integer.parseInt(fields[3].trim().split(" ")[0])*1024;
					int discoDuro=0;


					if (fields[2].trim().contains("TB")) {

						discoDuro=1048576*AJUSTE_DISTRIBUCION;

						Computador computadorSala= new Computador(procesador,arquitectura, sistemaOperativo, memoriaRAM, discoDuro);

						salas.add(new Sala(nombreSala, tipo, capacidad , computadorSala));

					}else {

						discoDuro=Integer.parseInt(fields[2].trim().split(" ")[0])*(1024*AJUSTE_DISTRIBUCION);

						Computador computadorSala= new Computador(procesador,arquitectura, sistemaOperativo, memoriaRAM, discoDuro);

						salas.add(new Sala(nombreSala, tipo, capacidad , computadorSala));
					}

				}

				numLine++;
				line = br.readLine();
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}


	}

	public void leerSoftwareBasico() {

		toolSoftwareBasico= new ArrayList<Software>();

		BufferedReader br = null;

		try {

			br =new BufferedReader(new FileReader("specs/software-oficinas.csv"));
			String line = br.readLine();
			int numLine=1;
			while (line != null) {

				if (numLine > 1) {

					String [] fields = line.split(SEPARATOR);

					String nombre=fields[0];
					String procesador=fields[2];
					String arquitectura=fields[4];
					String sistemaOperativo=fields[5];

					double velocidadProcesador=Double.parseDouble(fields[3].trim());
					int memoriaRAM=Integer.parseInt(fields[6].trim());
					int discoDuro=Integer.parseInt(fields[7].trim());

					String version=fields[1];
					int cantLicencias=0;
					boolean ejecutable=false;
					int tamanoEjecutable=0;

					toolSoftwareBasico.add(new Software("", "", 0, 0, 
							0, nombre, "", procesador, velocidadProcesador,arquitectura,sistemaOperativo,
							memoriaRAM, discoDuro, version, cantLicencias,ejecutable,tamanoEjecutable));

				}

				numLine++;
				line = br.readLine();
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public ArrayList<Sala> getSalas() {
		return salas;
	}

	public void setSalas(ArrayList<Sala> salas) {
		this.salas = salas;
	}

	public ArrayList<Software> getToolSoftware() {
		return toolSoftware;
	}

	public void setToolSoftware(ArrayList<Software> toolSoftware) {
		LectorDeArchivos.toolSoftware = toolSoftware;
	}

	public ArrayList<Software> getToolSoftwareBasico() {
		return toolSoftwareBasico;
	}

	public void setToolSoftwareBasico(ArrayList<Software> toolSoftwareBasico) {
		LectorDeArchivos.toolSoftwareBasico = toolSoftwareBasico;
	}

	public String getReporte() {
	
		return reporte;
	}

	public void setReporte(String reporte) {
		LectorDeArchivos.reporte = reporte;
	}

}
