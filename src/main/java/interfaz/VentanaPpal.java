package interfaz;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Software;
import modelo.SolverProject;

public class VentanaPpal extends JFrame {

	private JPanel contentPane;
	private JTextField txtFldLimSoluciones;
	private JComboBox<String> comboBoxFldPorcDisco;
	private JCheckBox chkRestSelecTodas;
	private JCheckBox chkRestSoftwareDepartamento;
	private JCheckBox chkRestSostwareSistemaOperativo;
	private JCheckBox chkRestSoftwareRAM;
	private JCheckBox chkRestSoftwareDemandaCapacidad;
	private JCheckBox chkRestSoftwareDiscoDuro;
	private JCheckBox chkRestSoftwareBasico;
	private JCheckBox chkRestSoftwareNumeroLicencias;
	private JCheckBox chkRestSoftwareSalaNombre;
	private JButton btnGenerar;
	private JButton btnLimpiar;
	private JButton btnImportar;
	private JButton btnExportTxt;
	private JButton btnExportPdf;
	private JButton btnExportCsv;
	private JTextArea txtAreaVista;
	private JLabel img;
	private JComboBox<String> comboSala;
	private JTextArea  consultaSala;
	private JComboBox<String> comboSoft;
	private JTextArea  consultaSoft;

	private static SolverProject solver;
	private static VentanaPpal frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		solver = new SolverProject();
		//Application.launch(args);
		frame = new VentanaPpal();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public VentanaPpal() {

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 50, 1200, 660);
				contentPane = new JPanel();
				contentPane.setLocation(20, -50);
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
		
				JPanel panelParametros = new JPanel();
				panelParametros.setBorder(new TitledBorder(null, "Cambiar Par\u00e1metros",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelParametros.setBounds(5, 47, 355, 200);
				contentPane.add(panelParametros);
				panelParametros.setLayout(null);
		
				JLabel lblLimsolu = new JLabel("N° Soluciones Parciales: ");
				lblLimsolu.setBounds(15, 77, 150, 14);
				panelParametros.add(lblLimsolu);
		
				txtFldLimSoluciones = new JTextField();
				txtFldLimSoluciones.setBounds(160, 74, 170, 20);
				panelParametros.add(txtFldLimSoluciones);
				txtFldLimSoluciones.setColumns(10);
		
				JLabel lblPorcDisco = new JLabel("Porcentaje de Disco: ");
				lblPorcDisco.setBounds(15, 102, 150, 14);
				panelParametros.add(lblPorcDisco);
		
				comboBoxFldPorcDisco = new JComboBox<>();
				comboBoxFldPorcDisco.setBounds(160, 99, 170, 20);
				comboBoxFldPorcDisco.addItem("Seleccione Porcentaje");
				comboBoxFldPorcDisco.addItem("10%");
				comboBoxFldPorcDisco.addItem("20%");
				comboBoxFldPorcDisco.addItem("30%");
				comboBoxFldPorcDisco.addItem("40%");
				comboBoxFldPorcDisco.addItem("50%");
				comboBoxFldPorcDisco.addItem("60%");
				comboBoxFldPorcDisco.addItem("70%");
				comboBoxFldPorcDisco.addItem("80%");
				comboBoxFldPorcDisco.addItem("90%");
				comboBoxFldPorcDisco.addItem("100%");
				panelParametros.add(comboBoxFldPorcDisco);
		
		
				btnImportar = new JButton("Importar");
				btnImportar.setBounds(147, 35, 89, 23);
				panelParametros.add(btnImportar);
		
		
				JLabel lblTitulo = new JLabel("Distribuci\u00F3n de Software Icesi");
				lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC,
						14));
				lblTitulo.setBounds(550, 20, 397, 14);
				contentPane.add(lblTitulo);
		
