package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.TimesBean;
import com.ciandt.include_day3.services.config.Params;
import com.ciandt.include_day3.services.dao.TimesDao;
import com.ciandt.include_day3.services.services.interfaces.ITimesService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Query;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class TimesService implements ITimesService {

    private TimesDao timesDao;

    public TimesService() {
        timesDao = new TimesDao();
    }

    @Override
    public List<TimesBean> list() {
        return timesDao.listAll();
    }

    @Override
    public List<TimesBean> list(String email) throws NotFoundException {
        List<TimesBean> list = timesDao.listByProperty("email", email);

        if(list == null || list.size() < 1) {
            throw new NotFoundException(String.format("Time não encontrado para o e-mail: %s", email));
        }

        return list;
    }

    @Override
    public List<TimesBean> list(float latitude, float longitude, @Nullable Double raio) throws NotFoundException {

        GeoPt center = new GeoPt(latitude, longitude);
        double radius = raio == null ? Params.getInstance().getRaio() : raio;
        Query.Filter filtro = new Query.StContainsFilter("localizacao", new Query.GeoRegion.Circle(center, radius));

        List<TimesBean> list = timesDao.listByFilter(filtro);

        if(list == null || list.size() < 1) {
            throw new NotFoundException(String.format("Usuário não encontrado para a região informada: %f, %f", latitude, longitude));
        }

        return list;
    }

    @Override
    public TimesBean getById(Long id) throws NotFoundException {
        TimesBean item = timesDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return item;
    }

    @Override
    public void insert(TimesBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Time não informado.");
        }
        else if(item.getNome() == null || item.getNome().isEmpty())
        {
            throw new ConflictException("Nome do usuário não informado.");
        }
        else if(item.getEmail() == null || item.getEmail().isEmpty())
        {
            throw new ConflictException("E-mail do usuário não informada.");
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

        TimesBean u = timesDao.getByProperty("email", item.getEmail());

        if(u != null)
        {
            throw new ConflictException("E-mail já cadastrado para o time: " + u.getNome());
        }

        timesDao.insert(item);
    }

    @Override
    public void update(TimesBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Time não informado.");
        }
        else if(item.getNome() == null || item.getNome().isEmpty())
        {
            throw new ConflictException("Nome do usuário não informado.");
        }
        else if(item.getEmail() == null || item.getEmail().isEmpty())
        {
            throw new ConflictException("E-mail do usuário não informada.");
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

        TimesBean u = timesDao.getById(item.getId());

        if(u == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        u = timesDao.getByProperty("email", item.getEmail());

        if(u != null && !u.getId().equals(item.getId()))
        {
            throw new ConflictException("E-mail já cadastrado para o time: " + u.getNome());
        }

        timesDao.update(item);
    }

    @Override
    public void remove(long id) throws ConflictException, NotFoundException {
        TimesBean item = timesDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        timesDao.delete(item);
    }
}
