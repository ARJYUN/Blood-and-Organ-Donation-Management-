package interfaces;

import java.util.Random;
import java.util.UUID;

/**
 * UPIPaymentGateway - Another concrete implementation of PaymentGateway
 * Demonstrates Polymorphism - different implementation of the same interface
 */
public class UPIPaymentGateway implements PaymentGateway {
    private String transactionId;
    private static final String GATEWAY_NAME = "UPI Payment Gateway";
    
    @Override
    public boolean processPayment(String donorName, double amount, String upiId, String pin) {
        // Simulate UPI payment processing
        if (!validatePaymentDetails(upiId, pin)) {
            return false;
        }
        
        if (amount <= 0) {
            return false;
        }
        
        // Simulate processing delay
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Generate UPI transaction ID
        transactionId = "UPI" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        
        // Simulate 98% success rate for UPI
        Random random = new Random();
        return random.nextInt(100) < 98;
    }
    
    @Override
    public boolean validatePaymentDetails(String upiId, String pin) {
        // Validate UPI ID format
        if (upiId == null || pin == null) {
            return false;
        }
        
        // UPI ID should contain @ symbol
        if (!upiId.contains("@")) {
            return false;
        }
        
        // UPI ID format: username@bank
        String[] parts = upiId.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            return false;
        }
        
        // PIN should be 4-6 digits
        if (pin.length() < 4 || pin.length() > 6) {
            return false;
        }
        
        // PIN should contain only digits
        if (!pin.matches("\\d+")) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String getGatewayName() {
        return GATEWAY_NAME;
    }
    
    @Override
    public String getTransactionId() {
        return transactionId;
    }
}
