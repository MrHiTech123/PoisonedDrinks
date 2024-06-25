package net.mrhitech.poisoned_drinks.common;

import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.world.level.material.Fluids;

import java.util.Locale;

public enum PoisonedBeverages {
    WATER(-12618011),
    BEER(-3957193),
    CIDER(-5198286),
    RUM(-9567965),
    SAKE(-4728388),
    VODKA(-2302756),
    WHISKEY(-10995943),
    CORN_WHISKEY(-2504777),
    RYE_WHISKEY(-3703471),
    AGED_BEER(-3957193),
    AGED_CIDER(-5198286),
    AGED_RUM(-9567965),
    AGED_SAKE(-4728388),
    AGED_VODKA(-2302756),
    AGED_WHISKEY(-10995943),
    AGED_CORN_WHISKEY(-2504777),
    AGED_RYE_WHISKEY(-3703471),
    RED_WINE(-6222591),
    WHITE_WINE(-3085),
    ROSE_WINE(-668985),
    SPARKLING_WINE(-4395),
    DESSERT_WINE(-857120);
    private final String id;
    private final int color;
    
    PoisonedBeverages(int color) {
        this.id = "poisoned_" + this.name().toLowerCase(Locale.ROOT);
        this.color = color;
    }
    
    public String getId() {
        return id;
    }
    
    public int getColor() {
        return color;
    }
    
}
