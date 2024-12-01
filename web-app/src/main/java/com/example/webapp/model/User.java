package com.example.webapp.model;

import com.example.webapp.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean isBun;

    @PrePersist
    private void init(){
        isBun=false;
    }

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet=new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user",orphanRemoval = true)
    private List<Cart>  carts =new ArrayList<>();


    public void addRole(Role role){
        roleSet.add(role);
    }
    public boolean isAdmin(){
        return roleSet.contains(Role.ROLE_ADMIN);
    }
    public boolean isManager(){
        return roleSet.contains(Role.ROLE_MANAGER);
    }

    public void addCartToList(Cart cart){
        carts.add(cart);
        cart.setUser(this);
    }

}
