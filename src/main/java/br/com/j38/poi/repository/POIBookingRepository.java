package br.com.j38.poi.repository;

import br.com.j38.poi.model.POI;
import br.com.j38.poi.model.POIBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface POIBookingRepository extends MongoRepository<POIBooking, String> {
    Optional<POIBooking> findByPlateAndIsPresent(String plate, boolean isPresent);
    @Query("{closedAt: {$gte:?0, $lt: ?1}}")
    List<POIBooking> findBetweenDates(LocalDateTime dateFrom, LocalDateTime dateTo);
    //List<POIBooking> findByClosedAtBetween(Range<ChronoLocalDateTime<?>> );
    @Query("{plate: ?0, closedAt: {$gte:?1, $lt: ?2}}")
    List<POIBooking> findBetweenDatesAndPlate(String plate, LocalDateTime dateFrom, LocalDateTime dateTo);
    @Query("{plate: ?0}")
    List<POIBooking> findByPlate(String plate);


}
