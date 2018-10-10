package com.nukkitx.api.library;

import java.util.Collection;

public interface Library {

	String getName();

	Collection<Class> getLoadedClasses();
}
