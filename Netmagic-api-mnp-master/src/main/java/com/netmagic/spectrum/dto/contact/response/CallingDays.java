package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the list of calling days for the contact
 * 
 * @author webonise
 *
 */
public class CallingDays implements Serializable {

    private static final long serialVersionUID = -6125801363635756470L;

    @JsonProperty("contactCallingTypeId")
    private Long id;
    @JsonProperty("contactCallingTypeName")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
