package modelo;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class SolverProject {
	
	//Constantes del Proyecto 1

	public final static String TIPO_TICS="TICS";

	public final static String PROCESADOR="Core i3";

	public final static String ARQUITECTURA_64_BITS="64 bits";
	public final static String ARQUITECTURA_32_BITS="32 bits";

	public final static String SISTEMA_OPERATIVO="windows 10";

	public final static int MEMORIA_RAM=4;
	public final static int DISCO_DURO=500;
	public final static int NUCLEOS=4;

	public final static int PORCENTAJE_DISPONIBLE=70;


	private static ArrayList<Sala> salas;
	private static ArrayList<Software> toolSoftware;

	public static void main(String[] args) {

		modeloInicial();

	}

	public static void constructorModelo() {

		salas= new ArrayList<Sala>();
		toolSoftware= new ArrayList<Software>();

		Computador comp1= new Computador(PROCESADOR, 1,ARQUITECTURA_32_BITS, SISTEMA_OPERATIVO, MEMORIA_RAM, DISCO_DURO, NUCLEOS);
		Computador comp2= new Computador("Core i5", 2,ARQUITECTURA_64_BITS, "Windows XP", 2, 1000, 8);
		Computador comp3= new Computador("Core i7", 3,ARQUITECTURA_32_BITS, "Linux", 8, 250, 2);
		Computador comp4= new Computador("AMD A12", 4,ARQUITECTURA_64_BITS, "Mac OS", MEMORIA_RAM, 750, 2);
		Computador comp5= new Computador("AMD A12", 4,ARQUITECTURA_32_BITS, "Mac OS", MEMORIA_RAM, 800, 4);

		salas.add(new Sala("202C", TIPO_TICS, 25 , comp1));
		salas.add(new Sala("305C", "Industrial", 25 , comp2));
		salas.add(new Sala("Sala General", "General", 25 , comp3));
		salas.add(new Sala("310C", "Diseño", 25 , comp4));
		salas.add(new Sala("201C", "Idiomas", 25 , comp5));

		toolSoftware.add(new Software("Visual Paradigm", TIPO_TICS, "Intel Pentium 4", 1,ARQUITECTURA_32_BITS, SISTEMA_OPERATIVO, 2, 4, "14.0",false,0));
		toolSoftware.add(new Software("3ds Max Studio", "Industrial", "Intel o AMD multi-core",1, ARQUITECTURA_64_BITS, SISTEMA_OPERATIVO, 4, 6, "2017",false,0));
		toolSoftware.add(new Software("Adobe Experience Design", "Diseño", "Intel o AMD multi-core",2, ARQUITECTURA_64_BITS, SISTEMA_OPERATIVO, 4, 2, "0",false,0));
		toolSoftware.add(new Software("Office", "General", "Intel",1, ARQUITECTURA_32_BITS, SISTEMA_OPERATIVO, 4, 3, "2013",false,0));
		toolSoftware.add(new Software("Matlab", TIPO_TICS, " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, SISTEMA_OPERATIVO, 2, 2, "R2017a",true,1));
		toolSoftware.add(new Software("Rosseta", "Idiomas", " Intel o AMD x86-64", 1, ARQUITECTURA_64_BITS, SISTEMA_OPERATIVO, 2, 2, "R2017a",true,1));


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
			System.out.println("Soluciones encontradas: "+list.size());

			int nSol=1;
			for(Solution s:list){

				System.out.println("Solución: " + nSol);
				for (int i = 0; i < salas.size(); i++) {

					for (int j = 0; j < toolSoftware.size(); j++) {

						System.out.print(s.getIntVal(carrera[i][j]));

					}
					System.out.print(" "+salas.get(i).getNombre());
					System.out.println("");

				}

				for (int i = 0; i < toolSoftware.size(); i++) {

					System.out.print(toolSoftware.get(i).getNombre().charAt(0));
				}

				System.out.println("\n");
				nSol++;
			}

		}

		System.out.println("0 = No se puede instalar"+"\n"+ "1 = Si instalar"+"\n"+"2 = Tiene ejecutable");

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
