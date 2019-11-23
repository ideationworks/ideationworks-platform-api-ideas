package ideation.platform.api.ideation;

import ideation.platform.api.organizations.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdeasRepository extends PagingAndSortingRepository<Idea, Long> {

    Optional<Idea> getByUuid(UUID uuid);

    Optional<Idea> getByUuidAndOrganization(UUID uuid, Organization organization);

    Page<Idea> getByOrganization(Organization organization, Pageable pageable);


}
