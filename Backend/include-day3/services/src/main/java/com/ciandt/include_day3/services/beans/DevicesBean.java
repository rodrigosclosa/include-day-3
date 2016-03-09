package com.ciandt.include_day3.services.beans;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by rodrigosclosa on 02/02/16.
 */
@Entity
public class DevicesBean {

    @Id
    private Long id;
    private String descricao;
    private String url;

    @Index
    private String geohash;
    private GeoPt localizacao;

    public DevicesBean() {
    }

    public DevicesBean(Long id, String descricao, String url, String geohash) {
        this.id = id;
        this.descricao = descricao;
        this.url = url;
        this.geohash = geohash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public GeoPt getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(GeoPt localizacao) {
        this.localizacao = localizacao;
    }
}
