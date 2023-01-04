package com.quat.Kumquat.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

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
    @Column(unique = true)
    private String itemName;
    @Column
    private double price;
    @Column
    private int stock;
    @Column
    private String description;
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image")
    private byte[] image;

}
