package br.com.j38.poi.controller;

import br.com.j38.poi.DTO.POIDTO;
import br.com.j38.poi.model.Location;
import br.com.j38.poi.model.POI;
import br.com.j38.poi.service.POIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "POI")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/poi")
public class POIController {

    @Autowired
    private POIService poiService;

    @ApiOperation(value = "Create", nickname = "createPOI")
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody @Valid POIDTO poidto){
        Location location = new Location();
        location.setCoordinates(poidto.getCoordinates());
        POI poi = POI.builder()
                .name(poidto.getName())
                .radius(poidto.getRadius())
                .location(location)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(poiService.create(poi));
    }

    @ApiOperation(value = "List", nickname = "ListAllPOIs")
    @GetMapping
    public ResponseEntity<Page<POI>> getAll(@PageableDefault(page = 0,size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                    Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(poiService.findAll(pageable));
    }

}
