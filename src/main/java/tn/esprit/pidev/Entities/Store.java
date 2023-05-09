package tn.esprit.pidev.Entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "stores")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Store implements Serializable {
    @Override
    public String toString() {
        return  " name=" + name + ", description=" + description + ", vendor=" + vendor
                + ", products=" + products + ", storeImage=" + storeImage + ", logo=" + logo + ", isApproved="
                + isApproved + "]";
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonBackReference
    Vendor vendor;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<Product> products;
    String storeImage;
    String logo;
    boolean isApproved;
    String address;
}
