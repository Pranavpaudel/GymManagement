## Gym Management System

Desktop-based Gym Management System built with **Java Swing**. It allows staff to register and manage gym members, handle different membership types, track attendance, manage payments, and view/export member data.

### Features

- **Member registration**
  - Capture ID, name, location, phone, email, gender, DOB, membership start date
  - Validation for dates, age (minimum 10 years), phone number, and email format
- **Membership types**
  - **RegularMember**
    - Basic/Standard/Deluxe plans with different prices
    - Attendance tracking and loyalty points (5 points per visit)
    - Upgrade eligibility after 30 visits
    - Revert/cancel membership with a recorded removal reason
  - **PremiumMember**
    - Fixed premium charge with personal trainer assignment
    - Payment tracking (partial and full)
    - 10% discount available after full payment
    - Attendance tracking and loyalty points (10 points per visit)
    - Ability to revert premium membership
- **Membership status**
  - Activate / deactivate membership
  - Enforce active status for operations like attendance and plan upgrade
- **Attendance tracking**
  - Per-member attendance counter
  - Different rules for Regular and Premium members
- **Payments & discounts (Premium)**
  - Pay outstanding amount in multiple transactions
  - Prevent overpayments
  - Calculate and display discount after full payment
- **Data viewing & persistence**
  - Display Regular and Premium members in separate tables
  - Save all member data to a formatted text file (`MemberDetails.txt`)
  - Read existing member details file and show in tables

### Project Structure

```text
GymManagement/
├─ GymMember.java        # Abstract base class for all members
├─ RegularMember.java    # Regular membership: plans, upgrades, attendance limit
├─ PremiumMember.java    # Premium membership: trainer, payments, discounts
└─ GymGUI.java           # Swing-based GUI and application entry point
```

### Requirements

- **Java**: JDK 8 or later (tested on modern JDKs)
- OS: Any OS that can run Java Swing applications (Windows / macOS / Linux)

### How to Run

From the `GymManagement` directory:

1. **Compile**

```bash
javac GymMember.java RegularMember.java PremiumMember.java GymGUI.java
```

2. **Run**

```bash
java GymGUI
```

The main window titled **"Gym Management System"** will open.

### Using the Application

- **Add Regular Member**
  - Fill in all basic fields (ID, Name, Location, Phone, Email, Gender, DOB, Membership Start Date)
  - Provide `Referral Source`
  - Click **"Add Regular Member"**
- **Add Premium Member**
  - Fill in all basic fields
  - Provide `Trainer's Name`
  - Click **"Add Premium Member"**
- **Activate / Deactivate Membership**
  - Enter a valid Member ID
  - Click **"Activate Membership"** or **"Deactivate Membership"**
- **Mark Attendance**
  - Enter Member ID
  - Ensure membership is active
  - Click **"Mark Attendance"**
- **Upgrade Plan (Regular only)**
  - Enter Regular Member ID
  - Select new plan from the dropdown (Basic / Standard / Deluxe)
  - Click **"Upgrade Plan"**
- **Premium Payments & Discount**
  - Enter Premium Member ID
  - Enter `Paid Amount`
  - Click **"Pay Due Amount"** to register a payment
  - After full payment, click **"Calculate Discount"** to compute 10% discount
- **Revert Membership**
  - **Regular**: Enter ID and `Removal Reason`, click **"Revert Regular Member"**
  - **Premium**: Enter ID, click **"Revert Premium Member"**
- **Display Members**
  - Click **"Display"** to open tables for Regular and Premium members
- **Save / Read from File**
  - **"Save to File"**: Writes current members to `MemberDetails.txt` (creates `MemberDetails_backup.txt` if file already exists)
  - **"Read from File"**: Reads `MemberDetails.txt` and displays content in tables

### File Output

- Member data is stored in a plain text file:
  - `MemberDetails.txt` (current export)
  - `MemberDetails_backup.txt` (auto-created backup of previous export)
- Data is written in a wide, tabular format that can be inspected with any text editor.

### Notes & Limitations

- Data is **in-memory only** while the program is running; there is no database.
- Member ID must be unique; duplicates are rejected.
- Members must be at least **10 years old** at membership start.
- Phone numbers must be exactly **10 digits**.
- Dates must be in **`YYYY/MM/DD`** format and pass basic validity checks.

