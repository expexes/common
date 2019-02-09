package com.nukkitx.library;

import com.nukkitx.api.library.Library;
import com.nukkitx.api.library.LibraryDescription;
import com.nukkitx.api.library.LibraryLoader;

import java.nio.file.Path;
import java.util.jar.Manifest;

public class SimpleLibrary extends SimpleLibraryDescription implements Library {

	public SimpleLibrary(String id, Path path, LibraryLoader loader, Manifest manifest) {
		super(id, path, loader, manifest);
	}

	public SimpleLibrary(LibraryDescription description) {
		super(description.getId(), description.getPath().get(), description.getLibraryLoader(), description.getManifest());
	}
}
