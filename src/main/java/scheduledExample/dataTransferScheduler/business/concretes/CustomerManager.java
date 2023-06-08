package scheduledExample.dataTransferScheduler.business.concretes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scheduledExample.dataTransferScheduler.business.abstracts.CustomerService;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;
import scheduledExample.dataTransferScheduler.business.dto.converter.CustomerDtoConverter;
import scheduledExample.dataTransferScheduler.business.services.helpers.WebClientHelper;
import scheduledExample.dataTransferScheduler.dataAccess.CustomerRepository;
import scheduledExample.dataTransferScheduler.entities.Customer;
import scheduledExample.dataTransferScheduler.exceptions.CustomerNotFoundException;
import scheduledExample.dataTransferScheduler.utilities.customConfigurations.CustomConfigurationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerManager implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    private final Logger logger = LoggerFactory.getLogger(CustomerManager.class);
    private final WebClientHelper webClientHelper;
    private final CustomConfigurationService customConfigurationService;

    public CustomerManager(CustomerRepository customerRepository,
                           CustomerDtoConverter customerDtoConverter,
                           WebClientHelper webClientHelper, CustomConfigurationService customConfigurationService) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
        this.webClientHelper = webClientHelper;
        this.customConfigurationService = customConfigurationService;
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return getAllCustomerDto(customers);
    }

    @Scheduled(cron = "#{@customConfigurationService.getCronValue()}")
    @Override
    public void getAllCustomerFromBase() {
        int version = getVersionNumber();
        customerOperation(
                Objects.requireNonNull(
                        webClientHelper
                                .getAllCustomerWithVersion(version)
                                .collectList().block()), version);
        logger.info("getAllCustomerFromBase executed.");
    }

    @Override
    public CustomerDto getCustomerByTckn(String tckn) {
        Customer customer = customerRepository.findByTcknEquals(tckn);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer could not find by tckn: " + tckn);
        }
        logger.info("Customer listed by tckn: " + tckn);
        return customerDtoConverter.convertToCustomerDto(customer);
    }

    private List<CustomerDto> getAllCustomerDto(List<Customer> customers) {
        try {
            List<CustomerDto> customerDtos =
                    customers.stream().map(customerDtoConverter::convertToCustomerDto).toList();
            logger.info("Customers listed");
            return customerDtos;
        } catch (Exception exception) {
            logger.error("An error occurred while listing: " + exception.getMessage());
            return null;
        }
    }


    private void customerOperation(List<Customer> customers, int versionNumber) {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customers) {
            if (customerRepository.findByTcknEqualsAndVersionNumberLessThan(customer.getTckn(), versionNumber) != null) {
                customer.setId(customerRepository.findByTcknEquals(customer.getTckn()).getId());
                customerList.add(customer);
                logger.info("Customer will update: " + customer.getName());
            }

            if (customerRepository.findByTcknEquals(customer.getTckn()) == null) {
                customerList.add(customer);
                logger.info("Customer will add: " + customer.getName());
            }
        }
        customerRepository.saveAll(customerList);
        logger.info("Customers operation executed.");
    }

    private int getVersionNumber() {
        return Integer.parseInt(customConfigurationService.getVersionNumberValue());
    }


    /*private void addCustomersIfNotExist(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customers) {
            if (customerRepository.findByTcknEquals(customer.getTckn()) == null) {
                customerList.add(customer);
            }
        }
        customerRepository.saveAll(customerList);
        logger.info("Customers added.");
    }

    private void updateCustomersIfExist(List<Customer> customers, int versionNumber) {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customers) {
            if (customerRepository.findByTcknEqualsAndVersionNumberLessThan(customer.getTckn(), versionNumber) != null) {
                customer.setId(customerRepository.findByTcknEquals(customer.getTckn()).getId());
                customerList.add(customer);
            }
        }
        customerRepository.saveAll(customerList);
        logger.info("Customers updated.");
    }*/

}
