package com.ciandt.include_day3.services.beans;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by rodrigosclosa on 02/02/16.
 */
@Entity
public class UsuariosBean {

    @Id
    private Long id;
    private String nome;
    @Index
    private String email;
    private String logradouro;
    private int numero;
    private String cidade;
    private String estado;
    private GeoPt localizacao;

    public UsuariosBean() {
    }

    public UsuariosBean(Long id, String nome, String email, String logradouro, int numero, String cidade, String estado, GeoPt localizacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.localizacao = localizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public GeoPt getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(GeoPt localizacao) {
        this.localizacao = localizacao;
    }
}
