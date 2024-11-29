package com.example.webapp.model;

import com.example.webapp.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;
    @Transient
    private String confirmPassword;

    private boolean isBun;

    @PrePersist
    private void init(){
        isBun=false;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roleSet=new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Cart> carts=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Address> addresses=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Order> orders=new ArrayList<>();

    public boolean isAdmin(){
        return roleSet.contains(Role.ROLE_ADMIN);
    }
    public boolean isManager(){
        return roleSet.contains(Role.ROLE_MANAGER);
    }

    private void addCartToCarts(Cart cart){
        carts.add(cart);
        cart.setUser(this);
    }

    public void addAddressToAddresses(Address address){
        addresses.add(address);
        address.setUser(this);
    }

}
