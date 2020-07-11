package de.ossi.modbustcp.gui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.SolarizedDarkTheme;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.connection.ModbusTCPWriter;
import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;

/**
 * Example Programm with Swing GUI
 * 
 * @author ossi
 *
 */
public class ModbusTCPGUI {

	private static final String FILE_ENDING = "modbustcp";
	private static final String FILE_ENDING_WITH_DOT = "." + FILE_ENDING;
	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;
	private static final CellConstraints CC = new CellConstraints();

	private static final String GITHUB_URL = "https://github.com/CommentSectionScientist/modbustcp";
	private final JFrame frame;
	private ModbusTCPReader modbusReader;
	private ModbusTCPWriter modbusWriter;

	private JTextField ipAddress;
	private JTextField port;
	private JTextField writeInput;
	private JComboBox<ModbusOperation> operations;
	private JComboBox<ModbusDevice> devices;
	private EventList<DeviceOperationResultTO> resultEventList;
	private DefaultEventSelectionModel<DeviceOperationResultTO> selectionModel;
	private JTable modbusOperationDeviceTable;

	private AdvancedTableModel<DeviceOperationResultTO> createModel() {
		resultEventList = new BasicEventList<>();
		return GlazedListsSwing.eventTableModel(resultEventList, new DeviceOperationResultTableFormat());
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(ModbusTCPGUI::new);
	}

	public ModbusTCPGUI() {
		// Laf has to be set first
		setLookAndFeel();
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
		return new ImageIcon(frame.getClass().getResource("/SolarPanel.png")).getImage();
	}

	private JPanel createTopPanel() {
		FormLayout layout = new FormLayout("3dlu,300dlu:g,8dlu,300dlu:g,3dlu,p,3dlu", "3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,p:g");
		PanelBuilder builder = new PanelBuilder(layout, new FormDebugPanel());
		builder.add(createIpAdressLabel(), CC.xy(2, 2));
		builder.add(createPortLabel(), CC.xy(4, 2));
		builder.add(createIpAdressField(), CC.xy(2, 4));
		builder.add(createPortField(), CC.xy(4, 4));
		builder.add(createOperationLabel(), CC.xy(2, 6));
		builder.add(createOperationsCombobox(), CC.xy(2, 8));
		builder.add(createDeviceLabel(), CC.xy(4, 6));
		builder.add(createDevicesCombobox(), CC.xy(4, 8));
		builder.add(createAddButton(), CC.xy(6, 8));
		builder.add(createTabbedPane(), CC.xywh(2, 10, 3, 4));
		builder.add(createMoveUpButton(), CC.xy(6, 10));
		builder.add(createMoveDownButton(), CC.xy(6, 12));
		return builder.getPanel();
	}

