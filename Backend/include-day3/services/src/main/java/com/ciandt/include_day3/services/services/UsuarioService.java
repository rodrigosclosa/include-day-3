package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.beans.UsuariosBean;
import com.ciandt.include_day3.services.config.Params;
import com.ciandt.include_day3.services.dao.UsuarioDao;
import com.ciandt.include_day3.services.services.interfaces.IUsuarioService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Query;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class UsuarioService implements IUsuarioService {

    private UsuarioDao usuarioDao;

    public UsuarioService() {
        usuarioDao = new UsuarioDao();
    }

    @Override
    public List<UsuariosBean> list() {
        return usuarioDao.listAll();
    }

    @Override
    public List<UsuariosBean> list(String email) throws NotFoundException {
        List<UsuariosBean> list = usuarioDao.listByProperty("email", email);

        if(list == null || list.size() < 1) {
            throw new NotFoundException(String.format("Usuário não encontrado para o e-mail: %s", email));
        }

        return list;
    }

    @Override
    public List<UsuariosBean> list(float latitude, float longitude, @Nullable Double raio) throws NotFoundException {

        GeoPt center = new GeoPt(latitude, longitude);
        double radius = raio == null ? Params.getInstance().getRaio() : raio;
        Query.Filter filtro = new Query.StContainsFilter("localizacao", new Query.GeoRegion.Circle(center, radius));

        List<UsuariosBean> list = usuarioDao.listByFilter(filtro);

        if(list == null || list.size() < 1) {
            throw new NotFoundException(String.format("Usuário não encontrado para a região informada: %f, %f", latitude, longitude));
        }

        return list;
    }

    @Override
    public UsuariosBean getById(Long id) throws NotFoundException {
        UsuariosBean item = usuarioDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return item;
    }

    @Override
    public void insert(UsuariosBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Usuário não informado.");
        }
        else if(item.getNome() == null || item.getNome().isEmpty())
        {
            throw new ConflictException("Nome do usuário não informado.");
        }
        else if(item.getEmail() == null || item.getEmail().isEmpty())
        {
            throw new ConflictException("E-mail do usuário não informada.");
        }
        else if(item.getLogradouro() == null || item.getLogradouro().isEmpty())
        {
            throw new ConflictException("Logradouro do usuário não informado.");
        }
        else if(item.getCidade() == null || item.getCidade().isEmpty())
        {
            throw new ConflictException("Cidade do usuário não informada.");
        }
        else if(item.getEstado() == null || item.getEstado().isEmpty())
        {
            throw new ConflictException("Estado do usuário não informado.");
        }
        else if(item.getLocalizacao() == null) {
            throw new ConflictException("Localização inválida para o device.");
        }

        UsuariosBean u = usuarioDao.getByProperty("email", item.getEmail());

        if(u != null)
        {
            throw new ConflictException("E-mail já cadastrado para o usuário: " + u.getNome());
        }

        usuarioDao.insert(item);
    }

    @Override
    public void update(UsuariosBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Usuário não informado.");
        }
        else if(item.getNome() == null || item.getNome().isEmpty())
        {
            throw new ConflictException("Nome do usuário não informado.");
        }
        else if(item.getEmail() == null || item.getEmail().isEmpty())
        {
            throw new ConflictException("E-mail do usuário não informada.");
        }
        else if(item.getLogradouro() == null || item.getLogradouro().isEmpty())
        {
            throw new ConflictException("Logradouro do usuário não informado.");
        }
        else if(item.getCidade() == null || item.getCidade().isEmpty())
        {
            throw new ConflictException("Cidade do usuário não informada.");
        }
        else if(item.getEstado() == null || item.getEstado().isEmpty())
        {
            throw new ConflictException("Estado do usuário não informado.");
        }
        else if(item.getLocalizacao() == null) {
            throw new ConflictException("Localização inválida para o device.");
        }

        UsuariosBean u = usuarioDao.getById(item.getId());

        if(u == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        u = usuarioDao.getByProperty("email", item.getEmail());

        if(u != null && !u.getId().equals(item.getId()))
        {
            throw new ConflictException("E-mail já cadastrado para o usuário: " + u.getNome());
        }

        usuarioDao.update(item);
    }

    @Override
    public void remove(long id) throws ConflictException, NotFoundException {
        UsuariosBean item = usuarioDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        usuarioDao.delete(item);
    }
}
