package com;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/BoardFileInsert.do")
public class BoardFileInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 public BoardFileInsert() {
		    
	  }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			
			profileUpload(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void profileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 이미지를 업로드할 경로		
		String saveDirectory = request.getSession().getServletContext().getRealPath("/savefile");
		
		String savefile = "savefile";
    	ServletContext scontext = getServletContext();
    	String realFolder = scontext.getRealPath(savefile);
    	// 폴더명을 저장
    	File folder = new File(realFolder);
		try {
			if (!folder.exists()) {
				folder.mkdirs();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		System.out.println(saveDirectory);

		// 크기
		int maxPostSize = 10 * 1024 * 1024;
		
		// 응답해주는 값
		PrintWriter out = response.getWriter();
		// 파일저장
		MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
		Enumeration<?> files =multi.getFileNames();
		String formName=(String)files.nextElement();
		String fileName=multi.getFilesystemName(formName); // 파일의 이름 얻기
		
		
		
		
		
		if(fileName == null) { 
			// 파일이 업로드 되지 않았을때 
			out.print("파일 업로드 되지 않았음"); 
		} else { // 파일이 업로드 되었을때 
			String uploadPath = "savefile\\" + fileName;
			
			System.out.println("savefile\\" + fileName);
			
			JSONObject jobj = new JSONObject();
			jobj.put("url", uploadPath);
			jobj.put("fileName", fileName);
			
			response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
			System.out.println("jobj.toJSONString()은 ? : " + jobj.toJSONString());
			response.getWriter().print(jobj.toJSONString());

		}
		
	}
}
