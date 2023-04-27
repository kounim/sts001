package com.co.kr.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.kr.domain.BoardListDomain;
import com.co.kr.domain.LoginDomain;
import com.co.kr.service.UploadService;
import com.co.kr.service.UserService;
import com.co.kr.util.CommonUtils;
import com.co.kr.util.Pagination;
import com.co.kr.vo.LoginVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j 
@RequestMapping(value = "/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UploadService uploadService;

	@RequestMapping(value = "board")
	public ModelAndView login(LoginVO loginDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		Map<String, String> map = new HashMap();
		map.put("mbId", loginDTO.getId());
		map.put("mbPw", loginDTO.getPw());
		
		int dupleCheck = userService.mbDuplicationCheck(map);
		LoginDomain loginDomain = userService.mbGetId(map);
		
		if(dupleCheck == 0) {  
			String alertText = "없는 아이디이거나 패스워드가 잘못되었습니다. 가입해주세요";
			String redirectPath = "/main/signin";
			CommonUtils.redirect(alertText, redirectPath, response);
			return mav;
		}

		String IP = CommonUtils.getClientIP(request);
		
		String MAC = getLocalMacAddress();
		
		
		session.setAttribute("mac",MAC);
		session.setAttribute("ip",IP);
		session.setAttribute("id", loginDomain.getMbId());
		session.setAttribute("mbLevel", loginDomain.getMbLevel());
				
		List<BoardListDomain> items = uploadService.boardList();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		
		mav.setViewName("board/boardList.html"); 
		
		return mav;
	};
	
	 public String getLocalMacAddress() {
		 	String result = "";
			InetAddress ip;

			try {
				ip = InetAddress.getLocalHost();
			   
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				byte[] mac = network.getHardwareAddress();
			   
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
					result = sb.toString();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e){
				e.printStackTrace();
			}
			    
			return result;
		 }
	

	@RequestMapping(value = "bdList")
	public ModelAndView bdList() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.boardList();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("board/boardList.html");
		return mav; 
	};
	
	@RequestMapping(value = "seoulBoard")
	public ModelAndView seoulBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.seoulBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("region/seoul/seoulBoard.html");
		return mav; 
	};
	
	@RequestMapping(value = "gangBoard")
	public ModelAndView gangBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.gangBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("region/gang/boardList.html");
		return mav; 
	};
	
	@RequestMapping(value = "chungBoard")
	public ModelAndView chungBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.chungBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("region/chung/boardList.html");
		return mav; 
	};
	
	@RequestMapping(value = "gyeongBoard")
	public ModelAndView gyeongBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.gyeongBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("region/gyeong/boardList.html");
		return mav; 
	};
	
	@RequestMapping(value = "jeonBoard")
	public ModelAndView jeonBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.jeonBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("region/jeon/boardList.html");
		return mav; 
	};
	
	@RequestMapping(value = "etcBoard")
	public ModelAndView etcBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.etcBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("region/etc/boardList.html");
		return mav; 
	};
	
	@RequestMapping(value = "tradeBoard")
	public ModelAndView tradeBoard() { 
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.tradeBoard();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("trade/boardList.html");
		return mav; 
	};
	
	
	
	
	
	
	
	
	
	@GetMapping("mbList")
	public ModelAndView mbList(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String page = (String) session.getAttribute("page");
		if(page==null)page="1";
		
		session.setAttribute("page", page);
		mav=mbListCall(request);
		mav.setViewName("admin/adminList.html");
		return mav;
	};
	
	public ModelAndView mbListCall(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		int totalcount=userService.mbGetAll();
		int contentnum=10;
		
		boolean itemsNotEmpty;
		if(totalcount>0) {
			itemsNotEmpty=true;
			Map<String, Object> pagination=Pagination.pagination(totalcount, request);
			Map map = new HashMap<String, Integer>();
			map.put("offset", pagination.get("offset"));
			map.put("contentnum", contentnum);
			
			List<LoginDomain> loginDomain=userService.mbAllList(map);
			mav.addObject("itemsNotEmpty", itemsNotEmpty);
			mav.addObject("items", loginDomain);
			mav.addObject("rowNUM", pagination.get("rowNUM"));
			mav.addObject("pageNum", pagination.get("pageNum"));
			mav.addObject("startpage", pagination.get("startpage"));
			mav.addObject("endpage", pagination.get("endpage"));
		}else {
			itemsNotEmpty=false;
		}
		return mav;
	}
	
	@GetMapping("/modify/{mbSeq}")
    public ModelAndView mbModify(@PathVariable("mbSeq") String mbSeq, RedirectAttributes re) throws IOException {
		ModelAndView mav = new ModelAndView();
		re.addAttribute("mbSeq", mbSeq);
		mav.setViewName("redirect:/mbEditList");
		return mav;
	};
	
	@GetMapping("mbEditList")
	public ModelAndView mbListEdit(@RequestParam("mbSeq") String mbSeq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav = mbListCall(request);  
		Map map = new HashMap<String, String>();
		map.put("mbSeq", mbSeq);
		LoginDomain loginDomain = userService.mbSelectList(map);
		mav.addObject("item",loginDomain);
		mav.setViewName("admin/adminEditList.html");
		return mav; 
	};
	
	@RequestMapping("/update")
	public ModelAndView mbModify(LoginVO loginVO, HttpServletRequest request, RedirectAttributes re) throws IOException {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String page = "1";
		LoginDomain loginDomain = null;
		
		String IP = CommonUtils.getClientIP(request);
		loginDomain = LoginDomain.builder()
				.mbSeq(Integer.parseInt(loginVO.getSeq()))
				.mbId(loginVO.getId())
				.mbPw(loginVO.getPw())
				.mbLevel(loginVO.getLevel())
				.mbIp(IP)
				.mbUse("Y")
				.build();
		userService.mbUpdate(loginDomain);
		re.addAttribute("page",page);
		mav.setViewName("redirect:/mbList");
		return mav;
	};
	
	@GetMapping("/remove/{mbSeq}")
    public ModelAndView mbRemove(@PathVariable("mbSeq") String mbSeq, RedirectAttributes re, HttpServletRequest request) throws IOException {
		ModelAndView mav = new ModelAndView();
		Map map = new HashMap<String, String>();
		map.put("mbSeq", mbSeq);
		userService.mbRemove(map);
		HttpSession session = request.getSession();
		re.addAttribute("page",session.getAttribute("page"));
		mav.setViewName("redirect:/mbList");
		return mav;
	};
	
	@PostMapping("create")
	public ModelAndView create(LoginVO loginVO, HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String page = (String) session.getAttribute("page");
		if(page == null)page = "1";
		
		Map<String, String> map = new HashMap();
		map.put("mbId", loginVO.getId());
		map.put("mbPw", loginVO.getPw());
		
		int dupleCheck = userService.mbDuplicationCheck(map);
		System.out.println(dupleCheck);

		if(dupleCheck > 0) {
			String alertText = "중복이거나 유효하지 않은 접근입니다";
			String redirectPath = "/main";
			System.out.println(loginVO.getAdmin());
			if(loginVO.getAdmin() != null) {
				redirectPath = "/main/mbList?page="+page;
			}
			CommonUtils.redirect(alertText, redirectPath, response);
		}else {
			String IP = CommonUtils.getClientIP(request);
			int totalcount = userService.mbGetAll();
			LoginDomain loginDomain = LoginDomain.builder()
					.mbId(loginVO.getId())
					.mbPw(loginVO.getPw())
					.mbLevel((totalcount == 0) ? "3" : "1")
					.mbIp(IP)
					.mbUse("Y")
					.build();
			userService.mbCreate(loginDomain);
			
			if(loginVO.getAdmin() == null) {
				session.setAttribute("ip",IP);
				session.setAttribute("id", loginDomain.getMbId());
				session.setAttribute("mbLevel", (totalcount == 0) ? "3" : "1");
				mav.setViewName("redirect:/bdList");
			}else {
				mav.setViewName("redirect:/mbList?page=1");
			}
		}
		return mav;
	};
	
	@GetMapping("signin")
    public ModelAndView signIn() throws IOException {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("signin/signin.html"); 
        return mav;
    };
    
    @RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();
		mav.setViewName("index.html");
		return mav;
	}
	
	
	
}