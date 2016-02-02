package com.ciandt.include_day3.services.services.interfaces;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public interface IDevicesService {

    List<DevicesBean> list();
    List<DevicesBean> list(Double latitude, Double longitude) throws NotFoundException;
    DevicesBean getById(Long id) throws NotFoundException;
    void insert(DevicesBean veiculo) throws ConflictException, NotFoundException;
    void update(DevicesBean veiculo) throws ConflictException, NotFoundException;
    void remove(long id) throws ConflictException, NotFoundException;

}
