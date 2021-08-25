# Crafting Mechanics

## Ideas

* Litter planets surface with special cells that are occupied, resources or just
  rubbish in the way. Techs can then allow to remove that allowing larger
  undisturbed modules.
* have construction components suitable for surface have different boosts than
  one for orbit
* use an _underground_ deck for colonies to model a mirror plane of the surface
  plane where pirate like fractions establish their operations. These are no
  ordinary building but what a building existing on the surface is used as. This
  means they cannot actively build new buildings, but they can use they for
  their affairs on the underground plane.
* most fractions only use planet surface deck - subterranean fractions can use a
  second _subterranean_ deck (which basically doubles the planet size)
* on colony have simple construction yard be limited to 1 deck construction and
  offer a construction hanger that can do 3 decks or so but has higher costs.
  Orbit has the benefit of allowing many decks but is less efficient. a space
  station has the benefits of allowing multi-deck construction with a higher
  efficiency but has higher demands in general.

## More Unlikely Ideas

* do component scaling size where each cell gives a base size and each edge to a
  compatible component gives additional "size" or scale. Size then uses the
  usual curve (list of "from x get y points") to get to effective points.
* add "sockets" to components, e.g. for lasers and other tech that can operate
  on crystals there can be a socket to plug in a crystal that buffs the
  efficiency and other stats => via edges

## Computation

* is dynamic (needed because of player control and damage):
    * what is a cluster?
    * how large is the boost?
    * what is an add-on?
* is always component/edge based

## Grid, Reach, Edges and Boosts

* grid pattern (4 edge neighbors, corners do not "touch")
* 1 module per deck (vertical layer), no 2d module overlap
* required input(s) are fixed per cell
* output has a fixed base value per cell
* same components touching form a cluster
* cluster boost is based on touching edges of same components
* e.g. +1 cluster boost for each touching edge
* e.g. +2 cluster boost for each cell with 2 touching edges
* e.g. +3 cluster boost for each cell with 3 touching edges
* ...
* components have a reach (number of cells in particular directions affected or
  required for the component to operate)
*
* input priority is given for each cluster covering any requirement, cluster
  that cannot be equipped to minimal requirements are skipped and not supplied
* reach and edge bonus are opposing forces. Cluster bonus increases the more
  components are covering full areas as this makes sure many of the cells have
  maximum edges. Reach on the other hand gives the greatest benefit if the
  components are spread out as they will cover the most area of other components
  that might require the reach. They are the two opposing goals players have to
  balance when deciding which shape clusters should have.
* make the hull (outline of a module) conduct energy and other resources needed
  by components

## Units of Organisation

* Each construction is individual, not following a linked pattern (but existing
  ships or ongoing constructions can be used as template)
* Flatten levels by allowing to build a module frame component within a "empty"
  cell of a module. Each new module then is independent one listed in which then
  components can be build. While this stacks from the users point of view the
  organisation only knows modules with components in them just that some
  components have the effect of creating new modules (canvases for components).

## Edges

When components of the same type tough, they belong to the same component
cluster. Each edge gives a bonus. Add-ons and same component bonus are basically
the same mechanism just that an add-on A states that a neighbouring component B
gets more output of type X while usually the B has no effect on A. If two B's
neighbour they affect each other. So add-ons improve neighboring components to
the degree they share edges (if they fit).

## Reach

* add-on: have no reach
* weapon: how far they can hit (depends on focus and targeting)
* weapon reach extends with size, focus add-ons and targeting computer add-ons
* shield: how far the shield gives protection
* drives: how far from the crafts outline the drive can be placed (and be
  effective); different types of drive have different reach allowing for
  components that stretch horizontal or require them to mostly stretch vertical.
  This again forces shapes by function.
* deck connectors: how large the connected deck can be?

## Surface

* have a "surface map" describing what the terrain looks like one could build
  upon (or not) as a basis to further limit what can be build where => more
  random, less same things again more general this is about component fit, like
  a precondition
