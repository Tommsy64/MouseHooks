package com.tommsy.mousehooks;

import static com.tommsy.mousehooks.MouseHooksCorePlugin.LOG;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

public class MouseHooksSetupHook implements IFMLCallHook {

	private static String LWJGL_PACKAGE = "org.lwjgl.";
	private LaunchClassLoader classLoader;

	@Override
	public void injectData(Map<String, Object> data) {
		classLoader = (LaunchClassLoader) data.get("classLoader");
	}

	@Override
	public Void call() throws Exception {
		try {
			Field field = classLoader.getClass().getDeclaredField("classLoaderExceptions");
			field.setAccessible(true);
			Set<String> classLoaderExceptions = (Set<String>) field.get(classLoader);
			classLoaderExceptions.remove(LWJGL_PACKAGE);
			LOG.trace("Removed {} from class loader exceptions.", LWJGL_PACKAGE);
		} catch (NoSuchFieldException | SecurityException e) {
			LOG.catching(Level.FATAL, e);
		}
		classLoader.loadClass(LWJGL_PACKAGE + "input.Mouse");
		classLoader.addTransformerExclusion(LWJGL_PACKAGE);
		return null;
	}
}
