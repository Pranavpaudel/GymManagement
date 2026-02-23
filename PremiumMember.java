/**
 * PremiumMember - Represents a premium gym membership type
 * Extends GymMember with premium-specific features and benefits
 * Includes personal training, payment tracking, and discount management
 */
public class PremiumMember extends GymMember {
    // Premium membership fees
    private final double premiumCharge;     // Fixed premium membership fee (50000 Rs)

    // Premium benefits
    private String personalTrainer;         // Assigned personal trainer's name

    // Payment tracking
    private boolean isFullPayment;          // Whether full payment is completed
    private double paidAmount;              // Total amount paid to date
    private double discountAmount;          // 10% discount after full payment

    /**
     * Constructor for creating a new premium member
     * Initializes member with premium benefits and payment tracking
     * Sets up personal training assignment
     *
     * @param id Unique identifier for the member
     * @param name Member's full name
     * @param location Member's residential address
     * @param phone Contact number (must be 10 digits)
     * @param email Valid email address
     * @param gender Member's gender
     * @param DOB Date of birth in YYYY/MM/DD format
     * @param membershipStartDate Start date in YYYY/MM/DD format
     * @param personalTrainer Name of assigned personal trainer
     */
    public PremiumMember(int id, String name, String location, String phone, String email,
                         String gender, String DOB, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.premiumCharge = 50000;         // Set fixed premium fee
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;         // Payment not completed initially
        this.paidAmount = 0;                // No payment made initially
        this.discountAmount = 0;            // No discount calculated initially
    }

    /**
     * @return Fixed premium membership charge
     */
    public double getPremiumCharge() {
        return premiumCharge;
    }

    /**
     * @return Name of assigned personal trainer
     */
    public String getPersonalTrainer() {
        return personalTrainer;
    }

    /**
     * @return Whether full payment has been made
     */
    public boolean isFullPayment() {
        return isFullPayment;
    }

    /**
     * @return Total amount paid so far
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * @return Calculated discount amount (if eligible)
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Records a gym visit for the premium member
     * Awards 10 loyalty points per visit (double the regular member points)
     */
    @Override
    public void markAttendance() {
        attendance++;                    // Increment visit count
        loyaltyPoints += 10;            // Premium members get double points
    }

    /**
     * Processes a payment towards the premium membership fee
     * Tracks payment progress and updates payment status
     *
     * @param payment Amount being paid in current transaction
     * @return Status message about payment and remaining balance
     */
    public String payDueAmount(double payment) {
        // Check if already paid in full
        if (isFullPayment) {
            return "Payment already completed. No due amount.";
        }

        double totalPaidAmount = this.paidAmount + payment;

        // Prevent overpayment
        if (totalPaidAmount > premiumCharge) {
            return "Invalid payment amount. Exceeds premium charge of Rs. " + premiumCharge;
        }

        // Process payment
        this.paidAmount = totalPaidAmount;
        double remainingAmount = premiumCharge - this.paidAmount;

        // Check if payment is now complete
        if (this.paidAmount == premiumCharge) {
            this.isFullPayment = true;
            return "Payment successful. Payment completed in full!";
        }

        return "Payment successful. Remaining amount to be paid: Rs. " + remainingAmount;
    }

    /**
     * Calculates 10% discount for full-payment members
     * Only available after complete payment of premium charge
     *
     * @return Status message with discount amount or eligibility requirement
     */
    public String calculateDiscount() {
        if (isFullPayment) {
            this.discountAmount = premiumCharge * 0.10;    // Calculate 10% discount
            return "Discount calculated successfully. Discount amount: Rs. " + discountAmount;
        } else {
            return "No discount available. Complete the payment to avail 10% discount.";
        }
    }

    /**
     * Reverts premium membership to initial state
     * Resets all premium benefits and payment tracking
     *
     * @return Confirmation message of successful reversion
     */
    public String revertPremiumMember() {
        super.resetMember();                // Reset base member attributes
        this.personalTrainer = "";          // Remove trainer assignment
        this.isFullPayment = false;         // Reset payment status
        this.paidAmount = 0;                // Clear payment history
        this.discountAmount = 0;            // Remove any discounts
        return "Premium member reverted successfully.";
    }

    /**
     * Displays all member information
     * Shows base member details plus premium-specific information
     * Includes payment status and discount details if applicable
     */
    @Override
    public void display() {
        super.display();                    // Show base member info

        // Show premium member specific details
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: Rs. " + paidAmount);
        System.out.println("Payment Status: " + (isFullPayment ? "Completed" : "Pending"));

        // Show remaining balance
        double remainingAmount = premiumCharge - paidAmount;
        System.out.println("Remaining Amount: Rs. " + remainingAmount);

        // Show discount if payment is complete
        if (isFullPayment) {
            System.out.println("Discount Amount: Rs. " + discountAmount);
        }
    }
}
