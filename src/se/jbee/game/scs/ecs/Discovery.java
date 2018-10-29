package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.constant.Ability;
import se.jbee.game.scs.ecs.constant.Material;
import se.jbee.game.scs.ecs.system.System;

/**
 * just an idea: technology can lead to a discovery in the same way a discovery
 * can be made through experience, for example by investigating sights on
 * planets, by travelling in other states of minds and things like that.
 *
 * These could be linked to a discovery that gives new {@link System.Ability}s or
 * {@link Material}s or {@link System}s and so forth.
 *
 * Benefit of capturing this in a separate entity is easier management and
 * presentation for the player as different mechanisms feed into one that adds
 * things to a players profile.
 *
 * All discoveries can be browsed and searched in a log book.
 */
@Entity("discovery")
public final class Discovery extends Event {

	public Ability.Ref addsAbility;
	public System.Ref<?> addsEquipment;
}
