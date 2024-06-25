package net.mrhitech.poisoned_drinks.common.block.crop;

import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.crop.DefaultCropBlock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.mrhitech.poisoned_drinks.common.block.PoisonedDrinksBlocks;
import net.mrhitech.poisoned_drinks.common.item.PoisonedDrinksItems;
import net.mrhitech.poisoned_drinks.util.climate.ClimateRanges;

import java.util.function.Supplier;

public abstract class DefaulterCropBlock extends DefaultCropBlock {
    public static DefaulterCropBlock create(ExtendedProperties properties, int stages, Crop crop) {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new DefaulterCropBlock(properties, stages - 1, (Supplier) PoisonedDrinksBlocks.DEAD_CROPS.get(crop), (Supplier) PoisonedDrinksItems.CROP_SEEDS.get(crop), crop.getPrimaryNutrient(), (Supplier) ClimateRanges.CROPS.get(crop)) {
            public IntegerProperty getAgeProperty() {
                return property;
            }
        };
    }
    
    protected DefaulterCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, FarmlandBlockEntity.NutrientType primaryNutrient, Supplier<ClimateRange> climateRange) {
        super(properties, maxAge, dead, seeds, primaryNutrient, climateRange);
    }
    
}
