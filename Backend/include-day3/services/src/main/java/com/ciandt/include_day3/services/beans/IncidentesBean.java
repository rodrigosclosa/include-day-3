package com.ciandt.include_day3.services.beans;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 02/02/16.
 */
@Entity
public class IncidentesBean {

    @Id
    private Long id;
    @Index
    private Long idUsuario;
    @Ignore
    @Nullable
    private UsuariosBean usuario;
    @Index
    private Long idTipoIncidente;
    @Ignore
    @Nullable
    private TipoIncidenteBean tipoIncidente;
    @Index
    private int gravidade;
    private String logradouro;
    private int numero;
    private String cidade;
    private String estado;
    @Index
    private GeoPt localizacao;
    private Date data;

    public IncidentesBean() {
    }

    public IncidentesBean(Long id, Long idUsuario, Long idTipoIncidente, int gravidade, String logradouro, int numero, String cidade, String estado, GeoPt localizacao, Date data) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idTipoIncidente = idTipoIncidente;
        this.gravidade = gravidade;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.localizacao = localizacao;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Nullable
    public UsuariosBean getUsuario() {
        return usuario;
    }

    public void setUsuario(@Nullable UsuariosBean usuario) {
        this.usuario = usuario;
    }

    public Long getIdTipoIncidente() {
        return idTipoIncidente;
    }

    public void setIdTipoIncidente(Long idTipoIncidente) {
        this.idTipoIncidente = idTipoIncidente;
    }

    @Nullable
    public TipoIncidenteBean getTipoIncidente() {
        return tipoIncidente;
    }

    public void setTipoIncidente(@Nullable TipoIncidenteBean tipoIncidente) {
        this.tipoIncidente = tipoIncidente;
    }

    public int getGravidade() {
        return gravidade;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
