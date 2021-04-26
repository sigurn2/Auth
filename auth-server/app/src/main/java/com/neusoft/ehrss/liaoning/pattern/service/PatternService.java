package com.neusoft.ehrss.liaoning.pattern.service;

import com.neusoft.ehrss.liaoning.pattern.controller.dto.UserPatternDTO;

public interface PatternService {
	
	public void bind(String account, UserPatternDTO dto);
	
	public void verify(String account, UserPatternDTO dto);

}
