package br.com.j38.poi.service;

import br.com.j38.poi.model.POI;
import br.com.j38.poi.model.POIBooking;
import br.com.j38.poi.model.Tracking;
import br.com.j38.poi.model.Vehicle;
import br.com.j38.poi.repository.POIBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class POIBookingService {
    @Autowired
    private POIBookingRepository poiBookingRepository;

    public POIBooking checkIn(POI poi, Tracking tracking){
        POIBooking poiBooking = POIBooking.builder()
                .poi(poi)
                .checkIn(tracking)
                .isPresent(true)
                .plate(tracking.getVehicle().getPlate())
                .build();
        return poiBookingRepository.save(poiBooking);
    }
    public Optional<POIBooking> poiBookingByVehicle(Vehicle vehicle) {
        return poiBookingRepository.findByPlateAndIsPresent(vehicle.getPlate(), true);
    }
    public void checkOut(POIBooking poiBooking, Tracking tracking) {
        poiBooking.setPresent(false);
        poiBooking.setCheckOut(tracking);
        poiBooking.setClosedAt(tracking.getPositionDate());
        poiBooking.setSecondsTimeHosted(
                ChronoUnit.SECONDS.between(poiBooking.getCheckIn().getPositionDate()
                        , poiBooking.getCheckOut().getPositionDate()));
    }
    public List<POIBooking> findAll(){
        return poiBookingRepository.findAll();
    }

    public List<POIBooking> findBetweenDates(LocalDateTime dateFrom, LocalDateTime dateTo) {
        //return poiBookingRepository.findByClosedAtBetween(Range.closed(dateFrom, dateTo));
        return poiBookingRepository.findBetweenDates(dateFrom, dateTo);
    }

    public List<POIBooking> ByVehiclePlateAndBetweenDates(String plate, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return poiBookingRepository.findBetweenDatesAndPlate(plate, dateFrom, dateTo);
    }

    public List<POIBooking> findByVehiclePlate(String plate) {
        return poiBookingRepository.findByPlate(plate);
    }
}
