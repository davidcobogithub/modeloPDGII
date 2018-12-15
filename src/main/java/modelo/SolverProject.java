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

	//Peso En disco duro del sistema operativo windows 10 64-bits en MegaBytes 
	public final static int SIS_OP_DISCO_WINDOWS=20480;

	//Peso En disco duro del sistema operativo MAC en MegaBytes 
	public final static int SIS_OP_DISCO_MAC=9216;

	//Lista que almacena los nombres de las herramientas de software 
	//usada en el comboBox de consulta de software
	private static ArrayList<String> softwareComboBox;

	//Lista que almacena las herramientas de software basico
	private static ArrayList<Software> toolSoftwareBasico;

	//Matriz de tipo IntVar que actua como variable del modelo encargada 
	//de satisfacer el conjusto inicial de restricciones
	private IntVar[][] matrizSolucionesIniciales; 

	//Matriz de tipo IntVar que actua como variable del modelo encargada 
	//de satisfacer el procentaje de disco duro
	private IntVar[][] matrizSolucionesDisco; 

	//Lista que almacena el conjunto de soluciones que satisfacen 
	//las restricciones aplicadas a la matrizSolucionesIniciales
	private List<Solution> listSolutionIniciales;

	//Lista que almacena el conjunto de soluciones que satisfacen 
	//la restriccion de disco duro aplicadaa la matrizSolucionesDisco
	private List<Solution> listSolutionDisco;

	//Cadena que almacena el reporte que el usuario genera en el modulo Exportar
	private String reporteDistribucionExportar;

	//Cadena que almacena el reporte que el usuario visualiza en el panel Distribucion
	private String reporteDistribucionVista;

	//Relacion entre SolverProject y LectorDeArchivos
	private LectorDeArchivos lectorArchivos;

	//Constructor de  la clase Computador
	public SolverProject() {

		lectorArchivos= new LectorDeArchivos();
		toolSoftwareBasico= new ArrayList<Software>();
		softwareComboBox=new ArrayList<String>();

		toolSoftwareBasico= lectorArchivos.getToolSoftwareBasico();

	}

	//Metodo principal en la implementacion de las restricciones utilizando las librerias de ChocoSolver
	//permite crear las variables, su dominio, aplicar restricciones y encontrar soluciones a las restricciones
	//recibe como parametros el numero de soluciones deseadas, los valores booleanos de los checkbox de 
	//restricciones como software en un departamento, habilitar memoria ram, sistema operativo, disco duro,
	//numero de licencias, software por nombre de la sala y el procentaje de disco deseado
	public void modeloInicial(int numSoluciones, boolean restrSoftDepartamento,
			boolean restrSoftRAM, boolean restrSistemaOperativo, boolean restrDiscoDuro,
			boolean restrNumLicencias, boolean restrSoftwareSalaNombre, int porcDisco) {

		//Crea un nuevo modelo ChocoSolver
		Model model= new Model();

		//Lista que obtiene la lista cargada de salas de computo
		ArrayList<Sala> salas=new ArrayList<>();
		salas=lectorArchivos.getSalas();

		//Lista que obtiene la lista cargada de las herramientas de software
		ArrayList<Software> toolSoftware= new ArrayList<>();
		toolSoftware=lectorArchivos.getToolSoftware();

		//Crea la variable carreraMatrizResultado de tipo matriz de Intvar, con nombre carrera, un tamano
		//salas X Software y posibles valores 0 ó 1
		IntVar[][] carreraMatrizResultado = model.intVarMatrix("carrera", salas.size(), toolSoftware.size(), 0, 1);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				//Restriccion fuerte en la que en ninguna sala de diseño puede tener el software Office
				//en este caso la matriz en esa posicion será asigana con el valor 0 (No se puede Instalar)
				if (toolSoftware.get(j).getNombre().equals("Office") && salas.get(i).getTipo().equals(TIPO_DISENO)) {

					//A[i][j]=0
					model.arithm(carreraMatrizResultado[i][j], "=",0).post();
				}

				//Restriccion fuerte en la que un software si su sistema operativo es MAC 
				//será asigando a su correspondiente sala MAC
				else if(toolSoftware.get(j).getSistemaOperativo().toUpperCase().contains("MAC") &&
						salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC") 
						&& restrSistemaOperativo == true) {

					//A[i][j]=1
					model.arithm(carreraMatrizResultado[i][j], "=",1).post();
				}

				//Restriccion fuerte en la que un software es categorizado como perteneciente a determinado
				//departamento y la sala tambien es de dicho departamento sera asignado a esa sala
				else  if (salas.get(i).getTipo().contains(toolSoftware.get(j).getTipoDepartamento()) &&
						restrSoftDepartamento == true) {

					//A[i][j]=1
					model.arithm(carreraMatrizResultado[i][j], "=",1).post();
				}

				//Restriccion fuerte en la que se debe cumplir que la capacidad de memoria RAM del software sea 
				//menor a la capacidad de memoria del computador de la sala. En caso de ser falso no asigna
				//el software a esa sala
				else  if (toolSoftware.get(j).getMemoriaRAM() >= salas.get(i).getComputadores().getMemoriaRAM() &&
						restrSoftRAM == true) {

					//A[i][j]=0
					model.arithm(carreraMatrizResultado[i][j], "=",0).post();
				}

				//Restriccion fuerte en la que un software es categorizado como perteneciente a determinado
				//departamento y la sala tambien es de dicho departamento sera asignado a esa sala
				else if(toolSoftware.get(j).getNombreSala().equals(salas.get(i).getNombre()) &&
						restrSoftwareSalaNombre == true) {

					//A[i][j]=1
					model.arithm(carreraMatrizResultado[i][j], "=",1).post();
				}

				//Restriccion fuerte en la que un software si es licenciado debe tener licencias que abarquen 
				//la capacidad de alguna sala el cual preferiblemente la cantidad de licencias sea igual a la 
				//capacidad de la sala
				else if(toolSoftware.get(j).getCantLicencias() == 0 && 
						toolSoftware.get(j).getCantLicencias() >= salas.get(i).getCapacidad() && 
						restrNumLicencias == true) {

					//A[i][j]=0
					model.arithm(carreraMatrizResultado[i][j], "=",0).post();
				}
			}
		}

		//Crea una solucion al modelo
		Solution solutionRecord= new Solution(model);

		//Establece un criterio de parada al buscar las posibles soluciones que satisfagan las restricciones
		//En este caso el criterio de parada es limitar las soluciones a una cantidad deseada por el usuario
		Criterion solcpt = new SolutionCounter(model, numSoluciones);

		//Crea una lista con el numero de soluciones pasadas por parametro encontradas y 
		//que satisfacen el modelo de restricciones
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		//Se inicializa la lista global de soluciones del modelo inicial de restricciones
		listSolutionIniciales= new ArrayList<>();
		listSolutionIniciales.addAll(list);

		//Asigna a la matriz global de IntVar la matriz temporal resultado del modelo solucionado
		matrizSolucionesIniciales= carreraMatrizResultado;

		//Asigna la ultima solucion de la lista de soluciones a la variable solucion solutionRecord
		//la ultima restriccion de la lista se considera mejor que las anteriores 
		for(Solution s:list){

			solutionRecord=s;

		}

		//Si se ha seleccionado el opcion de restriccion con disco duro
		if(restrDiscoDuro == true) {

			//Se dirige al metodo restriccionesDeDisco que recibe la matriz resultado anterior, la ultima 
			//solucion, los checkBox seleccionados, la lista de salas, las lista de software y el numero de soluciones
			restriccionesDeDisco(carreraMatrizResultado,solutionRecord, restrDiscoDuro,
					porcDisco, salas, toolSoftware, numSoluciones);
		}


	}

	//Metodo sub-principal en la implementacion de las restricciones utilizando las librerias de ChocoSolver
	//permite crear las variables, su dominio, aplicar restricciones y encontrar soluciones a las restricciones
	//recibe como parametros la matriz resultado anterior, la ultima solucion del modelo anterior, 
	//los valores booleanos de los checkbox, el procentaje de disco deseado, entre otros
	public void restriccionesDeDisco(IntVar[][] matrizCarreras, Solution solucionAnterior, 
			boolean restrDisco, int porcentajeDisco, ArrayList<Sala> salas, 
			ArrayList<Software> toolSoftware,int numSoluciones) {

		//Crea un nuevo modelo ChocoSolver
		Model model= new Model();

		//Crea la variable matrizPesos y matrizCopia de tipo matriz de Intvar y posibles valores 0 ó 1
		IntVar[][] matrizPesos = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);
		IntVar[][] matrizCopia = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);

		//Se asigna a la variable matrizCopia una copia de la matriz resultado del modelo inicial anterior
		//con su respectiva solucion, esta copia se realiza con el objetivo de utilizar en este metodo la
		//matriz solucion anterior
		matrizCopia=copiarMatriz(solucionAnterior, matrizCarreras,salas, toolSoftware);

		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {


				//Si en la matriz solucion del modelo anterior hay 1, es decir, si instale
				if (matrizCopia[i][j].getValue()==1) {

					//multiplica el valor 1 por el consumo de disco duro de cada software que se va a instalar
					//se asigna ese valor a la matriz de pesos
					matrizPesos[i][j]=matrizCopia[i][j].mul(toolSoftware.get(j).getDiscoDuro()).intVar();

				}
			}
		}

		//Crea la variable matrizResultado de tipo matriz de Intvar y posibles valores 0 ó 1
		IntVar[][] matrizResultado = model.intVarMatrix("pesos", salas.size(), toolSoftware.size(), 0, 1);

		//Variable temporal que guarda el valor total de la sumatoria del peso en disco duro de las 
		//herramientas de software que se van instalar 
		int pesoPorSala=0;
		for (int i = 0; i < salas.size(); i++) {

			for (int j = 0; j < toolSoftware.size(); j++) {

				pesoPorSala+=matrizPesos[i][j].getValue();

			}

			for (int j = 0; j < toolSoftware.size(); j++) {

				//Condicion en la que se restringe que el peso total de disco de las herramientas de software 
				//no sea mayor a la capacidad de disco duro del computador de la sala en la que se estima 
				//instalar dichas herramientas de software
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

		//Establece un criterio de parada al buscar las posibles soluciones que satisfagan las restricciones
		//En este caso el criterio de parada es limitar las soluciones a una cantidad deseada por el usuario
		Criterion solcpt = new SolutionCounter(model, numSoluciones);

		//Crea una lista con el numero de soluciones pasadas por parametro encontradas y 
		//que satisfacen el modelo de restricciones
		List<Solution> list=model.getSolver().findAllSolutions(solcpt);

		//Se inicializa la lista global de soluciones del modelo de restricciones de disco
		listSolutionDisco= new ArrayList<>();
		listSolutionDisco.addAll(list);

		//Asigna a la matriz global de IntVar la matriz temporal resultado del modelo solucionado aplicando 
		//las restricciones de disco
		matrizSolucionesDisco= matrizResultado;

	}

	//Metodo encargado de generar el formato del reporte que el usuario puede exportar como txt de
	//las soluciones encontradas
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

			if (salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")) {
				pesoPorSala+=SIS_OP_DISCO_MAC;
			}else {
				pesoPorSala+=SIS_OP_DISCO_WINDOWS;
			}


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

	//Metodo encargado de generar el formato del reporte que el usuario puede visualizar en la pantalla 
	//cuando selecciona una solucion encontrada y una sala especifica 
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

			if (salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")) {
				pesoPorSala+=SIS_OP_DISCO_MAC;
			}else {
				pesoPorSala+=SIS_OP_DISCO_WINDOWS;
			}

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

					}
					else {

						if (!toolSoftwareBasico.get(k).getSistemaOperativo().toUpperCase().contains("MAC")
								&& !salas.get(i).getComputadores().getSistemaOperativo().toUpperCase().contains("MAC")
								&& softBasico == true) {

							reporteDistribucionVista+=toolSoftwareBasico.get(k).getNombre()+"\n";

							arreglo.add(toolSoftwareBasico.get(k).getNombre());

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

	//Metodo que permite copiar una matriz resultado con una determinada solucion al modelo preliminar
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