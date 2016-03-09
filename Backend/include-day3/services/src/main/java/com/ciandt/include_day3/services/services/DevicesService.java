package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.config.Params;
import com.ciandt.include_day3.services.dao.DevicesDao;
import com.ciandt.include_day3.services.services.interfaces.IDevicesService;
import com.ciandt.include_day3.services.util.GeohashUtil;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Query;

import java.util.List;

import javax.annotation.Nullable;

import ch.hsr.geohash.GeoHash;

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
    public List<DevicesBean> list(String latitude, String longitude, @Nullable Double raio) throws NotFoundException {

//        GeoPt center = new GeoPt(latitude, longitude);
//        double radius = raio == null ? Params.getInstance().getRaio() : raio;
//        Query.Filter filtro = new Query.StContainsFilter("localizacao", new Query.GeoRegion.Circle(center, radius));
//
//        List<DevicesBean> list = devicesDao.listByFilter(filtro);

        String geohash = GeohashUtil.getInstance().geohash(latitude, longitude, 5);

        List<DevicesBean> list = devicesDao.listByStartWith("geohash", geohash);

        if(list == null || list.size() < 1) {
            throw new NotFoundException("Device não encontrado. Hash: " + geohash);
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

        String geohash = GeohashUtil.getInstance().geohash(String.valueOf(item.getLocalizacao().getLatitude()), String.valueOf(item.getLocalizacao().getLongitude()), 10);

        item.setGeohash(geohash);

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

        String geohash = GeohashUtil.getInstance().geohash(String.valueOf(item.getLocalizacao().getLatitude()), String.valueOf(item.getLocalizacao().getLongitude()), 10);

        item.setGeohash(geohash);

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
