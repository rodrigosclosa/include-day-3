package com.ciandt.include_day3.services.services.interfaces;

import com.ciandt.include_day3.services.beans.IncidentesBean;
import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public interface IIncidentesService {

    List<IncidentesBean> list();
    List<IncidentesBean> list(String latitude, String longitude, @Nullable Double raio) throws NotFoundException;
    IncidentesBean getById(Long id) throws NotFoundException;
    void insert(IncidentesBean incidente) throws ConflictException, NotFoundException;
    void update(IncidentesBean incidente) throws ConflictException, NotFoundException;
    void remove(long id) throws ConflictException, NotFoundException;

}