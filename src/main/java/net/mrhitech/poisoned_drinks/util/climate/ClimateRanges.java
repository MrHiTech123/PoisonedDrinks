package net.mrhitech.poisoned_drinks.util.climate;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.RegisteredDataManager;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.resources.ResourceLocation;
import net.mrhitech.poisoned_drinks.common.block.crop.Crop;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class ClimateRanges {
    
    public static final Map<Crop, Supplier<ClimateRange>> CROPS = Helpers.mapOfKeys(Crop.class, (crop) -> {
        return register("crop/" + crop.getSerializedName());
    });
    
    public ClimateRanges() {
    
    }
    
    
    private static RegisteredDataManager.Entry<ClimateRange> register(String name) {
        return ClimateRange.MANAGER.register(new ResourceLocation("poisoned_drinks", name.toLowerCase(Locale.ROOT)));
    }
}
