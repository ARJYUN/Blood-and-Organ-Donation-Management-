package interfaces;

import java.util.Random;
import java.util.UUID;

/**
 * DemoPaymentGateway - Concrete implementation of PaymentGateway interface
 * Demonstrates Abstraction and Polymorphism
 * This is a mock payment gateway for demonstration purposes
 */
public class DemoPaymentGateway implements PaymentGateway {
    private String transactionId;
    private static final String GATEWAY_NAME = "Demo Payment Gateway";
    
    @Override
    public boolean processPayment(String donorName, double amount, String cardNumber, String cvv) {
        // Simulate payment processing
        if (!validatePaymentDetails(cardNumber, cvv)) {
            return false;
        }
        
        if (amount <= 0) {
            return false;
        }
        
        // Simulate processing delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Generate transaction ID
        transactionId = "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Simulate 95% success rate
        Random random = new Random();
        return random.nextInt(100) < 95;
    }
    
    @Override
    public boolean validatePaymentDetails(String cardNumber, String cvv) {
        // Basic validation
        if (cardNumber == null || cvv == null) {
            return false;
        }
        
        // Remove spaces from card number
        cardNumber = cardNumber.replaceAll("\\s+", "");
        
        // Check card number length (13-19 digits)
        if (cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }
        
        // Check if card number contains only digits
        if (!cardNumber.matches("\\d+")) {
            return false;
        }
        
        // Check CVV length (3-4 digits)
        if (cvv.length() < 3 || cvv.length() > 4) {
            return false;
        }
        
        // Check if CVV contains only digits
        if (!cvv.matches("\\d+")) {
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
