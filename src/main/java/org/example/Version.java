package org.example;

public final class Version {
  public static final int MAJOR = 1;
  public static final int MINOR = 0;
  public static final int PATCH = 0;

  public static final String FULL_VERSION = MAJOR + "." + MINOR + "." + PATCH;

  private Version() {
        // Prevent instantiation
  }
}
