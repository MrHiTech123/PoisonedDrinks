package net.mrhitech.poisoned_drinks.common.block;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mrhitech.poisoned_drinks.PoisonedDrinks;
import net.mrhitech.poisoned_drinks.common.PoisonedBeverages;
import net.mrhitech.poisoned_drinks.common.block.crop.Crop;
import net.mrhitech.poisoned_drinks.common.fluids.PoisonedDrinksFluids;

import java.util.Map;



public class PoisonedDrinksBlocks {
    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, PoisonedDrinks.MOD_ID);
    
    public static final Map<PoisonedBeverages, RegistryObject<LiquidBlock>> POISONED_ALCOHOL = Helpers.mapOfKeys(PoisonedBeverages.class, fluid ->
            BLOCKS.register("fluid/" + fluid.getId(), () -> new LiquidBlock(PoisonedDrinksFluids.POISONED_BEVERAGES.get(fluid).source(), BlockBehaviour.Properties.copy(Blocks.WATER))));
    
    public static final Map<Crop, RegistryObject<Block>> CROPS = Helpers.mapOfKeys(Crop.class, crop ->
            BLOCKS.register("crop/" + crop.getSerializedName(), crop::create));
    
    public static final Map<Crop, RegistryObject<Block>> DEAD_CROPS = Helpers.mapOfKeys(Crop.class, crop ->
            BLOCKS.register("dead_crop/" + crop.getSerializedName(), crop::deadCreate));
    
    public static final Map<Crop, RegistryObject<Block>> WILD_CROPS = Helpers.mapOfKeys(Crop.class, crop ->
            BLOCKS.register("wild_crop/" + crop.getSerializedName(), crop::wildCreate));
    
    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
