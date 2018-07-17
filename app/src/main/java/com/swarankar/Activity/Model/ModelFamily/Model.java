package com.swarankar.Activity.Model.ModelFamily;

import com.swarankar.Activity.Activity.Family1Activity;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by softeaven on 7/3/17.
 */

public class Model implements Serializable {
    private String id, name, relation, relationInfo, partnerName;
    private JSONArray sonName, daughterName;

    public String getRelationInfo() {
        return relationInfo;
    }

    public void setRelationInfo(String relationInfo) {
        this.relationInfo = relationInfo;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public JSONArray getSonName() {
        return sonName;
    }

    public void setSonName(JSONArray sonName) {
        this.sonName = sonName;
    }

    public JSONArray getDaughterName() {
        return daughterName;
    }

    public void setDaughterName(JSONArray daughterName) {
        this.daughterName = daughterName;
    }

    public Model(String name, String relation) {
        this.name = name;
        this.relation = relation;
    }

    public Model() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }


}
