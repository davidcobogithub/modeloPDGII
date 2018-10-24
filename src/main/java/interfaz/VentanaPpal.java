package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JTextField txtFldPorcDisco;
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
	private JButton btnGenerar;
	private JButton btnLimpiar;
	//private JButton btnOrganizarRegistros;
	private JButton btnImportar;
	private JButton btnExportTxt;
	private JButton btnExportPdf;
	private JButton btnExportCsv;
	private JTextArea txtAreaVista;

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
		setBounds(200, 50, 1000, 660);
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

		JLabel lblLimsolu = new JLabel("L\u00edmite de Soluciones: ");
		lblLimsolu.setBounds(15, 77, 130, 14);
		panelParametros.add(lblLimsolu);

		txtFldLimSoluciones = new JTextField();
		txtFldLimSoluciones.setBounds(147, 74, 186, 20);
		panelParametros.add(txtFldLimSoluciones);
		txtFldLimSoluciones.setColumns(10);

		JLabel lblPorcDisco = new JLabel("Porcentaje de Disco: ");
		lblPorcDisco.setBounds(15, 102, 130, 14);
		panelParametros.add(lblPorcDisco);

		txtFldPorcDisco = new JTextField();
		txtFldPorcDisco.setColumns(10);
		txtFldPorcDisco.setBounds(147, 99, 186, 20);
		panelParametros.add(txtFldPorcDisco);

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
		lblTitulo.setBounds(400, 11, 397, 14);
		contentPane.add(lblTitulo);

		JPanel panelRestricciones = new JPanel();
		panelRestricciones.setBorder(new TitledBorder(null, "Selecci\u00F3n de Restricciones",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRestricciones.setBounds(5, 250, 355, 300);
		contentPane.add(panelRestricciones);
		panelRestricciones.setLayout(null);

		chkRestSelecTodas =  new JCheckBox("Seleccionar Todas");
		chkRestSelecTodas.setBounds(15, 25, 155, 23);
		panelRestricciones.add(chkRestSelecTodas);

		chkRest1 =  new JCheckBox("Restricci\u00F3n 1");
		chkRest1.setBounds(15, 50, 155, 23);
		panelRestricciones.add(chkRest1);

		chkRest2 =  new JCheckBox("Restricci\u00F3n 2");
		chkRest2.setBounds(15, 70, 155, 23);
		panelRestricciones.add(chkRest2);

		chkRest3 =  new JCheckBox("Restricci\u00F3n 3");
		chkRest3.setBounds(15, 90, 155, 23);
		panelRestricciones.add(chkRest3);

		chkRest4 =  new JCheckBox("Restricci\u00F3n 4");
		chkRest4.setBounds(15, 110, 155, 23);
		panelRestricciones.add(chkRest4);

		chkRest5 =  new JCheckBox("Restricci\u00F3n 5");
		chkRest5.setBounds(15, 130, 155, 23);
		panelRestricciones.add(chkRest5);

		btnGenerar = new JButton("Generar Distribuci\u00F3n");
		btnGenerar.setBounds(15, 200, 155, 23);
		panelRestricciones.add(btnGenerar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(185, 200, 155, 23);
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
		panel.setBounds(366, 55, 610, 560);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 590, 538);
		
		panel.add(scrollPane);

		txtAreaVista = new JTextArea();
		txtAreaVista.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC,14));
		scrollPane.setViewportView(txtAreaVista);
		txtAreaVista.setEditable(false);

		adicionarEventos();
	}

	public void adicionarEventos() {


		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtFldLimSoluciones.setText("");
				txtFldPorcDisco.setText("");
				//				txtFldCiudad.setText("");
				//				txtFldBarrio.setText("");
				//				txtFldDireccion.setText("");
				//				txtFldTelefono.setText("");
				//				txtFldTamanio.setText("");
				//				txtFldPrecio.setText("");
				//				txtFldRutaImportacion.setText("");
				//				txtFldRutaExportacion.setText("");
				txtAreaVista.setText("");

			}
		});

		btnGenerar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtAreaVista.setText(" ");

				String tipoOferta=txtFldLimSoluciones.getText();
				String tipoInmueble=txtFldPorcDisco.getText();
				//				String ciudad=txtFldCiudad.getText();
				//				String barrio=txtFldBarrio.getText();
				//				String direccion=txtFldDireccion.getText();
				//				String telefono= txtFldTelefono.getText();
				//				String tamanio=txtFldTamanio.getText();
				//				double tama= Double.parseDouble(tamanio);
				//				
				//				String precio=txtFldPrecio.getText();
				//				double prec=Double.parseDouble(precio);

				//				Inmueble nuevo = new Inmueble(tipoOferta, tipoInmueble, ciudad, tama, prec);
				//				nuevo.setBarrio(barrio);
				//				nuevo.setDirección(direccion);
				//				nuevo.setTelefono(telefono);
				//				
				//				finca.agregarInmueble(nuevo);


				// Se muestra la lista de inmuebles actualizada



				txtAreaVista
				.setText("La lista de inmuebles se ha actualizado a:\n");

				//				ArrayList<Inmueble> lista = finca.getInmuebles();
				//
				//				Inmueble temp;
				//				for (int i = 0; i < lista.size(); i++) {
				//					temp = (Inmueble) lista.get(i);
				//					txtAreaVista.append(temp.toString() + "\n");
				//				}
			}
		});

		btnImportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtAreaVista.setText(" ");

				JFileChooser directorio = new JFileChooser("docs/");
				FileNameExtensionFilter filtro = new FileNameExtensionFilter(
						"CVS", "csv");
				directorio.setFileFilter(filtro);

				int respuesta = directorio.showOpenDialog(frame);
				if (respuesta == JFileChooser.APPROVE_OPTION) {
					File escogido = directorio.getSelectedFile();
					//txtFldRutaImportacion.setText(escogido.getAbsolutePath());

					try {
					
						solver.leerCSVSoftware(escogido.getAbsolutePath());
						solver.modeloInicial();
						txtAreaVista.append(solver.getReporte());

						JOptionPane.showMessageDialog(null, "Se ha importado correctamente el archivo",
								"Mensaje", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "No se ha importado correctamente el archivo",
								"Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}

				}

				// Se muestra la lista de inmuebles actualizada
				//				txtAreaVista
				//				.setText("La lista de inmuebles se ha actualizado a:\n");

				//				ArrayList<Inmueble> lista = finca.getInmuebles();
				//
				//				Inmueble temp;
				//				for (int i = 0; i < lista.size(); i++) {
				//					temp = (Inmueble) lista.get(i);
				//					txtAreaVista.append(temp.toString() + "\n");
				//				}
			}
		});

		btnExportTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtAreaVista.setText(" ");

				JFileChooser directorio = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter(
						"txt", "txt");
				directorio.setFileFilter(filtro);

				String ruta = "";
				int respuesta = directorio.showOpenDialog(frame);
				if (respuesta == JFileChooser.APPROVE_OPTION) {
					File escogido = directorio.getSelectedFile();
					ruta = escogido.getAbsolutePath();
					//txtFldRutaExportacion.setText(ruta);
					try {
						//						finca.generarReporte(ruta);
						//					} catch (FileNotFoundException e1) {
						//						// TODO Auto-generated catch block
						//						e1.printStackTrace();
						//					}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				txtAreaVista
				.append("Se ha generado el reporte de Inmuebles en la ruta\n"
						+ ruta);

			}
		});
	}
}
