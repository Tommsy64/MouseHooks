package com.tommsy.mousehooks;

import static com.tommsy.mousehooks.MouseHooksCorePlugin.LOG;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions({ "com.tommsy.mousehooks" })
public class MouseHooksCorePlugin implements IFMLLoadingPlugin {

	static boolean isObfuscated;
	static File location;
	static final Logger LOG = LogManager.getLogger("MouseHooks");
	static final String PACKAGE_ROOT = "com.tommsy.mousehooks.";

	private static final String[] ASM_TRANSFORMER_CLASSES = new String[] {
			PACKAGE_ROOT + "MouseHooksClassTransformer" };

	@Override
	public String[] getASMTransformerClass() {
		return ASM_TRANSFORMER_CLASSES;
	}

	@Override
	public String getModContainerClass() {
		return PACKAGE_ROOT + "MouseHooksModContainer";
	}

	@Override
	public String getSetupClass() {
		return PACKAGE_ROOT + "MouseHooksSetupHook";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		isObfuscated = (boolean) data.get("runtimeDeobfuscationEnabled");
        location = (File) data.get("coremodLocation");
        if (location == null)
            location = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	static boolean isMouseLoaded() {
		try {
			Method findLoadedClass = ClassLoader.class.getDeclaredMethod("findLoadedClass",
					new Class[] { String.class });
			findLoadedClass.setAccessible(true);
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			boolean isLoaded = findLoadedClass.invoke(classLoader, "org.lwjgl.input.Mouse") != null;
			LOG.debug("Class loaded: {}", isLoaded);
			return isLoaded;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
}
