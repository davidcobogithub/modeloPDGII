package interfaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.LectorDeArchivos;
import modelo.Reporte;
import modelo.Software;
import modelo.SolverProject;

public class VentanaPpal extends Application {

	private TextField txtFldLimSoluciones;
	private ComboBox<String> comboBoxFldPorcDisco;
	private CheckBox chkRestSelecTodas;
	private CheckBox chkRestSoftwareDepartamento;
	private CheckBox chkRestSostwareSistemaOperativo;
	private CheckBox chkRestSoftwareRAM;
	private CheckBox chkRestSoftwareDiscoDuro;
	private CheckBox chkRestSoftwareBasico;
	private CheckBox chkRestSoftwareNumeroLicencias;
	private CheckBox chkRestSoftwareSalaNombre;
	private Button btnGenerar;
	private Button btnLimpiar;
	private Button btnImportar;
	private Button btnExportTxt;
	private TextArea txtAreaVista;
	private ImageView imageView;
	private Text tiempoDeCarga;
	private Text cargando;
	private ComboBox<String> comboSala;
	private TextArea  consultaSala;
	private ComboBox<String> comboSoft;
	private TextArea  consultaSoft;
	private VBox vBoxDistribucion;
	private static SolverProject solver;
	private static LectorDeArchivos lector;
	private static Reporte reportes;
	private static int solucionSeleccionada;
	private static String numeroSolucion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		lector= new LectorDeArchivos();
		solver = new SolverProject();
		reportes=new Reporte();

		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		stage.setTitle("Distribución de Software Icesi"); 
		stage.setResizable(false);
		ClassLoader classLoader = getClass().getClassLoader();
		Image image = new Image(classLoader.getResourceAsStream("icono.jpeg")); 
		stage.getIcons().add(image);
		Scene scene = new Scene(new Group(), 1200, 660);
		scene.setFill(Color.GHOSTWHITE);
		
		
		scene.getStylesheets().add("styles.css");

		Label lblTitulo = new Label("Distribuci\u00F3n de Software Icesi");
		lblTitulo.getStyleClass().add("titulo");
		lblTitulo.setLayoutX(520);
		lblTitulo.setLayoutY(10);
		lblTitulo.setPrefWidth(397);
		lblTitulo.setPrefHeight(15);

		TitledPane panelParametros = new TitledPane();
		panelParametros.setCollapsible(false);
		panelParametros.setText("Cambiar Par\u00e1metros");
		panelParametros.setLayoutX(10);
		panelParametros.setLayoutY(55);
		panelParametros.setPrefWidth(355);
		panelParametros.setPrefHeight(170);

		VBox vBox = new VBox(20);
		HBox hBox = new HBox();

		btnImportar  = new Button("Importar");
		btnImportar.setPrefWidth(90);
		btnImportar.setPrefHeight(23);
		hBox.setMargin(btnImportar, new Insets(10, 0, 0, 135));
		Tooltip tooltipBtnImportar = new Tooltip();
		tooltipBtnImportar.setText("Permite cargar un archivo con \n"
				+ "la información de herramientas de software");
		btnImportar.setTooltip(tooltipBtnImportar);
		hBox.getChildren().add(btnImportar);

		txtFldLimSoluciones = new TextField();
		Tooltip tooltipTxtLimSolu = new Tooltip();
		tooltipTxtLimSolu.setText("Aquí puedes ingresar la cantidad de \n"
				+ "soluciones que deseas obtener como resultado");
		txtFldLimSoluciones.setTooltip(tooltipTxtLimSolu);

		comboBoxFldPorcDisco = new ComboBox<>();
		comboBoxFldPorcDisco.setPromptText("Seleccione Porcentaje");
		comboBoxFldPorcDisco.getItems().addAll("Seleccione Porcentaje","10%","20%","30%","40%","50%",
				"60%","70%","80%","90%","100%");
		Tooltip tooltipBoxPorcDisc = new Tooltip();
		tooltipBoxPorcDisc.setText("Aquí puedes ingresar el porcentaje límite de \n"
				+ "instalación de las herramientas de software en las salas");
		comboBoxFldPorcDisco.setTooltip(tooltipBoxPorcDisc);
		comboBoxFldPorcDisco.setValue("Seleccione Porcentaje");

