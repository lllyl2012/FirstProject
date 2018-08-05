package com.hr.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.hr.model.Company;
import com.hr.model.ResponseResult;
import com.hr.service.CompanyService;
import com.hr.service.UserService;

@Service("companyInfoService")
public class CompanyServiceImpl implements CompanyService {

	@Resource
	private UserService userService;

	/**
	 * 检查公司信息是否完整
	 */
	public boolean checkResumeFull() {
		try {
			// 执行持久层的方法,如果数据所有库字段都非空,就返回true
			// 如果有字段是空,就抛出异常,并被catch到返回false
			// 这里以后写持久层方法
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 注册公司
	 */
	@Override
	public Integer insertCompany(Company company) {
		return null;
	}

	/**
	 * 检验公司名是否存在
	 */
	@Override
	public boolean checkCompanyName(String companyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registryCheck(ResponseResult<Void> result, HttpSession session, String token, Company company) {
		boolean ifHaveName = checkCompanyName(company.getCompanyName());
		if (ifHaveName) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("该公司名已存在");
		}
		// 验证用户名是否存在
		boolean ifHaveUsername = userService.checkHaveUser(company.getUsername());
		if (ifHaveUsername) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("该用户名已存在");
		}
	}
}
