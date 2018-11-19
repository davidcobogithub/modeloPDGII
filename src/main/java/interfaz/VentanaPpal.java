package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.SolverProject;

public class VentanaPpal extends JFrame {

	private JPanel contentPane;
	private JTextField txtFldLimSoluciones;
	private JComboBox<String> comboBoxFldPorcDisco;
	//	private JTextField txtFldCiudad;
	//	private JTextField txtFldBarrio;
	//	private JTextField txtFldDireccion;
	//	private JTextField txtFldTelefono;
	//	private JTextField txtFldTamanio;
	//	private JTextField txtFldPrecio;
	//	private JTextField txtFldRutaImportacion;
	//	private JTextField txtFldRutaExportacion;
	private JCheckBox chkRestSelecTodas;
	private JCheckBox chkRest1;
	private JCheckBox chkRest2;
	private JCheckBox chkRest3;
	private JCheckBox chkRest4;
	private JCheckBox chkRest5;
	private JCheckBox chkRest6;
	private JCheckBox chkRest7;
	private JCheckBox chkRest8;
	private JCheckBox chkRest9;
	private JCheckBox chkRest10;
	private JButton btnGenerar;
	private JButton btnLimpiar;
	//private JButton btnOrganizarRegistros;
	private JButton btnImportar;
	private JButton btnExportTxt;
	private JButton btnExportPdf;
	private JButton btnExportCsv;
	private JTextArea txtAreaVista;
	private JLabel img;

	private static SolverProject solver;
	private static VentanaPpal frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		solver = new SolverProject();
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

		//		JLabel lblCiudad = new JLabel("*Ciudad:");
		//		lblCiudad.setBounds(31, 129, 105, 14);
		//		panelAgregar.add(lblCiudad);

		//		JLabel lblBarrio = new JLabel("Barrio:");
		//		lblBarrio.setBounds(31, 154, 105, 14);
		//		panelAgregar.add(lblBarrio);
		//
		//		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		//		lblDireccin.setBounds(31, 179, 105, 14);
		//		panelAgregar.add(lblDireccin);
		//
		//		JLabel lblTelefono = new JLabel("Telefono:");
		//		lblTelefono.setBounds(31, 206, 105, 14);
		//		panelAgregar.add(lblTelefono);
		//
		//		JLabel lblTamao = new JLabel("*Tama\u00F1o:");
		//		lblTamao.setBounds(31, 231, 105, 14);
		//		panelAgregar.add(lblTamao);
		//
		//		JLabel lblPrecio = new JLabel("*Precio:");
		//		lblPrecio.setBounds(31, 256, 105, 14);
		//		panelAgregar.add(lblPrecio);

		btnImportar = new JButton("Importar");
		btnImportar.setBounds(147, 35, 89, 23);
		panelParametros.add(btnImportar);

		//		txtFldCiudad = new JTextField();
		//		txtFldCiudad.setColumns(10);
		//		txtFldCiudad.setBounds(146, 126, 186, 20);
		//		panelAgregar.add(txtFldCiudad);
		//
		//		txtFldBarrio = new JTextField();
		//		txtFldBarrio.setColumns(10);
		//		txtFldBarrio.setBounds(147, 151, 186, 20);
		//		panelAgregar.add(txtFldBarrio);
		//
		//		txtFldDireccion = new JTextField();
		//		txtFldDireccion.setColumns(10);
		//		txtFldDireccion.setBounds(147, 176, 186, 20);
		//		panelAgregar.add(txtFldDireccion);
		//
		//		txtFldTelefono = new JTextField();
		//		txtFldTelefono.setColumns(10);
		//		txtFldTelefono.setBounds(147, 203, 186, 20);
		//		panelAgregar.add(txtFldTelefono);
		//
		//		txtFldTamanio = new JTextField();
		//		txtFldTamanio.setColumns(10);
		//		txtFldTamanio.setBounds(147, 228, 186, 20);
		//		panelAgregar.add(txtFldTamanio);
		//
		//		txtFldPrecio = new JTextField();
		//		txtFldPrecio.setColumns(10);
		//		txtFldPrecio.setBounds(147, 253, 186, 20);
		//		panelAgregar.add(txtFldPrecio);


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

