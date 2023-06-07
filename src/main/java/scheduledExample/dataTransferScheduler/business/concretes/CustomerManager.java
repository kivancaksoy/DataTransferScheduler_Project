package scheduledExample.dataTransferScheduler.business.concretes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import scheduledExample.dataTransferScheduler.business.abstracts.CustomerService;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;
import scheduledExample.dataTransferScheduler.business.dto.converter.CustomerDtoConverter;
import scheduledExample.dataTransferScheduler.business.services.helpers.WebClientHelper;
import scheduledExample.dataTransferScheduler.dataAccess.CustomerRepository;
import scheduledExample.dataTransferScheduler.entities.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerManager implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    private final Logger logger = LoggerFactory.getLogger(CustomerManager.class);
    private final WebClientHelper webClientHelper;

    public CustomerManager(CustomerRepository customerRepository,
                           CustomerDtoConverter customerDtoConverter,
                           WebClientHelper webClientHelper) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
        this.webClientHelper = webClientHelper;
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return getAllCustomerDto(customers);
    }

    @Override
    public Flux<Customer> getAllCustomerFromBase(int version) {
        addCustomersIfNotExist(
                Objects.requireNonNull(
                        webClientHelper
                                .getAllCustomerWithVersion(version)
                                .collectList().block()));
        logger.info("getAllCustomerFromBase executed.");
        return webClientHelper.getAllCustomerWithVersion(version);
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

    private void addCustomersIfNotExist(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customers) {
            if (customerRepository.findByTcknEquals(customer.getTckn()) == null) {
                customerList.add(customer);
            }
        }
        customerRepository.saveAll(customerList);
        logger.info("Customers added.");
    }
}
