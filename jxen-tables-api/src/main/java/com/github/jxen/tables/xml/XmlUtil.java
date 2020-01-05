package com.github.jxen.tables.xml;

import com.github.jxen.tables.api.TableException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

final class XmlUtil {

	private XmlUtil() {
	}

	/**
	 * Generates DOM from given InputStream.
	 *
	 * @param input InputStream to read from
	 * @return Document generated from file
	 */
	static Document open(InputStream input) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(input);
		} catch (SAXException | ParserConfigurationException | IOException e) {
			throw new TableException("Unable to open document", e);
		}
	}

	/**
	 * Search for elements for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return array of <code>Element</code>s found
	 */
	static Element[] getElements(Node context, String xPath) {
		try {
			NodeList list = (NodeList) prepare(xPath).evaluate(context, XPathConstants.NODESET);
			int length = list.getLength();
			Element[] elements = new Element[length];
			for (int i = 0; i < length; i++) {
				elements[i] = (Element) list.item(i);
			}
			return elements;
		} catch (XPathExpressionException e) {
			throw new TableException("Unable to read elements", e);
		}
	}

	/**
	 * Search for text for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return text found
	 */
	static String getText(Node context, String xPath) {
		try {
			return (String) prepare(xPath).evaluate(context, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			throw new TableException("Unable to read text", e);
		}
	}

	/**
	 * Search for number for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return number found
	 */
	static Number getNumber(Node context, String xPath) {
		try {
			return (Number) prepare(xPath).evaluate(context, XPathConstants.NUMBER);
		} catch (XPathExpressionException e) {
			throw new TableException("Unable to read number", e);
		}
	}

	/**
	 * Checks if given XPath exists in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return true if XPath exists
	 */
	static boolean exists(Node context, String xPath) {
		try {
			return (Boolean) prepare(xPath).evaluate(context, XPathConstants.BOOLEAN);
		} catch (XPathExpressionException e) {
			throw new TableException("Unable to check node", e);
		}
	}

	private static XPathExpression prepare(String xPath) throws XPathExpressionException {
		return XPathFactory.newInstance().newXPath().compile(xPath);
	}
}
