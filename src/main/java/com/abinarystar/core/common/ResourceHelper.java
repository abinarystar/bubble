package com.abinarystar.core.common;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.jar.JarFile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ResourceHelper {

  public static JarFile getJarFile(String packageName) {
    try {
      packageName = packageName.replace('.', '/');
      URL url = new ClassPathResource(packageName).getURL();
      String path = url.getFile().replace("file:/", "").replaceFirst("!.*", "");
      return new JarFile(new File(path));
    } catch (Exception ex) {
      log.error("Get jar file failed | packageName: {} | error: {}", packageName, ex.getMessage());
      return null;
    }
  }

  public static List<String> readAllLines(String path) {
    try {
      File file = new ClassPathResource(path).getFile();
      return Files.readAllLines(file.toPath());
    } catch (Exception ex) {
      log.error("Read all lines failed | path: {} | error: {}", path, ex.getMessage());
      return List.of();
    }
  }
}
