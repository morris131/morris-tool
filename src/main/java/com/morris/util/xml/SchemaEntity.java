package com.morris.util.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "schema", namespace="http://www.w3.org/2001/XMLSchema")
public class SchemaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ElementEntity element;

	private List<ComplexTypeEntity> complexType = new ArrayList<ComplexTypeEntity>();

	public ElementEntity getElement() {
		return element;
	}

	public void setElement(ElementEntity element) {
		this.element = element;
	}

	public List<ComplexTypeEntity> getComplexType() {
		return complexType;
	}

	public void setComplexType(List<ComplexTypeEntity> complexType) {
		this.complexType = complexType;
	}
	
	public void addComplexType(ComplexTypeEntity complexType){
		this.complexType.add(complexType);
	}
	
	public boolean hasComplexType(String name){
		boolean flag = false;
		for ( ComplexTypeEntity complex:  complexType) {
			//System.out.println(complex.getName() + ":" +name);
			if(complex.getName().equals(name)){
				flag = true;
				break;
			}
		}
		return flag;
	}

}
