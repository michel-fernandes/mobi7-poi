package br.com.j38.poi.repository;

import br.com.j38.poi.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository  extends MongoRepository<Vehicle, String> {
    boolean existsByPlate(String plate);
}
