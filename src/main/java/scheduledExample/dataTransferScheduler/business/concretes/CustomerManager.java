package scheduledExample.dataTransferScheduler.business.concretes;

import org.springframework.stereotype.Service;
import scheduledExample.dataTransferScheduler.business.abstracts.CustomerService;
import scheduledExample.dataTransferScheduler.business.responses.GetAllCustomersResponse;
import scheduledExample.dataTransferScheduler.dataAccess.CustomerRepository;
import scheduledExample.dataTransferScheduler.entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<GetAllCustomersResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<GetAllCustomersResponse> getAllCustomersResponses =
                customers.stream().map(customer -> new GetAllCustomersResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getSurname(),
                        customer.getAddress()
                )).collect(Collectors.toList());

        return getAllCustomersResponses;
    }
}
