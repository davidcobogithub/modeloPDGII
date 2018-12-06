package interfaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Software;
import modelo.SolverProject;

public class VentanaPpal extends Application {

	private TextField txtFldLimSoluciones;
	private ComboBox<String> comboBoxFldPorcDisco;
	private CheckBox chkRestSelecTodas;
	private CheckBox chkRestSoftwareDepartamento;
	private CheckBox chkRestSostwareSistemaOperativo;
	private CheckBox chkRestSoftwareRAM;
	private CheckBox chkRestSoftwareDemandaCapacidad;
	private CheckBox chkRestSoftwareDiscoDuro;
	private CheckBox chkRestSoftwareBasico;
	private CheckBox chkRestSoftwareNumeroLicencias;
	private CheckBox chkRestSoftwareSalaNombre;
	private Button btnGenerar;
	private Button btnLimpiar;
	private Button btnImportar;
	private Button btnExportTxt;
	private Button btnExportPdf;
	private Button btnExportCsv;
	private TextArea txtAreaVista;
	private ImageView imageView;
	private Label tiempoDeCarga;
	private ComboBox<String> comboSala;
	private TextArea  consultaSala;
	private ComboBox<String> comboSoft;
	private TextArea  consultaSoft;

	private static SolverProject solver;
	private static VentanaPpal frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		solver = new SolverProject();
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		stage.setTitle("Distribución de Software Icesi"); 
		Scene scene = new Scene(new Group(), 1200, 660);
		scene.setFill(Color.GHOSTWHITE);
		File f = new File("css/styles.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		Label lblTitulo = new Label("Distribuci\u00F3n de Software Icesi");
		lblTitulo.getStyleClass().add("titulo");
		lblTitulo.setLayoutX(550);
		lblTitulo.setLayoutY(20);
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
		//btnImportar.getStyleClass().add("boton");
		btnImportar.setPrefWidth(90);
		btnImportar.setPrefHeight(23);
		hBox.setMargin(btnImportar, new Insets(10, 0, 0, 135));
		hBox.getChildren().add(btnImportar);

		txtFldLimSoluciones = new TextField();		
		comboBoxFldPorcDisco = new ComboBox<>();
		comboBoxFldPorcDisco.setPromptText("Seleccione Porcentaje");
		comboBoxFldPorcDisco.getItems().addAll("Seleccione Porcentaje","10%","20%","30%","40%","50%",
				"60%","70%","80%","90%","100%");

		GridPane grid = new GridPane();
		grid.setLayoutY(35);
		grid.setVgap(4);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(new Label("N° Soluciones Parciales: "), 0, 0);
		grid.add(txtFldLimSoluciones, 1, 0);
		grid.add(new Label("Porcentaje de Disco: "), 0, 1);
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
		vBoxRestricciones.getChildren().add(chkRestSoftwareDepartamento);

		chkRestSostwareSistemaOperativo =  new CheckBox("Software y Salas con mismo Sistema Operativo");
		vBoxRestricciones.getChildren().add(chkRestSostwareSistemaOperativo);

		chkRestSoftwareSalaNombre =  new CheckBox("Software Instalado en Salas Específicas");
		vBoxRestricciones.getChildren().add(chkRestSoftwareSalaNombre);

		chkRestSoftwareDiscoDuro =  new CheckBox("Capacidad de Disco Duro");
		vBoxRestricciones.getChildren().add(chkRestSoftwareDiscoDuro);

		chkRestSoftwareRAM=  new CheckBox("Capacidad de Memoria RAM");
		vBoxRestricciones.getChildren().add(chkRestSoftwareRAM);

		chkRestSoftwareDemandaCapacidad =  new CheckBox("Demanda y Capacidad de las Salas");
		vBoxRestricciones.getChildren().add(chkRestSoftwareDemandaCapacidad);

		chkRestSoftwareBasico =  new CheckBox("Instalación de Software Básico");
		vBoxRestricciones.getChildren().add(chkRestSoftwareBasico);

		chkRestSoftwareNumeroLicencias =  new CheckBox("Cantidad de Licencias de Software");
		vBoxRestricciones.getChildren().add(chkRestSoftwareNumeroLicencias);

		HBox hBoxBotonesRestricciones = new HBox(15);

		btnGenerar = new Button("Generar Distribuci\u00F3n");
		btnGenerar.setPrefWidth(155);
		hBoxBotonesRestricciones.getChildren().add(btnGenerar);

		btnLimpiar = new Button("Limpiar");
		btnLimpiar.setPrefWidth(155);
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
		btnExportTxt.setPrefWidth(90);
		hBoxExportar.getChildren().add(btnExportTxt);

		btnExportPdf = new Button("PDF");
		btnExportPdf.setPrefWidth(90);
		hBoxExportar.getChildren().add(btnExportPdf);

		btnExportCsv = new Button("CSV");
		btnExportCsv.setPrefWidth(90);
		hBoxExportar.getChildren().add(btnExportCsv);

		hBoxExportar.setMargin(btnExportTxt, new Insets(10, 0, 0, 0));
		hBoxExportar.setMargin(btnExportPdf, new Insets(10, 0, 0, 0));
		hBoxExportar.setMargin(btnExportCsv, new Insets(10, 0, 0, 0));

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

		VBox vBoxDistribucion = new VBox();
		txtAreaVista = new TextArea();
		//		txtAreaVista.setPrefWidth(700);
		txtAreaVista.setPrefHeight(450);
		txtAreaVista.setEditable(false);

		vBoxDistribucion.getChildren().add(txtAreaVista);

		tab1.setContent(vBoxDistribucion);

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
		//comboSoft.getItems().add("Seleccione un Software");

		vBoxConsultas.getChildren().add(comboSoft);

		consultaSoft=new TextArea();
		consultaSoft.setEditable(false);
		consultaSoft.setPrefWidth(810);
		consultaSoft.setPrefHeight(190);

		vBoxConsultas.getChildren().add(consultaSoft);

		tab2.setContent(vBoxConsultas);

		tabPane.getTabs().addAll(tab1, tab2);

		tiempoDeCarga=new Label("");

		FileInputStream inputstream = new FileInputStream("img/cargando.gif");
		Image image = new Image(inputstream);
		imageView = new ImageView(image); 
		imageView.setFitWidth(350); 
		imageView.setFitHeight(70); 
		imageView.setVisible(false);

		vBoxContent.getChildren().add(tabPane);
		vBoxContent.getChildren().add(tiempoDeCarga);
		vBoxContent.getChildren().add(imageView);
		vBoxContent.setMargin(imageView, new Insets(0, 0, 0, 230));
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
		chkRestSoftwareDemandaCapacidad.setDisable(false);
		chkRestSoftwareDiscoDuro.setDisable(false);
		chkRestSoftwareBasico.setDisable(false);
		chkRestSoftwareNumeroLicencias.setDisable(false);
		chkRestSoftwareSalaNombre.setDisable(false);
		btnGenerar.setDisable(false);
		btnLimpiar.setDisable(false);
		btnExportCsv.setDisable(false);
		btnExportPdf.setDisable(false);
		btnExportTxt.setDisable(false);
	}

