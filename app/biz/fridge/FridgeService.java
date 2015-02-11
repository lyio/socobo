package biz.fridge;


import datalayer.FridgeRepository;
import models.fridge.Fridge;

import javax.inject.Inject;

public class FridgeService {

    final FridgeRepository fridgeRepository;

    @Inject
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    public Fridge getFridgeForUser(String userName) {
        return fridgeRepository.findByUserUserName(userName);
    }
}
