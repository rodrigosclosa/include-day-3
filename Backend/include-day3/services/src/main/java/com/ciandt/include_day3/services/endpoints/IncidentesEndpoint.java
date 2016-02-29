package com.ciandt.include_day3.services.endpoints;

import com.ciandt.include_day3.services.beans.IncidentesBean;
import com.ciandt.include_day3.services.services.IncidentesService;
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
    name = "incidentes",
    version = "v1",
    namespace = @ApiNamespace(
            ownerDomain = "services.include_day3.ciandt.com",
            ownerName = "services.include_day3.ciandt.com",
            packagePath = ""
    )
)
public class IncidentesEndpoint {

    private IncidentesService incidentesService;

    public IncidentesEndpoint() {
        incidentesService = new IncidentesService();
    }

    @ApiMethod(name = "getIncidentes", path = "incidentes", httpMethod = ApiMethod.HttpMethod.GET)
    public List<IncidentesBean> getIncidentes(@Nullable @Named("latitude") Float latitude, @Nullable @Named("longitude") Float longitude) throws NotFoundException {
        if(latitude == null || longitude == null)
            return incidentesService.list();
        else
            return incidentesService.list(latitude, longitude, null);
    }

    @ApiMethod(name = "getIncidentesByLocationAndRadius", path = "usuarios/{latitude}/{longitude}/{raio}", httpMethod = "GET")
    public List<IncidentesBean> getIncidentesByLocationAndRadius(@Named("latitude") float latitude, @Named("longitude") float longitude, @Named("raio") Double raio) throws NotFoundException {
        return incidentesService.list(latitude, longitude, raio);
    }

    @ApiMethod(name = "getIncidente", path = "incidentes/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public IncidentesBean getIncidente(@Named("id") Long id) throws NotFoundException {
        return incidentesService.getById(id);
    }

    @ApiMethod(name = "insertIncidente", path = "incidentes", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertIncidente(IncidentesBean tipo) throws ConflictException, NotFoundException {
        incidentesService.insert(tipo);
    }

    @ApiMethod(name = "updateIncidente", path = "incidentes", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateIncidente(IncidentesBean tipo) throws NotFoundException, ConflictException {
        incidentesService.update(tipo);
    }

    @ApiMethod(name = "deleteIncidente", path = "incidentes/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void deleteIncidente(@Named("id") Long id) throws NotFoundException, ConflictException {
        incidentesService.remove(id);
    }

}
