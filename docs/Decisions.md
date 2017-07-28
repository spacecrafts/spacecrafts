# Design Decisions

* each player can only manage population of its own race
* if population is captured and very similar to the own race they might just be treated as if they "become" the players race
* captured components might be incompatible with the new owner - they need to be demolished
* need to track who build a component to be able to tell what players can use it
* encounters happen at the start of each turn, then the user adjusts, then effects are applied (user gets exactly what was shown)
* "empty" slots (a frame) are a component that can be build into a ship (structure) in order to have the structure itself build something else later on. planets and orbits in that regard consist of kind of empty slots (the do not reduce costs). The frame reduces the building costs by 1. So the total cost does not change but the frame has to be build before the slots are filled what will delay the total time somehwat in most cases.
* materials are required to build modules at all.
* biological and silicon based races have a natural basic growth, robots and cyborgs do not
* each module can be made from another material
* module with the largest bridge is the main module
* there must be a single module with the largest bridge
* structural limits are viewed from the main module
* modules come with building costs.   