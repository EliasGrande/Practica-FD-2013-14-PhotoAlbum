package es.udc.fi.dc.photoalbum.utils;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class PrivacyLevel {

	public static final String PRIVATE = "PRIVATE";
	public static final String SHAREABLE = "SHAREABLE";
	public static final String PUBLIC = "PUBLIC";
	
	/**
	 * Lista de valores posibles de nivel de privacidad.
	 */
	public static final List<String> LIST = Arrays.asList(new String[] {
			PRIVATE, SHAREABLE, PUBLIC });
	
	/**
	 * Nombre por defecto de la propiedad privacyLevel en las entities definidas
	 * en hibernate.
	 */
	private static final String DEFAULT_PROPERTY_NAME = "privacyLevel";
	
	/**
	 * Comprueba que el valor de privacidad indicado está entre los posibles.
	 * Útil en validacion de formularios.
	 */
	public static boolean validate(String privacyLevel) {
		return LIST.contains(privacyLevel);
	}

	/**
	 * Filtro según nivel mínimo de privacidad, es decir, si por ejemplo nos
	 * pasan SHAREABLE filtrará los PRIVATE y dejará pasar los PUBLIC y
	 * SHAREABLE.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad, útil en joins.
	 * @param minPrivacyLevel
	 *            Nivel de privacidad mínimo.
	 */
	public static Criterion minPrivacyLevelCriterion(String propertyName,
			String minPrivacyLevel) {
		return (minPrivacyLevel.equals(PUBLIC))
				? minPublicCriterion(propertyName)
				: (minPrivacyLevel.equals(SHAREABLE))
						? minShareableCriterion(propertyName)
						: minPrivateCriterion(propertyName);
	}

	/**
	 * Filtro según nivel mínimo de privacidad, es decir, si por ejemplo nos
	 * pasan SHAREABLE filtrará los PRIVATE y dejará pasar los PUBLIC y
	 * SHAREABLE. Usa como nombre de propiedad el nombre por defecto.
	 * 
	 * @param minPrivacyLevel
	 *            Nivel de privacidad mínimo.
	 */
	public static Criterion minPrivacyLevelCriterion(String minPrivacyLevel) {
		return minPrivacyLevelCriterion(DEFAULT_PROPERTY_NAME, minPrivacyLevel);
	}

	/**
	 * Filtro que solo deja pasar a los PUBLIC.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad, útil en joins.
	 */
	private static Criterion minPublicCriterion(String propertyName) {
		return Restrictions.like(propertyName, PUBLIC, MatchMode.EXACT);
	}

	/**
	 * Filtro que solo deja pasar a los PUBLIC y SHAREABLES.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad, útil en joins.
	 */
	private static Criterion minShareableCriterion(String propertyName) {
		return Restrictions.not(Restrictions.like(propertyName, PRIVATE,
				MatchMode.EXACT));
	}

	/**
	 * Filtro deja pasar todo, solo comprueba que no sea NULL.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad, útil en joins.
	 */
	private static Criterion minPrivateCriterion(String propertyName) {
		return Restrictions.isNotNull(propertyName);
	}
}