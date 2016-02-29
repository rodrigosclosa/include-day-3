package com.ciandt.include_day3.services.endpoints;

import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.ciandt.include_day3.services.services.TipoIncidenteService;
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
    name = "tipoincidente",
    version = "v1",
    namespace = @ApiNamespace(
            ownerDomain = "services.include_day3.ciandt.com",
            ownerName = "services.include_day3.ciandt.com",
            packagePath = ""
    )
)
public class TipoIncidenteEndpoint {

    private TipoIncidenteService tipoIncidenteService;

    public TipoIncidenteEndpoint() {
        tipoIncidenteService = new TipoIncidenteService();
    }

    @ApiMethod(name = "getTiposIncidente", path = "tipoincidente", httpMethod = ApiMethod.HttpMethod.GET)
    public List<TipoIncidenteBean> getTiposIncidente(@Nullable @Named("search") String descricao) throws NotFoundException {
        if(descricao == null || descricao.isEmpty())
            return tipoIncidenteService.list();
        else
            return tipoIncidenteService.list(descricao);
    }

    @ApiMethod(name = "getTipoIncidente", path = "tipoincidente/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public TipoIncidenteBean getTipoIncidente(@Named("id") Long id) throws NotFoundException {
        return tipoIncidenteService.getById(id);
    }

    @ApiMethod(name = "insertTipoIncidente", path = "tipoincidente", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertTipoIncidente(TipoIncidenteBean tipo) throws ConflictException, NotFoundException {
        tipoIncidenteService.insert(tipo);
    }

    @ApiMethod(name = "updateTipoIncidente", path = "tipoincidente", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateTipoIncidente(TipoIncidenteBean tipo) throws NotFoundException, ConflictException {
        tipoIncidenteService.update(tipo);
    }

    @ApiMethod(name = "deleteTipoIncidente", path = "tipoincidente/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void deleteTipoIncidente(@Named("id") Long id) throws NotFoundException, ConflictException {
        tipoIncidenteService.remove(id);
    }

}
