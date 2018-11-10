package com.example.UNKNOWN.myapplication.backend.endpoints;

import com.example.UNKNOWN.myapplication.backend.model.Aula;
import com.example.UNKNOWN.myapplication.backend.model.AulaRelationUser;
import com.example.UNKNOWN.myapplication.backend.model.Materia;
import com.example.UNKNOWN.myapplication.backend.model.MateriaRelationUser;
import com.example.UNKNOWN.myapplication.backend.model.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
public class AulaEndpoint {

    private static final Logger log = Logger.getLogger(AulaEndpoint.class.getName());

    @ApiMethod(name = "aulas.list", path = "aulas/list", httpMethod = "GET")
    public CollectionResponse<Aula> listAula() {
        List<Aula> aulas = ofy().load().type(Aula.class).list();
        return CollectionResponse.<Aula>builder().setItems(aulas).build();
    }

    @ApiMethod(name = "aulas.get", path = "aulas/get", httpMethod = "GET")
    public CollectionResponse<Aula> getAula(
            @Named("materia") Long materia) {
        List<Aula> aulas = ofy().load().type(Aula.class).filter("materia", materia).filter("status", 0).limit(1).list();
        return CollectionResponse.<Aula>builder().setItems(aulas).build();
    }
    @ApiMethod(name = "aulas.finalizar", path = "aulas/finalizar", httpMethod = "POST")
    public CollectionResponse<Aula> getFinalizarAula( @Named("aula") Long aula) {
        Aula a = ofy().load().type(Aula.class).id(aula).now();
        a.setStatus(1L);
        ofy().save().entity(a).now();
        return CollectionResponse.<Aula>builder().setItems(Arrays.asList(a)).build();
    }


    @ApiMethod(name = "aulas.materias.alunos.abertas", path = "users/materias/alunos/abertas", httpMethod = "GET")
    public CollectionResponse<Aula> getUserMateria(@Named("aluno") Long aluno) {

        List<MateriaRelationUser> materiaRelationUsers = ofy().load().type(MateriaRelationUser.class)
                .filter("user", aluno).list();
        if (materiaRelationUsers == null) {
            log.info("No find MateriaRelationUser with user " + aluno);
            return null;
        } else {
            log.info("Find " + materiaRelationUsers.size() + " MateriaRelationUser with user " + aluno);
        }

        List<Long> materiasids = new ArrayList<>();
        for (MateriaRelationUser mr : materiaRelationUsers) {
            materiasids.add(mr.getMateria());
            log.info("Materia " + mr.getMateria() + " add at list");
        }

        List<Aula> aulas = ofy().load().type(Aula.class).filter("status", Long.valueOf(0)).list();
        if (aulas == null || aulas.size() == 0) {
            log.info("No aula to user " + aluno);
            return null;
        } else {
            log.info("Find " + aulas.size() + " aulas with user " + aluno);
        }

        List<Aula> aulasOfAluno = new ArrayList<>();
        for (Aula aula : aulas) {
            log.info("Compare if " + aula.getMateria() + " contains in materiasids");
            if (materiasids.contains(aula.getMateria())) {
                List<AulaRelationUser> arus = ofy().load().type(AulaRelationUser.class)
                        .filter("aula", aula.getId())
                        .filter("aluno", aluno).list();

                log.info("Encontradas " + arus.size() + " aulaRelatiosUser, composite: aula_aluno  :" + aula.getId() + "_" + aluno);
                for (AulaRelationUser aru : arus) {
                    if (!aru.getPresente()) {
                        aulasOfAluno.add(aula);
                    }
                }
            }
        }

        return CollectionResponse.<Aula>builder().setItems(aulasOfAluno).build();
    }

    @ApiMethod(name = "aulas.user.presente", path = "aulas/user/presente", httpMethod = "POST")
    public CollectionResponse<AulaRelationUser> callPresente(@Named("aula") Long aula, @Named("aluno") Long aluno, @Nullable @Named("presente") Boolean presente) {
        List<AulaRelationUser> arus = ofy().load().type(AulaRelationUser.class)
                .filter("aula", aula)
                .filter("aluno", aluno).list();

        log.info("Encontradas " + arus.size() + " aulaRelatiosUser, user:" + aluno + " aula:" + aula);
        for (AulaRelationUser aru : arus) {
            aru.setPresente(presente == null ? !aru.getPresente(): presente.booleanValue() );
            aru.setDate(new Date());
            ofy().save().entity(aru).now();
        }

        return CollectionResponse.<AulaRelationUser>builder().setItems(arus).build();
    }
    @ApiMethod(name = "aulas.user.all", path = "aulas/user/all", httpMethod = "GET")
    public CollectionResponse<User> getAlunosInAula(@Named("aula") Long aula) {
        List<AulaRelationUser> arus = ofy().load().type(AulaRelationUser.class)
                .filter("aula", aula).list();
        List<User> alunos = new ArrayList<>();

        for (AulaRelationUser aru : arus) {
            User u = ofy().load().type(User.class).id(aru.getAluno()).now();
            u.setPresente(aru.getPresente());
            alunos.add(u);
        }

        return CollectionResponse.<User>builder().setItems(alunos).build();
    }

    @ApiMethod(name = "aulas.create", path = "aulas/create", httpMethod = "POST")
    public CollectionResponse<Aula> createAula(Aula aula) {

        ofy().save().entity(aula).now();

        Materia materias = ofy().load().type(Materia.class).id(aula.getMateria()).now();

        if (materias == null)
            return null;

        for (Long aluno : materias.getAlunos()) {
            AulaRelationUser aru = new AulaRelationUser();
            aru.setAluno(aluno);
            aru.setAula(aula.getId());
            aru.setPresente(false);
            aru.setComposite(aula.getId() + "_" + aluno);
            ofy().save().entity(aru).now();
        }


        return CollectionResponse.<Aula>builder().setItems(Arrays.asList(aula)).build();
    }
}
