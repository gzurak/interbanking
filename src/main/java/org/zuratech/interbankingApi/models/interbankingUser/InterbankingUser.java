package org.zuratech.interbankingApi.models.interbankingUser;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "interbanking_users")
@Data
public class InterbankingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToMany(mappedBy = "user")
    private List<InterbankingCreds> credentials;

}
