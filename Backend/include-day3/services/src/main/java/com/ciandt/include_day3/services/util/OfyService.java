package com.ciandt.include_day3.services.util;

import com.ciandt.include_day3.services.beans.DevicesBean;
import com.ciandt.include_day3.services.beans.IncidentesBean;
import com.ciandt.include_day3.services.beans.TipoIncidenteBean;
import com.ciandt.include_day3.services.beans.TimesBean;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by rodrigosclosa on 29/12/15.
 */
public class OfyService {

    static {
        ObjectifyService.register(TipoIncidenteBean.class);
        ObjectifyService.register(DevicesBean.class);
        ObjectifyService.register(TimesBean.class);
        ObjectifyService.register(IncidentesBean.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}