	private JTabbedPane createTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Read", createReadPanel());
		pane.addTab("Write", createWritePanel());
		return pane;
	}

	private JPanel createReadPanel() {
		FormLayout layout = new FormLayout("3dlu,122dlu:g,3dlu,122dlu:g,5dlu,122dlu:g,3dlu,122dlu:g", "3dlu,200dlu,3dlu,p,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.add(createTablePane(), CC.xyw(2, 2, 7));

		// builder.add(createAddButton(), CC.xy(2, 4));
		builder.add(createRemoveButton(), CC.xy(4, 4));
		builder.add(createSaveButton(), CC.xy(6, 4));
		builder.add(createLoadButton(), CC.xy(8, 4));

		builder.add(createReadButton(), CC.xyw(4, 6, 3));
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
		modbusOperationDeviceTable = new JTable(tableModel);
		TableColumnModel columnModel = modbusOperationDeviceTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(235);
		columnModel.getColumn(1).setPreferredWidth(235);
		columnModel.getColumn(2).setPreferredWidth(90);
		columnModel.getColumn(3).setPreferredWidth(340);
		modbusOperationDeviceTable.setColumnSelectionAllowed(false);
		selectionModel = new DefaultEventSelectionModel<>(resultEventList);
		selectionModel.setSelectionMode(DefaultEventSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		modbusOperationDeviceTable.setSelectionModel(selectionModel);
		return new JScrollPane(modbusOperationDeviceTable);
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
		operations = new JComboBox<>(registerSortedOperations());
		return operations;
	}

	private ModbusOperation[] registerSortedOperations() {
		List<ModbusOperation> allOperations = ModbusOperation.allOperations();
		Collections.sort(allOperations, Comparator.comparing(ModbusOperation::getAddress));
		return allOperations.toArray(new ModbusOperation[allOperations.size()]);
	}

	private JComponent createDevicesCombobox() {
		devices = new JComboBox<>(allDevices());
		return devices;
	}

	private ModbusDevice[] allDevices() {
		List<ModbusDevice> allDevices = ModbusDevice.allDevices();
		return allDevices.toArray(new ModbusDevice[allDevices.size()]);
	}

	private JComponent createReadButton() {
		JButton read = new JButton("Read All");
		read.addActionListener(new ReadAllAction());
		return read;
	}

	private JComponent createRemoveButton() {
		JButton remove = new JButton("Remove Selected");
		remove.addActionListener(l -> {
			List<DeviceOperationResultTO> tosToRemove = Arrays.stream(modbusOperationDeviceTable.getSelectedRows()).boxed().map(id -> resultEventList.get(id))
					.collect(Collectors.toList());
			tosToRemove.stream().forEach(resultEventList::remove);
			if (!resultEventList.isEmpty()) {
				selectionModel.setSelectionInterval(0, 0);
			}
		});
		return remove;
	}

	private JComponent createMoveDownButton() {
		JButton moveUp = new BasicArrowButton(BasicArrowButton.SOUTH);
		moveUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventList<DeviceOperationResultTO> selectedItems = selectionModel.getSelected();
				if (selectedItems.isEmpty()) {
					return;
				}
				moveItemUp(selectedItems.get(0));
			}

			private void moveItemUp(DeviceOperationResultTO item) {
				int indexOld = resultEventList.indexOf(item);
				if (indexOld == resultEventList.size() - 1) {
					return;
				}
				resultEventList.remove(indexOld);
				int indexNew = indexOld + 1;
				resultEventList.add(indexNew, item);
				selectionModel.setSelectionInterval(indexNew, indexNew);
			}
		});
		return moveUp;
	}

	private JComponent createMoveUpButton() {
		JButton moveUp = new BasicArrowButton(BasicArrowButton.NORTH);
		moveUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventList<DeviceOperationResultTO> selectedItems = selectionModel.getSelected();
				if (selectedItems.isEmpty()) {
					return;
				}
				moveItemUp(selectedItems.get(0));
			}

			private void moveItemUp(DeviceOperationResultTO item) {
				int indexOld = resultEventList.indexOf(item);
				if (indexOld == 0) {
					return;
				}
				resultEventList.remove(indexOld);
				int indexNew = indexOld - 1;
				resultEventList.add(indexNew, item);
				selectionModel.setSelectionInterval(indexNew, indexNew);
			}
		});
		return moveUp;
	}

	private JComponent createSaveButton() {
		JButton save = new JButton("Save Selection");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<DeviceOperationResultTO> tosToSave = resultEventList.stream().collect(Collectors.toList());
				if (!tosToSave.isEmpty()) {
					// Remove old Times/Results for TOs
					tosToSave.stream().forEach(to -> to.setErgebnis(null));
					tosToSave.stream().forEach(to -> to.setZeit(null));
					serializeTOs(tosToSave);
				}
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
					return new File(selectedFile.toString() + FILE_ENDING_WITH_DOT);
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
					resultEventList.clear();
					inputTOs.forEach(resultEventList::add);
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
			DeviceOperationResultTO newItem = new DeviceOperationResultTO(getSelectedItem(operations), getSelectedItem(devices));
			resultEventList.add(newItem);
			int newIndex = resultEventList.indexOf(newItem);
			selectionModel.setSelectionInterval(newIndex, newIndex);
		});
		return add;
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
			modbusWriter = writerFromAdressfield();
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
				return new StringBuilder().append("Device doesn't support Operation: ").append(e1.getMessage()).toString();
			} catch (ModbusException e1) {
				return new StringBuilder().append("ModbusException").append(e1.getMessage()).toString();
			}
		}

		private ModbusTCPReader readerFromAddressfield() {
			return new ModbusTCPReader(ipAddress.getText().trim(), Integer.parseInt(port.getText().trim()));
		}
	}
}
