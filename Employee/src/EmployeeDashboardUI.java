import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDashboardUI extends JFrame {
    private List<Employee> employees = new ArrayList<>();
    private JTextField idField, nameField, deptField, salaryField, searchField, leaveCountField;
    private DefaultTableModel tableModel;

    public EmployeeDashboardUI(String title) {
        setTitle(title);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 245, 255));
        setContentPane(mainPanel);

        // Header with gradient background
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));  // Blue color
        JLabel headerLabel = new JLabel(title);
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 26));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Input section with customized colors
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));  // Added one more row for Leave Count
        inputPanel.setBackground(new Color(240, 255, 255));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Employee Details"));

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        salaryField = new JTextField();
        searchField = new JTextField();
        leaveCountField = new JTextField();

        inputPanel.add(new JLabel("ID:")); inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Department:")); inputPanel.add(deptField);
        inputPanel.add(new JLabel("Salary:")); inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Leave Count:")); inputPanel.add(leaveCountField);  // Added leave count input
        inputPanel.add(new JLabel("Search by ID:")); inputPanel.add(searchField);

        // Button panel with modern effects and animations
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 255, 250));
        String[] labels = {"➕ Add", "📋 View All", "🔍 Search", "✏️ Update", "🗑️ Delete", "❌ Exit"};

        for (String label : labels) {
            JButton btn = new JButton(label);
            btn.setPreferredSize(new Dimension(150, 40));
            btn.setBackground(new Color(100, 149, 237));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            buttonPanel.add(btn);

            // Button hover animation
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(70, 130, 180));  // Hover effect
                    btn.setFont(new Font("Segoe UI", Font.BOLD, 16));  // Slight font enlargement for hover
                }
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(100, 149, 237));  // Default color
                    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Reset font size
                }
            });

            // Button action listeners
            switch (label) {
                case "➕ Add": btn.addActionListener(e -> addEmployee()); break;
                case "📋 View All": btn.addActionListener(e -> viewEmployees()); break;
                case "🔍 Search": btn.addActionListener(e -> searchEmployee()); break;
                case "✏️ Update": btn.addActionListener(e -> updateEmployee()); break;
                case "🗑️ Delete": btn.addActionListener(e -> deleteEmployee()); break;
                case "❌ Exit": btn.addActionListener(e -> System.exit(0)); break;
            }
        }

        // Table with alternating row colors and header customization
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Salary", "Leave Count"}, 0);  // Added Leave Count column
        JTable table = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                c.setBackground(row % 2 == 0 ? new Color(224, 255, 255) : Color.WHITE);  // Alternating row colors
                return c;
            }
        };
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 220, 255));
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(100, 149, 237));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(new Color(230, 245, 255));
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        JPanel inputWithButtons = new JPanel(new BorderLayout());
        inputWithButtons.setBackground(new Color(240, 255, 255));
        inputWithButtons.add(inputPanel, BorderLayout.CENTER);
        inputWithButtons.add(buttonPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(230, 245, 255));
        centerPanel.add(inputWithButtons, BorderLayout.NORTH);
        centerPanel.add(tableScroll, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    // Add Employee Method
    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String dept = deptField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            int leaveCount = Integer.parseInt(leaveCountField.getText());

            Employee newEmployee = new Employee(id, name, dept, salary, leaveCount);
            employees.add(newEmployee);
            tableModel.addRow(newEmployee.toRow());

            JOptionPane.showMessageDialog(this, "✅ Employee Added!");
            clearFields();  // Add this method to clear input fields
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid input! Please check your input values.");
        }
    }

    // View Employees Method
    private void viewEmployees() {
        tableModel.setRowCount(0); // Clear existing table rows
        for (Employee employee : employees) {
            tableModel.addRow(employee.toRow());
        }
    }

    // Update Employee Method
    private void updateEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            for (Employee e : employees) {
                if (e.id == id) {
                    e.name = nameField.getText();
                    e.department = deptField.getText();
                    e.salary = Double.parseDouble(salaryField.getText());
                    e.leaveCount = Integer.parseInt(leaveCountField.getText());

                    viewEmployees();  // Refresh table view
                    JOptionPane.showMessageDialog(this, "✅ Employee Updated!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ Employee Not Found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error Updating.");
        }
    }

    // Delete Employee Method
    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(searchField.getText());
            boolean removed = employees.removeIf(e -> e.id == id);
            if (removed) {
                viewEmployees();  // Refresh table after deletion
                JOptionPane.showMessageDialog(this, "🗑️ Employee Deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Employee Not Found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid ID.");
        }
    }

    // Search Employee Method
    private void searchEmployee() {
        try {
            int id = Integer.parseInt(searchField.getText());
            for (Employee e : employees) {
                if (e.id == id) {
                    idField.setText(String.valueOf(e.id));
                    nameField.setText(e.name);
                    deptField.setText(e.department);
                    salaryField.setText(String.valueOf(e.salary));
                    leaveCountField.setText(String.valueOf(e.leaveCount));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ Employee Not Found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid ID.");
        }
    }

    // Clear Input Fields Method
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        salaryField.setText("");
        leaveCountField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeDashboardUI("Employee Management System").setVisible(true));
    }
}
