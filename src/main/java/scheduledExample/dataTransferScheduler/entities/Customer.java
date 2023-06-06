package scheduledExample.dataTransferScheduler.entities;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "customers")
@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tckn")
    private String tckn;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "version_number")
    private int versionNumber;
}
