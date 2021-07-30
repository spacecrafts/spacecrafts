package se.jbee.game.any.screen;

import static java.lang.Math.max;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScreenNo {

	int value();
	
	static final class Init {
		@SafeVarargs
		public final static Screen[] screens(Class<? extends Screen>... screens) {
			int max = 0;
			for (Class<?> c : screens) {
				ScreenNo no = c.getAnnotation(ScreenNo.class);
				max = max(max, no.value());
			}
			Screen[] res = new Screen[max+1];
			for (int i = 0; i < screens.length; i++) {
				try {
					Class<? extends Screen> screen = screens[i];
					ScreenNo no = screen.getAnnotation(ScreenNo.class);				
					res[no.value()] = screen.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return res;
		}
	}
}