		GridPane grid = new GridPane();
		grid.setLayoutY(35);
		grid.setVgap(4);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(new Label("N° de Soluciones: "), 0, 0);
		grid.add(txtFldLimSoluciones, 1, 0);
		grid.add(new Label("% Máximo en Disco: "), 0, 1);
		grid.add(comboBoxFldPorcDisco, 1, 1);

		vBox.getChildren().add(hBox);
		vBox.getChildren().add(grid);

		panelParametros.setContent(vBox);

		TitledPane panelRestricciones = new TitledPane();
		panelRestricciones.setCollapsible(false);
		panelRestricciones.setText("Selecci\u00F3n de Restricciones");
		panelRestricciones.setLayoutX(10);
		panelRestricciones.setLayoutY(230);
		panelRestricciones.setPrefWidth(355);
		panelRestricciones.setPrefHeight(300);

		VBox vBoxRestricciones = new VBox(5);
		chkRestSelecTodas =  new CheckBox("Seleccionar Todas");
		vBoxRestricciones.getChildren().add(chkRestSelecTodas);

		chkRestSoftwareDepartamento =  new CheckBox("Software y Salas de un Departamento");
		Tooltip tooltipSoftDepar = new Tooltip();
		tooltipSoftDepar.setText("Consiste en instalar las herramientas de software en salas \n"
				+ "donde ambos estén asociados a un mismo departamento");
		chkRestSoftwareDepartamento.setTooltip(tooltipSoftDepar);
		vBoxRestricciones.getChildren().add(chkRestSoftwareDepartamento);

		chkRestSostwareSistemaOperativo =  new CheckBox("Software y Salas con mismo Sistema Operativo");
		Tooltip tooltipSoftSisOpe = new Tooltip();
		tooltipSoftSisOpe.setText("Consiste en instalar las herramientas de software de acuerdo al \n"
				+ "sistema operativo en el que se despliega y el sistema operativo de la sala, \n"
				+ "ambos deben ser iguales");
		chkRestSostwareSistemaOperativo.setTooltip(tooltipSoftSisOpe);
		vBoxRestricciones.getChildren().add(chkRestSostwareSistemaOperativo);

		chkRestSoftwareSalaNombre =  new CheckBox("Software Instalado en Salas Específicas");
		Tooltip tooltipSoftSalaNom = new Tooltip();
		tooltipSoftSalaNom.setText("Consiste en instalar las herramientas de software en salas únicas y asignadas \n"
				+ "de manera explícita, por ejemplo: Rosetta Stone en la sala 201C");
		chkRestSoftwareSalaNombre.setTooltip(tooltipSoftSalaNom);
		vBoxRestricciones.getChildren().add(chkRestSoftwareSalaNombre);

		chkRestSoftwareDiscoDuro =  new CheckBox("Capacidad de Disco Duro");
		Tooltip tooltipSoftDisc = new Tooltip();
		tooltipSoftDisc.setText("Consiste en instalar las herramientas de software que en conjunto ocupen \n"
				+ "el porcentaje de disco duro ingresado en el campo PORCENTAJE DE DISCO");
		chkRestSoftwareDiscoDuro.setTooltip(tooltipSoftDisc);
		vBoxRestricciones.getChildren().add(chkRestSoftwareDiscoDuro);

		chkRestSoftwareRAM=  new CheckBox("Capacidad de Memoria RAM");
		Tooltip tooltipSoftRam = new Tooltip();
		tooltipSoftRam.setText("Consiste en instalar las herramientas de software teniendo en cuenta la memoria RAM \n"
				+ "requerida y la memoria que tienen los computadores de la sala. La memoria que consume el \n"
				+ "software debe ser menor o igual a la memoria del computador a instalar");
		chkRestSoftwareRAM.setTooltip(tooltipSoftRam);
		vBoxRestricciones.getChildren().add(chkRestSoftwareRAM);

		chkRestSoftwareBasico =  new CheckBox("Instalación de Software Básico");
		Tooltip tooltipSoftBasico = new Tooltip();
		tooltipSoftBasico.setText("Se instalará en todas las salas la imagen de herramientas \n"
				+ "de software que se consideran como de uso básico");
		chkRestSoftwareBasico.setTooltip(tooltipSoftBasico);
		vBoxRestricciones.getChildren().add(chkRestSoftwareBasico);

