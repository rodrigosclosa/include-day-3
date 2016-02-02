package com.ciandt.include_day3.services.services.interfaces;

import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public interface ITipoIncidenteService {

    List<TipoIncidenteBean> list();
    List<TipoIncidenteBean> list(String search) throws NotFoundException;
    TipoIncidenteBean getById(Long id) throws NotFoundException;
    void insert(TipoIncidenteBean tipo) throws ConflictException, NotFoundException;
    void update(TipoIncidenteBean tipo) throws ConflictException, NotFoundException;
    void remove(long id) throws ConflictException, NotFoundException;

}
