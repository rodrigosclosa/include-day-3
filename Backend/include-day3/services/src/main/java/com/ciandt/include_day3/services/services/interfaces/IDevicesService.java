package com.ciandt.include_day3.services.services.interfaces;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public interface IDevicesService {

    List<DevicesBean> list();
    List<DevicesBean> list(float latitude, float longitude, @Nullable Double raio) throws NotFoundException;
    DevicesBean getById(Long id) throws NotFoundException;
    void insert(DevicesBean device) throws ConflictException, NotFoundException;
    void update(DevicesBean device) throws ConflictException, NotFoundException;
    void remove(long id) throws ConflictException, NotFoundException;

}
