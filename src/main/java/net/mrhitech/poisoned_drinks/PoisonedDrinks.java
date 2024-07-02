package net.mrhitech.poisoned_drinks;

import com.mojang.logging.LogUtils;
import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.recipes.TFCRecipeTypes;
import net.dries007.tfc.compat.jei.category.BarrelRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrhitech.poisoned_drinks.client.ClientEventHandler;
import net.mrhitech.poisoned_drinks.common.PoisonedBeverages;
import net.mrhitech.poisoned_drinks.common.block.PoisonedDrinksBlocks;
import net.mrhitech.poisoned_drinks.common.block.crop.Crop;
import net.mrhitech.poisoned_drinks.common.fluids.PoisonedDrinksFluids;
import net.mrhitech.poisoned_drinks.common.item.PoisonedDrinksItems;
import net.mrhitech.poisoned_drinks.common.recipes.modifiers.PoisonedBeveragesItemStackModifiers;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(PoisonedDrinks.MOD_ID)
public class PoisonedDrinks
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "poisoned_drinks";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public PoisonedDrinks()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        
        PoisonedDrinksBlocks.register(modEventBus);
        PoisonedDrinksItems.register(modEventBus);
        PoisonedDrinksFluids.register(modEventBus);
        PoisonedBeveragesItemStackModifiers.registerItemStackModifierTypes();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientEventHandler.init();
        }
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    
    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        
        if (event.getTabKey() == TFCCreativeTabs.MISC.tab().getKey()) {
            for (PoisonedBeverages alcohol : PoisonedBeverages.values()) {
                event.accept(PoisonedDrinksItems.FLUID_BUCKETS.get(alcohol));
            }
        }
        if (event.getTabKey() == TFCCreativeTabs.EARTH.tab().getKey()) {
            for (Crop crop : Crop.values()) {
                event.accept(PoisonedDrinksItems.CROP_SEEDS.get(crop));
                event.accept(PoisonedDrinksItems.WILD_CROPS.get(crop));
            }
        }
        if (event.getTabKey() == TFCCreativeTabs.FOOD.tab().getKey()) {
            event.accept(PoisonedDrinksItems.HEMLOCK);
            event.accept(PoisonedDrinksItems.COOKED_HEMLOCK);
            event.accept(PoisonedDrinksItems.HEMLOCK_POWDER);
        }
        
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            for (Crop crop : Crop.values()) {
                ItemBlockRenderTypes.setRenderLayer(PoisonedDrinksBlocks.CROPS.get(crop).get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(PoisonedDrinksBlocks.DEAD_CROPS.get(crop).get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(PoisonedDrinksBlocks.WILD_CROPS.get(crop).get(), RenderType.cutout());
            }
            
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
