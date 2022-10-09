package de.ossi.modbustcp.gui;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.SolarizedDarkTheme;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.connection.ModbusTCPWriter;
import de.ossi.modbustcp.data.ExcelListReader;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Example Programm with Swing GUI
 * 
 * @author ossi
 *
 */
@Log
public class ModbusTCPGUI {

	private static final String FILE_ENDING = "modbustcp";
	private static final String FILE_ENDING_WITH_DOT = "." + FILE_ENDING;
	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;
	private static final CellConstraints CC = new CellConstraints();

	private static final String OPERATIONS_DEVICES_FILENAME = "CCGX-Modbus-TCP-register-list-2.53.xlsx";
	private static final String GITHUB_URL = "https://github.com/CommentSectionScientist/modbustcp";
	private final JFrame frame;
	private ModbusTCPReader modbusReader;

	private JTextField ipAddress;
	private JTextField port;
	private JTextField writeInput;
	private JComboBox<ModbusOperation> operations;
	private JComboBox<ModbusDevice> devices;
	private EventList<DeviceOperationResultTO> resultEventList;
	private final List<ModbusDevice> deviceList;
	private final List<ModbusOperation> operationList;
	private DefaultEventSelectionModel<DeviceOperationResultTO> selectionModel;

	private AdvancedTableModel<DeviceOperationResultTO> createModel() {
		resultEventList = new BasicEventList<>();
		return GlazedListsSwing.eventTableModel(resultEventList, new DeviceOperationResultTableFormat());
	}

	public static void main(String[] args) {
		String fileName = args.length != 0 ? args[0] : OPERATIONS_DEVICES_FILENAME;
		EventQueue.invokeLater(() -> new ModbusTCPGUI(fileName));
	}

