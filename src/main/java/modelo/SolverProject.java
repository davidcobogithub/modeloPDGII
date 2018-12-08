package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.SynchronousQueue;

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

	public final static double PORCENTAJE_MAX_DEMANDA_ALTO=1;
	public final static double PORCENTAJE_MIN_DEMANDA_ALTO=0.71;
	public final static double PORCENTAJE_MAX_DEMANDA_MEDIO=0.7;
	public final static double PORCENTAJE_MIN_DEMANDA_MEDIO=0.41;
	public final static double PORCENTAJE_MAX_DEMANDA_BAJO=0.4;
	public final static double PORCENTAJE_MIN_DEMANDA_BAJO=0.1;

	public final static int PORCENTAJE_DISPONIBLE=70;

	private static ArrayList<String> nombreSoftware;
	private static ArrayList<String> softwareComboBox;
	private static ArrayList<Software> toolSoftwareBasicoOrdenado;
	private static ArrayList<Software> toolSoftwareBasico;

	private LectorDeArchivos lectorArchivos;
	
	private String reporteDistribucion;

	public static final String SEPARATOR=";";

	public SolverProject() {

		lectorArchivos= new LectorDeArchivos();
	
		nombreSoftware=new ArrayList<String>();
		toolSoftwareBasicoOrdenado= new ArrayList<Software>();
		toolSoftwareBasico= new ArrayList<Software>();
		softwareComboBox=new ArrayList<String>();
		
		toolSoftwareBasico= lectorArchivos.getToolSoftwareBasico();
		
	}

	public void modeloInicial(int numSoluciones, boolean restrSoftDepartamento,
			boolean restrSoftRAM, boolean restrSistemaOperativo, boolean restrDiscoDuro, 
			boolean restrDemandaCapacidad, boolean restrSoftBasico, boolean restrNumLicencias,
			boolean restrSoftwareSalaNombre, int porcDisco) {

		Model model= new Model();
		
		System.out.println(lectorArchivos.getReporte());
		reporteDistribucion=lectorArchivos.getReporte();
		
		ArrayList<Sala> salas=new ArrayList<>();
		salas=lectorArchivos.getSalas();
	
		ArrayList<Software> toolSoftware= new ArrayList<>();
		toolSoftware=lectorArchivos.getToolSoftware();
	
		System.out.println("Nº de salas: "+salas.size() + " | " + "Registros de Software Cargados: "+toolSoftware.size()+"\n");
		reporteDistribucion+="Nº de salas: "+ salas.size() + " | " +"Registros de Software Cargados: "+ toolSoftware.size()+"\n"+"\n";

		IntVar[][] carrera = model.intVarMatrix("carrera", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (toolSoftware.get(j).getNombre().equals("Office") && !salas.get(i).getTipo().equals(TIPO_DISENO)) {

					model.arithm(carrera[i][j], "=",1).post();
				}

				else if(toolSoftware.get(j).getSistemaOperativo().toUpperCase().contains("MAC") &&
						salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC") 
						&& restrSistemaOperativo == true) {

					model.arithm(carrera[i][j], "=",1).post();
				}

				else  if (!salas.get(i).getTipo().contains(toolSoftware.get(j).getTipoDepartamento()) &&
						restrSoftDepartamento == true) {

					model.arithm(carrera[i][j], "=",0).post();
				}

				else  if (toolSoftware.get(j).getMemoriaRAM() <= salas.get(i).getComputadores().getMemoriaRAM() &&
						restrSoftRAM == true) {

					model.arithm(carrera[i][j], "=",1).post();
				}

				else if(toolSoftware.get(j).getNombreSala().equals(salas.get(i).getNombre()) &&
						restrSoftwareSalaNombre == true) {

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

		System.out.println("Soluciones encontradas: "+list.size()+"\n");
		reporteDistribucion+="Soluciones encontradas: "+list.size()+"\n"+"\n";

		int nSol=1;
		for(Solution s:list){

			System.out.println("Solución: " + nSol);
			reporteDistribucion+="Solución: " + nSol+"\n";
			
			imprimirMatrizConSolucion(carrera, s, restrSoftBasico, salas, toolSoftware);
			solutionRecord=s;
			nSol++;
		}

		System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n");
		reporteDistribucion+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";
		restriccionesDeDisco(carrera,solutionRecord, restrDiscoDuro, restrDemandaCapacidad, 
				restrNumLicencias, restrSoftBasico, porcDisco, salas, toolSoftware);

	}

	public void restriccionesDeDisco(IntVar[][] matrizCarreras, Solution solucionAnterior, 
			boolean restrDisco , boolean demandaCapacidad, boolean licenciasSoftware, boolean softBasic,
			int porcentajeDisco, ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {

		if (restrDisco == true) {

			System.out.println("--------------------------------------------	Restricción de Disco	--------------------------------------------"+"\n");
			reporteDistribucion+="--------------------------------------------	Restricción de Disco	--------------------------------------------"+"\n"+"\n";	
		}

		Model model= new Model();

		IntVar[][] matrizPesos = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras,salas, toolSoftware);


		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (matrizCopia[i][j].getValue()==1) {

					matrizPesos[i][j]=matrizCopia[i][j].mul(toolSoftware.get(j).getDiscoDuro()).intVar();

				}

			}
		}

		IntVar[][] matrizResultado = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		int pesoPorSala=0;

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				pesoPorSala+=matrizPesos[i][j].getValue();

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (pesoPorSala>=(salas.get(i).getComputadores().getDiscoDuro()*porcentajeDisco)/100 &&
						restrDisco == true) {

					model.arithm(matrizResultado[i][j], "=", 0).post();

					System.out.println(pesoPorSala + " | "+ (salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100
							+" Ojo, en la sala " +salas.get(i).getNombre()
							+" la cantidad de espacio de disco de software supera la capacidad de disco del computador ");

					reporteDistribucion+=pesoPorSala + " | "+ (salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100
							+" Ojo, en la sala " +salas.get(i).getNombre()
							+" la cantidad de espacio de disco de software supera la capacidad de disco del computador "+"\n";
				} 

				else if (matrizPesos[i][j].getValue() != 0 && restrDisco == true) {

					model.arithm(matrizResultado[i][j], "=", 1).post();

				}
			}
			pesoPorSala=0;
		}

		Solution solution = new Solution(model);
		model.getSolver().solve();
		solution.record();

		if (restrDisco == true) {
		
			imprimirMatrizConSolucion(matrizResultado, solution, softBasic, salas, toolSoftware);
			//imprimirMatriz(matrizResultado);	
		}

		restriccionDemandaCapacidadSalas(matrizResultado, solution, demandaCapacidad, 
				licenciasSoftware, softBasic, salas, toolSoftware);

	}

	public void restriccionDemandaCapacidadSalas(IntVar[][] matrizCarreras, Solution solucionAnterior, 
			boolean demanda, boolean licenciaSoft, boolean softwareBasico,ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {

		if (demanda == true) {

			System.out.println("--------------------------------------------	Restricción de Demanda y Capacidad		--------------------------------------------"+"\n");
			reporteDistribucion+="--------------------------------------------	Restricción de Demanda y Capacidad 	--------------------------------------------"+"\n"+"\n";	
		}
	
		Model model= new Model();

		//		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		//		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras);

		Map<String, Software> map = new HashMap<String, Software>();

		for(Software p : toolSoftware) {
			map.put(p.getNombre(), p);
		}

		for(Entry<String, Software> p : map.entrySet()) {
			toolSoftwareBasicoOrdenado.add(p.getValue());

		}

		Collections.sort(toolSoftwareBasicoOrdenado, new Comparator<Software>() {
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

		int limiteDelArreglo=toolSoftwareBasicoOrdenado.size()/3;

		IntVar[][] matrizResultadoDemanda = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			//Alta Demanda
			for (int j = 0; j < limiteDelArreglo; j++) {

				int max= (int)Math.ceil(salas.size()*PORCENTAJE_MAX_DEMANDA_ALTO);
				int min= (int)Math.floor(salas.size()*PORCENTAJE_MIN_DEMANDA_ALTO);

				if (i >= min && i <= max && demanda == true) {

					model.arithm(matrizResultadoDemanda[i][j], "=", 1).post();

				}

			}

			//Media Demanda
			for (int j = limiteDelArreglo; j < limiteDelArreglo*2; j++) {

				int max= (int)Math.ceil(salas.size()*PORCENTAJE_MAX_DEMANDA_MEDIO);
				int min= (int)Math.floor(salas.size()*PORCENTAJE_MIN_DEMANDA_MEDIO);

				if (i >= min && i <= max && demanda == true) {

					model.arithm(matrizResultadoDemanda[i][j], "=", 1).post();

				}
			}

			//Baja Demanda
			for (int j = limiteDelArreglo*2; j < toolSoftwareBasicoOrdenado.size(); j++) {

				int max= (int)Math.ceil(salas.size()*PORCENTAJE_MAX_DEMANDA_BAJO);
				int min= (int)Math.floor(salas.size()*PORCENTAJE_MIN_DEMANDA_BAJO);

				if (i >= min && i <= max && demanda == true) {

					model.arithm(matrizResultadoDemanda[i][j], "=", 1).post();

				}

			}
		}

		Solution solution = new Solution(model);
		model.getSolver().solve();
		solution.record();


		if (demanda == true) {
			
			imprimirMatrizConSolucion(matrizResultadoDemanda, solution, softwareBasico, salas, toolSoftware);
			imprimirMatriz(matrizResultadoDemanda, salas, toolSoftware);	
		}

		matrizDeLicencias(matrizCarreras, solucionAnterior, licenciaSoft,softwareBasico, salas, toolSoftware);

	}


	public void matrizDeLicencias(IntVar[][] matrizCarreras, Solution solucionAnterior, boolean licencias,
			boolean softwareBasi,ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {

		if (licencias == true) {
			
			System.out.println("--------------------------------------------	Restricción de Licencias	--------------------------------------------"+"\n");
			reporteDistribucion+="--------------------------------------------	Restricción de Licencias 	--------------------------------------------"+"\n"+"\n";	
		}
		
		Model model= new Model();

		IntVar[][] matrizPesosLicencias = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		matrizPesosLicencias=copiarMatriz(solucionAnterior, matrizCarreras,salas, toolSoftware);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if(toolSoftware.get(j).getCantLicencias() != 0 && 
						toolSoftware.get(j).getCantLicencias()<= salas.get(i).getCapacidad() &&
						licencias == true) {

					model.arithm(matrizPesosLicencias[i][j], "=",1).post();
				}

			}
		}

		Solution solution = new Solution(model);
		model.getSolver().solve();
		solution.record();

		//imprimirMatriz(matrizPesosLicencias);

		if (licencias == true) {
			
//			System.out.println("------------------------Matriz Resultado final------------------------");
//			reporte+="------------------------Matriz Resultado final------------------------"+"\n";
			imprimirMatrizConSolucion(matrizPesosLicencias, solution, softwareBasi, salas, toolSoftware);	
		}
		
	}

	public void imprimirMatriz(IntVar[][] matriz, ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				System.out.print(matriz[i][j].getValue());
				reporteDistribucion+=matriz[i][j].getValue();
			}

			System.out.print(" "+salas.get(i).getNombre()+ " Tipo "+ salas.get(i).getTipo());
			System.out.println("");

			reporteDistribucion+=" "+salas.get(i).getNombre()+ " Tipo "+ salas.get(i).getTipo();
			reporteDistribucion+="\n";
		}
		for (int i = 0; i < toolSoftware.size(); i++) {

			System.out.print(toolSoftware.get(i).getNombre().charAt(0));
			reporteDistribucion+=toolSoftware.get(i).getNombre().charAt(0);
		}

		System.out.println("\n");
		reporteDistribucion+="\n"+"\n";

	}
	public void imprimirMatrizConSolucion(IntVar[][] matriz, Solution solut, boolean softBasico, 
			ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {
		
		ArrayList<String> arreglo=new ArrayList<>();
		
		for (int i = 0; i < salas.size(); i++) {

			System.out.println(salas.get(i).getNombre());
			reporteDistribucion+=salas.get(i).getNombre()+"\n";

			for (int k = 0; k < toolSoftwareBasico.size(); k++) {

				if (toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
						&& salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
						&& softBasico == true) {

					System.out.println(toolSoftwareBasico.get(k).getNombre());
					reporteDistribucion+=toolSoftwareBasico.get(k).getNombre()+"\n";
					arreglo.add(toolSoftwareBasico.get(k).getNombre());
				}
				else {

					if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
							&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
							&& softBasico == true) {

						System.out.println(toolSoftwareBasico.get(k).getNombre());
						reporteDistribucion+=toolSoftwareBasico.get(k).getNombre()+"\n";
						arreglo.add(toolSoftwareBasico.get(k).getNombre());
					}

				}

			}

			//softwareComboBox=toolSoftwareBasico;
			
			for (int j = 0; j < toolSoftware.size(); j++) {

				if (solut.getIntVal(matriz[i][j])==1) {

					if (!nombreSoftware.contains(toolSoftware.get(j).getNombre())) {

						nombreSoftware.add(toolSoftware.get(j).getNombre());
						arreglo.add(toolSoftware.get(j).getNombre());
						System.out.println(toolSoftware.get(j).getNombre());
						reporteDistribucion+=toolSoftware.get(j).getNombre()+"\n";

					}
					if (!softwareComboBox.contains(toolSoftware.get(j).getNombre())) {
						softwareComboBox.add(toolSoftware.get(j).getNombre());

					}
				}

			}
			
			System.out.println("Cantidad total de Software a instalar en la sala "
			+salas.get(i).getNombre()+": "+arreglo.size());
			reporteDistribucion+="Cantidad total de Software a instalar en la sala "
					+salas.get(i).getNombre()+": "+arreglo.size()+"\n";
			
			System.out.println("");
			reporteDistribucion+="\n";
			nombreSoftware.clear();
			arreglo.clear();

		}

	}

	public static IntVar[][] copiarMatriz(Solution solv, IntVar[][] matriz, 
			ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {
		
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

	public ArrayList<String> getNombreSalas() {

		ArrayList<String> nombresSalas=new ArrayList<>();
		
		for (int i = 0; i < lectorArchivos.getSalas().size(); i++) {
			nombresSalas.add(lectorArchivos.getSalas().get(i).getNombre().toString());
		}
		return nombresSalas;
	}

	public ArrayList<String> getNombreSoftware() {
		return softwareComboBox;
	}

	public void setNombreSoftware(ArrayList<String> nombreSoftware) {
		softwareComboBox = nombreSoftware;
	}

	public static ArrayList<Software> getToolSoftwareBasico() {
		return toolSoftwareBasico;
	}

	public static void setToolSoftwareBasico(ArrayList<Software> toolSoftwareBasico) {
		SolverProject.toolSoftwareBasico = toolSoftwareBasico;
	}

	public String getReporteDistribucion() {
		return reporteDistribucion;
	}

	public void setReporteDistribucion(String reporteDistribucion) {
		this.reporteDistribucion = reporteDistribucion;
	}

}
