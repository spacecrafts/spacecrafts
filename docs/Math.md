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

	complexity = (20n + floor(n/2)^2 + floor(n/tech-speed)^3) * switch-factor
	
with `n` being n-th technology researched (so it starts with 1)
and `tech-speed` being 3 for long games (slow research) and up to 6 for short games (fast research)
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
Multiple staff units can work on same yard (1-5).
Also work can be done without a yard at all (bare workers are less efficient).

	production = (sum(yard-base * culture-factor) + sum(bare-worker * culture-factor)) * speedup-factor
		
Production points are used to complete the building costs of the items in the queue (ration 1:1).

Mineral based races can speed up construction using rare materials.

	speedup-factor = 1 + (rare-material-addition / sum(building-cost))
		
So a 100% speedup costs the same amount of rare materials as the build component(s) cost.
The player can decide to use any amount of rare-materials available. 
The decision is made up front and it final.

The basic building cost of a component can be reduced by 1 when build at a slot with a frame. 

On ships building works differently as a yard can only be build on surface or in orbit. 
Engineering decks can be added to ships to allow building there. No bare workers on spacecrafts.
They work similar to the other economic buildings. 
Each deck cell allows for one staff unit.
A deck can (re)build or replace components within the ship or build a new module on top of the deck area.
Once the module is done it can be connected to the ship or become a ship of its own.

	production = sum(deck-base * culture-base) * speedup-factor

Robotic races need this to build troops "on board".


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


## Ecosystem
The conditions of the ecosystem are tracked per planet.
A healthy ecosystem is required for farms and forests to work and grow.
Tree nurseries are used to help the ecosystem to develop faster.
Like population growth the ecosystems "grows". 
Food forests are planed when "build" but grow through a healthy ecosystem and decline when it is unhealthy. 


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
Components can have a progressive property that depends on the amount of components in a cluster.
Such a progression is a sequence of pairs `(n base)` like `[(1 2),(5 3),(10 4)]`
The n in all pairs of a sequence must increase throughout the sequence.
It gives the first `n` for which the new `base` is valid. So base `2` (first pair) is valid `>=1` and `<5` (from next pair).
So the first pair usually is for `n=1`. If it is absent a base of zero is assumed for all `n` < first pairs `n`.
The base can go up and down during a sequence and even become zero.

The sum for a cluster of size `n` is calculated by summing the sections with different bases.
For the example above and different n's

	n=2 : 2*2 = 4
	n=5 : 4*2 + 1*3 = 11
	n=12: 4*2 + 5*3 + 3*4 = 35

While they do not have a single mathematical formula this way is quite intuitive for humans and allows for all kinds of curves.


# Reputation
A players reputation is an aggregate of the players status as well as his previous actions.
Technologies, leaders and such do raise the reputation, inconsistent actions lower it.
The details are not yet clear.
 
 
# Materials
There are 6 materials in the game.

* Irozine (the "standard")
* Litalium (ultra light, quite weak)
* Nicaron (strong but elastic, can go huge)
* Tritanium (strong and lighter than iron, hard to work with)
* Zirkonite ( zirkonium-nickel alloy, stronger and heavier then iron/steel, strong, good resistance, difficult to work with, a plating material)
* Iridiamond (super strong in all regards. dense, heavy! rare. nightmare to work with)

Properties

* hull points/cell (more hull => tougher)
* weight/cell (drive effect is relative to the ships weight)
* construction costs/cell over size (takes longer to build)
* special characteristics that make certain weapon more or less effective

				HP	WT	CC
	Irozine		3	6	[see SCSInfo]
	Litalium	1	2	[see SCSInfo]
	Nicaron		3	5	[see SCSInfo]
	Tritanium	6	4	[see SCSInfo]
	Zirkonite	12	8	[see SCSInfo]
	Iridiamond	18	12	[see SCSInfo]

