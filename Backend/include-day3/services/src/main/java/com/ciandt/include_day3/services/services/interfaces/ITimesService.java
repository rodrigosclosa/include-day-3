package com.ciandt.include_day3.services.services.interfaces;

import com.ciandt.include_day3.services.beans.TimesBean;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public interface ITimesService {

    List<TimesBean> list();
    List<TimesBean> list(String search) throws NotFoundException;
    List<TimesBean> list(float latitude, float longitude, @Nullable Double raio) throws NotFoundException;
    TimesBean getById(Long id) throws NotFoundException;
    void insert(TimesBean time) throws ConflictException, NotFoundException;
    void update(TimesBean time) throws ConflictException, NotFoundException;
    void remove(long id) throws ConflictException, NotFoundException;

}
