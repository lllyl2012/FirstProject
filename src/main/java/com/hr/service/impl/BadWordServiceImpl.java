package com.hr.service.impl;

import org.springframework.stereotype.Service;

import com.hr.service.BadWordService;

@Service("badWordService")
public class BadWordServiceImpl implements BadWordService{
	@Override
	public boolean checkWordIllegal(String word) {
		// TODO Auto-generated method stub
		return false;
	}
}
