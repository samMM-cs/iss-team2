package com.game.model.battle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Move {

    public Move() {
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private MoveType type;

    @JsonProperty("value")
    private int value;

    @JsonProperty("cost")
    private int cost;

    @JsonProperty("requirements")
    private List<String> req;

    @JsonProperty("description")
    private String description;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public MoveType getType() {
        return this.type;
    }

    public int getValue() {
        return this.value;
    }

    public int getCost() {
        return this.cost;
    }

    public List<String> getReq() {
        return req;
    }
    public String getDescription() {
        return this.description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
