package com.ciandt.include_day3.services.beans;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by rodrigosclosa on 02/02/16.
 */
@Entity
public class TimesBean {

id
cidade
estado
nome
integrantes

    @Id
    private Long id;
    private String nome;
    @Index
    private String integrantes;
    private String baseCiandt;

    public TimesBean() {
    }

    public TimesBean(Long id, String nome, String integrantes, String baseCiandt) {
        this.id = id;
        this.nome = nome;
        this.integrantes = integrantes;
        this.baseCiandt = baseCiandt;
    }

    public id getCidade() {
        return cidade;
    }

    public void setCidade(id cidade) {
        this.cidade = cidade;
    }

    public estado getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(String integrantes) {
        this.integrantes = integrantes;
    }

    public String getBaseCiandt() {
        return baseCiandt;
    }

    public void setBaseCiandt(String baseCiandt) {
        this.baseCiandt = baseCiandt;
    }

    public void setNome(estado nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
