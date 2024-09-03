package org.microservice;

import lombok.extern.slf4j.Slf4j;
import org.microservice.event.OrderPlacedEvent;
import org.microservice.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationServiceApplication {

    private final EmailService emailService;
    public NotificationServiceApplication(EmailService emailService) {
        this.emailService = emailService;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic", groupId = "notificationId")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {

        String orderNumber = orderPlacedEvent.getOrderNumber();
        String email = "hakimihakimi611@gmail.com"; // Replace with the recipient's email
        String subject = "Order Placed Successfully";

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Your order with number ").append(orderNumber).append(" has been successfully placed.\n\n");
        messageBuilder.append("Order Details:\n");
// Check if orderLineItemList is null or empty
        if (orderPlacedEvent.getOrderLineItemList() != null && !orderPlacedEvent.getOrderLineItemList().isEmpty()) {
            // Iterate over each order line item and append details to the message
            for (OrderPlacedEvent.OrderLineItem item : orderPlacedEvent.getOrderLineItemList()) {
                messageBuilder.append("SKU Code: ").append(item.getSkuCode()).append("\n");
                messageBuilder.append("Price: ").append(item.getPrice()).append("\n");
                messageBuilder.append("Quantity: ").append(item.getQuantity()).append("\n");
                messageBuilder.append("--------------------------\n");
            }
        } else {
            // If there are no items, append a message indicating no items were found
            messageBuilder.append("No items in the order.\n");
        }

// Convert the StringBuilder to a String
        String message = messageBuilder.toString();

// Send the email with the constructed message
        emailService.sendEmail(email, subject, message);

       try {
           log.info("Received notification for Order: {}", orderNumber);
       } catch (Exception e) {
           log.error("Error sending notification to email for order: {}. Exception: {}", orderNumber, e.getMessage());
       }
    }
}