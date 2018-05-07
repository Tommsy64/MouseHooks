package com.tommsy.mousehooks;

import java.io.File;

import org.lwjgl.LWJGLException;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MouseHooksModContainer extends DummyModContainer {
	public MouseHooksModContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "mousehooks";
		meta.name = "MouseHooks";
		meta.description = "Add hooks to LWJGL Mouse class";
		meta.version = "1.10.12-0.1";
		meta.authorList = ImmutableList.of("Tommsy64");
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public static void preInit(FMLPreInitializationEvent event) {

	}

	@Subscribe
	public static void init(FMLInitializationEvent event) throws LWJGLException {

	}

	@Subscribe
	public static void postInit(FMLPostInitializationEvent event) throws LWJGLException {

	}

	@Subscribe
	public static void init(FMLLoadCompleteEvent event) throws LWJGLException {

	}

	@Override
	public File getSource() {
		return MouseHooksCorePlugin.location;
	}
}
