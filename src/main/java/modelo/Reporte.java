package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Reporte {

	public Reporte() {

	}

	public void exportarReporteTxt(String ruta, String textoReporte) throws FileNotFoundException {

		File archivo= new File(ruta);

		PrintWriter escritor= new PrintWriter(archivo);
		escritor.println(textoReporte);

		escritor.close();

	}

	public void exportarReportePDF(String ruta, String textoReporte) throws FileNotFoundException {

		File archivo= new File(ruta);

		PrintWriter escritor= new PrintWriter(archivo);

		escritor.println(textoReporte);

		escritor.close();

	}
}
