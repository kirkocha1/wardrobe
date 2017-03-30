package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.repositories.interfaces.IThingsRepository;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

public class AddThingInteractor {

    IThingsRepository thingRepository;

    public AddThingInteractor(IThingsRepository thingRepository) {
        this.thingRepository = thingRepository;
    }


}
