package ru.practicum.LaterApp.note;

import lombok.*;
import ru.practicum.LaterApp.item.model.Item;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item_notes")
public class ItemNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Item item;

    private String text;

    @Column(name = "note_date")
    private LocalDateTime dateOfNote = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemNote)) return false;
        return id != null && id.equals(((ItemNote) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
