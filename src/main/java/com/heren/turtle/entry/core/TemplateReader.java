/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.core;


import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by luoxiaoming on 16-2-26.
 */
public class TemplateReader {

    public Template instanceTemplate(Document doc) throws Exception {
        Template template = new Template(doc);
        return template;
    }

    public class Template {
        private Set<Property> request;
        private Set<Property> response;
        private Set<String> keyWords;

        public Set<Property> getRequest() {
            return request;
        }

        public Set<Property> getResponse() {
            return response;
        }

        public Set<String> getKeyWords() {
            return keyWords;
        }

        public Template(Document doc) throws Exception {
            Element root = doc.getRootElement();

            Element request = root.element(REQUEST);
            this.request = analyzer(request);
            Element response = root.element(RESPONSE);
            this.response = analyzer(response);
            Element keyWordsEl = root.element(KEY_WORDS);
            this.keyWords = reader(keyWordsEl);
        }


        private Set<String> reader(Element keyWordsEl) {
            Set<String> keyWords = new HashSet<>();
            Element list = keyWordsEl.element(KEY_LIST);
            for (Iterator<Element> keys = list.elementIterator(); keys.hasNext(); ) {
                keyWords.add(keys.next().getTextTrim());
            }
            return keyWords;
        }

        private Set<Property> analyzer(Element payloadEL) {
            Set<Property> payload = new HashSet<>();
            for (Iterator<Element> iterator = payloadEL.elementIterator(); iterator.hasNext(); ) {
                Element el = iterator.next();
                Property property = analyzerToProperty(el);
                payload.add(property);
            }
            return payload;
        }

        private Property analyzerToProperty(Element element) {
            Property property = new Property();
            String name = element.attributeValue(NAME);
            String comments = element.attributeValue(COMMENTS);
            String maxLength = element.attributeValue(MAX_LENGTH);
            String dateType = element.attributeValue(DATE_TYPE);
            String nullable = element.attributeValue(NULLABLE);
            String majorProperty = element.attributeValue(MAJOR_PROPERTY);
            property.setName(name);
            property.setComments(comments);
            property.setMaxLength(maxLength != null ? Integer.valueOf(maxLength) : 0);
            if (dateType == null) {
                property.setDateType("string");
            } else {
                property.setDateType(dateType);
            }
            if (nullable == null) {
                property.setNullable(true);
            } else {
                property.setNullable(Boolean.valueOf(nullable));
            }
            if (majorProperty == null) {
                property.setMajorProperty(false);
            } else {
                property.setMajorProperty(Boolean.valueOf(majorProperty));
            }
            List<Element> subElements = element.elements();
            if (element.attributeValue(NAME).equals("in_hospital_list")) {
                System.out.println(subElements.size());
            }
            if (subElements != null && subElements.size() > 0) {
                if (property.getSubProperties() == null) {
                    property.setSubProperties(new HashSet<>());
                }
                for (Iterator<Element> iterator = subElements.iterator(); iterator.hasNext(); ) {
                    property.getSubProperties().add(analyzerToProperty(iterator.next()));
                }
            }
            return property;
        }
    }


    class Property {

        private String name;
        private String comments;
        private int maxLength;
        private String dateType;
        private boolean nullable;
        private boolean majorProperty;
        private boolean array;
        private Set<Property> subProperties;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        public String getDateType() {
            return dateType;
        }

        public void setDateType(String dateType) {
            this.dateType = dateType;
        }

        public boolean isNullable() {
            return nullable;
        }

        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }

        public boolean isMajorProperty() {
            return majorProperty;
        }

        public void setMajorProperty(boolean majorProperty) {
            this.majorProperty = majorProperty;
        }

        public void setArray(boolean array) {
            this.array = array;
        }

        public Set<Property> getSubProperties() {
            return subProperties;
        }

        public void setSubProperties(Set<Property> subProperties) {
            this.subProperties = subProperties;
        }

        public boolean isArray() {
            return subProperties != null && subProperties.size() > 0;
        }

    }

    public static final String REQUEST = "request";

    public static final String RESPONSE = "response";

    public static final String KEY_WORDS = "keyWords";

    public static final String KEY_LIST = "list";

    public static final String NAME = "name";

    public static final String DATE_TYPE = "dateType";

    public static final String NULLABLE = "nullable";

    public static final String MAJOR_PROPERTY = "majorProperty";

    public static final String MAX_LENGTH = "maxLength";

    public static final String COMMENTS = "comments";


}
