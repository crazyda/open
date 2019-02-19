package com.axp.domain;

/**
 * AbstractReroleRepermissionId entity provides the base persistence definition
 * of the ReroleRepermissionId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReroleRepermissionId implements
		java.io.Serializable {

	// Fields

	private ReRole reRole;
	private RePermission rePermission;

	// Constructors

	/** default constructor */
	public AbstractReroleRepermissionId() {
	}

	/** full constructor */
	public AbstractReroleRepermissionId(ReRole reRole, RePermission rePermission) {
		this.reRole = reRole;
		this.rePermission = rePermission;
	}

	// Property accessors

	public ReRole getReRole() {
		return this.reRole;
	}

	public void setReRole(ReRole reRole) {
		this.reRole = reRole;
	}

	public RePermission getRePermission() {
		return this.rePermission;
	}

	public void setRePermission(RePermission rePermission) {
		this.rePermission = rePermission;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AbstractReroleRepermissionId))
			return false;
		AbstractReroleRepermissionId castOther = (AbstractReroleRepermissionId) other;

		return ((this.getReRole() == castOther.getReRole()) || (this
				.getReRole() != null && castOther.getReRole() != null && this
				.getReRole().equals(castOther.getReRole())))
				&& ((this.getRePermission() == castOther.getRePermission()) || (this
						.getRePermission() != null
						&& castOther.getRePermission() != null && this
						.getRePermission().equals(castOther.getRePermission())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getReRole() == null ? 0 : this.getReRole().hashCode());
		result = 37
				* result
				+ (getRePermission() == null ? 0 : this.getRePermission()
						.hashCode());
		return result;
	}

}