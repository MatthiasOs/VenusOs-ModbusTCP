package de.ossi.wolfsbau.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.JAXBException;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.ossi.wolfsbau.modbus.ModbusTCPReader;
import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperation;

public class WolfsbauGUI extends JFrame {

	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;
	private static final long serialVersionUID = 1L;
	private static final String SPALTEN = "4dlu,170dlu,8dlu,215dlu,8dlu,50dlu,4dlu";
	private static final String ZEILEN = "4dlu,p,3dlu,p,3dlu,p,3dlu,p,8dlu,p,3dlu,100dlu,4dlu";
	private ModbusTCPReader modbusReader;

	private JTextField ipAdress;
	private JTextField port;
	private JTextArea output;
	private JComboBox<ModbusOperation> operations;
	private JComboBox<ModbusDevice> devices;

	public static void main(String[] args) throws IOException, JAXBException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new WolfsbauGUI();
			}
		});
	}

	public WolfsbauGUI() {
		this.add(createPanel());
		setIcon();
		setLookAndFeel();
		setTitle("WolfsbauGUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
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
		builder.add(createReadButton(), c.xy(6, 8));
		builder.add(createOutputLabel(), c.xy(2, 10));
		builder.add(createOutputArea(), c.xyw(2, 12, 5));
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.ORANGE);
		return panel;
	}

	private JComponent createOutputLabel() {
		JLabel outputLabel = new JLabel("Output:");
		return outputLabel;
	}

	private JComponent createOperationLabel() {
		JLabel operationLabel = new JLabel("Operation:");
		return operationLabel;
	}

	private JComponent createIpAdressLabel() {
		JLabel adressLabel = new JLabel("Ip-Adresse:");
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
		ipAdress = new JTextField(IP_VICTRON);
		return ipAdress;
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
				return Integer.compare(o1.address, o2.address);
			}
		});
		return operations.toArray(new ModbusOperation[operations.size()]);
	}

	private JComponent createDevicesCombobox() {
		devices = new JComboBox<>(unitIdSortedDevices());
		return devices;
	}

	private ModbusDevice[] unitIdSortedDevices() {
		List<ModbusDevice> devices = ModbusDevice.allDevices();
		Collections.sort(devices, new Comparator<ModbusDevice>() {
			@Override
			public int compare(ModbusDevice o1, ModbusDevice o2) {
				return Integer.compare(o1.unitId, o2.unitId);
			}
		});
		return devices.toArray(new ModbusDevice[devices.size()]);
	}

	private JComponent createReadButton() {
		JButton read = new JButton("Read");
		read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				output.append(createAnfrage());
				ModbusResultInt result;
				try {
					modbusReader = new ModbusTCPReader(ipAdress.getText().trim(), Integer.parseInt(port.getText().trim()));
					result = modbusReader.readOperationFromDevice(selectedOperation(), selectedDevice());
					output.append(createResultAntwort(result));
				} catch (ModbusSlaveException e1) {
					output.append(createAntwort("Das angegebene Gerät, unterstützt die Operation nicht!"));
				} catch (ModbusException e1) {
					output.append(createExceptionAntwort(e1));
				}
			}
		});
		return read;
	}

	private String createAntwort(String result) {
		StringBuilder antwort = new StringBuilder();
		antwort.append(">>> Antwort:");
		antwort.append(System.lineSeparator());
		antwort.append(result);
		antwort.append("<<<");
		antwort.append(System.lineSeparator());
		return antwort.toString();
	}

	private String createExceptionAntwort(ModbusException e) {
		return createAntwort(e.toString());
	}

	private String createResultAntwort(ModbusResultInt result) {
		return createAntwort(result.toString());
	}

	private String createAnfrage() {
		StringBuilder anfrage = new StringBuilder();
		anfrage.append(">>> ");
		anfrage.append(LocalDateTime.now());
		anfrage.append(" Anfrage:");
		anfrage.append(System.lineSeparator());
		anfrage.append("Gerät: ");
		anfrage.append(selectedDevice().toString());
		anfrage.append(System.lineSeparator());
		anfrage.append("Register: ");
		anfrage.append(selectedOperation().address);
		anfrage.append(System.lineSeparator());
		anfrage.append("Operation: ");
		anfrage.append(selectedOperation().description);
		anfrage.append(System.lineSeparator());
		anfrage.append("<<<");
		anfrage.append(System.lineSeparator());
		anfrage.append(System.lineSeparator());
		return anfrage.toString();
	}

	private JComponent createOutputArea() {
		output = new JTextArea(10, 70);
		output.setEditable(false);
		JScrollPane scrollpane = new JScrollPane(output);
		return scrollpane;
	}

	private ModbusDevice selectedDevice() {
		return devices.getItemAt(devices.getSelectedIndex());
	}

	private ModbusOperation selectedOperation() {
		return operations.getItemAt(operations.getSelectedIndex());
	}
}
