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