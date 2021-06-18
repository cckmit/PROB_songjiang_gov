package com.enesource.jump.web.dao;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.DataDTO;
import com.enesource.jump.web.dto.UserInfoDTO;


public interface ICommonMapper {
	/**
	 * 获得人员跳转路径
	 * @param param
	 * @return
	 */
	public List<DataDTO> findData();
	
	
	/**
	 * 根据用户名获得用户
	 * @param name
	 * @return
	 */
	public String findByUsername(String name);
	
	/**
	 * 检查密码
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> checkUserPwd(Map<String, Object> paramMap);
	
	/**
	 * 获得菜单
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getMenuList(Map<String, Object> paramMap);
	
	
	/**
	 * 获得省份列表
	 * @return
	 */
	public List<Map<String, Object>> getProvinceList(Map<String, Object> paramMap);
	
	/**
	 * 获得街道列表
	 * @return
	 */
	public List<Map<String, Object>> getStreetList(Map<String, Object> paramMap);
	
	/**
	 * 获得行业列表
	 * @return
	 */
	public List<Map<String, Object>> getIndustryList(Map<String, Object> paramMap);

	Map<String, Object> checkUserByUserId(String userId);

    String getSsoAuth(String areaLabel);

	UserInfoDTO getUserInfoByUserId(String userId);

    int updateUserPassWord(Map<String, Object> paramMap);
    
    /**
	 * 插入用户操作记录
	 * @param paramMap
	 */
	public void insertUserOperation(Map<String, Object> paramMap);
	
	/**
	 * 验证是否白名单
	 * @param paramMap
	 */
	public Integer checkwhitelist(String ip);

	String selectLimit1IP();
}
