package com.ciandt.include_day3.services.services;

import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.ciandt.include_day3.services.dao.TipoIncidenteDao;
import com.ciandt.include_day3.services.services.interfaces.ITipoIncidenteService;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

import java.util.List;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class TipoIncidenteService implements ITipoIncidenteService {

    private TipoIncidenteDao tipoIncidenteDao;

    public TipoIncidenteService() {
        tipoIncidenteDao = new TipoIncidenteDao();
    }

    @Override
    public List<TipoIncidenteBean> list() {
        return tipoIncidenteDao.listAll();
    }

    @Override
    public List<TipoIncidenteBean> list(String descricao) throws NotFoundException {
        List<TipoIncidenteBean> list = tipoIncidenteDao.listByProperty("descricao", descricao);

        if(list == null || list.size() < 1) {
            throw new NotFoundException("Tipo de incidente não encontrado");
        }

        return list;
    }

    @Override
    public TipoIncidenteBean getById(Long id) throws NotFoundException {
        TipoIncidenteBean item = tipoIncidenteDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Tipo de incidente não encontrado");
        }

        return item;
    }

    @Override
    public void insert(TipoIncidenteBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Usuário não informado.");
        }
        else if(item.getDescricao() == null || item.getDescricao().isEmpty())
        {
            throw new ConflictException("Nome do usuário não informado.");
        }

        TipoIncidenteBean u = tipoIncidenteDao.getByProperty("descricao", item.getDescricao());

        if(u != null)
        {
            throw new ConflictException("Descrição já cadastrado: " + u.getDescricao());
        }

        tipoIncidenteDao.insert(item);
    }

    @Override
    public void update(TipoIncidenteBean item) throws ConflictException, NotFoundException {
        if(item == null)
        {
            throw new ConflictException("Usuário não informado.");
        }
        else if(item.getDescricao() == null || item.getDescricao().isEmpty())
        {
            throw new ConflictException("Nome do usuário não informado.");
        }


        TipoIncidenteBean u = tipoIncidenteDao.getById(item.getId());

        if(u == null) {
            throw new NotFoundException("Tipo de incidente não encontrado");
        }

        u = tipoIncidenteDao.getByProperty("descricao", item.getDescricao());

        if(u != null && !u.getId().equals(item.getId()))
        {
            throw new ConflictException("Descrição já cadastrada: " + u.getDescricao());
        }

        tipoIncidenteDao.update(item);
    }

    @Override
    public void remove(long id) throws ConflictException, NotFoundException {
        TipoIncidenteBean item = tipoIncidenteDao.getByKey(id);

        if(item == null) {
            throw new NotFoundException("Tipo de Incidente não encontrado");
        }

        tipoIncidenteDao.delete(item);
    }
}
