package br.com.j38.poi.controller;

import br.com.j38.poi.model.POIBooking;
import br.com.j38.poi.service.POIBookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "POI Booking")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/poi-booking")
public class POIBookingController {
    @Autowired
    POIBookingService poiBookingService;
    @ApiOperation(value = "List", nickname = "ListAllPOIs")
    @GetMapping
    public ResponseEntity<List<POIBooking>> getAll(@RequestParam(name = "dateFrom", required = false) LocalDateTime dateFrom,
                                                   @RequestParam(name = "dateTo", required = false) LocalDateTime dateTo){

        if(dateFrom == null && dateTo == null)
            return ResponseEntity.status(HttpStatus.OK).body(poiBookingService.findAll());
        if(dateTo == null)
            dateTo = LocalDateTime.now();
        return ResponseEntity.status(HttpStatus.OK).body(poiBookingService.findBetweenDates(dateFrom, dateTo));
    }
    @ApiOperation(value = "List", nickname = "ListAllPOIs")
    @GetMapping("/vehicle/{plate}")
    public ResponseEntity<List<POIBooking>> getAll(@RequestParam(name = "dateFrom", required = false) LocalDateTime dateFrom,
                                                   @RequestParam(name = "dateTo", required = false) LocalDateTime dateTo,
                                                   @PathVariable String plate) {

        if(dateFrom == null && dateTo == null)
            return ResponseEntity.status(HttpStatus.OK).body(poiBookingService.findByVehiclePlate(plate));
        if(dateTo == null)
            dateTo = LocalDateTime.now();
        return ResponseEntity.status(HttpStatus.OK).body(poiBookingService.ByVehiclePlateAndBetweenDates(plate, dateFrom, dateTo));
    }
}
