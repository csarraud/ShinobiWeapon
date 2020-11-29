package fr.sonkuun.shinobiweapon.register;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import fr.sonkuun.shinobiweapon.recipes.IShinobiWeaponRecipe;
import fr.sonkuun.shinobiweapon.recipes.ShinobiWeaponRecipe;
import fr.sonkuun.shinobiweapon.recipes.ShinobiWeaponRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeRegister {
	
	public static final IRecipeSerializer<ShinobiWeaponRecipe> SHINOBI_WEAPON_RECIPE_SERIALIZER = new ShinobiWeaponRecipeSerializer();
	public static final IRecipeType<IShinobiWeaponRecipe> SHINOBI_WEAPON_TYPE = registerType(IShinobiWeaponRecipe.RECIPE_TYPE_ID);

	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(
			ForgeRegistries.RECIPE_SERIALIZERS, ShinobiWeapon.MODID);

	public static final RegistryObject<IRecipeSerializer<?>> SHINOBI_WEAPON_SERIALIZER = RECIPE_SERIALIZERS.register("kunai",
			() -> SHINOBI_WEAPON_RECIPE_SERIALIZER);
	
	public static void init() {
		RECIPE_SERIALIZERS.register("shuriken", () -> SHINOBI_WEAPON_RECIPE_SERIALIZER);
	}

	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
	
	private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId) {
		return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
	}
}
