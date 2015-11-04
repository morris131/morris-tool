package com.morris.util.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import com.sun.tools.xjc.XJCFacade;

public class Xml2Entity {

	private SchemaEntity schema = new SchemaEntity();
	
	/**
	 * @throws Throwable 
	 */
	public void generateEntity(String xml, String dirName, String packageName) throws Throwable {

		Document document = DocumentHelper.parseText(xml);

		Element rootElement = document.getRootElement();

		schema.setElement(new ElementEntity("element", rootElement.getName()));

		root(rootElement);

		JAXBContext context = JAXBContext.newInstance(SchemaEntity.class);

		Marshaller marshaller = context.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter out = new StringWriter();
		OutputFormat format = new OutputFormat();
		format.setIndent(true);
		format.setNewlines(true);
		format.setNewLineAfterDeclaration(false);
		XMLWriter writer = new XMLWriter(out, format);

		XMLFilterImpl nsfFilter = new XMLFilterImpl() {

			@Override
			public void startDocument() throws SAXException {
				super.startDocument();
			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes atts) throws SAXException {
				super.startElement(uri, localName, "xsd:" + localName, atts);
			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				super.endElement(uri, localName, "xsd:" + localName);
			}

			@Override
			public void startPrefixMapping(String prefix, String url)
					throws SAXException {
				super.startPrefixMapping("xsd", url);

			}
		};

		nsfFilter.setContentHandler(writer);
		marshaller.marshal(schema, nsfFilter);
		
		File tempFile = new File("temp.xsd");
		
		BufferedReader bufferedReader = new BufferedReader(new StringReader(out.toString()));
		
		PrintWriter printWriter = new PrintWriter(tempFile);
		
		String bufLine = "";
		
		while(null != (bufLine = bufferedReader.readLine())){
			printWriter.println(bufLine);
		}
		
		printWriter.close();
		bufferedReader.close();
		
		List<String> argsList = new ArrayList<String>();
		argsList.add(tempFile.getName());
		argsList.add("-d");
		argsList.add(dirName);
		argsList.add("-p");
		argsList.add(packageName);
		argsList.add("-encoding");
		argsList.add("utf-8");
		argsList.add("-no-header");
		
		String[] args = new String[argsList.size()];
		
		XJCFacade.main(argsList.toArray(args));

	}

	/**
	 * 根据传递进来的字符串判断是否是 int double date类型
	 * 
	 * @param data
	 * @return 返回schema中的类型如int返回xsd:int
	 */
	private String getDataType(String data) {
		String result = "xsd:";

		// 使用正则匹配
		if (data.matches("^-?\\d{1,9}$")) {
			result += "int";
		} else if (data.matches("^-?\\d+.\\d+$")) {
			result += "double";
		} else if (data
				.matches("(^\\d{4}-\\d{1,2}-\\d{1,2}$)|(^\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}$)")) {
			result += "date";
		} else {
			result += "string";
		}

		return result;
	}

	@SuppressWarnings({ "unchecked" })
	private void root(Element element) {

		if (schema.hasComplexType(element.getName())) {
			return;
		}

		ComplexTypeEntity complexType = new ComplexTypeEntity(element.getName()
				.toString());

		Iterator<Element> elementIterator = element.elementIterator();

		while (elementIterator.hasNext()) {
			Element childElement = elementIterator.next();

			String type = "xsd:string";

			if (childElement.elements().size() > 0) {
				type = childElement.getName();
			} else if (childElement.getData() instanceof String) {
				type = getDataType(childElement.getTextTrim());

			}

			complexType.getSequence().addElement(
					new ElementEntity(childElement.getName(), type));

			if (childElement.elements().size() != 0) {
				root(childElement);
			}

		}

		Iterator<Attribute> attributeIterator = element.attributeIterator();

		while (attributeIterator.hasNext()) {
			Attribute attribute = attributeIterator.next();

			String type = getDataType(attribute.getText());

			complexType.addAttribute(new ElementEntity(attribute.getName(),
					type));

		}
		schema.addComplexType(complexType);

	}

}