The costs are given in ranges. 
For example 1-5 cost 1, 6-10 cost 2. This is written a `[(1 1), (6 2)]`
A module of size 8 cost 5*1 + 3*2 = 11.
For most materials there will be ranges where they are more cost efficient.
Most importantly the ratios of cost/hp and cost/weight should not diverge too much.
There are of course areas where material shine (better ratio) and look poor (worse ratio).
But overall there should be a constant the field is centered upon. 
	
The costs should not be the same for all races as mineral based grows ships. 
They do not have same trouble to work the super strong ones.
Plating strength is computed similar to hull points:
Number of cells times plating material HP.
Same with extra weight through plating and the costs follow the costs sequence.
This keeps materials from becoming overly complex and it is more or less logical that a frame and the plating for it are somewhat equal.


# Drives

## Wrap Drive
Wrap speed depends on the size of the ship in relation to wrap output.

	wrap-speed = wrap-capacity / size

Wrap speed is given in parsec.
When size and output have a ration of 1:1 a ship does 1 parsec per turn.
That means the output of the wrap drive has to scale linearly with the ships size to keep up the same speed. 
A speed of 1 parsec is reasonably fast. 
A realistic range would be 1-10 parsec.
So a really fast (wrap drive heavy) ship should have an output of 10 times its size.
A 500 size ship with heavy wrap would use 1/4th wrap drive.
So 125 should output 5000, that is 40 per cell on average.
 

## Impulse Drive
Impulse speed depends on weight per drive output.
Minimal weight per cell is around 3-4. Maximum is around 30.
Minimal ship size is around 5, maximum around 500.
This gives ship weights from 15 to about 15000.
A ship that heavy should be slow unless large portion of that ships is in fact spent for drive.
A powerful drive should take about 1/4 of ship size.
So 125 drive cells should give reasonable speed for a ship weighing 15000t.
Now, a drive should be seen as a device converting energy to thrust.
So each cell can output a range depending on the energy input. 
But that is just a side note as ships would usually use 100% of drive capacity.
If impulse speed 1 is a ratio thrust/weight of 1:1, and a maximum of impulse speed 10 would be a ratio of 10:1 the drive would need to output 120 thrust on average at that size. So say a drive outputs 50 trust at size 1 a small ship weighing 20 would get to impulse speed 2.5 which sounds about right. 

	impulse-speed = thrust/weight
	thrust = sum(cluster(impulse-drive-base))
	weight = sum(material-weight) + sum(component-weight)

Best energy generation can output about 2300 units of energy at a size of 125 cells (1/4 of a 500 size ship).
If also about 1/4 of that 2300 is spend for drive this gives 500 energy for 125 drive cells. So each could take up 4.
It seams reasonable to lower this to 3 as we talk about the best energy generation and a quite drive heavy ship.
So a drive could use 0-3 energy. The thrust will increase linearly to the maximum as given in the thrust output sequence.
So then a certain impulse speed should be reached we compute the necessary thrust and the maximum output of the drive as well as the energy costs for that.
Than the needed thrust is put into proportion with the maximum thrust. Energy will have same proportion. This is the energy consumed. 


	
# Damage (Battle)
How damage is dealt to ships.

Each module is calculated independently (except for shields that guard the whole ship).
There might be plating, coating and shields.
Choice of material and module size gives hull and plating points.
Each component type (like a laser) has structure points (per cell).

Weapons deal parts of their damge to different "levels".
The levels are:

0. Hull (Components)
1. Plating
2. Shields

If partial damage dealt to a level makes that level ineffective it is dealt to the level below,
if that level is present and if the weapon used deals any damage to that level.
All damage is dealt to some level. So the hull is the last to take all the rest.
If the hull breaks the module is lost. If the central module break the ship falls apart and is lost. 
If any of the other break they become ineffective.
Shields might recharge.
Damage dealt to the hull will also be distributed upon the component(s) of the module.
Component damage might cause a malfunction or destruction of that component. 
The component structure points tell when a component stops working.
Depending on the weapon component damage can "spread" to adjacent components in case a component is destroyed. 

