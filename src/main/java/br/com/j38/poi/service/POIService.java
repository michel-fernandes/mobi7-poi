package br.com.j38.poi.service;

import br.com.j38.poi.exception.BusinessRulesException;
import br.com.j38.poi.model.Location;
import br.com.j38.poi.model.POI;
import br.com.j38.poi.repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class POIService {

    @Autowired
    private POIRepository poiRepository;

    public POI create(POI poi){
        if(existsByName(poi.getName()))
            throw new BusinessRulesException(
                    String.format("Conflict: POI %s is already in use!", poi.getName()));
        return poiRepository.save(poi);
    }

    private boolean existsByName(String name){
        return poiRepository.existsByName(name);
    }

    /*public POI getByID(String id){
        Optional<POI> poiOptional = poiRepository.findById(id);
        if(poiOptional.isEmpty())
            throw new BusinessRulesException(
                    String.format("Conflict: The POI with ID %s isn't exists!", id));
        return poiOptional.get();
    }*/

    public Page<POI> findAll(Pageable pageable){
        return poiRepository.findAll(pageable);
    }

    public Optional<POI> poiByLocation(Location location) {
        POI poiMax = poiRepository.maxRadius();
        System.out.println(poiMax.getRadius());
        Optional<POI> poiNear = poiRepository.geoNear(
                location.getCoordinates().get(0),
                location.getCoordinates().get(1),
                poiMax.getRadius());
        System.out.println(poiNear);
        return poiNear;
    }
}
