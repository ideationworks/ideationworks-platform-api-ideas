package ideation.platform.api.ideation;

import ideation.platform.api.organizations.users.User;
import ideation.platform.api.organizations.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class IdeasService {

    private final IdeasRepository ideasRepository;
    private final UsersService    usersService;

    @Autowired
    public IdeasService(final IdeasRepository ideasRepository,
                        final UsersService usersService) {

        this.ideasRepository = ideasRepository;
        this.usersService = usersService;

    }

    public Page<Idea> getByPrincipalOrganization(Principal principal, Pageable pageable) {

        Optional<User> optionalUser = usersService.getPrincipalUser(principal);

        if (optionalUser.isPresent()) {

            return ideasRepository.getByOrganization(optionalUser.get().getOrganization(), pageable);

        }

        return Page.empty();

    }

    public Optional<Idea> getByUUIDandPrincipalOrganization(UUID uuid, Principal principal) {

        Optional<User> optionalUser = usersService.getPrincipalUser(principal);

        if (optionalUser.isPresent()) {

            return ideasRepository.getByUuidAndOrganization(uuid, optionalUser.get().getOrganization());

        }

        return Optional.empty();

    }

    @Transactional
    public boolean deleteByUUIDandPrincipal(UUID uuid, Principal principal) {

        Optional<Idea> optionalIdea = getByUUIDandPrincipalOrganization(uuid, principal);

        if (optionalIdea.isPresent()) {

            ideasRepository.delete(optionalIdea.get());

            return true;

        }
        return false;


    }

    public Optional<Idea> create(Idea idea, Principal principal) {

        Optional<User> optionalUser = usersService.getPrincipalUser(principal);

        if (optionalUser.isPresent()) {

            idea.setOrganization(optionalUser.get().getOrganization());

            return Optional.of(ideasRepository.save(idea));

        }

        return Optional.empty();

    }


}
