package se.jbee.game.any.logic;

import java.util.Arrays;

import se.jbee.game.any.state.State;


public final class Logic {

	@SuppressWarnings("unchecked")
	public static Logic empty() {
		return new Logic((Class<? extends Transition>[]) new Class<?>[0], new Transition[0]);
	}
	
	private final Class<? extends Transition>[] roles;
	private final Transition[] impls;
	
	public Logic add(Class<? extends Transition> role) throws Exception {
		return add(role, role.newInstance());
	}

	/**
	 * This can be used to "mock" some {@link Transition}s for checking purposes.  
	 */
	public Logic add(Class<? extends Transition> role, Transition impl) {
		Class<? extends Transition>[] roles = Arrays.copyOf(this.roles, this.roles.length+1);
		roles[this.roles.length] = role;
		Transition[] impls = Arrays.copyOf(this.impls, this.impls.length+1);
		impls[this.impls.length] = impl;
		return new Logic(roles, impls);
	}
	
	public Logic(Class<? extends Transition>[] types, Transition[] instances) {
		super();
		this.roles = types;
		this.impls = instances;
	}
	
	private Transition transition(Class<? extends Transition> type) {
		for (int i = 0; i < impls.length; i++)
			if (type == roles[i])
				return impls[i];
		throw new RuntimeException("No such transition: "+type);
	}
	
	public State runLoop(State game) {
		return run(roles[0], game);
	}

	public State run(Class<? extends Transition> type, State game) {
		Transition t = transition(type);
		if (t != impls[0]) {
			System.out.println("▶ "+type.getSimpleName());
		}
		try {
			return t.transit(game, this);
		} catch (Exception e) {
			e.printStackTrace();
			//TODO repair? error page?
			return game;
		}
	}

}
