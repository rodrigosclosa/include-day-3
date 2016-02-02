package com.ciandt.include_day3.services.services.interfaces;

import com.ciandt.include_day3.services.beans.UsuariosBean;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public interface IUsuarioService {

    List<UsuariosBean> list();
    List<UsuariosBean> list(String search) throws NotFoundException;
    UsuariosBean getById(Long id) throws NotFoundException;
    void insert(UsuariosBean veiculo) throws ConflictException, NotFoundException;
    void update(UsuariosBean veiculo) throws ConflictException, NotFoundException;
    void remove(long id) throws ConflictException, NotFoundException;

}