				JPanel panelRestricciones = new JPanel();
				panelRestricciones.setBorder(new TitledBorder(null, "Selecci\u00F3n de Restricciones",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelRestricciones.setBounds(5, 250, 355, 300);
				contentPane.add(panelRestricciones);
				panelRestricciones.setLayout(null);
		
				chkRestSelecTodas =  new JCheckBox("Seleccionar Todas");
				chkRestSelecTodas.setBounds(15, 25, 300, 23);
				panelRestricciones.add(chkRestSelecTodas);
		
				chkRestSoftwareDepartamento =  new JCheckBox("Software y Salas de un Departamento");
				chkRestSoftwareDepartamento.setBounds(15, 50, 300, 23);
				panelRestricciones.add(chkRestSoftwareDepartamento);
		
				chkRestSostwareSistemaOperativo =  new JCheckBox("Software y Salas con mismo Sistema Operativo");
				chkRestSostwareSistemaOperativo.setBounds(15, 70, 300, 23);
				panelRestricciones.add(chkRestSostwareSistemaOperativo);
		
				chkRestSoftwareSalaNombre =  new JCheckBox("Software Instalado en Salas Específicas");
				chkRestSoftwareSalaNombre.setBounds(15, 90, 300, 23);
				panelRestricciones.add(chkRestSoftwareSalaNombre);
		
				chkRestSoftwareDiscoDuro =  new JCheckBox("Capacidad de Disco Duro");
				chkRestSoftwareDiscoDuro.setBounds(15, 110, 300, 23);
				panelRestricciones.add(chkRestSoftwareDiscoDuro);
		
				chkRestSoftwareRAM=  new JCheckBox("Capacidad de Memoria RAM");
				chkRestSoftwareRAM.setBounds(15, 130, 300, 23);
				panelRestricciones.add(chkRestSoftwareRAM);
		
				chkRestSoftwareDemandaCapacidad =  new JCheckBox("Demanda y Capacidad de las Salas");
				chkRestSoftwareDemandaCapacidad.setBounds(15, 150, 300, 23);
				panelRestricciones.add(chkRestSoftwareDemandaCapacidad);
		
				chkRestSoftwareBasico =  new JCheckBox("Instalación de Software Básico");
				chkRestSoftwareBasico.setBounds(15, 170, 300, 23);
				panelRestricciones.add(chkRestSoftwareBasico);
		
				chkRestSoftwareNumeroLicencias =  new JCheckBox("Cantidad de Licencias de Software");
				chkRestSoftwareNumeroLicencias.setBounds(15, 190, 300, 23);
				panelRestricciones.add(chkRestSoftwareNumeroLicencias);
		
				btnGenerar = new JButton("Generar Distribuci\u00F3n");
				btnGenerar.setBounds(15, 270, 155, 23);
				panelRestricciones.add(btnGenerar);
		
				btnLimpiar = new JButton("Limpiar");
				btnLimpiar.setBounds(185, 270, 155, 23);
				panelRestricciones.add(btnLimpiar);		
		
				JPanel panelExportacion = new JPanel();
				panelExportacion.setBorder(new TitledBorder(UIManager
						.getBorder("TitledBorder.border"), "Exportar",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelExportacion.setBounds(5, 552, 355, 65);
				contentPane.add(panelExportacion);
				panelExportacion.setLayout(null);
		
				btnExportTxt = new JButton("TXT");
				btnExportTxt.setBounds(20, 30, 90, 23);
				panelExportacion.add(btnExportTxt);
		
				btnExportPdf = new JButton("PDF");
				btnExportPdf.setBounds(130, 30, 90, 23);
				panelExportacion.add(btnExportPdf);
		
				btnExportCsv = new JButton("CSV");
				btnExportCsv.setBounds(240, 30, 90, 23);
				panelExportacion.add(btnExportCsv);
		
				JPanel panelContenedor = new JPanel();
				panelContenedor.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
						TitledBorder.TOP, null, null));
				panelContenedor.setBounds(366, 55, 810, 560);
				panelContenedor.setLayout(null);
				contentPane.add(panelContenedor);
		
				JTabbedPane pestanas = new JTabbedPane(JTabbedPane.TOP);
				pestanas.setBounds(0, 0, 810, 560);
				panelContenedor.add(pestanas);
		
				JPanel panelContentDistribucion = new JPanel();
				panelContentDistribucion.setBounds(366, 55, 810, 560);
				panelContentDistribucion.setLayout(null);
				pestanas.addTab("Distribución", panelContentDistribucion);
		
				img=new JLabel(new ImageIcon("img/cargando.gif"));
				img.setBounds(270, 150, 300, 300);
				img.setVisible(false);
				panelContentDistribucion.add(img);
		
				JScrollPane scrollPaneTxtDistribucion = new JScrollPane();
				scrollPaneTxtDistribucion.setBounds(3, 3, 800, 528);
				panelContentDistribucion.add(scrollPaneTxtDistribucion);
		
				txtAreaVista = new JTextArea();
				txtAreaVista.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC,14));
				scrollPaneTxtDistribucion.setViewportView(txtAreaVista);
				txtAreaVista.setEditable(false);
		
