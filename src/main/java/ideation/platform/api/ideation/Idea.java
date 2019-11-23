package ideation.platform.api.ideation;

import ideation.platform.api.categories.Category;
import ideation.platform.api.organizations.Organization;
import ideation.platform.api.tags.Tag;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Range(max = 4294967295L)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID uuid;

    @OneToOne
    private Organization organization;

    private String name;
    private String description;

    private LocalDateTime stampCreated;
    private LocalDateTime stampUpdated;

    @ManyToMany
    private List<Idea> children;

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany
    private List<Category> categories;

}
