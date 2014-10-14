package com.fypj.mymodule.api.util;

import com.fypj.mymodule.api.controller.EventEndpoint;
import com.fypj.mymodule.api.model.Appointment;
import com.fypj.mymodule.api.model.Clinic;
import com.fypj.mymodule.api.model.Event;
import com.fypj.mymodule.api.model.MedicalHistory;
import com.fypj.mymodule.api.model.Subsidies;
import com.fypj.mymodule.api.model.User;
import com.fypj.mymodule.api.model.User_Packages;
import com.fypj.mymodule.api.model.User_Subsidies;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
/**
 * Created by L33525 on 7/10/2014.
 */

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 *
 */
public class OfyService {

    static{
        ObjectifyService.register(Appointment.class);
        ObjectifyService.register(Clinic.class);
        ObjectifyService.register(Event.class);
        ObjectifyService.register(MedicalHistory.class);
        ObjectifyService.register(Subsidies.class);
        ObjectifyService.register(User.class);
        ObjectifyService.register(User_Packages.class);
        ObjectifyService.register(User_Subsidies.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}