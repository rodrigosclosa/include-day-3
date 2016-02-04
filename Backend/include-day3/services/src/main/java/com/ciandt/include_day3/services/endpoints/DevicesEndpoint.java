package com.ciandt.include_day3.services.endpoints;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.beans.UsuariosBean;
import com.ciandt.include_day3.services.services.DevicesService;
import com.ciandt.include_day3.services.services.UsuarioService;
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
    name = "devices",
    version = "v1",
    namespace = @ApiNamespace(
            ownerDomain = "services.include_day3.ciandt.com",
            ownerName = "services.include_day3.ciandt.com",
            packagePath = ""
    )
)
public class DevicesEndpoint {

    private DevicesService devicesService;

    public DevicesEndpoint() {
        devicesService = new DevicesService();
    }

    @ApiMethod(name = "getDevices", path = "devices", httpMethod = "GET")
    public List<DevicesBean> getDevices(@Nullable @Named("latitude") Float latitude, @Nullable @Named("longitude") Float longitude) throws NotFoundException {
        if(latitude == null && longitude == null)
            return devicesService.list();
        else
            return devicesService.list(latitude, longitude, null);
    }

    @ApiMethod(name = "getDevicesByLocationAndRadius", path = "devices/{latitude}/{longitude}/{raio}", httpMethod = "GET")
    public List<DevicesBean> getDevicesByLocationAndRadius(@Named("latitude") float latitude, @Named("longitude") float longitude, @Named("raio") Double raio) throws NotFoundException {
        return devicesService.list(latitude, longitude, raio);
    }

    @ApiMethod(name = "getDevice", path = "devices/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public DevicesBean getDevice(@Named("id") Long id) throws NotFoundException {
        return devicesService.getById(id);
    }

    @ApiMethod(name = "insertDevice", path = "devices", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertDevice(DevicesBean device) throws ConflictException, NotFoundException {
        devicesService.insert(device);
    }

    @ApiMethod(name = "updateDevice", path = "devices", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateDevice(DevicesBean device) throws NotFoundException, ConflictException {
        devicesService.update(device);
    }

    @ApiMethod(name = "removeDevice", path = "devices/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeDevice(@Named("id") Long id) throws NotFoundException, ConflictException {
        devicesService.remove(id);
    }

}
