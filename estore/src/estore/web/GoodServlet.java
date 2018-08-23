package estore.web;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;

import estore.domain.Good;
import estore.domain.PageBean;
import estore.service.IGoodService;
import estore.service.impl.GoodServiceImpl;

/**
 * Servlet implementation class GoodServlet
 */
public class GoodServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	IGoodService goodService = new GoodServiceImpl();
	// ��Ʒservlet
	public void findAllGoods(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 1. ��ȡ����
		
		// 2. ����service
		List<Good> goodList = goodService.findAllGoods();
		
		// 3.������Ӧ
		request.setAttribute("goodList", goodList);
		request.getRequestDispatcher("/goods.jsp").forward(request, response);
	}
	// ��Ʒ��ϸ��Ϣ����
	public  void findGoodDetail(HttpServletRequest request, HttpServletResponse response){
		// 1. ��ȡ����
		String id = request.getParameter("id");
		// 2.����service
		Good good = goodService.findGoodDetail(id);
		
		request.setAttribute("good", good);
		try {
			request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��̨����ϵͳ   ��ȡ��Ʒ����  ��ҳ��ѯ
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void findAllGoodsByJson(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		// ��ȡ����   rows  ����ÿҳ��С     page  ����ǰҳ
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		
		
		// ����service����з�ҳ����
		String strJson = goodService.findGoodByPage(pageSize,currentPage);
		
		response.getWriter().write(strJson);
	}
	public void addGood(HttpServletRequest request, HttpServletResponse response){
		
		// �Ӻ�̨�����ݿ��������Ʒ   ����ͼƬ�ļ�
		// ����������
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> fileItem = fileUpload.parseRequest(request);
			// ��������  ��������\
			// ���ȴ���һ��map ���� ������װ������
			Map<String ,Object> map = new HashMap<String,Object>();
			for (FileItem fileItem2 : fileItem) {
				if(fileItem2.isFormField()){
					// ��ʾ�Ǳ�  ��װ��javabean  ���浽���ݿ���
					// ���Ȼ�ȡ���ݱ��е�key �� value  
					String key = fileItem2.getFieldName();
					String value = fileItem2.getString("utf-8");
					map.put(key, value);
					
				}else {
					// ͼƬ�ļ�����   д��Ӳ��(��Ŀ������·���л��ߵ�����ͼƬ������)
					String name = fileItem2.getName();
					
					/**
					 * �ص�  :  �ϴ�ͼƬʱ ϣ����ֱ���ϴ�����������  ��ֱ�Ӳ鿴
					 * ����������ͼƬ��·��
					 * getServletContext().getRealPath("/upload")+"/"+name;
					 */
					String savePath=getServletContext().getRealPath("/upload")+"/"+name;
					savePath=savePath.replace("estore", "imgServer");
					fileItem2.write(new File(savePath));
					
					/**
					 * ����: �����ݱ��浽��������, һ���޸���Ŀ����,���·���֮��  ͼƬ���ᶪʧ
					 * 
					 * �Ľ�: �����ݱ��浽����һ��ͼƬ��������
					 *  ��tomcat ��webapps���½�һ���������ļ��� ��ͼƬ���浽����Ŀ��
					 */
					
					//��Ʊ��浽����     ����ļ�·��
					String dbPath = "/upload/"+name;
					map.put("imgurl", dbPath);
				}
			}
			
			// ��map �е����ݷ�װ��Good ����
			Good good = new Good();
			BeanUtils.populate(good, map);
			
			// ����   service �㷽��  �����ݱ��浽���ݿ���
			goodService.addGood(good);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findAllGoodsByRm(HttpServletRequest request, HttpServletResponse response){
		
		// ��ѯ������Ʒ
		String json = goodService.findAllGoodsByRm();
		
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// ��ҳ��ѯ
	public void findGoodByPage(HttpServletRequest request, HttpServletResponse response){
		
		// ���õ�ǰҳ��Ϊ1
		int currentPage = 1;
		String page = request.getParameter("currentPage");
		if(org.apache.commons.lang3.StringUtils.isNoneEmpty(page)){
			currentPage=Integer.parseInt(page);
		}
		PageBean pageBean = goodService.findGoodByPageHead(currentPage);
		request.setAttribute("pageBean", pageBean);
		try {
			request.getRequestDispatcher("goods.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}




