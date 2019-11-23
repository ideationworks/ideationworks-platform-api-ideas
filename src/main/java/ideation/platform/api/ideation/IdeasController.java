package ideation.platform.api.ideation;

import ideation.platform.api.common.Patterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ideas")
public class IdeasController {

    private final IdeasService ideasService;

    @Autowired
    public IdeasController(final IdeasService ideasService) {

        this.ideasService = ideasService;

    }

    @GetMapping
    public ResponseEntity<Page<Idea>> getAllByPrincipal(Principal principal, Pageable pageable) {

        return new ResponseEntity<>(ideasService.getByPrincipalOrganization(principal, pageable), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Idea> create(@RequestBody Idea idea, Principal principal) {

        Optional<Idea> optionalIdea = ideasService.create(idea, principal);

        if (optionalIdea.isPresent()) {

            return new ResponseEntity<>(optionalIdea.get(), HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

//        return optionalIdea.map(c -> new ResponseEntity<>(c, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));

    }

    @GetMapping(Patterns.UUIDv4)
    public ResponseEntity<Idea> getByUUID(@PathVariable("uuid") UUID uuid, Principal principal) {

        Optional<Idea> optionalIdea = ideasService.getByUUIDandPrincipalOrganization(uuid, principal);

        return optionalIdea.map(idea -> new ResponseEntity<>(idea, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping(Patterns.UUIDv4)
    public ResponseEntity<Boolean> deleteByUUIDandPrincipal(@PathVariable("uuid") UUID uuid, Principal principal) {

        if (ideasService.deleteByUUIDandPrincipal(uuid, principal)) {

            return new ResponseEntity<>(true, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
