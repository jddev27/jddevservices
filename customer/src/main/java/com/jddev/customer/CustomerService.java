package com.jddev.customer;

import com.jddev.clients.fraud.FraudCheckResponse;
import com.jddev.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
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

    }
}
