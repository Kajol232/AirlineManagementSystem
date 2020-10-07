package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AircraftManagementService {
    private final HashMap<String, Aircraft> _aircraft;

    public AircraftManagementService() {
        _aircraft = new HashMap<>();
    }

    public void createAircraft(String name, String model) {
        if (_aircraft.containsKey(name))
        {
            throw new UnsupportedOperationException("Aircraft already exists");
        }

        Aircraft aircraft = new Aircraft(name, model);
        _aircraft.put(name, aircraft);
    }

    public void updateAircraft(String name, String model){


        Aircraft aircraft = _aircraft.get(name);
        aircraft.setModel(model);
    }

    public void deleteAircraft(String name) {
        _aircraft.remove(name);
    }

    public Aircraft getAircraftByName(String name){
        if (!_aircraft.containsKey(name))
        {
            throw new UnsupportedOperationException("Aircraft does not exists");
        }
        return _aircraft.get(name);
    }

    public ArrayList<Aircraft> searchAircraft(String query) {
        ArrayList<Aircraft> result = new ArrayList<>();
        for (Aircraft a: _aircraft.values()) {
            if(a.getName().contains(query) || a.getModel().contains(query)) {
                result.add(a);
            }
        }
        return result;
    }
}