		chkRest1 =  new JCheckBox("Software y Salas de un Departamento");
		chkRest1.setBounds(15, 50, 300, 23);
		panelRestricciones.add(chkRest1);

		chkRest2 =  new JCheckBox("Software y Salas con mismo Sistema Operativo");
		chkRest2.setBounds(15, 70, 300, 23);
		panelRestricciones.add(chkRest2);

		chkRest3 =  new JCheckBox("Capacidad de Memoria RAM");
		chkRest3.setBounds(15, 90, 300, 23);
		panelRestricciones.add(chkRest3);

		chkRest4 =  new JCheckBox("Capacidad de Velocidad del Procesador");
		chkRest4.setBounds(15, 110, 300, 23);
		panelRestricciones.add(chkRest4);

		chkRest5 =  new JCheckBox("Capacidad de Disco Duro");
		chkRest5.setBounds(15, 130, 300, 23);
		panelRestricciones.add(chkRest5);

		chkRest6 =  new JCheckBox("Capacidad de las Salas");
		chkRest6.setBounds(15, 150, 300, 23);
		panelRestricciones.add(chkRest6);

		//		chkRest7 =  new JCheckBox("Capacidad de Disco Duro");
		//		chkRest7.setBounds(15, 170, 300, 23);
		//		panelRestricciones.add(chkRest7);
		//		
		//		chkRest8 =  new JCheckBox("Capacidad de Disco Duro");
		//		chkRest8.setBounds(15, 190, 300, 23);
		//		panelRestricciones.add(chkRest8);
		//		
		//		chkRest9 =  new JCheckBox("Capacidad de Disco Duro");
		//		chkRest9.setBounds(15, 210, 300, 23);
		//		panelRestricciones.add(chkRest9);
		//		
		//		chkRest10 =  new JCheckBox("Capacidad de Disco Duro");
		//		chkRest10.setBounds(15, 230, 300, 23);
		//		panelRestricciones.add(chkRest10);

		btnGenerar = new JButton("Generar Distribuci\u00F3n");
		btnGenerar.setBounds(15, 270, 155, 23);
		panelRestricciones.add(btnGenerar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(185, 270, 155, 23);
		panelRestricciones.add(btnLimpiar);		

		//		JLabel lblRutaDelArchivo = new JLabel("Ruta del archivo:");
		//		lblRutaDelArchivo.setBounds(21, 39, 108, 14);
		//		panelImportacion.add(lblRutaDelArchivo);

		//		txtFldRutaImportacion = new JTextField();
		//		txtFldRutaImportacion.setEnabled(false);
		//		txtFldRutaImportacion.setBounds(139, 36, 196, 20);
		//		panelImportacion.add(txtFldRutaImportacion);
		//		txtFldRutaImportacion.setColumns(10);

		JPanel panelExportacion = new JPanel();
		panelExportacion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Exportar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelExportacion.setBounds(5, 552, 355, 65);
		contentPane.add(panelExportacion);
		panelExportacion.setLayout(null);

		//		txtFldRutaExportacion = new JTextField();
		//		txtFldRutaExportacion.setEnabled(false);
		//		txtFldRutaExportacion.setColumns(10);
		//		txtFldRutaExportacion.setBounds(139, 28, 196, 20);
		//		panelExportacion.add(txtFldRutaExportacion);
		//
		//		JLabel label = new JLabel("Ruta del archivo:");
		//		label.setBounds(21, 31, 108, 14);
		//		panelExportacion.add(label);

		btnExportTxt = new JButton("TXT");
		btnExportTxt.setBounds(20, 30, 90, 23);
		panelExportacion.add(btnExportTxt);

		btnExportPdf = new JButton("PDF");
		btnExportPdf.setBounds(130, 30, 90, 23);
		panelExportacion.add(btnExportPdf);

		btnExportCsv = new JButton("CSV");
		btnExportCsv.setBounds(240, 30, 90, 23);
		panelExportacion.add(btnExportCsv);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(366, 55, 810, 560);
		contentPane.add(panel);
		panel.setLayout(null);

		img=new JLabel(new ImageIcon("img/cargando.gif"));
		img.setBounds(270, 150, 300, 300);
		img.setVisible(false);
		panel.add(img);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 790, 538);
		panel.add(scrollPane);

