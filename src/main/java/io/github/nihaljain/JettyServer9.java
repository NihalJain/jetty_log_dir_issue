package io.github.nihaljain;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.server.Handler;

public class JettyServer9 {
  private static final Logger logger = LoggerFactory.getLogger(JettyServer9.class);

  public static void main(String[] args) throws Exception {
    String logDirPath = Util.getLogDirPath(args);

    Server server = new Server(8080);
    ContextHandlerCollection parent = new ContextHandlerCollection();

    ServletContextHandler logContext = new ServletContextHandler(parent, "/logs");
    logContext.addServlet(new ServletHolder(new DefaultServlet()), "/");
    logContext.setResourceBase(logDirPath);
    logContext.setDisplayName("logs");

    parent.addHandler(logContext);

    HandlerCollection handlerCollection = new HandlerCollection();
    handlerCollection.addHandler(parent);

    server.setHandler(handlerCollection);

    server.start();
    System.out.println("Server started at port 8080");

    server.join();
  }
}
