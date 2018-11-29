package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	public final static String TIPO_INDUSTRIAL="IND";
	public final static String TIPO_DISENO="DIS";
	public final static String TIPO_IDIOMAS="IDI";
	public final static String TIPO_FINANZAS="FIN";

	//Arquitectura de los computadores
	public final static String ARQUITECTURA_64_BITS="64 bits";
	public final static String ARQUITECTURA_32_BITS="32 bits";

	public final static double PORCENTAJE_MAX_DEMANDA_ALTO=1;
	public final static double PORCENTAJE_MIN_DEMANDA_ALTO=0.71;
	public final static double PORCENTAJE_MAX_DEMANDA_MEDIO=0.7;
	public final static double PORCENTAJE_MIN_DEMANDA_MEDIO=0.41;
	public final static double PORCENTAJE_MAX_DEMANDA_BAJO=0.4;
	public final static double PORCENTAJE_MIN_DEMANDA_BAJO=0.1;

	//Caracteristicas de los computadores

	//Disco duro
	public final static int DISCO_DURO_1TB=1000;

	//Sistema Operativo
	public final static String SIS_OP_WINDOWS10="Windows 10";


	public final static int PORCENTAJE_DISPONIBLE=70;


	private static ArrayList<Sala> salas;
	private static ArrayList<Software> toolSoftware;
	private static ArrayList<Software> toolSoftwareBasico;
	private static ArrayList<String> nombreSoftware;
	private static ArrayList<Software> toolSoftwareBasicoOrdenado;

	private String reporte;

	public static final String SEPARATOR=";";

	public SolverProject() {

		reporte="\n";

		leerCSVSalas();
		leerSoftwareBasico();

		nombreSoftware=new ArrayList<String>();
		toolSoftwareBasicoOrdenado= new ArrayList<Software>();



		//	    for (int j = 0; j < escalaProcesadores.size(); j++) {
		//
		//			System.out.println(escalaProcesadores.indexOf(escalaProcesadores.get(j)) + " | " +escalaProcesadores.get(j));
		//
		//		}

		//		for (int i = 0; i < salas.size(); i++) {
		//
		//			Sala sala=salas.get(i);
		//			System.out.println(sala.getNombre()+" | "+sala.getTipo()+" | "+sala.getCapacidad()+" | "+sala.getComputadores().getDiscoDuro()+" GB "+" | RAM "+sala.getComputadores().getMemoriaRAM());
		//			//System.out.println(sala.getComputadores().getDiscoDuro() +" | "+sala.getComputadores().getMemoriaRAM()+" | "+sala.getComputadores().getSistemaOperativo());
		//
		//
		//		}
	}

	public void leerCSVSoftware(String path) {

		toolSoftware= new ArrayList<Software>();
		reporte="";

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
					int memoriaRAM=Integer.parseInt(fields[3].trim().split(" ")[0]);
					int discoDuro=0;


					if (fields[2].trim().contains("TB")) {

						discoDuro=DISCO_DURO_1TB;

						Computador computadorSala= new Computador(procesador,arquitectura, sistemaOperativo, memoriaRAM, discoDuro);

						salas.add(new Sala(nombreSala, tipo, capacidad , computadorSala));

					}else {

						discoDuro=Integer.parseInt(fields[2].trim().split(" ")[0]);

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

	public static void cargarInfoSoftware() {

		toolSoftware= new ArrayList<Software>();

		toolSoftware.add(new Software("Ingenieria de software","202C",0,0,0,"Visual Paradigm", TIPO_TICS, "Intel Pentium 4", 1,ARQUITECTURA_32_BITS, "windows 10", 2, 4, "14.0",0,false,0));
		toolSoftware.add(new Software("Diseño 3D","302C",0,0,0,"3ds Max Studio", TIPO_INDUSTRIAL, "Intel o AMD multi-core",1, ARQUITECTURA_64_BITS, "mac 10", 4, 6, "2017",0,false,0));
		toolSoftware.add(new Software("Diseño 3D","302C",0,0,0,"Adobe Experience Design", TIPO_DISENO, "Intel o AMD multi-core",2, ARQUITECTURA_64_BITS, "windows 10", 4, 2, "0",0,false,0));
		toolSoftware.add(new Software("COE 1","203C",0,0,0,"Office", TIPO_TICS, "Intel",1, ARQUITECTURA_32_BITS, "windows 10", 4, 3, "2013",0,false,0));
		toolSoftware.add(new Software("Arquitectura de HardWare","208C",0,0,0,"Matlab", TIPO_TICS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",0,false,0));
		toolSoftware.add(new Software("Ingles","201C",0,0,0,"Rosseta", TIPO_IDIOMAS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",0,false,0));
		toolSoftware.add(new Software("Algoritmos 1","203C",0,0,0,"Eclipse", TIPO_TICS, "Intel Pentium 4", 1,ARQUITECTURA_32_BITS, "windows 10", 2, 4, "14.0",0,false,0));
		toolSoftware.add(new Software("Pensamiento Sistemico","305C",0,0,0,"Age of Empires", TIPO_INDUSTRIAL, "Intel o AMD multi-core",1, ARQUITECTURA_64_BITS, "mac 10", 4, 6, "2017",0,false,0));
		toolSoftware.add(new Software("Diseño de medio","310C",0,0,0,"Creative Cloud", TIPO_DISENO, "Intel o AMD multi-core",2, ARQUITECTURA_64_BITS, "windows 10", 4, 2, "0",0,false,0));
		toolSoftware.add(new Software("Bases de Datos","202C",0,0,0,"SQL Developer", TIPO_TICS, "Intel",1, ARQUITECTURA_32_BITS, "windows 10", 4, 3, "2013",0,false,0));
		toolSoftware.add(new Software("Sistemas operativos","202C",0,0,0,"Virtual Box", TIPO_TICS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",0,false,0));
		toolSoftware.add(new Software("Econometria","303C",0,0,0,"@Risk", TIPO_FINANZAS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, "windows 10", 2, 2, "R2017a",0,false,0));

	}


	public void modeloInicial(int numSoluciones) {

		//cargarInfoSoftware();

		//		
		//		for (int j = 0; j < salas.size(); j++) {
		//
		//			System.out.println(salas.get(j).getCapacidad()+" | "+salas.get(j).getNombre());
		//
		//		}


		Model model= new Model();

		//		System.out.println(salas.size() + " | " + toolSoftware.size()+"\n");
		//		reporte+=salas.size() + " | " + toolSoftware.size()+"\n"+"\n";

		IntVar[][] carrera = model.intVarMatrix("carrera", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (toolSoftware.get(j).getNombre().equals("Office") && !salas.get(i).getTipo().equals(TIPO_DISENO)) {

					model.arithm(carrera[i][j], "=",1).post();
				}

				else if(toolSoftware.get(j).getSistemaOperativo().toUpperCase().contains("MAC") &&
						salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")) {

					model.arithm(carrera[i][j], "=",1).post();
				}

				else  if (!salas.get(i).getTipo().contains(toolSoftware.get(j).getTipoDepartamento())) {

					model.arithm(carrera[i][j], "=",0).post();
				}

				else  if (toolSoftware.get(j).getMemoriaRAM() <= salas.get(i).getComputadores().getMemoriaRAM()) {

					model.arithm(carrera[i][j], "=",1).post();
				}

				else if(toolSoftware.get(j).getNombreSala().equals(salas.get(i).getNombre())) {

					model.arithm(carrera[i][j], "=",1).post();
				}

			}
		}

		Solution solution = new Solution(model);
		Solution solutionRecord= new Solution(model);

		//Mi PC 3000
		//PC Liason 15000
		solution.record();
		Criterion solcpt = new SolutionCounter(model, numSoluciones);
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		System.out.println("Primera parte");
		System.out.println("Soluciones encontradas: "+list.size()+"\n");

		reporte+="Primera parte"+"\n";
		reporte+="Soluciones encontradas: "+list.size()+"\n"+"\n";

		int nSol=1;
		for(Solution s:list){

			System.out.println("Solución: " + nSol);
			reporte+="Solución: " + nSol+"\n";
			imprimirMatrizConSolucion(carrera, s);
			solutionRecord=s;
			nSol++;
		}

		System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n");
		reporte+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";
		restriccionesDeDisco(carrera,solutionRecord);

	}

	public void restriccionesDeDisco(IntVar[][] matrizCarreras, Solution solucionAnterior) {

		System.out.println("Segunda parte"+"\n");
		reporte+="Segunda parte"+"\n"+"\n";
		Model model= new Model();

		IntVar[][] matrizPesos = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras);


		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (matrizCopia[i][j].getValue()==1) {

					matrizPesos[i][j]=matrizCopia[i][j].mul(toolSoftware.get(j).getDiscoDuro()).intVar();

				}

			}
		}

		//		System.out.println("Matriz de Pesos:");
		//		reporte+="Matriz de Pesos:"+"\n";
		//imprimirMatriz(matrizPesos);

		IntVar[][] matrizResultado = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		int pesoPorSala=0;

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				pesoPorSala+=matrizPesos[i][j].getValue();

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				//System.out.println(pesoPorSala + " | "+(salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100);

				if (pesoPorSala>=(salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100) {

					model.arithm(matrizResultado[i][j], "=", 0).post();

					System.out.println(pesoPorSala + " | "+ (salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100
							+" Ojo, en la sala " +salas.get(i).getNombre()
							+" la cantidad de espacio de disco de software supera la capacidad de disco del computador ");

					reporte+=pesoPorSala + " | "+ (salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100
							+" Ojo, en la sala " +salas.get(i).getNombre()
							+" la cantidad de espacio de disco de software supera la capacidad de disco del computador "+"\n";
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

		System.out.println("Resultado parcial");
		reporte+="Resultado parcial"+"\n";
		imprimirMatrizConSolucion(matrizResultado, solution);
		//imprimirMatriz(matrizResultado);

		restriccionDemandaCapacidadSalas(matrizResultado, solution);

	}

	public void restriccionDemandaCapacidadSalas(IntVar[][] matrizCarreras, Solution solucionAnterior) {

		System.out.println("Tercera parte"+"\n");

		Model model= new Model();

		IntVar[][] matrizPesosDemandaCapacidad = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras);
	
		Collections.sort(toolSoftware, new Comparator<Software>() {
			public int compare(Software obj1, Software obj2) {

				if(obj1.getDemandaCurso() == obj2.getDemandaCurso()){
					return 0;
				}
				else if(obj1.getDemandaCurso() <= obj2.getDemandaCurso()){
					return 1;
				}
				else{
					return -1;
				}
			}
		});
		
		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (matrizCopia[i][j].getValue()==1) {

					matrizPesosDemandaCapacidad[i][j]=matrizCopia[i][j].mul(toolSoftware.get(j).getDemandaCurso()).intVar();

				}

			}
		}

		//		System.out.println("Matriz de Pesos:");
		//		reporte+="Matriz de Pesos:"+"\n";
		//imprimirMatriz(matrizPesos);

		IntVar[][] matrizResultadoDemanda = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		
		int pesoPorSala=0;
		int limiteDelArreglo=toolSoftware.size()/3;
		
		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				pesoPorSala+=matrizResultadoDemanda[i][j].getValue();

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (pesoPorSala>=0) {

					model.arithm(matrizResultadoDemanda[i][j], "=", 0).post();

				} 

				else if (pesoPorSala < 0) {

					model.arithm(matrizResultadoDemanda[i][j], "=", 1).post();

				}
				
				else if (pesoPorSala == 0) {

					model.arithm(matrizResultadoDemanda[i][j], "=", 1).post();

				}
			}
			pesoPorSala=0;
		}
		
//		Solution solution = new Solution(model);
//		model.getSolver().solve();
//		solution.record();
//
//		System.out.println("------------------------Matriz Resultado final------------------------");
//		imprimirMatrizConSolucion(matrizDemandaCapacidad, solution);


	}


	public void matrizDeLicencias(IntVar[][] matrizCarreras, Solution solucionAnterior) {

		System.out.println("Cuarta parte"+"\n");
		reporte+="Cuarta parte"+"\n"+"\n";
		Model model= new Model();

		IntVar[][] matrizPesosLicencias = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		matrizPesosLicencias=copiarMatriz(solucionAnterior, matrizCarreras);


		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if(toolSoftware.get(j).getCantLicencias() != 0 && 
						toolSoftware.get(j).getCantLicencias()<= salas.get(i).getCapacidad()) {

					model.arithm(matrizPesosLicencias[i][j], "=",1).post();
				}

			}
		}

		Solution solution = new Solution(model);
		model.getSolver().solve();
		solution.record();

		System.out.println("Resultado parcial");
		reporte+="Resultado parcial"+"\n";
		imprimirMatrizConSolucion(matrizPesosLicencias, solution);
		//imprimirMatriz(matrizPesosLicencias);

	}


	public void imprimirMatriz(IntVar[][] matriz) {

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				System.out.print(matriz[i][j].getValue());
				reporte+=matriz[i][j].getValue();
			}

			System.out.print(" "+salas.get(i).getNombre()+ " Tipo "+ salas.get(i).getTipo());
			System.out.println("");

			reporte+=" "+salas.get(i).getNombre()+ " Tipo "+ salas.get(i).getTipo();
			reporte+="\n";
		}
		for (int i = 0; i < toolSoftware.size(); i++) {

			System.out.print(toolSoftware.get(i).getNombre().charAt(0));
			reporte+=toolSoftware.get(i).getNombre().charAt(0);
		}

		System.out.println("\n");
		reporte+="\n"+"\n";

	}
	public void imprimirMatrizConSolucion(IntVar[][] matriz, Solution solut) {

		for (int i = 0; i < salas.size(); i++) {

			System.out.println(salas.get(i).getNombre());
			reporte+=salas.get(i).getNombre()+"\n";

			for (int k = 0; k < toolSoftwareBasico.size(); k++) {

				if (toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
						&& salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")) {

					System.out.println(toolSoftwareBasico.get(k).getNombre());
					reporte+=toolSoftwareBasico.get(k).getNombre()+"\n";
				}
				else {

					if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
							&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")) {

						System.out.println(toolSoftwareBasico.get(k).getNombre());
						reporte+=toolSoftwareBasico.get(k).getNombre()+"\n";
					}

				}

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (solut.getIntVal(matriz[i][j])==1) {

					if (!nombreSoftware.contains(toolSoftware.get(j).getNombre())) {

						nombreSoftware.add(toolSoftware.get(j).getNombre());

						System.out.println(toolSoftware.get(j).getNombre());
						reporte+=toolSoftware.get(j).getNombre()+"\n";
					}
				}

			}

			System.out.println("");
			reporte+="\n";
			nombreSoftware.clear();

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

	public void exportarReporteTxt(String ruta) throws FileNotFoundException {
		// TO-DO: Desarrollar el método que genera el reporte.

		File archivo= new File(ruta);

		PrintWriter escritor= new PrintWriter(archivo);

		escritor.println(reporte);

		escritor.close();

	}

	public ArrayList<Sala> getSalas() {
		return salas;
	}

	public ArrayList<String> getNombreSalas() {
		
		ArrayList<String> nombresSalas=new ArrayList<>();
		for (int i = 0; i < salas.size(); i++) {
			nombresSalas.add(salas.get(i).getNombre().toString());
		}
		return nombresSalas;
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

	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	
	public static ArrayList<String> getNombreSoftware() {
		return nombreSoftware;
	}

	public static void setNombreSoftware(ArrayList<String> nombreSoftware) {
		SolverProject.nombreSoftware = nombreSoftware;
	}
}