				JPanel panel2 = new JPanel();
				panelContenedor.setBounds(366, 55, 810, 560);
				pestanas.addTab("Consultas", panel2);
				panel2.setLayout(null);
		
				JLabel conSal=new JLabel("Consultar Salas");
				conSal.setBounds(0, 0, 810, 50);
				panel2.add(conSal);
		
				ArrayList<String> nombreSal=new ArrayList<>();
				comboSala=new JComboBox<String>();
				nombreSal=solver.getNombreSalas();
				comboSala.setBounds(0, 50, 805, 50);
				comboSala.addItem("Seleccione una Sala");
		
				for (int i = 0; i < nombreSal.size(); i++) {
					comboSala.addItem(nombreSal.get(i).toString());
				}
		
				panel2.add(comboSala);
		
				consultaSala=new JTextArea();
				consultaSala.setBounds(0, 100, 805, 80);
				consultaSala.setEditable(false);
				panel2.add(consultaSala);
		
		
				JLabel conSof=new JLabel("Consultar Software");
				conSof.setBounds(0, 180, 810, 50);
				panel2.add(conSof);
		
				comboSoft=new JComboBox<String>();
				comboSoft.setBounds(0, 230, 805, 50);
				panel2.add(comboSoft);
		
				JScrollPane scrollPaneConsultaSoft = new JScrollPane();
				scrollPaneConsultaSoft.setBounds(0, 280, 805, 190);
		
				consultaSoft=new JTextArea();
				consultaSoft.setEditable(false);
				scrollPaneConsultaSoft.setViewportView(consultaSoft);
	
				panel2.add(scrollPaneConsultaSoft);
		
				adicionarEventos();
		
