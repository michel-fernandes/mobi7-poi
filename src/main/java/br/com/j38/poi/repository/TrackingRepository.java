package br.com.j38.poi.repository;

import br.com.j38.poi.model.Tracking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TrackingRepository extends MongoRepository<Tracking, String> {

}
