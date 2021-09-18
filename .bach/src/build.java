import com.github.sormuras.bach.Bach;
import com.github.sormuras.bach.simple.SimpleSpace;
import com.github.sormuras.bach.external.JUnit;

class build {
  public static void main(String... args) {
    try (var bach = new Bach(args)) {
      var grabber = bach.grabber(JUnit.version("5.8.0"));

      var main =
          SimpleSpace.of(bach, "main")
              .withModule("se.jbee.spacecrafts")
              .withModule("se.jbee.turnmaster");
      main.compile(javac -> javac.add("-Xlint"), jar -> jar.verbose(true));

      bach.logCaption("Perform automated checks");
      var test = main.newDependentSpace("test").withModule("test.integration");
      test.grab(grabber, "org.junit.jupiter", "org.junit.platform.console");
      test.compile(javac -> javac.add("-g").add("-parameters"));
      test.runAllTests();
    }
  }
}
