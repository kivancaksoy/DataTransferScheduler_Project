package scheduledExample.dataTransferScheduler.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scheduledExample.dataTransferScheduler.business.abstracts.CustomerService;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
@Validated
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<CustomerDto>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/getCustomerByTckn/{tckn}")
    public ResponseEntity<CustomerDto> getCustomerByTckn(
            @PathVariable @Size(min = 11, max = 11, message = "TCKN must be exactly 11 characters long") String tckn) {
        return ResponseEntity.ok(customerService.getCustomerByTckn(tckn));
    }

/*    @GetMapping("/getAllCustomerFromBase")
    public ResponseEntity<Flux<Customer>> getAllCustomerFromBase(@RequestParam("version") int version) {
        return ResponseEntity.ok(customerService.getAllCustomerFromBase(version));
    }*/
}
