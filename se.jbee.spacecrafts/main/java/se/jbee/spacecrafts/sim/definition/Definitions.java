package se.jbee.spacecrafts.sim.definition;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Phenomenon;
import se.jbee.turnmaster.data.Any;
import se.jbee.turnmaster.data.Any.Defined;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.Numbers;

public interface Definitions {

    interface Defining {

        Defined newDefined(int serial, String code);

        <T extends Any.Entity> Flux<T> newFlux(Class<T> of);

        Numbers newNumbers();

        Game.Objects objects();
    }

    interface InfluenceSource {

        String code();

        Resourcing.Process progression();
    }

    interface AddPhenomena extends Defining {

        default <D extends Record & InfluenceSource> void addPhenomenon(String phenomenon, D... members) {
            var influences = newFlux(Influence.class);
            for (D member : members) {
                influences.add(objects().influences().spawn(
                    serial -> new Influence(newDefined(serial, member.code()),
                        member.progression(), newNumbers().clear())));
            }
            objects().phenomena().spawn(
                serial -> new Phenomenon(newDefined(serial, phenomenon),
                    influences.inStasis()));
        }

    }
}
