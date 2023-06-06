package scheduledExample.dataTransferScheduler.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    private int id;
    private String tckn;
    private String name;
    private String surname;
    private String address;
    private int versionNumber;
}
