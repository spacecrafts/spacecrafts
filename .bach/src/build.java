import com.github.sormuras.bach.*;
import com.github.sormuras.bach.external.JUnit;

class build {

  public static void main(String... args) {
    try (var bach = new Bach(args)) {
      var grabber = bach.grabber(JUnit.version("5.8.0-RC1"));

      var main = bach.builder().conventionalSpace("main", "se.jbee.spacecrafts", "se.jbee.turnmaster");
      main.compile(javac -> javac.with("-Xlint"), jar -> jar.with("--verbose"));

      bach.logCaption("Perform automated checks");
      var test = main.dependentSpace("test", "test.integration");
      test.grab(grabber, "org.junit.jupiter", "org.junit.platform.console");
      test.compile(javac -> javac.with("-g").with("-parameters"));
      test.runAllTests();
    }
  }
}