	public void inhabilitarComponentes() {

		txtFldLimSoluciones.setEditable(false);
		comboBoxFldPorcDisco.setDisable(true);
		chkRestSelecTodas.setDisable(true);
		chkRestSoftwareDepartamento.setDisable(true);
		chkRestSostwareSistemaOperativo.setDisable(true);
		chkRestSoftwareRAM.setDisable(true);
		chkRestSoftwareDemandaCapacidad.setDisable(true);
		chkRestSoftwareDiscoDuro.setDisable(true);
		chkRestSoftwareBasico.setDisable(true);
		chkRestSoftwareNumeroLicencias.setDisable(true);
		chkRestSoftwareSalaNombre.setDisable(true);
		btnGenerar.setDisable(true);
		btnLimpiar.setDisable(true);
		btnExportCsv.setDisable(true);
		btnExportPdf.setDisable(true);
		btnExportTxt.setDisable(true);
		comboSoft.setDisable(true);
	}

	public void adicionarEventos(Stage stage) {

		//			solver.modeloInicial(10);


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
				chkRestSoftwareDemandaCapacidad.setSelected(false);
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
					for (int i = 0; i < solver.getSalas().size(); i++) {
						if(item_seleccionado.equals(solver.getSalas().get(i).getNombre())) {
							consultaSala.setText("Nombre de la Sala: "+ solver.getSalas().get(i).getNombre()+"\n"+
									"Departamento de la Sala: "+ solver.getSalas().get(i).getTipo()+"\n"+
									"Nº de computadores de la Sala: "+ solver.getSalas().get(i).getCapacidad()+"\n"+
									"Sistema Operativo de Computadores: "+ solver.getSalas().get(i).getComputadores().getSistemaOperativo()+"\n"+
									"Disco Duro de Computadores: "+ solver.getSalas().get(i).getComputadores().getDiscoDuro()+" GB"+"\n"+
									"Memoria RAM de Computadores: "+ solver.getSalas().get(i).getComputadores().getMemoriaRAM()+" GB"+"\n");
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
				fileChooser.setInitialDirectory(new File("docs/")); 
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
				File escogido = fileChooser.showOpenDialog(stage);

				if (escogido != null) {

					try {

						solver.leerCSVSoftware(escogido.getAbsolutePath());
						txtAreaVista.setText(" ");
						Alert alert = new Alert(AlertType.INFORMATION, 
								"Se ha importado correctamente el archivo");
						alert.show();
						habilitarComponentes();
						chkRestSoftwareDepartamento.setSelected(true);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						Alert alert = new Alert(AlertType.ERROR, 
								"No se ha importado correctamente el archivo");
						alert.show();
						e1.printStackTrace();
					}

				}

			}
		});

