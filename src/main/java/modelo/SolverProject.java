package modelo;

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

	//Tipo de salas
	public final static String TIPO_TICS="TIC";
	public final static String TIPO_INDUSTRIAL="IND";
	public final static String TIPO_DISENO="DIS";
	public final static String TIPO_IDIOMAS="IDI";
	public final static String TIPO_FINANZAS="FIN";

	//lista que almacena los nombres de las herramientas de software 
	//usada en el comboBox de consulta de software
	private static ArrayList<String> softwareComboBox;

	//
	private static ArrayList<Software> toolSoftwareBasico;

	private IntVar[][] matrizSolucionesIniciales; 

	private IntVar[][] matrizSolucionesDisco; 

	private List<Solution> listSolutionIniciales;

	private List<Solution> listSolutionDisco;

	private String reporteDistribucionExportar;

	private String reporteDistribucionVista;

	private LectorDeArchivos lectorArchivos;

	public SolverProject() {

		lectorArchivos= new LectorDeArchivos();
		toolSoftwareBasico= new ArrayList<Software>();
		softwareComboBox=new ArrayList<String>();

		toolSoftwareBasico= lectorArchivos.getToolSoftwareBasico();

	}

	public void modeloInicial(int numSoluciones, boolean restrSoftDepartamento,
			boolean restrSoftRAM, boolean restrSistemaOperativo, boolean restrDiscoDuro,
			boolean restrNumLicencias, boolean restrSoftwareSalaNombre, int porcDisco) {

		Model model= new Model();

		ArrayList<Sala> salas=new ArrayList<>();
		salas=lectorArchivos.getSalas();

		ArrayList<Software> toolSoftware= new ArrayList<>();
		toolSoftware=lectorArchivos.getToolSoftware();


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
		listSolutionIniciales= new ArrayList<>();
		listSolutionIniciales.addAll(list);
		matrizSolucionesIniciales= carreraMatrizResultado;

		for(Solution s:list){

			solutionRecord=s;

		}

		if(restrDiscoDuro == true) {

			restriccionesDeDisco(carreraMatrizResultado,solutionRecord, restrDiscoDuro,
					porcDisco, salas, toolSoftware, numSoluciones);
		}


	}

	public void restriccionesDeDisco(IntVar[][] matrizCarreras, Solution solucionAnterior, 
			boolean restrDisco, int porcentajeDisco, ArrayList<Sala> salas, 
			ArrayList<Software> toolSoftware,int numSoluciones) {

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
		listSolutionDisco= new ArrayList<>();
		listSolutionDisco.addAll(list);
		matrizSolucionesDisco= matrizResultado;

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

	public void imprimirMatrizConSolucionExportar(IntVar[][] matriz, Solution solut, boolean softBasico, 
			ArrayList<Sala> salas, ArrayList<Software> toolSoftware, int porcDisco, String nombreSolucion) {

		ArrayList<String> arreglo=new ArrayList<>();

		reporteDistribucionExportar="";
		reporteDistribucionExportar=lectorArchivos.getReporte();
		reporteDistribucionExportar+="Nº de salas: "+ salas.size() + " | " +"Registros de Software Cargados: "+ toolSoftware.size()+"\n"+"\n";
		reporteDistribucionExportar+="Porcentaje De Disco: "+porcDisco+" %"+"\n"+"\n";

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

		int pesoPorSala=0;

		reporteDistribucionExportar+=nombreSolucion+"\n"+"\n";

		for (int i = 0; i < salas.size(); i++) {

			reporteDistribucionExportar+=salas.get(i).getNombre()+"\n";

			for (int k = 0; k < toolSoftwareBasico.size(); k++) {

				if (softBasico == true) {
					pesoPorSala+=toolSoftwareBasico.get(k).getDiscoDuro();
				}

				if (toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
						&& salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
						&& softBasico == true) {

					reporteDistribucionExportar+=toolSoftwareBasico.get(k).getNombre()+"\n";

					if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
						softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());

					}
				}
				else {

					if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
							&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
							&& softBasico == true) {

						reporteDistribucionExportar+=toolSoftwareBasico.get(k).getNombre()+"\n";

						//							arreglo.add(toolSoftwareBasico.get(k).getNombre());
						//
						if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
							softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());

						}
					}

				}

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				try {

					if (solut.getIntVal(matriz[i][j])==1) {

						pesoPorSala+=toolSoftware.get(j).getDiscoDuro();

						if (!arreglo.contains(toolSoftware.get(j).getNombre())) {

							arreglo.add(toolSoftware.get(j).getNombre());

							reporteDistribucionExportar+=toolSoftware.get(j).getNombre()+"\n"+"\n";

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

				reporteDistribucionExportar+="El consumo de disco duro de las herramientas de software ha "
						+ "superado la capacidad de disco de los computadores en esta sala"+"\n"+"\n";
			}

			double valor=((double)pesoPorSala/(salas.get(i).getComputadores().getDiscoDuro()/1))*100;
			reporteDistribucionExportar+="Porcentaje utilizado de disco duro: "+ valor+"\n"+"\n";

			reporteDistribucionExportar+="Cantidad total de Software a instalar en la sala "
					+salas.get(i).getNombre()+": "+arreglo.size()+"\n"+"\n"+"\n";

			arreglo.clear();

			pesoPorSala=0;
		}
		reporteDistribucionExportar+="0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"\n";

	}

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

		int pesoPorSala=0;

		for (int i = 0; i < salas.size(); i++) {

			if (salaEspecifica.equals(salas.get(i).getNombre())) {

				reporteDistribucionVista+=salaEspecifica+"\n";

				for (int k = 0; k < toolSoftwareBasico.size(); k++) {

					if (softBasico == true) {
						pesoPorSala+=toolSoftwareBasico.get(k).getDiscoDuro();
					}

					if (toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
							&& salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
							&& softBasico == true) {

						reporteDistribucionVista+=toolSoftwareBasico.get(k).getNombre()+"\n";

						arreglo.add(toolSoftwareBasico.get(k).getNombre());

						//						if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
						//							softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());
						//						}

					}
					else {

						if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
								&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
								&& softBasico == true) {

							reporteDistribucionVista+=toolSoftwareBasico.get(k).getNombre()+"\n";

							arreglo.add(toolSoftwareBasico.get(k).getNombre());

							//							if (!softwareComboBox.contains(toolSoftwareBasico.get(k).getNombre())) {
							//								softwareComboBox.add(toolSoftwareBasico.get(k).getNombre());
							//
							//							}
						}

					}

				}

				for (int j = 0; j < toolSoftware.size(); j++) {

					try {

						if (solut.getIntVal(matriz[i][j])==1) {

							pesoPorSala+=toolSoftware.get(j).getDiscoDuro();

							if (!arreglo.contains(toolSoftware.get(j).getNombre())) {

								arreglo.add(toolSoftware.get(j).getNombre());

								reporteDistribucionVista+=toolSoftware.get(j).getNombre()+"\n";

							}
							//							if (!softwareComboBox.contains(toolSoftware.get(j).getNombre())) {
							//								softwareComboBox.add(toolSoftware.get(j).getNombre());
							//
							//							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				if (arreglo.size()==0) {

					reporteDistribucionVista+="El consumo de disco duro de las herramientas de software ha "
							+ "superado la capacidad de disco de los computadores en esta sala"+"\n"+"\n";

				}

				reporteDistribucionVista+="Cantidad total de Software a instalar en la sala "
						+salaEspecifica+": "+arreglo.size()+"\n"+"\n";

				double valor=((double)pesoPorSala/(salas.get(i).getComputadores().getDiscoDuro()/1))*100;
				reporteDistribucionVista+="Porcentaje utilizado de disco duro: "+ valor+" % \n"+"\n";

				arreglo.clear();
			}
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
		return reporteDistribucionExportar;
	}

	public void setReporteDistribucion(String reporteDistribucion) {
		this.reporteDistribucionExportar = reporteDistribucion;
	}

	public List<Solution> getListSolution() {
		return listSolutionIniciales;
	}

	public void setListSolution(List<Solution> listSolution) {
		this.listSolutionIniciales = listSolution;
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
		this.listSolutionDisco = listSolutionDisco;
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