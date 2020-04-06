package com.sapient.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RulesBean {
	
	private String name;
	private String key;
    private String category;
    private Integer operand1;
    private String operator;
    private Integer operand2;
    private Integer score;
    private boolean status;

}
