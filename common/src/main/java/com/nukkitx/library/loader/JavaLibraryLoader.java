package com.nukkitx.library.loader;

import com.nukkitx.api.library.Library;
import com.nukkitx.api.library.LibraryDescription;
import com.nukkitx.api.library.LibraryLoader;
import com.nukkitx.library.SimpleLibrary;
import com.nukkitx.library.SimpleLibraryDescription;
import com.nukkitx.library.loader.java.JavaLibraryClassLoader;
import lombok.NonNull;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class JavaLibraryLoader implements LibraryLoader {

	private static final PathMatcher PATH_MATCHER = FileSystems.getDefault().getPathMatcher("glob:**.jar");
	private final Map<Class, Object> dependencies = new HashMap<>();

	@Nonnull
	@Override
	public LibraryDescription loadLibrary(@NonNull Path path) {
		Objects.requireNonNull(path, "path");

		try (JarInputStream jis = new JarInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
			Manifest manifest = jis.getManifest();
			if (manifest == null) {
				throw new IllegalArgumentException("Jar has no manifest");
			}

			return new SimpleLibraryDescription(path.getFileName().toString(), path, this, manifest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Nonnull
	@Override
	public Library createLibrary(LibraryDescription description) throws Exception {
		Path path = description.getPath().orElseThrow(() -> new IllegalArgumentException("No path in library description"));

		JavaLibraryClassLoader loader = new JavaLibraryClassLoader(new URL[]{path.toUri().toURL()});

		try (JarInputStream jis = new JarInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
			Manifest manifest = jis.getManifest();
			if (manifest == null) {
				throw new IllegalArgumentException("Jar has no manifest");
			}

			JarEntry entry;
			while ((entry = jis.getNextJarEntry()) != null) {
				if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
					continue;
				}

				String className = entry
						.getName()
						.substring(0, entry.getName().length() - 6)
						.replace('/', '.');

				loader.loadClass(className);
			}
		}

		return new SimpleLibrary(description);
	}

	@Nonnull
	@Override
	public PathMatcher getPathMatcher() {
		return PATH_MATCHER;
	}
}
