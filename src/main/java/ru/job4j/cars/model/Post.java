package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "auto_post")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String description;
    private LocalDateTime created;
    private int userId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistory = new ArrayList<>();
}
