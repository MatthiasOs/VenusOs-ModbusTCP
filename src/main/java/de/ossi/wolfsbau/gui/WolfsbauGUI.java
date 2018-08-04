package de.ossi.wolfsbau.gui;

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
import java.time.format.DateTimeFormatter;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.JAXBException;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import de.ossi.wolfsbau.modbus.ModbusTCPReader;
import de.ossi.wolfsbau.modbus.ModbusTCPWriter;
import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperation;

public class WolfsbauGUI extends JFrame {

	private static final String FILE_ENDING = ".wolfsbau";
	private static final Color LIGHT_BLUE = new Color(155, 200, 255);
	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;
	private static final long serialVersionUID = 1L;
	private static final String SPALTEN = "3dlu,123dlu,3dlu,123dlu,8dlu,123dlu,3dlu,123dlu,1dlu";
	private static final String ZEILEN = "4dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,200dlu,3dlu,p,4dlu";
	private static final String GITHUB_URL = "https://github.com/CommentSectionScientist/wolfsbau";
	private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
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
				new WolfsbauGUI();
			}
		});
	}

	public WolfsbauGUI() {
		this.setJMenuBar(createMenu());
		this.add(createPanel());
		setIcon();
		setLookAndFeel();
		setTitle("WolfsbauGUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
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

	private JPanel createPanel() {
		FormLayout layout = new FormLayout(SPALTEN, ZEILEN);
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints c = new CellConstraints();
		builder.add(createIpAdressLabel(), c.xy(2, 2));
		builder.add(createPortLabel(), c.xy(6, 2));
		builder.add(createIpAdressField(), c.xyw(2, 4, 3));
		builder.add(createPortField(), c.xyw(6, 4, 3));
		builder.add(createOperationLabel(), c.xyw(2, 6, 3));
		builder.add(createOperationsCombobox(), c.xyw(2, 8, 3));
		builder.add(createDeviceLabel(), c.xy(6, 6));
		builder.add(createDevicesCombobox(), c.xyw(6, 8, 3));
		builder.add(createWriteInputLabel(), c.xy(6, 10));
		builder.add(createAddButton(), c.xy(2, 12));
		builder.add(createLoadButton(), c.xy(4, 12));
		builder.add(createWriteInputField(), c.xy(6, 12));
		builder.add(createWriteButton(), c.xy(8, 12));
		builder.add(createTablePane(), c.xyw(2, 14, 7));
		builder.add(createSaveButton(), c.xy(4, 16));
		builder.add(createRemoveButton(), c.xy(6, 16));
		builder.add(createReadButton(), c.xy(8, 16));
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
				File f = new File(FILE_DATE_FORMATTER.format(LocalDateTime.now()) + FILE_ENDING);
				try (FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					oos.writeObject(tosToSave);
					oos.flush();
				} catch (IOException e) {
					showErrorDialog(e, e.getMessage());
				}
				JOptionPane.showMessageDialog(WolfsbauGUI.this, "Selection saved as File:\n" + f.getAbsolutePath());
			}
		});
		return save;
	}

	private JComponent createLoadButton() {
		JButton load = new JButton("Load Selection");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
				int response = chooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					List<DeviceOperationResultTO> inputTOs = readTOs(new File(chooser.getSelectedFile().getAbsolutePath()));
					resultEventList.clear();
					inputTOs.forEach(resultEventList::add);
				}
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
		JButton add = new JButton("Add");
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
				JOptionPane.showMessageDialog(WolfsbauGUI.this, "No input value specified!", "Write input field empty", JOptionPane.ERROR_MESSAGE);
				return;
			}
			modbusWriter = writerFromAdressfield();
			try {
				modbusWriter.writeOperationFromDevice(getSelectedItem(operations), getSelectedItem(devices), Integer.parseInt(writeInput.getText().trim()));
				JOptionPane.showMessageDialog(WolfsbauGUI.this, "Write successful!");
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
		JOptionPane.showMessageDialog(WolfsbauGUI.this, msg, e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
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
