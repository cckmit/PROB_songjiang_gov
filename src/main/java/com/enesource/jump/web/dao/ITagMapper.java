package com.enesource.jump.web.dao;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.TagDTO;

public interface ITagMapper {
	
	/**
	 * 保存标签
	 * @param dto
	 */
	public void saveTag(TagDTO dto);
	
	/**
	 * 保存标签关联关系
	 * @param dto
	 */
	public void saveTagRel(TagDTO dto);
	
	/**
	 * 删除标签
	 * @param dto
	 */
	public void delTag(TagDTO dto);
	
	/**
	 * 删除标签关联关系
	 * @param dto
	 */
	public void delTagRelByIdentifier(TagDTO dto);
	
	/**
	 * 验证标签名称是否重复
	 * @param dto
	 */
	public Integer checkTagName(TagDTO dto);
	
	
	/**
	 * 查询企业标签列表
	 * @param paramMap
	 * @return
	 */
	public List<TagDTO> findTagListByCompanyId(Map<String, Object> paramMap);
	
	/**
	 * 查询地区所有标签列表
	 * @param paramMap
	 * @return
	 */
	public List<TagDTO> findTagListByArealable(Map<String, Object> paramMap);
	
	
	
	/**
	 * 更新关注标签
	 * @param paramMap
	 * @return
	 */
	public void updateFollowTag(Map<String, Object> paramMap);
	
	
	/**
	 * 标签查询条件列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> findTagList(Map<String, Object> paramMap);
	
	
}
