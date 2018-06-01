package de.ossi.wolfsbau.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperation;

public class WolfsbauGUI extends JFrame {

	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;
	private static final long serialVersionUID = 1L;
	private static final String SPALTEN = "3dlu,230dlu,8dlu,250dlu,8dlu,80dlu,1dlu";
	private static final String ZEILEN = "4dlu,p,3dlu,p,3dlu,p,3dlu,p,8dlu,p,200dlu,3dlu,p,4dlu";
	private static final String GITHUB_URL = "https://github.com/CommentSectionScientist/wolfsbau";
	private ModbusTCPReader modbusReader;

	private JTextField ipAddress;
	private JTextField port;
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
					e.printStackTrace();
				}
			}
		}
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void setIcon() {
		Image img = new ImageIcon(this.getClass().getResource("/icon-solarpower.png")).getImage();
		this.setIconImage(img);
	}

	private JPanel createPanel() {
		FormLayout layout = new FormLayout(SPALTEN, ZEILEN);
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
		builder.add(createAddButton(), c.xy(6, 8));
		builder.add(createTablePane(), c.xywh(2, 10, 3, 2));
		builder.add(createRemoveButton(), c.xy(6, 10));
		builder.add(createReadButton(), c.xy(4, 13));
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.ORANGE);
		return panel;
	}

	private JComponent createTablePane() {
		AdvancedTableModel<DeviceOperationResultTO> tableModel = createModel();
		modbusOperationDeviceTable = new JTable(tableModel);
		modbusOperationDeviceTable.setColumnSelectionAllowed(false);
		return new JScrollPane(modbusOperationDeviceTable);
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
				tosToRemove.stream().forEach((to) -> resultEventList.remove(to));
				modbusOperationDeviceTable.clearSelection();
			}
		});
		return remove;
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

	private <T> T getSelectedItem(JComboBox<T> cb) {
		return cb.getItemAt(cb.getSelectedIndex());
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
