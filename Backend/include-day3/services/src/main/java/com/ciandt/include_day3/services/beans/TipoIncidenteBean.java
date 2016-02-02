package com.ciandt.include_day3.services.beans;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by rodrigosclosa on 02/02/16.
 */
@Entity
public class TipoIncidenteBean {

    @Id
    private Long id;
    private String descricao;

    public TipoIncidenteBean() {
    }

    public TipoIncidenteBean(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
}
