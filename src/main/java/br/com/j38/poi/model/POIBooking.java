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
@Document(collection = "poi-booking")
public class POIBooking {
    @Id
    private String id;
    @DBRef
    private POI poi;
    @DBRef
    private Tracking checkIn;
    private boolean isPresent;
    private Long secondsTimeHosted;
    private LocalDateTime closedAt;
    private String plate;
    @DBRef
    private Tracking checkOut;
}
