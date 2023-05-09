package tn.esprit.pidev.Entities;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

@Entity
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "user_id")
    private int userid;

    private String titre;

    private String Description;

    private LocalDate Date;

    private int rate;

    private int nbPlaces;

    private String image;



    @Enumerated(EnumType.STRING)
    private eventType Type;

    private float Prix;
    private String Localisation;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "Participations",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();



    public Event() {
    }

    public Event(int ID, int userID, String titre, String description, LocalDate date, int rate, int nbPlaces, String image, eventType type, float prix, String localisation) {
        this.ID = ID;
        userid = userID;
        this.titre = titre;
        Description = description;
        Date = date;
        this.rate = rate;
        this.nbPlaces = nbPlaces;
        this.image = image;
        Type = type;
        Prix = prix;
        Localisation = localisation;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userID) {
        userid = userID;
    }



    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public eventType getType() {
        return Type;
    }

    public void setType(eventType type) {
        Type = type;
    }

    public float getPrix() {
        return Prix;
    }

    public void setPrix(float prix) {
        Prix = prix;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public void setLocalisation(String localisation) {
        Localisation = localisation;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