		chkRestSoftwareNumeroLicencias =  new CheckBox("Cantidad de Licencias de Software");
		Tooltip tooltipSoftLicencias = new Tooltip();
		tooltipSoftLicencias.setText("Consiste en instalar las herramientas de software de acuerdo a la cantidad \n"
				+ "de licencias (Si el software es licenciado) de tal manera, que la suma de la \n "
				+ "capacidad de cada sala sea igual a la cantidad de las licencias existentes");
		chkRestSoftwareNumeroLicencias.setTooltip(tooltipSoftLicencias);
		vBoxRestricciones.getChildren().add(chkRestSoftwareNumeroLicencias);

		HBox hBoxBotonesRestricciones = new HBox(15);

		btnGenerar = new Button("Generar Distribuci\u00F3n");
		btnGenerar.setPrefWidth(155);
		Tooltip tooltipBtnGenerar = new Tooltip();
		tooltipBtnGenerar.setText("Permite generar la distribución de software de acuerdo \n"
				+ " a las entradas por parámetro y las restricciones predefinidas");
		btnGenerar.setTooltip(tooltipBtnGenerar);
		hBoxBotonesRestricciones.getChildren().add(btnGenerar);

		btnLimpiar = new Button("Limpiar");
		btnLimpiar.setPrefWidth(155);
		Tooltip tooltipBtnLimpiar = new Tooltip();
		tooltipBtnLimpiar.setText("Permite restaurar los valores de entradas por parámetro, \n"
				+ "las restricciones y la vista de distribución");
		btnLimpiar.setTooltip(tooltipBtnLimpiar);
		hBoxBotonesRestricciones.getChildren().add(btnLimpiar);	

		hBoxBotonesRestricciones.setMargin(btnGenerar, new Insets(20, 0, 0, 0));
		hBoxBotonesRestricciones.setMargin(btnLimpiar, new Insets(20, 0, 0, 0));

		vBoxRestricciones.getChildren().add(hBoxBotonesRestricciones);
		panelRestricciones.setContent(vBoxRestricciones);

		TitledPane panelExportacion = new TitledPane();
		panelExportacion.setCollapsible(false);
		panelExportacion.setText("Exportar");
		panelExportacion.setLayoutX(10);
		panelExportacion.setLayoutY(535);
		panelExportacion.setPrefWidth(355);
		panelExportacion.setPrefHeight(90);

		HBox hBoxExportar = new HBox(25);

		btnExportTxt = new Button("TXT");
		btnExportTxt.setPrefWidth(155);
		Tooltip tooltipBtnExpTxt = new Tooltip();
		tooltipBtnExpTxt.setText("Permite guardar el reporte de distribución \n"
				+ "en un archivo con extensión .txt");
		btnExportTxt.setTooltip(tooltipBtnExpTxt);
		hBoxExportar.getChildren().add(btnExportTxt);
		hBoxExportar.setMargin(btnExportTxt, new Insets(10, 0, 0, 80));

		panelExportacion.setContent(hBoxExportar);

		TitledPane panelContenedor = new TitledPane();
		panelContenedor.setCollapsible(false);
		panelContenedor.setLayoutX(370);
		panelContenedor.setLayoutY(55);
		panelContenedor.setPrefWidth(810);
		panelContenedor.setPrefHeight(570);

		VBox vBoxContent = new VBox();
		vBoxContent.setLayoutX(0);
		vBoxContent.setLayoutY(0);

		TabPane tabPane = new TabPane();

		Tab tab1 = new Tab();
		tab1.setText("Distribución");
		tab1.setClosable(false);

		GridPane gridPane = new GridPane();    
		gridPane.setVgap(5); 
		gridPane.setHgap(5);       

		txtAreaVista = new TextArea();
		txtAreaVista.setPrefHeight(400);
		txtAreaVista.setPrefWidth(550);
		txtAreaVista.setEditable(false);

		vBoxDistribucion = new VBox();
		ScrollPane sp = new ScrollPane();
		sp.setPrefWidth(250);

		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setContent(vBoxDistribucion);

		gridPane.add(sp, 0, 0); 
		gridPane.add(txtAreaVista, 1, 0); 

		tab1.setContent(gridPane);

		Tab tab2 = new Tab();
		tab2.setText("Consultas");
		tab2.setClosable(false);

		VBox vBoxConsultas = new VBox();

		Label conSal=new Label("Consultar Salas");
		conSal.setPrefWidth(800);
		conSal.setPrefHeight(30);
		vBoxConsultas.getChildren().add(conSal);

		vBoxConsultas.setMargin(conSal, new Insets(10, 0, 0, 0));

