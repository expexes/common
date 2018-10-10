package com.nukkitx.api.library;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;

@ParametersAreNonnullByDefault
public interface LibraryLoader {

	@Nonnull
	Library loadLibrary(Path path);

	@Nonnull
	boolean unloadLibrary(String name);
}
