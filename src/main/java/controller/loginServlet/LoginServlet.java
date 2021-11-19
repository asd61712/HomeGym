package controller.loginServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LogoutBean;
import model.StaffBean;
import service.StaffService;
import service.serviceimpl.StaffServiceImpl;
import util.GlobalService;

@WebServlet("/loginServlet.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginServlet() {
        super();
    }
    
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		// 定義存放錯誤訊息的Map物件
		Map<String, String> errorMsgMap = new HashMap<String, String>();

		// 將errorMsgMap放入request物件內，識別字串為 "ErrorMsgKey"
		request.setAttribute("ErrorMsgKey", errorMsgMap);
		
		//讀取使用者輸入
		String staffIdStr = request.getParameter("staffId");
//		Integer staffId = Integer.parseInt(staffIdStr.trim());
		String password = request.getParameter("staffPassword");
		
		
		
		
		//檢查使用者輸入資料
		if(staffIdStr == null){
			errorMsgMap.put("AccountEmptyError", "帳號欄必須輸入");
		}
		
		if (password == null || password.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "密碼欄必須輸入");
		}
		
		// 如果 errorMsgMap 不是空的，表示有錯誤，交棒給login.jsp
		if (!errorMsgMap.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
			return;
		}
		
		// 將MemberServiceImpl類別new為物件，存放物件參考的變數為 loginService
		StaffService staffService  = new StaffServiceImpl();
		
		// 將密碼加密兩次，以便與存放在表格內的密碼比對
		String encryptPassword = GlobalService.getMD5Endocing(GlobalService.encryptString(password));
		
		StaffBean sb = null;
		try {
			sb = staffService.findByMemberIdAndPassword(Integer.parseInt(staffIdStr.trim()), password);
			
			if(sb.getStaffStatus().equals("已離職")) {
				errorMsgMap.put("LoginError", "本員工已離職");
			} else if(sb != null) {
				// OK, 登入成功, 將mb物件放入Session範圍內，識別字串為"LoginOK"
				session.setAttribute("LoginOK", sb);
				// 建立登出所需的LogoutBean物件
				LogoutBean logoutBean = new LogoutBean(session);
				session.setAttribute("logoutBean", logoutBean);
//				String staffIdStr1 = staffId.toString();
				processCookies(request, response, staffIdStr, password);
				
			}else {
				errorMsgMap.put("LoginError", "該帳號不存在或密碼錯誤");
			}
			
		} catch (RuntimeException ex) {
//			errorMsgMap.put("LoginError", ex.getMessage());
			errorMsgMap.put("LoginError", "請輸入正確帳號密碼");
		}
		
		//依照 Business Logic 運算結果來挑選適當的畫面
		//如果 errorMsgMap 是空的，表示沒有任何錯誤，交棒給下一棒
		if (errorMsgMap.isEmpty()) {
			
//			if (requestURI != null) {
//				//                   如果長度是0  就回首頁   否則就 逛想逛的
//				requestURI = (requestURI.length() == 0 ? request.getContextPath() : requestURI); 
//				response.sendRedirect(response.encodeRedirectURL(requestURI));
//				return;
//			} else {
				String contextPath = request.getContextPath();
				response.sendRedirect(response.encodeRedirectURL(contextPath + "/Course/checkedCoursePage"));
//				return;
//			}
		} else {
			// 如果errorMsgMap不是空的，表示有錯誤，交棒給login.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
			return;
		}
		
		
	}
	
	
	private void processCookies(HttpServletRequest request, HttpServletResponse response, 
			String userId, String password) {
	// **********Remember Me****************************
		Cookie cookieUser = null;
		Cookie cookiePassword = null;
		Cookie cookieRememberMe = null;
	
		cookieUser = new Cookie("user", userId);
		cookieUser.setMaxAge(0); // MaxAge==0 表示要請瀏覽器刪除此Cookie
		cookieUser.setPath(request.getContextPath());
		
//		String encodePassword = GlobalService.encryptString(password);
//		cookiePassword = new Cookie("password", encodePassword);
		cookiePassword = new Cookie("password", password);
		cookiePassword.setMaxAge(0);
		cookiePassword.setPath(request.getContextPath());
		
		cookieRememberMe = new Cookie("rm", "true");
		cookieRememberMe.setMaxAge(0);
		cookieRememberMe.setPath(request.getContextPath());
		
		response.addCookie(cookieUser);
		response.addCookie(cookiePassword);
		response.addCookie(cookieRememberMe);
	}

}
