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
	// 商品servlet
	public void findAllGoods(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 1. 获取参数
		
		// 2. 调用service
		List<Good> goodList = goodService.findAllGoods();
		
		// 3.给出响应
		request.setAttribute("goodList", goodList);
		request.getRequestDispatcher("/goods.jsp").forward(request, response);
	}
	// 商品详细信息介绍
	public  void findGoodDetail(HttpServletRequest request, HttpServletResponse response){
		// 1. 获取参数
		String id = request.getParameter("id");
		// 2.调用service
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
	 * 后台管理系统   获取商品详情  分页查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void findAllGoodsByJson(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		// 获取参数   rows  代表每页大小     page  代表当前页
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		
		
		// 调用service层进行分页操作
		String strJson = goodService.findGoodByPage(pageSize,currentPage);
		
		response.getWriter().write(strJson);
	}
	public void addGood(HttpServletRequest request, HttpServletResponse response){
		
		// 从后台向数据库中添加商品   包含图片文件
		// 创建解析器
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> fileItem = fileUpload.parseRequest(request);
			// 遍历集合  解析集合\
			// 首先创建一个map 集合 用来封装表单数据
			Map<String ,Object> map = new HashMap<String,Object>();
			for (FileItem fileItem2 : fileItem) {
				if(fileItem2.isFormField()){
					// 表示是表单  封装成javabean  保存到数据库中
					// 首先获取数据表单中的key 和 value  
					String key = fileItem2.getFieldName();
					String value = fileItem2.getString("utf-8");
					map.put(key, value);
					
				}else {
					// 图片文件内容   写入硬盘(项目发布的路径中或者单独的图片服务器)
					String name = fileItem2.getName();
					
					/**
					 * 重点  :  上传图片时 希望能直接上传到服务器中  能直接查看
					 * 服务器保存图片的路径
					 * getServletContext().getRealPath("/upload")+"/"+name;
					 */
					String savePath=getServletContext().getRealPath("/upload")+"/"+name;
					savePath=savePath.replace("estore", "imgServer");
					fileItem2.write(new File(savePath));
					
					/**
					 * 升级: 将数据保存到服务器中, 一旦修改项目代码,从新发布之后  图片将会丢失
					 * 
					 * 改进: 将数据保存到另外一个图片服务器中
					 *  在tomcat 的webapps中新建一个发布的文件夹 将图片保存到该项目中
					 */
					
					//设计保存到数据     库的文件路径
					String dbPath = "/upload/"+name;
					map.put("imgurl", dbPath);
				}
			}
			
			// 将map 中的数据封装到Good 类中
			Good good = new Good();
			BeanUtils.populate(good, map);
			
			// 调用   service 层方法  将数据保存到数据库中
			goodService.addGood(good);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findAllGoodsByRm(HttpServletRequest request, HttpServletResponse response){
		
		// 查询热门商品
		String json = goodService.findAllGoodsByRm();
		
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 分页查询
	public void findGoodByPage(HttpServletRequest request, HttpServletResponse response){
		
		// 设置当前页码为1
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




