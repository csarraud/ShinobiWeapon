package fr.sonkuun.shinobiweapon.register;


public class RegistryHandler {
	
	public static void init() {
		ItemRegister.init();
		EntityTypeRegister.init();
		ModelRegister.init();
		RecipeRegister.init();
		KeyRegister.init();
	}
}
