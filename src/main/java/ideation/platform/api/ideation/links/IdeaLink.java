package ideation.platform.api.ideation.links;

import ideation.platform.api.ideation.Idea;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ideas_links")
public class IdeaLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Range(max = 4294967295L)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    private Idea idea;

}
