package com.example.UNKNOWN.myapplication.backend.endpoints;

import com.example.UNKNOWN.myapplication.backend.model.Materia;
import com.example.UNKNOWN.myapplication.backend.model.MateriaRelationUser;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.example.UNKNOWN.myapplication.backend.OfyService.ofy;

@Api(
        name = "presente",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.UNKNOWN.example.com",
                ownerName = "backend.myapplication.UNKNOWN.example.com",
                packagePath = ""
        )
)
public class MateriaEndpoint {

    private static final Logger log = Logger.getLogger(MateriaEndpoint.class.getName());

    @ApiMethod(name = "materias.list",path = "materias/list", httpMethod = "GET")
    public CollectionResponse<Materia>  listMateria() {
        List<Materia> materias = ofy().load().type(Materia.class).list();
        return CollectionResponse.<Materia>builder().setItems(materias).build();
    }

    @ApiMethod(name = "materias.create",path = "materias/create", httpMethod = "POST")
    public void createMateria(Materia materia) {
        ofy().save().entity(materia).now();
        List<MateriaRelationUser> materiaRelationUsers = new ArrayList<>();
        for (Long userId: materia.getAlunos()) {
            MateriaRelationUser mru = new MateriaRelationUser();
            mru.setMateria(materia.getId());
            mru.setUser(userId);
            mru.setComposite(mru.getMateria() + "_" + mru.getUser());
            materiaRelationUsers.add(mru);
        }
        ofy().save().entities(materiaRelationUsers);
    }


    @ApiMethod(name = "materias.user.list",path = "materias/user/list", httpMethod = "GET")
    public CollectionResponse<MateriaRelationUser>  listMateriaRelationUser() {
        List<MateriaRelationUser> materiaRelationUsers = ofy().load().type(MateriaRelationUser.class).list();
        return CollectionResponse.<MateriaRelationUser>builder().setItems(materiaRelationUsers).build();
    }

    @ApiMethod(name = "materias.user.create",path = "materias/user/create", httpMethod = "POST")
    public void createMateriaRelationUser(MateriaRelationUser materiaRelationUser) {
        ofy().save().entity(materiaRelationUser).now();
    }

}
