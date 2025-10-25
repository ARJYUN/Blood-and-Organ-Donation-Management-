package interfaces;

/**
 * PaymentGateway interface demonstrating Abstraction
 * This interface defines the contract for payment processing
 */
public interface PaymentGateway {
    /**
     * Process a payment transaction
     * @param donorName Name of the donor
     * @param amount Amount to be paid
     * @param cardNumber Card number for payment
     * @param cvv CVV code
     * @return true if payment is successful, false otherwise
     */
    boolean processPayment(String donorName, double amount, String cardNumber, String cvv);
    
    /**
     * Validate payment details
     * @param cardNumber Card number
     * @param cvv CVV code
     * @return true if valid, false otherwise
     */
    boolean validatePaymentDetails(String cardNumber, String cvv);
    
    /**
     * Get the name of the payment gateway
     * @return Payment gateway name
     */
    String getGatewayName();
    
    /**
     * Get transaction ID after successful payment
     * @return Transaction ID
     */
    String getTransactionId();
}
