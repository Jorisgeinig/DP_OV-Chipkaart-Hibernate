package org.hu.dp.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @Column(name = "reiziger_id", columnDefinition = "NUMERIC")
    private int reiziger_id;

    private String voorletters;

    private String tussenvoegsel = null;

    private String achternaam;

    private Date geboortedatum;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "adres") // One-to-one relationship
    @JoinColumn(name = "reiziger_id")
    private Adres adres;

    public Reiziger(){
    }

    public Reiziger(int reiziger_id, String voorletters,
                    String achternaam, Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.achternaam = achternaam;
        this.voorletters = voorletters;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(int reiziger_id, String voorletters,
                    String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.achternaam = achternaam;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.geboortedatum = geboortedatum;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }
    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }



    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Choose the desired date format
        String formattedDate = dateFormat.format(geboortedatum);

        // Logica om de punten (.) bij de voorletter goed neer te zetten.
        String voorl_punt = "";
        for (int i = 0; i < voorletters.length(); i++) {
            voorl_punt += voorletters.charAt(i);

            if (i < voorletters.length() - 1) {
                voorl_punt += ".";
            }
        }
        if (voorletters.length() > 1) {
            voorl_punt += ".";
        }
        if (voorletters.length() == 1) {
            voorl_punt += ".";
        }

        String adresDetails = (adres != null)
                ? String.format("Adres{id=%d, postcode='%s', huisnummer='%s', straat='%s', woonplaats='%s'}",
                adres.getAdres_id(),
                adres.getPostcode(),
                adres.getHuisnummer(),
                adres.getStraat(),
                adres.getWoonplaats())
                : "Adres{none}";

        return String.format("#%d %s%s %s (%s)",
                reiziger_id,
                voorl_punt,
                (tussenvoegsel != null) ? " " + tussenvoegsel : "",
                achternaam,
                formattedDate,
                adresDetails);
    }
}
