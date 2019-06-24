package hello.entities;

import javax.persistence.*;

@Entity
public class Participant {


//    CREATE TABLE `participant` (
//            `id` bigint(20) NOT NULL AUTO_INCREMENT,
//            `name` varchar(50) NOT NULL,
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=latin1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
