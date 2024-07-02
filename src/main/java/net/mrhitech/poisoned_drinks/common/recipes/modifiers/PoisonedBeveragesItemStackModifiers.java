package net.mrhitech.poisoned_drinks.common.recipes.modifiers;

import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifiers;
import net.minecraft.resources.ResourceLocation;

public class PoisonedBeveragesItemStackModifiers {
    public static void registerItemStackModifierTypes() {
        register("modify_fluid", OutputFluidItemIngredientModifier.Serializer.CHANGE_FLUID_NBT);
    }
    
    private static void register(String name, ItemStackModifier.Serializer<?> serializer) {
        ItemStackModifiers.register(new ResourceLocation("poisoned_drinks", name), serializer);
    }
}
