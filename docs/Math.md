This file describes the concrete math used to calculate the advancement of the game. 

# Player Resources
Are resources counted per player.

## Rare Materials
Rare materials are stockpiled and spent for construction.
Amounts are deducted when starting the component that required the rare materials.
Should the construction be canceled the rare materials are refunded.  

	mine-production = sum(floor(cluster(mine-base))) * culture-factor * leader-rm-factor + leader-rm-supply
	rare-materials += mine-production

Discovery: Every 10 staffed labs searching double the basic-chance to discover more rare materials.
The basic chance decreases with every depot discovered. 
		
	rm-basic-chance = rm-level - (100% * rm-depots / planet-slots)
	rm-discovery-chance = rm-labs * (rm-basic-chance / 10) 
		
`rm-labs` = number of labs searching for rare materials
`rm-depots` = number of discovered rare material depots (with or without mine) 

Mineral based races are not limited by labs. They can use all staff to search without having a lab.

## Wisdom
Wisdom is per race (player). It increases through academies and decreases when hiring leaders or diplomatic inconsistency.

	wisdom += sum(floor(cluster(wisdom-base))) * culture-factor * leader-wisdom-factor + leader-widsom-supply
		
Hiring:		
		
	wisdom -= leader-wisdom 
 
 
## Knowledge 
The knowledge is used to research technology in labs.
The breakthrough point is initialized with the complexity of the technology researched in decreases to 0.
Then the technology is complete and the next one is chosen. 
When technology under research is changed the progress is lost.  

	breakthrough -= sum(floor(cluster(knowledge-base))) * culture-factor * leader-knowledge-factor + leader-knowledge-supply

Specific technologies do not have specific costs or complexity.
Instead there is a base cost for any technology that increases with the number of already researched technologies.
The base cost is multiplied based on the position within the web relative to the previous technology researched.

The formula is:

	complexity = (20n + floor(n/2)^2 + floor(n/3)^3) * switch-factor
	
with `n` being n-th technology researched (so it starts with 1)
and `switch-factor` being 

* 1/2 (same cell as before), 
* 1 (adjacent as before or in players sector) or 
* 2 (otherwise) 



# Structure Management
A structure can have many modules. 
A planet is a structure with a single module, the surface. Hence no modules are required to place components.
The Orbit is like space for a stationary spaceship. Hence modules are required to place components.

