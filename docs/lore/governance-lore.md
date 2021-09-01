# Governance

On the management of people, resources and so forth in the game.

## Ideas

* each player can only manage population of its own race
* if population is captured and very similar to the own race they might just be
  treated as if they "become" the players race
* biological and silicon based races have a natural basic growth, robots and
  cyborgs do not
* there should be ways to damage enemy economy other than having spies do
  sabotage
* event: a stranger offers blueprints for something else, may turn out to be
  fake
* black market: trade unofficial goods, like old ships, technologies, rare
  resources
* Components like a hydroponic farm should create pollution and negative impact
  on spirit.

## On Uniqueness and Differences

Nothing is just be a better version of another thing. There needs to be
something you cannot get otherwise. This is what allows unique strategies that
are fun to try.

## On Colony vs Orbit

Colony should be better building small modules, Orbit better at building large
modules. => colony on 1 deck, orbit many decks at a time There must be a limit
to workers so workers work similar to other buildings where a building enables a
certain number of workers and here even module size and cells that can be worked
on. Also there maybe should a maximum cell distance between a component
providing a workplace, and a cell constructed by that place.

## Ecosystem

The conditions of the ecosystem are tracked per planet. A healthy ecosystem is
required for farms and forests to work and grow. Tree nurseries are used to help
the ecosystem to develop faster. Like population growth the ecosystems "grows".
Food forests are planed when "build" but grow through a healthy ecosystem and
decline when it is unhealthy.

## Professions

6+1 professions:

    C craftsman (construction)
    F farmer (food)
    M miner (mining)
    R researcher (research)
    E explorer (searching)
    S scholar (academy)
    A artist (studio)

## Resources

Player Resources (Scores):

* Reputation
* Wisdom

Module Resources:

* Energy
* Rare Materials
    * Rare Metals
    * Rare Earths
    * Rare Crystals
* Control ? => replace with boost add-ons
* Command
* Food
* Housing
* Knowledge
* Culture (in a way a mental pollution level)
* Spirit
* Pollution

Ship Properties:

* Mass (Weight)

### Reputation

A players' reputation is an aggregate of the players' status as well as his
previous actions. Technologies, leaders and such do raise the reputation,
inconsistent actions lower it. The details are not yet clear.

### Culture

How people think about solving problems, what solutions they seek. Worrier
culture vs explorer vs ?.

A per planet factor between 0.5 and 1.5 (50-150%) effecting the staffs output.
This corresponds to a cultural level from -5 (50%) to +5 (150%) with 0 being
100%.

Each turn the culture is updated:

	culture += sum(cluster(culture-base)))

When `culture` > `culture-level-capacity`:

	culture -= culture-level-capacity
	culture-level += 1
	culture-level-capacity = staff * abs(culture-level)

When `culture` < 0:

	culture-level -= 1
	culture-level-capacity = staff * abs(culture-level)
	culture-buffer = culture-level-capacity + culture-buffer

The current factor is always derived from the current level:

	culture-factor = (10 + cultural-level) / 10

Events or abilities simply add or subtract to the culture buffer once in the
same way produced culture does.

Newly founded colonies start with a culture-level of 1 and an half full buffer.
The culture is negated (when positive) when colonies are captured (resistance)
or becomes maximum negative (-5).

### Command

A resource of how many workers can be assigned to specific tasks by the player.
This limits the total amount of workers that can be assigned. Command is not "
used" or "required" by components but by workers working in them. Should a
population be larger the additional population cannot be assigned to work until
the control is increased. => only for players with hierachy culture? Leaders
often have boni for control. Otherwise components like a bridge or a captiol are
used to enhance control. The base control originales from the social
organisation. Some forms might not need control but this might also mean that
player might not be able to assign tasks at will. The worker might decide
themselves instead.

### Wisdom

Wisdom should be linked to spirit or culture. In an environment with low culture
and spiritual being there is little change to gain important wisdom. Wisdom is
per race (player). It increases through academies and decreases when hiring
leaders or diplomatic inconsistency.

### Spirit

Is both the short term mood people get into and their level of being connected
with nature and soul. In contrast, the culture is more about customs and rites
and their long term effects.

### Knowledge

The knowledge is used to make discoveries using e.g. labs. The breakthrough
point is initialized with the complexity of the researched area of interest in
decreases to 0. Then the discovery is made, and the next area of interest is
chosen. When research is redirected while not complete the progress is lost.

Specific areas of interest do not have specific costs or complexity. Instead,
there is a base cost for any area of interest that increases with the number of
already made discoveries. The base cost is multiplied based on the position
within the web relative to the previous technology researched.

The formula is:

	complexity = (20n + floor(n/2)^2 + floor(n/tech-speed)^3) * switch-factor

with `n` being n-th technology researched (so it starts with 1)
and `tech-speed` being 3 for long games (slow research) and up to 6 for short
games (fast research)
and `switch-factor` being

* 1/2 (same cell as before),
* 1 (adjacent as before or in players sector) or
* 2 (otherwise)

## Command

Command Points reflect a players fleet size. The bridge component (used in
ships) generates CPs. Great ship leaders get attracted by control points.

	command = sum(cluster(command-base)) + leader-control-supply

