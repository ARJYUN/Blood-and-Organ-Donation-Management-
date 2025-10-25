package utils;

import javax.swing.JOptionPane;

/**
 * Utility class for input validation
 */
public class ValidationUtils {
    
    /**
     * Validate if string is not empty
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone)) return false;
        String phoneRegex = "^[0-9]{10}$";
        return phone.matches(phoneRegex);
    }
    
    /**
     * Validate age (between 1 and 120 for recipients, 18-65 for donors)
     */
    public static boolean isValidAge(int age) {
        return age >= 1 && age <= 120;
    }
    
    /**
     * Validate donor age (between 18 and 65)
     */
    public static boolean isValidDonorAge(int age) {
        return age >= 18 && age <= 65;
    }
    
    /**
     * Validate password (minimum 6 characters)
     */
    public static boolean isValidPassword(String password) {
        return isNotEmpty(password) && password.length() >= 6;
    }
    
    /**
     * Validate phone number (10 digits) - alias for isValidPhone
     */
    public static boolean isValidPhoneNumber(String phone) {
        return isValidPhone(phone);
    }
    
    /**
     * Validate blood group
     */
    public static boolean isValidBloodGroup(String bloodGroup) {
        if (!isNotEmpty(bloodGroup)) return false;
        String[] validGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String group : validGroups) {
            if (group.equals(bloodGroup)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Validate amount (positive number)
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }
    
    /**
     * Show error message dialog
     */
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show success message dialog
     */
    public static void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show warning message dialog
     */
    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Show confirmation dialog
     */
    public static boolean showConfirmation(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Confirmation", 
                                                   JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
