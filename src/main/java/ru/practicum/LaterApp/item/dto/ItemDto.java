package ru.practicum.LaterApp.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    @NotBlank
    private Long userId;
    @NotBlank
    private String url;
    private List<String> tags = new ArrayList<>();
}
