
package com.abhirockzz.wordpress.javaee.async.jaxrs.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    
    
    private String id;
    private String name;
    private String dob;

    public Student() {
    }

    public Student(String id, String name, String dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }
    
    
}
