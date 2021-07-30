package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Algorithm;

public record Game(Entities entities) {

    public record Settings() {}

    public record Entities(
            Pool<Algorithm<?>> algorithms,

            Range<Resourcing.Property> properties,
            Range<Resourcing.Resource> resources,
            Range<Resourcing.Manifestation> manifestations,
            
            Pool<Crafting.Component> components,
            Pool<Crafting.Craft> crafts,
            Pool<Crafting.Deck> decks
    ) {

    }
}
