package com.hr.service;

import javax.servlet.http.HttpSession;

import com.hr.model.Company;
import com.hr.model.ResponseResult;

public interface CompanyService {
	/**
	 * 检查公司信息是否完整
	 */
	boolean checkResumeFull();

	/**
	 * 注册公司
	 * @param company
	 * @return
	 */
	Integer insertCompany(Company company);

	/**
	 * 检验公司名是否存在
	 * @param companyName
	 * @return
	 */
	boolean checkCompanyName(String companyName);
	/**
	 * 公司注册前检查
	 * @param result
	 * @param company
	 */
	void registryCheck(ResponseResult<Void> result,HttpSession session,String token,Company company);
}
