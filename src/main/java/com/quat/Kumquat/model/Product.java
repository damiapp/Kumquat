package com.quat.Kumquat.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String itemName;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int stock;
    @Column
    private String description;
    @Lob
    @Column(columnDefinition="bytea")
    private byte[] image;
    @ManyToMany(mappedBy = "products")
    private Set<User> users = Collections.emptySet();
}
