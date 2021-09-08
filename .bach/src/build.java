import com.github.sormuras.bach.Bach;
import com.github.sormuras.bach.conventional.ConventionalSpace;
import com.github.sormuras.bach.external.JUnit;

class build {
  public static void main(String... args) {
    try (var bach = new Bach(args)) {
      var grabber = bach.grabber(JUnit.version("5.8.0-RC1"));

      var main =
          ConventionalSpace.of(bach, "main")
              .modulesAddModule("se.jbee.spacecrafts")
              .modulesAddModule("se.jbee.turnmaster");
      main.compile(javac -> javac.add("-Xlint"), jar -> jar.verbose(true));

      bach.logCaption("Perform automated checks");
      var test = main.newDependentConventionalSpace("test").modulesAddModule("test.integration");
      test.grab(grabber, "org.junit.jupiter", "org.junit.platform.console");
      test.compile(javac -> javac.add("-g").add("-parameters"), jar -> jar);
      test.runAllTests();
    }
  }
}
