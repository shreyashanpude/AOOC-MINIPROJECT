
// Employee Class
class Employee {
    int id;
    String name;
    String department;
    double salary;
    int leaveCount;

    public Employee(int id, String name, String department, double salary, int leaveCount) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.leaveCount = leaveCount;
    }

    // Method to convert employee data to a row for the JTable
    public Object[] toRow() {
        return new Object[]{id, name, department, salary, leaveCount};
    }
}
