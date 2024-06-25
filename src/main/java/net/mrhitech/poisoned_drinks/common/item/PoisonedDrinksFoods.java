package net.mrhitech.poisoned_drinks.common.item;


import net.dries007.tfc.common.recipes.AdvancedShapedRecipe;
import net.dries007.tfc.common.recipes.AdvancedShapelessRecipe;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class PoisonedDrinksFoods {
    public static final FoodProperties HEMLOCK =
            new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 30 * 20, 1), 1).build();
    public static final FoodProperties COOKED_HEMLOCK =
            new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120 * 60, 1), 1).build();
    
}
