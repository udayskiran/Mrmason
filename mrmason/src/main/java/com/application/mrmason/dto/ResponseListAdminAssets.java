package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.AdminAsset;

import lombok.Data;
@Data
public class ResponseListAdminAssets {
	private String message;
	private boolean status;
    private List<AdminAsset> data;
}
