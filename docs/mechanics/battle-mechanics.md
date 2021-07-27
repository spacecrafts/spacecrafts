# Battle Mechanics

## Components

* add-ons have no action on their own during battle, they are either switched on
  or off to improve actual component clusters
* computers are add-ons with several roles
* computers improve chance to hit
* computers improve when weapons can be fired

## Effective Output, Damage and Staff

Only cells that are staffed and not beyond their damage threshold are
considered. Clusters and boots are always recomputed based on the actual
effectual composition.

## Supply Priority

Each cluster gets a priority assigned which determines the order in which
clusters use the available resources. Priority 1 is the highest. Higher numbers
are lower priority.

## Targeting

Without targeting random cell(s) are hit. With targeting the targeted cell is
the center. Depending on the success of the targeting a cell with max distance X
from the center is hit.

--------------------------------------------------------------------------------

--------------------------------------------------------------------------------

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

