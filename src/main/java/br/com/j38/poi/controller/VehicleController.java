package br.com.j38.poi.controller;

import br.com.j38.poi.DTO.TrackingDTO;
import br.com.j38.poi.DTO.VehicleDTO;
import br.com.j38.poi.model.Location;
import br.com.j38.poi.model.Tracking;
import br.com.j38.poi.model.Vehicle;
import br.com.j38.poi.service.TrackingService;
import br.com.j38.poi.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(tags = "Vehicle")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private TrackingService trackingService;

    @ApiOperation(value = "Create", nickname = "createVehicle")
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody @Valid VehicleDTO vehicleDTO){
        var vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDTO, vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(vehicle));
    }

    @ApiOperation(value = "List", nickname = "ListAllVehicles")
    @GetMapping
    public ResponseEntity<Page<Vehicle>> getAll(@PageableDefault(page = 0,size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                    Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAll(pageable));
    }

    @ApiOperation(value = "Track point", nickname = "trackPoint")
    @PostMapping("/{vehicleID}")
    public ResponseEntity<?> saveTrackPoint(@PathVariable String vehicleID, @RequestBody @Valid TrackingDTO trackingDTO){
        Vehicle vehicle = vehicleService.getByID(vehicleID);
        Location location = new Location();
        location.setCoordinates(trackingDTO.getCoordinates());
        Tracking tracking = Tracking.builder()
                .positionDate(trackingDTO.getPositionDate())
                .ignition(trackingDTO.isIgnition())
                .velocity(trackingDTO.getVelocity())
                .location(location)
                .vehicle(vehicle)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(trackingService.create(tracking));
    }

}
