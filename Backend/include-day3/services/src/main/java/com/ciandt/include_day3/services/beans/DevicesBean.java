package com.ciandt.include_day3.services.beans;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by rodrigosclosa on 02/02/16.
 */
@Entity
public class DevicesBean {

    @Id
    private Long id;
    private String descricao;
    private String url;
    private Double latitude;
    private Double longitude;

    public DevicesBean() {
    }

    public DevicesBean(Long id, String descricao, String url, Double latitude, Double longitude) {
        this.id = id;
        this.descricao = descricao;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
