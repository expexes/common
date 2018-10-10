package com.nukkitx.api.library;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Optional;

@ParametersAreNonnullByDefault
public interface LibraryManager {

	<T extends LibraryLoader> boolean registerLoader(Class<T> clazz, T loader);

	<T extends LibraryLoader> boolean deregisterLoader(Class<T> clazz);

	Collection<Library> getAllLibraries();

	Optional<Library> getLibrary(String name);

	boolean isLoaded(String name);
}
