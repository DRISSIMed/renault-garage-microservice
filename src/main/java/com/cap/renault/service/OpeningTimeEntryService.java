package com.cap.renault.service;

import com.cap.renault.entity.Garage;
import com.cap.renault.entity.OpeningTimeEntry;
import com.cap.renault.repository.OpeningTimeEntryRepository;
import com.cap.renault.util.OpeningTime;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpeningTimeEntryService {
    @Autowired
    private OpeningTimeEntryRepository openingTimeEntryRepository;

    @Transactional
    public void createOpneningTime(Map<DayOfWeek, List<OpeningTime>> horairesOuverture, Garage garage){
        List<OpeningTimeEntry> entryList=new ArrayList<>();
        horairesOuverture.forEach((day, times) ->
                times.forEach(time -> {
                    OpeningTimeEntry entry = new OpeningTimeEntry();
                    entry.setDayOfWeek(day);
                    entry.setOpeningTime(time);
                    entry.setGarage(garage);
                    entryList.add(entry);
                })
        );
        openingTimeEntryRepository.saveAllAndFlush(entryList);
    }
    @Transactional
    public void updateOpeningTime(Map<DayOfWeek, List<OpeningTime>> horairesOuverture, Garage garage){
        List<OpeningTimeEntry> openingTimeEntryList=openingTimeEntryRepository.findByGarageId(garage.getId());
        if(openingTimeEntryList !=null && !openingTimeEntryList.isEmpty()){
            garage.getHorairesOuvertureList().clear();
            createOpneningTime(horairesOuverture,garage);
        }
    }


    @Transactional
    public void deleteOpeningTime(Long garageId){
        List<OpeningTimeEntry> openingTimeEntryList=openingTimeEntryRepository.findByGarageId(garageId);
        if(openingTimeEntryList !=null && !openingTimeEntryList.isEmpty()){

        }
    }
}
