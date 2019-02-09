package com.nukkitx.api.library;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.jar.Manifest;

public interface LibraryDescription {

	/**
	 * The ID for this library. This should be an alphanumeric name. Slashes are also allowed.
	 *
	 * @return the ID for this library
	 */
	@Nonnull
	String getId();

	/**
	 * The path where the library is located on the file system.
	 *
	 * @return the path of this library
	 */
	@Nonnull
	Optional<Path> getPath();

	@Nonnull
	LibraryLoader getLibraryLoader();

	@Nullable
	Manifest getManifest();
}
