package com.example.webapp.model;

import com.example.webapp.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @Size(min = 5,message = "Username length must be more or equal than 5")
    @NotBlank
    private String username;
    @Size(min = 6,message = "Password length must be more or equal than 6 ")
    @NotBlank
    private String password;
    @Column(unique = true)
    @Email(message = "Filed email must contains @")
    private String email;
    @Column(name = "is_bun")
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

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

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

    public void addOrderToList(Order order){
        orders.add(order);
        order.setUser(this);
    }

}
