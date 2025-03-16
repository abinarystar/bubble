package com.abinarystar.core.nativeimage;

import com.abinarystar.core.common.ResourceHelper;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

import static org.springframework.aot.hint.MemberCategory.INVOKE_DECLARED_CONSTRUCTORS;
import static org.springframework.aot.hint.MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS;
import static org.springframework.aot.hint.MemberCategory.INVOKE_PUBLIC_METHODS;
import static org.springframework.aot.hint.MemberCategory.PUBLIC_CLASSES;
import static org.springframework.aot.hint.MemberCategory.PUBLIC_FIELDS;
import static org.springframework.aot.hint.MemberCategory.UNSAFE_ALLOCATED;

@Slf4j
public class NativeImageRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

  private static final MemberCategory[] MEMBER_CATEGORIES = {
      PUBLIC_FIELDS,
      INVOKE_PUBLIC_CONSTRUCTORS,
      INVOKE_DECLARED_CONSTRUCTORS,
      INVOKE_PUBLIC_METHODS,
      PUBLIC_CLASSES,
      UNSAFE_ALLOCATED
  };

  @Override
  @SneakyThrows
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    List<String> packages = ResourceHelper.readAllLines("native-image/packages.txt");
    for (String pkg : packages) {
      Set<String> classNames = new HashSet<>();
      Set<String> resources = new HashSet<>();
      populate(pkg, classNames, resources);
      for (String className : classNames) {
        log.debug("Register class | name: {}", className);
        try {
          hints.reflection().registerType(TypeReference.of(className), MEMBER_CATEGORIES);
        } catch (Exception ex) {
          log.error("Register class failed | name: {} | error: {}", className, ex.getMessage());
        }
      }
      for (String resource : resources) {
        log.debug("Register resource | name: {}", resource);
        hints.resources().registerPattern(resource);
      }
    }

    List<String> classes = ResourceHelper.readAllLines("native-image/classes.txt");
    for (String cls : classes) {
      log.debug("Register class | name: {}", cls);
      try {
        hints.reflection().registerType(TypeReference.of(cls), MEMBER_CATEGORIES);
      } catch (Exception ex) {
        log.error("Register class failed | name: {} | error: {}", cls, ex.getMessage());
      }
    }

    List<String> resources = ResourceHelper.readAllLines("native-image/resources.txt");
    for (String resource : resources) {
      log.debug("Register resource | name: {}", resource);
      hints.resources().registerPattern(resource);
    }
  }

  @SneakyThrows
  private void populate(String packageName, Set<String> classNames, Set<String> resources) {
    JarFile jarFile = ResourceHelper.getJarFile(packageName);
    if (jarFile == null) {
      return;
    }

    try (JarFile file = jarFile) {
      Enumeration<JarEntry> entries = file.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();

        if (entry.getName().endsWith(".class")) {
          String className = entry.getName()
              .replace("/", ".")
              .replace(".class", "");
          classNames.add(className);
        }

        if (entry.getName().endsWith(".properties")) {
          resources.add(entry.getName());
        }
      }
    }
  }
}
