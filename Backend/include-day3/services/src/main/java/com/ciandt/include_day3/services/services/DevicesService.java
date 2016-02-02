package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.dao.DevicesDao;
import com.ciandt.include_day3.services.dao.UsuarioDao;
import com.ciandt.include_day3.services.services.interfaces.IDevicesService;
import com.ciandt.include_day3.services.services.interfaces.IUsuarioService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

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
    public List<DevicesBean> list(Double latitude, Double longitude) throws NotFoundException {
        List<DevicesBean> list = devicesDao.listByProperty("latitude", latitude);

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
        else if(item.getLatitude() == null || (item.getLatitude() < -90.0d && item.getLatitude() > 90.0d))
        {
            throw new ConflictException("Latitude inválida para o device.");
        }
        else if(item.getLongitude() == null || (item.getLongitude() < -180.0d && item.getLongitude() > 180.0d))
        {
            throw new ConflictException("Longitude inválida para o device.");
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
        else if(item.getLatitude() == null || (item.getLatitude() < -90.0d && item.getLatitude() > 90.0d))
        {
            throw new ConflictException("Latitude inválida para o device.");
        }
        else if(item.getLongitude() == null || (item.getLongitude() < -180.0d && item.getLongitude() > 180.0d))
        {
            throw new ConflictException("Longitude inválida para o device.");
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
