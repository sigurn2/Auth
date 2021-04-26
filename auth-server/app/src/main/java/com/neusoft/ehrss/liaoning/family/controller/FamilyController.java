package com.neusoft.ehrss.liaoning.family.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.ehrss.liaoning.family.dto.FamilyDTO;
import com.neusoft.ehrss.liaoning.family.dto.FamilyQueryDTO;
import com.neusoft.ehrss.liaoning.family.service.FamilyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/family")
public class FamilyController {

	private static final Logger log = LoggerFactory.getLogger(FamilyController.class);

	@Autowired
	private FamilyService familyService;

	@ApiOperation(value = "POST添加/更新家庭成员", tags = "家庭成员操作接口", notes = "添加/更新家庭成员FamilyController")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createFamily(Principal user, @RequestBody List<FamilyDTO> dtos) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuth = authentication.getUserAuthentication();
		Object principal = userAuth.getPrincipal();
		String account = (String) principal;
		log.debug("添加家庭成员，account={}", account);
		familyService.createFamily(account, dtos, "mobile", "Auth");
	}

	@ApiOperation(value = "DELETE删除家庭成员", tags = "家庭成员操作接口", notes = "删除家庭成员FamilyController")
	@DeleteMapping(path = "/{familyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFamily(Principal user, @PathVariable("familyId") String familyId) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuth = authentication.getUserAuthentication();
		Object principal = userAuth.getPrincipal();
		String account = (String) principal;
		log.debug("删除家庭成员，account={}，familyId={}", account, familyId);
		familyService.deleteFamily(account, familyId);
	}

	@ApiOperation(value = "GET查询家庭成员", tags = "家庭成员操作接口", notes = "查询家庭成员FamilyController")
	@GetMapping
	public List<FamilyQueryDTO> queryFamily(Principal user) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuth = authentication.getUserAuthentication();
		Object principal = userAuth.getPrincipal();
		String account = (String) principal;
		log.debug("查询家庭成员，account={}", account);
		return familyService.queryFamily(account);
	}

	@ApiOperation(value = "POST一体机添加/更新家庭成员", tags = "家庭成员操作接口", notes = "一体机添加/更新家庭成员FamilyController")
	@PostMapping(value = "/atm/{account}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENT')")
	public void createFamilyForAtm(@PathVariable("account") String account, @RequestBody List<FamilyDTO> dtos) {
		log.debug("一体机添加家庭成员，account={}", account);
		familyService.createFamily(account, dtos, "atm", "Card");
	}

}
