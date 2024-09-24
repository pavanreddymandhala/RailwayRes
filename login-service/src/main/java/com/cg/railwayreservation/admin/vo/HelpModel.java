package com.cg.railwayreservation.admin.vo;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpModel {
	
    private String issueId;
	private String username;
	private String issue;
	private String status;
	private String solution;

}