	public ModbusTCPGUI(String fileName) {
		// Laf has to be set first
		setLookAndFeel();
		ExcelListReader operationDevicesReader = new ExcelListReader(fileName);
		deviceList = operationDevicesReader.readDevices();
		operationList = operationDevicesReader.readOperations();
		frame = new JFrame("ModbusTCP");
		frame.setJMenuBar(createMenu());
		frame.setIconImage(getIcon());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(createTopPanel());
		frame.setMinimumSize(frame.getPreferredSize());
		frame.pack();
		frame.setVisible(true);
		UIManager.put("FileChooser.cancelButtonText", "Cancel");
	}

	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("GitHub");
		menuItem.addActionListener(new OpenGitHubAction());
		menu.add(menuItem);
		menuBar.add(menu);
		return menuBar;
	}

	private class OpenGitHubAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			openWebpage(URI.create(GITHUB_URL));
		}

		public void openWebpage(URI uri) {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(uri);
				} catch (Exception e) {
					showErrorDialog(e, e.getMessage());
				}
			}
		}
	}

	private void setLookAndFeel() {
		LafManager.install(new SolarizedDarkTheme());
	}

	private Image getIcon() {
		URL resource = frame.getClass().getResource("/SolarPanel.png");
		if(resource !=null){
			return new ImageIcon(resource).getImage();
		}else{
			log.log(Level.SEVERE, "Icon nicht gefunden");
			return null;
		}
	}

	private JPanel createTopPanel() {
		FormLayout layout = new FormLayout("3dlu,300dlu:g,8dlu,300dlu:g,3dlu,p,3dlu", "3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p:g");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.add(createIpAdressLabel(), CC.xy(2, 2));
		builder.add(createPortLabel(), CC.xy(4, 2));
		builder.add(createIpAdressField(), CC.xy(2, 4));
		builder.add(createPortField(), CC.xy(4, 4));
		builder.add(createOperationLabel(), CC.xy(2, 6));
		builder.add(createOperationsCombobox(), CC.xy(2, 8));
		builder.add(createDeviceLabel(), CC.xy(4, 6));
		builder.add(createDevicesCombobox(), CC.xy(4, 8));
		builder.add(createAddButton(), CC.xy(6, 8));
		builder.add(createTabbedPane(), CC.xyw(2, 10, 5));
		return builder.getPanel();
	}

	private JTabbedPane createTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Read", createReadPanel());
		pane.addTab("Write", createWritePanel());
		return pane;
	}

	private JPanel createReadPanel() {
		FormLayout layout = new FormLayout("3dlu,122dlu:g,3dlu,122dlu:g,5dlu,224dlu:g,3dlu,20dlu", "3dlu,15dlu,20dlu,1dlu,20dlu,170dlu,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.add(createTablePane(), CC.xywh(2, 2, 5, 5));

		builder.add(createMoveUpButton(), CC.xy(8, 3));
		builder.add(createMoveDownButton(), CC.xy(8, 5));

		builder.add(createSaveButton(), CC.xy(2, 8));
		builder.add(createLoadButton(), CC.xy(4, 8));
		builder.add(createReadButton(), CC.xy(6, 8));
		return builder.getPanel();
	}

	private JPanel createWritePanel() {
		FormLayout layout = new FormLayout("3dlu,122dlu,3dlu,122dlu", "3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.add(createWriteInputLabel(), CC.xy(2, 2));
		builder.add(createWriteInputField(), CC.xy(2, 4));
		builder.add(createWriteButton(), CC.xy(4, 4));
		return builder.getPanel();
	}

	private JComponent createTablePane() {
		AdvancedTableModel<DeviceOperationResultTO> tableModel = createModel();
		JTable modbusOperationDeviceTable = new JTable(tableModel);
		modbusOperationDeviceTable.getColumn(modbusOperationDeviceTable.getColumnName(4)).setCellRenderer(new JButtonRenderer());
		modbusOperationDeviceTable.getColumn(modbusOperationDeviceTable.getColumnName(4)).setCellEditor(new JButtonCellEditor());
		TableColumnModel columnModel = modbusOperationDeviceTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(235);
		columnModel.getColumn(1).setPreferredWidth(260);
		columnModel.getColumn(2).setPreferredWidth(115);
		columnModel.getColumn(3).setPreferredWidth(360);
		columnModel.getColumn(4).setPreferredWidth(25);
		modbusOperationDeviceTable.setColumnSelectionAllowed(false);
		selectionModel = new DefaultEventSelectionModel<>(resultEventList);
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		modbusOperationDeviceTable.setSelectionModel(selectionModel);
		return new JScrollPane(modbusOperationDeviceTable);
	}

	private class JButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = 1L;

		public Object getCellEditorValue() {
			return null;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			return resultEventList.get(row).getRemoveButton();
		}
	}

	private class JButtonRenderer implements TableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			return resultEventList.get(row).getRemoveButton();
		}
	}

	private JComponent createWriteInputLabel() {
		return new JLabel("Input Value (Number):");
	}

	private JComponent createOperationLabel() {
		return new JLabel("Operation:");
	}

	private JComponent createIpAdressLabel() {
		return new JLabel("IP-Address:");
	}

	private JComponent createPortLabel() {
		return new JLabel("Port:");
	}

	private JComponent createDeviceLabel() {
		return new JLabel("Device:");
	}

	private JComponent createIpAdressField() {
		ipAddress = new JTextField(IP_VICTRON);
		return ipAddress;
	}

	private JComponent createWriteInputField() {
		writeInput = new JTextField();
		((PlainDocument) writeInput.getDocument()).setDocumentFilter(new SignedNumberFilterMode());
		return writeInput;
	}

	private JComponent createPortField() {
		port = new JTextField(String.valueOf(MODBUS_DEFAULT_PORT));
		((PlainDocument) port.getDocument()).setDocumentFilter(new SignedNumberFilterMode());
		return port;
	}

	private JComponent createOperationsCombobox() {
		operations = new JComboBox<>(allOperations());
		return operations;
	}

	private ModbusOperation[] allOperations() {
		List<ModbusOperation> allOperations = operationList;
		allOperations.sort(Comparator.comparing(ModbusOperation::getAddress));
		return allOperations.toArray(new ModbusOperation[0]);
	}

	private JComponent createDevicesCombobox() {
		devices = new JComboBox<>(allDevices());
		return devices;
	}

	private ModbusDevice[] allDevices() {
		List<ModbusDevice> allDevices = deviceList;
		allDevices.sort(Comparator.comparing(ModbusDevice::getUnitId));
		return allDevices.toArray(new ModbusDevice[0]);
	}

	private JComponent createReadButton() {
		JButton read = new JButton("Read All");
		read.addActionListener(new ReadAllAction());
		return read;
	}

	private abstract class MoveItemAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			EventList<DeviceOperationResultTO> selectedItems = selectionModel.getSelected();
			if (selectedItems.isEmpty()) {
				return;
			}
			move(selectedItems.get(0));
		}

		protected abstract void move(DeviceOperationResultTO item);
	}

	private class MoveUpAction extends MoveItemAction {
		@Override
		protected void move(DeviceOperationResultTO item) {
			// Only one item is selected when move is triggered
			int indexOld = selectionModel.getLeadSelectionIndex();
			if (indexOld == 0) {
				return;
			}
			resultEventList.remove(indexOld);
			int indexNew = indexOld - 1;
			resultEventList.add(indexNew, item);
			selectionModel.setSelectionInterval(indexNew, indexNew);
		}
	}

	private class MoveDownAction extends MoveItemAction {
		@Override
		protected void move(DeviceOperationResultTO item) {
			// Only one item is selected when move is triggered
			int indexOld = selectionModel.getLeadSelectionIndex();
			if (indexOld == resultEventList.size() - 1) {
				return;
			}
			resultEventList.remove(indexOld);
			int indexNew = indexOld + 1;
			resultEventList.add(indexNew, item);
			selectionModel.setSelectionInterval(indexNew, indexNew);
		}
	}

	private JComponent createMoveUpButton() {
		Icon icon = UIManager.getIcon("ArrowButton.up.icon");
		JButton moveUp = new JButton();
		moveUp.setIcon(icon);
		moveUp.addActionListener(new MoveUpAction());
		return moveUp;
	}

	private JComponent createMoveDownButton() {
		Icon icon = UIManager.getIcon("ArrowButton.down.icon");
		JButton moveDown = new JButton();
		moveDown.setIcon(icon);
		moveDown.addActionListener(new MoveDownAction());
		return moveDown;
	}

	private JComponent createSaveButton() {
		JButton save = new JButton("Save Selection");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<DeviceOperationResultTO> tosToSave = new ArrayList<>(resultEventList);
				if (!tosToSave.isEmpty()) {
					// Remove old Times/Results for TOs
					removeIrrelevantValues(tosToSave);
					serializeTOs(tosToSave);
				}
			}

			private void removeIrrelevantValues(List<DeviceOperationResultTO> tosToSave) {
				tosToSave.forEach(to -> {
					to.setErgebnis(null);
					to.setZeit(null);
					to.setRemoveButton(null);
				});
			}

			private void serializeTOs(List<DeviceOperationResultTO> tosToSave) {
				JFileChooser chooser = createSaveChooser();
				int response = chooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					File f = getFileWithEnding(chooser.getSelectedFile());
					try (FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
						oos.writeObject(tosToSave);
						oos.flush();
					} catch (IOException e) {
						showErrorDialog(e, e.getMessage());
					}
				}
			}

			private File getFileWithEnding(File selectedFile) {
				Optional<String> extension = getExtensionByStringHandling(selectedFile.getName());
				if (!extension.isPresent() || !extension.get().equalsIgnoreCase(FILE_ENDING)) {
					return new File(selectedFile + FILE_ENDING_WITH_DOT);
				}
				return selectedFile;
			}

			private Optional<String> getExtensionByStringHandling(String filename) {
				return Optional.ofNullable(filename).filter(f -> f.contains(".")).map(f -> f.substring(filename.lastIndexOf('.') + 1));
			}

			private JFileChooser createSaveChooser() {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				FileFilter fileFilter = new FileNameExtensionFilter(FILE_ENDING_WITH_DOT, FILE_ENDING);
				chooser.setApproveButtonText("Save");
				chooser.setDialogTitle("Save");
				chooser.setFileFilter(fileFilter);

				return chooser;
			}
		});
		return save;
	}

	private JComponent createLoadButton() {
		JButton load = new JButton("Load Selection");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = createLoadChooser();
				int response = chooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					List<DeviceOperationResultTO> inputTOs = readTOs(new File(chooser.getSelectedFile().getAbsolutePath()));
					inputTOs.forEach(to -> to.setRemoveButton(createRemoveButton()));
					resultEventList.clear();
					selectionModel.clearSelection();
					resultEventList.addAll(inputTOs);
				}
			}

			private JFileChooser createLoadChooser() {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				FileFilter fileFilter = new FileNameExtensionFilter(FILE_ENDING_WITH_DOT, FILE_ENDING);
				chooser.setFileFilter(fileFilter);
				chooser.setApproveButtonText("Load");
				chooser.setDialogTitle("Load");
				return chooser;
			}

			@SuppressWarnings("unchecked")
			private List<DeviceOperationResultTO> readTOs(File file) {
				List<DeviceOperationResultTO> inputTOs = new ArrayList<>();
				try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
					inputTOs = (List<DeviceOperationResultTO>) ois.readObject();
				} catch (IOException | ClassNotFoundException e1) {
					showErrorDialog(e1, e1.getMessage());
				}
				return inputTOs;
			}
		});
		return load;
	}

	private JComponent createAddButton() {
		JButton add = new JButton("+");
		add.addActionListener(l -> {
			DeviceOperationResultTO newItem = new DeviceOperationResultTO(getSelectedItem(operations), getSelectedItem(devices), createRemoveButton());
			resultEventList.add(newItem);
			int newIndex = resultEventList.indexOf(newItem);
			selectionModel.setSelectionInterval(newIndex, newIndex);
		});
		return add;
	}

	private JButton createRemoveButton() {
		JButton remove = new JButton("-");
		remove.addActionListener(l -> {
			resultEventList.remove(selectionModel.getSelected().get(0));
			if (!resultEventList.isEmpty()) {
				selectionModel.setSelectionInterval(0, 0);
			}
		});
		return remove;
	}

	private JComponent createWriteButton() {
		JButton write = new JButton("Write");
		write.addActionListener(new WriteAction());
		return write;
	}

	private <T> T getSelectedItem(JComboBox<T> cb) {
		return cb.getItemAt(cb.getSelectedIndex());
	}

	private final class WriteAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (writeInput.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "No input value specified!", "Write input field empty", JOptionPane.ERROR_MESSAGE);
				return;
			}
			ModbusTCPWriter modbusWriter = writerFromAdressfield();
			try {
				modbusWriter.writeOperationFromDevice(getSelectedItem(operations), getSelectedItem(devices), Integer.parseInt(writeInput.getText().trim()));
				JOptionPane.showMessageDialog(frame, "Write successful!");
			} catch (ModbusSlaveException e1) {
				showErrorDialog(e1, "The Device doesn't support this operation!\n" + e1.getMessage());
			} catch (NumberFormatException e2) {
				showErrorDialog(e2, "Only numbers are allowed!\n" + e2.getMessage());
			} catch (Exception e3) {
				showErrorDialog(e3, e3.getMessage());
			}
		}

		private ModbusTCPWriter writerFromAdressfield() {
			return new ModbusTCPWriter(ipAddress.getText().trim(), Integer.parseInt(port.getText().trim()));
		}
	}

	private void showErrorDialog(Exception e, String msg) {
		JOptionPane.showMessageDialog(frame, msg, e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
	}

	private final class ReadAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			modbusReader = readerFromAddressfield();
			for (int i = 0; i < resultEventList.size(); i++) {
				DeviceOperationResultTO result = resultEventList.get(i);
				result.setZeit(LocalDateTime.now());
				result.setErgebnis(readModbus(result.getOperation(), result.getModbusDevice()));
				resultEventList.set(i, result);
				try {
					/*
					 * Bad Workaround: Modbus Devices sometimes have Problems answering too many
					 * requests at once.
					 */
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					showErrorDialog(e1, e1.getMessage());
				}
			}
		}

		private String readModbus(ModbusOperation modbusOperation, ModbusDevice modbusDevice) {
			try {
				ModbusResultInt modbusResult = modbusReader.readOperationFromDevice(modbusOperation, modbusDevice);
				return modbusResult.getValueOfOperationWithUnit();
			} catch (ModbusSlaveException e1) {
				return "Device doesn't support Operation: " + e1.getMessage();
			} catch (ModbusException e1) {
				return "ModbusException: " + e1.getMessage();
			}
		}

		private ModbusTCPReader readerFromAddressfield() {
			return new ModbusTCPReader(ipAddress.getText().trim(), Integer.parseInt(port.getText().trim()));
		}
	}
}
