package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.Rentel;

import lombok.Data;
@Data
public class ResponseListRentelDto {
	private String message;
	private boolean status;
	private List<Rentel> data;
}
