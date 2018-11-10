package com.example.UNKNOWN.myapplication.backend.endpoints;

import com.example.UNKNOWN.myapplication.backend.model.MateriaRelationUser;
import com.example.UNKNOWN.myapplication.backend.model.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.ArrayList;
import java.util.Arrays;
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
public class UserEndpoint {

    private static final Logger log = Logger.getLogger(UserEndpoint.class.getName());

    @ApiMethod(name = "users.list",path = "users/list", httpMethod = "GET")
    public CollectionResponse<User>  listUser() {
        List<User> users = ofy().load().type(User.class).list();
        return CollectionResponse.<User>builder().setItems(users).build();
    }
    @ApiMethod(name = "users.get",path = "users/get", httpMethod = "GET")
    public CollectionResponse<User>  getUser(@Named("name") String name,
                                             @Named("deviceSerial") String deviceSerial,
                                             @Named("matricula") Long matricula) {

        if (deviceSerial == null || deviceSerial.isEmpty()){
            return null;
        }

        List<User> users = ofy().load().type(User.class).filter("serialNumber",deviceSerial).limit(1).list();
        if (users.size() == 0){
            User user = new User();
            user.setMatricula(matricula);
            user.setName(name);
            user.setUserType(0L);
            user.setSerialNumber(deviceSerial);
            ofy().save().entity(user).now();
            return CollectionResponse.<User>builder().setItems(Arrays.asList(user)).build();
        }
        return CollectionResponse.<User>builder().setItems(users).build();
    }
    @ApiMethod(name = "users.materia.get",path = "users/materias/get", httpMethod = "GET")
    public CollectionResponse<User>  getUsersMateria(@Named("materia") Long materia) {
        List<MateriaRelationUser> materiaRelationUsers = ofy().load().type(MateriaRelationUser.class)
                .filter("materia",materia).list();
        List<Long> userIds = new ArrayList<>();
        for(MateriaRelationUser mru : materiaRelationUsers){
            userIds.add(mru.getUser());
        }
        List<User> users = new ArrayList<User>();
        for(User u : ofy().load().type(User.class).list()){
            if(userIds.contains(u.getId())){
                users.add(u);
            }
        }

        return CollectionResponse.<User>builder().setItems(users).build();
    }

    @ApiMethod(name = "users.create",path = "users/create", httpMethod = "POST")
    public void createUser(User user) {
        ofy().save().entity(user).now();
    }
//    @ApiMethod(name = "users.salvar",path = "users/salvar", httpMethod = "POST")
//    public void salvarUser(User user) {
//        ofy().save().entity(user).now();
//    }
}
