/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2017-2020 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2020 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.20 at 10:20:43 PM EDT 
//


package org.opennms.netmgt.config.prometheus;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.opennms.netmgt.collection.api.AttributeType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="filter-exp" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *       &lt;attribute name="alias-exp" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *       &lt;attribute name="type"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;pattern value="([Cc](ounter|OUNTER)|[Gg](auge|AUGE))"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="compress-alias" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "numeric-attribute")
public class NumericAttribute {

    @XmlAttribute(name = "filter-exp")
    @XmlSchemaType(name = "anySimpleType")
    protected String filterExp;
    @XmlAttribute(name = "alias-exp", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String aliasExp;
    @XmlAttribute(name="type")
    protected AttributeType type;
    @XmlAttribute(name = "compress-alias")
    protected Boolean compressAlias;

    /**
     * Gets the value of the filterExp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterExp() {
        return filterExp;
    }

    /**
     * Sets the value of the filterExp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterExp(String value) {
        this.filterExp = value;
    }

    /**
     * Gets the value of the aliasExp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasExp() {
        return aliasExp;
    }

    /**
     * Sets the value of the aliasExp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasExp(String value) {
        this.aliasExp = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeType }
     *     
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeType }
     *     
     */
    public void setType(AttributeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the compressAlias property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCompressAlias() {
        if (compressAlias == null) {
            return true;
        } else {
            return compressAlias;
        }
    }

    /**
     * Sets the value of the compressAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCompressAlias(Boolean value) {
        this.compressAlias = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumericAttribute)) return false;
        NumericAttribute that = (NumericAttribute) o;
        return Objects.equals(filterExp, that.filterExp) &&
                Objects.equals(aliasExp, that.aliasExp) &&
                type == that.type &&
                Objects.equals(compressAlias, that.compressAlias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterExp, aliasExp, type, compressAlias);
    }
}
