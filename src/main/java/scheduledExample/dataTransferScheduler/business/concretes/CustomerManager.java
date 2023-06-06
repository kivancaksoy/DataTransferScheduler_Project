package scheduledExample.dataTransferScheduler.business.concretes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import scheduledExample.dataTransferScheduler.business.abstracts.CustomerService;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;
import scheduledExample.dataTransferScheduler.business.dto.converter.CustomerDtoConverter;
import scheduledExample.dataTransferScheduler.dataAccess.CustomerRepository;
import scheduledExample.dataTransferScheduler.entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    private final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

    public CustomerManager(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return getAllCustomerDto(customers);
    }

    private List<CustomerDto> getAllCustomerDto(List<Customer> customers) {
        try {
            List<CustomerDto> customerDtos =
                    customers.stream().map(customerDtoConverter::convertToCustomerDto).toList();
            logger.info("Customers listed");
            return customerDtos;
        } catch (Exception exception) {
            logger.error("An error occurred while listing: " + exception.getMessage());
        }
        return null;
    }
}
