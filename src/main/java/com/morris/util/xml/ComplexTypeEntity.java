package com.morris.util.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "complexType")
public class ComplexTypeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String name;

	private SequenceEntity sequence = new SequenceEntity();

	private List<ElementEntity> attribute;

	public SequenceEntity getSequence() {
		return sequence;
	}

	public void setSequence(SequenceEntity sequence) {
		this.sequence = sequence;
	}

	public List<ElementEntity> getAttribute() {
		return attribute;
	}

	public void setAttribute(List<ElementEntity> attribute) {
		this.attribute = attribute;
	}

	public void addAttribute(ElementEntity attribute) {
		if (this.attribute == null) {
			this.attribute = new ArrayList<ElementEntity>();
		}
		this.getAttribute().add(attribute);
	}

	@Override
	public String toString() {
		return "ComplexTypeEntity [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComplexTypeEntity(String name, SequenceEntity sequence,
			List<ElementEntity> attribute) {
		super();
		this.name = name;
		this.sequence = sequence;
		this.attribute = attribute;
	}

	public ComplexTypeEntity(String name) {
		super();
		this.name = name;
	}

	public ComplexTypeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
