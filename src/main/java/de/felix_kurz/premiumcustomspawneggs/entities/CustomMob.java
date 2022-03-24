package de.felix_kurz.premiumcustomspawneggs.entities;

import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class CustomMob {

    private String id;
    private String name;
    private EntityType type;
    private int health;

    public static HashMap<String, CustomMob> mobs = new HashMap<>();

}
