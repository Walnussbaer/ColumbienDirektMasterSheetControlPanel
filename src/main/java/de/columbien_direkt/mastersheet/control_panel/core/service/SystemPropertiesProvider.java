package de.columbien_direkt.mastersheet.control_panel.core.service;

import org.springframework.stereotype.Component;

@Component
public class SystemPropertiesProvider {

  public SystemPropertiesProvider() {}

  public String getJavaVersion() {
    return System.getProperty("java.version");
  }

  public String getJavaFxVersion() {
    return System.getProperty("javafx.version");
  }
}