		btnGenerar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				txtAreaVista.setText(" ");
				tiempoDeCarga.setText(" ");
				imageView.setVisible(true);
				
				new Thread() {

					@Override
					public void run() {

						long startTime = System.currentTimeMillis();
						String tiempo="";
						
						if (!txtFldLimSoluciones.getText().equals("") &&
								!comboBoxFldPorcDisco.getValue().toString().equals("Seleccione Porcentaje")) {

							int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
							int porc=Integer.parseInt(comboBoxFldPorcDisco.getValue().toString().split("%")[0]);

							solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
									chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
									chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareDemandaCapacidad.isSelected(),
									chkRestSoftwareBasico.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
									chkRestSoftwareSalaNombre.isSelected(), porc);
						}
						else if (!txtFldLimSoluciones.getText().equals("") &&
								comboBoxFldPorcDisco.getValue().toString().equals("Seleccione Porcentaje")) {

							int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
							int porc=70;

							solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
									chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
									chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareDemandaCapacidad.isSelected(),
									chkRestSoftwareBasico.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
									chkRestSoftwareSalaNombre.isSelected(), porc);
						}
						else if (txtFldLimSoluciones.getText().equals("") && comboBoxFldPorcDisco.getValue() != null &&
								!comboBoxFldPorcDisco.getValue().toString().equals("Seleccione Porcentaje")) {

							int numSol=10;
							int porc=Integer.parseInt(comboBoxFldPorcDisco.getValue().toString().split("%")[0]);

							solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
									chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
									chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareDemandaCapacidad.isSelected(),
									chkRestSoftwareBasico.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
									chkRestSoftwareSalaNombre.isSelected(), porc);
						}else {
							int numSol=10;
							int porc=70;
							solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
									chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
									chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareDemandaCapacidad.isSelected(),
									chkRestSoftwareBasico.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(),
									chkRestSoftwareSalaNombre.isSelected(), porc);
						}

						try {
							Thread.sleep(100);

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						imageView.setVisible(false);

						long endTime = System.currentTimeMillis() - startTime;
						int tiempoSegundos = (int) (endTime/1000.0); 
						int tiempoMinutos = (int) (tiempoSegundos/60.0);

						if (tiempoMinutos != 0) {
							int diferenciaSegundos = (int) (tiempoSegundos - 60.0); 
							tiempo="Tiempo de Carga: 0"+tiempoMinutos+":"+diferenciaSegundos;							
						}else {

							tiempo="Tiempo de Carga: 0"+tiempoMinutos+":"+tiempoSegundos;
						}

						txtAreaVista.setText(solver.getReporte()+"\n"+tiempo);

						comboSoft.setDisable(false);

						//												Collections.sort(solver.getNombreSoftware(), new Comparator<Software>() {
						//													public int compare(Software obj1, Software obj2) {
						//													
						//															return obj1.getNombre().compareTo(obj2.getNombre());								
						//														
						//													}
						//												});

						if (comboSoft.getItems().size() > 1) {

							comboSoft.getItems().clear();

						}

						Collections.sort(solver.getNombreSoftware());

						comboSoft.getItems().add("Seleccionar un Software");
						for (int i = 0; i < solver.getNombreSoftware().size(); i++) {
							comboSoft.getItems().add(solver.getNombreSoftware().get(i).toString());

						}

						solver.getNombreSoftware().clear();

					}
				}.start();
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
						String nombre="";
						String dpto="";
						String sisOpe="";
						int disco=0;
						int ram=0;
						String report="";
						for (int i = 0; i < solver.getSalas().size(); i++) {
							for (int j = 0; j < solver.getToolSoftware().size(); j++) {

								if(item_seleccionado.equals(solver.getToolSoftware().get(j).getNombre())) {

									nombre=solver.getToolSoftware().get(j).getNombre();
									dpto=solver.getToolSoftware().get(j).getTipoDepartamento();
									sisOpe=solver.getToolSoftware().get(j).getSistemaOperativo();
									disco=solver.getToolSoftware().get(j).getDiscoDuro();
									ram=solver.getToolSoftware().get(j).getMemoriaRAM();

									if (!materias.contains(solver.getToolSoftware().get(j).getNombreMateria())){
										materias.add(solver.getToolSoftware().get(j).getNombreMateria());
									}

									if (!salas.contains(solver.getToolSoftware().get(j).getNombreSala()) && 
											!solver.getToolSoftware().get(j).getNombreSala().contains("L")){
										salas.add(solver.getToolSoftware().get(j).getNombreSala());
									}
								}
							}
						}

						report+="Nombre del software: "+ nombre+"\n"+
								"Departamento asociado: "+ dpto+"\n"+"\n"+
								"Disco Duro: "+ disco+" GB"+"\n"+
								"Memoria RAM: "+ ram+" GB"+"\n"+
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

				//					JFileChooser directorio = new JFileChooser();
				//					FileNameExtensionFilter filtro = new FileNameExtensionFilter(
				//							"txt", "txt");
				//					directorio.setFileFilter(filtro);
				//	
				//					String ruta = "";
				//					int respuesta = directorio.showOpenDialog(frame);
				//					if (respuesta == JFileChooser.APPROVE_OPTION) {
				//						File escogido = directorio.getSelectedFile();
				//						ruta = escogido.getAbsolutePath();
				//	
				//						try {
				//							solver.exportarReporteTxt(ruta);
				//	
				//							JOptionPane.showMessageDialog(null, "Se ha exportado correctamente el archivo en la ruta "
				//									+ruta,
				//									"Mensaje", JOptionPane.INFORMATION_MESSAGE);
				//	
				//						} catch (FileNotFoundException e1) {
				//							// TODO Auto-generated catch block
				//							e1.printStackTrace();
				//	
				//						} catch (Exception e1) {
				//							// TODO Auto-generated catch block
				//							e1.printStackTrace();
				//						}
				//					}
			}
		});
		//	
		//			btnExportPdf.setOnAction(new EventHandler<ActionEvent>() {
		//				@Override
		//				public void handle(ActionEvent event) {
		//	
		//					JFileChooser directorio = new JFileChooser();
		//					FileNameExtensionFilter filtro = new FileNameExtensionFilter(
		//							"pdf", "pdf");
		//					directorio.setFileFilter(filtro);
		//	
		//					String ruta = "";
		//					int respuesta = directorio.showOpenDialog(frame);
		//					if (respuesta == JFileChooser.APPROVE_OPTION) {
		//						File escogido = directorio.getSelectedFile();
		//						ruta = escogido.getAbsolutePath();
		//	
		//						//					try {
		//						//						solver.exportarReporteTxt(ruta);
		//						//						
		//						//						JOptionPane.showMessageDialog(null, "Se ha exportado correctamente el archivo en la ruta "
		//						//								+ruta,
		//						//								"Mensaje", JOptionPane.INFORMATION_MESSAGE);
		//						//						
		//						//					} catch (FileNotFoundException e1) {
		//						//						// TODO Auto-generated catch block
		//						//						e1.printStackTrace();
		//						//
		//						//					} catch (Exception e1) {
		//						//						// TODO Auto-generated catch block
		//						//						e1.printStackTrace();
		//						//					}
		//					}
		//	
		//				}
		//			});
		//	
		//			btnExportCsv.setOnAction(new EventHandler<ActionEvent>() {
		//				@Override
		//				public void handle(ActionEvent event) {
		//	
		//				}
		//			});
		//	
		chkRestSelecTodas.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				if (chkRestSelecTodas.isSelected()) {
					chkRestSoftwareDepartamento.setSelected(true);
					chkRestSostwareSistemaOperativo.setSelected(true);
					chkRestSoftwareRAM.setSelected(true);
					chkRestSoftwareDemandaCapacidad.setSelected(true);
					chkRestSoftwareDiscoDuro.setSelected(true);
					chkRestSoftwareBasico.setSelected(true);
					chkRestSoftwareNumeroLicencias.setSelected(true);
					chkRestSoftwareSalaNombre.setSelected(true);
				}
				else {

					chkRestSoftwareDepartamento.setSelected(false);
					chkRestSostwareSistemaOperativo.setSelected(false);
					chkRestSoftwareRAM.setSelected(false);
					chkRestSoftwareDemandaCapacidad.setSelected(false);
					chkRestSoftwareDiscoDuro.setSelected(false);
					chkRestSoftwareBasico.setSelected(false);
					chkRestSoftwareNumeroLicencias.setSelected(false);
					chkRestSoftwareSalaNombre.setSelected(false);
				}
			}
		});
	}


}