The different ranges and speeds of weapons are represented by dividing a turn during battle in 4 phases:

1. waves (electromagnetic beams)
2. particles (particle beams)
3. masses (mass drivers)
4. special 

Within each phase ships act (roughly) from highest to lowest manoeuvrability.
They might drop shields or rechannel energy before firing a weapon.
Such changes however cannot be undone within the same turn.
Shields stay up or down.
The consequences of any damage are effective immediately. 
That means damaged opponents might not get their cache.
Special weapons and other actions occur in phase 4. These are:

* repairing (drones, crew)
* scanning
* shields up/down (if not already changed)
* unpower/power components (if not already changed)
 
Energy available is set at the beginning of the turn.
When action use energy the energy is reduced by the used amount.
If energy generator is damaged during a phase the energy available is reduced by output of that component divided by the phase. So a damage during phase 1 means loss of whole output, during 2 halve of it and so forth. The energy required for the survival critical infrastructure will be consumed before phase 1.
This can be turned off or altered during phase 4 for the next turn.
Energy capacitors can be used to buffer energy.
Up to the capacity is always kept if it has been generated at the start of the turn. 

## Shields
Shields create a strong electromagnetic field around the ship.
A bubble protected from certain kinds of weapons by deflecting beams. 

The maximum strength of a shield (its capacity) is given by the sum of shield outputs of all shield banks.

	shield-capacity = sum(shield-base * bank-factor)
	
Damage is subtracted from the shield capacity:

	shield-strength[100%] = shield-capacity
	shield-strength =- shield-damage
	
Each turn shields can regenerate some of the lost strength.
The amount depends on the recharge ability of the specific shield.

	shield-recharge = sum(shield-recharge-base) + sum(shield-capacitor-base)
	shield-strength += shield-recharge
	
Recharge only occurs when sufficient energy is provided.
The recharge can be speed up using field capacitors.
Their benefit (over a larger shield) is a smaller energy consumption while the recharge increase is even better.
 

	shield-weakening = min(shield-damage, shield-protection)
	overflow-damage = shield-damage - shield-weakening
	
Overflow damage is dealt to next appropriate level.


## Plating
The strength of plating depends on the plating material and the size of the module.

	plating = sum(material-base * module-size)
	
Plating is calculated per module.
Damage is subtracted from the plating strength:

	plating-absorption = material-base * 2
	plating -= plating-damage
	
Most damage is done to plating before it affects the hull.
Damage must be larger than the hull absorption to have an effect.
That means plating has to be reduced to zero to damage the hull and the components within.


## Hull
The strength of the hull is given by the material and the number of cells in a module.
Hull is calculated per module.

	hull = sum(material-hull * module-size)
	
Damage is subtracted from the hull.

	hull-absorption = material-hull
	hull -= hull-damage

If hull damage is less than a materials hull base value (1 cell) the hull is not damaged.
Such damage is to small to have an impact.	
Once hull points are down to zero the ship is destroyed.
Damage to the hull cannot be fixed during battle.
The hull can be repaired after battle.
A larger crew makes for a faster repair. Repair drones help as well.

	(math here)
	
Ships that orbit a planet with orbital docks get repaired as well.

	hull += sum(orbital-dock-repair)
	
Multiple ships can be repaired in parallel. 
Each gains repair from the orbital dock. The more dock cell the faster a full repair.

Ships can also be repaired on the surface. 
This a build item that explicitly has to be chosen. 
Building points required depend on the size of the ship and the damage.
Each fully damaged cell costs 1 BP.
Example: A module of size 20 has 10% damage. That is equal to 2 cells, so it costs 2 BP.

