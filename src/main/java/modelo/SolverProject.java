package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public final static double PORCENTAJE_MIN_DEMANDA_MEDIO=0.4;
	public final static double PORCENTAJE_MAX_DEMANDA_BAJO=0.39;
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
			boolean restrSoftBasico, boolean restrNumLicencias,
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

		IntVar[][] carreraMatrizResultado = model.intVarMatrix("carrera", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				if (toolSoftware.get(j).getNombre().equals("Office") && salas.get(i).getTipo().equals(TIPO_DISENO)) {

					model.arithm(carreraMatrizResultado[i][j], "=",0).post();
				}

				else if(toolSoftware.get(j).getSistemaOperativo().toUpperCase().contains("MAC") &&
						salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC") 
						&& restrSistemaOperativo == true) {

					model.arithm(carreraMatrizResultado[i][j], "=",1).post();
				}

				else  if (salas.get(i).getTipo().contains(toolSoftware.get(j).getTipoDepartamento()) &&
						restrSoftDepartamento == true) {

					model.arithm(carreraMatrizResultado[i][j], "=",1).post();
				}

				else  if (toolSoftware.get(j).getMemoriaRAM() >= salas.get(i).getComputadores().getMemoriaRAM() &&
						restrSoftRAM == true) {

					model.arithm(carreraMatrizResultado[i][j], "=",0).post();
				}

				else if(toolSoftware.get(j).getNombreSala().equals(salas.get(i).getNombre()) &&
						restrSoftwareSalaNombre == true) {

					model.arithm(carreraMatrizResultado[i][j], "=",1).post();
				}
				else if(toolSoftware.get(j).getCantLicencias() == 0 && 
						toolSoftware.get(j).getCantLicencias() >= salas.get(i).getCapacidad() && 
						restrNumLicencias == true) {

					model.arithm(carreraMatrizResultado[i][j], "=",0).post();
				}
			}
		}

		Solution solutionRecord= new Solution(model);

		Criterion solcpt = new SolutionCounter(model, numSoluciones);
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		for(Solution s:list){

			solutionRecord=s;
		}

		//Mi PC 3000
		//PC Liason 15000

		System.out.println("Porcentaje De Disco: "+ porcDisco+" %"+"\n");
		reporteDistribucion+="Porcentaje De Disco: "+porcDisco+" %"+"\n"+"\n";

		if (restrDiscoDuro != true) {

			System.out.println("Soluciones encontradas: "+list.size()+"\n");
			reporteDistribucion+="Soluciones encontradas: "+list.size()+"\n"+"\n";

			int nSol=1;
			for(Solution s:list){

				System.out.println("Solución: " + nSol);
				reporteDistribucion+="Solución: " + nSol+"\n";
				solutionRecord=s;
				imprimirMatrizConSolucion(carreraMatrizResultado, s, restrSoftBasico, salas, toolSoftware);

				nSol++;
			}

			System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n");
			reporteDistribucion+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";

		}
		else if(restrDiscoDuro == true) {
			restriccionesDeDisco(carreraMatrizResultado,solutionRecord, restrDiscoDuro
					, restrSoftBasico, porcDisco, salas, toolSoftware, numSoluciones);
		}
//		else if(restrDemandaCapacidad == true) {
//
//			restriccionDemandaCapacidadSalas(carreraMatrizResultado, solutionRecord, restrDemandaCapacidad,
//					restrSoftBasico, salas, toolSoftware, numSoluciones);
//		}

	}

	public void restriccionesDeDisco(IntVar[][] matrizCarreras, Solution solucionAnterior, 
			boolean restrDisco, boolean softBasic,
			int porcentajeDisco, ArrayList<Sala> salas, ArrayList<Software> toolSoftware,int numSoluciones) {

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
					
//					System.out.println(pesoPorSala + " | "+ (salas.get(i).getComputadores().getDiscoDuro()*porcentajeDisco)/100
//							+" Ojo, en la sala " +salas.get(i).getNombre()
//							+" la cantidad de espacio de disco de software supera la capacidad de disco del computador ");

//					reporte+=pesoPorSala + " | "+ (salas.get(i).getComputadores().getDiscoDuro()*PORCENTAJE_DISPONIBLE)/100
//							+" Ojo, en la sala " +salas.get(i).getNombre()
//							+" la cantidad de espacio de disco de software supera la capacidad de disco del computador "+"\n";
				} 

				else if (matrizPesos[i][j].getValue() != 0 && restrDisco == true) {

					model.arithm(matrizResultado[i][j], "=", 1).post();

				}
			}
			pesoPorSala=0;
		}

		Solution solutionRecord= new Solution(model);

		Criterion solcpt = new SolutionCounter(model, numSoluciones);
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		for(Solution s:list){

			solutionRecord=s;

		}

		//		System.out.println("Porcentaje De Disco: "+ porcDisco+" %"+"\n");
		//		reporteDistribucion+="Porcentaje De Disco: "+porcDisco+" %"+"\n"+"\n";

	

			System.out.println("Soluciones encontradas: "+list.size()+"\n");
			reporteDistribucion+="Soluciones encontradas: "+list.size()+"\n"+"\n";

			int nSol=1;
			for(Solution s:list){

				System.out.println("Solución: " + nSol);
				reporteDistribucion+="Solución: " + nSol+"\n";
				solutionRecord=s;
				imprimirMatrizConSolucion(matrizResultado, s, softBasic, salas, toolSoftware);

				nSol++;
			}

			System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n");
			reporteDistribucion+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";

		
