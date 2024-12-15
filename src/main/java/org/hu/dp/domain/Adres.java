package org.hu.dp.domain;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(reiziger.getGeboortedatum());


        String voorletters_metpunt = "";
        for (int i = 0; i < reiziger.getVoorletters().length(); i++) {
            voorletters_metpunt += reiziger.getVoorletters().charAt(i);

            if (i < reiziger.getVoorletters().length() - 1) {
                voorletters_metpunt += ".";
            }
        }
        if (reiziger.getVoorletters().length() > 1) {
            voorletters_metpunt += ".";
        }
        if (reiziger.getVoorletters().length() == 1) {
            voorletters_metpunt += ".";
        }

        String resultaat = String.format("Reiziger #%d %s%s %s (%s), Adres {#%d %s %s}",
                reiziger.getReiziger_id(),
                voorletters_metpunt,
                (reiziger.getTussenvoegsel() != null) ? " " + reiziger.getTussenvoegsel() : "",
                reiziger.getAchternaam(),
                formattedDate,
                adres_id,
                postcode,
                huisnummer);
        return resultaat;
    }
}
