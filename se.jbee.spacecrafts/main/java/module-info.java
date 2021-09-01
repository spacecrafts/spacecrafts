module se.jbee.spacecrafts {

    requires transitive se.jbee.turnmaster;

    exports se.jbee.spacecrafts.sim to test.integration;

    exports se.jbee.spacecrafts.sim.definition to test.integration;
    exports se.jbee.spacecrafts.sim.decision to test.integration;
    exports se.jbee.spacecrafts.sim.deduction to test.integration;

}
