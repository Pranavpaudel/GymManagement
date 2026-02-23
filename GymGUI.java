/**
 * GymGUI - Main graphical user interface for the Gym Management System
 * This class handles all UI components and user interactions for managing gym members
 * Features include: adding members, managing memberships, tracking attendance, and more
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class GymGUI extends JFrame implements ActionListener {
    // Collection to store all gym members
    private ArrayList<GymMember> gymMembers;

    // Text fields for member information input
    private JTextField txtId, txtName, txtLocation, txtPhone, txtEmail, txtDob, txtMembershipStartDate;
    private JTextField txtReferralSource, txtPaidAmount, txtRemovalReason, txtTrainerName;

    // Non-editable text fields for displaying prices and discounts
    private JTextField neTxtRegularPlanPrice, neTxtPremiumPlanPrice, neTxtDiscountPrice;

    // Labels for all input fields
    private JLabel lblId, lblName, lblLocation, lblPhone, lblEmail, lblDob, lblMembershipStartDate;
    private JLabel lblReferralSource, lblPaidAmount, lblRemovalReason, lblTrainerName, lblGender;
    private JLabel lblPlan, lblPremiumPrice, lblRegularPrice, lblDiscountPrice;

    // Radio buttons for gender selection
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;

    // Combo box for plan selection
    private JComboBox<String> cbPlan;

    // Buttons for various operations
    private JButton btnAddRegularMember, btnAddPremiumMember, btnActivateMembership, btnDeactivateMembership;
    private JButton btnMarkAttendance, btnUpgradePlan, btnCalculateDiscount;
    private JButton btnRevertRegularMember, btnRevertPremiumMember;
    private JButton btnPayDueAmount, btnDisplay, btnClear, btnSaveToFile, btnReadFromFile;

    /**
     * Constructor - Initializes the main GUI window and sets up all components
     */
    public GymGUI() {
        super("Gym Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setupLayout();
        addActionListeners();
        pack();  // Adjust frame size to fit all components
        setLocationRelativeTo(null);  // Center on screen
        setVisible(true);
    }

    /**
     * Initializes all GUI components including text fields, labels, buttons, and other controls
     * Sets up default values and placeholder text for date fields
     */
    private void initializeComponents() {
        // Initialize labels first
        lblId = new JLabel("ID: ");
        lblName = new JLabel("Name: ");
        lblLocation = new JLabel("Location: ");
        lblPhone = new JLabel("Phone No: ");
        lblEmail = new JLabel("Email: ");
        lblGender = new JLabel("Gender: ");
        lblDob = new JLabel("DOB: ");
        lblMembershipStartDate = new JLabel("Membership Start Date: ");
        lblReferralSource = new JLabel("Referral Source: ");
        lblPaidAmount = new JLabel("Paid Amount: ");
        lblRemovalReason = new JLabel("Removal Reason: ");
        lblTrainerName = new JLabel("Trainer's Name: ");
        lblPlan = new JLabel("Choose Plan: ");
        lblPremiumPrice = new JLabel("Premium plan price: ");
        lblRegularPrice = new JLabel("Regular plan price: ");
        lblDiscountPrice = new JLabel("Discount: ");

        // Initialize text fields
        txtId = new JTextField(10);
        txtName = new JTextField(10);
        txtLocation = new JTextField(10);
        txtPhone = new JTextField(10);
        txtEmail = new JTextField(10);
        txtDob = new JTextField("YYYY/MM/DD", 10);
        txtDob.setForeground(Color.GRAY);
        txtMembershipStartDate = new JTextField("YYYY/MM/DD", 10);
        txtMembershipStartDate.setForeground(Color.GRAY);
        txtReferralSource = new JTextField(10);
        txtPaidAmount = new JTextField(10);
        txtRemovalReason = new JTextField(10);
        txtTrainerName = new JTextField(10);

        // Initialize non-editable text fields
        neTxtRegularPlanPrice = new JTextField("4500", 10);
        neTxtPremiumPlanPrice = new JTextField("50000", 10);
        neTxtDiscountPrice = new JTextField(10);
        neTxtRegularPlanPrice.setEditable(false);
        neTxtPremiumPlanPrice.setEditable(false);
        neTxtDiscountPrice.setEditable(false);

        // Initialize radio buttons
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        // Initialize combo box
        String[] plans = {"Basic", "Standard", "Deluxe"};
        cbPlan = new JComboBox<>(plans);

        // Initialize buttons
        btnAddRegularMember = new JButton("Add Regular Member");
        btnAddPremiumMember = new JButton("Add Premium Member");
        btnActivateMembership = new JButton("Activate Membership");
        btnDeactivateMembership = new JButton("Deactivate Membership");
        btnMarkAttendance = new JButton("Mark Attendance");
        btnUpgradePlan = new JButton("Upgrade Plan");
        btnCalculateDiscount = new JButton("Calculate Discount");
        btnRevertRegularMember = new JButton("Revert Regular Member");
        btnRevertPremiumMember = new JButton("Revert Premium Member");
        btnPayDueAmount = new JButton("Pay Due Amount");
        btnDisplay = new JButton("Display");
        btnClear = new JButton("Clear");
        btnSaveToFile = new JButton("Save to File");
        btnReadFromFile = new JButton("Read from File");

        // Initialize ArrayList
        gymMembers = new ArrayList<>();

        // Add focus listeners for date fields
        txtDob.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDob.getText().equals("YYYY/MM/DD")) {
                    txtDob.setText("");
                    txtDob.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDob.getText().isEmpty()) {
                    txtDob.setText("YYYY/MM/DD");
                    txtDob.setForeground(Color.GRAY);
                }
            }
        });

        txtMembershipStartDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMembershipStartDate.getText().equals("YYYY/MM/DD")) {
                    txtMembershipStartDate.setText("");
                    txtMembershipStartDate.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtMembershipStartDate.getText().isEmpty()) {
                    txtMembershipStartDate.setText("YYYY/MM/DD");
                    txtMembershipStartDate.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Adds action listeners to all buttons in the GUI
     * This enables the buttons to respond to user clicks
     */
    private void addActionListeners() {
        btnAddRegularMember.addActionListener(this);
        btnAddPremiumMember.addActionListener(this);
        btnActivateMembership.addActionListener(this);
        btnDeactivateMembership.addActionListener(this);
        btnMarkAttendance.addActionListener(this);
        btnUpgradePlan.addActionListener(this);
        btnCalculateDiscount.addActionListener(this);
        btnRevertRegularMember.addActionListener(this);
        btnRevertPremiumMember.addActionListener(this);
        btnPayDueAmount.addActionListener(this);
        btnDisplay.addActionListener(this);
        btnClear.addActionListener(this);
        btnSaveToFile.addActionListener(this);
        btnReadFromFile.addActionListener(this);
    }

    /**
     * Main event handler for all button clicks
     * Handles different operations based on which button was clicked
     * Includes error handling and input validation
     * @param e The action event containing information about which button was clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnAddPremiumMember) {
                if (validateInputForPremium()) {
                    if (isDuplicateId(Integer.parseInt(txtId.getText()))) {
                        JOptionPane.showMessageDialog(null, "Member ID already exists!");
                        return;
                    }
                    addPremiumMember();
                    JOptionPane.showMessageDialog(null, "Premium member added successfully");
                }
            } else if (e.getSource() == btnAddRegularMember) {
                if (validateInputForRegular()) {
                    if (isDuplicateId(Integer.parseInt(txtId.getText()))) {
                        JOptionPane.showMessageDialog(null, "Member ID already exists!");
                        return;
                    }
                    addRegularMember();
                    JOptionPane.showMessageDialog(null, "Regular member added successfully");
                }
            } else if (e.getSource() == btnDisplay) {
                displayMembers();
            } else if (e.getSource() == btnClear) {
                clearFields();
            } else if (e.getSource() == btnActivateMembership) {
                if (validateId()) {
                    activateMembership();
                }
            } else if (e.getSource() == btnDeactivateMembership) {
                if (validateId()) {
                    deactivateMembership();
                }
            } else if (e.getSource() == btnMarkAttendance) {
                if (validateId()) {
                    markAttendance();
                }
            } else if (e.getSource() == btnUpgradePlan) {
                if (validateId()) {
                    upgradePlan();
                }
            } else if (e.getSource() == btnCalculateDiscount) {
                if (validateId()) {
                    calculateDiscount();
                }
            } else if (e.getSource() == btnRevertRegularMember) {
                if (validateId()) {
                    revertRegularMember();
                }
            } else if (e.getSource() == btnRevertPremiumMember) {
                if (validateId()) {
                    revertPremiumMember();
                }
            } else if (e.getSource() == btnPayDueAmount) {
                if (validateId() && validatePaidAmount()) {
                    payDueAmount();
                }
            } else if (e.getSource() == btnSaveToFile) {
                saveToFile();
            } else if (e.getSource() == btnReadFromFile) {
                readFromFile();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
        }
    }

    /**
     * Checks if a member ID already exists in the system
     * @param id The ID to check for duplicates
     * @return true if ID exists, false otherwise
     */
    private boolean isDuplicateId(int id) {
        for (GymMember member : gymMembers) {
            if (member.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates member ID input
     * Ensures ID field is not empty and contains a valid number
     * @return true if ID is valid, false otherwise
     */
    private boolean validateId() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Member ID");
                return false;
            }
            Integer.parseInt(txtId.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid Member ID");
            return false;
        }
    }

    /**
     * Validates payment amount input
     * Ensures amount is not empty and contains a valid number
     * @return true if amount is valid, false otherwise
     */
    private boolean validatePaidAmount() {
        try {
            if (txtPaidAmount.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter paid amount");
                return false;
            }
            Double.parseDouble(txtPaidAmount.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid paid amount");
            return false;
        }
    }

    /**
     * Validates date format and checks if it's a valid date
     * Supports YYYY/MM/DD format and includes leap year validation
     * @param date The date string to validate
     * @return true if date is valid, false otherwise
     */
    private boolean validateDate(String date) {
        try {
            if (date.equals("YYYY/MM/DD")) {
                return false;
            }

            String[] parts = date.split("/");
            if (parts.length != 3) {
                return false;
            }

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            if (year < 1900 || year > 2025) return false;

            // Check days in month
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day > 30) return false;
            } else if (month == 2) {
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                if (isLeapYear && day > 29) return false;
                if (!isLeapYear && day > 28) return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Saves member details to a file
     * Creates backup of existing file
     * Formats data in a tabulated structure
     * Handles file operation errors
     */
    private void saveToFile() {
        // Check if there are members to save
        if (gymMembers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members to save to file");
            return;
        }

        try {
            // Create file object and handle backup if file exists
            File file = new File("MemberDetails.txt");
            if (file.exists()) {
                File backup = new File("MemberDetails_backup.txt");
                if (backup.exists()) {
                    backup.delete();
                }
                file.renameTo(backup);
            }

            // Create new file writer
            FileWriter writer = new FileWriter(file);

            // Write header with proper tabulation
            String headerFormat = "%-6s  %-20s  %-20s  %-12s  %-30s  %-8s  %-12s  %-20s  %-10s  %-15s  %-15s  %-15s  %-12s  %-15s  %-15s  %-15s\n";
            writer.write(String.format(headerFormat,
                    "ID", "Name", "Location", "Phone", "Email", "Gender", "DOB",
                    "Start Date", "Type", "Plan/Trainer", "Price", "Status",
                    "Full Pay", "Paid Amount", "Remaining", "Discount"));

            // Add a line of dashes for visual separation
            writer.write("-".repeat(250) + "\n");

            // Write member details with same format
            String dataFormat = "%-6d  %-20s  %-20s  %-12s  %-30s  %-8s  %-12s  %-20s  %-10s  %-15s  %-15s  %-15s  %-12s  %-15s  %-15s  %-15s\n";

            for (GymMember member : gymMembers) {
                String type = "";
                String planOrTrainer = "";
                String price = "";
                String fullPayment = "N/A";
                String paidAmount = "N/A";
                String remainingAmount = "N/A";
                String discountAmount = "N/A";

                if (member instanceof RegularMember) {
                    RegularMember rm = (RegularMember) member;
                    type = "Regular";
                    planOrTrainer = rm.getPlan();
                    price = String.format("Rs. %.2f", rm.getPrice());
                } else if (member instanceof PremiumMember) {
                    PremiumMember pm = (PremiumMember) member;
                    type = "Premium";
                    planOrTrainer = pm.getPersonalTrainer();
                    price = String.format("Rs. %.2f", pm.getPremiumCharge());
                    fullPayment = String.valueOf(pm.isFullPayment());
                    paidAmount = String.format("Rs. %.2f", pm.getPaidAmount());
                    remainingAmount = String.format("Rs. %.2f", (pm.getPremiumCharge() - pm.getPaidAmount()));
                    discountAmount = String.format("Rs. %.2f", pm.getDiscountAmount());
                }

                writer.write(String.format(dataFormat,
                        member.getId(),
                        member.getName(),
                        member.getLocation(),
                        member.getPhone(),
                        member.getEmail(),
                        member.getGender(),
                        member.getDob(),
                        member.getMembershipStartDate(),
                        type,
                        planOrTrainer,
                        price,
                        member.isActive() ? "Active" : "Inactive",
                        fullPayment,
                        paidAmount,
                        remainingAmount,
                        discountAmount));
            }

            // Add a closing line
            writer.write("-".repeat(250) + "\n");

            writer.close();
            JOptionPane.showMessageDialog(null, "Member details saved to file successfully.\nLocation: " + file.getAbsolutePath());

        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(null, "Error: No permission to write to file. Please check file permissions.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving to file: " + e.getMessage() + "\nPlease make sure the file is not open in another program.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error while saving: " + e.getMessage());
        }
    }

    /**
     * Reads and displays member details from file
     * Creates separate tables for Regular and Premium members
     * Formats data in an organized view
     */
    private void readFromFile() {
        try {
            File file = new File("MemberDetails.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "No member details file found");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Skip header and separator lines
            reader.readLine(); // Skip header
            reader.readLine(); // Skip separator line

            // Create main frame with a better title
            JFrame frame = new JFrame("Member Details from File");
            frame.setLayout(new GridLayout(2, 1, 0, 10));  // 2 rows, 1 column, 10px vertical gap

            // Create panels for Regular and Premium members
            JPanel regularPanel = new JPanel(new BorderLayout());
            JPanel premiumPanel = new JPanel(new BorderLayout());
            regularPanel.setBorder(BorderFactory.createTitledBorder("Regular Members"));
            premiumPanel.setBorder(BorderFactory.createTitledBorder("Premium Members"));

            // Create table models for both types
            DefaultTableModel regularModel = new DefaultTableModel(
                    new String[]{"ID", "Name", "Location", "Phone", "Email", "Gender", "DOB",
                            "Start Date", "Plan", "Price", "Status"}, 0);

            DefaultTableModel premiumModel = new DefaultTableModel(
                    new String[]{"ID", "Name", "Location", "Phone", "Email", "Gender", "DOB",
                            "Start Date", "Trainer", "Price", "Status", "Full Pay", "Paid", "Remaining", "Discount"}, 0);

            String line;
            while ((line = reader.readLine()) != null && !line.startsWith("-")) {
                String[] data = line.trim().split("\\s{2,}"); // Split by 2 or more spaces

                // Check if it's a Regular or Premium member
                if (data.length >= 10) {
                    if (data[8].equals("Regular")) {
                        regularModel.addRow(new Object[]{
                                data[0], data[1], data[2], data[3], data[4],
                                data[5], data[6], data[7], data[9], data[10], data[11]
                        });
                    } else if (data[8].equals("Premium")) {
                        premiumModel.addRow(new Object[]{
                                data[0], data[1], data[2], data[3], data[4],
                                data[5], data[6], data[7], data[9], data[10], data[11],
                                data[12], data[13], data[14], data[15]
                        });
                    }
                }
            }
            reader.close();

            // Create tables with the models
            JTable regularTable = new JTable(regularModel);
            JTable premiumTable = new JTable(premiumModel);

            // Set table properties for better readability
            regularTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            premiumTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // Set column widths
            int[] regularWidths = {50, 100, 100, 100, 150, 70, 100, 100, 100, 100, 70};
            int[] premiumWidths = {50, 100, 100, 100, 150, 70, 100, 100, 100, 100, 70, 70, 100, 100, 100};

            for (int i = 0; i < regularTable.getColumnCount(); i++) {
                regularTable.getColumnModel().getColumn(i).setPreferredWidth(regularWidths[i]);
            }
            for (int i = 0; i < premiumTable.getColumnCount(); i++) {
                premiumTable.getColumnModel().getColumn(i).setPreferredWidth(premiumWidths[i]);
            }

            // Add tables to scroll panes
            JScrollPane regularScrollPane = new JScrollPane(regularTable);
            JScrollPane premiumScrollPane = new JScrollPane(premiumTable);

            // Add scroll panes to panels
            regularPanel.add(regularScrollPane, BorderLayout.CENTER);
            premiumPanel.add(premiumScrollPane, BorderLayout.CENTER);

            // Add panels to frame
            frame.add(regularPanel);
            frame.add(premiumPanel);

            // Set frame properties
            frame.setSize(1200, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading from file: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error processing file data: " + e.getMessage());
        }
    }

    /**
     * Validates input fields required for adding a regular member
     * Checks all basic fields plus referral source
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputForRegular() {
        if (!validateBasicFields()) return false;
        if (txtReferralSource.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter referral source");
            return false;
        }
        return true;
    }

    /**
     * Validates input fields required for adding a premium member
     * Checks all basic fields plus trainer name
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputForPremium() {
        if (!validateBasicFields()) return false;
        if (txtTrainerName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter trainer name");
            return false;
        }
        return true;
    }

    /**
     * Validates all basic member information fields
     * Includes validation for:
     * - Required fields are not empty
     * - ID is numeric
     * - Phone number format
     * - Email format
     * - Date formats
     * - Age requirement (minimum 10 years)
     * @return true if all basic fields are valid, false otherwise
     */
    private boolean validateBasicFields() {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() ||
                txtLocation.getText().isEmpty() || txtPhone.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || (!rbMale.isSelected() && !rbFemale.isSelected()) ||
                txtDob.getText().isEmpty() || txtDob.getText().equals("YYYY/MM/DD") ||
                txtMembershipStartDate.getText().isEmpty() || txtMembershipStartDate.getText().equals("YYYY/MM/DD")) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields");
            return false;
        }

        // Validate ID
        try {
            Integer.parseInt(txtId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID format");
            return false;
        }

        // Validate phone
        if (!validatePhoneNumber(txtPhone.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format");
            return false;
        }

        // Validate email
        if (!ValidateEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid email format");
            return false;
        }

        // Validate DOB
        if (!validateDate(txtDob.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid Date of Birth format (YYYY/MM/DD)");
            return false;
        }

        // Validate Membership Start Date
        if (!validateDate(txtMembershipStartDate.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid Membership Start Date format (YYYY/MM/DD)");
            return false;
        }

        // Validate age requirement (at least 10 years between DOB and membership start)
        try {
            String[] dobParts = txtDob.getText().split("/");
            String[] msdParts = txtMembershipStartDate.getText().split("/");

            int dobYear = Integer.parseInt(dobParts[0]);
            int msdYear = Integer.parseInt(msdParts[0]);
            int dobMonth = Integer.parseInt(dobParts[1]);
            int msdMonth = Integer.parseInt(msdParts[1]);
            int dobDay = Integer.parseInt(dobParts[2]);
            int msdDay = Integer.parseInt(msdParts[2]);

            // Calculate age at membership start
            int age = msdYear - dobYear;
            if (msdMonth < dobMonth || (msdMonth == dobMonth && msdDay < dobDay)) {
                age--;
            }

            if (age < 10) {
                JOptionPane.showMessageDialog(null, "Member must be at least 10 years old to start membership");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error calculating age requirement");
            return false;
        }

        return true;
    }

    /**
     * Sets up the GUI layout using GridBagLayout
     * Creates two main panels:
     * 1. Input panel - Contains all input fields and labels
     * 2. Button panel - Contains all action buttons
     * Organizes components in a user-friendly layout
     */
    private void setupLayout() {
        //creating panel,
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gymGbc = new GridBagConstraints();
        gymGbc.insets = new Insets(10, 30, 10, 0);

        //creating components and assigning their position in the grid
        gymGbc.gridx = 0;
        gymGbc.gridy = 0;
        inputPanel.add(lblId, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(txtId, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblName, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(txtName, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 1;
        inputPanel.add(lblLocation, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(txtLocation, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblEmail, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(txtEmail, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 2;
        inputPanel.add(lblPhone, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(txtPhone, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblGender, gymGbc);
        gymGbc.gridx = 3;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        inputPanel.add(genderPanel, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 3;
        inputPanel.add(lblDob, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(txtDob, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblMembershipStartDate, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(txtMembershipStartDate, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 4;
        inputPanel.add(lblReferralSource, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(txtReferralSource, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblPaidAmount, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(txtPaidAmount, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 5;
        inputPanel.add(lblTrainerName, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(txtTrainerName, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblRemovalReason, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(txtRemovalReason, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 6;
        inputPanel.add(lblRegularPrice, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(neTxtRegularPlanPrice, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblPlan, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(cbPlan, gymGbc);

        gymGbc.gridx = 0;
        gymGbc.gridy = 7;
        inputPanel.add(lblDiscountPrice, gymGbc);
        gymGbc.gridx = 1;
        inputPanel.add(neTxtDiscountPrice, gymGbc);
        gymGbc.gridx = 2;
        inputPanel.add(lblPremiumPrice, gymGbc);
        gymGbc.gridx = 3;
        inputPanel.add(neTxtPremiumPlanPrice, gymGbc);

        //Creating new panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(0, 10, 30, 10);
        btnGbc.gridx = 0;
        btnGbc.gridy = 0;
        buttonPanel.add(btnAddPremiumMember, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(btnAddRegularMember, btnGbc);
        btnGbc.gridx = 2;
        buttonPanel.add(btnMarkAttendance, btnGbc);

        btnGbc.gridx = 0;
        btnGbc.gridy = 1;
        buttonPanel.add(btnActivateMembership, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(btnDeactivateMembership, btnGbc);
        btnGbc.gridx = 2;
        buttonPanel.add(btnCalculateDiscount, btnGbc);

        btnGbc.gridx = 0;
        btnGbc.gridy = 2;
        buttonPanel.add(btnPayDueAmount, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(btnUpgradePlan, btnGbc);
        btnGbc.gridx = 2;
        buttonPanel.add(btnRevertRegularMember, btnGbc);

        btnGbc.gridx = 0;
        btnGbc.gridy = 3;
        buttonPanel.add(btnDisplay, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(btnRevertPremiumMember, btnGbc);
        btnGbc.gridx = 2;
        buttonPanel.add(btnReadFromFile, btnGbc);

        btnGbc.gridx = 0;
        btnGbc.gridy = 4;
        buttonPanel.add(btnSaveToFile, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(btnClear, btnGbc);

        //creating a frame and adding panel to the frame
        setLayout(new GridLayout(2, 1));
        add(inputPanel);
        add(buttonPanel);
    }

    /**
     * Creates a new regular member with the current input values
     * Adds the member to the gymMembers collection
     * Clears input fields after successful addition
     */
    private void addRegularMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String location = txtLocation.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String dob = txtDob.getText();
            String membershipStartDate = txtMembershipStartDate.getText();
            String referralSource = txtReferralSource.getText();

            RegularMember newMember = new RegularMember(id, name, location, phone, email,
                    gender, dob, membershipStartDate, referralSource);
            gymMembers.add(newMember);
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values");
        }
    }

    /**
     * Creates a new premium member with the current input values
     * Adds the member to the gymMembers collection
     * Clears input fields after successful addition
     */
    private void addPremiumMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String location = txtLocation.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String dob = txtDob.getText();
            String membershipStartDate = txtMembershipStartDate.getText();
            String trainerName = txtTrainerName.getText();

            PremiumMember newMember = new PremiumMember(id, name, location, phone, email,
                    gender, dob, membershipStartDate, trainerName);
            gymMembers.add(newMember);
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values");
        }
    }

    /**
     * Activates membership for the member with the specified ID
     * Displays success message or appropriate error message
     */
    private void activateMembership() {
        try {
            int id = Integer.parseInt(txtId.getText());
            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    member.activateMembership();
                    JOptionPane.showMessageDialog(null, "Membership activated successfully");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
        }
    }

    /**
     * Deactivates membership for the member with the specified ID
     * Displays success message or appropriate error message
     */
    private void deactivateMembership() {
        try {
            int id = Integer.parseInt(txtId.getText());
            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    member.deactivateMembership();
                    JOptionPane.showMessageDialog(null, "Membership deactivated successfully");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
        }
    }

    /**
     * Marks attendance for the member with the specified ID
     * Different point systems for Regular (5 points) and Premium (10 points) members
     * Checks membership status and attendance limits
     */
    private void markAttendance() {
        try {
            int id = Integer.parseInt(txtId.getText());
            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    if (member.isActive()) {
                        if (member instanceof RegularMember) {
                            RegularMember regularMember = (RegularMember) member;
                            if (regularMember.getAttendance() >= regularMember.getAttendanceLimit()) {
                                JOptionPane.showMessageDialog(null, "Attendance limit of 30 visits reached");
                                return;
                            }
                        }
                        member.markAttendance();
                        JOptionPane.showMessageDialog(null, "Attendance marked successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Member is not active");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
        }
    }

    /**
     * Handles plan upgrades for regular members
     * Allows upgrading between Basic, Standard, and Deluxe plans
     * Checks membership status before upgrade
     */
    private void upgradePlan() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String newPlan = (String) cbPlan.getSelectedItem();

            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    if (member instanceof RegularMember) {
                        RegularMember regularMember = (RegularMember) member;
                        if (regularMember.isActive()) {
                            String result = regularMember.upgradePlan(newPlan);
                            JOptionPane.showMessageDialog(null, result);
                        } else {
                            JOptionPane.showMessageDialog(null, "Member must be active to upgrade plan");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Only regular members can upgrade plans");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
        }
    }

    /**
     * Calculates discount for premium members
     * Regular members are not eligible for discounts
     * Updates the discount display field with the calculated amount
     */
    private void calculateDiscount() {
        try {
            int id = Integer.parseInt(txtId.getText());
            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    try {
                        // First try to cast as RegularMember to demonstrate proper type checking
                        RegularMember regularMember = (RegularMember) member;
                        JOptionPane.showMessageDialog(null, "Regular members are not eligible for discounts");
                        neTxtDiscountPrice.setText("");
                    } catch (ClassCastException e) {
                        // If casting to RegularMember fails, try PremiumMember
                        if (member instanceof PremiumMember) {
                            PremiumMember premiumMember = (PremiumMember) member;
                            String result = premiumMember.calculateDiscount();
                            JOptionPane.showMessageDialog(null, result);
                            neTxtDiscountPrice.setText(String.format("Rs. %.2f", premiumMember.getDiscountAmount()));
                        }
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
            neTxtDiscountPrice.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
            neTxtDiscountPrice.setText("");
        }
    }

    /**
     * Clears all input fields and resets form to initial state
     * Resets text fields, radio buttons, and combo boxes
     */
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtLocation.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtDob.setText("YYYY/MM/DD");
        txtDob.setForeground(Color.GRAY);
        txtMembershipStartDate.setText("YYYY/MM/DD");
        txtMembershipStartDate.setForeground(Color.GRAY);
        txtReferralSource.setText("");
        txtPaidAmount.setText("");
        txtRemovalReason.setText("");
        txtTrainerName.setText("");
        neTxtDiscountPrice.setText("");
        genderGroup.clearSelection();
        cbPlan.setSelectedIndex(0);
    }

    /**
     * Displays all members in a tabulated format
     * Creates separate tables for Regular and Premium members
     * Shows relevant information for each member type
     */
    private void displayMembers() {
        if (gymMembers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members to display");
            return;
        }

        // Create lists to separate Regular and Premium members
        ArrayList<RegularMember> regularMembers = new ArrayList<>();
        ArrayList<PremiumMember> premiumMembers = new ArrayList<>();

        // Separate members by type
        for (GymMember member : gymMembers) {
            if (member instanceof RegularMember) {
                regularMembers.add((RegularMember) member);
            } else if (member instanceof PremiumMember) {
                premiumMembers.add((PremiumMember) member);
            }
        }

        // Create main frame
        JFrame frame = new JFrame("Member Details");
        frame.setLayout(new GridLayout(2, 1, 0, 10));  // 2 rows, 1 column, 10px vertical gap

        // Create Regular Members panel
        JPanel regularPanel = new JPanel(new BorderLayout());
        regularPanel.setBorder(BorderFactory.createTitledBorder("Regular Members"));

        String[] regularColumnNames = {"ID", "Name", "Location", "Phone", "Email", "Gender", "DOB",
                "Start Date", "Plan", "Status", "Attendance", "Points", "Referral Source"};
        Object[][] regularData = new Object[regularMembers.size()][regularColumnNames.length];

        for (int i = 0; i < regularMembers.size(); i++) {
            RegularMember member = regularMembers.get(i);
            regularData[i][0] = member.getId();
            regularData[i][1] = member.getName();
            regularData[i][2] = member.getLocation();
            regularData[i][3] = member.getPhone();
            regularData[i][4] = member.getEmail();
            regularData[i][5] = member.getGender();
            regularData[i][6] = member.getDob();
            regularData[i][7] = member.getMembershipStartDate();
            regularData[i][8] = member.getPlan();
            regularData[i][9] = member.isActive() ? "Active" : "Inactive";
            regularData[i][10] = member.getAttendance();
            regularData[i][11] = member.getLoyaltyPoints();
            regularData[i][12] = member.getReferralSource();
        }

        JTable regularTable = new JTable(regularData, regularColumnNames);
        JScrollPane regularScrollPane = new JScrollPane(regularTable);
        regularPanel.add(regularScrollPane, BorderLayout.CENTER);

        // Create Premium Members panel
        JPanel premiumPanel = new JPanel(new BorderLayout());
        premiumPanel.setBorder(BorderFactory.createTitledBorder("Premium Members"));

        String[] premiumColumnNames = {"ID", "Name", "Location", "Phone", "Email", "Gender", "DOB",
                "Start Date", "Trainer", "Status", "Attendance", "Points", "Paid Amount", "Full Payment", "Discount"};
        Object[][] premiumData = new Object[premiumMembers.size()][premiumColumnNames.length];

        for (int i = 0; i < premiumMembers.size(); i++) {
            PremiumMember member = premiumMembers.get(i);
            premiumData[i][0] = member.getId();
            premiumData[i][1] = member.getName();
            premiumData[i][2] = member.getLocation();
            premiumData[i][3] = member.getPhone();
            premiumData[i][4] = member.getEmail();
            premiumData[i][5] = member.getGender();
            premiumData[i][6] = member.getDob();
            premiumData[i][7] = member.getMembershipStartDate();
            premiumData[i][8] = member.getPersonalTrainer();
            premiumData[i][9] = member.isActive() ? "Active" : "Inactive";
            premiumData[i][10] = member.getAttendance();
            premiumData[i][11] = member.getLoyaltyPoints();
            premiumData[i][12] = String.format("Rs. %.2f", member.getPaidAmount());
            premiumData[i][13] = member.isFullPayment() ? "Yes" : "No";
            premiumData[i][14] = String.format("Rs. %.2f", member.getDiscountAmount());
        }

        JTable premiumTable = new JTable(premiumData, premiumColumnNames);
        JScrollPane premiumScrollPane = new JScrollPane(premiumTable);
        premiumPanel.add(premiumScrollPane, BorderLayout.CENTER);

        // Add panels to frame
        frame.add(regularPanel);
        frame.add(premiumPanel);

        // Set frame properties
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Reverts a regular member's status
     * Requires a removal reason
     * Resets member attributes to default values
     */
    private void revertRegularMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    if (member instanceof RegularMember) {
                        RegularMember regularMember = (RegularMember) member;
                        String removalReason = txtRemovalReason.getText();
                        if (removalReason.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please enter removal reason");
                            return;
                        }
                        String result = regularMember.revertRegularMember(removalReason);
                        JOptionPane.showMessageDialog(null, result);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "This member is not a Regular Member");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
        }
    }

    /**
     * Reverts a premium member's status
     * Resets member attributes to default values
     */
    private void revertPremiumMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    if (member instanceof PremiumMember) {
                        PremiumMember premiumMember = (PremiumMember) member;
                        String result = premiumMember.revertPremiumMember();
                        JOptionPane.showMessageDialog(null, result);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "This member is not a Premium Member");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid member ID");
        }
    }

    /**
     * Processes payment for premium members
     * Updates payment status and remaining balance
     * Validates payment amount before processing
     */
    private void payDueAmount() {
        try {
            int id = Integer.parseInt(txtId.getText());
            double amount = Double.parseDouble(txtPaidAmount.getText());

            for (GymMember member : gymMembers) {
                if (member.getId() == id) {
                    if (member instanceof PremiumMember) {
                        PremiumMember premiumMember = (PremiumMember) member;
                        String result = premiumMember.payDueAmount(amount);
                        JOptionPane.showMessageDialog(null, result);
                    } else {
                        JOptionPane.showMessageDialog(null, "Only premium members can pay due amounts");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Member not found");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values");
        }
    }

    /**
     * Validates phone number format
     * Ensures number is exactly 10 digits
     * @param phoneNumber The phone number to validate
     * @return true if format is valid, false otherwise
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        String phonePattern = "^\\d{10}$";
        return phoneNumber.matches(phonePattern);
    }

    /**
     * Validates email address format
     * Checks for proper email structure (username@domain.tld)
     * @param email The email address to validate
     * @return true if format is valid, false otherwise
     */
    private boolean ValidateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    /**
     * Main method to launch the application
     * Sets up system look and feel
     * Creates GUI on the Event Dispatch Thread
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Use SwingUtilities to ensure GUI is created on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new GymGUI();
            }
        });
    }
}