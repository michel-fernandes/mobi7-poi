package br.com.j38.poi.service;

import br.com.j38.poi.exception.BusinessRulesException;
import br.com.j38.poi.model.Vehicle;
import br.com.j38.poi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle create(Vehicle vehicle){
        if(existsByPlate(vehicle.getPlate()))
            throw new BusinessRulesException(
                    String.format("Conflict: License Plate Vehicle %s is already in use!", vehicle.getPlate()));
        return vehicleRepository.save(vehicle);
    }
    
    private boolean existsByPlate(String plate){
        return vehicleRepository.existsByPlate(plate);
    }

    public Vehicle getByID(String id){
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if(vehicleOptional.isEmpty())
            throw new BusinessRulesException(
                    String.format("Conflict: The Vehicle with ID %s isn't exists!", id));
        return vehicleOptional.get();
    }

    public Page<Vehicle> findAll(Pageable pageable){
        return vehicleRepository.findAll(pageable);
    }


}
