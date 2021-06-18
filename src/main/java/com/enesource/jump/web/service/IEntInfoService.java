package com.enesource.jump.web.service;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.AccnumberMdmidRelDTO;
import com.enesource.jump.web.dto.EntInfoDTO;
import com.enesource.jump.web.dto.MultiplesDTO;

public interface IEntInfoService {

	void updateEntInfo(Map<String, Object> paramMap, EntInfoDTO dto);
	
	/**
	 * 更新企业基础信息
	 * @param paramMap
	 * @param dto
	 */
	void updateEntBaseInfo(Map<String, Object> paramMap, EntInfoDTO dto);
	
	/**
	 * 更新企业标签信息
	 * @param paramMap
	 * @param dto
	 */
	void updateEntTagInfo(Map<String, Object> paramMap, EntInfoDTO dto);
	
	/**
	 * 更新企业资料文件信息
	 * @param paramMap
	 * @param dto
	 */
	void updateEntFileInfo(Map<String, Object> paramMap, EntInfoDTO dto);
	
	/**
	 * 更新企业电力户号信息
	 * @param paramMap
	 * @param dto
	 */
	void updateEntAccnumberInfo(Map<String, Object> paramMap, AccnumberMdmidRelDTO dto);
	
	/**
	 * 更新企业其他户号信息
	 * @param paramMap
	 * @param dto
	 */
	void updateEntOtherAccnumberInfo(Map<String, Object> paramMap, EntInfoDTO dto);

    List<Map<String, Object>> multipleStatistics(Map<String, Object> paramMap);
}
