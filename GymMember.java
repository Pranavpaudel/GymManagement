/**
 * GymMember - Abstract base class for all gym membership types
 * Provides common functionality and attributes for gym members
 * Serves as the parent class for RegularMember and PremiumMember
 */
public abstract class GymMember {
    // Member personal information
    protected int id;                     // Unique identifier for each member
    protected String name;                // Full name of the member
    protected String location;            // Residential address
    protected String phone;               // Contact number (10 digits)
    protected String email;               // Email address
    protected String gender;              // Member's gender
    protected String dob;                 // Date of birth (YYYY/MM/DD)
    protected String membershipStartDate; // Membership start date (YYYY/MM/DD)

    // Member activity tracking
    protected int attendance;             // Number of gym visits
    protected int loyaltyPoints;          // Points earned through attendance
    protected boolean active;             // Current membership status

    /**
     * Constructor for creating a new gym member
     * Initializes all member attributes and sets default values for tracking fields
     *
     * @param id Unique identifier for the member
     * @param name Member's full name
     * @param location Member's residential address
     * @param phone Contact number (must be 10 digits)
     * @param email Valid email address
     * @param gender Member's gender
     * @param dob Date of birth in YYYY/MM/DD format
     * @param membershipStartDate Start date in YYYY/MM/DD format
     */
    public GymMember(int id, String name, String location, String phone, String email,
                     String gender, String dob, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;      // Initialize with zero visits
        this.loyaltyPoints = 0;   // Initialize with zero points
        this.active = false;      // Membership inactive by default
    }

    // Getter methods for accessing member information
    /**
     * @return Member's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return Member's full name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Member's residential address
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return Member's contact number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return Member's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Member's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return Member's date of birth
     */
    public String getDob() {
        return dob;
    }

    /**
     * @return Date when membership started
     */
    public String getMembershipStartDate() {
        return membershipStartDate;
    }

    /**
     * @return Number of times member has visited the gym
     */
    public int getAttendance() {
        return attendance;
    }

    /**
     * @return Total loyalty points earned by the member
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * @return true if membership is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Abstract method for marking member attendance
     * Each member type implements its own attendance rules:
     * - Regular members earn 5 points per visit
     * - Premium members earn 10 points per visit
     */
    public abstract void markAttendance();

    /**
     * Activates the member's gym membership
     * Allows member to use gym facilities and services
     */
    public void activateMembership() {
        this.active = true;
    }

    /**
     * Deactivates the member's gym membership
     * Prevents member from using gym facilities and services
     */
    public void deactivateMembership() {
        this.active = false;
    }

    /**
     * Resets member's tracking statistics to initial values
     * Used when reverting membership type or handling membership cancellation
     * Resets: attendance, loyalty points, and active status
     */
    protected void resetMember() {
        this.attendance = 0;
        this.loyaltyPoints = 0;
        this.active = false;
    }

    /**
     * Displays detailed member information
     * Shows all member attributes in a formatted output
     * Used for debugging and verification purposes
     */
    public void display() {
        System.out.println("Member ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Membership Start Date: " + membershipStartDate);
        System.out.println("Attendance: " + attendance);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Active Status: " + (active ? "Active" : "Inactive"));
    }
}
