package com.ciandt.include_day3.services.endpoints;

import com.ciandt.include_day3.services.beans.TimesBean;
import com.ciandt.include_day3.services.services.TimesService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

import javax.inject.Named;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
@Api(
    name = "times",
    version = "v1",
    namespace = @ApiNamespace(
            ownerDomain = "services.include_day3.ciandt.com",
            ownerName = "services.include_day3.ciandt.com",
            packagePath = ""
    )
)
public class TimesEndpoint {

    private TimesService timesService;

    public TimesEndpoint() {
        timesService = new TimesService();
    }

    @ApiMethod(name = "getTimes", path = "times", httpMethod = "GET")
    public List<TimesBean> getTimes(@Nullable @Named("email") String email) throws NotFoundException {
        if(email == null || email.isEmpty())
            return timesService.list();
        else
            return timesService.list(email);
    }

    @ApiMethod(name = "getTimesByLocation", path = "times/{latitude}/{longitude}", httpMethod = "GET")
    public List<TimesBean> getTimesByLocation(@Named("latitude") float latitude, @Named("longitude") float longitude) throws NotFoundException {
        return timesService.list(latitude, longitude, null);
    }

    @ApiMethod(name = "getTimesByLocationAndRadius", path = "times/{latitude}/{longitude}/{raio}", httpMethod = "GET")
    public List<TimesBean> getTimesByLocationAndRadius(@Named("latitude") float latitude, @Named("longitude") float longitude, @Named("raio") Double raio) throws NotFoundException {
        return timesService.list(latitude, longitude, raio);
    }

    @ApiMethod(name = "getTime", path = "times/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public TimesBean getTime(@Named("id") Long id) throws NotFoundException {
        return timesService.getById(id);
    }

    @ApiMethod(name = "insertTime", path = "times", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertTime(TimesBean timesBean) throws ConflictException, NotFoundException {
        timesService.insert(timesBean);
    }

    @ApiMethod(name = "updateTime", path = "times", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateTime(TimesBean timesBean) throws NotFoundException, ConflictException {
        timesService.update(timesBean);
    }

    @ApiMethod(name = "removeTime", path = "times/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeTime(@Named("id") Long id) throws NotFoundException, ConflictException {
        timesService.remove(id);
    }

}
