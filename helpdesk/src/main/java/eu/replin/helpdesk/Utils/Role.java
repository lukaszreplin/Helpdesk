package eu.replin.helpdesk.Utils;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    public Role() {

    }

    public Role(String email, String role) {
        this.email = email;
        this.role = role;
    }

}
