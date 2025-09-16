package com.rays.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rays.dto.UserDTO;

public class BaseCtl<F extends BaseForm, T extends BaseDTO, S extends BaseServiceInt<T>> {

	protected UserContext userContext;

	@Value("${page.size}")
	private int pageSize = 0;

	@ModelAttribute
	public void setUserContext(HttpSession session) {
		userContext = (UserContext) session.getAttribute("userContext");
		if (userContext == null) {
			UserDTO dto = new UserDTO();
			dto.setLoginId("root@sunilos.com");
			userContext = new UserContext(dto);
		}

	}

	@Autowired
	protected S baseService;

	public ORSResponse validate(BindingResult bindingResult) {

		ORSResponse res = new ORSResponse(true);

		if (bindingResult.hasErrors()) {
			res.setSuccess(false);

			Map<String, String> errors = new HashMap<String, String>();

			List<FieldError> list = bindingResult.getFieldErrors();
			list.forEach(e -> {
				errors.put(e.getField(), e.getDefaultMessage());
				System.out.println("Field :: " + e.getField() + "  Message :: " + e.getDefaultMessage());
			});
			res.addInputError(errors);
		}
		return res;

	}

	@PostMapping("/save")
	public ORSResponse save(@RequestBody @Valid F form, BindingResult bindingResult) {

		ORSResponse res = validate(bindingResult);

		if (res.isSuccess() == false) {
			return res;
		}

		try {
			T dto = (T) form.getDto();

			if (dto.getId() != null && dto.getId() > 0) {
				T existDto1 = (T) baseService.findByUniqueKey(dto.getUniqueKey(), dto.getUniqueValue(), userContext);
				if (existDto1 != null && dto.getId() != existDto1.getId()) {
					res.addMessage(dto.getLabel() + " already exist");
					res.setSuccess(false);
					return res;
				}
				baseService.update(dto, userContext);
				res.addMessage("Data updated successfully..!!");
			} else {
				if (dto.getUniqueKey() != null && !dto.getUniqueKey().equals("")) {
					T existDto = (T) baseService.findByUniqueKey(dto.getUniqueKey(), dto.getUniqueValue(), userContext);
					if (existDto != null) {
						res.addMessage(dto.getLabel() + " already exist");
						res.setSuccess(false);
						return res;
					}
				}
				baseService.add(dto, userContext);
				res.addMessage("Data added successfully..!!");
			}
			res.addData(dto.getId());
		} catch (Exception e) {
			res.setSuccess(false);
			res.addMessage(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	@PostMapping("deleteMany/{ids}")
	public ORSResponse deleteMany(@PathVariable String[] ids, @RequestParam("pageNo") String pageNo,
			@RequestBody F form) {

		ORSResponse res = new ORSResponse(true);
		try {
			for (String id : ids) {
				baseService.delete(Long.parseLong(id), userContext);

			}
			T dto = (T) form.getDto();

			List<T> list = baseService.search(dto, Integer.parseInt(pageNo), pageSize, userContext);

			res.addData(baseService.search(dto, Integer.parseInt(pageNo), pageSize, userContext));
			res.setSuccess(true);
			res.addMessage("Records Deleted Successfully");
			System.out.println("Records Deleted Successfully");

		} catch (Exception e) {
			res.setSuccess(false);
			res.addMessage(e.getMessage());
		}
		return res;
	}

	@RequestMapping(value = "/search/{pageNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody F form, @PathVariable int pageNo) {

		System.out.println("BaseCtl Search method with pageNo :: " + pageNo + "   Page size is :: " + pageSize);

		pageNo = (pageNo < 0) ? 0 : pageNo;

		T dto = (T) form.getDto();

		ORSResponse res = new ORSResponse(true);

		List list = baseService.search(dto, pageNo, pageSize, userContext);

		res.addData(list);

		List nextList = baseService.search(dto, pageNo + 1, pageSize, userContext);

		res.addResult("nextList", nextList.size());

		return res;
	}

}
