package br.com.j38.poi.repository;

import br.com.j38.poi.model.POI;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface POIRepository extends MongoRepository<POI, String>{
    boolean existsByName(String name);

    @Aggregation(pipeline = {"{$sort: {radius: -1}}", "{$limit: 1}"})
    POI maxRadius();

    @Query("{location: { $near: { $geometry: { type: \"Point\", coordinates:[?0, ?1]}, $maxDistance:?2}}}")
    Optional<POI> geoNear(Double lat, Double lon, int dist);

}
