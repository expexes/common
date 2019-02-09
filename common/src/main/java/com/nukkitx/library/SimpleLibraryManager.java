package com.nukkitx.library;

import com.nukkitx.api.library.Library;
import com.nukkitx.api.library.LibraryDescription;
import com.nukkitx.api.library.LibraryLoader;
import com.nukkitx.api.library.LibraryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class SimpleLibraryManager implements LibraryManager {

	private final Map<Object, Library> libraries = new HashMap<>();
	private final Map<Class, LibraryLoader> loaders = new HashMap<>();

	@Override
	public <T extends LibraryLoader> boolean registerLoader(Class<T> clazz, T loader) {
		Objects.requireNonNull(clazz, "clazz");
		Objects.requireNonNull(loader, "loader");

		synchronized (loaders) {
			if (loaders.containsKey(clazz)) {
				return false;
			} else {
				loaders.put(clazz, loader);
			}
		}
		return true;
	}

	@Override
	public <T extends LibraryLoader> boolean deregisterLoader(Class<T> clazz) {
		synchronized (loaders) {
			return loaders.remove(clazz) != null;
		}
	}

	@Override
	public Collection<Library> getAllLibraries() {
		return Collections.unmodifiableCollection(libraries.values());
	}

	@Override
	public Optional<Library> getLibrary(String name) {
		Objects.requireNonNull(name, "name");
		return Optional.ofNullable(libraries.get(name));
	}

	@Override
	public boolean isLoaded(String name) {
		Objects.requireNonNull(name, "name");
		return libraries.containsKey(name);
	}

	public void loadLibraries(Path directory) throws IOException {
		Objects.requireNonNull(directory, "directory");
		if (!Files.isDirectory(directory)) {
			throw new IllegalArgumentException("Path provided is not a directory");
		}

		Deque<LibraryDescription> found = new ArrayDeque<>();

		synchronized (loaders) {
			for (LibraryLoader loader : loaders.values()) {
				PathMatcher matcher = loader.getPathMatcher();
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, p -> Files.isRegularFile(p) && matcher.matches(p))) {
					for (Path path : stream) {
						try {
							found.add(loader.loadLibrary(path));
						} catch (Exception e) {
							log.error("Unable to load library {}", path, e);
						}
					}
				}
			}
		}

		for (LibraryDescription libraryFond : found) {
			Library library;
			try {
				library = libraryFond.getLibraryLoader().createLibrary(libraryFond);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			libraries.put(library.getId(), library);
		}
	}
}
