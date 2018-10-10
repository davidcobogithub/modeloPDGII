package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.search.limits.SolutionCounter;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.criteria.Criterion;

public class SolverProject {


	//Constantes del Proyecto 

	//Tipo de salas
	public final static String TIPO_TICS="TIC";
	//public final static String TIPO_LABORATORIO_REDES="Sala Redes";
	public final static String TIPO_INDUSTRIAL="IND";
	//public final static String TIPO_SALA_GENERAL="General";
	//public final static String TIPO_SALA_GENERAL_DISE�O="General Dise�o";
	public final static String TIPO_DISEÑO="DIS";
	//public final static String TIPO_DISE�O_INDUSTRIAL="Dise�o Industrial";
	public final static String TIPO_IDIOMAS="IDI";
	public final static String TIPO_FINANZAS="FIN";
	//	public final static String TIPO_BIBLIO_SALA_PROFESORES="Biblioteca Sala Profesores";
	//	public final static String TIPO_BIBLIO_BASES_DATOS="Biblioteca Bases de Datos";
	//	public final static String TIPO_BIBLIO_CONSULTAS="Biblioteca Consultas";
	//	public final static String TIPO_MAC="iMac";
	//	public final static String TIPO_GRANJA_RENDER="Granja Render";
	//public final static String TIPO_SALA_DOCTORADO="Sala de Doctorado";



	//Arquitectura de los computadores
	public final static String ARQUITECTURA_64_BITS="64 bits";
	public final static String ARQUITECTURA_32_BITS="32 bits";

	//Caracteristicas de los computadores
	//RAM
	public final static int RAM_2GB=2;
	public final static int RAM_3GB=3;
	public final static int RAM_4GB=4;
	public final static int RAM_8GB=8;
	public final static int RAM_10GB=10;
	public final static int RAM_16GB=16;

	//Disco duro
	public final static int DISCO_DURO_150GB=150;
	public final static int DISCO_DURO_250GB=250;
	public final static int DISCO_DURO_500GB=500;
	public final static int DISCO_DURO_750GB=750;
	public final static int DISCO_DURO_1TB=1000;


	//Procesadores
	public final static String PROCESADOR_INTEL_CORE_I3="Intel� Core� i3";
	public final static String PROCESADOR_INTEL_CORE_I5="Intel� Core� i5";
	public final static String PROCESADOR_INTEL_CORE_I7="Intel� Core� i7";
	public final static String PROCESADOR_INTEL_CORE_2DUO="Intel� Core� 2 DUO";
	public final static String PROCESADOR_INTEL_XEON="Intel� XEON";

	//Sistema Operativo
	public final static String SIS_OP_WINDOWS10="Windows 10";


	public final static int PORCENTAJE_DISPONIBLE=70;


	private static ArrayList<Sala> salas;
	private static ArrayList<Software> toolSoftware;

	public static final String SEPARATOR=";";

	public static void main(String[] args) {


		//		leerCSVSalas();
		//		leerCSVSoftware();
		modeloInicial();

		//		for (int j = 0; j < toolSoftware.size(); j++) {
		//
		//			Software soft=toolSoftware.get(j);
		//
		//			System.out.println(soft.getArquitectura()+" | "+soft.getCantLicencias()+" | "+soft.getDiscoDuro()+" | "+soft.getNombreSala());
		//
		//		}

		//		for (int i = 0; i < salas.size(); i++) {
		//
		//			Sala sala=salas.get(i);
		//			System.out.println(sala.getNombre()+" | "+sala.getTipo()+" | "+sala.getCapacidad()+" | "+sala.getComputadores().getDiscoDuro());
		//			//System.out.println(sala.getComputadores().getDiscoDuro() +" | "+sala.getComputadores().getMemoriaRAM()+" | "+sala.getComputadores().getSistemaOperativo());
		//
		//
		//		}
	}