		ArrayList<String> nombreSal=new ArrayList<>();
		comboSala=new ComboBox<String>();
		nombreSal=solver.getNombreSalas();
		comboSala.setPrefWidth(810);
		comboSala.setPrefHeight(50);
		comboSala.setPromptText("Seleccione una Sala");
		comboSala.getItems().add("Seleccione una Sala");

		for (int i = 0; i < nombreSal.size(); i++) {
			comboSala.getItems().add(nombreSal.get(i).toString());
		}
		Tooltip tooltipComSalas= new Tooltip();
		tooltipComSalas.setText("Permite consultar información de cada sala de cómputo seleccionando su nombre");
		comboSala.setTooltip(tooltipComSalas);
		vBoxConsultas.getChildren().add(comboSala);

		consultaSala=new TextArea();
		consultaSala.setEditable(false);
		consultaSala.setPrefWidth(810);
		consultaSala.setPrefHeight(80);

		vBoxConsultas.getChildren().add(consultaSala);

		Label conSof=new Label("Consultar Software");
		conSof.setPrefWidth(810);
		conSof.setPrefHeight(30);
		vBoxConsultas.getChildren().add(conSof);

		vBoxConsultas.setMargin(conSof, new Insets(10, 0, 0, 0));

		comboSoft=new ComboBox<String>();
		comboSoft.setPrefWidth(810);
		comboSoft.setPrefHeight(50);
		comboSoft.setPromptText("Seleccione un Software");
		Tooltip tooltipComSoft = new Tooltip();
		tooltipComSoft.setText("Permite consultar información de cada herramienta de software seleccionando su nombre");
		comboSoft.setTooltip(tooltipComSoft);
		vBoxConsultas.getChildren().add(comboSoft);

		consultaSoft=new TextArea();
		consultaSoft.setEditable(false);
		consultaSoft.setPrefWidth(810);
		consultaSoft.setPrefHeight(190);

		vBoxConsultas.getChildren().add(consultaSoft);

		tab2.setContent(vBoxConsultas);

		tabPane.getTabs().addAll(tab1, tab2);

		tiempoDeCarga=new Text("");
		cargando=new Text("");
		cargando.getStyleClass().add("cargando");

		ClassLoader classLoader1 = getClass().getClassLoader();
		Image cargar = new Image(classLoader1.getResourceAsStream("Cargando2.gif"));
		imageView = new ImageView(cargar); 
		imageView.setFitWidth(70); 
		imageView.setFitHeight(70); 
		imageView.setVisible(false);

		vBoxContent.getChildren().add(tabPane);
		vBoxContent.getChildren().add(tiempoDeCarga);
		vBoxContent.getChildren().add(imageView);
		vBoxContent.getChildren().add(cargando);
		vBoxContent.setMargin(tiempoDeCarga, new Insets(15, 0, 0, 10));
		vBoxContent.setMargin(cargando, new Insets(0, 0, 0, 320));
		vBoxContent.setMargin(imageView, new Insets(0, 0, 0, 330));
		panelContenedor.setContent(vBoxContent);

		Group root = (Group)scene.getRoot();
		root.getChildren().add(lblTitulo);
		root.getChildren().add(panelParametros);
		root.getChildren().add(panelRestricciones);
		root.getChildren().add(panelExportacion);
		root.getChildren().add(panelContenedor);

		stage.setScene(scene);
		stage.show();


		adicionarEventos(stage);

