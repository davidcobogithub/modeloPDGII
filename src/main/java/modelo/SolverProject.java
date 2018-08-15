package modelo;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class SolverProject {


	//Constantes del Proyecto 

	//Tipo de salas
	public final static String TIPO_TICS="TICS";
	public final static String TIPO_LABORATORIO_REDES="Sala Redes";
	public final static String TIPO_INDUSTRIAL="Industrial";
	public final static String TIPO_SALA_GENERAL="General";
	public final static String TIPO_SALA_GENERAL_DISEÑO="General Diseño";
	public final static String TIPO_DISEÑO="Diseño";
	public final static String TIPO_DISEÑO_INDUSTRIAL="Diseño Industrial";
	public final static String TIPO_IDIOMAS="Idiomas";
	public final static String TIPO_FINANZAS="Finanzas";
	public final static String TIPO_BIBLIO_SALA_PROFESORES="Biblioteca Sala Profesores";
	public final static String TIPO_BIBLIO_BASES_DATOS="Biblioteca Bases de Datos";
	public final static String TIPO_BIBLIO_CONSULTAS="Biblioteca Consultas";
	public final static String TIPO_MAC="iMac";
	public final static String TIPO_GRANJA_RENDER="Granja Render";
	public final static String TIPO_SALA_DOCTORADO="Sala de Doctorado";



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
	public final static String PROCESADOR_INTEL_CORE_I3="Intel® Core™ i3";
	public final static String PROCESADOR_INTEL_CORE_I5="Intel® Core™ i5";
	public final static String PROCESADOR_INTEL_CORE_I7="Intel® Core™ i7";
	public final static String PROCESADOR_INTEL_CORE_2DUO="Intel® Core™ 2 DUO";
	public final static String PROCESADOR_INTEL_XEON="Intel® XEON";

	//Sistema Operativo
	public final static String SIS_OP_WINDOWS10="Windows 10";


	public final static int PORCENTAJE_DISPONIBLE=70;


	private static ArrayList<Sala> salas;
	private static ArrayList<Software> toolSoftware;

	public static void main(String[] args) {

		modeloInicial();

	}

	public static void constructorModelo() {

		salas= new ArrayList<Sala>();
		toolSoftware= new ArrayList<Software>();

		//COMPUTADORES DE TB
		Computador computador1TBHD_2GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_1TB);
		Computador computador1TBHD_2GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_1TB);


		Computador computador1TBHD_4GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_1TB);
		Computador computador1TBHD_4GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_1TB);
		Computador computador1TBHD_4GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO,ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_1TB);


		Computador computador1TBHD_8GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_1TB);
		Computador computador1TBHD_8GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_1TB);
		Computador computador1TBHD_8GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_1TB);


		Computador computador1TBHD_10GBRAM= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_10GB, DISCO_DURO_1TB);


		Computador computador1TBHD_16GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5,ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_1TB);
		Computador computador1TBHD_16GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO,ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_1TB);
		Computador computador1TBHD_16GBRAM_PROC_XEON= new Computador(PROCESADOR_INTEL_XEON,ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_1TB);


		//COMPUTADORES DE GB
		Computador computador150GBHD_3GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_3GB, DISCO_DURO_150GB);
		Computador computador150GBHD_4GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB,DISCO_DURO_150GB);


		Computador computador250GBHD_2GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_250GB);		
		Computador computador250GBHD_4GBRAM= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_250GB);


		Computador computador500GBHD_2GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_2GB, DISCO_DURO_500GB);
		Computador computador500GBHD_4GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_500GB);
		Computador computador500GBHD_4GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_500GB);
		Computador computador500GBHD_4GBRAM_PROC_CORE2DUO= new Computador(PROCESADOR_INTEL_CORE_2DUO, ARQUITECTURA_32_BITS, SIS_OP_WINDOWS10, RAM_4GB, DISCO_DURO_500GB);


		Computador computador500GBHD_8GBRAM_PROC_COREI3= new Computador(PROCESADOR_INTEL_CORE_I3, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_500GB);
		Computador computador500GBHD_8GBRAM_PROC_COREI5= new Computador(PROCESADOR_INTEL_CORE_I5, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_500GB);


		Computador computador500GBHD_16GBRAM= new Computador(PROCESADOR_INTEL_XEON, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_16GB, DISCO_DURO_500GB);

		Computador computador750GBHD_8GBRAM= new Computador(PROCESADOR_INTEL_CORE_I7, ARQUITECTURA_64_BITS, SIS_OP_WINDOWS10, RAM_8GB, DISCO_DURO_750GB);

		//Salas de Computo
		//		salas.add(new Sala("Biblioteca", TIPO_BIBLIO_SALA_PROFESORES, 8 , computador250GBHD_4GBRAM));
		//		salas.add(new Sala("Biblioteca", TIPO_BIBLIO_BASES_DATOS, 7 , computador250GBHD_2GBRAM));
		//		salas.add(new Sala("Biblioteca", TIPO_BIBLIO_CONSULTAS, 8 , computador150GBHD_3GBRAM));
		//		salas.add(new Sala("Biblioteca Equipos MAC", TIPO_MAC, 5 , computador500GBHD_4GBRAM_PROC_CORE2DUO));
		//		salas.add(new Sala("Granja de Render", TIPO_GRANJA_RENDER, 23 , computador1TBHD_16GBRAM_PROC_XEON));
		salas.add(new Sala("Sala General Computo", TIPO_SALA_GENERAL, 61 , computador1TBHD_2GBRAM_PROC_COREI3));


		salas.add(new Sala("201C", TIPO_IDIOMAS, 22 , computador500GBHD_2GBRAM_PROC_CORE2DUO));
		salas.add(new Sala("202C", TIPO_TICS, 25 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("203C", TIPO_TICS, 30 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("205C", TIPO_INDUSTRIAL, 31 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("207C", TIPO_TICS, 30 , computador1TBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("208C", TIPO_TICS, 30 , computador500GBHD_8GBRAM_PROC_COREI5));
		salas.add(new Sala("2011C", TIPO_IDIOMAS, 22 , computador250GBHD_4GBRAM));


		//		salas.add(new Sala("301C", TIPO_DISEÑO, 25 , computador1TBHD_16GBRAM_PROC_COREI5));
		//		salas.add(new Sala("302C", TIPO_FINANZAS, 36 , computador1TBHD_8GBRAM_PROC_COREI5));
		//		salas.add(new Sala("303C", TIPO_INDUSTRIAL, 11 , computador500GBHD_8GBRAM_PROC_COREI3));
		//		salas.add(new Sala("304C", TIPO_INDUSTRIAL, 36 , computador1TBHD_8GBRAM_PROC_COREI5));
		//		salas.add(new Sala("305C", TIPO_SALA_GENERAL_DISEÑO, 18 , computador1TBHD_8GBRAM_PROC_CORE2DUO));
		//		salas.add(new Sala("306C", TIPO_LABORATORIO_REDES, 12 , computador750GBHD_8GBRAM));
		//		salas.add(new Sala("307C", TIPO_LABORATORIO_REDES, 12 , computador750GBHD_8GBRAM));
		//		salas.add(new Sala("308C", TIPO_DISEÑO_INDUSTRIAL, 25 , computador1TBHD_16GBRAM_PROC_XEON));
		//		salas.add(new Sala("309C", TIPO_DISEÑO, 25 , computador500GBHD_16GBRAM));
		salas.add(new Sala("310C", TIPO_DISEÑO, 25 , computador1TBHD_16GBRAM_PROC_COREI5));
		salas.add(new Sala("311C", TIPO_SALA_DOCTORADO, 5 , computador500GBHD_4GBRAM_PROC_CORE2DUO));

		cargarInfoSoftware();

	}

	public static void cargarInfoSoftware() {

		toolSoftware.add(new Software("Visual Paradigm", TIPO_TICS, "Intel Pentium 4", 1,ARQUITECTURA_32_BITS, "windows 10", 2, 4, "14.0",false,0));
		toolSoftware.add(new Software("3ds Max Studio", TIPO_INDUSTRIAL, "Intel o AMD multi-core",1, ARQUITECTURA_64_BITS, "windows 10", 4, 6, "2017",false,0));
		toolSoftware.add(new Software("Adobe Experience Design", TIPO_DISEÑO, "Intel o AMD multi-core",2, ARQUITECTURA_64_BITS, "windows 10", 4, 2, "0",false,0));
		toolSoftware.add(new Software("Office", TIPO_SALA_GENERAL, "Intel",1, ARQUITECTURA_32_BITS, "windows 10", 4, 3, "2013",false,0));
		toolSoftware.add(new Software("Matlab", TIPO_TICS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",true,1));
		toolSoftware.add(new Software("Rosseta", TIPO_IDIOMAS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",true,1));
	}

	public static void modeloInicial() {

		constructorModelo();

		Model model= new Model();

		IntVar[][] carrera = model.intVarMatrix("carrera", salas.size(), toolSoftware.size(), 0, 2);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (toolSoftware.get(j).getNombre().equals("Office") && !salas.get(i).getNombre().equals("310C") && !toolSoftware.get(j).isEjecutable()) {

					model.arithm(carrera[i][j], "=",1).post();
				}
				else  if (!toolSoftware.get(j).getTipo().equals(salas.get(i).getTipo())) {

					model.arithm(carrera[i][j], "=",0).post();
				}

				else if (toolSoftware.get(j).getTipo().equals(salas.get(i).getTipo()) && !toolSoftware.get(j).isEjecutable()) {

					model.arithm(carrera[i][j], "=",1).post();
				}

			}
		}

		Solution solution = new Solution(model);
		//Para que la última solución quede guardada
		while(model.getSolver().solve()){

			solution.record();

			List<Solution> list=model.getSolver().findAllSolutions();

			System.out.println("Primera parte");
			System.out.println("Soluciones encontradas: "+list.size());

			int nSol=1;
			for(Solution s:list){

				System.out.println("Solución: " + nSol);
				imprimirMatrizConSolucion(carrera, s);
				nSol++;
			}

		}

		System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"2 = Tiene ejecutable"+"\n");

		restriccionesDeDisco(carrera);

	}

	public static void restriccionesDeDisco(IntVar[][] matrizCarreras) {

		System.out.println("Segunda parte"+"\n");

		Model model= new Model();

		IntVar[][] matrizPesos = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 2);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (matrizCarreras[i][j].getValue()==1) {

					matrizPesos[i][j]=matrizCarreras[i][j].mul(toolSoftware.get(j).getDiscoDuro()).intVar();

				}

			}
		}

		imprimirMatriz(matrizPesos);

		IntVar[][] matrizResultado = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 2);
		int pesoPorSala=0;

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				pesoPorSala+=matrizPesos[i][j].getValue();


			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (pesoPorSala>=8) {

					model.arithm(matrizResultado[i][j], "=", 0).post();
					System.out.println("Ojo, en la sala " +salas.get(i).getNombre()+" la cantidad de espacio de disco de software supera la capacidad de disco del computador ");
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

		imprimirMatrizConSolucion(matrizResultado, solution);

	}


	public static void imprimirMatriz(IntVar[][] matriz) {

		System.out.println("Matriz....");

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

			for (int j = 0; j < toolSoftware.size(); j++) {

				System.out.print(solut.getIntVal(matriz[i][j]));

			}
			System.out.print(" "+salas.get(i).getNombre()+ " Tipo "+ salas.get(i).getTipo());
			System.out.println("");

		}
		for (int i = 0; i < toolSoftware.size(); i++) {

			System.out.print(toolSoftware.get(i).getNombre().charAt(0));
		}

		System.out.println("\n");

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
