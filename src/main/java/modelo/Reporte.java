package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Reporte {

	public Reporte() {

	}

//Metodo que permite escribir en un archivo txt el reporte de soluciones encontradas	
	public void exportarReporteTxt(String ruta, String textoReporte) throws FileNotFoundException {

		File archivo= new File(ruta);

		PrintWriter escritor= new PrintWriter(archivo);
		escritor.println(textoReporte);

		escritor.close();

	}

}
