package ru.practicum.laterapp.item.model;

import lombok.*;
import ru.practicum.laterapp.user.model.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter @ToString
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @Column(name = "url")
    private String url;
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "name")
    private Set<String> tags = new HashSet<>();
    private String resolvedUrl;
    private String mimeType;
    private String title;
    @Column(name = "has_image")
    private boolean hasImage;
    @Column(name = "has_video")
    private boolean hasVideo;
    private boolean unread = true;
    @Column(name = "date_resolved")
    private Instant dateResolved;


    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof Item)) return false;
        return id != null && id.equals(((Item) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
