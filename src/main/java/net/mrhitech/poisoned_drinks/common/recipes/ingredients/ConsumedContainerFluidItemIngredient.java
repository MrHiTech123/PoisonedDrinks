package net.mrhitech.poisoned_drinks.common.recipes.ingredients;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.AdvancedShapelessRecipe;
import net.dries007.tfc.common.recipes.ingredients.DelegateIngredient;
import net.dries007.tfc.common.recipes.ingredients.FluidIngredient;
import net.dries007.tfc.common.recipes.ingredients.FluidItemIngredient;
import net.dries007.tfc.common.recipes.ingredients.FluidStackIngredient;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.JsonHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class ConsumedContainerFluidItemIngredient extends DelegateIngredient {
    protected final FluidStackIngredient fluid;
    public ConsumedContainerFluidItemIngredient(@Nullable Ingredient delegate, FluidStackIngredient fluid) {
        super(delegate);
        this.fluid = fluid;
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (super.test(stack) && stack != null && !stack.isEmpty()) {
            return stack.getCapability(Capabilities.FLUID_ITEM)
                    .map(cap -> cap.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE))
                    .filter(fluid)
                    .isPresent();
        }
        return false;
    }
    
    @Override
    public IIngredientSerializer<? extends DelegateIngredient> getSerializer() {
        return Serializer.INSTANCE;
    }
    
    @Override
    protected ItemStack[] getDefaultItems() {
        return fluid.ingredient()
                .all()
                .flatMap(fluid -> Helpers.getAllTagValues(TFCTags.Items.FLUID_ITEM_INGREDIENT_EMPTY_CONTAINERS, ForgeRegistries.ITEMS)
                        .stream().map(item -> {return new ItemStack(Items.STICK);})
                )
                .toArray(ItemStack[]::new);
    }
    
    public enum Serializer implements IIngredientSerializer<ConsumedContainerFluidItemIngredient>{
        INSTANCE;
        
        @Override
        public ConsumedContainerFluidItemIngredient parse(FriendlyByteBuf buffer) {
            final Ingredient internal = Helpers.decodeNullable(buffer, Ingredient::fromNetwork);
            final FluidStackIngredient fluid = FluidStackIngredient.fromNetwork(buffer);
            return new ConsumedContainerFluidItemIngredient(internal, fluid);
        }
        
        @Override
        public ConsumedContainerFluidItemIngredient parse(JsonObject json) {
            final Ingredient internal = json.has("ingredient") ? Ingredient.fromJson(JsonHelpers.get(json, "ingredient")) : null;
            final FluidStackIngredient fluid = FluidStackIngredient.fromJson(json.getAsJsonObject("fluid_ingredient"));
            return new ConsumedContainerFluidItemIngredient(internal, fluid);
        }
        
        @Override
        public void write(FriendlyByteBuf buffer, ConsumedContainerFluidItemIngredient ingredient) {
            Helpers.encodeNullable(ingredient.delegate, buffer, Ingredient::toNetwork);
            ingredient.fluid.toNetwork(buffer);
        }
        
    }
    
}