//		else {
//
//			restriccionDemandaCapacidadSalas(matrizCopia, solutionRecord,
//					softBasic, salas, toolSoftware, numSoluciones);
//		}
	}

	public void restriccionDemandaCapacidadSalas(IntVar[][] matrizCarreras, Solution solucionAnterior, 
			boolean demanda, boolean softwareBasico,ArrayList<Sala> salas, 
			ArrayList<Software> toolSoftware, int numSoluciones) {

		Model model= new Model();

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

		//		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		//		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras,salas, toolSoftware);
		IntVar[][] matrizResultadoDemanda = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			//Alta Demanda
			for (int k = 0; k < limiteDelArreglo; k++) {

				int max= (int)Math.ceil(salas.size()*PORCENTAJE_MAX_DEMANDA_ALTO);
				int min= (int)Math.ceil(salas.size()*PORCENTAJE_MIN_DEMANDA_ALTO);

				if (i >= min && i <= max && demanda == true) {

					model.arithm(matrizResultadoDemanda[i][k], "=", 1).post();

				}

			}

			//Media Demanda
			for (int k = limiteDelArreglo; k < limiteDelArreglo*2; k++) {

				int max= (int)Math.floor(salas.size()*PORCENTAJE_MAX_DEMANDA_MEDIO);
				int min= (int)Math.floor(salas.size()*PORCENTAJE_MIN_DEMANDA_MEDIO);

				if (i >= min && i <= max && demanda == true) {

					model.arithm(matrizResultadoDemanda[i][k], "=", 1).post();

				}
			}

			//Baja Demanda
			for (int k = limiteDelArreglo*2; k < toolSoftwareBasicoOrdenado.size(); k++) {

				int max= (int)Math.floor(salas.size()*PORCENTAJE_MAX_DEMANDA_BAJO);
				int min= (int)Math.ceil(salas.size()*PORCENTAJE_MIN_DEMANDA_BAJO);

				if (i >= min && i <= max && demanda == true) {

					model.arithm(matrizResultadoDemanda[i][k], "=", 1).post();

				}

			}
		}

		Criterion solcpt = new SolutionCounter(model, numSoluciones);
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		//		System.out.println("Porcentaje De Disco: "+ porcDisco+" %"+"\n");
		//		reporteDistribucion+="Porcentaje De Disco: "+porcDisco+" %"+"\n"+"\n";

		System.out.println("Soluciones encontradas: "+list.size()+"\n");
		reporteDistribucion+="Soluciones encontradas: "+list.size()+"\n"+"\n";

		int nSol=1;
		for(Solution s:list){

			System.out.println("Solución: " + nSol);
			reporteDistribucion+="Solución: " + nSol+"\n";

			imprimirMatrizConSolucion(matrizResultadoDemanda, s, softwareBasico, salas, toolSoftware);

			nSol++;
		}

		System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n");
		reporteDistribucion+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";

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

					if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
						softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());

					}

				}
				else {

					if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
							&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
							&& softBasico == true) {

						System.out.println(toolSoftwareBasico.get(k).getNombre());
						reporteDistribucion+=toolSoftwareBasico.get(k).getNombre()+"\n";
						arreglo.add(toolSoftwareBasico.get(k).getNombre());

						if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
							softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());

						}
					}

				}

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				try {

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
				} catch (Exception e) {
					// TODO: handle exception
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
