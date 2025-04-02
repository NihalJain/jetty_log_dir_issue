package io.github.nihaljain;

import java.io.File;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for the JettyServerX class to get the log directory path.
 */
public class Util {

  private static final Logger logger = LoggerFactory.getLogger(Util.class);

  private Util() {
    throw new IllegalStateException("Utility class");
  }

  public static String getLogDirPath(String[] args) throws Exception {
    // Check if the log directory and useCanonicalPath flag are provided
    if (args.length < 2) {
      throw new IllegalArgumentException(
          "Log directory and useCanonicalPath flag must be provided");
    }

    // Get the log directory and useCanonicalPath flag
    final String logDir = args[0];
    if (logDir == null) {
      throw new IllegalArgumentException("Log directory not provided");
    }
    logger.info("Logs directory: " + logDir);

    // Get the useCanonicalPath flag
    boolean useCanonicalPath = false;
    try {
      useCanonicalPath = Boolean.parseBoolean(args[1]);
    } catch (Exception e) {
      throw new IllegalArgumentException("useCanonicalPath flag must be a boolean");
    }
    logger.info("useCanonicalPath flag: " + useCanonicalPath);

    // Get the final log path for the log directory
    String logDirPath = useCanonicalPath ? Paths.get(logDir).toFile().getCanonicalPath() : logDir;
    logger.info("Logs directory (final path): " + logDirPath);

    // Ensure the log directory exists, else create it
    File logDirectory = new File(logDir);
    if (!logDirectory.exists() || !logDirectory.isDirectory()) {
      if (!logDirectory.mkdirs()) {
        throw new IllegalArgumentException(
            "Log directory does not exist and could not be created: " + logDir);
      }
    }
    return logDirPath;
  }
}