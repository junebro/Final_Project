package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter @Setter @ToString
public class Nutri {
    private String nutriGenderState;
    private String nutriAge;
    private String nutriHeight;
    private String nutriWeight;
    private String selectedDisease;
    private String selectedAllergies;
    private String exerciseRate;
    private String soup;
    private Map<String, Map<String, List<String>>> drop;
}
