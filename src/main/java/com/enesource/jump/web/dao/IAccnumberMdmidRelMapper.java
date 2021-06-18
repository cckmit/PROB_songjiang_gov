package com.enesource.jump.web.dao;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.AccnumberMdmidRelDTO;

public interface IAccnumberMdmidRelMapper {
	/**
	 * 保存户号与电表关联关系
	 * @param dto
	 */
	public void saveAMRel(AccnumberMdmidRelDTO dto);
	
	
	/**
	 * 更新关联关系表同步标记
	 * @param dto
	 */
	public void updateAMRelSyn(AccnumberMdmidRelDTO dto);
	
	
	/**
	 * 删除户号与电表关联关系
	 * @param dto
	 */
	public void delAMRel(AccnumberMdmidRelDTO dto);
	
	
	/**
	 * 查询户号下的电表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> findDeviceList(Map<String, Object> paramMap);
}
