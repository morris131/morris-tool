package com.morris.util.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sequence")
public class SequenceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ElementEntity> element = new ArrayList<ElementEntity>();

	public List<ElementEntity> getElement() {
		return element;
	}

	public void setElement(List<ElementEntity> element) {
		this.element = element;
	}
	
	public void addElement(ElementEntity e){
		for (ElementEntity elementEntity : element) {
			if(elementEntity.getName().equals(e.getName())){
				elementEntity.setMaxOccurs("unbounded");
				return;
			}
		}
		this.element.add(e);
	}

}