	public static void leerCSVSoftware() {

		toolSoftware= new ArrayList<Software>();

		BufferedReader br = null;

		try {

			br =new BufferedReader(new FileReader("docs/dataset-software-copia.csv"));
			//br =new BufferedReader(new FileReader("docs/dataset-software.csv"));
			String line = br.readLine();
			int numLine=1;
			while (line != null) {

				if (numLine > 2 && numLine < 692) {

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
						int memoriaRAM=Integer.parseInt(fields[17].trim().charAt(0)+"");
						int discoDuro=Integer.parseInt(fields[18].trim().charAt(0)+"");
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

	public static void leerCSVSalas() {

		salas= new ArrayList<Sala>();

		BufferedReader br = null;

		try {

			br =new BufferedReader(new FileReader("docs/dataset-pc.csv"));
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
					int memoriaRAM=Integer.parseInt(fields[3].trim().charAt(0)+"");
					int discoDuro=0;

					if (fields[4].trim().contains("TB")) {

						discoDuro=Integer.parseInt(fields[2].trim().charAt(0)+"")*1000;

						Computador computadorSala= new Computador(procesador,arquitectura, sistemaOperativo, memoriaRAM, discoDuro);

						salas.add(new Sala(nombreSala, tipo, capacidad , computadorSala));

					}else {

						discoDuro=Integer.parseInt(fields[2].trim().charAt(0)+"");

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


	public static void constructorModelo() {

		//COMPUTADORES No utilizados
		//COMPUTADORES DE TB
		Computador computador1TBHD_2GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_1TB);


		Computador computador1TBHD_4GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_1TB);
		Computador computador1TBHD_4GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_1TB);
		Computador computador1TBHD_4GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_1TB);


		Computador computador1TBHD_8GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_1TB);
		Computador computador1TBHD_8GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_1TB);
		Computador computador1TBHD_8GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_1TB);


		Computador computador1TBHD_10GBRAM= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_10GB, DISCO_DURO_1TB);

