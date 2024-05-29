package com.PetStore.dto;

import java.util.Map;

public class InventoryDTO {

    private Map<String, Integer> inventory;

    public InventoryDTO(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }
}
