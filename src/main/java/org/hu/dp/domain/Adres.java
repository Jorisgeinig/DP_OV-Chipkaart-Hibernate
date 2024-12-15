package org.hu.dp.domain;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;

@Entity
@Table(name = "adres")
public class Adres {
    @Id
    @Column(name = "adres_id", columnDefinition = "NUMERIC")
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    @OneToOne
    @JoinColumn(name = "reiziger_id", referencedColumnName = "reiziger_id", nullable = false)
    private Reiziger reiziger;

    public Adres() {

    }

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public int getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString() {
        return String.format(
                "Adres{id=%d, postcode='%s', huisnummer='%s', reizigerId=%d}",
                adres_id,
                postcode,
                huisnummer,
                reiziger != null ? reiziger.getReiziger_id() : null
        );
    }
}
