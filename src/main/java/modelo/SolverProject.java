package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

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

	//Porcentajes de clasificacion de la demanda de software
	public final static double PORCENTAJE_MAX_DEMANDA_ALTO=1;
	public final static double PORCENTAJE_MIN_DEMANDA_ALTO=0.71;
	public final static double PORCENTAJE_MAX_DEMANDA_MEDIO=0.7;
	public final static double PORCENTAJE_MIN_DEMANDA_MEDIO=0.4;
	public final static double PORCENTAJE_MAX_DEMANDA_BAJO=0.39;
	public final static double PORCENTAJE_MIN_DEMANDA_BAJO=0.1;

	//lista que almacena los nombres de las herramientas de software 
	//usada en el comboBox de consulta de software
	private static ArrayList<String> softwareComboBox;

	//
	private static ArrayList<Software> toolSoftwareBasicoOrdenado;

	//
	private static ArrayList<Software> toolSoftwareBasico;

	private static List<Solution> listSolutionIniciales;

	private IntVar[][] matrizSolucionesIniciales; 

	private LectorDeArchivos lectorArchivos;

	private String reporteDistribucionExportar;

	private String reporteDistribucionVista;

	private static List<Solution> listSolutionDisco;

	private IntVar[][] matrizSolucionesDisco; 

	public static final String SEPARATOR=";";

	public SolverProject() {

		lectorArchivos= new LectorDeArchivos();
		toolSoftwareBasicoOrdenado= new ArrayList<Software>();
		toolSoftwareBasico= new ArrayList<Software>();
		softwareComboBox=new ArrayList<String>();
		listSolutionIniciales= new ArrayList<>();

		toolSoftwareBasico= lectorArchivos.getToolSoftwareBasico();

	}

	public void modeloInicial(int numSoluciones, boolean restrSoftDepartamento,
			boolean restrSoftRAM, boolean restrSistemaOperativo, boolean restrDiscoDuro,
			boolean restrSoftBasico, boolean restrNumLicencias,
			boolean restrSoftwareSalaNombre, int porcDisco) {

		Model model= new Model();

		reporteDistribucionExportar=lectorArchivos.getReporte();

		ArrayList<Sala> salas=new ArrayList<>();
		salas=lectorArchivos.getSalas();

		ArrayList<Software> toolSoftware= new ArrayList<>();
		toolSoftware=lectorArchivos.getToolSoftware();

		reporteDistribucionExportar+="Nº de salas: "+ salas.size() + " | " +"Registros de Software Cargados: "+ toolSoftware.size()+"\n"+"\n";

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
		
		listSolutionIniciales=list;
		matrizSolucionesIniciales= carreraMatrizResultado;
				
		for(Solution s:list){

			solutionRecord=s;

		}

		reporteDistribucionExportar+="Porcentaje De Disco: "+porcDisco+" %"+"\n"+"\n";

		reporteDistribucionExportar+="Soluciones encontradas: "+list.size()+"\n"+"\n";

		int nSol=1;
		for(Solution s:list){

			reporteDistribucionExportar+="Solución: " + nSol+"\n";
			
			//imprimirMatrizConSolucion(matrizSolucionesIniciales, s, restrSoftBasico, salas, toolSoftware);
			nSol++;
		}

		reporteDistribucionExportar+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";

		if(restrDiscoDuro == true) {

			restriccionesDeDisco(carreraMatrizResultado,solutionRecord, restrDiscoDuro,
					restrSoftBasico, porcDisco, salas, toolSoftware, numSoluciones);
		}


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

				} 

				else if (matrizPesos[i][j].getValue() != 0 && restrDisco == true) {

					model.arithm(matrizResultado[i][j], "=", 1).post();

				}
			}
			pesoPorSala=0;
		}

		Criterion solcpt = new SolutionCounter(model, numSoluciones);
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		listSolutionDisco=list;
		matrizSolucionesDisco= matrizResultado;

		int nSol=1;
		for(Solution s:listSolutionDisco){

			reporteDistribucionExportar+="Solución: " + nSol+"\n";
			//imprimirMatrizConSolucion(matrizSolucionesDisco, s, softBasic, salas, toolSoftware);

			nSol++;
		}
		reporteDistribucionExportar+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";

	}

