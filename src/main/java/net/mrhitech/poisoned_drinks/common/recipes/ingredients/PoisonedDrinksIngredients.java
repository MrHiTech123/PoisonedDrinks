package net.mrhitech.poisoned_drinks.common.recipes.ingredients;

import net.dries007.tfc.common.recipes.ingredients.FluidItemIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public class PoisonedDrinksIngredients {
    public static void registerIngredientTypes() {
        register("consumed_container_fluid_item", ConsumedContainerFluidItemIngredient.Serializer.INSTANCE);
    }
    
    private static <T extends Ingredient> void register(String name, IIngredientSerializer<T> serializer) {
        CraftingHelper.register(new ResourceLocation("poisoned_drinks", name), serializer);
    }
}
