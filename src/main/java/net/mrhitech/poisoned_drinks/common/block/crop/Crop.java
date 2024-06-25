package net.mrhitech.poisoned_drinks.common.block.crop;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity.NutrientType;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.crop.DeadCropBlock;
import net.dries007.tfc.common.blocks.crop.DefaultCropBlock;
import net.dries007.tfc.common.blocks.crop.WildCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.mrhitech.poisoned_drinks.util.climate.ClimateRanges;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public enum Crop implements StringRepresentable {
    HEMLOCK(NutrientType.POTASSIUM, 5);
    
    
    private static ExtendedProperties crop() {
        return dead().blockEntity(TFCBlockEntities.CROP).serverTicks(CropBlockEntity::serverTick);
    }
    
    private static ExtendedProperties dead() {
        return ExtendedProperties.of(MapColor.PLANT).noCollission().randomTicks().strength(0.4F).sound(SoundType.CROP).flammable(60, 30);
    }
    
    private final String serializedName;
    private final NutrientType primaryNutrient;
    private final Supplier<Block> factory;
    private final Supplier<Block> deadFactory;
    private final Supplier<Block> wildFactory;
    
    Crop(NutrientType f_primaryNutrient, int f_singleBlockStages) {
        this(f_primaryNutrient, self -> DefaulterCropBlock.create(crop(), f_singleBlockStages, self), self -> new DeadCropBlock(dead(), self.getClimateRange()), self -> new WildCropBlock(dead().randomTicks()));
    }
    
    Crop(NutrientType f_primaryNutrient, Function<Crop, Block> f_factory, Function<Crop, Block> f_deadFactory, Function<Crop, Block> f_wildFactory) {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.primaryNutrient = f_primaryNutrient;
        this.factory = () -> f_factory.apply(this);
        this.deadFactory = () -> f_deadFactory.apply(this);
        this.wildFactory = () -> f_wildFactory.apply(this);
    }
    
    @Override
    public String getSerializedName() {
        return serializedName;
    }
    
    public Block create() {
        return factory.get();
    }
    
    public Block deadCreate() {
        return deadFactory.get();
    }
    
    public Block wildCreate() {
        return wildFactory.get();
    }
    
    public NutrientType getPrimaryNutrient() {
        return this.primaryNutrient;
    }
    
    
    public Supplier<ClimateRange> getClimateRange()
    {
        return ClimateRanges.CROPS.get(this);
    }
    
    
}
