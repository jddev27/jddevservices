package com.jddev.customer;

import com.jddev.amqp.RabbitMQMessageProducer;
import com.jddev.clients.fraud.FraudCheckResponse;
import com.jddev.clients.fraud.FraudClient;
import com.jddev.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email is valid
        //todo: check if email is not taken
        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster

       FraudCheckResponse fraudCheckResponse =  fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw  new IllegalStateException("fraudster");
        }

        //Todo: send notification
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to jjdev services...",
                        customer.getLastName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
