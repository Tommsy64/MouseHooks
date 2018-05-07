package com.tommsy.mousehooks;

import static com.tommsy.mousehooks.MouseHooksCorePlugin.LOG;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.google.common.collect.ImmutableList;

import net.minecraft.launchwrapper.IClassTransformer;

public class MouseHooksClassTransformer implements IClassTransformer {

	static final List<String> CLASSES = ImmutableList.of("org.lwjgl.input.Mouse");

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		int index = CLASSES.indexOf(transformedName);
		return index != -1 ? transform(index, basicClass, MouseHooksCorePlugin.isObfuscated) : basicClass;
	}

	private static byte[] transform(int index, byte[] basicClass, boolean isObfuscated) {
		LOG.info("Transforming {}", CLASSES.get(index));
		try {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(basicClass);
			classReader.accept(classNode, 0);

			switch (index) {
			case 0:
				transformMouse(classNode, isObfuscated);
				break;
			}

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			return classWriter.toByteArray();
		} catch (Throwable t) {
			LOG.catching(Level.FATAL, t);
		}
		return basicClass;
	}

	private static void transformMouse(ClassNode classNode, boolean isObfuscated) {
		// TODO: Magic
		LOG.info("Magic is happening.");
	}
}
