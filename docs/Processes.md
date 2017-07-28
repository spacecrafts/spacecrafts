# User Processes

A list of all the user actions that change game state.

**Overview**

* dismiss leader
* hire leader
* move leader (to another colony/ship)
* capture colony/ship
* shift energy priority (a list like: life-support, weapons, shields, drive, research, ...)
* build component
* deactivate component (for lack of energy)
* activate component (semi-automatic when enough energy is available)
* demolish component
* move ship in solar system
* move ship in galaxy 
* create fleet
* join fleet
* leave fleet
* reassign profession 
* move population (between structures; like colony and orbit)
* move module (between structures; also become a structure on their own)
* propose diplomatic agreement
* (re/un)assign component/structure to plan
* create plan
* terminate plan


# Economic Processes

A list of all the computational processes that advance the game state based upon the previous game state.

**React to Changes**

* adjust energy (after priority shift; force deactivations; auto-activate)
* adjust staff (due to energy and population and component changes)


**Advance Turn**

* compute produced energy
* compute used energy
* compute available housing (sum biosphere, domes, bays * bonuses)
* compute food production (= sum farms, domes and biospheres * bonuses)
* compute food usage (= population size)
* compute gained/lost culture (= sum components * bonuses)
* compute yard production (sum yards + workers * bonuses)
* compute gained knowledge (= sum labs * bonuses)
* compute gained wisdom (= sum academies * bonuses)
* compute gained rare materials (= sum mines * bonuses)
* compute rare material discovery chance and determine discovery
* adjust global knowledge, wisdom and reputation (add completed techs)
* adjust cultural level
* adjust population (applied over/underflow to fixed buffer [size depends on game speed])
* adjust components completion (add completed components/structures)
* adjust energy
* adjust staff


# AI Processes

A list of all computations done to determine the strategy/actions of AI players.
As the AI does same things as a human player this list only features intermediate steps 
used as basis of the decisions of the human-like actions.


**Overview**

* ...


# Event Processes

A list of all computations done to initialize new events

**Overview**

* reputation based events
* attracted leaders (wisdom based event)
* ...
