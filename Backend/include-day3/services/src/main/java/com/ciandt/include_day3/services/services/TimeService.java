package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.beans.TimesBean;
import com.ciandt.include_day3.services.config.Params;
import com.ciandt.include_day3.services.dao.TimeDao;
import com.ciandt.include_day3.services.services.interfaces.ITimeService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Query;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class TimeService implements ITimeService {

    private TimeDao TimeDao;

    public TimeService() {
        TimeDao = new TimeDao();
    }

    @Override
    public List<TimesBean> list() {
        return TimeDao.listAll();
    }

    @Override
    public List<TimesBean> list(String nome) throws NotFoundException {
        List<TimesBean> list = TimeDao.listByProperty("nome", nome);

        if(list == null || list.size() < 1) {
            throw new NotFoundException(String.format("Time não encontrado para o nome: %s", nome));
        }

        return list;
    }

    @Override
    public TimesBean getById(Long id) throws NotFoundException {
        TimesBean item = TimeDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Time não encontrado");
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
            throw new ConflictException("Nome do Time não informado.");
        }
        else if(item.getIntegrantes() == null || item.getIntegrantes().isEmpty())
        {
            throw new ConflictException("Integrantes do Time não informada.");
        }
        else if(item.getBaseCiandt() == null || item.getBaseCiandt().isEmpty())
        {
            throw new ConflictException("Base CI&T do Time não informado.");
        }
      
        TimesBean u = TimeDao.getByProperty("nome", item.getNome());

        if(u != null)
        {
            throw new ConflictException("Nome já cadastrado para o Time: " + u.getNome());
        }

        TimeDao.insert(item);
    }

    @Override
    public void update(TimesBean item) throws ConflictException, NotFoundException {
 if(item == null)
        {
            throw new ConflictException("Time não informado.");
        }
        else if(item.getNome() == null || item.getNome().isEmpty())
        {
            throw new ConflictException("Nome do Time não informado.");
        }
        else if(item.getIntegrantes() == null || item.getIntegrantes().isEmpty())
        {
            throw new ConflictException("Integrantes do Time não informada.");
        }
        else if(item.getBaseCiandt() == null || item.getBaseCiandt().isEmpty())
        {
            throw new ConflictException("Base CI&T do Time não informado.");
        }

        TimesBean u = TimeDao.getById(item.getId());

        if(u == null) {
            throw new NotFoundException("Time não encontrado");
        }

        TimesBean u = TimeDao.getByProperty("nome", item.getNome());

        if(u != null && !u.getId().equals(item.getId()))
        {
            throw new ConflictException("Nome já cadastrado para o Time: " + u.getNome());
        }

        TimeDao.update(item);
    }

    @Override
    public void remove(long id) throws ConflictException, NotFoundException {
        TimesBean item = TimeDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Time não encontrado");
        }

        TimeDao.delete(item);
    }
}