				inhabilitarComponentes();
	}

	public void habilitarComponentes() {

		txtFldLimSoluciones.setEditable(true);
		comboBoxFldPorcDisco.setEnabled(true);
		chkRestSelecTodas.setEnabled(true);
		chkRestSoftwareDepartamento.setEnabled(true);
		chkRestSostwareSistemaOperativo.setEnabled(true);
		chkRestSoftwareRAM.setEnabled(true);
		chkRestSoftwareDemandaCapacidad.setEnabled(true);
		chkRestSoftwareDiscoDuro.setEnabled(true);
		chkRestSoftwareBasico.setEnabled(true);
		chkRestSoftwareNumeroLicencias.setEnabled(true);
		chkRestSoftwareSalaNombre.setEnabled(true);
		btnGenerar.setEnabled(true);
		btnLimpiar.setEnabled(true);
		btnExportCsv.setEnabled(true);
		btnExportPdf.setEnabled(true);
		btnExportTxt.setEnabled(true);
	}

	public void inhabilitarComponentes() {

		txtFldLimSoluciones.setEditable(false);
		comboBoxFldPorcDisco.setEnabled(false);
		chkRestSelecTodas.setEnabled(false);
		chkRestSoftwareDepartamento.setEnabled(false);
		chkRestSostwareSistemaOperativo.setEnabled(false);
		chkRestSoftwareRAM.setEnabled(false);
		chkRestSoftwareDemandaCapacidad.setEnabled(false);
		chkRestSoftwareDiscoDuro.setEnabled(false);
		chkRestSoftwareBasico.setEnabled(false);
		chkRestSoftwareNumeroLicencias.setEnabled(false);
		chkRestSoftwareSalaNombre.setEnabled(false);
		btnGenerar.setEnabled(false);
		btnLimpiar.setEnabled(false);
		btnExportCsv.setEnabled(false);
		btnExportPdf.setEnabled(false);
		btnExportTxt.setEnabled(false);
		comboSoft.setEnabled(false);
	}

	public void adicionarEventos() {

		//solver.modeloInicial(10);

		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtFldLimSoluciones.setText("");
				comboBoxFldPorcDisco.setSelectedIndex(0);

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

		comboSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item_seleccionado = comboSala.getSelectedItem().toString();
				if (item_seleccionado.equals("Seleccione una Sala")) {
					consultaSala.setText("Por favor seleccione una sala");
				}else {
					for (int i = 0; i < solver.getSalas().size(); i++) {
						if(item_seleccionado.equals(solver.getSalas().get(i).getNombre())) {
							consultaSala.setText("Nombre de la Sala: "+ solver.getSalas().get(i).getNombre()+"\n"+
									"Departamento de la Sala: "+ solver.getSalas().get(i).getTipo()+"\n"+
									"Nº de computadores de la Sala: "+ solver.getSalas().get(i).getCapacidad());
						}
					}
				}
			}
		});

		btnImportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

								JFileChooser directorio = new JFileChooser("docs/");
								FileNameExtensionFilter filtro = new FileNameExtensionFilter(
										"CVS", "csv");
								directorio.setFileFilter(filtro);
				
								int respuesta = directorio.showOpenDialog(frame);
								if (respuesta == JFileChooser.APPROVE_OPTION) {
									File escogido = directorio.getSelectedFile();
				
									try {
				
										solver.leerCSVSoftware(escogido.getAbsolutePath());
										txtAreaVista.setText(" ");
										JOptionPane.showMessageDialog(null, "Se ha importado correctamente el archivo",
												"Mensaje", JOptionPane.INFORMATION_MESSAGE);
				
										habilitarComponentes();
										chkRestSoftwareDepartamento.setSelected(true);
				
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										JOptionPane.showMessageDialog(null, "No se ha importado correctamente el archivo",
												"Error", JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									}
				
								}

			}
		});

		btnGenerar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new Thread() {

					@Override
					public void run() {

						txtAreaVista.setText(" ");
						img.setVisible(true);

						if (!txtFldLimSoluciones.getText().equals("") &&
								!comboBoxFldPorcDisco.getSelectedItem().toString().equals("Seleccione Porcentaje")) {

							int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
							int porc=Integer.parseInt(comboBoxFldPorcDisco.getSelectedItem().toString().split("%")[0]);

							solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
									chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
									chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareDemandaCapacidad.isSelected(),
									chkRestSoftwareBasico.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
									chkRestSoftwareSalaNombre.isSelected(), porc);
						}
						else if (!txtFldLimSoluciones.getText().equals("") &&
								comboBoxFldPorcDisco.getSelectedItem().toString().equals("Seleccione Porcentaje")) {

							int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
							int porc=70;

							solver.modeloInicial(numSol, chkRestSoftwareDepartamento.isSelected(), 
									chkRestSoftwareRAM.isSelected(), chkRestSostwareSistemaOperativo.isSelected(),
									chkRestSoftwareDiscoDuro.isSelected(), chkRestSoftwareDemandaCapacidad.isSelected(),
									chkRestSoftwareBasico.isSelected(), chkRestSoftwareNumeroLicencias.isSelected(), 
									chkRestSoftwareSalaNombre.isSelected(), porc);
						}
						else if (txtFldLimSoluciones.getText().equals("") &&
								!comboBoxFldPorcDisco.getSelectedItem().toString().equals("Seleccione Porcentaje")) {

							int numSol=10;
							int porc=Integer.parseInt(comboBoxFldPorcDisco.getSelectedItem().toString().split("%")[0]);

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

						img.setVisible(false);
						txtAreaVista.append(solver.getReporte());
						comboSoft.setEnabled(true);

						//												Collections.sort(solver.getNombreSoftware(), new Comparator<Software>() {
						//													public int compare(Software obj1, Software obj2) {
						//													
						//															return obj1.getNombre().compareTo(obj2.getNombre());								
						//														
						//													}
						//												});

						if (comboSoft.getItemCount() > 1) {

							comboSoft.removeAllItems();

						}

						Collections.sort(solver.getNombreSoftware());

						comboSoft.addItem("Seleccionar un Software");
						for (int i = 0; i < solver.getNombreSoftware().size(); i++) {
							comboSoft.addItem(solver.getNombreSoftware().get(i).toString());

						}

						solver.getNombreSoftware().clear();

					}
				}.start();
			}
		});

		comboSoft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String item_seleccionado = comboSoft.getSelectedItem().toString();
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

									if (!salas.contains(solver.getToolSoftware().get(j).getNombreSala())){
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

		btnExportTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

								JFileChooser directorio = new JFileChooser();
								FileNameExtensionFilter filtro = new FileNameExtensionFilter(
										"txt", "txt");
								directorio.setFileFilter(filtro);
				
								String ruta = "";
								int respuesta = directorio.showOpenDialog(frame);
								if (respuesta == JFileChooser.APPROVE_OPTION) {
									File escogido = directorio.getSelectedFile();
									ruta = escogido.getAbsolutePath();
				
									try {
										solver.exportarReporteTxt(ruta);
				
										JOptionPane.showMessageDialog(null, "Se ha exportado correctamente el archivo en la ruta "
												+ruta,
												"Mensaje", JOptionPane.INFORMATION_MESSAGE);
				
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
				
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
			}
		});

		btnExportPdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

								JFileChooser directorio = new JFileChooser();
								FileNameExtensionFilter filtro = new FileNameExtensionFilter(
										"pdf", "pdf");
								directorio.setFileFilter(filtro);
				
								String ruta = "";
								int respuesta = directorio.showOpenDialog(frame);
								if (respuesta == JFileChooser.APPROVE_OPTION) {
									File escogido = directorio.getSelectedFile();
									ruta = escogido.getAbsolutePath();
				
									//					try {
									//						solver.exportarReporteTxt(ruta);
									//						
									//						JOptionPane.showMessageDialog(null, "Se ha exportado correctamente el archivo en la ruta "
									//								+ruta,
									//								"Mensaje", JOptionPane.INFORMATION_MESSAGE);
									//						
									//					} catch (FileNotFoundException e1) {
									//						// TODO Auto-generated catch block
									//						e1.printStackTrace();
									//
									//					} catch (Exception e1) {
									//						// TODO Auto-generated catch block
									//						e1.printStackTrace();
									//					}
								}

			}
		});

		btnExportCsv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		chkRestSelecTodas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (chkRestSelecTodas.isSelected()) {
					chkRestSoftwareDepartamento.setSelected(true);
					chkRestSostwareSistemaOperativo.setSelected(true);
					chkRestSoftwareRAM.setSelected(true);
					chkRestSoftwareDemandaCapacidad.setSelected(true);
					chkRestSoftwareDiscoDuro.setSelected(true);
					chkRestSoftwareBasico.setSelected(true);
					chkRestSoftwareNumeroLicencias.setEnabled(true);
					chkRestSoftwareSalaNombre.setEnabled(true);
				}
				else {

					chkRestSoftwareDepartamento.setSelected(false);
					chkRestSostwareSistemaOperativo.setSelected(false);
					chkRestSoftwareRAM.setSelected(false);
					chkRestSoftwareDemandaCapacidad.setSelected(false);
					chkRestSoftwareDiscoDuro.setSelected(false);
					chkRestSoftwareBasico.setSelected(false);
					chkRestSoftwareNumeroLicencias.setEnabled(false);
					chkRestSoftwareSalaNombre.setEnabled(false);
				}
			}
		});
	}

