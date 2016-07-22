package sg.studium.hproxyutil;

import java.io.Serializable;

public final class HProxyUtil implements Serializable {

	private static boolean hibernateOnClasspath;

	private static Class<?> hibernateProxyClass;

	static {
		try {
			// Detecting if org.hibernate.proxy.HibernateProxy on classpath.
			hibernateProxyClass = Class.forName("org.hibernate.proxy.HibernateProxy");
			// Yes
			hibernateOnClasspath = true;
		} catch (ClassNotFoundException e) {
			// No
			hibernateOnClasspath = false;
			hibernateProxyClass = null;
		}
	}

	/**
	 * http://stackoverflow.com/a/2216603
	 * <p>
	 * Use .size() and other referencing to trigger load for collections. For
	 * single objects there is no choice.
	 *
	 * @param entity to be de-proxied
	 * @return de-proxied entity
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deproxy(final T entity) {
		if (hibernateOnClasspath) {
			return HibernateDeproxy.deproxy(entity);
		} else {
			return entity;
		}
	}

	/**
	 * @param entity to get the class of
	 * @return original entity class without the proxy-class.
	 */
	public static Class<?> getClass(final Object entity) {
		if (hibernateOnClasspath) {
			return HibernateDeproxy.getClass(entity);
		} else {
			return entity.getClass();
		}
	}

	/**
	 * @return true if Hibernate detected, false otherwise
	 */
	public static boolean isHibernateOnClasspath() {
		return hibernateOnClasspath;
	}

	/**
	 * @return null if not on classpath, otherwise org.hibernate.proxy.HibernateProxy.
	 */
	public static Class<?> getHibernateProxyClass() {
		return hibernateProxyClass;
	}

	private static final long serialVersionUID = 1L;

	private HProxyUtil() {
	}

}
