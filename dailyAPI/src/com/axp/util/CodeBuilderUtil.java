package com.axp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 该类的主要作用在于生成那些需要重复操作的类似于dao，service之类的代码；
 * @author Administrator
 *
 */
public class CodeBuilderUtil {

	public static void main(String[] args) {
		//build(CashshopTypeLable.class);
	}

	//需要生成代码的domain中的类的类名；
	private static String className;

	//项目的绝对基础路径，形如：C:\Java\Workspaces\jupinhuiInterface\src\com\axp
	private static String basePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "com" + File.separator
			+ "axp";

	/**
	 * 直接生成通用的类的代码；
	 * 生成内容；
	 * 1，dao的接口类，
	 * 2，dao的接口的实现类；
	 * 3，service的接口类；
	 * 4，service的接口实现类；
	 * 5，baseServiceImpl中的注入值；
	 * 6，baseController中的注入值；
	 * 
	 * @param clazz 需要生成的类对应的domain对象；
	 */
	public static <T> void build(Class<T> clazz) {
		System.out.println("==================================================");

		className = clazz.getSimpleName();

		Boolean b1 = buildDaoInterface();
		if (!b1) {
			System.out.println("创建dao接口类出错");
			return;
		} else {
			System.out.println("创建dao接口类成功");
		}

		Boolean b2 = buildDao();
		if (!b2) {
			System.out.println("创建dao接口类的实现类出错");
			return;
		} else {
			System.out.println("创建dao接口类的实现类成功");
		}

		Boolean b3 = buildServiceInterface();
		if (!b3) {
			System.out.println("创建service接口类出错");
			return;
		} else {
			System.out.println("创建service接口类成功");
		}

		Boolean b4 = buildService();
		if (!b4) {
			System.out.println("创建dao接口类的实现类出错");
			return;
		} else {
			System.out.println("创建dao接口类的实现类成功");
		}

		Boolean b5 = insertIntoBaseServiceImpl();
		if (!b5) {
			System.out.println("向baserServiceImpl中插入数据出错");
			return;
		} else {
			System.out.println("向baserServiceImpl中插入数据成功");
		}

		Boolean b6 = insertIntoBaseController();
		if (!b6) {
			System.out.println("向BaseController中插入数据出错");
			return;
		} else {
			System.out.println("向BaseController中插入数据成功");
		}

		System.out.println("==================================================");
	}

	/**
	 * 生成dao的接口类；
	 * @return 是否成功；
	 */
	private static Boolean buildDaoInterface() {
		String fileName = "I" + className + "Dao";
		File file = new File(basePath + File.separator + "dao", fileName + ".java");

		if (file.exists()) {//此路径不应该存在；
			return false;
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.print("package com.axp.dao;\r");
			writer.print("import com.axp.domain." + className + ";\r");
			writer.print("public interface " + fileName + " extends IBaseDao<" + className + "> {}");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

		return false;
	}

	/**
	 * 生成dao的接口的实现类；
	 * @return 是否成功；
	 */
	private static Boolean buildDao() {
		String fileName = className + "DaoImpl";
		File file = new File(basePath + File.separator + "dao" + File.separator + "impl", fileName + ".java");

		if (file.exists()) {//此路径不应该存在；
			return false;
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.print("package com.axp.dao.impl;\r");
			writer.print("import org.springframework.stereotype.Repository;\r");
			writer.print("import com.axp.dao.I" + className + "Dao;\r");
			writer.print("import com.axp.domain." + className + ";\r");
			writer.print("@Repository \r");
			writer.print("public class " + className + "DaoImpl extends BaseDaoImpl<" + className + "> implements I" + className + "Dao {}");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

		return false;
	}

	/**
	 * 生成Service的接口类；
	 * @return 是否成功；
	 */
	private static Boolean buildServiceInterface() {
		String fileName = "I" + className + "Service";
		File file = new File(basePath + File.separator + "service", fileName + ".java");

		if (file.exists()) {//此路径不应该存在；
			return false;
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.print("package com.axp.service;\r");
			writer.print("import com.axp.domain." + className + ";\r");
			writer.print("public interface " + fileName + " extends IBaseService<" + className + "> {}");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

		return false;
	}

	/**
	 * 生成Service的接口的实现类；
	 * @return 是否成功；
	 */
	private static Boolean buildService() {
		String fileName = className + "ServiceImpl";
		File file = new File(basePath + File.separator + "service" + File.separator + "impl", fileName + ".java");

		if (file.exists()) {//此路径不应该存在；
			return false;
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.print("package com.axp.service.impl;\r");
			writer.print("import org.springframework.stereotype.Service;\r");
			writer.print("import com.axp.domain." + className + ";\r");
			writer.print("import com.axp.service.I" + className + "Service;\r");
			writer.print("@Service\r");
			writer.print("public class " + className + "ServiceImpl extends BaseServiceImpl<" + className + "> implements I" + className
					+ "Service {}");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

		return false;
	}

	/**
	 * 向BaseServiceImpl中插入内容；
	 * @return 是否成功；
	 */
	private static Boolean insertIntoBaseServiceImpl() {
		File file = new File(basePath + File.separator + "service" + File.separator + "impl", "BaseServiceImpl.java");

		if (!file.exists()) {//此路径应该存在；
			return false;
		}

		//获取里面的所有内容；
		BufferedReader reader = null;
		FileOutputStream out = null;
		StringBuilder sb = new StringBuilder(500);
		try {
			//获取原有的内容；
			reader = new BufferedReader(new FileReader(file));
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
				sb.append("\r");
			}

			//在原有内容中插入内容；
			int index = sb.lastIndexOf("IBaseService<T> {");
			sb.insert(index + "IBaseService<T> {".length(),
					" \r  @Autowired \r public I" + className + "Dao " + StringUtil.lowerFirstChar(className) + "Dao; ");
			int index2 = sb.lastIndexOf("package com.axp.service.impl;");
			sb.insert(index2 + "package com.axp.service.impl;".length(), "import com.axp.dao.I" + className + "Dao;\r");

			//将插入后的新内容写回到原有内容中；
			out = new FileOutputStream(file);
			out.write(sb.toString().getBytes());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	/**
	 * 向BaseController中插入内容；
	 * @return 是否成功；
	 */
	private static Boolean insertIntoBaseController() {
		File file = new File(basePath + File.separator + "controller", "BaseController.java");

		if (!file.exists()) {//此路径应该存在；
			return false;
		}

		//获取里面的所有内容；
		BufferedReader reader = null;
		FileOutputStream out = null;
		StringBuilder sb = new StringBuilder(500);
		try {
			//获取原有的内容；
			reader = new BufferedReader(new FileReader(file));
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
				sb.append("\r");
			}

			//在原有内容中插入内容；
			int index = sb.lastIndexOf("package com.axp.controller;");
			sb.insert(index + "package com.axp.controller;".length(), "import com.axp.service.I" + className + "Service;\r");
			int index2 = sb.lastIndexOf("public class BaseController {");
			sb.insert(index2 + "public class BaseController {".length(),
					" \r @Autowired \r I" + className + "Service " + StringUtil.lowerFirstChar(className) + "Service;");

			//将插入后的新内容写回到原有内容中；
			out = new FileOutputStream(file);
			out.write(sb.toString().getBytes());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

}
