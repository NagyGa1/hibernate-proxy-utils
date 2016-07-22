package sg.studium.hproxyutil;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 * Separated to allow using the class only if hibernate is on the classpath.
 */
class HibernateDeproxy {

	/**
	 * @param entity to be deproxied
	 * @return deproxied instance of the entity
	 */
	@SuppressWarnings("unchecked")
	static <T> T deproxy(final T entity) {
		if (entity == null) {
			return null;
		}

		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			return (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
					.getImplementation();
		}

		return entity;
	}

	/**
	 * @param entity to get the class of
	 * @return original entity class without the proxy-class.
	 */
	static <T> Class getClass(final T entity) {
		if (entity instanceof HibernateProxy) {
			return Hibernate.getClass(entity);
		}
		return entity.getClass();
	}
}