		inhabilitarComponentes();

	}

	public void habilitarComponentes() {

		txtFldLimSoluciones.setEditable(true);
		comboBoxFldPorcDisco.setDisable(false);
		chkRestSelecTodas.setDisable(false);
		chkRestSoftwareDepartamento.setDisable(false);
		chkRestSostwareSistemaOperativo.setDisable(false);
		chkRestSoftwareRAM.setDisable(false);
		chkRestSoftwareDiscoDuro.setDisable(false);
		chkRestSoftwareBasico.setDisable(false);
		chkRestSoftwareNumeroLicencias.setDisable(false);
		chkRestSoftwareSalaNombre.setDisable(false);
		btnGenerar.setDisable(false);
		btnLimpiar.setDisable(false);
		btnExportTxt.setDisable(false);
	}

	public void inhabilitarComponentes() {

		txtFldLimSoluciones.setEditable(false);
		comboBoxFldPorcDisco.setDisable(true);
		chkRestSelecTodas.setDisable(true);
		chkRestSoftwareDepartamento.setDisable(true);
		chkRestSostwareSistemaOperativo.setDisable(true);
		chkRestSoftwareRAM.setDisable(true);
		chkRestSoftwareDiscoDuro.setDisable(true);
		chkRestSoftwareBasico.setDisable(true);
		chkRestSoftwareNumeroLicencias.setDisable(true);
		chkRestSoftwareSalaNombre.setDisable(true);
		btnGenerar.setDisable(true);
		btnLimpiar.setDisable(true);
		btnExportTxt.setDisable(true);
		comboSoft.setDisable(true);
	}

	public void adicionarEventos(Stage stage) {

		btnLimpiar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				txtFldLimSoluciones.setText("");
				comboBoxFldPorcDisco.setValue("Seleccione Porcentaje");

				txtAreaVista.setText("");
				chkRestSelecTodas.setSelected(false);
				chkRestSoftwareDepartamento.setSelected(false);
				chkRestSostwareSistemaOperativo.setSelected(false);
				chkRestSoftwareRAM.setSelected(false);
				chkRestSoftwareDiscoDuro.setSelected(false);
				chkRestSoftwareBasico.setSelected(false);
				chkRestSoftwareNumeroLicencias.setSelected(false);
				chkRestSoftwareSalaNombre.setSelected(false);

			}
		});


		comboSala.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String item_seleccionado = comboSala.getValue().toString();
				if (item_seleccionado.equals("Seleccione una Sala")) {
					consultaSala.setText("Por favor seleccione una sala");
				}else {
					for (int i = 0; i < lector.getSalas().size(); i++) {
						if(item_seleccionado.equals(lector.getSalas().get(i).getNombre())) {
							consultaSala.setText("Nombre de la Sala: "+ lector.getSalas().get(i).getNombre()+"\n"+
									"Departamento de la Sala: "+ lector.getSalas().get(i).getTipo()+"\n"+
									"Nº de computadores de la Sala: "+ lector.getSalas().get(i).getCapacidad()+"\n"+
									"Sistema Operativo de Computadores: "+ lector.getSalas().get(i).getComputadores().getSistemaOperativo()+"\n"+
									"Disco Duro de Computadores: "+ (lector.getSalas().get(i).getComputadores().getDiscoDuro())/3+" MB = "+((double)lector.getSalas().get(i).getComputadores().getDiscoDuro()/1024)/3+" GB"+"\n"+
									"Memoria RAM de Computadores: "+ (lector.getSalas().get(i).getComputadores().getMemoriaRAM())/3+" MB = "+ ((double)lector.getSalas().get(i).getComputadores().getMemoriaRAM()/1024)/3+" GB"+"\n");
						}
					}
				}
			}
		});

		comboBoxFldPorcDisco.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String item_seleccionado = comboBoxFldPorcDisco.getValue().toString(); 
				if (!item_seleccionado.equals("Seleccione Porcentaje")) {
					chkRestSoftwareDiscoDuro.setSelected(true);
				}
			}
		});

		btnImportar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Abrir Archivo");
				fileChooser.setInitialDirectory(new File("./")); 
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
				File escogido = fileChooser.showOpenDialog(stage);

				if (escogido != null) {

					try {

						lector.leerCSVSoftware(escogido.getAbsolutePath());
						txtAreaVista.setText(" ");
						tiempoDeCarga.setText(" ");
						Alert alert = new Alert(AlertType.INFORMATION, 
								"Se ha importado correctamente el archivo");
						alert.show();
						habilitarComponentes();
						chkRestSoftwareDepartamento.setSelected(true);

					} 
					catch (IOException e1) {
						// TODO Auto-generated catch block
						Alert alert = new Alert(AlertType.ERROR, 
								"No se ha importado correctamente el archivo");
						alert.show();

					}catch (Exception e1) {
						// TODO Auto-generated catch block
						Alert alert = new Alert(AlertType.ERROR, 
								"No se ha importado correctamente el archivo");
						alert.show();
					}

				}

			}
		});

		btnGenerar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					if (!txtFldLimSoluciones.getText().equals("")){
						int x=Integer.parseInt(txtFldLimSoluciones.getText());
					}
					txtAreaVista.setText(" ");
					tiempoDeCarga.setText(" ");
					imageView.setVisible(true);
					cargando.setText("Cargando...");

					new Thread() {

						@Override
						public void run() {

							long startTime = System.currentTimeMillis();

							if (!txtFldLimSoluciones.getText().equals("") 
									&& !comboBoxFldPorcDisco.getValue().toString().equals("Seleccione Porcentaje") 
									) {

								int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
								int porc=Integer.parseInt(comboBoxFldPorcDisco.getValue().toString().split("%")[0]);

								solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
										chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
										chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
										chkRestSoftwareSalaNombre.isSelected(), porc);
								visualizarResultados(porc);
								solver.imprimirMatrizConSolucionExportar(solver.getMatrizSolucionesIniciales(), 
										solver.getListSolution().get(solucionSeleccionada), chkRestSoftwareBasico.isSelected(), lector.getSalas(), 
										lector.getToolSoftware(), porc, "Mejor Solución Estimada");

							}
							else if (!txtFldLimSoluciones.getText().equals("") &&
									comboBoxFldPorcDisco.getValue().toString().equals("Seleccione Porcentaje")) {

								int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
								int porc=70;

								solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
										chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
										chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
										chkRestSoftwareSalaNombre.isSelected(), porc);

								visualizarResultados(porc);
								solver.imprimirMatrizConSolucionExportar(solver.getMatrizSolucionesIniciales(), 
										solver.getListSolution().get(solucionSeleccionada), chkRestSoftwareBasico.isSelected(), lector.getSalas(), 
										lector.getToolSoftware(), porc, "Mejor Solución Estimada");
							}
							else if (txtFldLimSoluciones.getText().equals("")  &&
									!comboBoxFldPorcDisco.getValue().toString().equals("Seleccione Porcentaje")) {

								int numSol=10;
								int porc=Integer.parseInt(comboBoxFldPorcDisco.getValue().toString().split("%")[0]);

								solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
										chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
										chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
										chkRestSoftwareSalaNombre.isSelected(), porc);
						
								visualizarResultados(porc);
								solver.imprimirMatrizConSolucionExportar(solver.getMatrizSolucionesIniciales(), 
										solver.getListSolution().get(solucionSeleccionada), chkRestSoftwareBasico.isSelected(), lector.getSalas(), 
										lector.getToolSoftware(), porc, "Mejor Solución Estimada");
							}else {
								int numSol=10;
								int porc=70;
								solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
										chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
										chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(),
										chkRestSoftwareSalaNombre.isSelected(), porc);
								visualizarResultados(porc);
								solver.imprimirMatrizConSolucionExportar(solver.getMatrizSolucionesIniciales(), 
										solver.getListSolution().get(solucionSeleccionada), chkRestSoftwareBasico.isSelected(), lector.getSalas(), 
										lector.getToolSoftware(), porc, "Mejor Solución Estimada");

							}
								
							try {
								Thread.sleep(100);

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							imageView.setVisible(false);
							cargando.setText("");
							long endTime = System.currentTimeMillis() - startTime;

							int tiempoMinutos = (int) (endTime/60000);
							int restoMinutos = (int) (endTime%60000);
							int tiempoSegundos = (int) (restoMinutos/1000);
							int restoSegundos = (int) (restoMinutos%1000);

							if (tiempoSegundos != 0) {

								tiempoDeCarga.setText("Tiempo de Carga: 0"+tiempoMinutos+":"+tiempoSegundos);							
							}else {

								tiempoDeCarga.setText("Tiempo de Carga: 0"+tiempoMinutos+":0"+restoSegundos);
							}

							comboSoft.setDisable(false);

							if (comboSoft.getItems().size() > 1) {

								comboSoft.getItems().removeAll(comboSoft.getItems());

							}

							Collections.sort(solver.getNombreSoftware());

							comboSoft.getItems().add("Seleccionar un Software");
							for (int i = 0; i < solver.getNombreSoftware().size(); i++) {
								comboSoft.getItems().add(solver.getNombreSoftware().get(i).toString());

							}

							solver.getNombreSoftware().clear();

						}
					}.start();


				} catch (NumberFormatException e) {

					txtFldLimSoluciones.setText("");
					Alert alert = new Alert(AlertType.ERROR, 
							"Debe ingresar un valor numérico entero");
					alert.show();
				}
			}
		});

		comboSoft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {

					String item_seleccionado = comboSoft.getValue().toString();
					if (item_seleccionado.equals("Seleccionar un Software")) {
						consultaSoft.setText("Por favor seleccione una herramienta de software");
					}else {
						ArrayList<String> materias=new ArrayList<>();
						ArrayList<String> salas=new ArrayList<>();

						ArrayList<Software> listaSoftware=new ArrayList<>();
						listaSoftware=lector.getToolSoftware();
						listaSoftware.addAll(SolverProject.getToolSoftwareBasico());
						String nombre="";
						String dpto="";
						String sisOpe="";
						int licencias=0;
						int disco=0;
						int ram=0;
						String report="";
						for (int i = 0; i < lector.getSalas().size(); i++) {
							for (int j = 0; j < listaSoftware.size(); j++) {

								if(item_seleccionado.equals(listaSoftware.get(j).getNombre())) {

									nombre=listaSoftware.get(j).getNombre();
									dpto=listaSoftware.get(j).getTipoDepartamento();
									sisOpe=listaSoftware.get(j).getSistemaOperativo();
									disco=listaSoftware.get(j).getDiscoDuro();
									ram=listaSoftware.get(j).getMemoriaRAM();
									licencias=listaSoftware.get(j).getCantLicencias();
									if (!materias.contains(listaSoftware.get(j).getNombreMateria())){
										materias.add(listaSoftware.get(j).getNombreMateria());
									}

									if (!salas.contains(listaSoftware.get(j).getNombreSala()) && 
											!listaSoftware.get(j).getNombreSala().equals("") &&
											!listaSoftware.get(j).getNombreSala().equals(" ") &&
											!listaSoftware.get(j).getNombreSala().contains("L") &&
											!listaSoftware.get(j).getNombreSala().contains("B")
											&& !listaSoftware.get(j).getNombreSala().contains("/")){
										salas.add(listaSoftware.get(j).getNombreSala());
									}
								}
							}
						}

						report+="Nombre del software: "+ nombre+"\n"+
								"Departamento asociado: "+ dpto+"\n"+
								"Cantidad de Licencias: "+ licencias+"\n"+"\n"+
								"Disco Duro: "+ disco+" MB = "+((double)disco/1024)+ " GB"+"\n"+
								"Memoria RAM: "+ ram+" MB = "+((double)ram/1024)+ " GB"+"\n"+
								"Sistema Operativo: "+ sisOpe+"\n"+"\n"+
								"Materia que la utiliza:"+"\n";

						int cont=1;
						for (int i = 0; i < materias.size(); i++) {
							report+=" "+cont+". "+materias.get(i)+"\n";
							cont++;
						}
						cont=1;
						report+="\n"+"Salas en que se encuentra:"+"\n";

						for (int j = 0; j < salas.size(); j++) {
							report+=" "+cont+". "+salas.get(j)+"\n";
							cont++;
						}
						consultaSoft.setText(report);

					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});

		btnExportTxt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				String ruta = "";

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Abrir Archivo");
				fileChooser.setInitialDirectory(new File("./")); 
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));
				File escogido = fileChooser.showSaveDialog(stage);

				if (escogido != null) {
					try {

						ruta = escogido.getAbsolutePath();

						reportes.exportarReporteTxt(ruta, solver.getReporteDistribucion());

						Alert alert = new Alert(AlertType.INFORMATION, 
								"Se ha exportado correctamente el archivo en la ruta " +ruta);
						alert.show();

					} 
					catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						Alert alert = new Alert(AlertType.ERROR, 
								"Archivo no encontrado");
						alert.show();
						e1.printStackTrace();

					} 
					catch (Exception e1) {
						// TODO Auto-generated catch block
						Alert alert = new Alert(AlertType.ERROR, 
								"No se ha exportado correctamente el archivo");
						alert.show();
						e1.printStackTrace();
					}
				}

			}
		});

		chkRestSelecTodas.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				if (chkRestSelecTodas.isSelected()) {
					chkRestSoftwareDepartamento.setSelected(true);
					chkRestSostwareSistemaOperativo.setSelected(true);
					chkRestSoftwareRAM.setSelected(true);
					chkRestSoftwareDiscoDuro.setSelected(true);
					chkRestSoftwareBasico.setSelected(true);
					chkRestSoftwareNumeroLicencias.setSelected(true);
					chkRestSoftwareSalaNombre.setSelected(true);
				}
				else {

					chkRestSoftwareDepartamento.setSelected(false);
					chkRestSostwareSistemaOperativo.setSelected(false);
					chkRestSoftwareRAM.setSelected(false);
					chkRestSoftwareDiscoDuro.setSelected(false);
					chkRestSoftwareBasico.setSelected(false);
					chkRestSoftwareNumeroLicencias.setSelected(false);
					chkRestSoftwareSalaNombre.setSelected(false);
				}
			}
		});
	}

	public void visualizarResultados(int porc) {

		solucionSeleccionada=0;
		numeroSolucion="";

		if (chkRestSoftwareDiscoDuro.isSelected() != true) {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					vBoxDistribucion.getChildren().clear();
					TitledPane[] panelDistribucion = new TitledPane[solver.getListSolution().size()];
					Accordion accordion = new Accordion();  

					for (int i = 0; i < solver.getListSolution().size(); i++) {

						panelDistribucion[i] = new TitledPane();
						panelDistribucion[i].setText("Solución "+(i+1));
						panelDistribucion[i].setPrefWidth(220);
						panelDistribucion[i].setExpanded(false);

						ListView<String> listView=new ListView<>();
						listView.setPrefHeight(180);

						for (int j = 0; j < solver.getNombreSalas().size(); j++) {
							listView.getItems().add(solver.getNombreSalas().get(j).toString());
						}

						panelDistribucion[i].setContent(listView);

						listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								// TODO Auto-generated method stub

								txtAreaVista.setText("");
																
									solver.imprimirMatrizConSolucionVista(solver.getMatrizSolucionesIniciales(), 
											solver.getListSolution().get(solucionSeleccionada), chkRestSoftwareBasico.isSelected(), lector.getSalas(), 
											lector.getToolSoftware(), newValue, porc);
																
								txtAreaVista.setText(solver.getReporteDistribucionVista()+numeroSolucion+"\n"+"\n"+tiempoDeCarga.getText());
							}
						});

					}

					accordion.getPanes().addAll(panelDistribucion);    
					vBoxDistribucion.getChildren().add(accordion);

					accordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
						public void changed(ObservableValue<? extends TitledPane> ov,
								TitledPane old_val, TitledPane new_val) {

							if (new_val != null) {

								for (int i = 0; i < panelDistribucion.length; i++) {							
									
									if (panelDistribucion[i].getText().equals(new_val.getText())) {

										solucionSeleccionada=i;	
										numeroSolucion=new_val.getText();
							
									}

								}
							}
						}
					});

				}
			});
		}
		else {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					vBoxDistribucion.getChildren().clear();
					TitledPane[] panelDistribucionDisco = new TitledPane[solver.getListSolutionDisco().size()];
					Accordion accordionDisco = new Accordion();  

					for (int i = 0; i < solver.getListSolutionDisco().size(); i++) {

						panelDistribucionDisco[i] = new TitledPane();
						panelDistribucionDisco[i].setText("Solución "+(i+1));
						panelDistribucionDisco[i].setPrefWidth(220);
						panelDistribucionDisco[i].setExpanded(false);

						ListView<String> listViewSalas=new ListView<>();
						listViewSalas.setPrefHeight(180);

						for (int j = 0; j < solver.getNombreSalas().size(); j++) {
							listViewSalas.getItems().add(solver.getNombreSalas().get(j).toString());
						}

						panelDistribucionDisco[i].setContent(listViewSalas);

						listViewSalas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								// TODO Auto-generated method stub

								txtAreaVista.setText("");

								solver.imprimirMatrizConSolucionVista(solver.getMatrizSolucionesDisco(), 
										solver.getListSolutionDisco().get(solucionSeleccionada), chkRestSoftwareBasico.isSelected(), lector.getSalas(), 
										lector.getToolSoftware(), newValue, porc);

								txtAreaVista.setText(solver.getReporteDistribucionVista()+numeroSolucion+"\n"+"\n"+tiempoDeCarga.getText());
							}
						});

					}

					accordionDisco.getPanes().addAll(panelDistribucionDisco);    
					vBoxDistribucion.getChildren().add(accordionDisco);

					accordionDisco.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
						public void changed(ObservableValue<? extends TitledPane> ov,
								TitledPane old_val, TitledPane new_val) {

							if (new_val != null) {

								for (int i = 0; i < panelDistribucionDisco.length; i++) {

									if (panelDistribucionDisco[i].getText().equals(new_val.getText())) {

										solucionSeleccionada=i;	
										numeroSolucion=new_val.getText();


									}

								}
							}
						}
					});
				}
			});

		}

	}

}