		//COMPUTADORES DE GB
		Computador computador150GBHD_3GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_3GB, DISCO_DURO_150GB);
		Computador computador150GBHD_4GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB,DISCO_DURO_150GB);

		Computador computador250GBHD_2GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_250GB);		

		Computador computador500GBHD_4GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_500GB);
		Computador computador500GBHD_4GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_500GB);
		Computador computador500GBHD_4GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_500GB);

		Computador computador500GBHD_16GBRAM= new Computador(PROCESADOR_INTEL_XEON, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_500GB);
		Computador computador750GBHD_8GBRAM= new Computador(PROCESADOR_INTEL_CORE_I7, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_750GB);


		//COMPUTADORES Utilizados
		//COMPUTADORES DE TB
		Computador computador1TBHD_2GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_1TB);

		Computador computador1TBHD_16GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5,ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_1TB);
		Computador computador1TBHD_16GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO,ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_1TB);
		Computador computador1TBHD_16GBRAM_PROC_XEON= new Computador(PROCESADOR_INTEL_XEON,ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_1TB);

		//COMPUTADORES DE GB
		Computador computador250GBHD_4GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_250GB);

		Computador computador500GBHD_2GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_500GB);

		Computador computador500GBHD_8GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_500GB);
		Computador computador500GBHD_8GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_500GB);


		//Salas de Computo

		//		salas.add(new Sala("Biblioteca", TIPO_BIBLIO_SALA_PROFESORES, 8 , computador250GBHD_4GBRAM));
		//		salas.add(new Sala("Biblioteca", TIPO_BIBLIO_BASES_DATOS, 7 , computador250GBHD_2GBRAM));
		//		salas.add(new Sala("Biblioteca", TIPO_BIBLIO_CONSULTAS, 8 , computador150GBHD_3GBRAM));
		//		salas.add(new Sala("Biblioteca Equipos MAC", TIPO_MAC, 5 , computador500GBHD_4GBRAM_PROC_CORE2DUO));
		//		salas.add(new Sala("Granja de Render", TIPO_GRANJA_RENDER, 23 , computador1TBHD_16GBRAM_PROC_XEON));
		//salas.add(new Sala("Sala General Computo", TIPO_SALA_GENERAL, 61 , computador1TBHD_2GBRAM_PROC_COREI3));

		salas= new ArrayList<Sala>();

		salas.add(new Sala("201C", TIPO_IDIOMAS, 22 , computador500GBHD_2GBRAM_PROC_CORE2DUO));
		salas.add(new Sala("202C", TIPO_TICS, 25 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("203C", TIPO_TICS, 30 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("205C", TIPO_INDUSTRIAL, 31 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("207C", TIPO_TICS, 30 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("208C", TIPO_TICS, 30 , computador500GBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("211C", TIPO_IDIOMAS, 22 , computador250GBHD_4GBRAM));

		salas.add(new Sala("302C", TIPO_DISEÑO, 36 , computador1TBHD_16GBRAM_PROC_COREI5));
		salas.add(new Sala("303C", TIPO_FINANZAS, 11 , computador1TBHD_16GBRAM_PROC_COREI5));
		salas.add(new Sala("304C", TIPO_INDUSTRIAL, 36 , computador500GBHD_8GBRAM_PROC_COREI3));
		salas.add(new Sala("305C", TIPO_INDUSTRIAL, 18 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("306C", TIPO_DISEÑO, 12 , computador1TBHD_16GBRAM_PROC_CORE2DUO));
		//		salas.add(new Sala("307C", TIPO_TICS, 12 , computador750GBHD_8GBRAM));
		//		salas.add(new Sala("308C", TIPO_TICS, 25 , computador1TBHD_16GBRAM_PROC_XEON));
		salas.add(new Sala("309C", TIPO_INDUSTRIAL, 25 , computador1TBHD_16GBRAM_PROC_XEON));
		salas.add(new Sala("310C", TIPO_DISEÑO, 25 , computador1TBHD_16GBRAM_PROC_XEON));
		salas.add(new Sala("311C", TIPO_DISEÑO, 5 , computador1TBHD_16GBRAM_PROC_COREI5));

		//		cargarInfoSoftware();

	}

	public static void cargarInfoSoftware() {

		toolSoftware= new ArrayList<Software>();

		toolSoftware.add(new Software("","",0,0,0,"Visual Paradigm", TIPO_TICS, "Intel Pentium 4", 1,ARQUITECTURA_32_BITS, "windows 10", 2, 4, "14.0",0,false,0));
		toolSoftware.add(new Software("","",0,0,0,"3ds Max Studio", TIPO_INDUSTRIAL, "Intel o AMD multi-core",1, ARQUITECTURA_64_BITS, "windows 10", 4, 6, "2017",0,false,0));
		toolSoftware.add(new Software("","",0,0,0,"Adobe Experience Design", TIPO_DISEÑO, "Intel o AMD multi-core",2, ARQUITECTURA_64_BITS, "windows 10", 4, 2, "0",0,false,0));
		toolSoftware.add(new Software("","",0,0,0,"Office", TIPO_TICS, "Intel",1, ARQUITECTURA_32_BITS, "windows 10", 4, 3, "2013",0,false,0));
		toolSoftware.add(new Software("","",0,0,0,"Matlab", TIPO_TICS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",0,false,0));
		//toolSoftware.add(new Software("","",0,0,0,"Rosseta", TIPO_IDIOMAS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",0,false,0));
	}

	public static void modeloInicial() {

		constructorModelo();
		cargarInfoSoftware();

		Model model= new Model();

		System.out.println(salas.size() + " | " + toolSoftware.size());

		IntVar[][] carrera = model.intVarMatrix("carrera", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (toolSoftware.get(j).getNombre().equals("Office") && !salas.get(i).getNombre().equals("310C")) {

					model.arithm(carrera[i][j], "=",1).post();
				}
				else  if (!toolSoftware.get(j).getTipo().equals(salas.get(i).getTipo())) {

					model.arithm(carrera[i][j], "=",0).post();
				}

				//				else if (toolSoftware.get(j).getTipo().equals(salas.get(i).getTipo()) && !toolSoftware.get(j).isEjecutable()) {
				//
				//					model.arithm(carrera[i][j], "=",1).post();
				//				}
			}
		}

		Solution solution = new Solution(model);
		Solution solutionRecord= new Solution(model);
		//Para que la �ltima soluci�n quede guardada

		//		while(model.getSolver().solve()){


		//Mi PC 3000
		//PC Liason 15000
		solution.record();
		Criterion solcpt = new SolutionCounter(model, 15000);
		//List<Solution> list=model.getSolver().findAllSolutions(solcpt);
		List<Solution> list=model.getSolver().findAllSolutions();

		System.out.println("Primera parte");
		System.out.println("Soluciones encontradas: "+list.size()+"\n");

		int nSol=1;
		for(Solution s:list){

			System.out.println("Soluci�n: " + nSol);
			imprimirMatrizConSolucion(carrera, s);
			solutionRecord=s;
			nSol++;
		}

		//}

		System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"2 = Tiene ejecutable"+"\n");

		restriccionesDeDisco(carrera,solutionRecord);

	}

	public static void restriccionesDeDisco(IntVar[][] matrizCarreras, Solution solucionAnterior) {

		System.out.println("Segunda parte"+"\n");

		Model model= new Model();

		IntVar[][] matrizPesos = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras);


		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (matrizCopia[i][j].getValue()==1) {

					matrizPesos[i][j]=matrizCopia[i][j].mul(toolSoftware.get(j).getDiscoDuro()).intVar();

				}

				else if (matrizCopia[i][j].getValue()==2) {

					matrizPesos[i][j]=matrizCopia[i][j].mul(toolSoftware.get(j).getTamanoEjecutable()).div(2).intVar();
				}

			}
		}

		System.out.println("Matriz de Pesos:");
		imprimirMatriz(matrizPesos);

		IntVar[][] matrizResultado = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		int pesoPorSala=0;

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				pesoPorSala+=matrizPesos[i][j].getValue();

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (pesoPorSala>=8) {

					model.arithm(matrizResultado[i][j], "=", 0).post();
					//System.out.println("Ojo, en la sala " +salas.get(i).getNombre()+" la cantidad de espacio de disco de software supera la capacidad de disco del computador ");
				} 

				else if (matrizPesos[i][j].getValue() != 0) {

					model.arithm(matrizResultado[i][j], "=", 1).post();

				}
			}
			pesoPorSala=0;
		}

		Solution solution = new Solution(model);
		model.getSolver().solve();
		solution.record();

		System.out.println("------------------------Matriz Resultado final------------------------");
		imprimirMatrizConSolucion(matrizResultado, solution);

	}


	public static void imprimirMatriz(IntVar[][] matriz) {

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				System.out.print(matriz[i][j].getValue());
			}
			
			System.out.print(" "+salas.get(i).getNombre()+ " Tipo "+ salas.get(i).getTipo());
			System.out.println("");

		}
		for (int i = 0; i < toolSoftware.size(); i++) {

			System.out.print(toolSoftware.get(i).getNombre().charAt(0));
		}

		System.out.println("\n");

	}
	public static void imprimirMatrizConSolucion(IntVar[][] matriz, Solution solut) {


		for (int i = 0; i < salas.size(); i++) {

			System.out.println(salas.get(i).getNombre());

			for (int j = 0; j < toolSoftware.size(); j++) {


				if (solut.getIntVal(matriz[i][j])==1) {
					System.out.println(toolSoftware.get(j).getNombre());
				}

			}
			System.out.println("");

		}

	}

	public static IntVar[][] copiarMatriz(Solution solv, IntVar[][] matriz) {

		Model model= new Model();

		IntVar[][] matrizResul = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 2);
		IntVar val=null;
		int value=0;

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				value=solv.getIntVal(matriz[i][j].intVar());
				val=model.intVar(value);
				matrizResul[i][j]=val;

			}

		}

		return matrizResul;

	}

	public ArrayList<Sala> getSalas() {
		return salas;
	}

	public void setSalas(ArrayList<Sala> salas) {
		SolverProject.salas = salas;
	}

	public ArrayList<Software> getToolSoftware() {
		return toolSoftware;
	}

	public void setToolSoftware(ArrayList<Software> toolSoftware) {
		SolverProject.toolSoftware = toolSoftware;
	}
}