//	@Override
//	public void start(Stage stage) throws Exception {
//		// TODO Auto-generated method stub
//	     TitledPane firstTitledPane = new TitledPane();
//	      firstTitledPane.setText("Java");
//	 
//	      VBox content1 = new VBox();
//	      content1.getChildren().add(new Label("Java Swing Tutorial"));
//	      content1.getChildren().add(new Label("JavaFx Tutorial"));
//	      content1.getChildren().add(new Label("Java IO Tutorial"));
//	 
//	      firstTitledPane.setContent(content1);
//	 
//	      // Create Second TitledPane.
//	      TitledPane secondTitledPane = new TitledPane();
//	      secondTitledPane.setText("CShape");
//	 
//	      VBox content2 = new VBox();
//	      content2.getChildren().add(new Label("CShape Tutorial for Beginners"));
//	      content2.getChildren().add(new Label("CShape Enums Tutorial"));
//	 
//	      secondTitledPane.setContent(content2);
//	 
//	    
//	      Accordion root= new Accordion();      
//	      root.getPanes().addAll(firstTitledPane, secondTitledPane);
//	    
//	      Scene scene = new Scene(root, 300, 200);
//	      stage.setTitle("Accordion (o7planning.org)");
//	      stage.setScene(scene);
//	      stage.show();
//		
//	}
}
