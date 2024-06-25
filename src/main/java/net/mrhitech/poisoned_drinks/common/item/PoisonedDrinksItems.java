package net.mrhitech.poisoned_drinks.common.item;

import net.dries007.tfc.util.Helpers;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrhitech.poisoned_drinks.PoisonedDrinks;
import net.mrhitech.poisoned_drinks.common.PoisonedBeverages;
import net.mrhitech.poisoned_drinks.common.block.PoisonedDrinksBlocks;
import net.mrhitech.poisoned_drinks.common.block.crop.Crop;
import net.mrhitech.poisoned_drinks.common.fluids.PoisonedDrinksFluids;

import java.util.Map;


public class PoisonedDrinksItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PoisonedDrinks.MOD_ID);
    
    public static final Map<PoisonedBeverages, RegistryObject<Item>> FLUID_BUCKETS = Helpers.mapOfKeys(PoisonedBeverages.class, fluid ->
            ITEMS.register("bucket/" + fluid.getId(), () -> new BucketItem(PoisonedDrinksFluids.POISONED_BEVERAGES.get(fluid).source(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))));
    
    public static final Map<Crop, RegistryObject<Item>> CROP_SEEDS = Helpers.mapOfKeys(Crop.class, crop ->
            ITEMS.register("seeds/" + crop.getSerializedName(), () -> new ItemNameBlockItem(PoisonedDrinksBlocks.CROPS.get(crop).get(), new Item.Properties())));
    
    public static final Map<Crop, RegistryObject<BlockItem>> WILD_CROPS = Helpers.mapOfKeys(Crop.class, crop ->
            ITEMS.register("wild_crop/" + crop.getSerializedName(), () -> new BlockItem(PoisonedDrinksBlocks.WILD_CROPS.get(crop).get(), new Item.Properties())));
    
    public static final RegistryObject<Item> HEMLOCK = ITEMS.register("food/hemlock", () -> new Item(new Item.Properties().food(PoisonedDrinksFoods.HEMLOCK)));
    public static final RegistryObject<Item> COOKED_HEMLOCK = ITEMS.register("food/cooked_hemlock", () -> new Item(new Item.Properties().food(PoisonedDrinksFoods.COOKED_HEMLOCK)));
    public static final RegistryObject<Item> HEMLOCK_POWDER = ITEMS.register("powder/hemlock", () -> new Item(new Item.Properties()));
    
    
    
    public static void register(IEventBus bus) {
        System.out.println("Registered!");
        ITEMS.register(bus);
        
    }

}
