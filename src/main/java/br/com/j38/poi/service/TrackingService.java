package br.com.j38.poi.service;

import br.com.j38.poi.model.POI;
import br.com.j38.poi.model.POIBooking;
import br.com.j38.poi.model.Tracking;
import br.com.j38.poi.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TrackingService {
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private POIService poiService;
    @Autowired
    private POIBookingService poiBookingService;

    public Tracking create(Tracking tracking){
        Optional<POI> poiLocalOpt = poiService.poiByLocation(tracking.getLocation());
        //premise: a vehicle can only be in a single POI => TO business rules when create a poi
        Optional<POIBooking> poiBookingOpenOpt = poiBookingService.poiBookingByVehicle(tracking.getVehicle());
        Tracking trackingSaved =  trackingRepository.save(tracking);
        /*
        nothing TO-DO: if case 1 -> vehicle is still in the same POI
                       if case 2 -> vehicle is not on an POI and does not have an Open Booking POI
        */
        if(poiLocalOpt.isPresent() && poiBookingOpenOpt.isPresent()){
            if(!poiLocalOpt.get().equals(poiBookingOpenOpt.get().getPoi())){
                //vehicle is in another POI and have an Open Booking POI
                poiBookingService.checkOut(poiBookingOpenOpt.get(), trackingSaved);
                poiBookingService.checkIn(poiLocalOpt.get(), trackingSaved);
            }
        } else if (poiLocalOpt.isPresent() && poiBookingOpenOpt.isEmpty()) {
            //vehicle get in a POI and does not have an Open Booking POI
            poiBookingService.checkIn(poiLocalOpt.get(), trackingSaved);
        } else if (poiLocalOpt.isEmpty()) {
            if(poiBookingOpenOpt.isPresent()){
                //vehicle get out POI and does not have an Open Booking POI
                poiBookingService.checkOut(poiBookingOpenOpt.get(), trackingSaved);
            }
        }
        return trackingSaved;
    }
}
