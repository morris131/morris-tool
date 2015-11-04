package com.morris.util.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "element")
public class ElementEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String type;

	@XmlAttribute
	private String maxOccurs;

	public ElementEntity(String name, String type, String maxOccurs) {
		super();
		this.name = name;
		this.type = type;
		this.maxOccurs = maxOccurs;
	}

	public ElementEntity(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public ElementEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

}
