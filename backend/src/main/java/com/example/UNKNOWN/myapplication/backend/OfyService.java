package com.example.UNKNOWN.myapplication.backend;

import com.example.UNKNOWN.myapplication.backend.model.Aula;
import com.example.UNKNOWN.myapplication.backend.model.AulaRelationUser;
import com.example.UNKNOWN.myapplication.backend.model.Materia;
import com.example.UNKNOWN.myapplication.backend.model.MateriaRelationUser;
import com.example.UNKNOWN.myapplication.backend.model.RegistrationRecord;
import com.example.UNKNOWN.myapplication.backend.model.User;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(User.class);
        ObjectifyService.register(Materia.class);
        ObjectifyService.register(Aula.class);
        ObjectifyService.register(MateriaRelationUser.class);
        ObjectifyService.register(AulaRelationUser.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
