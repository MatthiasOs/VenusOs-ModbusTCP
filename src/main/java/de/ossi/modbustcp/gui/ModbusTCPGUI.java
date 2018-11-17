package de.ossi.modbustcp.gui;

import java.awt.Color;
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
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
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
public class ModbusTCPGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String FILE_ENDING = "modbustcp";
	private static final String FILE_ENDING_WITH_DOT = "." + FILE_ENDING;
	private static final Color LIGHT_BLUE = new Color(155, 200, 255);
	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;

	private static final String GITHUB_URL = "https://github.com/CommentSectionScientist/modbustcp";
	private ModbusTCPReader modbusReader;
	private ModbusTCPWriter modbusWriter;

	private JTextField ipAddress;
	private JTextField port;
	private JTextField writeInput;
	private JComboBox<ModbusOperation> operations;
	private JComboBox<ModbusDevice> devices;
	private EventList<DeviceOperationResultTO> resultEventList;
	private JTable modbusOperationDeviceTable;

	private AdvancedTableModel<DeviceOperationResultTO> createModel() {
		resultEventList = new BasicEventList<>();
		return GlazedListsSwing.eventTableModel(resultEventList, new DeviceOperationResultTableFormat());
	}

	public static void main(String[] args) throws IOException, JAXBException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ModbusTCPGUI();
			}
		});
	}

	public ModbusTCPGUI() {
		this.setJMenuBar(createMenu());
		this.add(createTopPanel());
		setIcon();
		setLookAndFeel();
		setTitle("ModbusTCP");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
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
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			showErrorDialog(e, e.getMessage());
		}
	}

	private void setIcon() {
		Image img = new ImageIcon(this.getClass().getResource("/SolarPanel.png")).getImage();
		this.setIconImage(img);
	}

	private JPanel createTopPanel() {
		FormLayout layout = new FormLayout("3dlu,250dlu,8dlu,250dlu", "3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints c = new CellConstraints();
		builder.add(createIpAdressLabel(), c.xy(2, 2));
		builder.add(createPortLabel(), c.xy(4, 2));
		builder.add(createIpAdressField(), c.xy(2, 4));
		builder.add(createPortField(), c.xy(4, 4));
		builder.add(createOperationLabel(), c.xy(2, 6));
		builder.add(createOperationsCombobox(), c.xy(2, 8));
		builder.add(createDeviceLabel(), c.xy(4, 6));
		builder.add(createDevicesCombobox(), c.xy(4, 8));
		builder.add(createTabbedPane(), c.xyw(2, 10, 3));
		return getBluePanel(builder);
	}

	private JTabbedPane createTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Read", createReadPanel());
		pane.addTab("Write", createWritePanel());
		return pane;
	}

	private JPanel createReadPanel() {
		FormLayout layout = new FormLayout("3dlu,122dlu,3dlu,122dlu,5dlu,122dlu,3dlu,122dlu", "3dlu,200dlu,3dlu,p,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints c = new CellConstraints();
		builder.add(createTablePane(), c.xyw(2, 2, 7));

		builder.add(createAddButton(), c.xy(2, 4));
		builder.add(createRemoveButton(), c.xy(4, 4));
		builder.add(createSaveButton(), c.xy(6, 4));
		builder.add(createLoadButton(), c.xy(8, 4));

		builder.add(createReadButton(), c.xyw(4, 6, 3));
		return getBluePanel(builder);
	}

	private JPanel createWritePanel() {
		FormLayout layout = new FormLayout("3dlu,122dlu,3dlu,122dlu", "3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints c = new CellConstraints();
		builder.add(createWriteInputLabel(), c.xy(2, 2));
		builder.add(createWriteInputField(), c.xy(2, 4));
		builder.add(createWriteButton(), c.xy(4, 4));
		return getBluePanel(builder);
	}

	private JPanel getBluePanel(PanelBuilder builder) {
		JPanel panel = builder.getPanel();
		panel.setBackground(LIGHT_BLUE);
		return panel;
	}

	private JComponent createTablePane() {
		AdvancedTableModel<DeviceOperationResultTO> tableModel = createModel();
		modbusOperationDeviceTable = new JTable(tableModel);
		modbusOperationDeviceTable.setColumnSelectionAllowed(false);
		return new JScrollPane(modbusOperationDeviceTable);
	}

	private JComponent createWriteInputLabel() {
		JLabel inputLabel = new JLabel("Input Value:");
		return inputLabel;
	}

	private JComponent createOperationLabel() {
		JLabel operationLabel = new JLabel("Operation:");
		return operationLabel;
	}

	private JComponent createIpAdressLabel() {
		JLabel adressLabel = new JLabel("IP-Address:");
		return adressLabel;
	}

	private JComponent createPortLabel() {
		JLabel portLabel = new JLabel("Port:");
		return portLabel;
	}

	private JComponent createDeviceLabel() {
		JLabel deviceLabel = new JLabel("Device:");
		return deviceLabel;
	}

	private JComponent createIpAdressField() {
		ipAddress = new JTextField(IP_VICTRON);
		return ipAddress;
	}

	private JComponent createWriteInputField() {
		writeInput = new JTextField();
		return writeInput;
	}

	private JComponent createPortField() {
		port = new JTextField(String.valueOf(MODBUS_DEFAULT_PORT));
		return port;
	}

	private JComponent createOperationsCombobox() {
		operations = new JComboBox<>(registerSortedOperations());
		return operations;
	}

	private ModbusOperation[] registerSortedOperations() {
		List<ModbusOperation> operations = ModbusOperation.allOperations();
		Collections.sort(operations, new Comparator<ModbusOperation>() {
			@Override
			public int compare(ModbusOperation o1, ModbusOperation o2) {
				return Integer.compare(o1.getAddress(), o2.getAddress());
			}
		});
		return operations.toArray(new ModbusOperation[operations.size()]);
	}

	private JComponent createDevicesCombobox() {
		devices = new JComboBox<>(allDevices());
		return devices;
	}

	private ModbusDevice[] allDevices() {
		List<ModbusDevice> devices = ModbusDevice.allDevices();
		return devices.toArray(new ModbusDevice[devices.size()]);
	}

	private JComponent createReadButton() {
		JButton read = new JButton("Read All");
		read.addActionListener(new ReadAllAction());
		return read;
	}

	private JComponent createRemoveButton() {
		JButton remove = new JButton("Remove Selected");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<DeviceOperationResultTO> tosToRemove = Arrays.stream(modbusOperationDeviceTable.getSelectedRows()).boxed().map(id -> resultEventList.get(id))
						.collect(Collectors.toList());
				if (!tosToRemove.isEmpty()) {
					tosToRemove.stream().forEach((to) -> resultEventList.remove(to));
					modbusOperationDeviceTable.clearSelection();
				}
			}

		});
		return remove;
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
				if (!FilenameUtils.getExtension(selectedFile.getName()).equalsIgnoreCase(FILE_ENDING)) {
					return new File(selectedFile.toString() + FILE_ENDING_WITH_DOT);
				}
				return selectedFile;
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
		JButton add = new JButton("Add Selected");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultEventList.add(new DeviceOperationResultTO(getSelectedItem(operations), getSelectedItem(devices)));
				modbusOperationDeviceTable.clearSelection();
			}
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
				JOptionPane.showMessageDialog(ModbusTCPGUI.this, "No input value specified!", "Write input field empty", JOptionPane.ERROR_MESSAGE);
				return;
			}
			modbusWriter = writerFromAdressfield();
			try {
				modbusWriter.writeOperationFromDevice(getSelectedItem(operations), getSelectedItem(devices), Integer.parseInt(writeInput.getText().trim()));
				JOptionPane.showMessageDialog(ModbusTCPGUI.this, "Write successful!");
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
		JOptionPane.showMessageDialog(ModbusTCPGUI.this, msg, e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
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
				return String.valueOf(modbusResult.ermittleWertMitEinheit());
			} catch (ModbusSlaveException e1) {
				return "Device doesn't support Operation";
			} catch (ModbusException e1) {
				return "ModbusException";
			}
		}

		private ModbusTCPReader readerFromAddressfield() {
			return new ModbusTCPReader(ipAddress.getText().trim(), Integer.parseInt(port.getText().trim()));
		}

	}

}
