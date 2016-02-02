package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.IncidentesBean;
import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.ciandt.include_day3.services.beans.UsuariosBean;
import com.ciandt.include_day3.services.dao.IncidentesDao;
import com.ciandt.include_day3.services.dao.TipoIncidenteDao;
import com.ciandt.include_day3.services.dao.UsuarioDao;
import com.ciandt.include_day3.services.services.interfaces.IIncidentesService;
import com.ciandt.include_day3.services.services.interfaces.ITipoIncidenteService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class IncidentesService implements IIncidentesService {

    private IncidentesDao incidentesDao;
    private UsuarioDao usuarioDao;
    private TipoIncidenteDao tipoIncidenteDao;

    public IncidentesService() {
        incidentesDao = new IncidentesDao();
        usuarioDao = new UsuarioDao();
        tipoIncidenteDao = new TipoIncidenteDao();
    }

    @Override
    public List<IncidentesBean> list() {
        List<IncidentesBean> lista = incidentesDao.listAll();

        for (IncidentesBean in : lista) {
            UsuariosBean u = usuarioDao.getById(in.getIdUsuario());
            in.setUsuario(u);

            TipoIncidenteBean tp = tipoIncidenteDao.getById(in.getIdTipoIncidente());
            in.setTipoIncidente(tp);
        }

        return incidentesDao.listAll();
    }

    @Override
    public List<IncidentesBean> list(Double latitude, Double longitude) throws NotFoundException {
        List<IncidentesBean> list = incidentesDao.listByProperty("latitude", latitude);

        if(list == null || list.size() < 1) {
            throw new NotFoundException("Incidente não encontrado");
        }

        for (IncidentesBean in : list) {
            UsuariosBean u = usuarioDao.getById(in.getIdUsuario());
            in.setUsuario(u);

            TipoIncidenteBean tp = tipoIncidenteDao.getById(in.getIdTipoIncidente());
            in.setTipoIncidente(tp);
        }

        return list;
    }

    @Override
    public IncidentesBean getById(Long id) throws NotFoundException {
        IncidentesBean item = incidentesDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Incidente não encontrado");
        }

        UsuariosBean u = usuarioDao.getById(item.getIdUsuario());
        item.setUsuario(u);

        TipoIncidenteBean tp = tipoIncidenteDao.getById(item.getIdTipoIncidente());
        item.setTipoIncidente(tp);

        return item;
    }

    @Override
    public void insert(IncidentesBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Incidente não informado.");
        }
        else if(!(item.getGravidade() > 0))
        {
            throw new ConflictException("Gravidade do incidente não informada.");
        }
        else if(item.getData() == null)
        {
            throw new ConflictException("Data do incidente não informada.");
        }
        else if(item.getIdUsuario() == null)
        {
            throw new ConflictException("Usuário reportador do incidente não informado.");
        }
        else if(item.getIdTipoIncidente() == null)
        {
            throw new ConflictException("Tipo do incidente não informado.");
        }
        else if(item.getLogradouro() == null || item.getLogradouro().isEmpty())
        {
            throw new ConflictException("Logradouro do usuário não informado.");
        }
        else if(item.getCidade() == null || item.getCidade().isEmpty())
        {
            throw new ConflictException("Cidade do usuário não informada.");
        }
        else if(item.getEstado() == null || item.getEstado().isEmpty())
        {
            throw new ConflictException("Estado do usuário não informado.");
        }
        else if(item.getLatitude() == null || (item.getLatitude() < -90.0d && item.getLatitude() > 90.0d))
        {
            throw new ConflictException("Latitude inválida para o usuário.");
        }
        else if(item.getLongitude() == null || (item.getLongitude() < -180.0d && item.getLongitude() > 180.0d))
        {
            throw new ConflictException("Longitude inválida para o usuário.");
        }

        incidentesDao.insert(item);
    }

    @Override
    public void update(IncidentesBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Incidente não informado.");
        }
        else if(!(item.getGravidade() > 0))
        {
            throw new ConflictException("Gravidade do incidente não informada.");
        }
        else if(item.getData() == null)
        {
            throw new ConflictException("Data do incidente não informada.");
        }
        else if(item.getIdUsuario() == null)
        {
            throw new ConflictException("Usuário reportador do incidente não informado.");
        }
        else if(item.getIdTipoIncidente() == null)
        {
            throw new ConflictException("Tipo do incidente não informado.");
        }
        else if(item.getLogradouro() == null || item.getLogradouro().isEmpty())
        {
            throw new ConflictException("Logradouro do usuário não informado.");
        }
        else if(item.getCidade() == null || item.getCidade().isEmpty())
        {
            throw new ConflictException("Cidade do usuário não informada.");
        }
        else if(item.getEstado() == null || item.getEstado().isEmpty())
        {
            throw new ConflictException("Estado do usuário não informado.");
        }
        else if(item.getLatitude() == null || (item.getLatitude() < -90.0d && item.getLatitude() > 90.0d))
        {
            throw new ConflictException("Latitude inválida para o usuário.");
        }
        else if(item.getLongitude() == null || (item.getLongitude() < -180.0d && item.getLongitude() > 180.0d))
        {
            throw new ConflictException("Longitude inválida para o usuário.");
        }

        IncidentesBean u = incidentesDao.getById(item.getId());

        if(u == null) {
            throw new NotFoundException("Incidente não encontrado");
        }

        incidentesDao.update(item);
    }

    @Override
    public void remove(long id) throws ConflictException, NotFoundException {
        IncidentesBean item = incidentesDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Incidente não encontrado");
        }

        incidentesDao.delete(item);
    }
}
