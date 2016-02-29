package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.IncidentesBean;
import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.ciandt.include_day3.services.beans.TimesBean;
import com.ciandt.include_day3.services.config.Params;
import com.ciandt.include_day3.services.dao.IncidentesDao;
import com.ciandt.include_day3.services.dao.TipoIncidenteDao;
import com.ciandt.include_day3.services.dao.TimesDao;
import com.ciandt.include_day3.services.services.interfaces.IIncidentesService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Query;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class IncidentesService implements IIncidentesService {

    private IncidentesDao incidentesDao;
    private TimesDao timesDao;
    private TipoIncidenteDao tipoIncidenteDao;

    public IncidentesService() {
        incidentesDao = new IncidentesDao();
        timesDao = new TimesDao();
        tipoIncidenteDao = new TipoIncidenteDao();
    }

    @Override
    public List<IncidentesBean> list() {
        List<IncidentesBean> lista = incidentesDao.listAll();

        for (IncidentesBean in : lista) {
            TimesBean u = timesDao.getById(in.getIdUsuario());
            in.setUsuario(u);

            TipoIncidenteBean tp = tipoIncidenteDao.getById(in.getIdTipoIncidente());
            in.setTipoIncidente(tp);
        }

        return incidentesDao.listAll();
    }

    @Override
    public List<IncidentesBean> list(float latitude, float longitude, @Nullable Double raio) throws NotFoundException {
        GeoPt center = new GeoPt(latitude, longitude);
        double radius = raio == null ? Params.getInstance().getRaio() : raio;
        Query.Filter filtro = new Query.StContainsFilter("localizacao", new Query.GeoRegion.Circle(center, radius));

        List<IncidentesBean> list = incidentesDao.listByFilter(filtro);

        if(list == null || list.size() < 1) {
            throw new NotFoundException("Incidente não encontrado");
        }

        for (IncidentesBean in : list) {
            TimesBean u = timesDao.getById(in.getIdUsuario());
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

        TimesBean u = timesDao.getById(item.getIdUsuario());
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
        else if(item.getLocalizacao() == null) {
            throw new ConflictException("Localização inválida para o device.");
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
        else if(item.getLocalizacao() == null) {
            throw new ConflictException("Localização inválida para o device.");
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