//	public void imprimirMatrizConSolucion(IntVar[][] matriz, Solution solut, boolean softBasico, 
//			ArrayList<Sala> salas, ArrayList<Software> toolSoftware) {
//
//		ArrayList<String> arreglo=new ArrayList<>();
//
//		Collections.sort(toolSoftwareBasico, new Comparator<Software>() {
//			public int compare(Software obj1, Software obj2) {
//
//				return obj1.getNombre().compareTo(obj2.getNombre());								
//
//			}
//		});
//
//		Collections.sort(toolSoftware, new Comparator<Software>() {
//			public int compare(Software obj1, Software obj2) {
//
//				return obj1.getNombre().compareTo(obj2.getNombre());								
//
//			}
//		});
//
//		for (int i = 0; i < salas.size(); i++) {
//
//			reporteDistribucionExportar+=salas.get(i).getNombre()+"\n";
//
//			for (int k = 0; k < toolSoftwareBasico.size(); k++) {
//
//				if (toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
//						&& salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
//						&& softBasico == true) {
//
//					reporteDistribucionExportar+=toolSoftwareBasico.get(k).getNombre()+"\n";
//					arreglo.add(toolSoftwareBasico.get(k).getNombre());
//
//					if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
//						softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());
//
//					}
//
//				}
//				else {
//
//					if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
//							&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
//							&& softBasico == true) {
//
//						reporteDistribucionExportar+=toolSoftwareBasico.get(k).getNombre()+"\n";
//						arreglo.add(toolSoftwareBasico.get(k).getNombre());
//
//						if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
//							softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());
//
//						}
//					}
//
//				}
//
//			}
//
//			for (int j = 0; j < toolSoftware.size(); j++) {
//
//				try {
//
//					if (solut.getIntVal(matriz[i][j])==1) {
//
//						if (!arreglo.contains(toolSoftware.get(j).getNombre())) {
//
//							arreglo.add(toolSoftware.get(j).getNombre());
//
//							reporteDistribucionExportar+=toolSoftware.get(j).getNombre()+"\n";
//
//						}
//						if (!softwareComboBox.contains(toolSoftware.get(j).getNombre())) {
//							softwareComboBox.add(toolSoftware.get(j).getNombre());
//
//						}
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
//
//			if (arreglo.size()==0) {
//
//				reporteDistribucionExportar+="El consumo de disco duro de las herramientas de software ha "
//						+ "superado la capacidad de disco de los computadores en esta sala"+"\n";
//			}
//
//			reporteDistribucionExportar+="Cantidad total de Software a instalar en la sala "
//					+salas.get(i).getNombre()+": "+arreglo.size()+"\n";
//
//			reporteDistribucionExportar+="\n";
//			arreglo.clear();
//
//		}
//
//	}

	public void imprimirMatrizConSolucionVista(IntVar[][] matriz, Solution solut, boolean softBasico, 
			ArrayList<Sala> salas, ArrayList<Software> toolSoftware, String salaEspecifica, int porcDisco) {

		ArrayList<String> arreglo=new ArrayList<>();

		reporteDistribucionVista="";
		reporteDistribucionVista=lectorArchivos.getReporte();
		reporteDistribucionVista+="Nº de salas: "+ salas.size() + " | " +"Registros de Software Cargados: "+ toolSoftware.size()+"\n"+"\n";
		reporteDistribucionVista+="Porcentaje De Disco: "+porcDisco+" %"+"\n"+"\n";

		Collections.sort(toolSoftwareBasico, new Comparator<Software>() {
			public int compare(Software obj1, Software obj2) {

				return obj1.getNombre().compareTo(obj2.getNombre());								

			}
		});

		Collections.sort(toolSoftware, new Comparator<Software>() {
			public int compare(Software obj1, Software obj2) {

				return obj1.getNombre().compareTo(obj2.getNombre());								

			}
		});

		//int pesoPorSala=0;
		
		for (int i = 0; i < salas.size(); i++) {

			reporteDistribucionExportar+=salas.get(i).getNombre()+"\n";
			
			if (salaEspecifica.equals(salas.get(i).getNombre())) {

				reporteDistribucionVista+=salaEspecifica+"\n";
				reporteDistribucionExportar+=salaEspecifica+"\n";
				
				for (int k = 0; k < toolSoftwareBasico.size(); k++) {
										
					if (toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
							&& salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
							&& softBasico == true) {

						reporteDistribucionVista+=toolSoftwareBasico.get(k).getNombre()+"\n";
						reporteDistribucionExportar+=toolSoftwareBasico.get(k).getNombre()+"\n";
						
						arreglo.add(toolSoftwareBasico.get(k).getNombre());
						//pesoPorSala+=toolSoftwareBasico.get(k).getDiscoDuro();
						
						if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
							softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());
						}

					}
					else {

						if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
								&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
								&& softBasico == true) {

							reporteDistribucionVista+=toolSoftwareBasico.get(k).getNombre()+"\n";
							reporteDistribucionExportar+=toolSoftwareBasico.get(k).getNombre()+"\n";
							
							arreglo.add(toolSoftwareBasico.get(k).getNombre());
							//pesoPorSala+=toolSoftwareBasico.get(k).getDiscoDuro();
							
							if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
								softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());

							}
						}

					}

				}

				for (int j = 0; j < toolSoftware.size(); j++) {

					try {

						if (solut.getIntVal(matriz[i][j])==1) {

							//pesoPorSala+=toolSoftwareBasico.get(j).getDiscoDuro();
							
							if (!arreglo.contains(toolSoftware.get(j).getNombre())) {

								arreglo.add(toolSoftware.get(j).getNombre());

								reporteDistribucionVista+=toolSoftware.get(j).getNombre()+"\n";
								reporteDistribucionExportar+=toolSoftware.get(j).getNombre()+"\n";
								
							}
							if (!softwareComboBox.contains(toolSoftware.get(j).getNombre())) {
								softwareComboBox.add(toolSoftware.get(j).getNombre());

							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				if (arreglo.size()==0) {

					reporteDistribucionVista+="El consumo de disco duro de las herramientas de software ha "
							+ "superado la capacidad de disco de los computadores en esta sala"+"\n";
					
					reporteDistribucionExportar+="El consumo de disco duro de las herramientas de software ha "
							+ "superado la capacidad de disco de los computadores en esta sala"+"\n";
				}

				reporteDistribucionVista+="Cantidad total de Software a instalar en la sala "
						+salaEspecifica+": "+arreglo.size()+"\n";
//						"Porcentaje utilizado de disco duro: "
//						+(pesoPorSala/salas.get(i).getComputadores().getDiscoDuro())+"\n";
				
				reporteDistribucionExportar+="Cantidad total de Software a instalar en la sala "
						+salaEspecifica+": "+arreglo.size()+"\n";
//						"Porcentaje utilizado de disco duro: "
//						+(pesoPorSala/salas.get(i).getComputadores().getDiscoDuro())+"\n";

				reporteDistribucionExportar+="\n";
				reporteDistribucionVista+="\n";
				arreglo.clear();

			}
//			pesoPorSala=0;
		}
//		System.out.println(reporteDistribucionExportar);
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
		return reporteDistribucionExportar;
	}

	public void setReporteDistribucion(String reporteDistribucion) {
		this.reporteDistribucionExportar = reporteDistribucion;
	}

	public List<Solution> getListSolution() {
		return listSolutionIniciales;
	}

	public void setListSolution(List<Solution> listSolution) {
		SolverProject.listSolutionIniciales = listSolution;
	}

	public IntVar[][] getMatrizSolucionesIniciales() {
		return matrizSolucionesIniciales;
	}

	public void setMatrizSolucionesIniciales(IntVar[][] matrizSolucionesIniciales) {
		this.matrizSolucionesIniciales = matrizSolucionesIniciales;
	}

	public List<Solution> getListSolutionDisco() {
		return listSolutionDisco;
	}

	public void setListSolutionDisco(List<Solution> listSolutionDisco) {
		SolverProject.listSolutionDisco = listSolutionDisco;
	}

	public IntVar[][] getMatrizSolucionesDisco() {
		return matrizSolucionesDisco;
	}

	public void setMatrizSolucionesDisco(IntVar[][] matrizSolucionesDisco) {
		this.matrizSolucionesDisco = matrizSolucionesDisco;
	}

	public String getReporteDistribucionVista() {
		return reporteDistribucionVista;
	}

	public void setReporteDistribucionVista(String reporteDistribucionExportar) {
		this.reporteDistribucionVista = reporteDistribucionExportar;
	}

}
