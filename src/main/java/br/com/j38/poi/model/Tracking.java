package br.com.j38.poi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tracking")
public class Tracking {
    @Id
    private String id;
    private LocalDateTime positionDate;
    private int velocity;
    private boolean ignition;
    private Location location;
    @DBRef
    private Vehicle vehicle;
}