## Housing
Cellular races use biospheres and domes to house their population.

	housing = sum(floor(cluster(housing-base)) 
		
Cyborg races use regen-bays as housing. They are simply less flexible. 
Mineral based life forms do not need housing at all. 

Robotic populations do not grow. 
They have to build more units using construction and house them in warehouses.
The costs for more robots depend on the `game-speed` since usual population growth does as well. 

	robots-cost = floor(game-speed / 2) + 1
 
## Food
Population and food are calculated per structure.
1000 citizens are handled as a staff unit.
Each staff unit consumes one unit of food per turn. 
Staff (and population and growth) are limited by housing.

	food-production = sum(floor(cluster(food-base))) * culture-factor * leader-food-factor + leader-food-supply
	population-limit = housing * 1000
	population-growth += (food-production - staff + natural-growth) * game-speed
	population += population-growth
	staff = floor(population / 1000)

Robotic races consume energy instead of food. Their staff does not die but idles without energy.
Cyborg races are limited to the cyber-nursery that has to be staffed to be effective.
`natural-growth` is 2 for cellular, 1 for mineral and 0 for robots and cyborgs.

## Energy
Energy production and usage is looked at per structure and for each turn individually.

	energy = sum(floor(cluster(energy-base)) * leader-energy-factor
	energy-consumption = sum(energy-usage) + robot-energy-consumption
	
For robotic races each worker consumes 1 unit of energy per turn to work. 
Without energy they do not work.

	robot-energy-consumption = staff

## Construction
New components (or modules) can be build on all empty slots of the structure.
Each structure has a single build queue. 
All yards and yard-workers work on that queue. 
Components on the surface (module) get a queue position each. 
Components within modules are completed together (one position in the queue).
Components in modules first become available when the whole module is done.
Multiple queue positions can be completed in one turn.

	production = (sum(yard-base) + sum(yard-worker * culture-factor)) * speedup-factor
		
Production points are used to complete the building costs of the items in the queue (ration 1:1).

Mineral based races can speed up construction using rare materials.

	speedup-factor = 1 + (rare-material-addition / sum(building-cost))
		
So a 100% speedup costs the same amount of rare materials as the build component(s) cost.
The player can decide to use any amount of rare-materials available. 
The decision is made up front and it final.

The basic building cost of a component can be reduced by 1 when build at a slot with a frame. 

## Culture
A per planet factor between 0.5 and 1.5 (50-150%) effecting the staffs output.
This corresponds to a cultural level from -5 (50%) to +5 (150%) with 0 being 100%.

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

Events or abilities simply add or subtract to the culture buffer once in the same way produced culture does. 

Newly founded colonies start with a culture-level of 1 and an half full buffer.
The culture is negated (when positive) when colonies are captured (resistance) or becomes maximum negative (-5).


## Command Points
Command Points reflect a players fleet size.
The bridge component (used in ships) generates CPs.
Great ship leaders get attracted by control points.

	command = sum(cluster(command-base)) + leader-control-supply
	
Depending on the race characteristics command points are needed per structure to control troops.
On ships the size of the bridge determines the possible crew's quarters.
On planets the size of the headquarters determines the possible number of barracks.  
For each command point one barrack/crew's quarter can be build.

Collective races can build any number of troop quarters.	


## Clusters and Banks
When building same component directly adjacent to each other the unity of them is called a cluster or bank.
Each component type has a linkage factor that tell how good it clusters.
The `cluster(n)` function is:

	cluster = base * n * ( 1 + linkage * n)

With `n` being the amount of components in the cluster,
`base` the output for a single component and
`linkage` the factor like 0.03 for a 3% linkage.
This means the bonus grows quadratic with the number of components in a cluster.


# Reputation
A players reputation is an aggregate of the players status as well as his previous actions.
Technologies, leaders and such do raise the reputation, inconsistent actions lower it.
The details are not yet clear.
 
 
# Materials
There are 5 materials in the game.

* Irozine (the "standard")
* Litalium (ultra light, quite weak)
* Nicaron (strong but elastic, can go huge)
* Tritanium (strong and lighter than iron, hard to work with)
* Irmond (super strong in all regards. dense, heavy! rare. nightmare to work with)

Properties

* hull points/cell (more hull => tougher)
* weight/cell (drive effect is relative to the ships weight)
* building costs/cell over size (takes longer to build)
* special characteristics that make certain weapon more or less effective


Properties that are cell based might be (non-linear) functions! Maybe just make cost a function.

				hp	w	c
	Irozine		3	6	1
	Litalium	1	2	1
	Nicaron		3	5	3
	Tritanium	6	4	6
	Irmond		12	12	9

The costs are given in ranges. 
For example 1-5 cost 1, 2-10 cost 2.
A module of size 8 cost 5*1 + 3*2 = 11.
For most materials there will be ranges where they are more cost efficient.
Most importantly the ratios of cost/hp and cost/weight should not diverge too much.
There are of course areas where material shine (better ratio) and look poor (worse ratio).
But overall there should be a constant the field is centered upon. 
	
The costs should not be the same for all races as mineral based grows ships. 
They do not have same trouble to work the super strong ones.


# Minimal Ship
An example calculation for a minimal ship.

Required systems:

* 1 Bridge
* 1 Crew's Quarters
* 1 Impulse Drive
* 1 Wrap Drive
* 1 Fuel Cell

Additions for a colony ship:

* 1 Biosphere (in a separate module)

The energy of one fuel cell should be enough to have this ship working.
To colonize a planet one "lands" the module on that planet.
To colonize within the same solar system no wrap drive is needed (so it might be ok requiring 2 fuel cells for such a ship).

Rough cost math:

	5 (cells)
	2 bridge
	2 quarters
	2 fuel cell
	2 Impulse Drive
	---------------
	13
	
# Damage
How damage is dealt to ships.
Choice of material and size gives hull points.
Each component (like a laser) has structure points.
There might be plating and shields.

If shields are effective the damage is subtracted from the shield strength.
If shields are down or not effective damage is dealt to plating (if available).
Plating works similar to shields. Most of the time only one of them applies to the kind of damage dealt.
Without shields and plating the hull is damaged (with full damage left). 
At the same time the affected component(s) are determined and the same damage is dealt to them
what might cause a malfunction or destruction of that component. 
The hull points tells when ship falls apart.
The component structure points tell when a component stops working.  