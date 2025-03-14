package com.abinarystar.core.nativeimage;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ImportRuntimeHints;

@AutoConfiguration
@ImportRuntimeHints(NativeImageRuntimeHintsRegistrar.class)
public class NativeImageAutoConfiguration {
}
