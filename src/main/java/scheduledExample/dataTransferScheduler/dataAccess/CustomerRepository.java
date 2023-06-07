package scheduledExample.dataTransferScheduler.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scheduledExample.dataTransferScheduler.entities.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByTcknEquals(String tckn);
    Customer findByTcknEqualsAndVersionNumberLessThan(String tckn, int versionNumber);

    @Query("SELECT MAX(c.versionNumber) FROM Customer c")
    Integer findMaxVersionNumber();


}
