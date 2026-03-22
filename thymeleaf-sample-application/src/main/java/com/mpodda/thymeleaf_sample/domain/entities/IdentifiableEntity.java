package com.mpodda.thymeleaf_sample.domain.entities;


import com.mpodda.thymeleaf_sample.utils.Serializer;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public class IdentifiableEntity {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Version
	protected Long version = Long.valueOf(1);

	public Long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Long version) {
        this.version = version;
    }
	
	public String toJson() {
		return Serializer.objectToJsonString(this);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}

		if (!(object instanceof IdentifiableEntity)) {
			return false;
		}

		IdentifiableEntity identifiableEntity = (IdentifiableEntity)object;

		if (this.getId() == null || identifiableEntity.getId() == null) {
			return false;
		}

		return this.getId().equals(identifiableEntity.getId());
	}
	
	public boolean isNewEntry() {
		return this.id == null;
	}
}
