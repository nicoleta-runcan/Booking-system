package net.codejava.proiect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchedItems  {

    public List choose(List<Locatie> listLocation, SearchCriteria searchCriteria)
    {

        List<Locatie> listFin = new ArrayList<>();
        for(Locatie loc: listLocation )
            if(loc.getNumeOras().equals(searchCriteria.getLocation()) &&
                    loc.getTip().equals(searchCriteria.getType()))
                listFin.add(loc);
        Collections.sort(listFin);

        return listFin;
    }

}