Depending on the race characteristics command points are needed per structure to
control troops. On ships the size of the bridge determines the possible crew's
quarters. On planets the size of the headquarters determines the possible number
of barracks.  
For each command point one barrack/crew's quarter can be build.

Collective races can build any number of troop quarters.

## Rare Materials

Rare materials are stockpiled and spent for construction. Amounts are computed
when starting the component that requires the rare materials. Should the
construction be canceled the rare materials are refunded.

Rare materials are discovered by chance. The higher the mine's output the higher
the chance of discovery. The chance of discovery lowers the more discoveries are
made.

Mineral based races are not limited by labs. They can use all staff to search
without having a lab.

Each planet has one of the following levels for rare materials:

1. poor
2. sparse
3. scattered
4. common
5. rich

### Housing

Cellular races use biospheres and domes to house their population.

Cyborg races use regen-bays as housing. They are simply less flexible. Mineral
based life forms do not need housing at all.

Robotic populations do not grow. They have to build more units using
construction and house them in warehouses. The costs for more robots depend on
the `game-speed` since usual population growth does as well.

	robots-cost = floor(game-speed / 2) + 1

### Food

Population and food are calculated per structure. 1000 citizens are handled as a
staff unit. Each staff unit consumes one unit of food per turn. Staff (and
population and growth) are limited by housing.

	food-production = sum(floor(cluster(food-base))) * culture-factor * leader-food-factor + leader-food-supply
	population-limit = housing * 1000
	population-growth += (food-production - staff + natural-growth) * game-speed
	population += population-growth
	staff = floor(population / 1000)

Robotic races consume energy instead of food. Their staff does not die but idles
without energy. Cyborg races are limited to the cyber-nursery that has to be
staffed to be effective.
`natural-growth` is 2 for cellular, 1 for mineral and 0 for robots and cyborgs.

### Energy

Energy production and usage is looked at per structure and for each turn
individually.

	energy = sum(floor(cluster(energy-base)) * leader-energy-factor
	energy-consumption = sum(energy-usage) + robot-energy-consumption

For robotic races each worker consumes 1 unit of energy per turn to work.
Without energy, they do not work.

	robot-energy-consumption = staff

### Production

New components (or modules) can be build on all empty slots of the structure.
Each structure has a single build queue. All yards and yard-workers work on that
queue. Components on the surface (module) get a queue position each. Components
within modules are completed together (one position in the queue). Components in
modules first become available when the whole module is done. Multiple queue
positions can be completed in one turn. Multiple staff units can work on same
yard (1-5). Also work can be done without a yard at all (bare workers are less
efficient).

	production = (sum(yard-base * culture-factor) + sum(bare-worker * culture-factor)) * speedup-factor

Production points are used to complete the building costs of the items in the
queue (ration 1:1).

Mineral based races can speed up construction using rare materials.

	speedup-factor = 1 + (rare-material-addition / sum(building-cost))

So a 100% speedup costs the same amount of rare materials as the build
component(s) cost. The player can decide to use any amount of rare-materials
available. The decision is made up front and it final.

The basic building cost of a component can be reduced by 1 when build at a slot
with a frame.

On ships building works differently as a yard can only be build on surface or in
orbit. Engineering decks can be added to ships to allow building there. No bare
workers on spacecrafts. They work similar to the other economic buildings. Each
deck cell allows for one staff unit. A deck can (re)build or replace components
within the ship or build a new module on top of the deck area. Once the module
is done it can be connected to the ship or become a ship of its own.

	production = sum(deck-base * culture-base) * speedup-factor

Robotic races need this to build troops "on board".

## Materials

There are 6 materials in the game.

* Irozine (the "standard")
* Litalium (ultra light, quite weak)
* Nicaron (strong but elastic, can go huge)
* Tritanium (strong and lighter than iron, hard to work with)
* Zirkonite ( zirkonium-nickel alloy, stronger and heavier then iron/steel,
  strong, good resistance, difficult to work with, a plating material)
* Iridiamond (super strong in all regards. dense, heavy! rare. nightmare to work
  with)

Properties

* hull points/cell (more hull => tougher)
* weight/cell (drive effect is relative to the ships weight)
* construction costs/cell over size (takes longer to build)
* special characteristics that make certain weapon more or less effective

  			HP	WT	CC
  Irozine 3 6    [see SCSInfo]
  Litalium 1 2    [see SCSInfo]
  Nicaron 3 5    [see SCSInfo]
  Tritanium 6 4    [see SCSInfo]
  Zirkonite 12 8    [see SCSInfo]
  Iridiamond 18 12    [see SCSInfo]

The costs are given in ranges. For example 1-5 cost 1, 6-10 cost 2. This is
written a `[(1 1), (6 2)]`
A module of size 8 cost 5*1 + 3*2 = 11. For most materials there will be ranges
where they are more cost efficient. Most importantly the ratios of cost/hp and
cost/weight should not diverge too much. There are of course areas where
material shine (better ratio) and look poor (worse ratio). But overall there
should be a constant the field is centered upon.

The costs should not be the same for all races as mineral based grows ships.
They do not have same trouble to work the super strong ones. Plating strength is
computed similar to hull points:
Number of cells times plating material HP. Same with extra weight through
plating and the costs follow the costs sequence. This keeps materials from
becoming overly complex and it is more or less logical that a frame and the
plating for it are somewhat equal.
