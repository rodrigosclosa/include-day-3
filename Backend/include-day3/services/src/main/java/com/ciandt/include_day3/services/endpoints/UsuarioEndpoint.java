package com.ciandt.include_day3.services.endpoints;

import com.ciandt.include_day3.services.beans.UsuariosBean;
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
    name = "usuario",
    version = "v1",
    namespace = @ApiNamespace(
            ownerDomain = "services.include_day3.ciandt.com",
            ownerName = "services.include_day3.ciandt.com",
            packagePath = ""
    )
)
public class UsuarioEndpoint {

    private UsuarioService usuarioService;

    public UsuarioEndpoint() {
        usuarioService = new UsuarioService();
    }

    @ApiMethod(name = "getUsuarios", path = "usuarios", httpMethod = "GET")
    public List<UsuariosBean> getUsuarios(@Nullable @Named("email") String email) throws NotFoundException {
        if(email == null || email.isEmpty())
            return usuarioService.list();
        else
            return usuarioService.list(email);
    }

    @ApiMethod(name = "getUsuariosByLocation", path = "usuarios/{latitude}/{longitude}", httpMethod = "GET")
    public List<UsuariosBean> getUsuariosByLocation(@Named("latitude") float latitude, @Named("longitude") float longitude) throws NotFoundException {
        return usuarioService.list(latitude, longitude, null);
    }

    @ApiMethod(name = "getUsuariosByLocationAndRadius", path = "usuarios/{latitude}/{longitude}/{raio}", httpMethod = "GET")
    public List<UsuariosBean> getUsuariosByLocationAndRadius(@Named("latitude") float latitude, @Named("longitude") float longitude, @Named("raio") Double raio) throws NotFoundException {
        return usuarioService.list(latitude, longitude, raio);
    }

    @ApiMethod(name = "getUsuario", path = "usuarios/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public UsuariosBean getUsuario(@Named("id") Long id) throws NotFoundException {
        return usuarioService.getById(id);
    }

    @ApiMethod(name = "insertUsuario", path = "usuarios", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertUsuario(UsuariosBean usuario) throws ConflictException, NotFoundException {
        usuarioService.insert(usuario);
    }

    @ApiMethod(name = "updateUsuario", path = "usuarios", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateUsuario(UsuariosBean veiculo) throws NotFoundException, ConflictException {
        usuarioService.update(veiculo);
    }

    @ApiMethod(name = "removeUsuario", path = "usuarios/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeUsuario(@Named("id") Long id) throws NotFoundException, ConflictException {
        usuarioService.remove(id);
    }

}
