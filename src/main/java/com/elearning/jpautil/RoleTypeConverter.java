package com.elearning.jpautil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.elearning.entities.Role;

@Converter
public class RoleTypeConverter implements AttributeConverter<Role, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Role role) {
		return role.getValue();
	}

	@Override
	public Role convertToEntityAttribute(Integer value) {
		for (Role type : Role.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException(" Illegal tagType value exception.");
	}
}
