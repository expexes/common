package com.nukkitx.library;

import com.nukkitx.api.library.LibraryDescription;
import com.nukkitx.api.library.LibraryLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.jar.Manifest;

public class SimpleLibraryDescription implements LibraryDescription {

	private final String id;
	private final Path path;
	private final LibraryLoader loader;
	private final Manifest manifest;

	public SimpleLibraryDescription(String id, Path path, LibraryLoader loader, Manifest manifest) {
		this.id = id;
		this.path = path;
		this.loader = loader;
		this.manifest = manifest;
	}

	@Nonnull
	@Override
	public String getId() {
		return id;
	}

	@Nonnull
	@Override
	public Optional<Path> getPath() {
		return Optional.ofNullable(path);
	}

	@Override
	public LibraryLoader getLibraryLoader() {
		return loader;
	}

	@Nullable
	@Override
	public Manifest getManifest() {
		return manifest;
	}
}
