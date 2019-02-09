package com.nukkitx.api.library;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

@ParametersAreNonnullByDefault
public interface LibraryLoader {

	@Nonnull
	LibraryDescription loadLibrary(Path path) throws Exception;

	@Nonnull
	Library createLibrary(LibraryDescription description) throws Exception;

	@Nonnull
	PathMatcher getPathMatcher();
}
