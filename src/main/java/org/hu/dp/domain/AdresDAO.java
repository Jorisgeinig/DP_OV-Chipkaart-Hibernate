package org.hu.dp.domain;

import java.util.List;

public interface AdresDAO {
    public boolean save(Adres adres)  ;
    public boolean update(Adres adres)  ;
    public boolean delete(Adres adres)  ;
    public List<Adres> findAll()  ;
    public Adres findByReiziger(Reiziger reiziger)  ;
}