		txtAreaVista = new JTextArea();
		txtAreaVista.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC,14));
		scrollPane.setViewportView(txtAreaVista);
		txtAreaVista.setEditable(false);

		adicionarEventos();

		inhabilitarComponentes();
	}

	public void habilitarComponentes() {

		txtFldLimSoluciones.setEditable(true);
		comboBoxFldPorcDisco.setEnabled(true);
		chkRestSelecTodas.setEnabled(true);
		chkRest1.setEnabled(true);
		chkRest2.setEnabled(true);
		chkRest3.setEnabled(true);
		chkRest4.setEnabled(true);
		chkRest5.setEnabled(true);
		chkRest6.setEnabled(true);
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
		chkRest1.setEnabled(false);
		chkRest2.setEnabled(false);
		chkRest3.setEnabled(false);
		chkRest4.setEnabled(false);
		chkRest5.setEnabled(false);
		chkRest6.setEnabled(false);
		btnGenerar.setEnabled(false);
		btnLimpiar.setEnabled(false);
		btnExportCsv.setEnabled(false);
		btnExportPdf.setEnabled(false);
		btnExportTxt.setEnabled(false);
	}

	public void adicionarEventos() {

		//solver.modeloInicial(10);

		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtFldLimSoluciones.setText("");
				comboBoxFldPorcDisco.setSelectedIndex(0);
				//				txtFldCiudad.setText("");
				//				txtFldBarrio.setText("");
				//				txtFldDireccion.setText("");
				//				txtFldTelefono.setText("");
				//				txtFldTamanio.setText("");
				//				txtFldPrecio.setText("");
				//				txtFldRutaImportacion.setText("");
				//				txtFldRutaExportacion.setText("");
				txtAreaVista.setText("");
				chkRestSelecTodas.setSelected(false);
				chkRest1.setSelected(false);
				chkRest2.setSelected(false);
				chkRest3.setSelected(false);
				chkRest4.setSelected(false);
				chkRest5.setSelected(false);
				chkRest6.setSelected(false);

			}
		});

		btnGenerar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new Thread() {

					@Override
					public void run() {

						img.setVisible(true);

						if (!txtFldLimSoluciones.getText().equals("")) {

							int numSol=Integer.parseInt(txtFldLimSoluciones.getText());
							solver.modeloInicial(numSol);
						}else {
							int numSol=10;
							solver.modeloInicial(numSol);
						}

						try {
							Thread.sleep(100);

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						img.setVisible(false);
						JOptionPane.showMessageDialog(null, "Distribuci\u00f3n generada satisfactoriamente",
								"Mensaje", JOptionPane.INFORMATION_MESSAGE);
						txtAreaVista.append(solver.getReporte());
					}
				}.start();
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

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "No se ha importado correctamente el archivo",
								"Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}

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
					chkRest1.setSelected(true);
					chkRest2.setSelected(true);
					chkRest3.setSelected(true);
					chkRest4.setSelected(true);
					chkRest5.setSelected(true);
					chkRest6.setSelected(true);
				}
				else {

					chkRest1.setSelected(false);
					chkRest2.setSelected(false);
					chkRest3.setSelected(false);
					chkRest4.setSelected(false);
					chkRest5.setSelected(false);
					chkRest6.setSelected(false);
				}
			}
		});
	}
}
