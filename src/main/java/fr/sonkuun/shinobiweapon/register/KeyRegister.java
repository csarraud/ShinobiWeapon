package fr.sonkuun.shinobiweapon.register;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyRegister {
	public static final KeyBinding FIRST_ITEM_POWER = new KeyBinding("Item Power 1", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_J, "Shinobi Weapon Powers");

	public static void init() {
		
		ClientRegistry.registerKeyBinding(FIRST_ITEM_POWER);
	}
}
