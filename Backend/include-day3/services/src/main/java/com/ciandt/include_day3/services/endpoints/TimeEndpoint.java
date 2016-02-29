package com.ciandt.include_day3.services.endpoints;

import com.ciandt.include_day3.services.beans.TimesBean;
import com.ciandt.include_day3.services.services.TimeService;
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
public class TimeEndpoint {

    private TimeService TimeService;

    public TimeEndpoint() {
        TimeService = new TimeService();
    }

    @ApiMethod(name = "getTimes", path = "times", httpMethod = "GET")
    public List<TimesBean> getTimes(@Nullable @Named("nome") String nome) throws NotFoundException {
        if(nome == null || nome.isEmpty())
            return TimeService.list();
        else
            return TimeService.list(nome);
    }

    @ApiMethod(name = "getTime", path = "times/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public TimesBean getTime(@Named("id") Long id) throws NotFoundException {
        return TimeService.getById(id);
    }

    @ApiMethod(name = "insertTime", path = "times", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertTime(TimesBean Time) throws ConflictException, NotFoundException {
        TimeService.insert(Time);
    }

    @ApiMethod(name = "updateTime", path = "times", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateTime(TimesBean veiculo) throws NotFoundException, ConflictException {
        TimeService.update(veiculo);
    }

    @ApiMethod(name = "removeTime", path = "times/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeTime(@Named("id") Long id) throws NotFoundException, ConflictException {
        TimeService.remove(id);
    }

}
