# Design Decisions

* each player can only manage population of its own race
* if population is captured and very similar to the own race they might just be treated as if they "become" the players race
* captured components might be incompatible with the new owner - they need to be demolished
* need to track who build a component to be able to tell what players can use it
* encounters happen at the start of each turn, then the user adjusts, then effects are applied (user gets exactly what was shown)
* "empty" slots (a frame) are a component that can be build into a ship (structure) in order to have the structure itself build something else later on. planets and orbits in that regard consist of kind of empty slots (the do not reduce costs). The frame reduces the building costs by 1. So the total cost does not change but the frame has to be build before the slots are filled what will delay the total time somehwat in most cases. Buildings that cost 1 will not be free.
* materials are required to build modules at all.
* biological and silicon based races have a natural basic growth, robots and cyborgs do not
* each module can be made from another material
* size limits are given through materials
* modules have a small additional cost on their own (could be in terms of consumed energy)
* automation for orbital dock and the yard are more or less on opposite of the wheel - this way one has to go round to get this prod boost
* spies can do sabotage during battle (they can be on a specific enemy ship)
* troops can do sabotage (more damage with knowledge)
* there should be ways to damage enemy economy other than having spies do sabotage
* space stations can be build in orbits of gas giants
* colony independent space stations require a separate technology (extracting supplied from gas giants)
* components have a specific weight
* battle speed depends on weight and impulse drive (more weight needs more drive to get similar speed)
* wrap speed is ration of ship size (not weight) to wrap drive (smaller ships travel faster easier)
* overload of energy weapons (up to 200% output if energy is available, can damage weapon) is an option during battle that has to researched
* overload properties of weapons are different (how likely it is that it will not be damaged) 
* when modules are connected each module can only connect to a similar sum of structure as it has itself. this makes sure that the "center" part is stable enough to carry the added modules. This gives another reason to use string materials as it allows to connect more mass to them. Stating point of the analysis is the module with most structure.
* 100% plating is similar to structure sum. One can also chose a thinner plating. Maybe fix steps: 25-50-75-100% to allow some plating.
* maybe plating should not be allowed to exceed structure by a factor of 2 or something like that
* event: a stranger offers blueprints for something else, may turn out to be fake
* black market: trade unofficial goods, like old ships, technologies, rare resources
* maybe it is a good idea to let the user pay for rocket speed. Rockets with impulse 6 speed cost more then those with 4 and so on. Or one gets less with higher speed vs. more with lower. 
* as size is a key factor for manoeuvrability, small spacecrafts naturally have a higher manoeuvrability.
* gatling weapon modification reduces chance to fully miss by firing 4 shots with 1/4 damage. Each of which might miss. The modification costs 1 extra per cell and can be used with beam and mass guns. For rockets this has a different name but essentially one gets 4 smaller rockets launched together instead of 1. gatling should decrease chance-to-miss directly.
* wrap disturber does not affect the possibility to use a wrap drive but disturbs the sensors needed to plan the jump without crashing into mass on the way. So ships can always chose to escape but the more powerful the wrap disturber used is, the higher the chance the ship is destroyed in wrap collision.
* there should be a property of material or plating that is the limit of damage that can be absorbed. That means if the damage is to small it has no effect at all. The weapon simply is to weak to do something significant to the material. If the damage is larger the full damage is done as the weapon is effective. The limit is not subtracted from the damage in some way. This should provide an argument against many small weapons instead of few large ones. It could be as simple as using the materials structure as the limit. Maybe 1x for structure and 2x for plating. Simple and quite fitting values.
* some components (with a curve going down not up) need to be counted globally. Otherwise one would just build many small units to avoid getting less effective outputs. It still makes sense to build one bank or many to make it more secure against sabotage or damage 
* "beam" means a weapon uses fast impulses (can be used from afar)
* "energy" means a weapon converts energy to the output
* "mass" means a weapon fires particles or matter.
* "gun" means a weapon uses slower projectiles (used in closer distance and therefore later)
* "plasma" means a weapon uses a mass/energy plasma discharge
* shields differ also by the weapons that can be used while the shield is active
* laser has benefit of firing first (speed of light)
* particle guns fire particles at about 1/2 speed of light
* mass guns fire matter much slower than speed of light
* rockets are even slower than mass guns
