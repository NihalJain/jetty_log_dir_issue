package io.github.nihaljain;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.ee8.servlet.ServletContextHandler;
import org.eclipse.jetty.ee8.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.ee8.servlet.DefaultServlet;
import org.eclipse.jetty.server.Handler;

public class JettyServer12 {
  private static final Logger logger = LoggerFactory.getLogger(JettyServer12.class);

  public static void main(String[] args) throws Exception {
    String logDirPath = Util.getLogDirPath(args);

    Server server = new Server(8080);
    ContextHandlerCollection parent = new ContextHandlerCollection();

    ServletContextHandler logContext = new ServletContextHandler(parent, "/logs");
    logContext.addServlet(new ServletHolder(new DefaultServlet()), "/");
    logContext.setResourceBase(logDirPath);
    logContext.setDisplayName("logs");

    parent.addHandler(logContext);

    Handler.Sequence handlerCollection = new Handler.Sequence();
    handlerCollection.addHandler(parent);

    server.setHandler(handlerCollection);

    server.start();
    System.out.println("Server started at port 8080");

    server.join();
  }
}
