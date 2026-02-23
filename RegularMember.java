/**
 * RegularMember - Represents a standard gym membership type
 * Extends GymMember with regular membership specific features
 * Includes plan management, attendance tracking, and upgrade eligibility
 */
public class RegularMember extends GymMember {
    // Membership constraints and status
    private final int attendanceLimit;          // Required visits for upgrade eligibility (default: 30)
    private boolean isEligibleForUpgrade;       // Whether member can upgrade to a higher plan
    private String removalReason;               // Documentation for membership cancellation

    // Membership details
    private String referralSource;              // Marketing tracking - how member found the gym
    private String plan;                        // Current plan level (basic/standard/deluxe)
    private double price;                       // Current plan price in rupees

    /**
     * Constructor for creating a new regular member
     * Initializes member with basic plan and default pricing
     * Sets up upgrade tracking and referral information
     *
     * @param id Unique identifier for the member
     * @param name Member's full name
     * @param location Member's residential address
     * @param phone Contact number (must be 10 digits)
     * @param email Valid email address
     * @param gender Member's gender
     * @param DOB Date of birth in YYYY/MM/DD format
     * @param membershipStartDate Start date in YYYY/MM/DD format
     * @param referralSource How the member learned about the gym
     */
    public RegularMember(int id, String name, String location, String phone, String email,
                         String gender, String DOB, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.isEligibleForUpgrade = false;      // Not eligible for upgrade initially
        this.attendanceLimit = 30;              // Must visit 30 times before upgrade
        this.plan = "basic";                    // Start with basic plan
        this.price = 6500;                      // Basic plan price
        this.removalReason = "";                // No removal reason initially
        this.referralSource = referralSource;   // Track marketing source
    }

    /**
     * @return Number of visits required for upgrade eligibility
     */
    public int getAttendanceLimit() {
        return attendanceLimit;
    }

    /**
     * @return Whether member can upgrade to a higher plan
     */
    public boolean isEligibleForUpgrade() {
        return isEligibleForUpgrade;
    }

    /**
     * @return Reason for membership cancellation if any
     */
    public String getRemovalReason() {
        return removalReason;
    }

    /**
     * @return How the member learned about the gym
     */
    public String getReferralSource() {
        return referralSource;
    }

    /**
     * @return Current membership plan level
     */
    public String getPlan() {
        return plan;
    }

    /**
     * @return Current plan price in rupees
     */
    public double getPrice() {
        return price;
    }

    /**
     * Records a gym visit for the member
     * Awards 5 loyalty points per visit
     * Checks and updates upgrade eligibility
     */
    @Override
    public void markAttendance() {
        attendance++;                    // Increment visit count
        loyaltyPoints += 5;             // Award points for visit

        // Check if member has reached required visits for upgrade
        if (attendance >= attendanceLimit) {
            isEligibleForUpgrade = true;
        }
    }

    /**
     * Gets the price for a specific membership plan
     *
     * @param plan The plan to check (basic/standard/deluxe)
     * @return Price in rupees, or -1 if invalid plan
     */
    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic":
                return 6500;     // Basic plan price
            case "standard":
                return 12500;    // Standard plan price
            case "deluxe":
                return 18500;    // Deluxe plan price
            default:
                return -1;       // Invalid plan indicator
        }
    }

    /**
     * Processes a plan upgrade request
     * Validates eligibility and plan type before upgrading
     *
     * @param newPlan The plan to upgrade to
     * @return Status message indicating success or reason for failure
     */
    public String upgradePlan(String newPlan) {
        newPlan = newPlan.toLowerCase();    // Standardize plan name

        // Check if already on requested plan
        if (newPlan.equals(this.plan.toLowerCase())) {
            return "You are already subscribed to " + this.plan + " plan";
        }

        // Process upgrade if eligible
        if (isEligibleForUpgrade) {
            double newPrice = getPlanPrice(newPlan);
            if (newPrice != -1) {
                this.plan = newPlan;
                this.price = newPrice;
                return "Plan upgraded to " + newPlan + " at price Rs. " + newPrice;
            } else {
                return "Invalid plan. Available plans: basic, standard, deluxe";
            }
        } else {
            return "Not eligible for upgrade. Required attendance: " + attendanceLimit;
        }
    }

    /**
     * Reverts member to initial regular membership state
     * Resets all tracking metrics and plan details
     * Records reason for reversion
     *
     * @param removalReason Why the membership is being reverted
     * @return Confirmation message with removal reason
     */
    public String revertRegularMember(String removalReason) {
        super.resetMember();                // Reset base member attributes
        this.isEligibleForUpgrade = false;  // Remove upgrade eligibility
        this.plan = "basic";                // Reset to basic plan
        this.price = 6500;                  // Reset to basic price
        this.removalReason = removalReason; // Record removal reason
        return "Member reverted successfully. Reason: " + removalReason;
    }

    /**
     * Displays all member information
     * Shows base member details plus regular-specific information
     * Includes removal reason if membership was reverted
     */
    @Override
    public void display() {
        super.display();                     // Show base member info

        // Show regular member specific details
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);

        // Show removal reason if exists
        if (!removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }
}