## Components
Damage that is dealt to the hull is also dealt to a component.
An attacker might try to target a specific component (with knowledge).
Or the attacker has to chose a module (no knowledge).
Then a random component is chosen to be damaged.
Each component has structure point. These are similar to hull point.
Damage is subtracted from a components structure points.

	structure -= hull-damage
	
Depending on the component is can start to fail when damaged to a certain degree.
Some technologies are robust and work even heavily damaged, others are fragile and malfunction already with light damage.
Repair drones can be used to repair component damage (structure) during battle to regain a functioning component.

	structure += drone-repair
	
Each drone has a fix amount of damage/turn that can be repaired.
The player selects what drone should repair what component.
Multiple drones can work on the same component.

When damage is dealt to a components in a bank/cluster the cluster output is reduced accordingly when components are not functioning.
Also each component type has a property that indicates how many components in a cluster need to break down before the cluster breaks as a whole so there is no output at all.
Some components work like a grid of independent units so they partially work until almost all component of the cluster are gone.
Other components work as a whole so they do breakdown as a whole when too much damage is accumulated in a cluster.
This property is given in percent of components that need to break before the cluster breaks as a whole. 
100% means the cluster works until the last component. 10% means the cluster fails after 10% of its parts failed.

## Cloaking
The strength of the cloaking field determines how good the cloaking works.

	cloaking-strength = sum(cluster(cloaking-field-base))

If the scanner strength of an opponent is larger than the cloaking strength the cloaking is ineffective.

	cloaked = cloaking-strength > scanner-strength

That means its main benefit of attacking first is not given any longer. 
However, the ineffective cloaking still makes it harder to hit the ship.
Therefore it increases the chance-to-miss the cloaked ship by a fix percentage. 


## Manoeuvrability & Missing
Manoeuvrability expresses how well a spacecraft can change speed and direction in space.
Smaller spacescrafts are naturally more manoeuvrable than larger ones.
Faster spacescrafts more manoeuvrable than slow ones.
So manoeuvrability is based on size and speed.
Damage can be reduced for manoeuvrable targets as they are harder to hit.
To hit manoeuvrable targets with full damage anyway the attacker needs good computers. 
Size ranges from 5-500. Speed from 0-10 impulse.

	manoeuvrability = floor(5 * maxiumim-speed - (size)^1/2)
	
This gives a range from about 0-45. 
This is also equivalent to the basic chance to miss this spacecraft as a target.
This chance can be modified by 1/2 of its amount. 
Jammers are used by targets to increase their chance.
Computers are used by attackers to decrease the chance. 

	jammer-factor = min(100, 100 * sum(cluster(jammer-base)) / size)
	
100% jammer factor is reached when an output equal to the ship's size is created.
The factor is limited to a maximum of 100%.

Computers are less dependent on the target's size.
	
	computer-factor = min(100, size^(1/4) * sum(cluster(computer-base)))
	
That is about 1.6 to 5.6 times computer output. 
So an output of 18 (very large target) to 62 (very small target) is needed for a 100% factor.
Each weapon bank needs a computer. Usually such a computer is directly attached to the bank. 
Banks can share a computer but the output will be divided upon the banks.
The whole ship can share a computer. Such a central computer is not directly attached to any weapon.
The benefit of a central computer is the higher output per cell at larger size.
The drawback is a single point of failure.

	modification-factor = (jammer-factor - computer-factor)/100
	chance-to-miss = 	manoeuvrability + (manoeuvrability/2 * modification-factor) +/- special-effects

Of course the computer are those of the attacker, while manoeuvrability and jammer are those of the attacked spacecraft. Special effects are such like gatling modification of a weapon.

## Targeting
To target specific components the attacker needs knowledge or sufficient scanners to obtain that knowledge ad-hoc.
Scanners provide information about the target. 
How is it composed? What is the status? What is already damage or malfunctioning.
In general this information allows for better tactics (tactical decisions) by focusing on the weak points of the design and the ships current status.