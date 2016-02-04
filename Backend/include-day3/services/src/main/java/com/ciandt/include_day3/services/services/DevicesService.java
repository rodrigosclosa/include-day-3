package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.config.Params;
import com.ciandt.include_day3.services.dao.DevicesDao;
import com.ciandt.include_day3.services.dao.UsuarioDao;
import com.ciandt.include_day3.services.services.interfaces.IDevicesService;
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
public class DevicesService implements IDevicesService {

    private DevicesDao devicesDao;

    public DevicesService() {
        devicesDao = new DevicesDao();
    }

    @Override
    public List<DevicesBean> list() {
        return devicesDao.listAll();
    }

    @Override
    public List<DevicesBean> list(float latitude, float longitude, @Nullable Double raio) throws NotFoundException {

        GeoPt center = new GeoPt(latitude, longitude);
        double radius = raio == null ? Params.getInstance().getRaio() : raio;
        Query.Filter filtro = new Query.StContainsFilter("localizacao", new Query.GeoRegion.Circle(center, radius));

        List<DevicesBean> list = devicesDao.listByFilter(filtro);

        if(list == null || list.size() < 1) {
            throw new NotFoundException("Device não encontrado.");
        }

        return list;
    }

    @Override
    public DevicesBean getById(Long id) throws NotFoundException {
        DevicesBean item = devicesDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Device não encontrado");
        }

        return item;
    }

    @Override
    public void insert(DevicesBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Device não informado.");
        }
        else if(item.getDescricao() == null || item.getDescricao().isEmpty())
        {
            throw new ConflictException("Nome do device não informado.");
        }
        else if(item.getLocalizacao() == null) {
            throw new ConflictException("Localização inválida para o device.");
        }

        devicesDao.insert(item);
    }

    @Override
    public void update(DevicesBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Device não informado.");
        }
        else if(item.getDescricao() == null || item.getDescricao().isEmpty())
        {
            throw new ConflictException("Nome do device não informado.");
        }
        else if(item.getLocalizacao() == null) {
            throw new ConflictException("Localização inválida para o device.");
        }

        DevicesBean u = devicesDao.getById(item.getId());

        if(u == null) {
            throw new NotFoundException("Device não encontrado");
        }

        devicesDao.update(item);
    }

    @Override
    public void remove(long id) throws ConflictException, NotFoundException {
        DevicesBean item = devicesDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Device não encontrado");
        }

        devicesDao.delete(item);
    }
}
