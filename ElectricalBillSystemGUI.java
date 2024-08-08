import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Customer {
    String name;
    String address;
    String billnum;
    String type; // "Residential" or "Commercial"

    public Customer(String name, String address, String billnum, String type) {
        this.name = name;
        this.address = address;
        this.billnum = billnum;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBillnum() {
        return billnum;
    }

    public String getType() {
        return type;
    }
}

class ElectricityBill {
    Customer customer;
    double usage; // in kilowatt-hours
    double totalAmount;

    public ElectricityBill(Customer customer, double usage) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (usage < 0) {
            throw new IllegalArgumentException("Usage cannot be negative");
        }
        this.customer = customer;
        this.usage = usage;
        this.totalAmount = calculateBill();
    }

    public double calculateBill() {
        double energyCharge = 0;
        double fixedCharge = 0;

        // Determine energy charge and fixed charge based on customer type and
        // consumption range
        if (customer.getType().equalsIgnoreCase("R")) { // Residential
            if (usage <= 60) {
                energyCharge = 7.85;
                fixedCharge = 0;
            } else if (usage <= 90) {
                energyCharge = 10.00;
                fixedCharge = 90.00;
            } else if (usage <= 120) {
                energyCharge = 27.75;
                fixedCharge = 480.00;
            } else if (usage > 180) {
                energyCharge = 40.00;
                fixedCharge = 500.00;
            }
        } else if (customer.getType().equalsIgnoreCase("C")) { // Commercial
            if (usage <= 60) {
                energyCharge = 15.7;
                fixedCharge = 0;
            } else if (usage <= 90) {
                energyCharge = 20.00;
                fixedCharge = 180.00;
            } else if (usage <= 120) {
                energyCharge = 55.50;
                fixedCharge = 960.00;
            } else if (usage <= 180) {
                energyCharge = 64.00;
                fixedCharge = 960.00;
            } else if (usage > 180) {
                energyCharge = 80.00;
                fixedCharge = 1000.00;
            }
        }

        // Calculate total amount
        return (usage * energyCharge) + fixedCharge;
    }

}

public class ElectricalBillSystemGUI extends JFrame implements ActionListener {
    private JTextField nameField, addressField, billnumField, typeField, usageField;
    private JButton calculateButton;
    private JLabel resultLabel;

    public ElectricalBillSystemGUI() {
        setTitle("Electricity Bill System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Customer Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Bill Number:"));
        billnumField = new JTextField();
        add(billnumField);

        add(new JLabel("Customer Type (Residential (R) / Commercial (C):"));
        typeField = new JTextField();
        add(typeField);

        add(new JLabel("Electricity Usage (kWh):"));
        usageField = new JTextField();
        add(usageField);

        calculateButton = new JButton("Calculate Bill");
        calculateButton.addActionListener(this);
        add(calculateButton);

        resultLabel = new JLabel();
        add(resultLabel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            String name = nameField.getText();
            String address = addressField.getText();
            String billnum = billnumField.getText();
            String type = typeField.getText();
            double usage = Double.parseDouble(usageField.getText());

            Customer customer = new Customer(name, address, billnum, type);
            ElectricityBill bill = new ElectricityBill(customer, usage);
            resultLabel.setText("Total Amount: Rs " + bill.totalAmount);
        }
    }

    public static void main(String[] args) {
        new ElectricalBillSystemGUI();
    }
}
