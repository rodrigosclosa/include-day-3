package com.ciandt.include_day3.services.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.google.appengine.api.datastore.Query.Filter;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.ciandt.include_day3.services.util.OfyService.ofy;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class GenericDao<T> implements IGenericDao<T> {

    protected Class<T> clazz;

    @SuppressWarnings("unchecked")
    public GenericDao() {
        clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public Key<T> save(T entity) {
        return ofy().save().entity(entity).now();
    }

    @Override
    public void insert(T entity) {
        save(entity);
    }

    @Override
    public void delete(T entity) {
        ofy().delete().entity(entity).now();
    }

    @Override
    public void update(T entity) {
        save(entity);
    }

    @Override
    public List<T> listAll() {
        Query<T> query = ofy().load().type(clazz);
        return query.list();
    }

    @Override
    public T getByProperty(String propName, Object propValue) {
        return ofy().load().type(clazz).filter(propName, propValue).first().now();
    }

    @Override
    public T getByFilter(Filter filtro) {
        return ofy().load().type(clazz).filter(filtro).first().now();
    }

    @Override
    public T getById(Long id) {
        return ofy().load().type(clazz).id(id).now();
    }

    @Override
    public T getByKey(Long id) {
        return ofy().load().key(Key.create(clazz, id)).now();
    }

    @Override
    public List<T> listByProperty(String propName, Object propValue) {
        return ofy().load().type(clazz).filter(propName, propValue).list();
    }

}
