/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * com.myloverqian.util JettyService
 * <p>
 * import dom4j
 * </p>
 *
 * @author zhiwei
 * @create 2016-07-19 15:09.
 */
public class XmlUtils {

    /**
     * is xml
     *
     * @param value
     * @return
     */
    public static boolean isXml(String value) {
        try {
            DocumentHelper.parseText(value);
        } catch (DocumentException e) {
            return false;
        }
        return true;
    }

    /**
     * @param message
     * @return
     * @throws DocumentException
     */
    public static Document getDocument(String message) throws DocumentException {
        return DocumentHelper.parseText(message);
    }

    /**
     * @param fileName
     * @return
     * @throws DocumentException
     */
    public static Document getFileDocument(String fileName) throws DocumentException {
        return new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
    }

    /**
     * @param message
     * @return
     * @throws DocumentException
     */
    public static Element getRootElement(String message) throws DocumentException {
        return getDocument(message).getRootElement();
    }

    /**
     * @param fileName
     * @return
     * @throws DocumentException
     */
    public static Element getFileRootElement(String fileName) throws DocumentException {
        return getFileDocument(fileName).getRootElement();
    }

    /**
     * @param fileName
     * @param qName
     * @return
     * @throws DocumentException
     */
    public static List getFileElementList(String fileName, String qName) throws DocumentException {
        return getFileRootElement(fileName).elements(qName);
    }

    /**
     * @param elements
     * @return
     * @throws DocumentException
     */
    public static List<Map<String, String>> getEachElement(List elements) throws DocumentException {
        List<Map<String, String>> resultList = new ArrayList<>();
        if (elements.size() > 0) {
            for (Iterator it = elements.iterator(); it.hasNext(); ) {
                Map<String, String> resultMap = new HashMap<>();
                Element subElement = (Element) it.next();
                resultMap.put("eleName", subElement.getName());
                List subElements = subElement.elements();
                for (Iterator subIt = subElements.iterator(); subIt.hasNext(); ) {
                    Element son = (Element) subIt.next();
                    String name = son.getName();
                    String text = son.getText();
                    resultMap.put(name, text);
                }
                resultList.add(resultMap);
            }
        } else {
            throw new DocumentException("this element is disappeared");
        }
        return resultList;
    }

}
